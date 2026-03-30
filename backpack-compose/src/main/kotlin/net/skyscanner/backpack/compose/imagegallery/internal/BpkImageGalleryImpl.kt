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

package net.skyscanner.backpack.compose.imagegallery.internal

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import net.skyscanner.backpack.compose.imagegallery.BpkImageGalleryCategories
import net.skyscanner.backpack.compose.imagegallery.BpkImageGalleryChipCategory
import net.skyscanner.backpack.compose.imagegallery.BpkImageGalleryImage
import net.skyscanner.backpack.compose.imagegallery.BpkImageGalleryImageCategory
import net.skyscanner.backpack.compose.modal.BpkModal
import net.skyscanner.backpack.compose.modal.BpkModalState
import net.skyscanner.backpack.compose.modal.rememberBpkModalState
import net.skyscanner.backpack.compose.navigationbar.NavIcon

@Composable
internal fun BpkImageGallerySlideshowImpl(
    images: List<BpkImageGalleryImage>,
    closeContentDescription: String,
    onCloseClicked: () -> Unit,
    onDismiss: (() -> Unit),
    modifier: Modifier = Modifier,
    initialImage: Int = 0,
    onImageChanged: ((Int) -> Unit)? = null,
    state: BpkModalState = rememberBpkModalState(),
) {
    BpkModal(
        navIcon = NavIcon.Close(closeContentDescription, onCloseClicked),
        onDismiss = onDismiss,
        state = state,
        modifier = modifier,
    ) {
        BpkImageGallerySlideshow(
            images = images,
            initialImage = initialImage,
            onImageChanged = onImageChanged,
        )
    }
}

@Composable
internal fun BpkImageGalleryChipGridImpl(
    categories: List<BpkImageGalleryChipCategory>,
    closeContentDescription: String,
    onCloseClicked: () -> Unit,
    onDismiss: (() -> Unit),
    modifier: Modifier = Modifier,
    initialCategory: Int = 0,
    onCategoryChanged: ((BpkImageGalleryChipCategory) -> Unit)? = null,
    onImageClicked: ((BpkImageGalleryChipCategory, BpkImageGalleryImage) -> Unit)? = null,
    onImageChanged: ((BpkImageGalleryChipCategory, BpkImageGalleryImage) -> Unit)? = null,
    state: BpkModalState = rememberBpkModalState(),
) {
    BpkImageGalleryGridModal(
        categories = BpkImageGalleryCategories.Chip(categories),
        initialCategory = initialCategory,
        closeContentDescription = closeContentDescription,
        onCloseClicked = onCloseClicked,
        onDismiss = onDismiss,
        modifier = modifier,
        onCategoryChanged = { category -> onCategoryChanged?.invoke(category as BpkImageGalleryChipCategory) },
        onImageClicked = { category, image ->
            onImageClicked?.invoke(
                category as BpkImageGalleryChipCategory,
                image,
            )
        },
        onImageChanged = { category, image ->
            onImageChanged?.invoke(
                category as BpkImageGalleryChipCategory,
                image,
            )
        },
        state = state,
    )
}

@Composable
internal fun BpkImageGalleryImageGridImpl(
    categories: List<BpkImageGalleryImageCategory>,
    closeContentDescription: String,
    onCloseClicked: () -> Unit,
    onDismiss: (() -> Unit),
    modifier: Modifier = Modifier,
    initialCategory: Int = 0,
    onCategoryChanged: ((BpkImageGalleryImageCategory) -> Unit)? = null,
    onImageClicked: ((BpkImageGalleryImageCategory, BpkImageGalleryImage) -> Unit)? = null,
    onImageChanged: ((BpkImageGalleryImageCategory, BpkImageGalleryImage) -> Unit)? = null,
    state: BpkModalState = rememberBpkModalState(),
) {
    BpkImageGalleryGridModal(
        categories = BpkImageGalleryCategories.Image(categories),
        initialCategory = initialCategory,
        closeContentDescription = closeContentDescription,
        onCloseClicked = onCloseClicked,
        onDismiss = onDismiss,
        modifier = modifier,
        onCategoryChanged = { category -> onCategoryChanged?.invoke(category as BpkImageGalleryImageCategory) },
        onImageClicked = { category, image ->
            onImageClicked?.invoke(
                category as BpkImageGalleryImageCategory,
                image,
            )
        },
        onImageChanged = { category, image ->
            onImageChanged?.invoke(
                category as BpkImageGalleryImageCategory,
                image,
            )
        },
        state = state,
    )
}
