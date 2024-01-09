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

package net.skyscanner.backpack.compose.imagegallery.internal

import androidx.compose.animation.Crossfade
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import net.skyscanner.backpack.compose.imagegallery.BpkImageGalleryCategories
import net.skyscanner.backpack.compose.imagegallery.BpkImageGalleryImage
import net.skyscanner.backpack.compose.modal.BpkModal
import net.skyscanner.backpack.compose.modal.BpkModalState
import net.skyscanner.backpack.compose.modal.rememberBpkModalState
import net.skyscanner.backpack.compose.navigationbar.NavIcon

@Composable
internal fun BpkImageGalleryGridModal(
    categories: BpkImageGalleryCategories,
    initialCategory: Int,
    closeContentDescription: String,
    onCloseClicked: () -> Unit,
    onDismiss: (() -> Unit),
    modifier: Modifier = Modifier,
    onCategoryChanged: ((BpkImageGalleryCategories.Category) -> Unit)? = null,
    onImageClicked: ((BpkImageGalleryCategories.Category, BpkImageGalleryImage) -> Unit)? = null,
    onImageChanged: ((BpkImageGalleryCategories.Category, BpkImageGalleryImage) -> Unit)? = null,
    state: BpkModalState = rememberBpkModalState(),
) {
    var currentCategory by remember { mutableIntStateOf(initialCategory) }
    var gridState by remember { mutableStateOf<GridModalState>(GridModalState.Grid) }
    BpkModal(
        navIcon = NavIcon.Close(closeContentDescription) {
            if (gridState is GridModalState.Slideshow) {
                gridState = GridModalState.Grid
            } else {
                onCloseClicked()
            }
        },
        onDismiss = onDismiss,
        state = state,
        modifier = modifier,
    ) {
        Crossfade(targetState = gridState, label = "Grid/Slideshow crossfade") { state ->
            when (state) {
                is GridModalState.Grid -> {
                    BpkImageGalleryGrid(
                        categories = categories,
                        currentCategory = currentCategory,
                        onCategoryChanged = {
                            currentCategory = categories.categories.indexOf(it)
                            onCategoryChanged?.invoke(it)
                        },
                        onImageClicked = { category, image ->
                            onImageClicked?.invoke(category, image)
                            gridState =
                                GridModalState.Slideshow(categories.categories[currentCategory].images.indexOf(image))
                        },
                    )
                }

                is GridModalState.Slideshow -> {
                    var currentImage by remember { mutableIntStateOf(state.imageToShow) }
                    BpkImageGallerySlideshow(
                        images = categories.categories[currentCategory].images,
                        initialImage = currentImage,
                        onImageChanged = {
                            currentImage = it
                            onImageChanged?.invoke(
                                categories.categories[currentCategory],
                                categories.categories[currentCategory].images[it],
                            )
                        },
                    )
                }
            }
        }
    }
}

private sealed class GridModalState {
    data object Grid : GridModalState()
    data class Slideshow(val imageToShow: Int) : GridModalState()
}
