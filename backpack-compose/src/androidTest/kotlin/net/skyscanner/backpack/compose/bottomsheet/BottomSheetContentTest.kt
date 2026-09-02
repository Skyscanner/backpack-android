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

private const val FIRST_CONTENT = "First content"
private const val SECOND_CONTENT = "Second content"

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
                        { BpkText(text = FIRST_CONTENT) }
                    } else {
                        { BpkText(text = SECOND_CONTENT) }
                    },
                    content = {},
                )
            }
        }

        composeTestRule.onNodeWithText(FIRST_CONTENT).assertExists()

        composeTestRule.runOnIdle { showFirstContent.value = false }

        composeTestRule.onNodeWithText(SECOND_CONTENT).assertExists()
        composeTestRule.onNodeWithText(FIRST_CONTENT).assertDoesNotExist()
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
                        { BpkText(text = FIRST_CONTENT) }
                    } else {
                        { BpkText(text = SECOND_CONTENT) }
                    },
                )
            }
        }

        composeTestRule.onNodeWithText(FIRST_CONTENT).assertExists()

        composeTestRule.runOnIdle { showFirstContent.value = false }

        composeTestRule.onNodeWithText(SECOND_CONTENT).assertExists()
        composeTestRule.onNodeWithText(FIRST_CONTENT).assertDoesNotExist()
    }
}
