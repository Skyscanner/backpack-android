/**
 * Backpack for Android - Skyscanner's Design System
 *
 * Copyright 2018 - 2025 Skyscanner Ltd
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

package net.skyscanner.backpack.compose.card

import net.skyscanner.backpack.BpkTestVariant
import net.skyscanner.backpack.Variants
import net.skyscanner.backpack.compose.BpkSnapshotTest
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.configuration.BpkConfiguration
import net.skyscanner.backpack.demo.compose.LargeCornersCardExample
import net.skyscanner.backpack.demo.compose.NoPaddingCardExample
import net.skyscanner.backpack.demo.compose.NonClickableCardExample
import net.skyscanner.backpack.demo.compose.OnContrastStyleExample
import net.skyscanner.backpack.demo.compose.OnDefaultStyleExample
import net.skyscanner.backpack.demo.compose.OnStyleSwapExample
import net.skyscanner.backpack.demo.compose.SmallCornersCardExample
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.ParameterizedRobolectricTestRunner

@RunWith(ParameterizedRobolectricTestRunner::class)
class BpkCardTest(val vdl2Enabled: Boolean) :
    BpkSnapshotTest(if (vdl2Enabled) listOf("VDL2") else listOf("Default")) {

    @Before
    fun setup() {
        // Ensure we start from a known state
        BpkConfiguration.clearConfigs()
        BpkConfiguration.setConfigs(cardConfig = vdl2Enabled)
    }

    @Test
    fun onDefaultStyle() = snap(background = { BpkTheme.colors.surfaceHighlight }) {
        OnDefaultStyleExample()
    }

    @Test
    fun onContrastStyle() = snap(background = { BpkTheme.colors.surfaceHighlight }) {
        OnContrastStyleExample()
    }

    @Test
    fun smallCorner() = snap(background = { BpkTheme.colors.surfaceHighlight }) {
        SmallCornersCardExample()
    }

    @Test
    @Variants(BpkTestVariant.Default)
    fun largeCorner() = snap(background = { BpkTheme.colors.surfaceHighlight }) {
        LargeCornersCardExample()
    }

    @Test
    @Variants(BpkTestVariant.Default)
    fun noPadding() = snap(background = { BpkTheme.colors.surfaceHighlight }) {
        NoPaddingCardExample()
    }

    @Test
    @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode)
    fun unClickable() = snap(background = { BpkTheme.colors.surfaceHighlight }) {
        NonClickableCardExample()
    }

    @Test
    @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode)
    fun clickable() = snap(background = { BpkTheme.colors.surfaceHighlight }) {
        OnStyleSwapExample()
    }

    companion object {

        @JvmStatic
        @ParameterizedRobolectricTestRunner.Parameters
        fun flavours(): List<Boolean> = listOf(true, false)
    }
}
