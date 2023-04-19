package net.skyscanner.backpack.compose.bottomnav.internal

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.TweenSpec
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import net.skyscanner.backpack.compose.bottomnav.BpkBottomNavItem
import net.skyscanner.backpack.compose.bottomnav.IconBottomNavItem
import net.skyscanner.backpack.compose.bottomnav.PainterBottomNavItem
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.icon.BpkIconSize
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkSpacing

@Composable
internal fun BottomNavigation(
    elevation: Dp,
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit,
) {
    Surface(
        color = BpkTheme.colors.surfaceDefault,
        contentColor = BpkTheme.colors.textSecondary,
        shadowElevation = elevation,
        modifier = modifier,
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .height(BottomNavigationHeight)
                .selectableGroup(),
            horizontalArrangement = Arrangement.SpaceBetween,
            content = content,
        )
    }
}

@Composable
internal fun RowScope.BottomNavigationItem(
    tabItem: BpkBottomNavItem,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {

    val ripple = rememberRipple(bounded = false, color = BpkTheme.colors.textLink)

    val contentColor by animateColorAsState(
        label = "BottomNavItem content color",
        targetValue = if (selected) BpkTheme.colors.textLink else BpkTheme.colors.textSecondary,
        animationSpec = BottomNavigationAnimationSpec,
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .fillMaxHeight()
            .weight(1f)
            .selectable(
                selected = selected,
                onClick = onClick,
                role = Role.Tab,
                interactionSource = remember { MutableInteractionSource() },
                indication = ripple,
            ),
    ) {

        BottomNavIcon(
            tabItem = tabItem,
            tint = contentColor,
        )

        BpkText(
            text = tabItem.title,
            style = BpkTheme.typography.label3,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = contentColor,
        )
    }
}

@Composable
private fun BottomNavIcon(
    tabItem: BpkBottomNavItem,
    tint: Color,
    modifier: Modifier = Modifier,
) {
    Box(modifier) {
        when (tabItem) {
            is IconBottomNavItem -> BpkIcon(
                icon = tabItem.icon,
                contentDescription = null,
                size = BpkIconSize.Large,
                tint = tint,
            )

            is PainterBottomNavItem -> Icon(
                modifier = Modifier.height(BpkSpacing.Lg),
                painter = tabItem.painter,
                contentDescription = null,
                tint = tint,
            )
        }
        if (tabItem.showBadge) {
            NotificationDot(
                Modifier
                    .align(Alignment.BottomEnd)
                    .offset(x = 1.dp, y = (-2).dp),
            )
        }
    }
}

@Composable
private fun NotificationDot(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .size(12.dp)
            .border(width = 2.dp, color = BpkTheme.colors.surfaceDefault, shape = CircleShape)
            .padding(2.dp)
            .background(color = BpkTheme.colors.coreAccent, shape = CircleShape),
    )
}

private val BottomNavigationAnimationSpec = TweenSpec<Color>(
    durationMillis = 300,
    easing = FastOutSlowInEasing,
)

private val BottomNavigationHeight = 56.dp
