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

package net.skyscanner.backpack.compose.searchinputsummary

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import net.skyscanner.backpack.compose.fieldset.BpkFieldStatus
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.textfield.BpkClearAction
import net.skyscanner.backpack.compose.textfield.internal.BpkTextFieldImpl
import net.skyscanner.backpack.compose.textfield.internal.BpkTextFieldType
import net.skyscanner.backpack.compose.tokens.Search

sealed class Prefix {
    data class Icon(
        val icon: BpkIcon = BpkIcon.Search,
    ) : Prefix()

    data class Text(
        val prefixText: String,
    ) : Prefix()

    data object None : Prefix()
}

sealed class BpkSearchInputSummaryType {
    data object TextInput : BpkSearchInputSummaryType()
    data class ReadOnly(val isFocused: Boolean) : BpkSearchInputSummaryType()
}

/**
 * Defines the corner rounding style for BpkSearchInputSummary.
 */
sealed class BpkSearchInputSummaryRounding {
    /**
     * All corners are rounded (default behavior).
     */
    data object AllCorners : BpkSearchInputSummaryRounding()

    /**
     * Only the start corners (top-start and bottom-start) are rounded.
     * Useful for the first item in a horizontal row of inputs.
     */
    data object TopCorners : BpkSearchInputSummaryRounding()

    /**
     * Only the end corners (top-end and bottom-end) are rounded.
     * Useful for the last item in a horizontal row of inputs.
     */
    data object BottomCorners : BpkSearchInputSummaryRounding()

    data object NoRoundedCorners : BpkSearchInputSummaryRounding()
}

@Composable
fun BpkSearchInputSummary(
    inputText: String,
    inputHint: String,
    prefix: Prefix,
    onInputChanged: (String) -> Unit,
    clearAction: BpkClearAction,
    modifier: Modifier = Modifier,
    type: BpkSearchInputSummaryType = BpkSearchInputSummaryType.TextInput,
    rounding: BpkSearchInputSummaryRounding = BpkSearchInputSummaryRounding.AllCorners,
) {
    val isFocused = if (type is BpkSearchInputSummaryType.ReadOnly) type.isFocused else null
    BpkTextFieldImpl(
        value = inputText,
        onValueChange = onInputChanged,
        modifier = modifier,
        readOnly = type is BpkSearchInputSummaryType.ReadOnly,
        placeholder = inputHint,
        prefix = prefix,
        status = BpkFieldStatus.Default,
        clearAction = clearAction,
        type = BpkTextFieldType.Search,
        isFocused = isFocused,
        rounding = rounding,
    )
}
