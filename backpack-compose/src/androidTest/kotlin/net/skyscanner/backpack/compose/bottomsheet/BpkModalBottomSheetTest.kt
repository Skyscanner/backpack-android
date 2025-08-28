/**
 * Backpack for Android - Skyscanner's Design System
 *
 * Copyright 2018 - 2025 Skyscanner Ltd
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

import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import net.skyscanner.backpack.compose.theme.BpkTheme
import org.junit.Rule
import org.junit.Test

class BpkModalBottomSheetTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val title = "Title"
    private val customTitleContentDesc = "Custom content desc"

    @Test
    fun givenTitleContentDescriptionNotNull_whenBpkModalBottomSheet_thenSemanticsSet() {
        composeTestRule.setContent {
            BpkTheme {
                BpkModalBottomSheet(
                    onDismissRequest = { },
                    title = title,
                    titleContentDescription = customTitleContentDesc,
                    content = {},
                )
            }
        }

        composeTestRule
            .onNode(SemanticsMatcher.expectValue(key = SemanticsProperties.Heading, expectedValue = Unit))
            .assertTextEquals(title)

        composeTestRule
            .onNodeWithContentDescription(customTitleContentDesc)
            .assertExists()
    }
}
