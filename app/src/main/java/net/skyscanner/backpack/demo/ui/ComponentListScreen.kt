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

package net.skyscanner.backpack.demo.ui

import android.content.Intent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.navigationbar.BpkTopNavBar
import net.skyscanner.backpack.compose.navigationbar.IconAction
import net.skyscanner.backpack.compose.navigationbar.NavIcon
import net.skyscanner.backpack.compose.navigationbar.nestedScroll
import net.skyscanner.backpack.compose.navigationbar.rememberTopAppBarState
import net.skyscanner.backpack.compose.tokens.Settings
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.demo.SettingsActivity
import net.skyscanner.backpack.demo.compose.ComponentItem
import net.skyscanner.backpack.demo.compose.ComponentsTitle
import net.skyscanner.backpack.demo.meta.Component
import net.skyscanner.backpack.demo.meta.Story
import net.skyscanner.backpack.demo.meta.all

@Composable
fun ComponentListScreen(
  modifier: Modifier = Modifier,
  stories: List<Story> = remember { Story.all() },
  onClick: (Component) -> Unit,
) {

  val state = rememberTopAppBarState()
  val map = remember(stories) { stories.groupBy { it.component }.filter { it.value.isNotEmpty() } }
  val components = remember(map) { map.keys.sortedBy { it.name } }

  Column(modifier = modifier.nestedScroll(state)) {
    val context = LocalContext.current
    BpkTopNavBar(
      state = state,
      navIcon = NavIcon.None,
      title = stringResource(R.string.app_name),
      actions = listOf(
        IconAction(
          icon = BpkIcon.Settings,
          contentDescription = stringResource(R.string.settings_title),
          onClick = {
            val intent = Intent(context, SettingsActivity::class.java)
            context.startActivity(intent)
          },
        ),
      ),
    )
    LazyColumn {
      item {
        ComponentsTitle(title = stringResource(R.string.components_title))
      }

      items(components) { component ->
        ComponentItem(
          title = component.name,
          showComposeBadge = map.getValue(component).any { it.isCompose },
          onClick = { onClick(component) },
        )
      }
    }
  }
}
