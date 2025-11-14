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
import net.skyscanner.backpack.demo.compose.ClickableDefaultElevationCardExample
import net.skyscanner.backpack.demo.compose.ClickableNoElevationCardExample
import net.skyscanner.backpack.demo.compose.DisabledCardExample
import net.skyscanner.backpack.demo.compose.EmptyContentCardExample
import net.skyscanner.backpack.demo.compose.FocusableCardExample
import net.skyscanner.backpack.demo.compose.LargeCornersCardExample
import net.skyscanner.backpack.demo.compose.LargeCornersNoElevationCardExample
import net.skyscanner.backpack.demo.compose.LargeCornersNoPaddingCardExample
import net.skyscanner.backpack.demo.compose.LongTextCardExample
import net.skyscanner.backpack.demo.compose.MultipleContentCardExample
import net.skyscanner.backpack.demo.compose.NoElevationCardExample
import net.skyscanner.backpack.demo.compose.NoPaddingCardExample
import net.skyscanner.backpack.demo.compose.NonClickableCardExample
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
    fun unfocused() = snap(background = { BpkTheme.colors.surfaceHighlight }) {
        NonClickableCardExample()
    }

    @Test
    @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode)
    fun focused() = snap(background = { BpkTheme.colors.surfaceHighlight }) {
        FocusableCardExample()
    }

    @Test
    @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode)
    fun noElevation() = snap(background = { BpkTheme.colors.surfaceHighlight }) {
        NoElevationCardExample()
    }

    @Test
    @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode)
    fun disabled() = snap(background = { BpkTheme.colors.surfaceHighlight }) {
        DisabledCardExample()
    }

    @Test
    @Variants(BpkTestVariant.Default)
    fun clickableWithDefaultElevation() = snap(background = { BpkTheme.colors.surfaceHighlight }) {
        ClickableDefaultElevationCardExample()
    }

    @Test
    @Variants(BpkTestVariant.Default)
    fun clickableWithNoElevation() = snap(background = { BpkTheme.colors.surfaceHighlight }) {
        ClickableNoElevationCardExample()
    }

    @Test
    @Variants(BpkTestVariant.Default)
    fun largeCornersNoPadding() = snap(background = { BpkTheme.colors.surfaceHighlight }) {
        LargeCornersNoPaddingCardExample()
    }

    @Test
    @Variants(BpkTestVariant.Default)
    fun largeCornersNoElevation() = snap(background = { BpkTheme.colors.surfaceHighlight }) {
        LargeCornersNoElevationCardExample()
    }

    @Test
    @Variants(BpkTestVariant.Rtl)
    fun rtlLayout() = snap(background = { BpkTheme.colors.surfaceHighlight }) {
        SmallCornersCardExample()
    }

    @Test
    @Variants(BpkTestVariant.Default)
    fun emptyContent() = snap(background = { BpkTheme.colors.surfaceHighlight }) {
        EmptyContentCardExample()
    }

    @Test
    @Variants(BpkTestVariant.Default)
    fun longText() = snap(background = { BpkTheme.colors.surfaceHighlight }) {
        LongTextCardExample()
    }

    @Test
    @Variants(BpkTestVariant.Default)
    fun multipleContent() = snap(background = { BpkTheme.colors.surfaceHighlight }) {
        MultipleContentCardExample()
    }

    companion object {

        @JvmStatic
        @ParameterizedRobolectricTestRunner.Parameters
        fun flavours(): List<Boolean> = listOf(true, false)
    }
}
