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
import androidx.compose.ui.unit.Dp
import net.skyscanner.backpack.compose.fieldset.BpkFieldStatus
import net.skyscanner.backpack.compose.searchinputsummary.Prefix
import net.skyscanner.backpack.compose.textfield.BpkClearAction
import net.skyscanner.backpack.compose.textfield.internal.BpkTextFieldImpl
import net.skyscanner.backpack.compose.textfield.internal.BpkTextFieldType
import net.skyscanner.backpack.compose.tokens.BpkSpacing

sealed class BpkSearchInputControlType {
    data object TextInput : BpkSearchInputControlType()
    data class ReadOnly(val isFocused: Boolean) : BpkSearchInputControlType()
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
    onInputChanged: (String) -> Unit,
    modifier: Modifier = Modifier,
    clearAction: BpkClearAction? = null,
    type: BpkSearchInputControlType = BpkSearchInputControlType.TextInput,
    docking: Docking = Docking.Float,
    horizontalPadding: Dp = BpkSpacing.Md,
    contentPadding: Dp = BpkSpacing.Md,
    minHeight: Dp = BpkSpacing.Xxl + BpkSpacing.Md,
) {
    val isFocused = if (type is BpkSearchInputControlType.ReadOnly) type.isFocused else null
    BpkTextFieldImpl(
        value = inputText,
        onValueChange = onInputChanged,
        modifier = modifier,
        readOnly = type is BpkSearchInputControlType.ReadOnly,
        placeholder = inputHint,
        prefix = prefix,
        status = BpkFieldStatus.Default,
        clearAction = clearAction,
        type = BpkTextFieldType.Search,
        isFocused = isFocused,
        docking = docking,
        horizontalPadding = horizontalPadding,
        contentPadding = contentPadding,
        minHeight = minHeight,
    )
}