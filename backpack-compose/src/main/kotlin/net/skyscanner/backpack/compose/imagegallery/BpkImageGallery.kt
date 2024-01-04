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
import net.skyscanner.backpack.compose.imagegallery.internal.BpkImageGallerySlideshow
import net.skyscanner.backpack.compose.modal.BpkModal
import net.skyscanner.backpack.compose.modal.BpkModalState
import net.skyscanner.backpack.compose.modal.rememberBpkModalState
import net.skyscanner.backpack.compose.navigationbar.NavIcon

@Composable
fun BpkImageGallery(
    images: List<BpkImageGalleryImage>,
    currentImage: Int,
    closeContentDescription: String,
    onImageChanged: (Int) -> Unit,
    onCloseClicked: () -> Unit,
    onDismiss: (() -> Unit),
    modifier: Modifier = Modifier,
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
            currentImage = currentImage,
            onImageChanged = onImageChanged,
        )
    }
}

data class BpkImageGalleryImage(
    val title: String,
    val description: String? = null,
    val credit: String? = null,
    val content: @Composable (contentDescription: String) -> Unit,
)
