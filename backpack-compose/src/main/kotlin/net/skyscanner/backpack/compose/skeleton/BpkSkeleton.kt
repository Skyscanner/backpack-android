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

package net.skyscanner.backpack.compose.skeleton

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
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkBorderRadius
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.compose.tokens.internal.BpkSkeletonColors

private val BpkSkeletonBorderRadiusXXS = BpkBorderRadius.Xs.div(2)
private val BpkHeadlineSkeletonHeightSm = BpkSpacing.Md
private val BpkHeadlineSkeletonHeightMd = BpkSpacing.Base
private val BpkHeadlineSkeletonHeightLg = BpkSpacing.Xl
private val BpkCircleSizeSm = BpkSpacing.Xl
private val BpkCircleSizeLg = BpkSpacing.Lg.times(2)

enum class BpkSkeletonHeightSizeType {
    /**
     * Small size: 8.dp
     */
    Small,

    /**
     * Medium size: 16.dp
     */
    Medium,

    /**
     * Large size: 32.dp
     */
    Large,

    /**
     * Custom size, need set a detail height of the component.
     */
    Custom,
}

sealed class BpkCircleSizeType {
    /**
     * Small size: 32.dp
     */
    data object Small : BpkCircleSizeType()

    /**
     * Large size: 48.dp
     */
    data object Large : BpkCircleSizeType()

    /**
     * Custom size width dimension.
     * @param diameter unit: Dp
     */
    class Custom(val diameter: Dp) : BpkCircleSizeType()
}

enum class BpkSkeletonCornerType {
    Square,
    Rounded,
}

enum class BpkShimmerSize {
    Large, Small
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
private fun shimmerBackgroundColor(): Color = BpkTheme.colors.surfaceHighlight

@Composable
private fun shimmerPrimaryColor(): Color = BpkSkeletonColors.shimmerStartEnd

@Composable
private fun shimmerSecondaryColor(): Color = BpkSkeletonColors.shimmerCenter

@Composable
private fun shimmerAnimation(width: Dp, size: BpkShimmerSize): State<Dp> {
    val infiniteTransition = rememberInfiniteTransition()
    return infiniteTransition.animateValue(
        initialValue = -width,
        targetValue = width,
        typeConverter = Dp.VectorConverter,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = if (size == BpkShimmerSize.Small) 300 else 1000,
                delayMillis = 200,
                easing = LinearEasing,
            ),
        ),
    )
}

@Composable
private fun ShimmerBox(
    modifier: Modifier = Modifier,
) {
    return Box(
        modifier = modifier
            .background(
                brush = Brush.horizontalGradient(
                    listOf(
                        shimmerPrimaryColor(),
                        shimmerSecondaryColor(),
                        shimmerPrimaryColor(),
                    ),
                ),
            ),
    )
}

/**
 * Shimmer Overlay
 * @param modifier To set some common attrs.
 * @param shimmerSize To get the right animation for the size of the component.
 * @param content Child component.
 */
@Composable
fun BpkShimmerOverlay(
    modifier: Modifier = Modifier,
    shimmerSize: BpkShimmerSize = BpkShimmerSize.Large,
    content: @Composable BoxScope.() -> Unit,
) {
    BoxWithConstraints(modifier = modifier) {
        content()
        val offsetX by shimmerAnimation(maxWidth, shimmerSize)
        ShimmerBox(
            modifier = Modifier
                .fillMaxSize()
                .clipToBounds()
                .graphicsLayer {
                    translationX = offsetX.toPx()
                    scaleX = if (shimmerSize == BpkShimmerSize.Small) 2f else 1f
                },
        )
    }
}

/**
 * Image skeleton.
 * @param modifier To set some common attrs such as width, height.
 * @param cornerType To decide if it is square corner or rounded corner.
 */
@Composable
fun BpkImageSkeleton(
    modifier: Modifier = Modifier,
    cornerType: BpkSkeletonCornerType = BpkSkeletonCornerType.Square,
) {
    val cornerRadius = if (cornerType === BpkSkeletonCornerType.Rounded) BpkBorderRadius.Sm else 0.dp
    Box(
        modifier = modifier
            .background(
                shimmerBackgroundColor(),
                RoundedCornerShape(cornerRadius),
            ),
    )
}

/**
 * Body text skeleton.
 * @param modifier To set some common attrs such as width, height, please set height to wrap content.
 */
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
                    .background(shimmerBackgroundColor(), RoundedCornerShape(BpkSkeletonBorderRadiusXXS)),
            )
            Box(
                modifier = Modifier
                    .height(BpkSpacing.Md)
                    .fillMaxWidth()
                    .background(shimmerBackgroundColor(), RoundedCornerShape(BpkSkeletonBorderRadiusXXS)),
            )
            Box(
                modifier = Modifier
                    .height(BpkSpacing.Md)
                    .fillMaxWidth(0.57f)
                    .background(shimmerBackgroundColor(), RoundedCornerShape(BpkSkeletonBorderRadiusXXS)),
            )
        }
    }
}

/**
 * Headline skeleton.
 * @param modifier To set some common attrs such as width, height.
 * @param skeletonHeightSize Small: 8.dp, Medium: 16.dp, Large: 32.dp, or set to Custom and set height of the modifier.
 */
@Composable
fun BpkHeadlineSkeleton(
    modifier: Modifier = Modifier,
    skeletonHeightSize: BpkSkeletonHeightSizeType = BpkSkeletonHeightSizeType.Small,
) {
    val cornerRadius = if (skeletonHeightSize === BpkSkeletonHeightSizeType.Small) {
        BpkSkeletonBorderRadiusXXS
    } else {
        BpkBorderRadius.Xs
    }
    Box(
        modifier = modifier
            .enhanceHeadlineHeight(skeletonHeightSize)
            .background(shimmerBackgroundColor(), RoundedCornerShape(cornerRadius)),
    )
}

/**
 * Circle skeleton.
 * @param circleSize Small: 32.dp, Large: 48.dp, or use Custom(xx.dp).
 */
@Composable
fun BpkCircleSkeleton(
    circleSize: BpkCircleSizeType,
    modifier: Modifier = Modifier,
) {
    val diameter: Dp = when (circleSize) {
        is BpkCircleSizeType.Small -> BpkCircleSizeSm
        is BpkCircleSizeType.Large -> BpkCircleSizeLg
        is BpkCircleSizeType.Custom -> circleSize.diameter
    }

    Box(
        modifier = modifier
            .size(diameter, diameter)
            .background(shimmerBackgroundColor(), RoundedCornerShape(diameter.div(2))),
    )
}
