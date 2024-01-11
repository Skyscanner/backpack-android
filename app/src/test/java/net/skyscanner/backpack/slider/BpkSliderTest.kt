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

package net.skyscanner.backpack.slider

import net.skyscanner.backpack.BpkSnapshotTest
import net.skyscanner.backpack.BpkTestVariant
import net.skyscanner.backpack.Variants
import org.junit.Test

class BpkSliderTest : BpkSnapshotTest() {

    private val slider = BpkSlider(testContext)

    @Test
    fun default() {
        slider.value = 0.5f
        snap(slider, width = 200)
    }

    @Test
    @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode)
    fun stepped() {
        slider.valueTo = 100f
        slider.value = 10f
        slider.stepSize = 10f
        snap(slider, width = 200)
    }

    @Test
    @Variants(BpkTestVariant.Default, BpkTestVariant.Rtl)
    fun range() {
        slider.valueTo = 100f
        slider.setValues(25f, 75f)
        snap(slider, width = 200)
    }
}
