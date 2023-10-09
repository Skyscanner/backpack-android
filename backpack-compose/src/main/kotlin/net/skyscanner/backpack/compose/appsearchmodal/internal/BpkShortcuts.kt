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
