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

package net.skyscanner.backpack.compose.chipgroup.multiple.internal

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import net.skyscanner.backpack.compose.chip.BpkChipStyle
import net.skyscanner.backpack.compose.chip.BpkChipType
import net.skyscanner.backpack.compose.chip.internal.BpkChipImpl
import net.skyscanner.backpack.compose.chip.internal.BpkDismissibleChipImpl
import net.skyscanner.backpack.compose.chipgroup.multiple.BpkMultiChipGroupType
import net.skyscanner.backpack.compose.chipgroup.multiple.BpkMultiChipItem
import net.skyscanner.backpack.compose.chipgroup.multiple.BpkStickyChipItem
import net.skyscanner.backpack.compose.divider.BpkDivider
import net.skyscanner.backpack.compose.tokens.BpkSpacing

@OptIn(ExperimentalLayoutApi::class)
@Composable
internal fun BpkMultiSelectChipGroupImpl(
    chips: List<BpkMultiChipItem>,
    type: BpkMultiChipGroupType,
    style: BpkChipStyle,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
    when (type) {
        is BpkMultiChipGroupType.Rail -> {
            Row(modifier = modifier) {
                type.stickyChip?.let { chip ->
                    StickyChip(chip = chip, style = style)
                }
                LazyRow(
                    modifier = Modifier.selectableGroup(),
                    contentPadding = contentPadding,
                    state = rememberLazyListState(),
                    horizontalArrangement = Arrangement.spacedBy(BpkSpacing.Md),
                ) {
                    items(items = chips) { chip ->
                        ChipItem(
                            chip = chip,
                            style = style,
                            modifier = Modifier
                                .semantics { role = Role.Checkbox },)
                    }
                }
            }
        }

        BpkMultiChipGroupType.Wrap -> {
            FlowRow(
                modifier = modifier.selectableGroup().padding(contentPadding),
                horizontalArrangement = Arrangement.spacedBy(BpkSpacing.Md),
                verticalArrangement = Arrangement.spacedBy(BpkSpacing.Md),
            ) {
                chips.forEach { chip ->
                    ChipItem(
                        chip = chip,
                        style = style,
                        modifier = Modifier
                            .semantics { role = Role.Checkbox },)
                }
            }
        }
    }
}

@Composable
private fun StickyChip(
    chip: BpkStickyChipItem,
    style: BpkChipStyle,
    modifier: Modifier = Modifier,
) {
    val interactionSource = remember { MutableInteractionSource() }
    Row(
        modifier = modifier
            .padding(end = BpkSpacing.Md)
            .height(IntrinsicSize.Min),
    ) {
        BpkChipImpl(
            modifier = Modifier
                .padding(end = BpkSpacing.Md)
                .semantics {
                    role = Role.Button
                    contentDescription = chip.text
                }
                .clickable(
                    interactionSource = interactionSource,
                    indication = LocalIndication.current,
                    onClick = chip.onClick,
                ),
            text = null,
            type = BpkChipType.Selectable,
            selected = chip.selected,
            onSelectedChange = null,
            enabled = true,
            style = style,
            icon = chip.icon,
        )

        BpkDivider(
            modifier = Modifier
                .fillMaxHeight()
                .width(1.dp),
        )
    }
}

@Composable
private fun ChipItem(
    chip: BpkMultiChipItem,
    style: BpkChipStyle,
    modifier: Modifier = Modifier,
) {
    when (chip.type) {
        BpkChipType.Selectable, BpkChipType.Dropdown -> BpkChipImpl(
            modifier = modifier,
            text = chip.text,
            type = chip.type,
            selected = chip.selected,
            onSelectedChange = { chip.onClick() },
            enabled = true,
            style = style,
            icon = chip.icon,
        )

        BpkChipType.Dismiss -> BpkDismissibleChipImpl(
            text = chip.text,
            style = style,
            icon = chip.icon,
            modifier = modifier,
            onClick = chip.onClick,
        )
    }
}
