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

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class TokenSuggestionBuilderTest {

    private val testTokenMap = mapOf(
        4 to "BpkSpacing.Sm",
        8 to "BpkSpacing.Md",
        16 to "BpkSpacing.Base",
    )

    private val builder = TokenSuggestionBuilder(
        tokenMap = testTokenMap,
        tokenTypePrefix = "BpkSpacing",
    )

    @Test
    fun `given existing token value, when buildMessage called, then returns specific suggestion`() {
        val message = builder.buildMessage(16)

        assertEquals("Use BpkSpacing.Base instead of 16.dp", message)
    }

    @Test
    fun `given non-existing token value, when buildMessage called, then returns available tokens`() {
        val message = builder.buildMessage(20)

        assertTrue(message.contains("Use BpkSpacing.* tokens instead of 20.dp"))
        assertTrue(message.contains("4.dp = BpkSpacing.Sm"))
        assertTrue(message.contains("8.dp = BpkSpacing.Md"))
        assertTrue(message.contains("16.dp = BpkSpacing.Base"))
    }

    @Test
    fun `given existing token value, when buildLintFix called, then returns non-null fix`() {
        val fix = builder.buildLintFix(8)

        assertNotNull(fix)
    }

    @Test
    fun `given non-existing token value, when buildLintFix called, then returns null`() {
        val fix = builder.buildLintFix(20)

        assertNull(fix)
    }

    @Test
    fun `given single token in multi-token map, when buildMultiTokenMessage called, then returns specific suggestion`() {
        val multiTokenMap = mapOf(
            "0xFFFF0000" to listOf("BpkTheme.colors.corePrimary"),
        )

        val message = TokenSuggestionBuilder.buildMultiTokenMessage(
            value = "0xFFFF0000",
            tokenMap = multiTokenMap,
            tokenTypePrefix = "BpkTheme.colors",
        )

        assertEquals("Use BpkTheme.colors.corePrimary instead of 0xFFFF0000", message)
    }

    @Test
    fun `given multiple tokens in multi-token map, when buildMultiTokenMessage called, then returns multiple options`() {
        val multiTokenMap = mapOf(
            "0xFFFF0000" to listOf("BpkTheme.colors.corePrimary", "BpkTheme.colors.systemRed"),
        )

        val message = TokenSuggestionBuilder.buildMultiTokenMessage(
            value = "0xFFFF0000",
            tokenMap = multiTokenMap,
            tokenTypePrefix = "BpkTheme.colors",
        )

        assertTrue(message.contains("Use one of these tokens"))
        assertTrue(message.contains("BpkTheme.colors.corePrimary"))
        assertTrue(message.contains("BpkTheme.colors.systemRed"))
    }

    @Test
    fun `given non-existing value in multi-token map with custom explanation, when buildMultiTokenMessage called, then returns custom explanation`() {
        val multiTokenMap = mapOf<String, List<String>>()
        val customExplanation = "Custom explanation text"

        val message = TokenSuggestionBuilder.buildMultiTokenMessage(
            value = "0xFFFF0000",
            tokenMap = multiTokenMap,
            tokenTypePrefix = "BpkTheme.colors",
            noTokenExplanation = customExplanation,
        )

        assertEquals(customExplanation, message)
    }

    @Test
    fun `given single token, when buildMultiTokenLintFix called, then returns auto-fix`() {
        val multiTokenMap = mapOf(
            "0xFFFF0000" to listOf("BpkTheme.colors.corePrimary"),
        )

        val fix = TokenSuggestionBuilder.buildMultiTokenLintFix(
            value = "0xFFFF0000",
            tokenMap = multiTokenMap,
            originalText = "Color(0xFFFF0000)",
        )

        assertNotNull(fix)
    }

    @Test
    fun `given multiple tokens, when buildMultiTokenLintFix called, then returns alternatives fix`() {
        val multiTokenMap = mapOf(
            "0xFFFF0000" to listOf("BpkTheme.colors.corePrimary", "BpkTheme.colors.systemRed"),
        )

        val fix = TokenSuggestionBuilder.buildMultiTokenLintFix(
            value = "0xFFFF0000",
            tokenMap = multiTokenMap,
            originalText = "Color(0xFFFF0000)",
        )

        assertNotNull(fix)
    }

    @Test
    fun `given non-existing value, when buildMultiTokenLintFix called, then returns null`() {
        val multiTokenMap = mapOf<String, List<String>>()

        val fix = TokenSuggestionBuilder.buildMultiTokenLintFix(
            value = "0xFFFF0000",
            tokenMap = multiTokenMap,
            originalText = "Color(0xFFFF0000)",
        )

        assertNull(fix)
    }
}
