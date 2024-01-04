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

package net.skyscanner.backpack.compose.carousel

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import net.skyscanner.backpack.compose.pageindicator.BpkPageIndicator
import net.skyscanner.backpack.compose.pageindicator.BpkPageIndicatorStyle

@Composable
fun BpkCarousel(
    state: BpkCarouselState,
    modifier: Modifier = Modifier,
    content: @Composable (BoxScope.(Int) -> Unit),
) {
    BpkCarousel(
        state = state,
        modifier = modifier,
        content = content,
        overlayContent = { pageIndicator -> Box(Modifier.align(Alignment.BottomCenter)) { pageIndicator?.invoke() } },
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun BpkCarousel(
    state: BpkCarouselState,
    content: @Composable (BoxScope.(Int) -> Unit),
    overlayContent: @Composable (BoxScope.((@Composable () -> Unit)?) -> Unit),
    modifier: Modifier = Modifier,
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
