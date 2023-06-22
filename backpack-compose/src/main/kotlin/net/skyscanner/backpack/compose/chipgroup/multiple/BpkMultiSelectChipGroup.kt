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

package net.skyscanner.backpack.compose.chipgroup.multiple

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import net.skyscanner.backpack.compose.chip.BpkChipStyle
import net.skyscanner.backpack.compose.chip.BpkChipType
import net.skyscanner.backpack.compose.chipgroup.multiple.internal.BpkMultiSelectChipGroupImpl
import net.skyscanner.backpack.compose.icon.BpkIcon

sealed class BpkMultiChipGroupType {
    class Rail(val stickyChip: BpkMultiChipItem? = null) : BpkMultiChipGroupType()
    object Wrap : BpkMultiChipGroupType()
}

data class BpkMultiChipItem(
    val text: String,
    val icon: BpkIcon? = null,
    val type: BpkChipType = BpkChipType.Selectable,
    val selected: Boolean = false,
    val onClick: () -> Unit,
)

@Composable
fun BpkMultiSelectChipGroup(
    chips: List<BpkMultiChipItem>,
    type: BpkMultiChipGroupType,
    modifier: Modifier = Modifier,
    style: BpkChipStyle = BpkChipStyle.Default,
) {
    BpkMultiSelectChipGroupImpl(
        chips = chips,
        type = type,
        modifier = modifier,
        style = style,
    )
}
