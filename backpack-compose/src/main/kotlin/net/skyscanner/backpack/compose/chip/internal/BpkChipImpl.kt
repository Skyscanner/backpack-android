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

package net.skyscanner.backpack.compose.chip.internal

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import net.skyscanner.backpack.compose.chip.BpkChipStyle
import net.skyscanner.backpack.compose.chip.BpkChipType
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.icon.BpkIconSize
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.theme.bpkRipple
import net.skyscanner.backpack.compose.tokens.BpkBorderRadius
import net.skyscanner.backpack.compose.tokens.BpkBorderSize
import net.skyscanner.backpack.compose.tokens.BpkElevation
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.compose.tokens.ChevronDown
import net.skyscanner.backpack.compose.tokens.CloseCircle
import net.skyscanner.backpack.compose.tokens.internal.BpkChipColors
import net.skyscanner.backpack.compose.utils.animateAsColor
import net.skyscanner.backpack.compose.utils.applyIf

@Composable
internal fun BpkChipImpl(
    text: String?,
    type: BpkChipType,
    selected: Boolean,
    onSelectedChange: ((Boolean) -> Unit)?,
    enabled: Boolean,
    style: BpkChipStyle,
    icon: BpkIcon?,
    modifier: Modifier = Modifier,
) {
    val interactionSource = remember { MutableInteractionSource() }
    BpkChipImpl(
        text = text,
        selected = selected,
        enabled = enabled,
        style = style,
        icon = icon,
        type = type,
        interactionSource = interactionSource,
        modifier = modifier.applyIf(onSelectedChange != null) {
            clip(ChipShape)
                .selectable(
                    selected = selected,
                    enabled = enabled,
                    interactionSource = interactionSource,
                    indication = bpkRipple(),
                    role = Role.Button,
                ) { onSelectedChange!!.invoke(!selected) }
        },
    )
}

@Composable
internal fun BpkDismissibleChipImpl(
    text: String?,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    style: BpkChipStyle = BpkChipStyle.Default,
    icon: BpkIcon? = null,
) {
    val interactionSource = remember { MutableInteractionSource() }
    BpkChipImpl(
        text = text,
        selected = true,
        enabled = true,
        style = style,
        icon = icon,
        type = BpkChipType.Dismiss,
        interactionSource = interactionSource,
        modifier = modifier.applyIf(onClick != null) {
            clip(ChipShape)
                .clickable(
                    interactionSource = interactionSource,
                    indication = bpkRipple(),
                ) { onClick!!.invoke() }
        },
    )
}

