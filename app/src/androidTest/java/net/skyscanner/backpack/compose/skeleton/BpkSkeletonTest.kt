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

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.ui.Modifier
import androidx.test.ext.junit.runners.AndroidJUnit4
import net.skyscanner.backpack.BpkSnapshotTest
import net.skyscanner.backpack.BpkTestVariant
import net.skyscanner.backpack.compose.sleketon.BpkBodyTextSkeleton
import net.skyscanner.backpack.compose.sleketon.BpkCircleSizeType
import net.skyscanner.backpack.compose.sleketon.BpkCircleSkeleton
import net.skyscanner.backpack.compose.sleketon.BpkHeadlineSkeleton
import net.skyscanner.backpack.compose.sleketon.BpkImageSkeleton
import net.skyscanner.backpack.compose.sleketon.BpkShimmerOverlay
import net.skyscanner.backpack.compose.sleketon.BpkSkeletonCornerType
import net.skyscanner.backpack.compose.sleketon.BpkSkeletonHeightSizeType
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BpkSkeletonTest : BpkSnapshotTest() {

  @Before
  fun setup() {
    setDimensions(height = 200, width = 200)
  }

  @Test
  fun imageSquare() = composed {
    BpkImageSkeleton(modifier = Modifier.size(BpkSpacing.Xl, BpkSpacing.Xl))
  }

  @Test
  fun imageRounded() = composed {
    BpkImageSkeleton(modifier = Modifier.size(BpkSpacing.Xl, BpkSpacing.Xl), cornerType = BpkSkeletonCornerType.Rounded)
  }

  @Test
  fun circleWithSizeType() = composed {
    BpkCircleSkeleton(circleSize = BpkCircleSizeType.Large)
  }

  @Test
  fun circleWithCustomDiameter() {
    assumeVariant(BpkTestVariant.Default)
    composed {
      BpkCircleSkeleton(circleSize = BpkCircleSizeType.Custom(BpkSpacing.Xl))
    }
  }

  @Test
  fun smallHeadline() = composed {
    BpkHeadlineSkeleton(
      modifier = Modifier.width(BpkSpacing.Xxl.times(2)),
      skeletonHeightSize = BpkSkeletonHeightSizeType.Medium
    )
  }

  @Test
  fun headlineWithCustomHeight() {
    assumeVariant(BpkTestVariant.Default)
    composed {
      BpkHeadlineSkeleton(
        modifier = Modifier
          .width(BpkSpacing.Xxl.times(2))
          .height(BpkSpacing.Xxl),
        skeletonHeightSize = BpkSkeletonHeightSizeType.Custom
      )
    }
  }

  @Test
  fun bodyText() = composed {
    BpkBodyTextSkeleton(modifier = Modifier.width(BpkSpacing.Xxl.times(2)))
  }

  @Test
  fun shimmerOverlay() = composed {
    BpkShimmerOverlay(modifier = Modifier.wrapContentSize()) {
      BpkBodyTextSkeleton(modifier = Modifier.width(BpkSpacing.Xxl.times(2)))
    }
  }
}
