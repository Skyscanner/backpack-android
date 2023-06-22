package net.skyscanner.backpack.compose.chipgroup.multiple.internal

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import net.skyscanner.backpack.compose.chip.BpkChip
import net.skyscanner.backpack.compose.chip.BpkChipStyle
import net.skyscanner.backpack.compose.chip.BpkChipType.Dismiss
import net.skyscanner.backpack.compose.chip.BpkChipType.Dropdown
import net.skyscanner.backpack.compose.chip.BpkChipType.Selectable
import net.skyscanner.backpack.compose.chip.BpkDismissibleChip
import net.skyscanner.backpack.compose.chip.BpkDropdownChip
import net.skyscanner.backpack.compose.chipgroup.multiple.BpkMultiChipGroupType
import net.skyscanner.backpack.compose.chipgroup.multiple.BpkMultiChipItem
import net.skyscanner.backpack.compose.divider.BpkDivider
import net.skyscanner.backpack.compose.tokens.BpkSpacing

@OptIn(ExperimentalLayoutApi::class)
@Composable
internal fun BpkMultiSelectChipGroupImpl(
    chips: List<BpkMultiChipItem>,
    type: BpkMultiChipGroupType,
    style: BpkChipStyle,
    modifier: Modifier = Modifier,
) {
    when (type) {
        is BpkMultiChipGroupType.Rail -> {
            Row(modifier = modifier) {
                type.stickyChip?.let { chip ->
                    StickyChip(chip = chip, style = style)
                }
                LazyRow(
                    modifier = Modifier.selectableGroup(),
                    state = rememberLazyListState(),
                ) {
                    items(items = chips) { chip ->
                        ChipItem(
                            chip = chip,
                            style = style,
                            modifier = Modifier
                                .padding(PaddingValues(BpkSpacing.Sm))
                                .semantics { role = Role.Checkbox },)
                    }
                }
            }
        }

        BpkMultiChipGroupType.Wrap -> {
            FlowRow(
                modifier = modifier.selectableGroup(),
            ) {
                chips.forEach { chip ->
                    ChipItem(
                        chip = chip,
                        style = style,
                        modifier = Modifier
                            .padding(PaddingValues(BpkSpacing.Sm))
                            .semantics { role = Role.Checkbox },)
                }
            }
        }
    }
}

@Composable
private fun StickyChip(
    chip: BpkMultiChipItem,
    style: BpkChipStyle,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .padding(PaddingValues(BpkSpacing.Sm))
            .height(IntrinsicSize.Min),
    ) {
        ChipItem(
            chip = chip,
            style = style,
            modifier = Modifier
                .padding(PaddingValues(end = BpkSpacing.Md))
                .semantics {
                    role = Role.RadioButton
                    contentDescription = chip.text
                },
            isStickyChip = true,
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
    isStickyChip: Boolean = false,
) {
    var selectedState by remember { mutableStateOf(chip.selected) }
    val text = if (isStickyChip) "" else chip.text
    when (chip.type) {
        Selectable -> {
            BpkChip(
                text = text,
                icon = chip.icon,
                style = style,
                onSelectedChange = {
                    selectedState = it
                    chip.onClick.invoke()
                },
                modifier = modifier,
                selected = selectedState,
            )
        }

        Dropdown -> {
            BpkDropdownChip(
                text = text,
                icon = chip.icon,
                style = style,
                onSelectedChange = {
                    selectedState = it
                    chip.onClick.invoke()
                },
                modifier = modifier,
                selected = selectedState,
            )
        }

        Dismiss -> {
            BpkDismissibleChip(
                text = text,
                icon = chip.icon,
                style = style,
                modifier = modifier,
                onClick = chip.onClick,
            )
        }
    }
}
