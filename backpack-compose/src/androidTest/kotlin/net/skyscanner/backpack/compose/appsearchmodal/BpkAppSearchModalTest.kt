/**
 * Backpack for Android - Skyscanner's Design System
 *
 * Copyright 2018 Skyscanner Ltd
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

package net.skyscanner.backpack.compose.appsearchmodal

import androidx.compose.runtime.Composable
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.text.buildAnnotatedString
import junit.framework.Assert.assertFalse
import kotlinx.coroutines.test.runTest
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.textfield.BpkClearAction
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.City
import net.skyscanner.backpack.compose.utils.BehaviouralCallback
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class BpkAppSearchModalTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun withBehaviouralCallbackAndClickActionAdded() = runTest {
        var didReceiveBehaviouralOnDrawnCallback = false
        var didReceiveBehaviouralOnClickCallback = false
        var didReceiveRegularOnClickCallback = false

        val onClickAction: () -> Unit = {
            didReceiveRegularOnClickCallback = true
        }

        val testLabel = "TestLabel"

        composeTestRule.setContent {
            BpkTheme {
                BpkAppSearchModal(
                    title = "Title",
                    inputText = "Input",
                    inputHint = "Hint",
                    results = contentResult(label = testLabel, onClickAction = onClickAction),
                    closeAccessibilityLabel = "Close",
                    onClose = { },
                    onInputChanged = { },
                    clearAction = BpkClearAction("Clear") { },
                    behaviouralCallback = object : BehaviouralCallback {
                        override fun onDrawn(element: Any) {
                            didReceiveBehaviouralOnDrawnCallback = true
                        }

                        override fun onClick(element: Any) {
                            didReceiveBehaviouralOnClickCallback = true
                        }
                    },
                )
            }
        }

        // assert the correct state pre-click
        assertTrue(didReceiveBehaviouralOnDrawnCallback)
        assertFalse(didReceiveBehaviouralOnClickCallback)
        assertFalse(didReceiveRegularOnClickCallback)

        // perform a click action
        composeTestRule.onNodeWithText(testLabel).performClick()

        // assert the correct state post-click
        assertTrue(didReceiveBehaviouralOnClickCallback)
        assertTrue(didReceiveRegularOnClickCallback)
    }

    @Test
    fun withOnlyBehaviouralCallbackAdded() = runTest {
        var didReceiveBehaviouralOnDrawnCallback = false
        var didReceiveBehaviouralOnClickCallback = false

        val testLabel = "TestLabel"

        composeTestRule.setContent {
            BpkTheme {
                BpkAppSearchModal(
                    title = "Title",
                    inputText = "Input",
                    inputHint = "Hint",
                    results = contentResult(label = testLabel, onClickAction = { }),
                    closeAccessibilityLabel = "Close",
                    onClose = { },
                    onInputChanged = { },
                    clearAction = BpkClearAction("Clear") { },
                    behaviouralCallback = object : BehaviouralCallback {
                        override fun onDrawn(element: Any) {
                            didReceiveBehaviouralOnDrawnCallback = true
                        }

                        override fun onClick(element: Any) {
                            didReceiveBehaviouralOnClickCallback = true
                        }
                    },
                )
            }
        }

        // assert the correct state pre-click
        assertTrue(didReceiveBehaviouralOnDrawnCallback)
        assertFalse(didReceiveBehaviouralOnClickCallback)

        // perform a click action
        composeTestRule.onNodeWithText(testLabel).performClick()

        // assert the correct state post-click
        assertTrue(didReceiveBehaviouralOnClickCallback)
    }

    @Test
    fun withOnlyClickActionAdded() = runTest {
        var didReceiveRegularOnClickCallback = false

        val onClickAction: () -> Unit = {
            didReceiveRegularOnClickCallback = true
        }

        val testLabel = "TestLabel"

        composeTestRule.setContent {
            BpkTheme {
                BpkAppSearchModal(
                    title = "Title",
                    inputText = "Input",
                    inputHint = "Hint",
                    results = contentResult(label = testLabel, onClickAction = onClickAction),
                    closeAccessibilityLabel = "Close",
                    onClose = { },
                    onInputChanged = { },
                    clearAction = BpkClearAction("Clear") { },
                    /* no behavioural callback added- the default value is null */
                )
            }
        }

        // assert the correct state pre-click
        assertFalse(didReceiveRegularOnClickCallback)

        // perform a click action
        composeTestRule.onNodeWithText(testLabel).performClick()

        // assert the correct state post-click
        assertTrue(didReceiveRegularOnClickCallback)
    }

    @Composable
    internal fun contentResult(label: String, onClickAction: () -> Unit) = BpkAppSearchModalResult.Content(
        sections = listOf(
            BpkSection(
                headings = BpkSectionHeading(title = "Heading"),
                items = listOf(
                    BpkItem(
                        title = buildAnnotatedString { "Title" },
                        subtitle = buildAnnotatedString { "Subtitle" },
                        icon = BpkIcon.City,
                        onItemSelected = { onClickAction.invoke() },
                        tertiaryLabel = label,
                    ),
                ),
            ),
        ),
    )
}
