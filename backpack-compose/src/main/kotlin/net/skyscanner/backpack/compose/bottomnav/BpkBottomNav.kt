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

import androidx.compose.foundation.layout.height
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationDefaults
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.icon.BpkIconSize
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.theme.BpkTheme

sealed class TabIcon{
  data class Bpk(val icon: BpkIcon) : TabIcon()
  data class Custom(val painter: Painter) : TabIcon()
}
data class TabItem(val icon: TabIcon, val title: String, val id: Int)

@Composable
fun BpkBottomNav(
  onTabClicked: (Int) -> Unit,
  selectedItemId: Int,
  modifier: Modifier = Modifier,
  elevation: Dp = BottomNavigationDefaults.Elevation,
  tabItems: List<TabItem> = emptyList(),
) {
  BottomNavigation(
    modifier = modifier,
    backgroundColor = BpkTheme.colors.surfaceDefault,
    contentColor = BpkTheme.colors.textSecondary,
    elevation = elevation,
  ) {
    tabItems.forEach { tabItem ->
      BottomNavigationItem(
        selected = selectedItemId == tabItem.id,
        onClick = { onTabClicked(tabItem.id) },
        icon = { when(tabItem.icon) {
          is TabIcon.Bpk -> BpkIcon(icon = tabItem.icon.icon, contentDescription = null, size = BpkIconSize.Large)
          is TabIcon.Custom -> Icon(modifier = Modifier.height(24.dp), painter = tabItem.icon.painter, contentDescription = null)
        } },
        label = {
          BpkText(
            text = tabItem.title
          )
        },
        selectedContentColor = BpkTheme.colors.textLink,
        unselectedContentColor = BpkTheme.colors.textSecondary
      )
    }
  }
}
