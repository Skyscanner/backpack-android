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
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
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

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun BpkPageIndicator(
  currentIndex: Int,
  totalIndicators: Int,
  modifier: Modifier = Modifier,
  style: BpkPageIndicatorStyle = BpkPageIndicatorStyle.Default,
) {

  if (totalIndicators <= 1) throw IllegalArgumentException("totalIndicators must be greater than 1")
  val coercedIndex = currentIndex.coerceIn(0 until totalIndicators)
  val indicatorSize = BpkSpacing.Md
  val indicatorBoxWidth = indicatorSize * 2
  val indicatorBoxHeight = indicatorSize * 3
  val indicatorCount = min(totalIndicators, DISPLAY_DOTS_MAX)
  val rowWidth = (indicatorBoxWidth * indicatorCount) + indicatorSize

  val coroutineScope = rememberCoroutineScope()
  val state = rememberLazyListState()

  val offsetCount = when {
    totalIndicators > DISPLAY_DOTS_MAX -> {
      when (coercedIndex) {
        0 -> 0
        1 -> 2
        totalIndicators - 2 -> 6
        totalIndicators - 1 -> 8
        else -> 4
      }
    }
    else -> 0
  }
  val offsetPx = with(LocalDensity.current) { (indicatorSize * offsetCount).toPx().toInt() }
  LaunchedEffect(coercedIndex) {
    coroutineScope.launch {
      state.animateScrollToItem(coercedIndex, -offsetPx)
    }
  }

  LazyRow(
    modifier = modifier.width(rowWidth)
      .height(indicatorBoxHeight)
      .padding(start = indicatorSize / 2, end = indicatorSize / 2)
      .semantics(mergeDescendants = true) { invisibleToUser() },
    state = state,
    verticalAlignment = Alignment.CenterVertically,
    userScrollEnabled = false,
  ) {
    itemsIndexed(
      items = (0 until totalIndicators).toList(),
      key = { index, _ -> index.hashCode() },
    ) { index, _ ->
      PageIndicatorDot(
        indicatorSize = indicatorSize,
        isSelected = index == coercedIndex,
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
    modifier = modifier.size(indicatorSize * 2),
    contentAlignment = Alignment.Center,
  ) {
    Box(
      modifier = Modifier
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
}

private const val DISPLAY_DOTS_MAX = 5
