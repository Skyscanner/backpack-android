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
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import net.skyscanner.backpack.compose.carousel.BpkCarousel
import net.skyscanner.backpack.compose.carousel.rememberBpkCarouselState
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.demo.components.CarouselComponent
import net.skyscanner.backpack.demo.meta.ComposeStory

@Composable
@CarouselComponent
@ComposeStory
fun CarouselStory(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = BpkSpacing.Base, vertical = BpkSpacing.Base),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        CarouselSample(totalImages = 4)
    }
}

@Composable
private fun CarouselSample(
    totalImages: Int,
    modifier: Modifier = Modifier,
    initialImage: Int = 0,
    showCurrentPageLabel: Boolean = false,
) {
    val pagerState = rememberBpkCarouselState(totalImages = totalImages, initialImage = initialImage)

    if (showCurrentPageLabel) {
        BpkText(
            text = stringResource(R.string.carousel_current_image, pagerState.currentPage),
            style = BpkTheme.typography.caption,
        )
    }

    BpkCarousel(
        modifier = modifier
            .aspectRatio(1.9f)
            .padding(vertical = BpkSpacing.Base),
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
