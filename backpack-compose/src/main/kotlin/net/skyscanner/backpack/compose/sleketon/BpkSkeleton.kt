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

package net.skyscanner.backpack.compose.sleketon

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.animateValue
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import net.skyscanner.backpack.compose.tokens.BpkBorderRadius
import net.skyscanner.backpack.compose.tokens.BpkColor
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.compose.utils.dynamicColorOf

private val BpkSkeletonBorderRadiusXXS = BpkBorderRadius.Xs.div(2)
private val BpkHeadlineSkeletonHeightSm = BpkSpacing.Md
private val BpkHeadlineSkeletonHeightMd = BpkSpacing.Base
private val BpkHeadlineSkeletonHeightLg = BpkSpacing.Xl
private val BpkCircleSizeSm = BpkSpacing.Xl
private val BpkCircleSizeLg = BpkSpacing.Lg.times(2)

enum class BpkSkeletonHeightSizeType {
  Small,
  Medium,
  Large,
  Custom
}

sealed class BpkCircleSizeType {
  object Small : BpkCircleSizeType()
  object Large : BpkCircleSizeType()
  class Custom(val diameter: Dp) : BpkCircleSizeType()
}

enum class BpkSkeletonCornerType {
  Square,
  Rounded
}

private fun Modifier.enhanceHeadlineHeight(skeletonHeightSize: BpkSkeletonHeightSizeType): Modifier {
  if (skeletonHeightSize === BpkSkeletonHeightSizeType.Custom) {
    return this
  }

  val heightSize = when (skeletonHeightSize) {
    BpkSkeletonHeightSizeType.Small -> BpkHeadlineSkeletonHeightSm
    BpkSkeletonHeightSizeType.Medium -> BpkHeadlineSkeletonHeightMd
    BpkSkeletonHeightSizeType.Large -> BpkHeadlineSkeletonHeightLg
    BpkSkeletonHeightSizeType.Custom -> 0.dp
  }

  return this.height(heightSize)
}

@Composable
private fun shimmerBackgroundColor(): Color {
  return dynamicColorOf(BpkColor.SkyGrayTint06, BpkColor.BlackTint02)
}

@Composable
private fun shimmerPrimaryColor(): Color {
  return Color.Transparent
}

@Composable
private fun shimmerSecondaryColor(): Color {
  return dynamicColorOf(Color(0x99FFFFFF), Color(0x33000000))
}

@Composable
private fun shimmerAnimation(): State<Dp> {
  val infiniteTransition = rememberInfiniteTransition()
  return infiniteTransition.animateValue(
    initialValue = (-500).dp, targetValue = 500.dp, typeConverter = Dp.VectorConverter,
    animationSpec = infiniteRepeatable(animation = tween(durationMillis = 1000, delayMillis = 200, easing = LinearEasing))
  )

}

@Composable
private fun ShimmerBox(modifier: Modifier) {
  return Box(
    modifier = modifier
      .background(
        brush = Brush.horizontalGradient(
          listOf(
            shimmerPrimaryColor(),
            shimmerSecondaryColor(),
            shimmerPrimaryColor(),
          )
        )
      )
  )
}

@Composable
fun BpkImageSkeleton(
  modifier: Modifier = Modifier,
  cornerType: BpkSkeletonCornerType = BpkSkeletonCornerType.Square
) {
  val cornerRadius = if (cornerType === BpkSkeletonCornerType.Rounded) BpkBorderRadius.Sm else 0.dp
  Box(
    modifier = modifier
      .background(
        shimmerBackgroundColor(),
        RoundedCornerShape(cornerRadius)
      )
  )
}

@Composable
fun BpkBodyTextSkeleton(
  modifier: Modifier = Modifier,
) {
  Box(modifier = modifier.height(BpkSpacing.Xxl)) {
    Column(verticalArrangement = Arrangement.spacedBy(BpkSpacing.Md)) {
      Box(
        modifier = Modifier
          .height(BpkSpacing.Md)
          .fillMaxWidth(0.85f)
          .background(shimmerBackgroundColor(), RoundedCornerShape(BpkSkeletonBorderRadiusXXS))
      )
      Box(
        modifier = Modifier
          .height(BpkSpacing.Md)
          .fillMaxWidth()
          .background(shimmerBackgroundColor(), RoundedCornerShape(BpkSkeletonBorderRadiusXXS))
      )
      Box(
        modifier = Modifier
          .height(BpkSpacing.Md)
          .fillMaxWidth(0.57f)
          .background(shimmerBackgroundColor(), RoundedCornerShape(BpkSkeletonBorderRadiusXXS))
      )
    }
  }
}

@Composable
fun BpkHeadlineSkeleton(
  modifier: Modifier = Modifier,
  skeletonHeightSize: BpkSkeletonHeightSizeType = BpkSkeletonHeightSizeType.Small
) {
  Box(
    modifier = modifier
      .enhanceHeadlineHeight(skeletonHeightSize)
      .background(shimmerBackgroundColor(), RoundedCornerShape(BpkBorderRadius.Sm))
  )
}

@Composable
fun BpkCircleSkeleton(
  circleSize: BpkCircleSizeType
) {
  val diameter: Dp = when (circleSize) {
    is BpkCircleSizeType.Small -> BpkCircleSizeSm
    is BpkCircleSizeType.Large -> BpkCircleSizeLg
    is BpkCircleSizeType.Custom -> circleSize.diameter
  }

  Box(
    modifier = Modifier
      .size(diameter, diameter)
      .background(shimmerBackgroundColor(), RoundedCornerShape(diameter.div(2)))
  )
}

@Composable
fun BpkShimmerOverlay(modifier: Modifier, content: @Composable BoxScope.() -> Unit) {
  val offsetX by shimmerAnimation()
  Box(modifier = modifier) {
    content()
    ShimmerBox(
      modifier = Modifier
        .fillMaxSize()
        .clipToBounds()
        .offset(Dp(offsetX.value), 0.dp)
    )
  }
}
