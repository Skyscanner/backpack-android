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

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import net.skyscanner.backpack.compose.bottomnav.internal.BottomNavigation
import net.skyscanner.backpack.compose.bottomnav.internal.BottomNavigationItem
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
    BottomNavigation(
        modifier = modifier,
        elevation = elevation,
    ) {
        items.forEach { tabItem ->
            BottomNavigationItem(
                selected = selectedItemId == tabItem.id,
                onClick = { onTabClicked(tabItem.id) },
                icon = {
                    Box {
                        when (tabItem) {
                            is IconBottomNavItem -> BpkIcon(
                                icon = tabItem.icon,
                                contentDescription = null,
                                size = BpkIconSize.Large,
                            )
                            is PainterBottomNavItem -> Icon(
                                modifier = Modifier.height(BpkSpacing.Lg),
                                painter = tabItem.painter,
                                contentDescription = null,
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
                },
                label = {
                    BpkText(
                        text = tabItem.title,
                        style = BpkTheme.typography.label3,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                },
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
