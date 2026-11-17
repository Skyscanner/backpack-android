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
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.toggleable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.disabled
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.toggleableState
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.switch.internal.BpkSwitchImpl
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.compose.tokens.InformationCircle
import net.skyscanner.backpack.compose.utils.BpkToggleableContent
import net.skyscanner.backpack.compose.utils.applyIf
import net.skyscanner.backpack.compose.utils.suppressToggleableStateAnnouncement
import net.skyscanner.backpack.util.ExperimentalBackpackApi

enum class BpkSwitchStyle {
    Default,
    OnContrast,
}

@ExperimentalBackpackApi
data class BpkSwitchHelpAction(
    val contentDescription: String,
    val onClick: () -> Unit,
)

/**
 * @param announceState Whether TalkBack announces the checked state ("On"/"Off") before [text].
 * Set to false when the state is redundant or misleading in context.
 */
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
    announceState: Boolean = true,
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
        announceState = announceState,
        content = {
            TextWithSpacer(
                annotatedString = buildAnnotatedString { append(text) },
                shouldTruncate = shouldTruncate,
            )
        },
    )
}

@ExperimentalBackpackApi
@Composable
fun BpkSwitch(
    text: String,
    checked: Boolean,
    onCheckedChange: ((Boolean) -> Unit)?,
    helpAction: BpkSwitchHelpAction,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    style: BpkSwitchStyle = BpkSwitchStyle.Default,
    shouldTruncate: Boolean = true,
    switchAlignment: Alignment.Vertical = Alignment.CenterVertically,
    textStyle: TextStyle? = null,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    announceState: Boolean = true,
) {
    BpkSwitchWithHelpAction(
        checked = checked,
        onCheckedChange = onCheckedChange,
        helpAction = helpAction,
        modifier = modifier,
        enabled = enabled,
        style = style,
        switchAlignment = switchAlignment,
        textStyle = textStyle,
        interactionSource = interactionSource,
        announceState = announceState,
    ) {
        HelpLabelText(buildAnnotatedString { append(text) }, shouldTruncate)
    }
}

/**
 * @param announceState Whether TalkBack announces the checked state ("On"/"Off") before [text].
 * Set to false when the state is redundant or misleading in context.
 */
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
    announceState: Boolean = true,
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
        announceState = announceState,
        content = {
            TextWithSpacer(
                annotatedString = text,
                shouldTruncate = shouldTruncate,
            )
        },
    )
}

@ExperimentalBackpackApi
@Composable
fun BpkSwitch(
    text: AnnotatedString,
    checked: Boolean,
    onCheckedChange: ((Boolean) -> Unit)?,
    helpAction: BpkSwitchHelpAction,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    style: BpkSwitchStyle = BpkSwitchStyle.Default,
    shouldTruncate: Boolean = true,
    switchAlignment: Alignment.Vertical = Alignment.CenterVertically,
    textStyle: TextStyle? = null,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    announceState: Boolean = true,
) {
    BpkSwitchWithHelpAction(
        checked = checked,
        onCheckedChange = onCheckedChange,
        helpAction = helpAction,
        modifier = modifier,
        enabled = enabled,
        style = style,
        switchAlignment = switchAlignment,
        textStyle = textStyle,
        interactionSource = interactionSource,
        announceState = announceState,
    ) {
        HelpLabelText(text, shouldTruncate)
    }
}

/**
 * @param announceState Whether TalkBack announces the checked state ("On"/"Off") before
 * [content]. Set to false when the state is redundant or misleading in context.
 */
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
    announceState: Boolean = true,
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
                ).suppressToggleableStateAnnouncement(shouldSuppress = !announceState)
            }
            .applyIf(onCheckedChange == null) {
                semantics(mergeDescendants = true) {
                    role = Role.Switch
                    toggleableState = ToggleableState(checked)
                    if (!enabled) {
                        disabled()
                    }
                }.suppressToggleableStateAnnouncement(shouldSuppress = !announceState)
            },
    ) {

        BpkToggleableContent(
            enabled = enabled,
            onContrast = style == BpkSwitchStyle.OnContrast,
            textStyle = textStyle ?: BpkTheme.typography.footnote,
            content = { content(checked) },
        )

        BpkSwitchImpl(
            modifier = Modifier.align(switchAlignment),
            checked = checked,
            enabled = enabled,
            interactionSource = interactionSource,
            style = style,
        )
    }
}

