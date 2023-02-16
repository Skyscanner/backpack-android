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

import androidx.annotation.IntRange
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import net.skyscanner.backpack.compose.carousel.internal.BpkCarouselImpl

@OptIn(ExperimentalPagerApi::class)
@Composable
fun BpkCarousel(
  count: Int,
  modifier: Modifier = Modifier,
  onImageChanged: ((Int) -> Unit)? = null,
  pagerState: PagerState = rememberPagerState(),
  imageContent: @Composable (BoxScope.(Int) -> Unit),
) {
  BpkCarouselImpl(
    count = count,
    modifier = modifier,
    pagerState = pagerState,
    onImageChanged = { onImageChanged?.invoke(it) },
    imageContent = { imageContent(it) },
  )
}

@ExperimentalPagerApi
@Composable
fun rememberCarouselPagerState(
  @IntRange(from = 0) currentImage: Int = 0,
): PagerState {
  val initialPageIndex = (Int.MAX_VALUE / 2) + currentImage
  return rememberPagerState(initialPageIndex)
}
