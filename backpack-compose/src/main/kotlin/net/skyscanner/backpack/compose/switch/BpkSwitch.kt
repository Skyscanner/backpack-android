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

package net.skyscanner.backpack.compose.switch

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.toggleable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.disabled
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.toggleableState
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import net.skyscanner.backpack.compose.switch.internal.BpkSwitchContent
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.switch.internal.BpkSwitchImpl
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.compose.utils.applyIf

enum class BpkSwitchStyle {
    Default,
    OnContrast,
}

@Composable
fun BpkSwitch(
    text: String,
    checked: Boolean,
    onCheckedChange: ((Boolean) -> Unit)?,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    style: BpkSwitchStyle = BpkSwitchStyle.Default,
    shouldTruncate: Boolean = true,
    switchAlignment: Alignment.Vertical = Alignment.CenterVertically,
    textStyle: TextStyle? = null,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    BpkSwitch(
        checked = checked,
        onCheckedChange = onCheckedChange,
        modifier = modifier,
        enabled = enabled,
        style = style,
        switchAlignment = switchAlignment,
        textStyle = textStyle,
        interactionSource = interactionSource,
        content = {
            TextWithSpacer(
                annotatedString = buildAnnotatedString { append(text) },
                shouldTruncate = shouldTruncate,
            )
        },
    )
}

@Composable
fun BpkSwitch(
    text: AnnotatedString,
    checked: Boolean,
    onCheckedChange: ((Boolean) -> Unit)?,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    style: BpkSwitchStyle = BpkSwitchStyle.Default,
    shouldTruncate: Boolean = true,
    switchAlignment: Alignment.Vertical = Alignment.CenterVertically,
    textStyle: TextStyle? = null,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    BpkSwitch(
        checked = checked,
        onCheckedChange = onCheckedChange,
        modifier = modifier,
        enabled = enabled,
        style = style,
        switchAlignment = switchAlignment,
        textStyle = textStyle,
        interactionSource = interactionSource,
        content = {
            TextWithSpacer(
                annotatedString = text,
                shouldTruncate = shouldTruncate,
            )
        },
    )
}

@Composable
fun BpkSwitch(
    checked: Boolean,
    onCheckedChange: ((Boolean) -> Unit)?,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    style: BpkSwitchStyle = BpkSwitchStyle.Default,
    switchAlignment: Alignment.Vertical = Alignment.CenterVertically,
    textStyle: TextStyle? = null,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable RowScope.(Boolean) -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .applyIf(onCheckedChange != null) {
                toggleable(
                    value = checked,
                    role = Role.Switch,
                    interactionSource = interactionSource,
                    indication = null,
                    onValueChange = onCheckedChange!!,
                    enabled = enabled,
                )
            }
            .applyIf(onCheckedChange == null) {
                semantics(mergeDescendants = true) {
                    role = Role.Switch
                    toggleableState = ToggleableState(checked)
                    if (!enabled) {
                        disabled()
                    }
                }
            },
    ) {

        BpkSwitchContent(
            enabled = enabled,
            onContrast = style == BpkSwitchStyle.OnContrast,
            textStyle = textStyle ?: BpkTheme.typography.footnote,
            content = { content(checked) },
        )

        BpkSwitchImpl(
            modifier = Modifier.align(switchAlignment),
            checked = checked,
            onCheckedChange = onCheckedChange,
            enabled = enabled,
            interactionSource = interactionSource,
            style = style,
        )
    }
}

@Composable
private fun RowScope.TextWithSpacer(
    annotatedString: AnnotatedString,
    shouldTruncate: Boolean,
) {
    takeIf { annotatedString.isNotEmpty() }?.let {
        BpkText(
            modifier = Modifier.weight(1f),
            text = annotatedString,
            maxLines = if (shouldTruncate) 1 else Int.MAX_VALUE,
            overflow = TextOverflow.Ellipsis,
        )
        Spacer(modifier = Modifier.width(BpkSpacing.Base))
    }
}
