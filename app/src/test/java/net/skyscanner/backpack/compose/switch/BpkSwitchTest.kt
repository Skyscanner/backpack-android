/**
 * Backpack for Android - Skyscanner's Design System
 *
 * Copyright 2018 - 2026 Skyscanner Ltd
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
import net.skyscanner.backpack.BpkTestVariant
import net.skyscanner.backpack.Variants
import net.skyscanner.backpack.compose.BpkSnapshotTest
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.demo.compose.CustomContentSwitchExample
import net.skyscanner.backpack.demo.compose.DefaultCheckedSwitchExample
import net.skyscanner.backpack.demo.compose.DefaultDisabledCheckedSwitchExample
import net.skyscanner.backpack.demo.compose.DefaultDisabledUncheckedSwitchExample
import net.skyscanner.backpack.demo.compose.DefaultUncheckedSwitchExample
import net.skyscanner.backpack.demo.compose.OnContrastCheckedSwitchExample
import net.skyscanner.backpack.demo.compose.OnContrastDisabledCheckedSwitchExample
import net.skyscanner.backpack.demo.compose.OnContrastDisabledUncheckedSwitchExample
import net.skyscanner.backpack.demo.compose.OnContrastUncheckedSwitchExample
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
    fun defaultDisabledUnchecked() = snap(width = 200.dp) {
        DefaultDisabledUncheckedSwitchExample()
    }

    @Test
    @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode)
    fun defaultDisabledChecked() = snap(width = 200.dp) {
        DefaultDisabledCheckedSwitchExample()
    }

    @Test
    fun onContrastUnchecked() = snap(width = 200.dp, background = { BpkTheme.colors.surfaceContrast }) {
        OnContrastUncheckedSwitchExample()
    }

    @Test
    @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode)
    fun onContrastChecked() = snap(width = 200.dp, background = { BpkTheme.colors.surfaceContrast }) {
        OnContrastCheckedSwitchExample()
    }

    @Test
    @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode)
    fun onContrastDisabledUnchecked() = snap(width = 200.dp, background = { BpkTheme.colors.surfaceContrast }) {
        OnContrastDisabledUncheckedSwitchExample()
    }

    @Test
    @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode)
    fun onContrastDisabledChecked() = snap(width = 200.dp, background = { BpkTheme.colors.surfaceContrast }) {
        OnContrastDisabledCheckedSwitchExample()
    }

    @Test
    @Variants(BpkTestVariant.Default)
    fun customContent() = snap(width = 200.dp) {
        CustomContentSwitchExample()
    }
}