@Composable
@OptIn(ExperimentalBackpackApi::class)
private fun BpkSwitchWithHelpAction(
    checked: Boolean,
    onCheckedChange: ((Boolean) -> Unit)?,
    helpAction: BpkSwitchHelpAction,
    modifier: Modifier,
    enabled: Boolean,
    style: BpkSwitchStyle,
    switchAlignment: Alignment.Vertical,
    textStyle: TextStyle?,
    interactionSource: MutableInteractionSource,
    announceState: Boolean,
    content: @Composable () -> Unit,
) {
    val currentChecked = rememberUpdatedState(checked)
    val currentOnCheckedChange = rememberUpdatedState(onCheckedChange)
    Layout(
        modifier = modifier,
        content = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .applyIf(onCheckedChange != null) {
                        toggleable(
                            value = checked,
                            role = Role.Switch,
                            interactionSource = interactionSource,
                            indication = null,
                            onValueChange = onCheckedChange!!,
                            enabled = enabled,
                        ).suppressToggleableStateAnnouncement(shouldSuppress = !announceState)
                    }
                    .applyIf(onCheckedChange == null) {
                        semantics(mergeDescendants = true) {
                            role = Role.Switch
                            toggleableState = ToggleableState(checked)
                            if (!enabled) disabled()
                        }.suppressToggleableStateAnnouncement(shouldSuppress = !announceState)
                    },
            ) {
                BpkToggleableContent(
                    enabled = enabled,
                    onContrast = style == BpkSwitchStyle.OnContrast,
                    textStyle = textStyle ?: BpkTheme.typography.footnote,
                    content = content,
                )
            }
            Box(
                modifier = Modifier
                    .size(HelpActionTouchTarget)
                    .clickable(role = Role.Button, onClick = helpAction.onClick)
                    .semantics { contentDescription = helpAction.contentDescription },
                contentAlignment = Alignment.CenterStart,
            ) {
                BpkIcon(
                    icon = BpkIcon.InformationCircle,
                    contentDescription = null,
                    tint = BpkTheme.colors.textSecondary,
                )
            }
            BpkSwitchImpl(
                modifier = Modifier
                    .applyIf(onCheckedChange != null) {
                        pointerInput(enabled) {
                            if (enabled) {
                                detectTapGestures { currentOnCheckedChange.value!!(!currentChecked.value) }
                            }
                        }
                    },
                checked = checked,
                enabled = enabled,
                interactionSource = interactionSource,
                style = style,
            )
        },
    ) { measurables, constraints ->
        val labelMeasurable = measurables[0]
        val helpMeasurable = measurables[1]
        val switchMeasurable = measurables[2]
        val switch = switchMeasurable
            .measure(constraints.copy(minWidth = 0, minHeight = 0))
        val help = helpMeasurable
            .measure(constraints.copy(minWidth = 0, minHeight = 0))
        val helpGap = BpkSpacing.Sm.roundToPx()
        val label = labelMeasurable
            .measure(
                constraints.copy(
                    minWidth = 0,
                    minHeight = 0,
                    maxWidth = (constraints.maxWidth - switch.width - help.width - helpGap).coerceAtLeast(0),
                ),
            )
        val contentWidth = label.width + helpGap + help.width + switch.width
        val width = (if (constraints.hasBoundedWidth) constraints.maxWidth else contentWidth)
            .coerceIn(constraints.minWidth, constraints.maxWidth)
        val height = maxOf(label.height, help.height, switch.height)
            .coerceIn(constraints.minHeight, constraints.maxHeight)
        layout(width, height) {
            label.placeRelative(0, (height - label.height) / 2)
            help.placeRelative(label.width + helpGap, (height - help.height) / 2)
            switch.placeRelative(width - switch.width, when (switchAlignment) {
                Alignment.Top -> 0
                Alignment.Bottom -> height - switch.height
                else -> (height - switch.height) / 2
            })
        }
    }
}

private val HelpActionTouchTarget = 48.dp

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

@Composable
private fun HelpLabelText(
    annotatedString: AnnotatedString,
    shouldTruncate: Boolean,
) {
    annotatedString.takeIf { it.isNotEmpty() }?.let {
        BpkText(
            text = annotatedString,
            maxLines = if (shouldTruncate) 1 else Int.MAX_VALUE,
            overflow = TextOverflow.Ellipsis,
        )
    }
}
