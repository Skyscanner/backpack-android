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

package net.skyscanner.backpack.compose.select

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import net.skyscanner.backpack.compose.fieldset.BpkFieldStatus
import net.skyscanner.backpack.compose.fieldset.LocalFieldStatus
import net.skyscanner.backpack.compose.select.internal.BpkDropDownWidth
import net.skyscanner.backpack.compose.select.internal.BpkSelectImpl

@Composable
fun BpkSelect(
    options: List<String>,
    placeholder: String,
    selectedIndex: Int?,
    modifier: Modifier = Modifier,
    status: BpkFieldStatus = LocalFieldStatus.current,
    onSelectionChange: ((selectedIndex: Int) -> Unit)? = null,
    dropDownWidth: BpkDropDownWidth = BpkDropDownWidth.MAX_WIDTH,
) {
    BpkSelectImpl(options, selectedIndex, placeholder, modifier, status, onSelectionChange, dropDownWidth)
}

@Composable
fun BpkSelect(
    placeholder: String,
    text: String,
    modifier: Modifier = Modifier,
    status: BpkFieldStatus = LocalFieldStatus.current,
    onClick: (() -> Unit)? = null,
) {
    BpkSelectImpl(placeholder, text, modifier, status, onClick)
}
