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
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import net.skyscanner.backpack.compose.badge.BpkBadge
import net.skyscanner.backpack.compose.badge.BpkBadgeType
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.navigationbar.BpkTopNavBar
import net.skyscanner.backpack.compose.navigationbar.IconAction
import net.skyscanner.backpack.compose.navigationbar.NavIcon
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkDimension
import net.skyscanner.backpack.compose.tokens.Settings
import net.skyscanner.backpack.demo.data.ComponentRegistry
import net.skyscanner.backpack.demo.data.ComposeNode
import net.skyscanner.backpack.demo.data.NodeItem
import net.skyscanner.backpack.demo.data.RegistryItem

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
    setContent {
      BackpackDemoTheme {
        ComponentScreen()
      }
    }
  }

  @Composable
  private fun ComponentScreen(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
      val context = LocalContext.current
      BpkTopNavBar(
        navIcon = NavIcon.None,
        title = stringResource(R.string.app_name),
        actions = listOf(
          IconAction(
            icon = BpkIcon.Settings,
            contentDescription = stringResource(R.string.settings_title),
            onClick = {
              val intent = Intent(context, SettingsActivity::class.java)
              context.startActivity(intent)
            }
          )
        )
      )
      LazyColumn {
        item {
          ComponentsTitle(stringResource(R.string.tokens_title))
        }
        items(ComponentRegistry.TOKENS.values.toList()) {
          ComponentItem(title = it.name, showComposeBadge = hasComposeNodes(item = it))
        }
        item {
          ComponentsTitle(title = stringResource(R.string.components_title))
        }
        items(ComponentRegistry.COMPONENTS.values.toList()) {
          ComponentItem(title = it.name, showComposeBadge = hasComposeNodes(item = it))
        }
      }
    }
  }

  @Composable
  fun ComponentItem(
    title: String,
    showComposeBadge: Boolean,
    modifier: Modifier = Modifier,
  ) {
    val context = LocalContext.current
    Column(
      modifier = modifier
        .clickable {
          val intent = Intent(context, ComponentDetailActivity::class.java)
          intent.putExtra(ComponentDetailFragment.ARG_ITEM_ID, title)
          context.startActivity(intent)
        }
        .height(56.dp)
        .fillMaxWidth()
        .padding(horizontal = BpkDimension.Spacing.Lg),
    ) {

      Row(
        modifier = Modifier.weight(1f),
        verticalAlignment = Alignment.CenterVertically,
      ) {
        BpkText(text = title, style = BpkTheme.typography.bodyLongform)
        if (showComposeBadge) {
          BpkBadge(
            text = stringResource(R.string.story_badge_compose),
            type = BpkBadgeType.Success,
            modifier = Modifier.padding(start = BpkDimension.Spacing.Base)
          )
        }
      }
      Divider()
    }
  }

  @Composable
  fun ComponentsTitle(title: String, modifier: Modifier = Modifier) {
    BpkText(
      text = title.uppercase(),
      modifier = modifier
        .fillMaxWidth()
        .padding(
          vertical = BpkDimension.Spacing.Base,
          horizontal = BpkDimension.Spacing.Lg
        ),
      color = BpkTheme.colors.textSecondary,
      style = BpkTheme.typography.label2,
    )
  }

  private fun hasComposeNodes(item: RegistryItem): Boolean {
    if (item is ComposeNode) {
      return true
    }
    return item is NodeItem && item.subItems.values.any { hasComposeNodes(it) }
  }
}
