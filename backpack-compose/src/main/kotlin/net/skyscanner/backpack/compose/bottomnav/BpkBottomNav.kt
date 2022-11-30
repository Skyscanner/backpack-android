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

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationDefaults
import androidx.compose.material.BottomNavigationItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.theme.BpkTheme

data class TabItem(val icon: BpkIcon, val title: String)

@Composable
fun BpkBottomNav(
  modifier: Modifier = Modifier,
  elevation: Dp = BottomNavigationDefaults.Elevation,
  actions: List<TabItem> = emptyList(),
) {
  BottomNavigation(
    modifier = modifier,
    backgroundColor = BpkTheme.colors.surfaceDefault,
    contentColor = BpkTheme.colors.textSecondary,
    elevation = elevation,
  ) {
    actions.forEach { tabItem ->
      BottomNavigationItem(
        selected = false,
        onClick = { /*TODO*/ },
        icon = { BpkIcon(icon = tabItem.icon, contentDescription = null) },
        label = {
          BpkText(
            text = tabItem.title
          )
        },
        selectedContentColor = BpkTheme.colors.textLink,
        unselectedContentColor = BpkTheme.colors.textSecondary)

    }


  }
}
