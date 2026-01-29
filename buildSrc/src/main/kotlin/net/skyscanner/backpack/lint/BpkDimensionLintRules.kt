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
import net.skyscanner.backpack.tokens.BpkDimensions
import net.skyscanner.backpack.tokens.BpkTransformer

/**
 * Generates lint detector rule files from Backpack dimension tokens.
 * Handles spacing (padding/margins) and border radius (corner rounding).
 */
object BpkDimensionLintRules {

    sealed class Format<Output> : BpkTransformer<BpkDimensions, Output> {

        /**
         * Generates lint detector map for spacing tokens (padding, margins, etc.)
         */
        data class SpacingLintDetectorMap(val namespace: String) : Format<String>() {
            override fun invoke(source: BpkDimensions): String =
                toSpacingLintDetectorMap(source, namespace)
        }

        /**
         * Generates lint detector map for border radius tokens (corner rounding)
         */
        data class BorderRadiusLintDetectorMap(val namespace: String) : Format<String>() {
            override fun invoke(source: BpkDimensions): String =
                toBorderRadiusLintDetectorMap(source, namespace)
        }
    }

    private fun toSpacingLintDetectorMap(source: BpkDimensions, namespace: String): String {
        val entries = source.entries
            .sortedBy { it.value }
            .map { (name, value) ->
                val tokenName = CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, name)
                "        $value to \"$namespace.$tokenName\","
            }.joinToString("\n")

        return """
            |package net.skyscanner.backpack.lint.check
            |
            |internal object GeneratedSpacingTokenMap {
            |    val SPACING_TOKEN_MAP = mapOf(
            |$entries
            |    )
            |}
        """.trimMargin()
    }

    private fun toBorderRadiusLintDetectorMap(source: BpkDimensions, namespace: String): String {
        val entries = source.entries
            .sortedBy { it.value }
            .map { (name, value) ->
                val tokenName = CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, name)
                "        $value to \"$namespace.$tokenName\","
            }.joinToString("\n")

        return """
            |package net.skyscanner.backpack.lint.check
            |
            |internal object GeneratedBorderRadiusTokenMap {
            |    val BORDER_RADIUS_TOKEN_MAP = mapOf(
            |$entries
            |    )
            |}
        """.trimMargin()
    }
}
