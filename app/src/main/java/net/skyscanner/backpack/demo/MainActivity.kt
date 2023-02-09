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

package net.skyscanner.backpack.demo

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import net.skyscanner.backpack.compose.horizontalnav.BpkHorizontalNav
import net.skyscanner.backpack.compose.horizontalnav.BpkHorizontalNavTab
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.navigationbar.BpkTopNavBar
import net.skyscanner.backpack.compose.navigationbar.IconAction
import net.skyscanner.backpack.compose.navigationbar.NavIcon
import net.skyscanner.backpack.compose.navigationbar.nestedScroll
import net.skyscanner.backpack.compose.navigationbar.rememberTopAppBarState
import net.skyscanner.backpack.compose.tokens.Settings
import net.skyscanner.backpack.demo.compose.ComponentItem
import net.skyscanner.backpack.demo.compose.ComponentsTitle
import net.skyscanner.backpack.demo.meta.ComponentEntry
import net.skyscanner.backpack.demo.meta.Kind
import net.skyscanner.backpack.demo.meta.Stories
import net.skyscanner.backpack.demo.meta.StoryEntry
import net.skyscanner.backpack.demo.meta.all

/**
 * An activity representing a list of Components. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a [ComponentDetailActivity] representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
class MainActivity : BpkBaseActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val screens = Stories.all()
    val components = screens
      .groupBy { it.component }

    setContent {
      BackpackDemoTheme {

        var currentComponent by remember { mutableStateOf<ComponentEntry?>(null) }
        var currentCase by remember { mutableStateOf<StoryEntry?>(null) }

        when {
          currentCase != null -> DemoScreen(
            case = currentCase!!,
            onBack = { currentCase = null },
          )
          currentComponent != null -> CasesScreen(
            cases = components[currentComponent!!]!!,
            onBack = { currentComponent = null },
            onClick = { currentCase = it },
          )
          else -> ComponentsScreen(
            components = components.keys.toList().sortedBy { it.name },
            onClick = { currentComponent = it },
          )
        }
      }
    }
  }
}

@Composable
private fun ComponentsScreen(
  components: List<ComponentEntry>,
  modifier: Modifier = Modifier,
  onClick: (ComponentEntry) -> Unit,
) {
  val state = rememberTopAppBarState()
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
      items(components) {
        ComponentItem(title = it.name, showComposeBadge = it.kind == Kind.Compose)
      }
    }
  }
}

@Composable
private fun CasesScreen(
  cases: List<StoryEntry>,
  onBack: () -> Unit,
  onClick: (StoryEntry) -> Unit,
  modifier: Modifier = Modifier,
) {
  val component = cases.first().component

  Column(modifier = modifier) {
    val context = LocalContext.current
    BpkTopNavBar(
      navIcon = NavIcon.Back(
        contentDescription = "Back",
        onClick = { onBack() },
      ),
      title = cases.first().component.name,
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

    var kind by remember { mutableStateOf(component.kind) }

    if (cases.distinctBy { it.component.kind }.size > 1) {
      BpkHorizontalNav(
        tabs = listOf(
          BpkHorizontalNavTab(title = "View"),
          BpkHorizontalNavTab(title = "Compose"),
        ),
        activeIndex = if (kind == Kind.View) 0 else 1,
        onChanged = { kind = if (it == 0) Kind.View else Kind.Compose },
      )
    }

    val cases = cases.filter { it.component.kind == kind }
    if (cases.size == 1) {
      cases.first().content()
    } else {
      LazyColumn {
        items(cases) {
          ComponentItem(title = it.name, showComposeBadge = false)
        }
      }
    }
  }
}

@Composable
private fun DemoScreen(
  case: StoryEntry,
  modifier: Modifier = Modifier,
  onBack: () -> Unit,
) {
  Column(modifier = modifier) {
    val context = LocalContext.current
    BpkTopNavBar(
      navIcon = NavIcon.Back(
        contentDescription = "Back",
        onClick = { onBack() },
      ),
      title = case.component.name + " - " + case.name,
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
    case.content()
  }
}
