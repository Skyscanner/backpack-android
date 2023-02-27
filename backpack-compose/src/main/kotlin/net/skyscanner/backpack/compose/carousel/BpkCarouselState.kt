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

import androidx.annotation.FloatRange
import androidx.annotation.IntRange
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import net.skyscanner.backpack.compose.carousel.internal.BpkCarouselStateImpl

sealed interface BpkCarouselState : ScrollableState {

  val interactionSource: InteractionSource

  @get:IntRange(from = 0)
  val pageCount: Int

  @get:IntRange(from = 0)
  val currentPage: Int

  val currentPageOffset: Float

  suspend fun animateScrollToPage(
    @IntRange(from = 0) page: Int,
    @FloatRange(from = -1.0, to = 1.0) pageOffset: Float = 0f,
  )

  suspend fun scrollToPage(
    @IntRange(from = 0) page: Int,
    @FloatRange(from = -1.0, to = 1.0) pageOffset: Float = 0f,
  )
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun rememberBpkCarouselState(
  totalImages: Int,
  currentImage: Int = 0,
): BpkCarouselState {
  val initialPage = (Int.MAX_VALUE / 2) + currentImage
  val pagerState = rememberPagerState(initialPage = initialPage)
  return remember(pagerState, totalImages) {
    BpkCarouselStateImpl(delegate = pagerState, totalImages = totalImages)
  }
}

@OptIn(ExperimentalPagerApi::class)
fun BpkCarouselState(
  totalImages: Int,
  currentImage: Int = 0,
): BpkCarouselState {
  val initialPage = (Int.MAX_VALUE / 2) + currentImage
  return BpkCarouselStateImpl(delegate = PagerState(currentPage = initialPage), totalImages = totalImages)
}

@OptIn(ExperimentalPagerApi::class)
internal interface BpkCarouselInternalState : BpkCarouselState {
  val delegate: PagerState
  val totalImages: Int
}

internal fun BpkCarouselState.asInternalState(): BpkCarouselInternalState =
  when (this) {
    is BpkCarouselInternalState -> this
  }
