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

package net.skyscanner.backpack.compose.navigationbar.internal

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.lerp
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import net.skyscanner.backpack.compose.LocalTextStyle
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.icon.BpkIconSize
import net.skyscanner.backpack.compose.navigationbar.Action
import net.skyscanner.backpack.compose.navigationbar.IconAction
import net.skyscanner.backpack.compose.navigationbar.NavBarStyle
import net.skyscanner.backpack.compose.navigationbar.NavIcon
import net.skyscanner.backpack.compose.navigationbar.TextAction
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkDimension
import net.skyscanner.backpack.compose.tokens.NativeAndroidBack
import net.skyscanner.backpack.compose.tokens.NativeAndroidClose
import net.skyscanner.backpack.compose.utils.clickable

@Composable
internal fun BpkTopNavBarImpl(
    fraction: Float,
    title: String,
    insets: WindowInsets?,
    navIcon: IconAction?,
    actions: List<Action>,
    style: NavBarStyle,
    modifier: Modifier = Modifier,
) {
    val backgroundColor = if (fraction == 0f) {
        BpkTheme.colors.surfaceDefault
    } else if (style == NavBarStyle.OnImage) {
        Color.Transparent
    } else {
        BpkTheme.colors.canvas
    }

    val contentColor = if (fraction > 0f && style == NavBarStyle.OnImage) {
        BpkTheme.colors.textOnDark
    } else {
        BpkTheme.colors.textPrimary
    }

    Surface(
        color = animateColorAsState(targetValue = backgroundColor).value,
        contentColor = contentColor,
        elevation = animateDpAsState(targetValue = if (fraction == 0f) BpkDimension.Elevation.Sm else 0.dp).value,
        shape = RectangleShape,
        modifier = modifier.zIndex(1f),
    ) {

        val titleStyle = lerp(
            start = BpkTheme.typography.heading4,
            stop = BpkTheme.typography.heading2,
            fraction = fraction,
        )

        CompositionLocalProvider(
            LocalContentAlpha provides 1f,
            LocalTextStyle provides titleStyle,
        ) {

            TopAppBarLayout(
                fraction = fraction,
                modifier = if (insets != null) Modifier.windowInsetsPadding(insets) else Modifier,
                navIcon = {
                    if (navIcon != null) {
                        IconAction(action = navIcon)
                    }
                },
                title = {
                    BpkText(text = title, maxLines = 1, overflow = TextOverflow.Ellipsis)
                },
                actions = {
                    actions.forEach { action ->
                        when (action) {
                            is IconAction -> IconAction(action)
                            is TextAction -> TextAction(action)
                        }
                    }
                },
            )
        }
    }
}

@Composable
private fun IconAction(action: IconAction, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .size(TopNavBarSizes.IconActionSize)
            .clickable(bounded = false, role = Role.Button) { action.onClick() },
        contentAlignment = Alignment.Center,
    ) {
        BpkIcon(icon = action.icon, contentDescription = action.contentDescription, size = BpkIconSize.Large)
    }
}

@Composable
private fun TextAction(action: TextAction, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxHeight()
            .padding(horizontal = BpkDimension.Spacing.Md)
            .clickable(bounded = false, role = Role.Button) { action.onClick() },
        contentAlignment = Alignment.Center,
    ) {
        BpkText(
            text = action.text,
            color = BpkTheme.colors.textLink,
            style = BpkTheme.typography.label1,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

internal fun NavIcon.toAction(): IconAction? =
    when (this) {
        is NavIcon.Back -> IconAction(BpkIcon.NativeAndroidBack, contentDescription, onClick)
        is NavIcon.Close -> IconAction(BpkIcon.NativeAndroidClose, contentDescription, onClick)
        is NavIcon.None -> null
    }
