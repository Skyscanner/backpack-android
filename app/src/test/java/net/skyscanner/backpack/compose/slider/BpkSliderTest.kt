/*
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
 *
 */

package net.skyscanner.backpack.compose.slider

import androidx.compose.ui.unit.dp
import net.skyscanner.backpack.BpkTestVariant
import net.skyscanner.backpack.Variants
import net.skyscanner.backpack.compose.BpkSnapshotTest
import net.skyscanner.backpack.demo.compose.DefaultSliderSample
import net.skyscanner.backpack.demo.compose.RangeSliderSample
import net.skyscanner.backpack.demo.compose.RangeSliderWithLabelsSample
import org.junit.Test

class BpkSliderTest : BpkSnapshotTest() {

    @Test
    fun default() = snap(width = 200.dp) {
        DefaultSliderSample()
    }

    @Test
    @Variants(BpkTestVariant.Default, BpkTestVariant.Rtl)
    fun range() = snap(width = 200.dp) {
        RangeSliderSample()
    }

    @Test
    @Variants(BpkTestVariant.Default, BpkTestVariant.Rtl)
    fun rangeWithLabel() = snap(width = 200.dp) {
        RangeSliderWithLabelsSample()
    }
}
