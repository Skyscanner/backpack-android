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

package net.skyscanner.backpack.compose.badge

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import net.skyscanner.backpack.BpkTestVariant
import net.skyscanner.backpack.Variants
import net.skyscanner.backpack.compose.BpkSnapshotTest
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.CloseCircle
import net.skyscanner.backpack.configuration.BpkConfiguration
import net.skyscanner.backpack.demo.R
import org.junit.Assume.assumeTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.ParameterizedRobolectricTestRunner

@RunWith(ParameterizedRobolectricTestRunner::class)
class BpkBadgeTest(flavour: Flavor) :
    BpkSnapshotTest(listOfNotNull(flavour.first, "VDL2".takeIf { flavour.second })) {

    private val type: BpkBadgeType = flavour.first
    private val vdl2 = flavour.second

    @Before
    fun setup() {
        // Ensure we start from a known state
        BpkConfiguration.clearConfigs()
        BpkConfiguration.setConfigs(badgeConfig = vdl2)
    }

    @Test
    @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode, BpkTestVariant.Themed)
    fun text() {
        snap(background = { type.backgroundContent() }) {
            BpkBadge(text = "Badge", type = type)
        }
    }

    @Test
    @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode, BpkTestVariant.Themed)
    fun textWithIcon() {
        // Skip some combinations to reduce test volume while maintaining coverage
        assumeTrue(type in listOf(BpkBadgeType.Normal, BpkBadgeType.Strong, BpkBadgeType.Success))

        snap(background = { type.backgroundContent() }) {
            BpkBadge(text = "Badge", type = type, icon = BpkIcon.CloseCircle)
        }
    }

    @Test
    @Variants(BpkTestVariant.Default, BpkTestVariant.Rtl)
    fun rtl() {
        // Test RTL only for types with default icons
        assumeTrue(type in listOf(BpkBadgeType.Success, BpkBadgeType.Warning, BpkBadgeType.Destructive))

        snap(background = { type.backgroundContent() }) {
            BpkBadge(text = "Badge", type = type)
        }
    }

    @Test
    @Variants(BpkTestVariant.Default)
    fun customIcon() {
        // Test custom icon only for Normal type to avoid excessive combinations
        assumeTrue(type == BpkBadgeType.Normal)

        snap {
            BpkBadge(
                text = "Badge",
                type = type,
                icon = painterResource(id = R.drawable.sample_icon),
            )
        }
    }

    companion object {

        @JvmStatic
        @ParameterizedRobolectricTestRunner.Parameters(name = "{0} Screenshot")
        fun flavours(): List<Flavor> = BpkBadgeType.entries.flatMap { type ->
            // Include VDL2 and non-VDL2 variants for all types
            listOf(
                Pair(type, true), // VDL2 enabled
                Pair(type, false), // VDL2 disabled
            )
        }
    }
}

private typealias Flavor = Pair<BpkBadgeType, Boolean>

@Composable
private fun BpkBadgeType.backgroundContent() = when (this) {
    BpkBadgeType.Inverse,
    BpkBadgeType.Outline,
    -> BpkTheme.colors.surfaceContrast
    else -> BpkTheme.colors.canvas
}
