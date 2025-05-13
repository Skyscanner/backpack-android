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

package net.skyscanner.backpack.compose.textfield.internal

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.Role
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
import net.skyscanner.backpack.compose.tokens.CloseCircle
import net.skyscanner.backpack.compose.tokens.ExclamationCircle
import net.skyscanner.backpack.compose.tokens.TickCircle
import net.skyscanner.backpack.compose.utils.clickableWithRipple
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
    isFocused: Boolean? = null,
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
        isFocused = isFocused,
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
    isFocused: Boolean? = null,
    trailingIcon: BpkIcon? = null,
    clearAction: BpkClearAction? = null,
    type: BpkTextFieldType = BpkTextFieldType.Default,
) {
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        enabled = status != BpkFieldStatus.Disabled && !(type == BpkTextFieldType.Search && readOnly),
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
                isFocused = isFocused ?: interactionSource.collectIsFocusedAsState().value,
                trailingIcon = trailingIcon,
                textFieldContent = it,
                clearAction = if (readOnly && prefix == null) null else clearAction, // Remove clearAction if readOnly enabled.
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
    val textFieldBoxTintColor by animateColorAsState(
        when (status) {
            is BpkFieldStatus.Disabled -> BpkTheme.colors.textDisabled
            else -> BpkTheme.colors.textSecondary
        },
    )
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .width(IntrinsicSize.Max)
            .requiredHeightIn(min = BpkSpacing.Xxl + BpkSpacing.Md)
            .border(
                width = 1.dp, shape = textFieldShape(type = type),
                color = animateColorAsState(
                    when {
                        status is BpkFieldStatus.Disabled -> BpkTheme.colors.surfaceHighlight
                        status is BpkFieldStatus.Error -> BpkTheme.colors.textError
                        isFocused -> BpkTheme.colors.coreAccent
                        else -> BpkTheme.colors.line
                    },
                ).value,
            )
            .background(BpkTheme.colors.surfaceDefault, textFieldShape(type = type))
            .padding(horizontal = BpkSpacing.Md),
    ) {

        when (prefix) {
            is Prefix.Text ->
                BpkText(
                    text = prefix.prefixText,
                    modifier = Modifier.padding(start = BpkSpacing.Sm),
                    color = BpkTheme.colors.textSecondary,
                )

            is Prefix.Icon ->
                BpkIcon(
                    icon = prefix.icon,
                    contentDescription = null,
                    size = BpkIconSize.Large,
                    modifier = Modifier.padding(start = BpkSpacing.Sm),
                    tint = textFieldBoxTintColor,
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
                color = textFieldBoxTintColor,
                maxLines = maxLines,
                modifier = Modifier.hideContentIf(value.text.isNotEmpty()),
                style = BpkTheme.typography.bodyDefault,
                overflow = TextOverflow.Ellipsis,
            )

            textFieldContent()
        }

        TrailingIcon(trailingIcon, status, clearAction, value, type)
    }
}

@Composable
private fun RowScope.TrailingIcon(
    trailingIcon: BpkIcon?,
    status: BpkFieldStatus,
    clearAction: BpkClearAction?,
    value: TextFieldValue,
    type: BpkTextFieldType,
) {
    var lastIcon by remember { mutableStateOf<Icon?>(null) }
    val currentIcon = if (trailingIcon != null) {
        Icon(
            icon = trailingIcon,
            contentDescription = null,
            color = animateColorAsState(
                when (status) {
                    is BpkFieldStatus.Disabled -> BpkTheme.colors.textDisabled
                    else -> BpkTheme.colors.textPrimary
                },
                label = "Trailing icon color",
            ).value,
        )
    } else {
        when {
            status is BpkFieldStatus.Validated -> Icon(
                icon = BpkIcon.TickCircle,
                color = BpkTheme.colors.statusSuccessSpot,
            )

            status is BpkFieldStatus.Error -> Icon(
                icon = BpkIcon.ExclamationCircle,
                color = BpkTheme.colors.statusDangerSpot,
            )

            status is BpkFieldStatus.Default && clearAction != null && value.text.isNotEmpty() -> Icon(
                icon = BpkIcon.CloseCircle,
                contentDescription = clearAction.contentDescription,
                color = BpkTheme.colors.textSecondary,
                modifier = Modifier
                    .clickableWithRipple(bounded = false, role = Role.Button) {
                        clearAction.onClick()
                    }
                    .testTag("textFieldClearButton"),
            )

            else -> null
        }
    }
    if (currentIcon != null) {
        lastIcon = currentIcon
    }
    AnimatedVisibility(
        visible = currentIcon != null,
        enter = fadeIn() + scaleIn(),
        exit = scaleOut() + fadeOut(),
    ) {
        Crossfade(lastIcon!!.icon, label = "textFieldTrailingIcon") {
            val icon = lastIcon!!
            BpkIcon(
                icon = it,
                contentDescription = icon.contentDescription,
                size = if (type == BpkTextFieldType.Search) BpkIconSize.Large else BpkIconSize.Small,
                tint = icon.color,
                modifier = icon.modifier.padding(if (type == BpkTextFieldType.Search) BpkSpacing.Sm else 0.dp),
            )
        }
    }
}

internal enum class BpkTextFieldType {
    Default,
    Search,
    Select,
}

@Composable
private fun textFieldShape(
    type: BpkTextFieldType,
) = RoundedCornerShape(if (type == BpkTextFieldType.Search || type == BpkTextFieldType.Select) BpkBorderRadius.Md else BpkBorderRadius.Sm)

private data class Icon(
    val icon: BpkIcon,
    val color: Color,
    val contentDescription: String? = null,
    val modifier: Modifier = Modifier,
)
