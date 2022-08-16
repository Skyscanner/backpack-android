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
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import net.skyscanner.backpack.compose.tokens.BpkBorderRadius
import net.skyscanner.backpack.compose.tokens.BpkColor
import net.skyscanner.backpack.compose.utils.dynamicColorOf

private val BpkImageSkeletonSize = 96.dp
private val BpkCircleSkeletonSizeSm = 32.dp
private val BpkCircleSkeletonSizeLg = 48.dp
private val BpkSkeletonBorderRadiusXXS = 2.dp
private val BpkHeadlineSkeletonWidth = 80.dp
private val BpkHeadlineSkeletonHeightSm = 8.dp
private val BpkHeadlineSkeletonHeightMd = 16.dp
private val BpkHeadlineSkeletonHeightLg = 32.dp

enum class BpkSkeletonSizeType {
    Small,
    Medium,
    Large
}

enum class BpkCircleSkeletonSizeType {
    Small,
    Large
}

enum class BpkSkeletonCornerType {
    Square,
    Rounded
}

@Composable
private fun shimmerBackgroundColor() : Color {
    return dynamicColorOf(BpkColor.SkyGrayTint06, BpkColor.BlackTint02)
}

@Composable
private fun shimmerPrimaryColor() : Color {
    return dynamicColorOf(Color(0x00FFFFFF), Color(0x00000000))
}

@Composable
private fun shimmerSecondaryColor() : Color {
    return dynamicColorOf(Color(0x99FFFFFF), Color(0x33000000))
}

@Composable
private fun shimmerAnimation(): State<Dp> {
    var inifiniteTransition = rememberInfiniteTransition()
    return inifiniteTransition.animateValue(initialValue = (-500).dp, targetValue = 500.dp, typeConverter = Dp.VectorConverter,
        animationSpec = infiniteRepeatable(animation = tween(durationMillis = 1000, delayMillis = 200, easing = LinearEasing)))

}
@Composable
private fun ShimmerBox () {
    return Box(modifier = Modifier
        .size(1000.dp, 1000.dp)
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

@Preview
@Composable
fun BpkImageSkeleton (
    modifier: Modifier = Modifier,
    cornerType: BpkSkeletonCornerType = BpkSkeletonCornerType.Square
) {
    val offsetX by shimmerAnimation()
    Box(modifier = modifier
        .size(BpkImageSkeletonSize, BpkImageSkeletonSize)
        .background(shimmerBackgroundColor(),
            RoundedCornerShape(if (cornerType === BpkSkeletonCornerType.Rounded) BpkBorderRadius.Sm else 0.dp))
        .offset(Dp(offsetX.value), 0.dp)) {
        ShimmerBox()
    }
}

@Preview
@Composable
fun BpkBodyTextSkeleton (
    modifier: Modifier = Modifier,
    skeletonSize:  BpkSkeletonSizeType= BpkSkeletonSizeType.Small
) {
    val offsetX by shimmerAnimation()

    val skeletonWidthPercentage = when (skeletonSize) {
        BpkSkeletonSizeType.Small -> 0.42F
        BpkSkeletonSizeType.Medium -> 0.62F
        BpkSkeletonSizeType.Large -> 0.73F
    }

    Box(modifier = modifier){
        Box(modifier = modifier
            .fillMaxWidth(skeletonWidthPercentage)
            .height(8.dp)
            .background(shimmerBackgroundColor(), RoundedCornerShape(BpkSkeletonBorderRadiusXXS))
            .offset(Dp(offsetX.value), 0.dp)) {
            ShimmerBox()
        }
    }
}

@Preview
@Composable
fun BpkHeadlineSkeleton (
    modifier: Modifier = Modifier,
    skeletonSize:  BpkSkeletonSizeType= BpkSkeletonSizeType.Small
) {
    val offsetX by shimmerAnimation()
    val skeletonHeight = when (skeletonSize) {
        BpkSkeletonSizeType.Small -> BpkHeadlineSkeletonHeightSm
        BpkSkeletonSizeType.Medium -> BpkHeadlineSkeletonHeightMd
        BpkSkeletonSizeType.Large -> BpkHeadlineSkeletonHeightLg
    }

    Box(modifier = modifier
        .size(BpkHeadlineSkeletonWidth, skeletonHeight)
        .background(shimmerBackgroundColor(), RoundedCornerShape(BpkSkeletonBorderRadiusXXS))
        .offset(Dp(offsetX.value), 0.dp)) {
        ShimmerBox()
    }
}

@Preview
@Composable
fun BpkCircleSkeleton (
    modifier: Modifier = Modifier,
    circleSize:  BpkCircleSkeletonSizeType= BpkCircleSkeletonSizeType.Small
) {
    val offsetX by shimmerAnimation()
    val circleSizeDp = if(circleSize === BpkCircleSkeletonSizeType.Large) BpkCircleSkeletonSizeLg else BpkCircleSkeletonSizeSm
    Box(modifier = modifier
        .size(circleSizeDp, circleSizeDp)
        .background(shimmerBackgroundColor(), RoundedCornerShape(circleSizeDp.div(2)))
        .offset(Dp(offsetX.value), 0.dp)) {
        ShimmerBox()
    }
}