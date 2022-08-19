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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import net.skyscanner.backpack.compose.sleketon.BpkBodyTextSkeleton
import net.skyscanner.backpack.compose.sleketon.BpkCircleSizeType
import net.skyscanner.backpack.compose.sleketon.BpkCircleSkeleton
import net.skyscanner.backpack.compose.sleketon.BpkHeadlineSkeleton
import net.skyscanner.backpack.compose.sleketon.BpkImageSkeleton
import net.skyscanner.backpack.compose.sleketon.BpkShimmerOverlay
import net.skyscanner.backpack.compose.sleketon.BpkSkeletonCornerType
import net.skyscanner.backpack.compose.sleketon.BpkSkeletonSizeType
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkSpacing

@Composable
fun SkeletonStory() {
  Column(
    modifier = Modifier.padding(BpkSpacing.Base),
    verticalArrangement = Arrangement.spacedBy(BpkSpacing.Md)
  ) {
    CircleSkeletonExample()
    ImageSkeletonExample()
    HeadlineSkeletonExample()
    BodyTextSkeletonExample()
    BpkShimmerSkeletonExample()
  }
}

@Composable
@Preview
fun ImageSkeletonExample() {
  Column() {
    BpkText(
      modifier = Modifier.padding(bottom = BpkSpacing.Sm),
      text = "Image Skeleton",
      style = BpkTheme.typography.footnote
    )
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceAround
    ) {
      BpkImageSkeleton(
        modifier = Modifier.size(BpkSpacing.Xxl.times(2), BpkSpacing.Xxl.times(2)),
        cornerType = BpkSkeletonCornerType.Square
      )
      BpkImageSkeleton(
        modifier = Modifier.size(BpkSpacing.Xl.times(3), BpkSpacing.Xl.times(2)),
        cornerType = BpkSkeletonCornerType.Rounded
      )
    }
  }
}

@Composable
@Preview
fun CircleSkeletonExample() {
  Column() {
    BpkText(
      modifier = Modifier.padding(bottom = BpkSpacing.Sm),
      text = "Circle Skeleton",
      style = BpkTheme.typography.footnote
    )
    Row(
      modifier = Modifier.fillMaxWidth(),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.SpaceAround
    ) {
      BpkCircleSkeleton(circleDiameter = BpkSpacing.Xl)
      BpkCircleSkeleton(circleSize = BpkCircleSizeType.Large)
    }
  }
}

@Composable
@Preview
fun BodyTextSkeletonExample() {
  Column() {
    BpkText(
      modifier = Modifier.padding(bottom = BpkSpacing.Sm),
      text = "Body Text Skeleton",
      style = BpkTheme.typography.footnote
    )
    Column() {
      BpkBodyTextSkeleton(modifier = Modifier.width(BpkSpacing.Xxl.times(5)))
    }
  }
}

@Composable
@Preview
fun HeadlineSkeletonExample() {
  Column(verticalArrangement = Arrangement.spacedBy(BpkSpacing.Base)) {
    BpkText(text = "Headline Skeleton", style = BpkTheme.typography.footnote)
    Row(
      modifier = Modifier.fillMaxWidth(),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.SpaceEvenly
    ) {
      BpkHeadlineSkeleton(modifier = Modifier.width(BpkSpacing.Xxl.times(2)), BpkSkeletonSizeType.Small)
      BpkHeadlineSkeleton(modifier = Modifier.width(BpkSpacing.Xxl.times(2)), BpkSkeletonSizeType.Medium)
      BpkHeadlineSkeleton(modifier = Modifier.width(BpkSpacing.Xxl.times(2)).height(50.dp))
    }
  }
}

@Composable
@Preview
fun BpkShimmerSkeletonExample() {
  Column(verticalArrangement = Arrangement.spacedBy(BpkSpacing.Base)) {
    BpkText(text = "Shimmer Skeleton", style = BpkTheme.typography.footnote)
    Row(
      modifier = Modifier.fillMaxWidth(),
    ) {
      BpkShimmerOverlay(modifier = Modifier.width(200.dp).wrapContentHeight()) {
        Row() {
          BpkImageSkeleton(modifier = Modifier.size(BpkSpacing.Xxl, BpkSpacing.Xxl))
          BpkBodyTextSkeleton(modifier = Modifier.padding(start = BpkSpacing.Md).width(152.dp))
        }
      }
    }
  }
}
