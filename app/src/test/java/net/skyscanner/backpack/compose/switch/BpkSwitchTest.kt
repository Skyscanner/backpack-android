/**
 * Backpack for Android - Skyscanner's Design System
 *
 * Copyright 2025 Skyscanner Ltd
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

package net.skyscanner.backpack.compose.switch

import androidx.compose.ui.unit.dp
import net.skyscanner.backpack.compose.BpkSnapshotTest
import net.skyscanner.backpack.BpkTestVariant
import net.skyscanner.backpack.Variants
import net.skyscanner.backpack.demo.compose.CustomContentSwitchExample
import net.skyscanner.backpack.demo.compose.DefaultCheckedSwitchExample
import net.skyscanner.backpack.demo.compose.DefaultUncheckedSwitchExample
import net.skyscanner.backpack.demo.compose.DisabledCheckedSwitchExample
import net.skyscanner.backpack.demo.compose.DisabledUncheckedSwitchExample
import org.junit.Test

class BpkSwitchTest : BpkSnapshotTest() {

    @Test
    fun defaultUnchecked() = snap(width = 200.dp) {
        DefaultUncheckedSwitchExample()
    }

    @Test
    @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode)
    fun defaultChecked() = snap(width = 200.dp) {
        DefaultCheckedSwitchExample()
    }

    @Test
    @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode)
    fun disabledUnchecked() = snap(width = 200.dp) {
        DisabledUncheckedSwitchExample()
    }

    @Test
    @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode)
    fun disabledChecked() = snap(width = 200.dp) {
        DisabledCheckedSwitchExample()
    }

    @Test
    @Variants(BpkTestVariant.Default)
    fun customContent() = snap(width = 200.dp) {
        CustomContentSwitchExample()
    }
}
