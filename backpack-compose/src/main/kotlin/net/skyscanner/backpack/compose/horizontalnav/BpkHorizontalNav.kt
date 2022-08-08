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
 */

package net.skyscanner.backpack.compose.horizontalnav

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkSpacing

enum class BpkHorizontalNavSize {
  Default,
  Small,
}

data class BpkHorizontalNavTab(
  val title: String,
  val icon: BpkIcon? = null,
)

@Composable
fun BpkHorizontalNav(
  tabs: List<BpkHorizontalNavTab>,
  activeTab: BpkHorizontalNavTab,
  onChanged: (BpkHorizontalNavTab) -> Unit,
  modifier: Modifier = Modifier,
  size: BpkHorizontalNavSize = BpkHorizontalNavSize.Default,
) {
  TabRow(
    selectedTabIndex = tabs.indexOf(activeTab),
    modifier = modifier.height(
      when (size) {
        BpkHorizontalNavSize.Default -> 48.dp
        BpkHorizontalNavSize.Small -> 36.dp
      }
    ),
    backgroundColor = BpkTheme.colors.backgroundElevation01,
    contentColor = BpkTheme.colors.textLink,
  ) {
    tabs.forEach { tab ->
      Tab(
        selected = tab == activeTab,
        onClick = { onChanged(tab) },
        selectedContentColor = BpkTheme.colors.textLink,
        unselectedContentColor = BpkTheme.colors.textPrimary,
        text = {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(BpkSpacing.Md),
          ) {
            if (tab.icon != null) {
              BpkIcon(
                icon = tab.icon,
                contentDescription = null,
              )
            }

            BpkText(
              text = tab.title,
              style = when (size) {
                BpkHorizontalNavSize.Default -> BpkTheme.typography.label1
                BpkHorizontalNavSize.Small -> BpkTheme.typography.label2
              }
            )
          }
        },
      )
    }
  }
}
