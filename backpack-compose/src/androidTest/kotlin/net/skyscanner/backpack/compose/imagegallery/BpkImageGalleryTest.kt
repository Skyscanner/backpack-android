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

package net.skyscanner.backpack.compose.imagegallery

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotSelected
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToIndex
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.theme.BpkTheme
import org.junit.Rule
import org.junit.Test

class BpkImageGalleryTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun slideshow_changes_page() {
        composeTestRule.setContent {
            BpkTheme {
                BpkImageGallerySlideshow(
                    images = listOf(
                        galleryImageItem(0),
                        galleryImageItem(1),
                        galleryImageItem(2),
                    ),
                    closeContentDescription = "Close",
                    onCloseClicked = {},
                    onDismiss = {},
                    onImageChanged = {},
                )
            }
        }

        composeTestRule.onNodeWithText("Title 0").assertIsDisplayed()
        composeTestRule.onNodeWithText("Item 0").assertIsDisplayed()

        composeTestRule.onNodeWithTag("pager").performScrollToIndex(2)

        composeTestRule.onNodeWithText("Title 2").assertIsDisplayed()
        composeTestRule.onNodeWithText("Item 2").assertIsDisplayed()
    }

    @Test
    fun image_grid_changes_category() {
        composeTestRule.setContent {
            BpkTheme {
                BpkImageGalleryImageGrid(
                    categories = listOf(
                        categoryImageItem(0),
                        categoryImageItem(1),
                        categoryImageItem(2),
                    ),
                    closeContentDescription = "Close",
                    onCloseClicked = {},
                    onDismiss = {},
                    onCategoryChanged = {},
                )
            }
        }

        composeTestRule.onNodeWithText("Category 0").assertIsSelected()
        composeTestRule.onNodeWithText("Category 2").assertIsNotSelected()
        composeTestRule.onNodeWithText("Category 0 Item 0").assertIsDisplayed()

        composeTestRule.onNodeWithText("Category 2").performClick()

        composeTestRule.onNodeWithText("Category 0").assertIsNotSelected()
        composeTestRule.onNodeWithText("Category 2").assertIsSelected()
        composeTestRule.onNodeWithText("Category 2 Item 0").assertIsDisplayed()
    }

    @Test
    fun chip_grid_changes_category() {
        composeTestRule.setContent {
            BpkTheme {
                BpkImageGalleryChipGrid(
                    categories = listOf(
                        categoryChipItem(0),
                        categoryChipItem(1),
                        categoryChipItem(2),
                    ),
                    closeContentDescription = "Close",
                    onCloseClicked = {},
                    onDismiss = {},
                    onCategoryChanged = {},
                )
            }
        }

        composeTestRule.onNodeWithText("Category 0").assertIsSelected()
        composeTestRule.onNodeWithText("Category 2").assertIsNotSelected()
        composeTestRule.onNodeWithText("Category 0 Item 0").assertIsDisplayed()

        composeTestRule.onNodeWithText("Category 2").performClick()

        composeTestRule.onNodeWithText("Category 0").assertIsNotSelected()
        composeTestRule.onNodeWithText("Category 2").assertIsSelected()
        composeTestRule.onNodeWithText("Category 2 Item 0").assertIsDisplayed()
    }

    @Test
    fun image_grid_moves_to_slideshow() {
        composeTestRule.setContent {
            BpkTheme {
                BpkImageGalleryImageGrid(
                    categories = listOf(
                        categoryImageItem(0),
                        categoryImageItem(1),
                        categoryImageItem(2),
                    ),
                    closeContentDescription = "Close",
                    onCloseClicked = {},
                    onDismiss = {},
                    onCategoryChanged = {},
                )
            }
        }

        composeTestRule.onNodeWithText("Category 0 Item 0").performClick()

        composeTestRule.onNodeWithText("Category 0").assertDoesNotExist()
        composeTestRule.onNodeWithText("Category 1").assertDoesNotExist()
        composeTestRule.onNodeWithText("Category 2").assertDoesNotExist()

        composeTestRule.onNodeWithText("Category 0 Title 0").assertIsDisplayed()
    }

    @Test
    fun chip_grid_moves_to_slideshow() {
        composeTestRule.setContent {
            BpkTheme {
                BpkImageGalleryChipGrid(
                    categories = listOf(
                        categoryChipItem(0),
                        categoryChipItem(1),
                        categoryChipItem(2),
                    ),
                    closeContentDescription = "Close",
                    onCloseClicked = {},
                    onDismiss = {},
                    onCategoryChanged = {},
                )
            }
        }

        composeTestRule.onNodeWithText("Category 0 Item 0").performClick()

        composeTestRule.onNodeWithText("Category 0").assertDoesNotExist()
        composeTestRule.onNodeWithText("Category 1").assertDoesNotExist()
        composeTestRule.onNodeWithText("Category 2").assertDoesNotExist()

        composeTestRule.onNodeWithText("Category 0 Title 0").assertIsDisplayed()
    }

    private fun categoryChipItem(index: Int): BpkImageGalleryChipCategory {
        val title = "Category $index"
        return BpkImageGalleryChipCategory(
            title = title,
            images = listOf(
                galleryImageItem(0, title),
                galleryImageItem(1, title),
                galleryImageItem(2, title),
            ),
        )
    }

    private fun categoryImageItem(index: Int): BpkImageGalleryImageCategory {
        val title = "Category $index"
        return BpkImageGalleryImageCategory(
            title = title,
            images = listOf(
                galleryImageItem(0, title),
                galleryImageItem(1, title),
                galleryImageItem(2, title),
            ),
            content = {
                BpkText(
                    text = title,
                )
            },
        )
    }

    private fun galleryImageItem(index: Int, prefix: String = "") =
        BpkImageGalleryImage(
            title = "$prefix Title $index".trim(),
            content = { _, _ ->
                BpkText(
                    text = "$prefix Item $index".trim(),
                )
            },
        )
}
