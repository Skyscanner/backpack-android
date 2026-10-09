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

package net.skyscanner.backpack.compose.price

import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithContentDescription
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithContentDescription
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.InformationCircle
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

/**
 * Regression coverage for the `leadingText` + `onLeadingTextClicked` accessible target built via
 * [androidx.compose.ui.semantics.clearAndSetSemantics] in `BpkPriceLeadingText`. Previously the child
 * `BpkText`'s own semantics leaked into the tree alongside the parent's, so a screen reader would land
 * on the leadingText value as two separate, independently-focusable nodes (or announce the custom
 * contentDescription and the raw leadingText together). This is intentionally a plain Compose UI test
 * (no Roborazzi screenshot capture) since only the semantics tree is under test, not pixels.
 */
class BpkPriceAccessibilityTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun givenClickableLeadingTextStartAligned_thenExposedAsSingleAccessibleNode() {
        setUpPrice(align = BpkPriceAlign.Start)
        assertSingleAccessibleNode()
    }

    @Test
    fun givenClickableLeadingTextEndAligned_thenExposedAsSingleAccessibleNode() {
        setUpPrice(align = BpkPriceAlign.End)
        assertSingleAccessibleNode()
    }

    @Test
    fun givenClickableLeadingTextRowAligned_thenExposedAsSingleAccessibleNode() {
        setUpPrice(align = BpkPriceAlign.Row)
        assertSingleAccessibleNode()
    }

    @Test
    fun givenCustomContentDescription_thenRawLeadingTextDoesNotLeakAsSeparateNode() {
        setUpPrice(align = BpkPriceAlign.Start, contentDescription = CONTENT_DESCRIPTION)

        val descriptionMatches = composeTestRule.onAllNodesWithContentDescription(CONTENT_DESCRIPTION)
            .fetchSemanticsNodes()
        assertEquals(1, descriptionMatches.size)

        val rawTextMatches = composeTestRule.onAllNodesWithText(LEADING_TEXT).fetchSemanticsNodes()
        assertEquals(0, rawTextMatches.size)
    }

    private fun assertSingleAccessibleNode() {
        val descriptionMatches = composeTestRule.onAllNodesWithContentDescription(LEADING_TEXT)
            .fetchSemanticsNodes()
        assertEquals(1, descriptionMatches.size)

        val leakedTextMatches = composeTestRule.onAllNodesWithText(LEADING_TEXT).fetchSemanticsNodes()
        assertEquals(0, leakedTextMatches.size)

        composeTestRule.onNodeWithContentDescription(LEADING_TEXT)
            .assertIsDisplayed()
            .assert(hasClickAction())
    }

    private fun setUpPrice(align: BpkPriceAlign, contentDescription: String? = null) {
        composeTestRule.setContent {
            BpkTheme {
                BpkPrice(
                    price = PRICE,
                    leadingText = LEADING_TEXT,
                    leadingTextContentDescription = contentDescription,
                    leadingIcon = BpkIcon.InformationCircle,
                    trailingIcon = BpkIcon.InformationCircle,
                    align = align,
                    onLeadingTextClicked = {},
                )
            }
        }
        composeTestRule.waitForIdle()
    }

    private companion object {
        const val PRICE = "£1,830"
        const val LEADING_TEXT = "£10 cheaper"
        const val CONTENT_DESCRIPTION = "10 pounds cheaper than usual, tap to learn more"
    }
}
