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

package net.skyscanner.backpack.skeleton

import android.view.ViewGroup
import net.skyscanner.backpack.BpkSnapshotTest
import net.skyscanner.backpack.BpkTestVariant
import net.skyscanner.backpack.Variants
import org.junit.Test

class BpkHeadlineSkeletonTest : BpkSnapshotTest() {

    @Test
    fun smallHeadline() {
        val skeleton = BpkHeadlineSkeleton(testContext)
        skeleton.heightSize = BpkHeadlineSkeleton.SkeletonHeightSizeType.Small
        snap(skeleton, width = 100, height = 30)
    }

    @Test
    @Variants(BpkTestVariant.Default)
    fun mediumHeadline() {
        val skeleton = BpkHeadlineSkeleton(testContext)
        skeleton.heightSize = BpkHeadlineSkeleton.SkeletonHeightSizeType.Medium
        snap(skeleton, width = 100, height = 40)
    }

    @Test
    @Variants(BpkTestVariant.Default)
    fun largeHeadline() {
        val skeleton = BpkHeadlineSkeleton(testContext)
        skeleton.heightSize = BpkHeadlineSkeleton.SkeletonHeightSizeType.Large
        snap(skeleton, width = 100, height = 50)
    }

    @Test
    @Variants(BpkTestVariant.Default)
    fun headlineWithCustomHeight() {
        val skeleton = BpkHeadlineSkeleton(testContext)
        skeleton.heightSize = BpkHeadlineSkeleton.SkeletonHeightSizeType.Custom
        skeleton.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            (testContext.resources.displayMetrics.density * 30).toInt(),
        )
        snap(skeleton, width = 100, height = 50)
    }
}
