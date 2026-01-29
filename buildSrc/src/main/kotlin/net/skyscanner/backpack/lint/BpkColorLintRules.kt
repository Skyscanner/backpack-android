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
import net.skyscanner.backpack.tokens.BpkParser
import net.skyscanner.backpack.tokens.BpkTransformer

/**
 * Generates lint detector rule files from Backpack color tokens.
 * Focuses on light mode colors, mapping hex values to semantic BpkTheme.colors properties.
 */
object BpkColorLintRules {

    /**
     * Parser that extracts semantic colors from token source for lint rules.
     * Only includes non-deprecated semantic colors in light mode.
     */
    object Parser : BpkParser<Map<String, Any>, BpkColors> {

        @Suppress("UNCHECKED_CAST")
        override fun invoke(source: Map<String, Any>): BpkColors {
            val props = source["props"] as? Map<String, Map<String, String>> ?: return emptyBpkColors()

            val colorData = props.filter { (_, value) -> value["type"] == "color" }

            val map = colorData
                .filter { (key, value) ->
                    // Include semantic colors (have darkValue) that are not private or deprecated
                    val hasDarkValue = value["darkValue"] != null
                    val isPrivate = key.startsWith("PRIVATE_")
                    val isDeprecated = value["deprecated"].toBoolean()
                    val isMarcomms = key.startsWith("MARCOMMS_")
                    hasDarkValue && !isPrivate && !isDeprecated && !isMarcomms
                }
                .map { (key, value) ->
                    val tokenName = key.removePrefix("COLOR_").removeSuffix("_COLOR")
                    val hexValue = value["value"]?.uppercase()?.let { hex ->
                        // Convert #RRGGBBAA to 0xAARRGGBB format for Compose Color
                        if (hex.startsWith("#") && hex.length == 9) {
                            val rgb = hex.substring(1, 7)
                            val alpha = hex.substring(7, 9)
                            "0x$alpha$rgb"
                        } else if (hex.startsWith("#") && hex.length == 7) {
                            "0xFF${hex.substring(1)}"
                        } else {
                            hex
                        }
                    } ?: ""
                    tokenName to hexValue
                }
                .filter { it.second.isNotEmpty() }
                .toMap()

            return object : BpkColors, Map<String, String> by map {
                override fun toString(): String = map.toString()
            }
        }

        private fun emptyBpkColors(): BpkColors = object : BpkColors, Map<String, String> by emptyMap() {
            override fun toString(): String = "{}"
        }
    }

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

        val entries = hexToTokens.entries
            .sortedBy { it.key }
            .map { (hex, tokens) ->
                val tokenList = tokens.sorted().joinToString(", ") { "\"$it\"" }
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
 * Interface for color data (hex string mapped to token name)
 * Multiple tokens can share the same hex value
 */
interface BpkColors : Map<String, String>
