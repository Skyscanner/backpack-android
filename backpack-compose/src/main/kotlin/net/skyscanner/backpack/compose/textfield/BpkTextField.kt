/*
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

package net.skyscanner.backpack.compose.textfield

import SearchTextFieldShape
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import net.skyscanner.backpack.compose.fieldset.BpkFieldStatus
import net.skyscanner.backpack.compose.fieldset.LocalFieldStatus
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.searchinputsummary.Prefix
import net.skyscanner.backpack.compose.textfield.internal.BpkTextFieldImpl

data class BpkClearAction(
    val contentDescription: String,
    val onClick: () -> Unit,
)

@Composable
fun BpkTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    readOnly: Boolean = false,
    placeholder: String? = null,
    icon: BpkIcon? = null,
    status: BpkFieldStatus = LocalFieldStatus.current,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions(),
    maxLines: Int = 1,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    clearAction: BpkClearAction? = null,
    shape: SearchTextFieldShape = SearchTextFieldShape.Float,
) {
    BpkTextFieldImpl(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        readOnly = readOnly,
        placeholder = placeholder,
        status = status,
        prefix = icon?.let { Prefix.Icon(it) },
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        maxLines = maxLines,
        interactionSource = interactionSource,
        clearAction = clearAction,
        shape = shape,
    )
}

@Composable
fun BpkTextField(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    modifier: Modifier = Modifier,
    readOnly: Boolean = false,
    placeholder: String? = null,
    icon: BpkIcon? = null,
    status: BpkFieldStatus = LocalFieldStatus.current,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions(),
    maxLines: Int = 1,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    clearAction: BpkClearAction? = null,
) {
    BpkTextFieldImpl(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        readOnly = readOnly,
        placeholder = placeholder,
        prefix = icon?.let { Prefix.Icon(it) },
        status = status,
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        maxLines = maxLines,
        interactionSource = interactionSource,
        clearAction = clearAction,
    )
}
