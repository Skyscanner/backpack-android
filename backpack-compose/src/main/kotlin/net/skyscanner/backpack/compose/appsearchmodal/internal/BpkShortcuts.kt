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

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import kotlinx.coroutines.launch
import net.skyscanner.backpack.compose.appsearchmodal.BpkShortcut
import net.skyscanner.backpack.compose.chipgroup.single.BpkSingleChipItem
import net.skyscanner.backpack.compose.chipgroup.single.BpkSingleSelectChipGroup
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.compose.utils.BpkBehaviouralEventWrapper

@Composable
internal fun BpkShortcuts(
    shortcuts: List<BpkShortcut>,
    onHideModal: suspend () -> Unit,
    modifier: Modifier = Modifier,
    behaviouralEventWrapper: BpkBehaviouralEventWrapper? = null,
) {
    val singleSelectChips = shortcuts.map {
        BpkSingleChipItem(text = it.text, icon = it.icon)
    }
    val coroutineScope = rememberCoroutineScope()
    BpkSingleSelectChipGroup(
        modifier = modifier.padding(
            top = BpkSpacing.Md,
            bottom = BpkSpacing.Base,
        ),
        contentPadding = PaddingValues(horizontal = BpkSpacing.Base),
        chips = singleSelectChips,
        selectedIndex = remember { mutableIntStateOf(-1) }.intValue,
        onItemClicked = { item ->
            coroutineScope.launch { onHideModal() }
                .invokeOnCompletion { shortcuts[singleSelectChips.indexOf(item)].onShortcutSelected() }
        },
        behaviouralEventWrapper = behaviouralEventWrapper,
    )
}
