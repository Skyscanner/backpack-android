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
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkBorderRadius
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.compose.tokens.CloseCircle
import net.skyscanner.backpack.compose.tokens.ExclamationCircle
import net.skyscanner.backpack.compose.tokens.TickCircle
import net.skyscanner.backpack.compose.utils.clickable
import net.skyscanner.backpack.compose.utils.hideContentIf

@Composable
internal fun BpkTextFieldImpl(
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
    minLines: Int = 1,
    maxLines: Int = 1,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    trailingIcon: BpkIcon? = null,
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
        icon = icon,
        status = status,
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        minLines = minLines,
        maxLines = maxLines,
        interactionSource = interactionSource,
        trailingIcon = trailingIcon,
    )
}

@Composable
internal fun BpkTextFieldImpl(
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
    minLines: Int = 1,
    maxLines: Int = 1,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    trailingIcon: BpkIcon? = null,
) {
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
                icon = icon,
                status = status,
                maxLines = maxLines,
                interactionSource = interactionSource,
                trailingIcon = trailingIcon,
                textFieldContent = it,
                readOnly = readOnly,
            )
        },
    )
}

@Composable
private fun TextFieldBox(
    value: TextFieldValue,
    modifier: Modifier = Modifier,
    placeholder: String? = null,
    icon: BpkIcon? = null,
    status: BpkFieldStatus = LocalFieldStatus.current,
    maxLines: Int = 1,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    trailingIcon: BpkIcon? = null,
    readOnly: Boolean = false,
    textFieldContent: @Composable () -> Unit,
) {
    val isFocused by interactionSource.collectIsFocusedAsState()

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .width(IntrinsicSize.Max)
            .requiredHeightIn(min = BpkSpacing.Xxl + BpkSpacing.Md)
            .border(
                width = 1.dp, shape = Shape,
                color = animateColorAsState(
                    when {
                        status is BpkFieldStatus.Disabled -> BpkTheme.colors.surfaceHighlight
                        status is BpkFieldStatus.Error -> BpkTheme.colors.textError
                        isFocused -> BpkTheme.colors.coreAccent
                        else -> BpkTheme.colors.line
                    },
                ).value,
            )
            .background(BpkTheme.colors.surfaceDefault, Shape)
            .padding(horizontal = BpkSpacing.Md),
    ) {

        if (icon != null) {
            BpkIcon(
                icon = icon,
                contentDescription = null,
                size = BpkIconSize.Large,
                modifier = Modifier.padding(start = BpkSpacing.Sm),
                tint = animateColorAsState(
                    when (status) {
                        is BpkFieldStatus.Disabled -> BpkTheme.colors.textDisabled
                        else -> BpkTheme.colors.textSecondary
                    },
                ).value,
            )
        }

        Box(
            modifier = Modifier
                .weight(1f)
                .padding(BpkSpacing.Md),
        ) {

            BpkText(
                text = placeholder ?: "",
                color = BpkTheme.colors.textDisabled,
                maxLines = maxLines,
                modifier = Modifier.hideContentIf(value.text.isNotEmpty()),
                style = BpkTheme.typography.bodyDefault,
                overflow = TextOverflow.Ellipsis,
            )

            textFieldContent()
        }

        var lastIcon by remember { mutableStateOf<Pair<BpkIcon, Color>?>(null) }
        if (trailingIcon != null) {
            BpkIcon(
                icon = trailingIcon,
                contentDescription = null,
                size = BpkIconSize.Small,
                tint = animateColorAsState(
                    when (status) {
                        is BpkFieldStatus.Disabled -> BpkTheme.colors.textDisabled
                        else -> BpkTheme.colors.textPrimary
                    },
                ).value,
            )
        } else if (status is BpkFieldStatus.Clear && !readOnly && value.text.isNotEmpty()) {
            BpkIcon(
                icon = BpkIcon.CloseCircle,
                contentDescription = status.action.contentDescription,
                size = BpkIconSize.Small,
                tint = BpkTheme.colors.textSecondary,
                modifier = Modifier
                    .clickable(bounded = false, role = Role.Button) {
                        status.action.onClick()
                    }
                    .testTag("textFieldClearButton"),
            )
        } else {
            when (status) {
                is BpkFieldStatus.Validated -> lastIcon = Pair(BpkIcon.TickCircle, BpkTheme.colors.statusSuccessSpot)
                is BpkFieldStatus.Error -> lastIcon = Pair(BpkIcon.ExclamationCircle, BpkTheme.colors.statusDangerSpot)
                else -> Unit // do nothing
            }
            AnimatedVisibility(
                visible = status is BpkFieldStatus.Validated || status is BpkFieldStatus.Error,
                enter = fadeIn() + scaleIn(),
                exit = scaleOut() + fadeOut(),
            ) {
                lastIcon?.let {
                    Crossfade(it) { (icon, color) ->
                        BpkIcon(
                            icon = icon,
                            contentDescription = null,
                            size = BpkIconSize.Large,
                            tint = color,
                        )
                    }
                }
            }
        }
    }
}

private val Shape = RoundedCornerShape(BpkBorderRadius.Sm)
