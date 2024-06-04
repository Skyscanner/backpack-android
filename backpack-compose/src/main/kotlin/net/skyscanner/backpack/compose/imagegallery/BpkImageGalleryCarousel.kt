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

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.invisibleToUser
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import net.skyscanner.backpack.compose.badge.BpkBadge
import net.skyscanner.backpack.compose.badge.BpkBadgeType
import net.skyscanner.backpack.compose.carousel.BpkCarousel
import net.skyscanner.backpack.compose.carousel.BpkCarouselState
import net.skyscanner.backpack.compose.tokens.BpkSpacing

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun BpkImageGalleryCarousel(
    state: BpkCarouselState,
    modifier: Modifier = Modifier,
    onImageClicked: ((Int) -> Unit)? = null,
    content: @Composable (BoxScope.(Int) -> Unit),
) {
    val interactionSource = remember { MutableInteractionSource() }

    BpkCarousel(
        modifier = modifier.clickable(interactionSource = interactionSource, indication = null) {
            onImageClicked?.invoke(
                state.currentPage,
            )
        },
        state = state,
        content = content,
        overlayContent = { pageIndicator ->
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .padding(
                        horizontal = BpkSpacing.Base,
                        vertical = ImageGalleryPreviewVerticalSpacing,
                    ),
            ) {
                pageIndicator?.invoke()
                BpkBadge(
                    modifier = Modifier
                        .align(Alignment.BottomEnd).semantics { this.invisibleToUser() },
                    text = "${state.currentPage + 1}/${state.pageCount}",
                    type = BpkBadgeType.Inverse,

                )
            }
        },
    )
}

private val ImageGalleryPreviewVerticalSpacing = 48.dp
