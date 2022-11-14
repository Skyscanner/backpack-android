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

package net.skyscanner.backpack.demo.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.navigationbar.BpkTopNavBar
import net.skyscanner.backpack.compose.navigationbar.IconAction
import net.skyscanner.backpack.compose.navigationbar.NavIcon
import net.skyscanner.backpack.compose.navigationbar.TopNavBarStatus
import net.skyscanner.backpack.compose.navigationbar.nestedScroll
import net.skyscanner.backpack.compose.navigationbar.rememberTopAppBarState
import net.skyscanner.backpack.compose.tokens.Accessibility
import net.skyscanner.backpack.compose.tokens.Account
import net.skyscanner.backpack.compose.tokens.AccountIdCard
import net.skyscanner.backpack.demo.R

@Composable
@Preview
fun CollapsibleNavigationBarStory(
  modifier: Modifier = Modifier,
  initialStatus: TopNavBarStatus = TopNavBarStatus.Expanded,
  showList: Boolean = true,
  showActions: Boolean = true,
  showNav: Boolean = true,
  insets: WindowInsets = WindowInsets.statusBars,
) {
  val state = rememberTopAppBarState(initialStatus)
  Column(modifier.nestedScroll(state)) {
    BpkTopNavBar(
      state = state,
      title = stringResource(R.string.navigation_bar_title),
      insets = insets,
      navIcon = when {
        showNav -> NavIcon.Back(contentDescription = stringResource(R.string.navigation_back)) {}
        else -> NavIcon.None
      },
      actions = if (showActions) listOf(
        IconAction(icon = BpkIcon.AccountIdCard, contentDescription = stringResource(R.string.navigation_id_card)) {},
        IconAction(icon = BpkIcon.Accessibility, contentDescription = stringResource(R.string.navigation_accessibility)) {},
        IconAction(icon = BpkIcon.Account, contentDescription = stringResource(R.string.navigation_account)) {},
      ) else emptyList(),
    )
    if (showList) {
      LazyColumn {
        item {
          ListItem(title = stringResource(R.string.generic_scroll_the_list))
        }
        item {
          ListItem(title = stringResource(R.string.generic_scroll_the_list))
        }
        items(100) {
          ListItem(title = stringResource(R.string.generic_scroll_the_list))
        }
      }
    }
  }
}
