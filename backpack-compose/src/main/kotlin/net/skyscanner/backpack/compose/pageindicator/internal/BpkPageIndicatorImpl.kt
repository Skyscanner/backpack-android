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

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
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

  Box {
    HorizontalPager(
      count = totalIndicators,
      state = pagerState,
      modifier = Modifier.size(1.dp).alpha(0f)
    ) {

    }
    HorizontalPagerIndicator(
      modifier = modifier.semantics { contentDescription = indicatorLabel },
      pagerState = pagerState,
      pageCount = min(totalIndicators, PAGE_INDICATOR_DOT_MAX_COUNT),
      activeColor = if (style == BpkPageIndicatorStyle.Default) BpkTheme.colors.textSecondary else BpkTheme.colors.textOnDark,
      inactiveColor = if (style == BpkPageIndicatorStyle.Default) BpkTheme.colors.line else BpkTheme.colors.textOnDark.copy(alpha = 0.5f),
      indicatorWidth = BpkSpacing.Md,
    )
  }
}

private const val PAGE_INDICATOR_DOT_MAX_COUNT = 5
