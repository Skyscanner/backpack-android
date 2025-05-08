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

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import net.skyscanner.backpack.compose.imagegallery.internal.BpkImageGalleryGridModal
import net.skyscanner.backpack.compose.imagegallery.internal.BpkImageGallerySlideshow
import net.skyscanner.backpack.compose.modal.BpkModal
import net.skyscanner.backpack.compose.modal.BpkModalState
import net.skyscanner.backpack.compose.modal.rememberBpkModalState
import net.skyscanner.backpack.compose.navigationbar.NavIcon

@Composable
fun BpkImageGallerySlideshow(
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
fun BpkImageGalleryChipGrid(
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
fun BpkImageGalleryImageGrid(
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

data class BpkImageGalleryImage(
    val title: String,
    val description: String? = null,
    val credit: String? = null,
    val content: @Composable (contentDescription: String, contentScale: ContentScale) -> Unit,
) {
    internal fun contentDescription() = "$title. ${description ?: ""}. ${credit ?: ""}"
}

data class BpkImageGalleryChipCategory(
    override val title: String,
    override val images: List<BpkImageGalleryImage>,
) : BpkImageGalleryCategories.Category

data class BpkImageGalleryImageCategory(
    override val title: String,
    override val images: List<BpkImageGalleryImage>,
    val content: @Composable () -> Unit,
) : BpkImageGalleryCategories.Category

internal sealed interface BpkImageGalleryCategories {
    val categories: List<Category>

    data class Chip(
        override val categories: List<BpkImageGalleryChipCategory>,
    ) : BpkImageGalleryCategories

    data class Image(
        override val categories: List<BpkImageGalleryImageCategory>,
    ) : BpkImageGalleryCategories

    sealed interface Category {
        val title: String
        val images: List<BpkImageGalleryImage>
    }
}
