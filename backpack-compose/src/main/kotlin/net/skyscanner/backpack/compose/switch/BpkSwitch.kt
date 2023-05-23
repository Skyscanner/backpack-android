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

package net.skyscanner.backpack.compose.switch

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalMinimumTouchTargetEnforcement
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.invisibleToUser
import androidx.compose.ui.semantics.semantics
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.utils.BpkToggleableContent
import net.skyscanner.backpack.compose.utils.applyIf

@Composable
fun BpkSwitch(
    text: String,
    checked: Boolean,
    onCheckedChange: ((Boolean) -> Unit)?,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    BpkSwitch(
        checked = checked,
        onCheckedChange = onCheckedChange,
        modifier = modifier,
        enabled = enabled,
        interactionSource = interactionSource,
        content = { BpkText(text) },
    )
}

@Composable
fun BpkSwitch(
    checked: Boolean,
    onCheckedChange: ((Boolean) -> Unit)?,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable RowScope.(Boolean) -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier.applyIf(onCheckedChange != null) {
            toggleable(
                value = checked,
                role = Role.Switch,
                interactionSource = interactionSource,
                indication = null,
                onValueChange = onCheckedChange!!,
            )
        },
    ) {

        BpkToggleableContent(
            enabled = enabled,
            content = { content(checked) },
        )

        BpkSwitchImpl(
            checked = checked,
            onCheckedChange = onCheckedChange,
            enabled = enabled,
            interactionSource = interactionSource,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
private fun BpkSwitchImpl(
    checked: Boolean,
    onCheckedChange: ((Boolean) -> Unit)?,
    enabled: Boolean,
    interactionSource: MutableInteractionSource,
    modifier: Modifier = Modifier,
) {
    // our design system isn't designed with the minimum touch target in mind at the moment.
    // Disable the enforcement to avoid the extra padding
    CompositionLocalProvider(LocalMinimumTouchTargetEnforcement provides false) {
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            modifier = modifier.semantics { invisibleToUser() },
            enabled = enabled,
            interactionSource = interactionSource,
            colors = SwitchDefaults.colors(
                checkedThumbColor = BpkTheme.colors.textPrimaryInverse,
                checkedTrackColor = BpkTheme.colors.coreAccent,
                checkedBorderColor = BpkTheme.colors.coreAccent,
                checkedIconColor = Color.Transparent,
                uncheckedThumbColor = BpkTheme.colors.textSecondary,
                uncheckedTrackColor = BpkTheme.colors.canvasContrast,
                uncheckedBorderColor = BpkTheme.colors.textSecondary,
                uncheckedIconColor = Color.Transparent,
                disabledCheckedThumbColor = BpkTheme.colors.textDisabled,
                disabledCheckedTrackColor = BpkTheme.colors.textDisabled,
                disabledCheckedBorderColor = BpkTheme.colors.textDisabled,
                disabledCheckedIconColor = Color.Transparent,
                disabledUncheckedThumbColor = BpkTheme.colors.textDisabled,
                disabledUncheckedTrackColor = BpkTheme.colors.textDisabled,
                disabledUncheckedBorderColor = BpkTheme.colors.textDisabled,
                disabledUncheckedIconColor = Color.Transparent,
            ),
        )
    }
}
