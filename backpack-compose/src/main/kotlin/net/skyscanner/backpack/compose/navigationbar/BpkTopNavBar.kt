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

package net.skyscanner.backpack.compose.navigationbar

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.statusBars
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.navigationbar.internal.BpkTopNavBarImpl
import net.skyscanner.backpack.compose.navigationbar.internal.toAction

sealed interface NavIcon {
    data object None : NavIcon
    data class Back(val contentDescription: String, val onClick: () -> Unit) : NavIcon
    data class Close(val contentDescription: String, val onClick: () -> Unit) : NavIcon
}

enum class NavBarStyle {
    Default,
    OnImage,
    SurfaceContrast,
}

internal sealed interface Action {
    val onClick: () -> Unit
}

data class IconAction(
    val icon: BpkIcon,
    val contentDescription: String,
    override val onClick: () -> Unit,
) : Action

data class TextAction(
    val text: String,
    override val onClick: () -> Unit,
) : Action

@Composable
fun BpkTopNavBar(
    navIcon: NavIcon,
    title: String,
    modifier: Modifier = Modifier,
    insets: WindowInsets? = WindowInsets.statusBars,
    actions: List<IconAction> = emptyList(),
    style: NavBarStyle = NavBarStyle.Default,

) {
    BpkTopNavBarImpl(
        scrollBehavior = rememberFixedTopAppBarState(),
        title = title,
        modifier = modifier,
        insets = insets,
        navIcon = navIcon.toAction(),
        actions = actions,
        style = style,
    )
}

@Composable
fun BpkTopNavBar(
    navIcon: NavIcon,
    title: String,
    action: TextAction,
    modifier: Modifier = Modifier,
    insets: WindowInsets? = WindowInsets.statusBars,
    style: NavBarStyle = NavBarStyle.Default,
) {
    BpkTopNavBarImpl(
        scrollBehavior = rememberFixedTopAppBarState(),
        title = title,
        modifier = modifier,
        insets = insets,
        navIcon = navIcon.toAction(),
        actions = listOf(action),
        style = style,
    )
}

@Composable
fun BpkTopNavBar(
    state: TopNavBarState,
    navIcon: NavIcon,
    title: String,
    modifier: Modifier = Modifier,
    insets: WindowInsets? = WindowInsets.statusBars,
    actions: List<IconAction> = emptyList(),
    style: NavBarStyle = NavBarStyle.Default,
) {
    BpkTopNavBarImpl(
        scrollBehavior = state,
        title = title,
        modifier = modifier,
        insets = insets,
        navIcon = navIcon.toAction(),
        actions = actions,
        style = style,
    )
}

@Composable
fun BpkTopNavBar(
    state: TopNavBarState,
    navIcon: NavIcon,
    title: String,
    action: TextAction,
    modifier: Modifier = Modifier,
    insets: WindowInsets? = WindowInsets.statusBars,
    style: NavBarStyle = NavBarStyle.Default,
) {
    BpkTopNavBarImpl(
        scrollBehavior = state,
        title = title,
        modifier = modifier,
        insets = insets,
        navIcon = navIcon.toAction(),
        actions = listOf(action),
        style = style,
    )
}
