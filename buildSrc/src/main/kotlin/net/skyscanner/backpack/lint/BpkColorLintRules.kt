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
import net.skyscanner.backpack.tokens.BpkTransformer

/**
 * Generates lint detector rule files from Backpack color tokens.
 * Focuses on light mode colors, mapping hex values to semantic BpkTheme.colors properties.
 */
object BpkColorLintRules {

    sealed class Format<Output> : BpkTransformer<BpkColors, Output> {

        /**
         * Generates lint detector map for color tokens (light mode)
         */
        data class ColorLintDetectorMap(val namespace: String) : Format<String>() {
            override fun invoke(source: BpkColors): String =
                toColorLintDetectorMap(source, namespace)
        }
    }

    private fun toColorLintDetectorMap(source: BpkColors, namespace: String): String {
        // Group by hex value to find tokens with same color
        val hexToTokens = source.entries
            .groupBy({ it.value }, { it.key })
            .mapValues { (_, names) ->
                names.map { name ->
                    val tokenName = CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, name)
                    "$namespace.$tokenName"
                }
            }

        val entries = hexToTokens.map { (hex, tokens) ->
            val tokenList = tokens.joinToString(", ") { "\"$it\"" }
            "        \"$hex\" to listOf($tokenList),"
        }.joinToString("\n")

        return """
            |package net.skyscanner.backpack.lint.check
            |
            |internal object GeneratedColorTokenMap {
            |    val COLOR_TOKEN_MAP = mapOf(
            |$entries
            |    )
            |}
        """.trimMargin()
    }
}

/**
 * Interface for color data (hex string mapped to list of token names)
 * Multiple tokens can share the same hex value
 */
interface BpkColors : Map<String, String>
