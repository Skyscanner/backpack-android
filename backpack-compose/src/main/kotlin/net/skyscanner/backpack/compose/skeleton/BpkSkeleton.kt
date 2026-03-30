/**
 * Backpack for Android - Skyscanner's Design System
 *
 * Copyright 2018 - 2026 Skyscanner Ltd
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

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import net.skyscanner.backpack.compose.skeleton.internal.BpkBodyTextSkeletonImpl
import net.skyscanner.backpack.compose.skeleton.internal.BpkCircleSkeletonImpl
import net.skyscanner.backpack.compose.skeleton.internal.BpkHeadlineSkeletonImpl
import net.skyscanner.backpack.compose.skeleton.internal.BpkImageSkeletonImpl
import net.skyscanner.backpack.compose.skeleton.internal.BpkShimmerOverlayImpl

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

/**
 * Contains animation variables used for the shimmer effect for a given size of BpkSkeleton
 */
enum class BpkShimmerSize(
    internal val durationMillis: Int,
    internal val delayMillis: Int,
) {
    Large(durationMillis = 1000, delayMillis = 200),
    Small(durationMillis = 300, delayMillis = 400),
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
    BpkShimmerOverlayImpl(
        modifier = modifier,
        shimmerSize = shimmerSize,
        content = content,
    )
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
    BpkImageSkeletonImpl(
        modifier = modifier,
        cornerType = cornerType,
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
    BpkBodyTextSkeletonImpl(
        modifier = modifier,
    )
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
    BpkHeadlineSkeletonImpl(
        modifier = modifier,
        skeletonHeightSize = skeletonHeightSize,
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
    BpkCircleSkeletonImpl(
        circleSize = circleSize,
        modifier = modifier,
    )
}
