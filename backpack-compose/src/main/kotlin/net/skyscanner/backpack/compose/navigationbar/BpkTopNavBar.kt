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

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.navigationbar.internal.BpkTopNavBarImpl
import net.skyscanner.backpack.compose.navigationbar.internal.toAction

sealed interface NavIcon {
  object None : NavIcon
  data class Back(val contentDescription: String, val onClick: () -> Unit) : NavIcon
  data class Close(val contentDescription: String, val onClick: () -> Unit) : NavIcon
}

internal sealed interface Action {
  val onClick: () -> Unit
}

data class IconAction(val icon: BpkIcon, val contentDescription: String, override val onClick: () -> Unit) : Action
data class TextAction(val text: String, override val onClick: () -> Unit) : Action

@Composable
fun BpkTopNavBar(
  navIcon: NavIcon,
  title: String,
  modifier: Modifier = Modifier,
  actions: List<IconAction> = emptyList(),
) {
  BpkTopNavBarImpl(
    title = title,
    modifier = modifier,
    navigationIcon = navIcon.toAction(),
    actions = actions
  )
}

@Composable
fun BpkTopNavBar(
  navigationIcon: NavIcon,
  title: String,
  action: TextAction,
  modifier: Modifier = Modifier,
) {
  BpkTopNavBarImpl(
    title = title,
    modifier = modifier,
    navigationIcon = navigationIcon.toAction(),
    actions = listOf(action)
  )
}
