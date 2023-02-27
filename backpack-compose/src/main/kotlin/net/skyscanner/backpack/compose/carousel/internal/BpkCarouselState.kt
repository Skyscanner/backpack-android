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

package net.skyscanner.backpack.compose.carousel.internal

import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.interaction.InteractionSource
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import net.skyscanner.backpack.compose.carousel.BpkCarouselInternalState

@OptIn(ExperimentalPagerApi::class)
class BpkCarouselStateImpl constructor(
  override val delegate: PagerState,
  override val totalImages: Int,
) : BpkCarouselInternalState, ScrollableState by delegate {

  override val interactionSource: InteractionSource
    get() = delegate.interactionSource

  override val pageCount: Int
    get() = totalImages

  override val currentPage: Int
    get() = getModdedPageNumber(delegate.currentPage, totalImages)

  override val currentPageOffset: Float
    get() = delegate.currentPageOffset

  override suspend fun animateScrollToPage(page: Int, pageOffset: Float) =
    delegate.animateScrollToPage(getTargetPage(page), pageOffset)

  override suspend fun scrollToPage(page: Int, pageOffset: Float) =
    delegate.scrollToPage(getTargetPage(page), pageOffset)

  private fun getTargetPage(page: Int): Int {
    return delegate.currentPage - (currentPage - page)
  }

  private fun getModdedPageNumber(index: Int, count: Int) = (index - (Int.MAX_VALUE / 2)).floorMod(count)
}

// floor modulo operation
private fun Int.floorMod(other: Int): Int = when (other) {
  0 -> this
  else -> this - floorDiv(other) * other
}
