/*
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
 *
 */

package net.skyscanner.backpack.compose.bottomnav

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
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
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.icon.BpkIconSize
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkElevation
import net.skyscanner.backpack.compose.tokens.BpkSpacing

@Stable
sealed interface BpkBottomNavItem {
    val title: String
    val id: Int
    val showBadge: Boolean
}

fun BpkBottomNavItem(
    title: String,
    id: Int,
    icon: BpkIcon,
    showBadge: Boolean = false,
): BpkBottomNavItem =
    IconBottomNavItem(title, id, showBadge, icon)

fun BpkBottomNavItem(
    title: String,
    id: Int,
    painter: Painter,
    showBadge: Boolean = false,
): BpkBottomNavItem =
    PainterBottomNavItem(title, id, showBadge, painter)

@Composable
fun BpkBottomNav(
    onTabClicked: (Int) -> Unit,
    selectedItemId: Int,
    items: List<BpkBottomNavItem>,
    modifier: Modifier = Modifier,
    elevation: Dp = BpkElevation.Lg,
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
                .height(56.dp)
                .selectableGroup(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            items.forEach { tabItem ->
                BottomNavigationItem(
                    tabItem = tabItem,
                    selected = selectedItemId == tabItem.id,
                    onClick = { onTabClicked(tabItem.id) },
                )
            }
        }
    }
}

@Composable
private fun RowScope.BottomNavigationItem(
    tabItem: BpkBottomNavItem,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {

    val ripple = rememberRipple(bounded = false, color = BpkTheme.colors.textLink)

    val contentColor by animateColorAsState(
        label = "BottomNavItem content color",
        targetValue = if (selected) BpkTheme.colors.textLink else BpkTheme.colors.textSecondary,
        animationSpec = tween(
            durationMillis = 300,
            easing = FastOutSlowInEasing,
        ),
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
                modifier = Modifier
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

private data class IconBottomNavItem(
    override val title: String,
    override val id: Int,
    override val showBadge: Boolean,
    val icon: BpkIcon,
) : BpkBottomNavItem

private data class PainterBottomNavItem(
    override val title: String,
    override val id: Int,
    override val showBadge: Boolean,
    val painter: Painter,
) : BpkBottomNavItem
