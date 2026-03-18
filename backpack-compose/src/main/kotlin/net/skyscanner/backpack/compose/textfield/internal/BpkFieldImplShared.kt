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

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import net.skyscanner.backpack.compose.fieldset.BpkFieldStatus
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.icon.BpkIconSize
import net.skyscanner.backpack.compose.textfield.BpkClearAction
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.CloseCircle
import net.skyscanner.backpack.compose.tokens.ExclamationCircle
import net.skyscanner.backpack.compose.tokens.TickCircle
import net.skyscanner.backpack.compose.utils.clickableWithRipple

internal val BpkFieldMinHeight: Dp = 48.dp

@Composable
internal fun fieldTintColor(status: BpkFieldStatus): Color = animateColorAsState(
    when (status) {
        is BpkFieldStatus.Disabled -> BpkTheme.colors.textDisabled
        else -> BpkTheme.colors.textSecondary
    },
).value

@Composable
internal fun fieldBorderColor(status: BpkFieldStatus, isFocused: Boolean): Color = animateColorAsState(
    when {
        status is BpkFieldStatus.Disabled -> BpkTheme.colors.surfaceHighlight
        status is BpkFieldStatus.Error -> BpkTheme.colors.textError
        isFocused -> BpkTheme.colors.coreAccent
        else -> BpkTheme.colors.line
    },
).value

@Composable
private fun trailingIconTintColor(status: BpkFieldStatus): Color = animateColorAsState(
    when (status) {
        is BpkFieldStatus.Disabled -> BpkTheme.colors.textDisabled
        else -> BpkTheme.colors.textPrimary
    },
    label = "Trailing icon color",
).value

@Composable
internal fun RowScope.BpkFieldTrailingIcon(
    trailingIcon: BpkIcon?,
    status: BpkFieldStatus,
    clearAction: BpkClearAction?,
    hasValue: Boolean,
    iconSize: BpkIconSize,
    iconPadding: Dp = 0.dp,
) {
    var lastIcon by remember { mutableStateOf<FieldTrailingIconData?>(null) }
    val currentIcon = if (trailingIcon != null) {
        FieldTrailingIconData(
            icon = trailingIcon,
            contentDescription = null,
            color = trailingIconTintColor(status),
        )
    } else {
        when {
            status is BpkFieldStatus.Validated -> FieldTrailingIconData(
                icon = BpkIcon.TickCircle,
                color = BpkTheme.colors.statusSuccessSpot,
            )

            status is BpkFieldStatus.Error -> FieldTrailingIconData(
                icon = BpkIcon.ExclamationCircle,
                color = BpkTheme.colors.statusDangerSpot,
            )

            status is BpkFieldStatus.Default && clearAction != null && hasValue -> FieldTrailingIconData(
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
                size = iconSize,
                tint = icon.color,
                modifier = if (iconPadding > 0.dp) {
                    icon.modifier.padding(iconPadding)
                } else {
                    icon.modifier
                },
            )
        }
    }
}

private data class FieldTrailingIconData(
    val icon: BpkIcon,
    val color: Color,
    val contentDescription: String? = null,
    val modifier: Modifier = Modifier,
)
