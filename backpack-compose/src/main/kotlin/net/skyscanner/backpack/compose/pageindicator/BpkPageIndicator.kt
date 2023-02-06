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

package net.skyscanner.backpack.compose.pageindicator

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.invisibleToUser
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import kotlinx.coroutines.launch
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import java.lang.Integer.min

enum class BpkPageIndicatorStyle {
  Default,
  OverImage,
}

@Composable
fun BpkPageIndicator(
  currentIndex: Int,
  totalIndicators: Int,
  modifier: Modifier = Modifier,
  style: BpkPageIndicatorStyle = BpkPageIndicatorStyle.Default,
) {

  if (totalIndicators <= 1) throw IllegalArgumentException("totalIndicators must be greater than 1")
  if (currentIndex !in 0 until totalIndicators) throw IllegalArgumentException("currentIndex must be between 0 and $totalIndicators")
  val indicatorSize = BpkSpacing.Md
  val indicatorCount = min(totalIndicators, DISPLAY_DOTS_MAX)

  val coroutineScope = rememberCoroutineScope()
  val state = rememberLazyListState()

  val offsetCount = if (totalIndicators > DISPLAY_DOTS_MAX) {
    when (currentIndex) {
      0 -> 0
      1 -> 2
      totalIndicators - 2 -> 6
      totalIndicators - 1 -> 8
      else -> 4
    }
  } else {
    0
  }
  val offsetPx = with(LocalDensity.current) { (indicatorSize * offsetCount).toPx().toInt() }
  LaunchedEffect(currentIndex) {
    coroutineScope.launch {
      state.animateScrollToItem(currentIndex, -offsetPx)
    }
  }

  LazyRow(
    modifier = modifier.pageIndicatorModifier(indicatorCount, indicatorSize),
    state = state,
    horizontalArrangement = Arrangement.Center,
    verticalAlignment = Alignment.CenterVertically,
    userScrollEnabled = false,
  ) {
    itemsIndexed(
      items = (0 until totalIndicators - 1).toList(),
      key = { index, _ -> index.hashCode() },
    ) { index, _ ->
      PageIndicatorDot(
        indicatorSize = indicatorSize,
        isSelected = index == currentIndex,
        style = style,
      )
      Spacer(modifier = Modifier.width(indicatorSize))
    }
    item {
      PageIndicatorDot(
        indicatorSize = indicatorSize,
        isSelected = totalIndicators - 1 == currentIndex,
        style = style,
      )
    }
  }
}

@Composable
private fun PageIndicatorDot(
  indicatorSize: Dp,
  isSelected: Boolean,
  style: BpkPageIndicatorStyle,
  modifier: Modifier = Modifier,
) {
  Box(
    modifier = modifier
      .clip(CircleShape)
      .size(indicatorSize)
      .background(
        color = animateColorAsState(
          targetValue = when {
            isSelected -> if (style == BpkPageIndicatorStyle.Default) BpkTheme.colors.textSecondary else BpkTheme.colors.textOnDark
            else -> if (style == BpkPageIndicatorStyle.Default) BpkTheme.colors.line else BpkTheme.colors.textOnDark.copy(
              alpha = 0.5f,
            )
          },
        ).value,
      ),
  )
}

@OptIn(ExperimentalComposeUiApi::class)
private fun Modifier.pageIndicatorModifier(
  indicatorCount: Int,
  indicatorSize: Dp,
): Modifier = size(
  width = indicatorSize * (2 * indicatorCount + 1),
  height = indicatorSize * 3,
)
  .padding(start = indicatorSize, end = indicatorSize)
  .semantics(mergeDescendants = true) { invisibleToUser() }

private const val DISPLAY_DOTS_MAX = 5
