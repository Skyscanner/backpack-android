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

package net.skyscanner.backpack.compose.bottomsheet

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.theme.BpkTheme
import org.junit.Rule
import org.junit.Test

class BottomSheetContentTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun bottomSheet_updatesWhenContentLambdaChanges() {
        val showFirstContent = mutableStateOf(true)

        composeTestRule.setContent {
            BpkTheme {
                BpkBottomSheet(
                    state = rememberBpkBottomSheetState(BpkBottomSheetValue.Expanded),
                    sheetContent = if (showFirstContent.value) {
                        { BpkText(text = "First content") }
                    } else {
                        { BpkText(text = "Second content") }
                    },
                    content = {},
                )
            }
        }

        composeTestRule.onNodeWithText("First content").assertExists()

        composeTestRule.runOnIdle { showFirstContent.value = false }

        composeTestRule.onNodeWithText("Second content").assertExists()
        composeTestRule.onNodeWithText("First content").assertDoesNotExist()
    }

    @Test
    fun modalBottomSheetWithHeader_updatesWhenContentLambdaChanges() {
        val showFirstContent = mutableStateOf(true)

        composeTestRule.setContent {
            BpkTheme {
                BpkModalBottomSheet(
                    onDismissRequest = {},
                    title = "Title",
                    content = if (showFirstContent.value) {
                        { BpkText(text = "First content") }
                    } else {
                        { BpkText(text = "Second content") }
                    },
                )
            }
        }

        composeTestRule.onNodeWithText("First content").assertExists()

        composeTestRule.runOnIdle { showFirstContent.value = false }

        composeTestRule.onNodeWithText("Second content").assertExists()
        composeTestRule.onNodeWithText("First content").assertDoesNotExist()
    }
}
