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

package net.skyscanner.backpack.compose.navigationtabgroup.internal

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.icon.BpkIconSize
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkBorderSize
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.compose.tokens.internal.BpkNavigationTabColors
import net.skyscanner.backpack.compose.utils.animateAsColor
import net.skyscanner.backpack.compose.utils.applyIf

internal enum class BpkNavigationTabStyle {
    CanvasDefault,
    SurfaceContrast,
}

@Composable
internal fun BpkNavigationTab(
    text: String,
    onClick: (() -> Unit)?,
    selected: Boolean,
    modifier: Modifier = Modifier,
    style: BpkNavigationTabStyle = BpkNavigationTabStyle.CanvasDefault,
    icon: BpkIcon? = null,
) {
    val interactionSource = remember { MutableInteractionSource() }
    BpkNavigationTabImpl(
        text = text,
        selected = selected,
        style = style,
        icon = icon,
        interactionSource = interactionSource,
        modifier = modifier.applyIf(onClick != null) {
            clip(CircleShape)
                .selectable(
                    selected = selected,
                    interactionSource = interactionSource,
                    indication = rememberRipple(),
                ) { onClick!!.invoke() }
        },
    )
}

@Composable
private fun BpkNavigationTabImpl(
    text: String,
    selected: Boolean,
    style: BpkNavigationTabStyle,
    icon: BpkIcon?,
    interactionSource: MutableInteractionSource,
    modifier: Modifier = Modifier,
) {
    val backgroundColor by animateColorAsState(
        targetValue = when {
            selected -> BpkTheme.colors.coreAccent
            else -> interactionSource.animateAsColor(
                default = Color.Transparent,
                pressed = style.pressedBackgroundColor,
            )
        },
        label = "",
    )

    val strokeColor by animateColorAsState(
        targetValue = when {
            selected -> BpkTheme.colors.coreAccent
            else -> interactionSource.animateAsColor(
                default = style.strokeColor,
                pressed = style.pressedStrokeColor,
            )
        },
        label = "",
    )

    val contentColor by animateColorAsState(
        targetValue = when {
            selected -> BpkTheme.colors.textPrimaryInverse
            else -> interactionSource.animateAsColor(
                default = style.contentColor,
                pressed = style.pressedContentColor,
            )
        },
        label = "",
    )

    val navigationTabHeight = 36.dp

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .height(navigationTabHeight)
            .clip(CircleShape)
            .background(backgroundColor)
            .border(BorderStroke(BpkBorderSize.Sm, strokeColor), CircleShape)
            .padding(horizontal = BpkSpacing.Base),
    ) {
        if (icon != null) {
            BpkIcon(
                icon = icon,
                size = BpkIconSize.Small,
                contentDescription = null,
                tint = contentColor,
            )
        }

        BpkText(
            text = text,
            color = contentColor,
            style = BpkTheme.typography.label2,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(start = if (icon != null) BpkSpacing.Md else 0.dp),
        )
    }
}

private val BpkNavigationTabStyle.pressedBackgroundColor: Color
    @Composable
    get() = when (this) {
        BpkNavigationTabStyle.CanvasDefault -> Color.Transparent
        BpkNavigationTabStyle.SurfaceContrast -> BpkNavigationTabColors.hover
    }

private val BpkNavigationTabStyle.strokeColor: Color
    @Composable
    get() = when (this) {
        BpkNavigationTabStyle.CanvasDefault -> BpkNavigationTabColors.outline
        BpkNavigationTabStyle.SurfaceContrast -> BpkTheme.colors.textOnDark
    }

private val BpkNavigationTabStyle.pressedStrokeColor: Color
    @Composable
    get() = when (this) {
        BpkNavigationTabStyle.CanvasDefault -> BpkTheme.colors.textPrimary
        BpkNavigationTabStyle.SurfaceContrast -> BpkNavigationTabColors.hover
    }

private val BpkNavigationTabStyle.contentColor: Color
    @Composable
    get() = when (this) {
        BpkNavigationTabStyle.CanvasDefault -> BpkTheme.colors.textPrimary
        BpkNavigationTabStyle.SurfaceContrast -> BpkTheme.colors.textOnDark
    }

private val BpkNavigationTabStyle.pressedContentColor: Color
    @Composable
    get() = when (this) {
        BpkNavigationTabStyle.CanvasDefault -> BpkTheme.colors.textPrimary
        BpkNavigationTabStyle.SurfaceContrast -> BpkTheme.colors.textPrimaryInverse
    }
