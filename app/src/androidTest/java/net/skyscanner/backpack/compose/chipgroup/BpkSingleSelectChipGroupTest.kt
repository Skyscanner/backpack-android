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

package net.skyscanner.backpack.compose.chipgroup

import net.skyscanner.backpack.compose.BpkSnapshotTest
import net.skyscanner.backpack.compose.chip.BpkChipStyle
import net.skyscanner.backpack.compose.chipgroup.single.internal.BpkSingleChipGroupType
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.demo.compose.SingleSelectChipGroupSample
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class BpkSingleSelectChipGroupTest(private val type: BpkSingleChipGroupType) : BpkSnapshotTest(listOf(type)) {

    @Test
    fun default() = snap {
        SingleSelectChipGroupSample(type = type)
    }

    @Test
    fun onDark() = snap(background = { BpkTheme.colors.surfaceContrast }) {
        SingleSelectChipGroupSample(type = type, style = BpkChipStyle.OnDark)
    }

    @Test
    fun onImage() = snap(background = { BpkTheme.colors.coreAccent }) {
        SingleSelectChipGroupSample(type = type, style = BpkChipStyle.OnImage)
    }

    companion object {

        @JvmStatic
        @Parameterized.Parameters(name = "{0} Screenshot")
        fun types(): List<BpkSingleChipGroupType> = BpkSingleChipGroupType.values().toList()
    }
}
