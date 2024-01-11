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

package net.skyscanner.backpack.nudger

import net.skyscanner.backpack.BpkSnapshotTest
import net.skyscanner.backpack.BpkTestVariant
import net.skyscanner.backpack.Variants
import org.junit.Before
import org.junit.Test

class BpkNudgerTest : BpkSnapshotTest() {

    private lateinit var nudger: BpkNudger

    @Before
    fun setup() {
        nudger = BpkNudger(testContext)
    }

    @Test
    fun default() {
        nudger.value = 5
        snap(nudger)
    }

    @Test
    @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode, BpkTestVariant.Themed)
    fun minusDisabled() {
        nudger.value = 0
        snap(nudger)
    }

    @Test
    @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode, BpkTestVariant.Themed)
    fun plusDisabled() {
        nudger.value = 10
        nudger.maxValue = 10
        snap(nudger)
    }
}
