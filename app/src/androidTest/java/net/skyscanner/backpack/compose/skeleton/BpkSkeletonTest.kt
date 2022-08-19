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
import net.skyscanner.backpack.compose.sleketon.BpkSkeletonSizeType
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
  fun imageSkeletonSquare() = composed {
    BpkImageSkeleton(modifier = Modifier.size(BpkSpacing.Xl, BpkSpacing.Xl))
  }

  @Test
  fun imageSkeletonRounded() = composed {
    BpkImageSkeleton(modifier = Modifier.size(BpkSpacing.Xl, BpkSpacing.Xl), cornerType = BpkSkeletonCornerType.Rounded)
  }

  @Test
  fun circleSkeletonWithSizeType() {
    assumeVariant(BpkTestVariant.Default)
    composed {
      BpkCircleSkeleton(circleSize = BpkCircleSizeType.Large)
    }
  }

  @Test
  fun circleSkeletonWithCustomDiameter() {
    assumeVariant(BpkTestVariant.Default)
    composed {
      BpkCircleSkeleton(circleDiameter = BpkSpacing.Xl)
    }
  }

  @Test
  fun headlineSkeletonSmall() {
    assumeVariant(BpkTestVariant.Default, BpkTestVariant.DarkMode)
    composed {
      BpkHeadlineSkeleton(
        modifier = Modifier.width(BpkSpacing.Xxl.times(2)),
        skeletonSize = BpkSkeletonSizeType.Medium
      )
    }
  }

  @Test
  fun headlineSkeletonWithCustomHeight() {
    assumeVariant(BpkTestVariant.Default, BpkTestVariant.DarkMode)
    composed {
      BpkHeadlineSkeleton(modifier = Modifier.width(BpkSpacing.Xxl.times(2)).height(BpkSpacing.Xxl))
    }
  }

  @Test
  fun bodyTextSkeleton() {
    assumeVariant(BpkTestVariant.Default, BpkTestVariant.DarkMode)
    composed {
      BpkBodyTextSkeleton(modifier = Modifier.width(BpkSpacing.Xxl.times(2)))
    }
  }

  @Test
  fun shimmerOverlay() {
    assumeVariant(BpkTestVariant.Default, BpkTestVariant.DarkMode)
    composed {
      BpkShimmerOverlay(modifier = Modifier.wrapContentSize()) {
        BpkBodyTextSkeleton(modifier = Modifier.width(BpkSpacing.Xxl.times(2)))
      }
    }
  }
}
