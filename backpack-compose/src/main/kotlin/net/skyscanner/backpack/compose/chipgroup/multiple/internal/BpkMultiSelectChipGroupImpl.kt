package net.skyscanner.backpack.compose.chipgroup.multiple.internal

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
        StickyChipItem(
            chip = chip,
            style = style,
            modifier = Modifier
                .padding(PaddingValues(end = BpkSpacing.Md))
                .semantics {
                    role = Role.Button
                    contentDescription = chip.text
                },
        )
        BpkDivider(
            modifier = Modifier
                .fillMaxHeight()
                .width(1.dp),

        )
    }
}

@Composable
private fun StickyChipItem(
    chip: BpkMultiChipItem,
    style: BpkChipStyle,
    modifier: Modifier = Modifier,
) {
    val interactionSource = remember { MutableInteractionSource() }
    when (chip.type) {
        BpkChipType.Selectable, BpkChipType.Dropdown -> BpkChipImpl(
            modifier = modifier.clickable(
                interactionSource = interactionSource,
                indication = LocalIndication.current,
            ) { chip.onClick.invoke() },
            text = null,
            type = chip.type,
            selected = chip.selected,
            onSelectedChange = null,
            enabled = true,
            style = style,
            icon = chip.icon,
        )

        BpkChipType.Dismiss -> BpkDismissibleChipImpl(
            text = null,
            style = style,
            icon = chip.icon,
            modifier = modifier,
            onClick = chip.onClick,
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
