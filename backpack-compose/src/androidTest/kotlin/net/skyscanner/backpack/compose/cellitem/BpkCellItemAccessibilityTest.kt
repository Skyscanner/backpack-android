/**
 * Backpack for Android - Skyscanner's Design System
 *
 * Copyright 2018 - 2026 Skyscanner Ltd
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.skyscanner.backpack.compose.cellitem

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import net.skyscanner.backpack.compose.theme.BpkTheme
import org.junit.Rule
import org.junit.Test

class BpkCellItemAccessibilityTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun givenImageContentDescription_whenBpkCellItemRendered_thenDescriptionIsExposed() {
        val imageContentDescription = "Partner logo"

        composeTestRule.setContent {
            BpkTheme {
                BpkCellItem(
                    title = "Partner",
                    slot = BpkCellItemSlot.Image(
                        imageDrawable = android.R.drawable.ic_menu_gallery,
                        contentDescription = imageContentDescription,
                    ),
                )
            }
        }

        composeTestRule
            .onNodeWithContentDescription(imageContentDescription)
            .assertExists()
    }
}
