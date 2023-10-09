package net.skyscanner.backpack.compose.appsearchmodal.internal

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import net.skyscanner.backpack.compose.appsearchmodal.Shortcut
import net.skyscanner.backpack.compose.chipgroup.single.BpkSingleChipItem
import net.skyscanner.backpack.compose.chipgroup.single.BpkSingleSelectChipGroup
import net.skyscanner.backpack.compose.tokens.BpkSpacing

@Composable
internal fun BpkShortcuts(
    shortcuts: List<Shortcut>,
    modifier: Modifier = Modifier,
) {
    val selectedChip = remember { mutableIntStateOf(-1) }
    val singleSelectChips = shortcuts.map {
        BpkSingleChipItem(text = it.text, icon = it.icon)
    }
    BpkSingleSelectChipGroup(
        modifier = modifier.padding(
            top = BpkSpacing.Lg,
            start = BpkSpacing.Base,
        ),
        chips = singleSelectChips,
        selectedIndex = selectedChip.intValue,
        onItemClicked = {
            selectedChip.intValue = singleSelectChips.indexOf(it)
            shortcuts[selectedChip.intValue].onShortcutSelected()
        },
    )
}
