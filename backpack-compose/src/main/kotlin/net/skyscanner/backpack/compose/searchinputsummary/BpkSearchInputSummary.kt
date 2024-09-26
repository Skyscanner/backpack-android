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

@Composable
fun BpkSearchInputSummary(
    inputText: String,
    inputHint: String,
    prefix: Prefix,
    onInputChanged: (String) -> Unit,
    clearAction: BpkClearAction,
    modifier: Modifier = Modifier,
    type: BpkSearchInputSummaryType = BpkSearchInputSummaryType.TextInput,
) {
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
        isFocused = type is BpkSearchInputSummaryType.ReadOnly && type.isFocused,
    )
}
