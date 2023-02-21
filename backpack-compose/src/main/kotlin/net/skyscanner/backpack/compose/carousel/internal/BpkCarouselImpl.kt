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

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import kotlinx.coroutines.flow.distinctUntilChanged
import net.skyscanner.backpack.compose.carousel.BpkCarouselInternalState
import net.skyscanner.backpack.compose.pageindicator.BpkPageIndicator
import net.skyscanner.backpack.compose.pageindicator.BpkPageIndicatorStyle

@OptIn(ExperimentalPagerApi::class)
@Composable
internal fun BpkCarouselImpl(
  state: BpkCarouselInternalState,
  modifier: Modifier = Modifier,
  onImageChanged: ((Int) -> Unit)? = null,
  imageContent: @Composable (BoxScope.(Int) -> Unit),
) {
  Box(modifier = modifier) {
    LaunchedEffect(state.currentPage) {
      snapshotFlow { state.currentPage }.distinctUntilChanged().collect { index ->
        onImageChanged?.invoke(index)
      }
    }

    HorizontalPager(
      modifier = Modifier
        .testTag("pager")
        .fillMaxSize(),
      count = if (state.pageCount > 1) Int.MAX_VALUE else 1, // if count > 1, set to Int.MAX_VALUE for infinite looping
      state = state.delegate,
    ) {
      imageContent(getModdedPageNumber(it, state.pageCount))
    }

    // if there is more than one image, display the page indicator
    if (state.pageCount > 1) {
      BpkPageIndicator(
        modifier = Modifier
          .align(Alignment.BottomCenter)
          .testTag("pageIndicator"),
        totalIndicators = state.pageCount,
        currentIndex = state.currentPage,
        style = BpkPageIndicatorStyle.OverImage,
      )
    }
  }
}

internal fun getModdedPageNumber(index: Int, count: Int) = (index - (Int.MAX_VALUE / 2)).floorMod(count)

// floor modulo operation
private fun Int.floorMod(other: Int): Int = when (other) {
  0 -> this
  else -> this - floorDiv(other) * other
}
