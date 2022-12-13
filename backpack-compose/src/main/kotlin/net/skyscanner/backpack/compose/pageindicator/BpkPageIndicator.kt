/*
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

package net.skyscanner.backpack.compose.pageindicator

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import net.skyscanner.backpack.compose.pageindicator.internal.BpkPageIndicatorImpl

enum class BpkPageIndicatorStyle {
  Default,
  OverImage
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun BpkPageIndicator(
  state: BpkPageIndicatorState,
  totalIndicators: Int,
  indicatorLabel: String,
  modifier: Modifier = Modifier,
  style: BpkPageIndicatorStyle = BpkPageIndicatorStyle.Default,
) {

  BpkPageIndicatorImpl(state.pagerState, totalIndicators, indicatorLabel, modifier, style)
}

@Stable
@OptIn(ExperimentalPagerApi::class)
class BpkPageIndicatorState internal constructor(
  internal var pagerState: PagerState
) {
  suspend fun scrollToPage(page: Int) {
    if (page >= 0) {
      pagerState.animateScrollToPage(page)
    }
  }

  val currentPage
    get() = pagerState.currentPage
}

@Composable
@OptIn(ExperimentalPagerApi::class)
fun rememberBpkPageIndicatorState(): BpkPageIndicatorState {
  val pagerState = rememberPagerState()
  return remember {
    BpkPageIndicatorState(
      pagerState = pagerState
    )
  }
}

