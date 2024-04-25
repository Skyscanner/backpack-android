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

package net.skyscanner.backpack.compose.chipgroup.single.internal

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import net.skyscanner.backpack.compose.chip.BpkChip
import net.skyscanner.backpack.compose.chip.BpkChipStyle
import net.skyscanner.backpack.compose.chipgroup.single.BpkSingleChipGroupType
import net.skyscanner.backpack.compose.chipgroup.single.BpkSingleChipItem
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.compose.utils.BpkBehaviouralEventWrapper

@OptIn(ExperimentalLayoutApi::class)
@Composable
internal fun BpkSingleSelectChipGroupImpl(
    chips: List<BpkSingleChipItem>,
    selectedIndex: Int,
    onItemClicked: (BpkSingleChipItem) -> Unit,
    style: BpkChipStyle,
    type: BpkSingleChipGroupType,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    behaviouralEventWrapper: BpkBehaviouralEventWrapper? = null,
) {
    when (type) {
        BpkSingleChipGroupType.Rail -> {
            LazyRow(
                modifier = modifier.selectableGroup(),
                contentPadding = contentPadding,
                state = rememberLazyListState(),
                horizontalArrangement = Arrangement.spacedBy(BpkSpacing.Md),
            ) {
                itemsIndexed(items = chips) { index, chip ->
                    BehaviouralChipItem(
                        behaviouralEventWrapper = behaviouralEventWrapper,
                        chip = chip,
                        selected = selectedIndex == index,
                        style = style,
                        onItemClicked = { onItemClicked(chips[index]) },
                    )
                }
            }
        }

        BpkSingleChipGroupType.Wrap -> {
            FlowRow(
                modifier = modifier
                    .selectableGroup()
                    .padding(contentPadding),
                horizontalArrangement = Arrangement.spacedBy(BpkSpacing.Md),
                verticalArrangement = Arrangement.spacedBy(BpkSpacing.Md),
            ) {
                chips.forEachIndexed { index, chip ->
                    BehaviouralChipItem(
                        behaviouralEventWrapper = behaviouralEventWrapper,
                        chip = chip,
                        selected = selectedIndex == index,
                        style = style,
                        onItemClicked = { onItemClicked(chips[index]) },
                    )
                }
            }
        }
    }
}

@Composable
private fun BehaviouralChipItem(
    behaviouralEventWrapper: BpkBehaviouralEventWrapper?,
    chip: BpkSingleChipItem,
    selected: Boolean,
    style: BpkChipStyle,
    onItemClicked: () -> Unit,
) {
    if (behaviouralEventWrapper != null) {
        behaviouralEventWrapper(chip, Modifier) {
            ChipItem(
                chip = chip,
                selected = selected,
                style = style,
                onSelectedChange = onItemClicked,
            )
        }
    } else {
        ChipItem(
            chip = chip,
            selected = selected,
            style = style,
            onSelectedChange = onItemClicked,
        )
    }
}

@Composable
private fun ChipItem(
    chip: BpkSingleChipItem,
    selected: Boolean,
    style: BpkChipStyle,
    modifier: Modifier = Modifier,
    onSelectedChange: () -> Unit,
) {
    BpkChip(
        modifier = modifier
            .semantics { role = Role.RadioButton },
        text = chip.text,
        icon = chip.icon,
        selected = selected,
        style = style,
        onSelectedChange = { onSelectedChange.invoke() },
    )
}
