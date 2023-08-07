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
import net.skyscanner.backpack.compose.chipgroup.multiple.BpkMultiChipGroupType
import net.skyscanner.backpack.compose.chipgroup.multiple.BpkStickyChipItem
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.Filter
import net.skyscanner.backpack.demo.compose.MultiSelectChipGroupSample
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class BpkMultiSelectChipGroupTest(private val permutation: Permutation) : BpkSnapshotTest(listOf(permutation)) {

    @Test
    fun default() = snap {
        MultiSelectChipGroupSample(permutation.type)
    }

    @Test
    fun on_dark() = snap(background = { BpkTheme.colors.surfaceContrast }) {
        MultiSelectChipGroupSample(type = permutation.type, style = BpkChipStyle.OnDark)
    }

    @Test
    fun on_image() = snap(background = { BpkTheme.colors.coreAccent }) {
        MultiSelectChipGroupSample(type = permutation.type, style = BpkChipStyle.OnImage)
    }

    companion object {
        enum class Permutation(
            val type: BpkMultiChipGroupType,
        ) {
            RailWithoutStickyChip(type = BpkMultiChipGroupType.Rail()),
            RailWithStickyChip(
                type = BpkMultiChipGroupType.Rail(BpkStickyChipItem(text = "Sticky Chip", icon = BpkIcon.Filter) {}),
            ),
            Wrap(type = BpkMultiChipGroupType.Wrap),
        }

        @JvmStatic
        @Parameterized.Parameters(name = "{0} Screenshot")
        fun data(): Collection<Array<Any>> {
            return Permutation.entries.map { arrayOf(it) }
        }
    }
}
