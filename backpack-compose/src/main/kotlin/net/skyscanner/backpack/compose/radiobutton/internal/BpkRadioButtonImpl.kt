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

package net.skyscanner.backpack.compose.radiobutton.internal

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalMinimumInteractiveComponentSize
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.invisibleToUser
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import net.skyscanner.backpack.compose.theme.BpkTheme

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
internal fun BpkRadioButtonImpl(
    selected: Boolean,
    onClick: (() -> Unit)?,
    enabled: Boolean,
    interactionSource: MutableInteractionSource,
    modifier: Modifier = Modifier,
) {
    // our design system isn't designed with the minimum touch target in mind at the moment.
    // Disable the enforcement to avoid the extra padding
    CompositionLocalProvider(LocalMinimumInteractiveComponentSize provides Dp.Unspecified) {
        RadioButton(
            selected = selected,
            onClick = onClick,
            modifier = modifier.semantics { invisibleToUser() },
            enabled = enabled,
            interactionSource = interactionSource,
            colors = RadioButtonDefaults.colors(
                selectedColor = BpkTheme.colors.coreAccent,
                unselectedColor = BpkTheme.colors.textSecondary,
                disabledSelectedColor = BpkTheme.colors.textDisabled,
                disabledUnselectedColor = BpkTheme.colors.textDisabled,
            ),
        )
    }
}
