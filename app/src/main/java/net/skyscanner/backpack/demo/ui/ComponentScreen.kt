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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.zIndex
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator
import net.skyscanner.backpack.compose.horizontalnav.BpkHorizontalNav
import net.skyscanner.backpack.compose.horizontalnav.BpkHorizontalNavSize
import net.skyscanner.backpack.compose.horizontalnav.BpkHorizontalNavTab
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.navigationbar.BpkTopNavBar
import net.skyscanner.backpack.compose.navigationbar.IconAction
import net.skyscanner.backpack.compose.navigationbar.NavIcon
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.Settings
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.demo.SettingsActivity
import net.skyscanner.backpack.demo.meta.StoriesRepository
import net.skyscanner.backpack.demo.ui.destinations.StoryScreenDestination

@Composable
@Destination("component")
fun ComponentScreen(
  component: String,
  modifier: Modifier = Modifier,
  navigator: DestinationsNavigator = EmptyDestinationsNavigator,
  repository: StoriesRepository = StoriesRepository.getInstance(),
  story: @Composable (@Composable () -> Unit) -> Unit = { it() },
) {

  Column(modifier = modifier
    .background(BpkTheme.colors.canvas)
    .fillMaxSize(),
  ) {
    val context = LocalContext.current
    BpkTopNavBar(
      navIcon = NavIcon.Back(
        contentDescription = stringResource(R.string.navigation_back),
        onClick = navigator::popBackStack,
      ),
      title = component,
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

    val viewStories = remember(repository, component) { repository.storiesOf(component, compose = false) }
    val composeStories = remember(repository, component) { repository.storiesOf(component, compose = true) }

    var composeTabSelected by rememberSaveable { mutableStateOf(false) }

    if (viewStories.isNotEmpty() && composeStories.isNotEmpty()) {
      BpkHorizontalNav(
        tabs = listOf(
          BpkHorizontalNavTab(title = stringResource(R.string.tab_view)),
          BpkHorizontalNavTab(title = stringResource(R.string.tab_compose)),
        ),
        size = BpkHorizontalNavSize.Small,
        activeIndex = if (composeTabSelected) 1 else 0,
        onChanged = { composeTabSelected = it != 0 },
        modifier = Modifier.zIndex(Float.MAX_VALUE),
      )
    } else {
      composeTabSelected = composeStories.isNotEmpty()
    }

    val storiesToDisplay = if (composeTabSelected) composeStories else viewStories

    if (storiesToDisplay.size == 1) {
      story(storiesToDisplay.first().content)
    } else {
      LazyColumn {
        items(storiesToDisplay) {
          ComponentItem(
            title = it.name,
            onClick = { navigator.navigate(StoryScreenDestination(it.component.name, it.name, it.isCompose)) },
          )
        }
      }
    }
  }
}
