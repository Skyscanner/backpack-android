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

package net.skyscanner.backpack.compose.checkbox

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.triStateToggleable
import net.skyscanner.backpack.compose.checkbox.internal.BpkCheckboxImpl
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.text.style.LineHeightStyle
import net.skyscanner.backpack.compose.LocalTextStyle
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.compose.utils.BpkToggleableContent
import net.skyscanner.backpack.compose.utils.applyIf

enum class BpkCheckboxPosition {
    Start,
    End,
}

@Composable
fun BpkCheckbox(
    text: String,
    checked: Boolean,
    onCheckedChange: ((Boolean) -> Unit)?,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    checkboxPosition: BpkCheckboxPosition = BpkCheckboxPosition.Start,
    icon: BpkIcon? = null,
    iconContentDescription: String? = null,
    onIconClick: (() -> Unit)? = null,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    BpkCheckbox(
        checked = checked,
        onCheckedChange = onCheckedChange,
        interactionSource = interactionSource,
        enabled = enabled,
        modifier = modifier,
        checkboxPosition = checkboxPosition,
        content = {
            CheckboxLabel(
                text = text,
                enabled = enabled,
                icon = icon,
                iconContentDescription = iconContentDescription,
                onIconClick = onIconClick,
            )
        },
    )
}

@Composable
fun BpkCheckbox(
    text: String,
    state: ToggleableState,
    onClick: (() -> Unit)?,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    checkboxPosition: BpkCheckboxPosition = BpkCheckboxPosition.Start,
    icon: BpkIcon? = null,
    iconContentDescription: String? = null,
    onIconClick: (() -> Unit)? = null,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    BpkCheckbox(
        state = state,
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        interactionSource = interactionSource,
        checkboxPosition = checkboxPosition,
        content = {
            CheckboxLabel(
                text = text,
                enabled = enabled,
                icon = icon,
                iconContentDescription = iconContentDescription,
                onIconClick = onIconClick,
            )
        },
    )
}

@Composable
fun BpkCheckbox(
    checked: Boolean,
    onCheckedChange: ((Boolean) -> Unit)?,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    checkboxPosition: BpkCheckboxPosition = BpkCheckboxPosition.Start,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable RowScope.(Boolean) -> Unit,
) {
    BpkCheckbox(
        state = ToggleableState(checked),
        onClick = if (onCheckedChange != null) {
            { onCheckedChange(!checked) }
        } else null,
        interactionSource = interactionSource,
        enabled = enabled,
        modifier = modifier,
        checkboxPosition = checkboxPosition,
        content = { content(checked) },
    )
}

@Composable
fun BpkCheckbox(
    state: ToggleableState,
    onClick: (() -> Unit)?,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    checkboxPosition: BpkCheckboxPosition = BpkCheckboxPosition.Start,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable RowScope.(ToggleableState) -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier.applyIf(onClick != null) {
            triStateToggleable(
                state = state,
                interactionSource = interactionSource,
                indication = null,
                enabled = enabled,
                role = Role.Checkbox,
                onClick = onClick!!,
            )
        },
    ) {
        if (checkboxPosition == BpkCheckboxPosition.Start) {
            BpkCheckboxImpl(
                modifier = Modifier.padding(end = BpkSpacing.Md),
                state = state,
                enabled = enabled,
                interactionSource = interactionSource,
                onClick = onClick,
            )
        }

        BpkToggleableContent(
            enabled = enabled,
            content = { content(state) },
        )

        if (checkboxPosition == BpkCheckboxPosition.End) {
            BpkCheckboxImpl(
                modifier = Modifier.padding(start = BpkSpacing.Md),
                state = state,
                enabled = enabled,
                interactionSource = interactionSource,
                onClick = onClick,
            )
        }
    }
}

@Composable
private fun RowScope.CheckboxLabel(
    text: String,
    enabled: Boolean,
    icon: BpkIcon?,
    iconContentDescription: String?,
    onIconClick: (() -> Unit)?,
) {
    // Without an icon, render the label unchanged to preserve the layout existing
    // consumers already depend on.
    if (icon == null) {
        BpkText(text)
        return
    }

    Row(
        // Constrain to the space left by the checkbox so long labels wrap instead of
        // growing under the icon.
        modifier = Modifier.weight(1f),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(BpkSpacing.Sm),
    ) {
        // Trim the line-height leading so the label's box hugs its glyphs, keeping the
        // text visually centred against the icon rather than riding high in its line box.
        BpkText(
            modifier = Modifier.weight(1f, fill = false),
            text = text,
            style = LocalTextStyle.current.copy(
                lineHeightStyle = LineHeightStyle(
                    alignment = LineHeightStyle.Alignment.Center,
                    trim = LineHeightStyle.Trim.Both,
                ),
            ),
        )

        BpkIcon(
            icon = icon,
            contentDescription = iconContentDescription,
            modifier = Modifier.applyIf(enabled && onIconClick != null) {
                clickable(onClick = onIconClick!!)
            },
        )
    }
}