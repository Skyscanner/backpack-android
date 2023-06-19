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

package net.skyscanner.backpack.compose.singleselectchipgroup

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import net.skyscanner.backpack.compose.chip.BpkChip
import net.skyscanner.backpack.compose.chip.BpkChipStyle
import net.skyscanner.backpack.compose.singleselectchipgroup.SingleSelectChipGroupData.ChipGroupType
import net.skyscanner.backpack.compose.singleselectchipgroup.SingleSelectChipGroupData.ChipItem
import net.skyscanner.backpack.compose.tokens.BpkSpacing

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun BpkSingleSelectChipGroup(
    chips: List<ChipItem>,
    selectedIndex: Int,
    onItemClicked: (ChipItem) -> Unit,
    modifier: Modifier = Modifier,
    style: BpkChipStyle = BpkChipStyle.Default,
    type: ChipGroupType = ChipGroupType.RAIL,
) {
    when (type) {
        ChipGroupType.RAIL -> {
            LazyRow(
                modifier = modifier,
                state = rememberLazyListState(),
            ) {
                itemsIndexed(items = chips) { index, chip ->
                    ChipItem(chip, index == selectedIndex, style) {
                        onItemClicked.invoke(chips[index])
                    }
                }
            }
        }

        ChipGroupType.WRAP -> {
            FlowRow(
                modifier = modifier,
            ) {
                chips.forEachIndexed { index, chip ->
                    ChipItem(chip, index == selectedIndex, style) {
                        onItemClicked.invoke(chips[index])
                    }
                }
            }
        }
    }
}

@Composable
private fun ChipItem(chip: ChipItem, selected: Boolean, style: BpkChipStyle, onSelectedChange: () -> Unit) {
    BpkChip(
        modifier = Modifier
            .padding(PaddingValues(BpkSpacing.Sm))
            .semantics { role = Role.RadioButton },
        text = chip.text,
        icon = chip.icon,
        selected = selected,
        style = style,
        onSelectedChange = { onSelectedChange.invoke() },
    )
}
