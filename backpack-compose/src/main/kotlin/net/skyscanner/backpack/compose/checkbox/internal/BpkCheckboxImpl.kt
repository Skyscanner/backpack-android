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

package net.skyscanner.backpack.compose.checkbox.internal

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalMinimumInteractiveComponentSize
import androidx.compose.material3.TriStateCheckbox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.semantics.invisibleToUser
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.unit.Dp
import net.skyscanner.backpack.compose.theme.BpkTheme

@OptIn(ExperimentalMaterial3Api::class, androidx.compose.ui.ExperimentalComposeUiApi::class)
@Composable
internal fun BpkCheckboxImpl(
    state: ToggleableState,
    onClick: (() -> Unit)?,
    enabled: Boolean,
    interactionSource: MutableInteractionSource,
    modifier: Modifier = Modifier,
) {
    // our design system isn't designed with the minimum touch target in mind at the moment.
    // Disable the enforcement to avoid the extra padding
    CompositionLocalProvider(LocalMinimumInteractiveComponentSize provides Dp.Unspecified) {
        TriStateCheckbox(
            state = state,
            onClick = onClick,
            enabled = enabled,
            modifier = modifier.scale(BackpackCheckboxScale).semantics { invisibleToUser() },
            interactionSource = interactionSource,
            colors = CheckboxDefaults.colors(
                checkedColor = BpkTheme.colors.coreAccent,
                uncheckedColor = BpkTheme.colors.textSecondary,
                checkmarkColor = BpkTheme.colors.textPrimaryInverse,
                disabledCheckedColor = BpkTheme.colors.textDisabled,
                disabledUncheckedColor = BpkTheme.colors.textDisabled,
                disabledIndeterminateColor = BpkTheme.colors.textDisabled,
            ),
        )
    }
}

private const val BackpackCheckboxScale = 0.89f
