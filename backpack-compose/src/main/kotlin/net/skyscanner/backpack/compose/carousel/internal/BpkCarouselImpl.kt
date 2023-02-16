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
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.flow.distinctUntilChanged
import net.skyscanner.backpack.compose.pageindicator.BpkPageIndicator
import net.skyscanner.backpack.compose.pageindicator.BpkPageIndicatorStyle

@OptIn(ExperimentalPagerApi::class)
@Composable
internal fun BpkCarouselImpl(
  count: Int,
  modifier: Modifier = Modifier,
  pagerState: PagerState = rememberPagerState(),
  onImageChanged: ((Int) -> Unit)? = null,
  imageContent: @Composable (BoxScope.(Int) -> Unit),
) {
  Box(modifier = modifier) {
    LaunchedEffect(pagerState.currentPage) {
      snapshotFlow { pagerState.currentPage }.distinctUntilChanged().collect { index ->
        val page = getModNumber(index, count)
        onImageChanged?.invoke(page)
      }
    }

    HorizontalPager(
      modifier = Modifier
        .testTag("pager")
        .fillMaxSize(),
      count = Int.MAX_VALUE, // if count > 1, set to Int.MAX_VALUE for infinite looping
      state = pagerState,
    ) { index ->
      val page = getModNumber(index, count)
      imageContent(page)
    }

    // if there is more than one image, display the page indicator
    if (count > 1) {
      BpkPageIndicator(
        modifier = Modifier
          .align(Alignment.BottomCenter)
          .testTag("pageIndicator"),
        totalIndicators = count,
        currentIndex = getModNumber(pagerState.currentPage, count),
        style = BpkPageIndicatorStyle.OverImage,
      )
    }
  }
}

private fun getModNumber(index: Int, count: Int) = (index - (Int.MAX_VALUE / 2)).floorMod(count)

// floor modulo operation
private fun Int.floorMod(other: Int): Int = when (other) {
  0 -> this
  else -> this - floorDiv(other) * other
}
