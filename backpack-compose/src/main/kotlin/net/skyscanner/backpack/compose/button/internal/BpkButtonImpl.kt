/**
 * Backpack for Android - Skyscanner's Design System
 *
 * Copyright 2018 - 2025 Skyscanner Ltd
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

package net.skyscanner.backpack.compose.button.internal

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalRippleConfiguration
import androidx.compose.material3.RippleConfiguration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import net.skyscanner.backpack.compose.LocalContentColor
import net.skyscanner.backpack.compose.LocalTextStyle
import net.skyscanner.backpack.compose.button.BpkButtonSize
import net.skyscanner.backpack.compose.button.BpkButtonType
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.icon.BpkIconSize
import net.skyscanner.backpack.compose.spinner.BpkSpinner
import net.skyscanner.backpack.compose.spinner.BpkSpinnerSize
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkBorderRadius
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.compose.utils.hideContentIf
import net.skyscanner.backpack.compose.utils.toRippleAlpha
import net.skyscanner.backpack.configuration.BpkConfiguration

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun BpkButtonImpl(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    size: BpkButtonSize = BpkButtonSize.Default,
    type: BpkButtonType = BpkButtonType.Primary,
    enabled: Boolean = true,
    loading: Boolean = false,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    contentDescription: String? = null,
    content: @Composable RowScope.() -> Unit,
) {
    val isInteractionEnabled = enabled && !loading
    val rippleConfiguration = createRippleConfiguration(type)
    val buttonModifier = modifier
        .defaultButtonSize(size)
        .applyAccessibilityOverride(
            contentDescription = contentDescription,
            enabled = isInteractionEnabled,
            onClick = onClick,
        )
    val buttonColors = rememberButtonColors(type, loading, interactionSource)

    CompositionLocalProvider(LocalRippleConfiguration provides rippleConfiguration) {
        Button(
            onClick = onClick,
            enabled = isInteractionEnabled,
            modifier = buttonModifier,
            interactionSource = interactionSource,
            colors = buttonColors,
            shape = buttonShape(),
            contentPadding = type.contentPadding(size),
            elevation = null,
            content = {
                ButtonContent(
                    loading = loading,
                    size = size,
                    content = content,
                )
            },
        )
    }
}

@Composable
private fun ButtonContent(
    loading: Boolean,
    size: BpkButtonSize,
    content: @Composable RowScope.() -> Unit,
) {
    CompositionLocalProvider(LocalTextStyle provides BpkTheme.typography.label1) {
        Box {
            Row(
                modifier = Modifier.hideContentIf(loading),
                horizontalArrangement = Arrangement.spacedBy(size.horizontalSpacing),
                verticalAlignment = Alignment.CenterVertically,
                content = content,
            )

            if (loading) {
                LoadingSpinner(size)
            }
        }
    }
}

@Composable
private fun BoxScope.LoadingSpinner(size: BpkButtonSize) {
    val spinnerSize = when (size) {
        BpkButtonSize.Default -> BpkSpinnerSize.Small
        BpkButtonSize.Large -> BpkSpinnerSize.Large
    }

    BpkSpinner(
        modifier = Modifier.align(Alignment.Center),
        color = LocalContentColor.current,
        size = spinnerSize,
    )
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun createRippleConfiguration(type: BpkButtonType): RippleConfiguration {
    val rippleColor = type.rippleColor()
    return RippleConfiguration(rippleColor, rippleColor.toRippleAlpha())
}

private fun Modifier.defaultButtonSize(size: BpkButtonSize): Modifier =
    defaultMinSize(BpkSpacing.Sm, size.minHeight)
        .requiredHeight(size.minHeight)

private fun Modifier.applyAccessibilityOverride(
    contentDescription: String?,
    enabled: Boolean,
    onClick: () -> Unit,
): Modifier {
    return if (contentDescription != null) {
        this.then(
            Modifier
                .clickable(
                    enabled = enabled,
                    onClick = onClick,
                    role = Role.Button,
                )
                .clearAndSetSemantics {
                    this.contentDescription = contentDescription
                },
        )
    } else {
        this
    }
}

@Composable
private fun rememberButtonColors(
    type: BpkButtonType,
    loading: Boolean,
    interactionSource: MutableInteractionSource,
) = ButtonDefaults.buttonColors(
    containerColor = type.backgroundColor(interactionSource),
    contentColor = type.contentColor(interactionSource),
    disabledContainerColor = if (loading) {
        type.loadingBackgroundColor()
    } else {
        type.disabledBackgroundColor()
    },
    disabledContentColor = if (loading) {
        type.loadingContentColor()
    } else {
        type.disabledContentColor()
    },
)

@Composable
internal fun ButtonIcon(
    icon: BpkIcon,
    contentDescription: String?,
    size: BpkButtonSize,
    modifier: Modifier = Modifier,
) {
    BpkIcon(icon, contentDescription, size = size.iconSize, modifier = modifier)
}

@Composable
internal fun ButtonText(
    text: String,
    type: BpkButtonType,
    modifier: Modifier = Modifier,
) {
    val isLink = type == BpkButtonType.Link || type == BpkButtonType.LinkOnDark
    BpkText(
        text = text,
        modifier = modifier,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        style = if (isLink) {
            LocalTextStyle.current.copy(textDecoration = TextDecoration.Underline)
        } else {
            LocalTextStyle.current
        },
    )
}

@Composable
internal fun ButtonDrawable(
    icon: Painter,
    contentDescription: String?,
    size: BpkButtonSize,
    modifier: Modifier = Modifier,
) {
    val iconSize = size.iconSize
    Image(
        painter = icon,
        contentDescription = contentDescription,
        modifier = modifier.defaultIconSize(iconSize),
    )
}

private fun buttonShape() = BpkConfiguration.buttonConfig?.cornerRadius?.let {
    RoundedCornerShape(it)
} ?: RoundedCornerShape(BpkBorderRadius.Sm)

private fun Modifier.defaultIconSize(size: BpkIconSize): Modifier =
    when (size) {
        BpkIconSize.Small -> this.requiredSize(BpkSpacing.Base, BpkSpacing.Base)
        BpkIconSize.Large -> this.requiredSize(BpkSpacing.Lg, BpkSpacing.Lg)
    }
