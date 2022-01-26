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

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView
import net.skyscanner.backpack.demo.StoriesRecyclerViewAdapter.HeaderItem
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
    setContentView(R.layout.activity_component_list)

    val toolbar = findViewById<Toolbar>(R.id.toolbar)
    setSupportActionBar(toolbar)
    toolbar.title = title

    val componentsList = findViewById<View>(R.id.componentsList) as RecyclerView
    val allItems = mutableListOf<StoriesRecyclerViewAdapter.ListItem>()
    allItems.add(HeaderItem("Tokens"))
    allItems.addAll(ComponentRegistry.TOKENS.map { it.toStoryItem() })
    allItems.add(HeaderItem("Components"))
    allItems.addAll(ComponentRegistry.COMPONENTS.map { it.toStoryItem() })

    componentsList.adapter = StoriesRecyclerViewAdapter(allItems)
    componentsList.addItemDecoration(StoryItemDecoration(this))
  }

  private fun Map.Entry<String, NodeItem>.toStoryItem(): StoryItem {
    return StoryItem(key, hasComposeNodes(value))
  }

  private fun hasComposeNodes(item: RegistryItem): Boolean {
    if (item is ComposeNode) {
      return true
    }
    return item is NodeItem && item.subItems.values.any { hasComposeNodes(it) }
  }
}
