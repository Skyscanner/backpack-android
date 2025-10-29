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

package net.skyscanner.backpack.badge

import androidx.appcompat.content.res.AppCompatResources
import net.skyscanner.backpack.BpkSnapshotTest
import net.skyscanner.backpack.BpkTestVariant
import net.skyscanner.backpack.Variants
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

    private val type: BpkBadge.Type = flavour.first
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
        val badge = BpkBadge(testContext).apply {
            this.type = this@BpkBadgeTest.type
            text = "Badge"
        }
        snap(badge, background = type.rowBackground() ?: R.color.bpkCanvas)
    }

    @Test
    @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode, BpkTestVariant.Themed)
    fun textWithIcon() {
        // Skip some combinations to reduce test volume while maintaining coverage
        assumeTrue(type in listOf(BpkBadge.Type.Normal, BpkBadge.Type.Strong, BpkBadge.Type.Success))

        val badge = BpkBadge(testContext).apply {
            this.type = this@BpkBadgeTest.type
            text = "Badge"
            icon = AppCompatResources.getDrawable(testContext, R.drawable.bpk_tick_circle_sm)
        }
        snap(badge, background = type.rowBackground() ?: R.color.bpkCanvas)
    }

    @Test
    @Variants(BpkTestVariant.Default, BpkTestVariant.Rtl)
    fun rtl() {
        // Test RTL only for types with icons
        assumeTrue(type in listOf(BpkBadge.Type.Success, BpkBadge.Type.Warning, BpkBadge.Type.Destructive))

        val badge = BpkBadge(testContext).apply {
            this.type = this@BpkBadgeTest.type
            text = "Badge"
        }
        snap(badge, background = type.rowBackground() ?: R.color.bpkCanvas)
    }

    companion object {

        @JvmStatic
        @ParameterizedRobolectricTestRunner.Parameters(name = "{0} Screenshot")
        fun flavours(): List<Flavor> = BpkBadge.Type.entries.flatMap { type ->
            // Include VDL2 and non-VDL2 variants for all types
            listOf(
                Pair(type, true), // VDL2 enabled
                Pair(type, false), // VDL2 disabled
            )
        }
    }
}

private typealias Flavor = Pair<BpkBadge.Type, Boolean>

private fun BpkBadge.Type.rowBackground() =
    when (this) {
        BpkBadge.Type.Inverse,
        BpkBadge.Type.Outline,
        -> R.color.bpkCorePrimary
        else -> null
    }
