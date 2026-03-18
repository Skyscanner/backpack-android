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

package net.skyscanner.backpack.compose.textfield.internal

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeightIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import net.skyscanner.backpack.compose.fieldset.BpkFieldStatus
import net.skyscanner.backpack.compose.fieldset.LocalFieldStatus
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.icon.BpkIconSize
import net.skyscanner.backpack.compose.searchinputsummary.Prefix
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.textfield.BpkClearAction
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkBorderRadius
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.compose.utils.hideContentIf

@Composable
internal fun BpkTextFieldImpl(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    readOnly: Boolean = false,
    placeholder: String? = null,
    prefix: Prefix? = null,
    status: BpkFieldStatus = LocalFieldStatus.current,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions(),
    minLines: Int = 1,
    maxLines: Int = 1,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    trailingIcon: BpkIcon? = null,
    clearAction: BpkClearAction? = null,
    type: BpkTextFieldType = BpkTextFieldType.Default,
) {

    var textFieldValueState by remember { mutableStateOf(TextFieldValue(text = value)) }
    val textFieldValue = textFieldValueState.copy(text = value)

    BpkTextFieldImpl(
        value = textFieldValue,
        onValueChange = {
            textFieldValueState = it
            if (value != it.text) {
                onValueChange(it.text)
            }
        },
        modifier = modifier,
        readOnly = readOnly,
        placeholder = placeholder,
        prefix = prefix,
        status = status,
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        minLines = minLines,
        maxLines = maxLines,
        interactionSource = interactionSource,
        trailingIcon = trailingIcon,
        clearAction = clearAction,
        type = type,
    )
}

@Composable
internal fun BpkTextFieldImpl(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    modifier: Modifier = Modifier,
    readOnly: Boolean = false,
    placeholder: String? = null,
    prefix: Prefix? = null,
    status: BpkFieldStatus = LocalFieldStatus.current,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions(),
    minLines: Int = 1,
    maxLines: Int = 1,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    trailingIcon: BpkIcon? = null,
    clearAction: BpkClearAction? = null,
    type: BpkTextFieldType = BpkTextFieldType.Default,
) {
    val isFocused = interactionSource.collectIsFocusedAsState().value
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        enabled = status != BpkFieldStatus.Disabled,
        readOnly = readOnly,
        textStyle = BpkTheme.typography.bodyDefault.copy(
            color = animateColorAsState(
                when (status) {
                    is BpkFieldStatus.Disabled -> BpkTheme.colors.textDisabled
                    else -> BpkTheme.colors.textPrimary
                },
            ).value,
        ),
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        singleLine = maxLines == 1,
        minLines = minLines,
        maxLines = maxLines,
        visualTransformation = visualTransformation,
        interactionSource = interactionSource,
        cursorBrush = SolidColor(BpkTheme.colors.coreAccent),
        modifier = modifier,
        decorationBox = {
            TextFieldBox(
                value = value,
                modifier = Modifier,
                placeholder = placeholder,
                status = status,
                prefix = prefix,
                maxLines = maxLines,
                isFocused = isFocused,
                trailingIcon = trailingIcon,
                textFieldContent = it,
                clearAction = if (readOnly && prefix == null) null else clearAction,
                type = type,
            )
        },
    )
}

@Composable
private fun TextFieldBox(
    value: TextFieldValue,
    modifier: Modifier = Modifier,
    placeholder: String? = null,
    status: BpkFieldStatus = LocalFieldStatus.current,
    prefix: Prefix? = null,
    maxLines: Int = 1,
    isFocused: Boolean = false,
    trailingIcon: BpkIcon? = null,
    clearAction: BpkClearAction? = null,
    type: BpkTextFieldType = BpkTextFieldType.Default,
    textFieldContent: @Composable () -> Unit,
) {
    val tintColor = fieldTintColor(status)
    val shape = textFieldShape(type)
    val borderColor = fieldBorderColor(status = status, isFocused = isFocused)

    Box(
        modifier = modifier
            .width(IntrinsicSize.Max)
            .requiredHeightIn(min = BpkFieldMinHeight)
            .background(BpkTheme.colors.surfaceDefault, shape)
            .border(width = 1.dp, shape = shape, color = borderColor),
        contentAlignment = Alignment.Center,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(horizontal = BpkSpacing.Md),
        ) {

            when (prefix) {
                is Prefix.Text ->
                    BpkText(
                        text = prefix.prefixText,
                        modifier = Modifier.padding(start = BpkSpacing.Sm),
                        color = tintColor,
                    )

                is Prefix.Icon ->
                    BpkIcon(
                        icon = prefix.icon,
                        contentDescription = null,
                        size = BpkIconSize.Large,
                        modifier = Modifier.padding(start = BpkSpacing.Sm),
                        tint = tintColor,
                    )

                else -> {}
            }

            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(BpkSpacing.Md),
            ) {

                BpkText(
                    text = placeholder ?: "",
                    color = tintColor,
                    maxLines = maxLines,
                    modifier = Modifier.hideContentIf(value.text.isNotEmpty()),
                    style = BpkTheme.typography.bodyDefault,
                    overflow = TextOverflow.Ellipsis,
                )

                textFieldContent()
            }

            BpkFieldTrailingIcon(
                trailingIcon = trailingIcon,
                status = status,
                clearAction = clearAction,
                hasValue = value.text.isNotEmpty(),
                iconSize = if (type == BpkTextFieldType.Search) BpkIconSize.Large else BpkIconSize.Small,
                iconPadding = if (type == BpkTextFieldType.Search) BpkSpacing.Sm else 0.dp,
            )
        }
    }
}

internal enum class BpkTextFieldType {
    Default,
    Search,
}

@Composable
private fun textFieldShape(type: BpkTextFieldType): RoundedCornerShape {
    val radius = if (type == BpkTextFieldType.Search) BpkBorderRadius.Md else BpkBorderRadius.Sm
    return RoundedCornerShape(radius)
}
