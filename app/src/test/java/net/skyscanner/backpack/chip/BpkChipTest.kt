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

package net.skyscanner.backpack.chip

import androidx.appcompat.content.res.AppCompatResources
import net.skyscanner.backpack.BpkSnapshotTest
import net.skyscanner.backpack.BpkTestVariant
import net.skyscanner.backpack.Variants
import net.skyscanner.backpack.configuration.BpkConfiguration
import net.skyscanner.backpack.demo.R
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.ParameterizedRobolectricTestRunner

@RunWith(ParameterizedRobolectricTestRunner::class)
class BpkChipTest(flavour: Flavor) : BpkSnapshotTest(listOfNotNull(flavour.first, "VDL2".takeIf { flavour.second })) {

    private val vdl2 = flavour.second

    @Before
    fun setup() {
        // Ensure we start from a known state
        BpkConfiguration.clearConfigs()
        BpkConfiguration.setConfigs(chipConfig = vdl2)
    }

    @Test
    fun default() {
        val view = BpkChip(testContext)
        view.text = "Chip"
        snap(view)
    }

    @Test
    @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode, BpkTestVariant.Themed)
    fun notSelected() {
        val view = BpkChip(testContext)
        view.text = "Chip"
        view.isSelected = false
        snap(view)
    }

    @Test
    @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode, BpkTestVariant.Themed)
    fun selected() {
        val view = BpkChip(testContext)
        view.text = "Chip"
        view.isSelected = true
        snap(view)
    }

    @Test
    @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode, BpkTestVariant.Themed)
    fun disabled() {
        val view = BpkChip(testContext)
        view.text = "Chip"
        view.isEnabled = false
        snap(view)
    }

    @Test
    fun withIcon() {
        val view = BpkChip(testContext)
        view.text = "Chip"
        view.icon = AppCompatResources.getDrawable(testContext, R.drawable.bpk_account)
        snap(view)
    }

    @Test
    @Variants(BpkTestVariant.Default)
    fun typeDropdown() {
        val view = BpkChip(testContext)
        view.text = "Chip"
        view.type = BpkChip.Type.Dropdown
        snap(view)
    }

    @Test
    @Variants(BpkTestVariant.Default)
    fun typeDismiss() {
        val view = BpkChip(testContext)
        view.text = "Chip"
        view.type = BpkChip.Type.Dismiss
        snap(view)
    }

    @Test
    fun withIconAndTypeDismiss() {
        val view = BpkChip(testContext)
        view.text = "Chip"
        view.icon = AppCompatResources.getDrawable(testContext, R.drawable.bpk_account)
        view.type = BpkChip.Type.Dismiss
        snap(view)
    }

    @Test
    @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode, BpkTestVariant.Themed)
    fun withIconAndTypeDropdown() {
        val view = BpkChip(testContext)
        view.text = "Chip"
        view.icon = AppCompatResources.getDrawable(testContext, R.drawable.bpk_account)
        view.type = BpkChip.Type.Dropdown
        snap(view)
    }

    @Test
    @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode)
    fun withIconAndTypeDropdown_Selected() {
        val view = BpkChip(testContext)
        view.text = "Chip"
        view.icon = AppCompatResources.getDrawable(testContext, R.drawable.bpk_account)
        view.type = BpkChip.Type.Dropdown
        view.isSelected = true
        snap(view)
    }

    @Test
    @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode)
    fun withIconAndTypeDropdown_Disabled() {
        val view = BpkChip(testContext)
        view.text = "Chip"
        view.icon = AppCompatResources.getDrawable(testContext, R.drawable.bpk_account)
        view.type = BpkChip.Type.Dropdown
        view.isEnabled = true
        snap(view)
    }

    companion object {

        @JvmStatic
        @ParameterizedRobolectricTestRunner.Parameters(name = "{0} Screenshot")
        fun flavours(): List<Pair<BpkChip.Style, Boolean>> = BpkChip.Style.entries.flatMap { style ->
            listOf(
                Pair(style, true),
                Pair(style, false),
            )
        }
    }
}

private typealias Flavor = Pair<BpkChip.Style, Boolean>
