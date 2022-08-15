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

package net.skyscanner.backpack.demo.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import net.skyscanner.backpack.compose.sleketon.BpkBodyTextSkeleton
import net.skyscanner.backpack.compose.sleketon.BpkCircleSkeleton
import net.skyscanner.backpack.compose.sleketon.BpkCircleSkeletonSizeType
import net.skyscanner.backpack.compose.sleketon.BpkHeadlineSkeleton
import net.skyscanner.backpack.compose.sleketon.BpkImageSkeleton
import net.skyscanner.backpack.compose.sleketon.BpkSkeletonCornerType
import net.skyscanner.backpack.compose.sleketon.BpkSkeletonSizeType
import net.skyscanner.backpack.compose.tokens.BpkSpacing

@Composable
fun SkeletonStory() {
  Column(
    modifier = Modifier.padding(BpkSpacing.Base),
    verticalArrangement = Arrangement.spacedBy(BpkSpacing.Xl)
  ) {
    CircleSkeletonExample()
    ImageSkeletonExample()
    HeadlineSkeletonExample()
    BodyTextSkeletonExample()
  }
}

@Composable
@Preview
fun ImageSkeletonExample() {
  Row(
    modifier = Modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.SpaceAround
  ) {
    BpkImageSkeleton(cornerType = BpkSkeletonCornerType.Square)
    BpkImageSkeleton(cornerType = BpkSkeletonCornerType.Rounded)
  }
}

@Composable
@Preview
fun CircleSkeletonExample() {
  Row(
    modifier = Modifier.fillMaxWidth(),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.SpaceAround
  ) {
    BpkCircleSkeleton()
    BpkCircleSkeleton(circleSize = BpkCircleSkeletonSizeType.Large)
  }
}

@Composable
@Preview
fun BodyTextSkeletonExample() {
  Column(
    modifier = Modifier.fillMaxWidth(),
    verticalArrangement = Arrangement.spacedBy(BpkSpacing.Base)
  ) {
    BpkBodyTextSkeleton()
    BpkBodyTextSkeleton(skeletonSize = BpkSkeletonSizeType.Medium)
    BpkBodyTextSkeleton(skeletonSize = BpkSkeletonSizeType.Large)
  }
}

@Composable
@Preview
fun HeadlineSkeletonExample() {
  Row(
    modifier = Modifier.fillMaxWidth(),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.SpaceEvenly
  ) {
    BpkHeadlineSkeleton()
    BpkHeadlineSkeleton(skeletonSize = BpkSkeletonSizeType.Medium)
    BpkHeadlineSkeleton(skeletonSize = BpkSkeletonSizeType.Large)
  }
}
