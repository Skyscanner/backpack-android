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

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import net.skyscanner.backpack.compose.carousel.BpkCarouselState
import net.skyscanner.backpack.compose.carousel.asInternalState
import net.skyscanner.backpack.compose.carousel.rememberBpkCarouselState
import net.skyscanner.backpack.compose.pageindicator.BpkPageIndicator
import net.skyscanner.backpack.compose.pageindicator.BpkPageIndicatorStyle
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.demo.components.PageIndicatorComponent
import net.skyscanner.backpack.demo.meta.ComposeStory

@Composable
@PageIndicatorComponent
@ComposeStory
fun PageIndicatorStory(modifier: Modifier = Modifier) {
    val pagerState = rememberBpkCarouselState(
        totalImages = 3,
        initialImage = 0,
    )

    val pagerState2 = rememberBpkCarouselState(
        totalImages = 6,
        initialImage = 0,
    )

    val pagerState3 = rememberBpkCarouselState(
        totalImages = 1,
        initialImage = 0,
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(
                horizontal = BpkSpacing.Base,
                vertical = BpkSpacing.Base,
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        BpkText(text = stringResource(id = R.string.page_indicator_less_than_5))
        PageIndicatorSample(
            state = pagerState,
            modifier = Modifier.aspectRatio(1.9f)
                .padding(vertical = BpkSpacing.Base),
            overlayContent = { pageIndicator -> Box(Modifier.align(Alignment.BottomCenter)) { pageIndicator?.invoke() } },
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

        BpkText(text = stringResource(id = R.string.page_indicator_more_than_5))
        PageIndicatorSample(
            state = pagerState2,
            modifier = Modifier.aspectRatio(1.9f)
                .padding(vertical = BpkSpacing.Base),
            overlayContent = { pageIndicator -> Box(Modifier.align(Alignment.BottomCenter)) { pageIndicator?.invoke() } },
        ) {
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(
                    id = when (it) {
                        0 -> R.drawable.carousel_placeholder_1
                        1 -> R.drawable.carousel_placeholder_2
                        2 -> R.drawable.carousel_placeholder_3
                        3 -> R.drawable.carousel_placeholder_4
                        4 -> R.drawable.london_saintpancrasstation
                        5 -> R.drawable.canadian_rockies_canada
                        else -> R.drawable.carousel_placeholder_1
                    },
                ),
                contentDescription = "Image $it",
                contentScale = ContentScale.Crop,
            )
        }

        BpkText(text = stringResource(id = R.string.page_indicator_only_1))
        PageIndicatorSample(
            state = pagerState3,
            modifier = Modifier.aspectRatio(1.9f)
                .padding(vertical = BpkSpacing.Base),
            overlayContent = { pageIndicator -> Box(Modifier.align(Alignment.BottomCenter)) { pageIndicator?.invoke() } },
        ) {
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(
                    id = when (it) {
                        0 -> R.drawable.carousel_placeholder_1
                        else -> R.drawable.carousel_placeholder_1
                    },
                ),
                contentDescription = "Image $it",
                contentScale = ContentScale.Crop,
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun PageIndicatorSample(
    state: BpkCarouselState,
    overlayContent: @Composable (BoxScope.((@Composable () -> Unit)?) -> Unit),
    modifier: Modifier = Modifier,
    content: @Composable (BoxScope.(Int) -> Unit),
) {
    val internalState = state.asInternalState()
    Box(modifier = modifier) {
        HorizontalPager(
            modifier = Modifier
                .testTag("pager")
                .fillMaxSize(),
            state = internalState.delegate,
        ) {
            content(internalState.getModdedPageNumber(it, internalState.pageCount))
        }

        // if there is more than one image, display the page indicator
        overlayContent {
            if (internalState.pageCount > 1) {
                BpkPageIndicator(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .testTag("pageIndicator"),
                    totalIndicators = internalState.pageCount,
                    currentIndex = internalState.currentPage,
                    style = BpkPageIndicatorStyle.OverImage,
                )
            }
        }
    }
}
