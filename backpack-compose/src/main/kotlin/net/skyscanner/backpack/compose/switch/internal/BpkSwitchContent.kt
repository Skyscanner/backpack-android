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

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.text.TextStyle
import net.skyscanner.backpack.compose.LocalContentColor
import net.skyscanner.backpack.compose.LocalTextStyle
import net.skyscanner.backpack.compose.theme.BpkTheme

@Composable
internal fun BpkSwitchContent(
    enabled: Boolean,
    onContrast: Boolean = false,
    textStyle: TextStyle = BpkTheme.typography.footnote,
    content: @Composable () -> Unit,
) {

    val contentColor = when {
        enabled && onContrast -> BpkTheme.colors.textOnDark
        enabled -> BpkTheme.colors.textPrimary
        onContrast -> BpkTheme.colors.textDisabledOnDark
        else -> BpkTheme.colors.textDisabled
    }

    CompositionLocalProvider(
        LocalContentColor provides contentColor,
        LocalTextStyle provides textStyle,
        content = content,
    )
}
