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

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.progressSemantics
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.setProgress
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import java.lang.Integer.min
import kotlin.math.roundToInt

enum class BpkPageIndicatorStyle {
  Default,
  OverImage,
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun BpkPageIndicator(
  currentIndex: Int,
  totalIndicators: Int,
  indicatorLabel: String,
  modifier: Modifier = Modifier,
  style: BpkPageIndicatorStyle = BpkPageIndicatorStyle.Default,
) {

  if (totalIndicators <= 1) return
  val indicatorSize = BpkSpacing.Md
  val indicatorCount = min(totalIndicators, DISPLAY_DOTS_MAX)
  val transitionDistance = LocalDensity.current.run { (indicatorSize * 2).toPx().toInt() }
  val rtlMultiplier = if (LocalLayoutDirection.current == LayoutDirection.Rtl) -1 else 1

  AnimatedContent(
    modifier = modifier.pageIndicatorModifier(
      indicatorCount = indicatorCount,
      indicatorSize = indicatorSize,
      range = 0 until totalIndicators,
      currentIndex = currentIndex,
      indicatorLabel = indicatorLabel,
    ),
    targetState = currentIndex,
    transitionSpec = {
      val isIncreasing = targetState >= initialState
      val multiplier = if (isIncreasing) 1 else -1
      val indexRange = if (isIncreasing) 3..totalIndicators - 3 else 2..totalIndicators - 4
      val shouldAnimate = totalIndicators > DISPLAY_DOTS_MAX && currentIndex in indexRange
      ContentTransform(
        targetContentEnter = if (shouldAnimate) slideInHorizontally(
          animationSpec = tween(TRANSITION_DURATION),
          initialOffsetX = { transitionDistance * multiplier * rtlMultiplier },
        ) else EnterTransition.None,
        initialContentExit = if (shouldAnimate) slideOutHorizontally(
          animationSpec = tween(TRANSITION_DURATION),
          targetOffsetX = { transitionDistance * -multiplier * rtlMultiplier },
        ) else ExitTransition.None,
      )
    },
  ) { index ->
    PageIndicatorRow(
      indicatorSize = indicatorSize,
      totalIndicators = totalIndicators,
      index = index,
      style = style,
    )
  }
}

@Composable
private fun PageIndicatorRow(
  indicatorSize: Dp,
  totalIndicators: Int,
  index: Int,
  style: BpkPageIndicatorStyle,
  modifier: Modifier = Modifier,
) {
  Row(
    modifier = modifier,
    verticalAlignment = Alignment.CenterVertically,
  ) {
    val targetIndex = if (totalIndicators > DISPLAY_DOTS_MAX) {
      when (index) {
        in 0..2 -> index
        totalIndicators - 2 -> DISPLAY_DOTS_MAX - 2
        totalIndicators - 1 -> DISPLAY_DOTS_MAX - 1
        else -> 2
      }
    } else {
      index
    }
    for (i in 0 until totalIndicators) {
      PageIndicatorDot(
        indicatorSize = indicatorSize,
        isSelected = i == targetIndex,
        style = style,
      )
      Spacer(modifier = Modifier.width(indicatorSize))
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
          animationSpec = tween(TRANSITION_DURATION),
        ).value,
      ),
  )
}

private fun Modifier.pageIndicatorModifier(
  indicatorCount: Int,
  indicatorSize: Dp,
  range: IntRange,
  currentIndex: Int,
  indicatorLabel: String,
): Modifier = size(
  width = indicatorSize * (2 * indicatorCount + 1),
  height = indicatorSize * 3,
)
  .padding(start = indicatorSize, end = indicatorSize)
  .semantics(mergeDescendants = true) {
    setProgress { targetValue ->
      // without this rounding the values will only decrease
      val newValue = targetValue
        .roundToInt()
        .coerceIn(range)
      newValue != currentIndex
    }

    // override describing percents
    stateDescription = indicatorLabel
  }
  .progressSemantics(
    // this is needed to use volume keys
    value = currentIndex.toFloat(),
    valueRange = range.first.toFloat()..range.last.toFloat(),
    steps = range.last - range.first,
  )

private const val DISPLAY_DOTS_MAX = 5
private const val TRANSITION_DURATION = 200
