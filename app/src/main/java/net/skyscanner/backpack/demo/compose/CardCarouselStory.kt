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

import android.content.res.Configuration
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import net.skyscanner.backpack.compose.cardcarousel.BpkCardCarousel
import net.skyscanner.backpack.compose.cardcarousel.BpkCardCarouselItem
import net.skyscanner.backpack.compose.carousel.rememberBpkCarouselState
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.demo.components.CardCarouselComponent
import net.skyscanner.backpack.demo.meta.ComposeStory

@Composable
@CardCarouselComponent
@ComposeStory
fun CardCarouselStory(
    modifier: Modifier = Modifier,
) {
    val configuration = LocalConfiguration.current
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(vertical = BpkSpacing.Base),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(BpkSpacing.Base),
    ) {
        when (configuration.orientation) {
            Configuration.ORIENTATION_LANDSCAPE -> {
                CardCarouselMultiCardSample(imageAspectRatio = 3.5f)
            }
            else -> {
                CardCarouselMultiCardSample(
                    imageAspectRatio = 0.85f,
                    modifier = Modifier.aspectRatio(ratio = 0.75f),
                )
            }
        }
    }
}

@Composable
internal fun CardCarouselMultiCardSample(
    imageAspectRatio: Float,
    modifier: Modifier = Modifier,
    initialImage: Int = 0,
) {
    val cards = getCards(imageAspectRatio)
    val carouselState = rememberBpkCarouselState(totalImages = cards.size, initialImage = initialImage)
    BpkCardCarousel(
        state = carouselState,
        modifier = modifier,
        cards = cards,
    )
}

@Composable
internal fun CardCarouselSingleCardSample(imageAspectRatio: Float, modifier: Modifier = Modifier) {
    val cards = getCards(imageAspectRatio)
    val carouselState = rememberBpkCarouselState(1)
    BpkCardCarousel(
        state = carouselState,
        modifier = modifier,
        cards = cards.subList(0, 1),
    )
}

private fun getCards(imageAspectRatio: Float) = listOf(
    BpkCardCarouselItem(
        title = "Card title",
        description = "Cupidatat elit elit cupidatat quis consequat sunt anim do ullamco",
        contentDescription = "imageAccessibilityLabel",
        imageContent = { contentDescription ->
            Image(
                modifier = Modifier.aspectRatio(imageAspectRatio),
                contentScale = ContentScale.Crop,
                painter = painterResource(id = R.drawable.carousel_placeholder_1),
                contentDescription = contentDescription,
            )
        },
    ),
    BpkCardCarouselItem(
        title = "A long card title that should wrap",
        description = "Enim fugiat sunt quis culpa nostrud officia mollit.",
        contentDescription = "imageAccessibilityLabel",
        imageContent = { contentDescription ->
            Image(
                modifier = Modifier.aspectRatio(imageAspectRatio),
                contentScale = ContentScale.Crop,
                painter = painterResource(id = R.drawable.carousel_placeholder_2),
                contentDescription = contentDescription,
            )
        },
    ),
    BpkCardCarouselItem(
        title = "Another card title",
        description = "Voluptate anim occaecat cillum veniam sunt irure minim.",
        contentDescription = "imageAccessibilityLabel",
        imageContent = { contentDescription ->
            Image(
                modifier = Modifier.aspectRatio(imageAspectRatio),
                contentScale = ContentScale.Crop,
                painter = painterResource(id = R.drawable.carousel_placeholder_3),
                contentDescription = contentDescription,
            )
        },
    ),
)
