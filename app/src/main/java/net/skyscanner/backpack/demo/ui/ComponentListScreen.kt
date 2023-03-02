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
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator
import net.skyscanner.backpack.compose.badge.BpkBadgeType
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.navigationbar.BpkTopNavBar
import net.skyscanner.backpack.compose.navigationbar.IconAction
import net.skyscanner.backpack.compose.navigationbar.NavIcon
import net.skyscanner.backpack.compose.navigationbar.nestedScroll
import net.skyscanner.backpack.compose.navigationbar.rememberTopAppBarState
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.Settings
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.demo.SettingsActivity
import net.skyscanner.backpack.demo.meta.StoriesRepository
import net.skyscanner.backpack.demo.ui.destinations.ComponentScreenDestination

@Composable
@Destination("/")
@RootNavGraph(start = true)
fun ComponentListScreen(
  modifier: Modifier = Modifier,
  repository: StoriesRepository = StoriesRepository.getInstance(),
  navigator: DestinationsNavigator = EmptyDestinationsNavigator,
) {

  val state = rememberTopAppBarState()

  Column(modifier = modifier
    .background(BpkTheme.colors.canvas)
    .fillMaxSize()
    .nestedScroll(state),
  ) {
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
        ComponentsTitle(title = stringResource(R.string.tokens_title))
      }
      items(repository.tokenComponents()) { component ->
        ComponentItem(
          title = component.name,
          onClick = { navigator.navigate(ComponentScreenDestination(component.name)) },
        )
      }

      item {
        ComponentsTitle(title = stringResource(R.string.components_title))
      }
      items(repository.uiComponents()) { component ->
        val composeOnly = repository.isComposeOnly(component.name)
        val viewOnly = repository.isViewOnly(component.name)
        ComponentItem(
          title = component.name,
          badgeType = when {
            composeOnly -> BpkBadgeType.Success
            viewOnly -> BpkBadgeType.Normal
            else -> null
          },
          badgeText = when {
            composeOnly -> stringResource(R.string.story_badge_compose)
            viewOnly -> stringResource(R.string.story_badge_view)
            else -> null
          },
          onClick = { navigator.navigate(ComponentScreenDestination(component.name)) },
        )
      }
    }
  }
}
