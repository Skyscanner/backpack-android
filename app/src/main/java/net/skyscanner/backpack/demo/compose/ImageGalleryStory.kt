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

package net.skyscanner.backpack.demo.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import net.skyscanner.backpack.compose.button.BpkButton
import net.skyscanner.backpack.compose.carousel.rememberBpkCarouselState
import net.skyscanner.backpack.compose.imagegallery.BpkImageGallery
import net.skyscanner.backpack.compose.imagegallery.BpkImageGalleryCarousel
import net.skyscanner.backpack.compose.imagegallery.BpkImageGalleryImage
import net.skyscanner.backpack.compose.modal.rememberBpkModalState
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.demo.components.ImageGalleryComponent
import net.skyscanner.backpack.demo.meta.ComposeStory

@Composable
@ImageGalleryComponent
@ComposeStory(name = "Carousel")
fun ImageGalleryCarouselStory(
    modifier: Modifier = Modifier,
) {
    val pagerState = rememberBpkCarouselState(
        totalImages = 4,
    )

    BpkImageGalleryCarousel(
        modifier = modifier.height(345.dp),
        state = pagerState,
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(
                id = when (it) {
                    0 -> R.drawable.carousel_placeholder_1
                    1 -> R.drawable.carousel_placeholder_2
                    2 -> R.drawable.carousel_placeholder_3
                    3 -> R.drawable.carousel_placeholder_4
                    else -> R.drawable.carousel_placeholder_1
                },
            ),
            contentDescription = "Image $it",
            contentScale = ContentScale.Crop,
        )
    }
}

@Composable
@ImageGalleryComponent
@ComposeStory(name = "Slideshow")
fun ImageGallerySlideshowStory(
    modifier: Modifier = Modifier,
    initialPage: Int = 0,
) {
    val showModal = rememberSaveable { mutableStateOf(true) }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        BpkButton(
            text = stringResource(R.string.generic_show),
            onClick = { showModal.value = true },
        )
    }

    if (showModal.value) {
        val modalState = rememberBpkModalState()
        val coroutineScope = rememberCoroutineScope()

        var currentImage by remember { mutableIntStateOf(initialPage) }
        BpkImageGallery(
            modifier = modifier,
            state = modalState,
            closeContentDescription = stringResource(R.string.navigation_close),
            currentImage = currentImage,
            onImageChanged = { currentImage = it },
            onCloseClicked = { coroutineScope.launch { modalState.hide() } },
            onDismiss = { showModal.value = false },
            images = listOf(
                BpkImageGalleryImage(
                    title = stringResource(R.string.image_gallery_title_1),
                    description = stringResource(R.string.image_gallery_description_1),
                    credit = stringResource(R.string.image_gallery_photographer_1),
                    content = { contentDescription ->
                        Image(
                            modifier = Modifier.fillMaxSize(),
                            painter = painterResource(R.drawable.carousel_placeholder_1),
                            contentDescription = contentDescription,
                        )
                    },
                ),
                BpkImageGalleryImage(
                    title = stringResource(R.string.image_gallery_title_x, 2),
                    content = { contentDescription ->
                        Image(
                            modifier = Modifier.fillMaxSize(),
                            painter = painterResource(R.drawable.carousel_placeholder_2),
                            contentDescription = contentDescription,
                        )
                    },
                ),
                BpkImageGalleryImage(
                    title = stringResource(R.string.image_gallery_title_x, 3),
                    content = { contentDescription ->
                        Image(
                            modifier = Modifier.fillMaxSize(),
                            painter = painterResource(R.drawable.carousel_placeholder_3),
                            contentDescription = contentDescription,
                        )
                    },
                ),
                BpkImageGalleryImage(
                    title = stringResource(R.string.image_gallery_title_x, 4),
                    content = { contentDescription ->
                        Image(
                            modifier = Modifier.fillMaxSize(),
                            painter = painterResource(R.drawable.carousel_placeholder_4),
                            contentDescription = contentDescription,
                        )
                    },
                ),
            ),
        )
    }
}