@Composable
internal fun BpkChipImpl(
    text: String?,
    selected: Boolean,
    enabled: Boolean,
    style: BpkChipStyle,
    icon: BpkIcon?,
    type: BpkChipType,
    interactionSource: MutableInteractionSource,
    modifier: Modifier = Modifier,
) {
    val backgroundColor by animateColorAsState(
        targetValue = when {
            !enabled -> BpkChipColors.disabledBackground
            selected -> style.selectedBackgroundColor
            else -> interactionSource.animateAsColor(
                default = style.backgroundColor,
                pressed = style.pressedBackgroundColor,
            )
        },
    )

    val strokeColor by animateColorAsState(
        targetValue = when {
            !enabled || selected -> Color.Transparent
            else -> interactionSource.animateAsColor(
                default = style.strokeColor,
                pressed = style.pressedStrokeColor,
            )
        },
    )

    val contentColor by animateColorAsState(
        targetValue = when {
            !enabled -> BpkTheme.colors.textDisabled
            selected -> style.selectedContentColor
            else -> style.contentColor
        },
    )

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(BpkSpacing.Md),
        modifier = modifier
            .height(BpkSpacing.Xl)
            .border(BorderStroke(BpkBorderSize.Sm, strokeColor), ChipShape)
            .shadow(if (style == BpkChipStyle.OnImage) BpkElevation.Sm else 0.dp, ChipShape)
            .background(backgroundColor, ChipShape)
            .clip(ChipShape)
            .padding(horizontal = BpkSpacing.Md),
    ) {

        if (text != null) {
            // due to the vertical spacing this ends up with 8dp again
            Spacer(modifier = Modifier.width(0.dp))
        }

        if (icon != null) {
            BpkIcon(
                icon = icon,
                size = BpkIconSize.Small,
                contentDescription = null,
                tint = contentColor,
            )
        }
        text?.let {
            BpkText(
                text = text,
                color = contentColor,
                style = BpkTheme.typography.footnote,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
        val trailingIcon = type.icon
        if (trailingIcon != null) {
            val trailingIconColor by animateColorAsState(
                targetValue = when {
                    !enabled -> BpkTheme.colors.textDisabled
                    type == BpkChipType.Dismiss -> interactionSource.animateAsColor(
                        default = style.dismissibleTrailingIconColor,
                        pressed = style.dismissibleTrailingIconPressedColor,
                    )

                    selected -> style.selectedContentColor
                    else -> interactionSource.animateAsColor(
                        default = style.contentColor,
                        pressed = BpkTheme.colors.textPrimary,
                    )
                },
            )

            BpkIcon(
                icon = trailingIcon,
                size = BpkIconSize.Small,
                contentDescription = null,
                tint = trailingIconColor,
            )
        } else if (text != null) {
            // due to the vertical spacing this ends up with 8dp again
            Spacer(modifier = Modifier.width(0.dp))
        }
    }
}

private val BpkChipStyle.selectedBackgroundColor: Color
    @Composable
    get() = when (this) {
        BpkChipStyle.Default, BpkChipStyle.OnImage -> BpkTheme.colors.corePrimary
        BpkChipStyle.OnDark -> BpkChipColors.onDarkOnBackground
    }

private val BpkChipStyle.pressedBackgroundColor: Color
    @Composable
    get() = when (this) {
        BpkChipStyle.Default, BpkChipStyle.OnDark -> Color.Transparent
        BpkChipStyle.OnImage -> BpkTheme.colors.canvasContrast
    }

private val BpkChipStyle.backgroundColor: Color
    @Composable
    get() = when (this) {
        BpkChipStyle.Default, BpkChipStyle.OnDark -> Color.Transparent
        BpkChipStyle.OnImage -> BpkTheme.colors.surfaceDefault
    }

private val BpkChipStyle.pressedStrokeColor: Color
    @Composable
    get() = when (this) {
        BpkChipStyle.Default -> BpkTheme.colors.corePrimary
        BpkChipStyle.OnDark -> BpkChipColors.onDarkPressedStroke
        BpkChipStyle.OnImage -> Color.Transparent
    }

private val BpkChipStyle.strokeColor: Color
    @Composable
    get() = when (this) {
        BpkChipStyle.Default -> BpkTheme.colors.line
        BpkChipStyle.OnDark -> BpkTheme.colors.lineOnDark
        BpkChipStyle.OnImage -> Color.Transparent
    }

private val BpkChipStyle.selectedContentColor: Color
    @Composable
    get() = when (this) {
        BpkChipStyle.Default, BpkChipStyle.OnImage -> BpkTheme.colors.textOnDark
        BpkChipStyle.OnDark -> BpkTheme.colors.textPrimary
    }

private val BpkChipStyle.contentColor: Color
    @Composable
    get() = when (this) {
        BpkChipStyle.Default, BpkChipStyle.OnImage -> BpkTheme.colors.textPrimary
        BpkChipStyle.OnDark -> BpkTheme.colors.textOnDark
    }

private val BpkChipStyle.dismissibleTrailingIconColor: Color
    @Composable
    get() = when (this) {
        BpkChipStyle.Default, BpkChipStyle.OnImage -> BpkTheme.colors.textDisabledOnDark
        BpkChipStyle.OnDark -> BpkChipColors.onDarkOnDismissIcon
    }

private val BpkChipStyle.dismissibleTrailingIconPressedColor: Color
    @Composable
    get() = when (this) {
        BpkChipStyle.Default, BpkChipStyle.OnImage -> BpkTheme.colors.textOnDark
        BpkChipStyle.OnDark -> BpkTheme.colors.textPrimary
    }

private val BpkChipType.icon: BpkIcon?
    get() = when (this) {
        BpkChipType.Selectable -> null
        BpkChipType.Dropdown -> BpkIcon.ChevronDown
        BpkChipType.Dismiss -> BpkIcon.CloseCircle
    }

private val ChipShape = RoundedCornerShape(BpkBorderRadius.Sm)
