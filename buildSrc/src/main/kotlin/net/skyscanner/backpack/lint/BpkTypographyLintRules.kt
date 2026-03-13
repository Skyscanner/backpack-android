/**
 * Backpack for Android - Skyscanner's Design System
 *
 * Copyright 2018 - 2026 Skyscanner Ltd
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.skyscanner.backpack.lint

import com.google.common.base.CaseFormat
import net.skyscanner.backpack.tokens.BpkTextStyles
import net.skyscanner.backpack.tokens.BpkTransformer

/**
 * Generates lint detector rule files from Backpack typography tokens.
 * Maps (fontSize, fontWeight) pairs to their corresponding BpkTheme.typography style names.
 */
object BpkTypographyLintRules {

    sealed class Format<Output> : BpkTransformer<BpkTextStyles, Output> {

        data class TypographyLintDetectorMap(val namespace: String) : Format<String>() {
            override fun invoke(source: BpkTextStyles): String =
                toTypographyLintDetectorMap(source, namespace)
        }
    }

    private fun normalizeWeight(weight: String): String =
        CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, weight.replace('-', '_')).let {
            if (it == "Book") "Normal" else it
        }

    private fun toTypographyLintDetectorMap(source: BpkTextStyles, namespace: String): String {
        // Group styles by (fontSize, fontWeight) pair
        val grouped = source
            .groupBy(
                keySelector = {
                    val sp = it.fontSize.value.toDouble().toInt()
                    val weight = normalizeWeight(it.fontWeight.name)
                    sp to weight
                },
                valueTransform = {
                    val tokenName = CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, it.name.replace('-', '_'))
                    "$namespace.$tokenName"
                },
            )
            .toSortedMap(compareBy<Pair<Int, String>> { it.first }.thenBy { it.second })

        val entries = grouped.map { (key, tokens) ->
            val (sp, weight) = key
            val tokenList = tokens.joinToString(", ") { "\"$it\"" }
            "        ($sp to \"$weight\") to listOf($tokenList),"
        }.joinToString("\n")

        return """
            |package net.skyscanner.backpack.lint.check
            |
            |internal object GeneratedTypographyTokenMap {
            |    /**
            |     * Maps (fontSize in sp, fontWeight name) to typography style names.
            |     * When fontWeight is not specified, default to "Normal".
            |     */
            |    val TYPOGRAPHY_TOKEN_MAP: Map<Pair<Int, String>, List<String>> = mapOf(
            |$entries
            |    )
            |}
        """.trimMargin()
    }
}
