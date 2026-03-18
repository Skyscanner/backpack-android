/**
 * Backpack for Android - Skyscanner's Design System
 *
 * Copyright 2018 - 2026 Skyscanner Ltd
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

package net.skyscanner.backpack.compose.searchinputcontrol

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import net.skyscanner.backpack.compose.fieldset.BpkFieldStatus
import net.skyscanner.backpack.compose.searchinputsummary.Prefix
import net.skyscanner.backpack.compose.textfield.BpkClearAction
import net.skyscanner.backpack.compose.textfield.internal.BpkControlFieldImpl

sealed class BpkSearchInputControlStyle {
    data object OnDefault : BpkSearchInputControlStyle()
    data object OnContrast : BpkSearchInputControlStyle()
}

/**
 * Defines the corner rounding style for BpkSearchInputControl, allowing multiple
 * controls to be stacked together with shared corner rounding.
 */
sealed class Docking {
    /** All corners are rounded (default). */
    data object Float : Docking()

    /** Top corners rounded, bottom corners square. Use for the first item in a docked stack. */
    data object Top : Docking()

    /** No corners rounded. Use for middle items in a docked stack. */
    data object Middle : Docking()

    /** Bottom corners rounded, top corners square. Use for the last item in a docked stack. */
    data object Bottom : Docking()
}

@Composable
fun BpkSearchInputControl(
    inputText: String,
    inputHint: String,
    prefix: Prefix,
    modifier: Modifier = Modifier,
    clearAction: BpkClearAction? = null,
    isFocused: Boolean = false,
    style: BpkSearchInputControlStyle = BpkSearchInputControlStyle.OnDefault,
    docking: Docking = Docking.Float,
) {
    BpkControlFieldImpl(
        value = inputText,
        modifier = modifier,
        placeholder = inputHint,
        prefix = prefix,
        status = BpkFieldStatus.Default,
        isFocused = isFocused,
        clearAction = clearAction,
        style = style,
        docking = docking,
    )
}
