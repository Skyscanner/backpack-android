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

package net.skyscanner.backpack.compose.pageindicator.internal

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import net.skyscanner.backpack.compose.pageindicator.BpkPageIndicatorStyle
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import java.lang.Integer.min

@OptIn(ExperimentalPagerApi::class)
@Composable
internal fun BpkPageIndicatorImpl(
  pagerState: PagerState,
  totalIndicators: Int,
  indicatorLabel: String,
  modifier: Modifier = Modifier,
  style: BpkPageIndicatorStyle,
) {

  val listState = rememberLazyListState()

  val indicatorSize = BpkSpacing.Md
  val indicatorCount = min(totalIndicators, PAGE_INDICATOR_DOT_MAX_COUNT)
  val totalWidth = indicatorSize * (2 * indicatorCount - 1)
  val widthInPx = LocalDensity.current.run { totalWidth.toPx() }

  val currentItem by remember { derivedStateOf { pagerState.currentPage } }
  val itemCount = pagerState.pageCount
  println("currentItem = $currentItem")

  LaunchedEffect(key1 = currentItem) {
    val viewportSize = listState.layoutInfo.viewportSize
    listState.animateScrollToItem(
      currentItem,
      (widthInPx / 2 - viewportSize.width / 2).toInt()
    )
  }

  Box(
    modifier = Modifier
      .width(totalWidth + indicatorSize * 2)
      .height(indicatorSize * 3)
      .semantics { contentDescription = indicatorLabel },
    contentAlignment = Alignment.Center,
  ) {
    HorizontalPager(
      count = totalIndicators,
      state = pagerState,
      modifier = Modifier
        .size(1.dp)
        .alpha(0f),
      content = {}
    )

    LazyRow(
      modifier = modifier.width(totalWidth),
      state = listState,
      horizontalArrangement = Arrangement.spacedBy(indicatorSize),
      userScrollEnabled = false
    ) {

      items(itemCount) { index ->
        val isSelected = (index == currentItem)

        Box(
          modifier = Modifier
            .clip(CircleShape)
            .size(indicatorSize)
            .background(
              color = if (isSelected) {
                if (style == BpkPageIndicatorStyle.Default) BpkTheme.colors.textSecondary else BpkTheme.colors.textOnDark
              } else {
                if (style == BpkPageIndicatorStyle.Default) BpkTheme.colors.line else BpkTheme.colors.textOnDark.copy(alpha = 0.5f)
              },
              shape = CircleShape
            )
        )
      }
    }
  }
}

private const val PAGE_INDICATOR_DOT_MAX_COUNT = 5
