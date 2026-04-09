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

package net.skyscanner.backpack.compose.switch.internal

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material3.LocalMinimumInteractiveComponentSize
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.hideFromAccessibility
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import net.skyscanner.backpack.compose.switch.BpkSwitchStyle
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.internal.BpkSwitchColors

@Composable
internal fun BpkSwitchImpl(
    checked: Boolean,
    onCheckedChange: ((Boolean) -> Unit)?,
    enabled: Boolean,
    interactionSource: MutableInteractionSource,
    modifier: Modifier = Modifier,
    style: BpkSwitchStyle = BpkSwitchStyle.Default,
) {
    // our design system isn't designed with the minimum touch target in mind at the moment.
    // Disable the enforcement to avoid the extra padding
    CompositionLocalProvider(LocalMinimumInteractiveComponentSize provides Dp.Unspecified) {
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            modifier = modifier.semantics { hideFromAccessibility() },
            enabled = enabled,
            interactionSource = interactionSource,
            colors = when (style) {
                BpkSwitchStyle.Default -> SwitchDefaults.colors(
                    checkedThumbColor = BpkTheme.colors.textPrimaryInverse,
                    checkedTrackColor = BpkTheme.colors.coreAccent,
                    checkedBorderColor = BpkTheme.colors.coreAccent,
                    checkedIconColor = Color.Transparent,
                    uncheckedThumbColor = BpkTheme.colors.textSecondary,
                    uncheckedTrackColor = BpkTheme.colors.surfaceDefault,
                    uncheckedBorderColor = BpkTheme.colors.textSecondary,
                    uncheckedIconColor = Color.Transparent,
                    disabledCheckedThumbColor = BpkSwitchColors.knobDisabled,
                    disabledCheckedTrackColor = BpkSwitchColors.defaultDisabled,
                    disabledCheckedBorderColor = Color.Transparent,
                    disabledCheckedIconColor = Color.Transparent,
                    disabledUncheckedThumbColor = BpkSwitchColors.defaultDisabled,
                    disabledUncheckedTrackColor = BpkTheme.colors.surfaceDefault,
                    disabledUncheckedBorderColor = BpkSwitchColors.defaultDisabled,
                    disabledUncheckedIconColor = Color.Transparent,
                )

                BpkSwitchStyle.OnContrast -> SwitchDefaults.colors(
                    checkedThumbColor = BpkTheme.colors.textPrimaryInverse,
                    checkedTrackColor = BpkTheme.colors.coreAccent,
                    checkedBorderColor = BpkTheme.colors.coreAccent,
                    checkedIconColor = Color.Transparent,
                    uncheckedThumbColor = BpkSwitchColors.onContrastOff,
                    uncheckedTrackColor = Color.Transparent,
                    uncheckedBorderColor = BpkSwitchColors.onContrastOff,
                    uncheckedIconColor = Color.Transparent,
                    disabledCheckedThumbColor = BpkSwitchColors.knobDisabled,
                    disabledCheckedTrackColor = BpkSwitchColors.onContrastFillDisabled,
                    disabledCheckedBorderColor = Color.Transparent,
                    disabledCheckedIconColor = Color.Transparent,
                    disabledUncheckedThumbColor = BpkSwitchColors.onContrastFillDisabled,
                    disabledUncheckedTrackColor = BpkTheme.colors.surfaceContrast,
                    disabledUncheckedBorderColor = BpkSwitchColors.onContrastFillDisabled,
                    disabledUncheckedIconColor = Color.Transparent,
                )
            },
        )
    }
}
