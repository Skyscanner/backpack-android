/**
 * Backpack for Android - Skyscanner's Design System
 *
 * Copyright 2018-2021 Skyscanner Ltd
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
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import net.skyscanner.backpack.demo.data.ComponentRegistry

class StoriesRecyclerViewAdapter internal constructor(
  private val values: List<ListItem>
) : RecyclerView.Adapter<StoriesRecyclerViewAdapter.ViewHolder>() {

  private val onClickListener = View.OnClickListener { view ->
    val viewId = view.tag as String

    val context = view.context
    val intent = Intent(context, ComponentDetailActivity::class.java)
    intent.putExtra(ComponentDetailFragment.ARG_ITEM_ID, viewId)

    context.startActivity(intent)
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    return if (viewType == 0) {
      ViewHolder(
        LayoutInflater.from(parent.context)
          .inflate(R.layout.component_list_header, parent, false)
      )
    } else {
      ViewHolder(
        LayoutInflater.from(parent.context)
          .inflate(R.layout.component_list_content, parent, false)
      )
    }
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    holder.name.text = ComponentRegistry.getStoryName(values[position].getText())
    holder.itemView.tag = values[position].getText()

    if (values[position] is StoryItem) {
      holder.itemView.setOnClickListener(onClickListener)
    }
  }

  override fun getItemCount(): Int {
    return values.size
  }

  override fun getItemViewType(position: Int): Int {
    return if (values[position] is HeaderItem) HEADER_VIEW_TYPE else STORY_VIEW_TYPE
  }

  inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    val name: TextView = view.findViewById(R.id.component_name)
  }

  interface ListItem {
    fun getText(): String
  }

  class ListItemImpl(private val text: String) : ListItem {
    override fun getText(): String = text
  }

  class HeaderItem(text: String) : ListItem by ListItemImpl(text)
  class StoryItem(text: String) : ListItem by ListItemImpl(text)

  companion object {
    private const val HEADER_VIEW_TYPE = 0
    const val STORY_VIEW_TYPE = 1
  }
}
