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

package net.skyscanner.backpack.compose.chipgroup.single

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import net.skyscanner.backpack.compose.chip.BpkChipStyle
import net.skyscanner.backpack.compose.chipgroup.single.internal.BpkSingleChipGroupType
import net.skyscanner.backpack.compose.chipgroup.single.internal.BpkSingleChipItem
import net.skyscanner.backpack.compose.chipgroup.single.internal.BpkSingleSelectChipGroupImpl

@Composable
fun BpkSingleSelectChipGroup(
    chips: List<BpkSingleChipItem>,
    selectedIndex: Int,
    onItemClicked: (BpkSingleChipItem) -> Unit,
    modifier: Modifier = Modifier,
    style: BpkChipStyle = BpkChipStyle.Default,
    type: BpkSingleChipGroupType = BpkSingleChipGroupType.RAIL,
) {
    BpkSingleSelectChipGroupImpl(
        chips = chips, selectedIndex = selectedIndex,
        onItemClicked = onItemClicked,
        modifier = modifier,
        style = style,
        type = type,
    )
}
