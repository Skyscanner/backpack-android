package net.skyscanner.backpack.compose.navigationtabgroup.internal

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
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
            selectable(
                selected = selected,
                interactionSource = interactionSource,
                indication = LocalIndication.current,
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

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(BpkSpacing.Md),
        modifier = modifier
            .height(BpkSpacing.Xl)
            .border(BorderStroke(BpkBorderSize.Sm, strokeColor), CircleShape)
            .shadow(0.dp, CircleShape)
            .background(backgroundColor, CircleShape)
            .clip(CircleShape)
            .padding(horizontal = BpkSpacing.Md),
    ) {

        Spacer(modifier = Modifier.width(0.dp))

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
            style = BpkTheme.typography.footnote,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )

        Spacer(modifier = Modifier.width(0.dp))
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
