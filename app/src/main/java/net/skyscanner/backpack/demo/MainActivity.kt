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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
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
import net.skyscanner.backpack.compose.tokens.Settings
import net.skyscanner.backpack.demo.StoriesRecyclerViewAdapter.StoryItem
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
//    setContentView(R.layout.activity_component_list)
//
//    val toolbar = findViewById<Toolbar>(R.id.toolbar)
//    setSupportActionBar(toolbar)
//    toolbar.title = title

//    val componentsList = findViewById<View>(R.id.componentsList) as RecyclerView
//    val allItems = mutableListOf<StoriesRecyclerViewAdapter.ListItem>()
//    allItems.add(HeaderItem("Tokens"))
//    allItems.addAll(ComponentRegistry.TOKENS.map { it.toStoryItem() })
//    allItems.add(HeaderItem("Components"))
//    allItems.addAll(ComponentRegistry.COMPONENTS.map { it.toStoryItem() })

    setContent {
      BpkTheme {
        Column() {
          BpkTopNavBar(
            navIcon = NavIcon.None,
            title = stringResource(R.string.app_name),
//            modifier = Modifier.background(color = BpkColor.SkyBlue),
            actions = listOf(
              IconAction(icon = BpkIcon.Settings, contentDescription = stringResource(R.string.settings_title), onClick = {})
            )
          )
          LazyColumn(
            modifier = Modifier
              .padding(horizontal = 24.dp)
              .fillMaxWidth()
          ) {
            item {
              BpkText(
                text = "Tokens".uppercase(),
                modifier = Modifier.padding(top = 16.dp, bottom = 16.dp),
                color = BpkTheme.colors.textSecondary,
              )
            }
            items(ComponentRegistry.TOKENS.values.toList()) {
              val context = LocalContext.current
              Row(
                modifier = Modifier
                  .padding(
                    top = 16.dp,
                  )
                  .clickable {
                    val intent = Intent(context, ComponentDetailActivity::class.java)
                    intent.putExtra(ComponentDetailFragment.ARG_ITEM_ID, it.name)
                    context.startActivity(intent)
                  }
              ) {
                BpkText(text = it.name)
                if (hasComposeNodes(item = it)) {
                  BpkBadge(text = "Compose", type = BpkBadgeType.Success, modifier = Modifier.padding(start = 16.dp))
                }
              }
            }
            item {
              BpkText(
                text = "Components".uppercase(),
                modifier = Modifier.padding(top = 26.dp, bottom = 16.dp),
                color = BpkTheme.colors.textSecondary,
              )
            }
            items(ComponentRegistry.COMPONENTS.values.toList()) {
              val context = LocalContext.current
              Row(
                modifier = Modifier
                  .padding(top = 16.dp)
                  .clickable {
                    val intent = Intent(context, ComponentDetailActivity::class.java)
                    intent.putExtra(ComponentDetailFragment.ARG_ITEM_ID, it.name)
                    context.startActivity(intent)
                  }
              ) {
                BpkText(text = it.name)
                if (hasComposeNodes(item = it)) {
                  BpkBadge(text = "Compose", type = BpkBadgeType.Success, modifier = Modifier.padding(start = 16.dp))
                }
              }
            }
          }
        }
      }
//    componentsList.adapter = StoriesRecyclerViewAdapter(allItems)
//    componentsList.addItemDecoration(StoryItemDecoration(this))
    }
  }

  @Composable
  private fun Map.Entry<String, NodeItem>.toStoryItem(): StoryItem {
    return StoryItem(key, hasComposeNodes(value))
  }

  @Composable
  private fun hasComposeNodes(item: RegistryItem): Boolean {
    if (item is ComposeNode) {
      return true
    }
    return item is NodeItem && item.subItems.values.any { hasComposeNodes(it) }
  }
}

//  @Composable
//  private fun hasComposeNodes(item: RegistryItem): Boolean {
//    if (item is ComposeNode) {
//      return true
//    }
//    return item is NodeItem && item.subItems.values.any { hasComposeNodes(it) }
//  }
// }
