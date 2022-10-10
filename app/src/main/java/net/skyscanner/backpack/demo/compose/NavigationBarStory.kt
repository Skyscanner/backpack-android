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

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.navigationbar.BpkTopNavBar
import net.skyscanner.backpack.compose.navigationbar.IconAction
import net.skyscanner.backpack.compose.navigationbar.NavIcon
import net.skyscanner.backpack.compose.navigationbar.TextAction
import net.skyscanner.backpack.compose.tokens.Accessibility
import net.skyscanner.backpack.compose.tokens.Account
import net.skyscanner.backpack.compose.tokens.AccountIdCard
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.demo.R

@Composable
fun NavigationBarStory() {
  Column(
    modifier = Modifier.padding(vertical = BpkSpacing.Base),
    verticalArrangement = Arrangement.spacedBy(BpkSpacing.Base),
  ) {

    val modifier = Modifier.fillMaxWidth()
    NoNavIconTopNavBar(modifier)
    BackTopNavBar(modifier)
    CloseTopNavBar(modifier)
    ActionsTopNavBar(modifier)
    TextActionTopNavBar(modifier)
  }
}

@Preview
@Composable
internal fun NoNavIconTopNavBar(modifier: Modifier = Modifier) {
  BpkTopNavBar(navIcon = NavIcon.None, title = stringResource(R.string.navigation_bar_title), modifier = modifier)
}

@Preview
@Composable
internal fun BackTopNavBar(modifier: Modifier = Modifier) {
  BpkTopNavBar(
    title = stringResource(R.string.navigation_bar_title),
    navIcon = NavIcon.Back(contentDescription = stringResource(R.string.navigation_back)) {},
    modifier = modifier,
  )
}

@Preview
@Composable
internal fun CloseTopNavBar(modifier: Modifier = Modifier) {
  BpkTopNavBar(
    title = stringResource(R.string.navigation_bar_title),
    navIcon = NavIcon.Close(contentDescription = stringResource(R.string.navigation_close)) {},
    modifier = modifier,
  )
}

@Preview
@Composable
internal fun ActionsTopNavBar(modifier: Modifier = Modifier) {
  BpkTopNavBar(
    title = stringResource(R.string.navigation_bar_title),
    navIcon = NavIcon.Back(contentDescription = stringResource(R.string.navigation_back)) {},
    actions = listOf(
      IconAction(icon = BpkIcon.AccountIdCard, contentDescription = stringResource(R.string.navigation_id_card)) {},
      IconAction(icon = BpkIcon.Accessibility, contentDescription = stringResource(R.string.navigation_accessibility)) {},
      IconAction(icon = BpkIcon.Account, contentDescription = stringResource(R.string.navigation_account)) {},
    ),
    modifier = modifier,
  )
}

@Preview
@Composable
internal fun TextActionTopNavBar(modifier: Modifier = Modifier) {
  BpkTopNavBar(
    navigationIcon = NavIcon.None,
    title = stringResource(R.string.navigation_bar_title),
    action = TextAction(text = stringResource(R.string.navigation_text_action)) {},
    modifier = modifier,
  )
}
