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

package net.skyscanner.backpack.lint.util

import com.android.tools.lint.detector.api.LintFix

/**
 * Builds suggestion messages and lint fixes for token replacement.
 *
 * @param K The type of the key (typically Int for dp values)
 * @param V The type of the value (typically String for token names, or List<String> for multiple tokens)
 */
internal class TokenSuggestionBuilder<K : Comparable<K>, V>(
    private val tokenMap: Map<K, V>,
    private val tokenTypePrefix: String,
    private val valueSuffix: String = ".dp",
) {

    /**
     * Builds a suggestion message for a single-value token map.
     *
     * @param value The value to look up
     * @return A suggestion message indicating the appropriate token to use
     */
    fun buildMessage(value: K): String {
        val token = tokenMap[value]
        return if (token != null) {
            "Use $token instead of $value$valueSuffix"
        } else {
            val available = tokenMap.entries
                .sortedBy { it.key }
                .joinToString("\n") { "- ${it.key}$valueSuffix = ${it.value}" }
            "Use $tokenTypePrefix.* tokens instead of $value$valueSuffix. Available tokens:\n$available"
        }
    }

    /**
     * Builds a lint fix for replacing a hardcoded value with a token.
     *
     * @param value The hardcoded value to replace
     * @return A [LintFix] if a matching token exists, null otherwise
     */
    fun buildLintFix(value: K): LintFix? {
        val token = tokenMap[value] ?: return null
        return LintFix.create()
            .replace()
            .text("$value$valueSuffix")
            .with(token.toString())
            .autoFix()
            .build()
    }

    companion object {

        /**
         * Builds a suggestion message for a token map that can have multiple tokens per value.
         *
         * @param value The value to look up
         * @param tokenMap Map of values to list of tokens
         * @param tokenTypePrefix The prefix for the token type (e.g., "BpkTheme.colors")
         * @param valueSuffix The suffix for the value (e.g., ".dp")
         * @param noTokenExplanation Optional explanation when no token is found
         * @return A suggestion message indicating the appropriate token(s) to use
         */
        fun <K : Comparable<K>> buildMultiTokenMessage(
            value: K,
            tokenMap: Map<K, List<String>>,
            tokenTypePrefix: String,
            valueSuffix: String = "",
            noTokenExplanation: String? = null,
        ): String {
            val tokens = tokenMap[value]
            return if (tokens != null) {
                if (tokens.size == 1) {
                    "Use ${tokens[0]} instead of $value$valueSuffix"
                } else {
                    val tokenList = tokens.joinToString("\n") { "- $it" }
                    "Use one of these tokens instead of $value$valueSuffix:\n$tokenList"
                }
            } else {
                noTokenExplanation ?: run {
                    val available = tokenMap.entries
                        .sortedBy { it.key }
                        .joinToString("\n") { (key, styles) ->
                            "- $key$valueSuffix = ${styles.joinToString(", ")}"
                        }
                    "Use $tokenTypePrefix.* instead of $value$valueSuffix. Available:\n$available"
                }
            }
        }

        /**
         * Builds a lint fix for a token map that can have multiple tokens per value.
         *
         * @param value The value to look up
         * @param tokenMap Map of values to list of tokens
         * @param originalText The original text to replace
         * @return A [LintFix] if matching token(s) exist, null otherwise
         */
        fun <K> buildMultiTokenLintFix(
            value: K,
            tokenMap: Map<K, List<String>>,
            originalText: String,
        ): LintFix? {
            val tokens = tokenMap[value] ?: return null
            return if (tokens.size == 1) {
                LintFix.create()
                    .replace()
                    .text(originalText)
                    .with(tokens[0])
                    .autoFix()
                    .build()
            } else {
                val alternatives = tokens.map { tokenName ->
                    LintFix.create()
                        .replace()
                        .text(originalText)
                        .with(tokenName)
                        .build()
                }
                LintFix.create()
                    .alternatives(*alternatives.toTypedArray())
            }
        }
    }
}
