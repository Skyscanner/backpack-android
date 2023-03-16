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
import androidx.compose.ui.Modifier
import net.skyscanner.backpack.compose.BpkSnapshotTest
import net.skyscanner.backpack.BpkTestVariant
import androidx.test.ext.junit.runners.AndroidJUnit4
import net.skyscanner.backpack.Variants
import net.skyscanner.backpack.compose.sleketon.BpkBodyTextSkeleton
import net.skyscanner.backpack.compose.sleketon.BpkCircleSizeType
import net.skyscanner.backpack.compose.sleketon.BpkCircleSkeleton
import net.skyscanner.backpack.compose.sleketon.BpkHeadlineSkeleton
import net.skyscanner.backpack.compose.sleketon.BpkImageSkeleton
import net.skyscanner.backpack.compose.sleketon.BpkSkeletonCornerType
import net.skyscanner.backpack.compose.sleketon.BpkSkeletonHeightSizeType
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BpkSkeletonTest : BpkSnapshotTest() {

    @Test
    fun imageSquare() = snap {
        BpkImageSkeleton(modifier = Modifier.size(BpkSpacing.Xl, BpkSpacing.Xl))
    }

    @Test
    fun imageRounded() = snap {
        BpkImageSkeleton(modifier = Modifier.size(BpkSpacing.Xl, BpkSpacing.Xl), cornerType = BpkSkeletonCornerType.Rounded)
    }

    @Test
    fun circleWithSizeType() = snap {
        BpkCircleSkeleton(circleSize = BpkCircleSizeType.Large)
    }

    @Test
    @Variants(BpkTestVariant.Default)
    fun circleWithCustomDiameter() {
        snap {
            BpkCircleSkeleton(circleSize = BpkCircleSizeType.Custom(BpkSpacing.Xl))
        }
    }

    @Test
    fun smallHeadline() = snap {
        BpkHeadlineSkeleton(
            modifier = Modifier.width(BpkSpacing.Xxl.times(2)),
            skeletonHeightSize = BpkSkeletonHeightSizeType.Medium,
        )
    }

    @Test
    @Variants(BpkTestVariant.Default)
    fun headlineWithCustomHeight() {
        snap {
            BpkHeadlineSkeleton(
                modifier = Modifier
                    .width(BpkSpacing.Xxl.times(2))
                    .height(BpkSpacing.Xxl),
                skeletonHeightSize = BpkSkeletonHeightSizeType.Custom,
            )
        }
    }

    @Test
    fun bodyText() = snap {
        BpkBodyTextSkeleton(modifier = Modifier.width(BpkSpacing.Xxl.times(2)))
    }
}
