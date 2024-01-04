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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import net.skyscanner.backpack.compose.carousel.rememberBpkCarouselState
import net.skyscanner.backpack.compose.imagegallery.BpkImageGalleryCarousel
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
