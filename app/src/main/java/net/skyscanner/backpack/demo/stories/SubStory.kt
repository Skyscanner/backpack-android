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

package net.skyscanner.backpack.demo.stories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.demo.StoriesRecyclerViewAdapter
import net.skyscanner.backpack.demo.StoryItemDecoration

open class SubStory : Story() {

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    val stories = arguments?.getStringArray(STORIES) ?: savedInstanceState?.getStringArray(STORIES)
    if (stories != null) {
      val view = inflater.inflate(R.layout.component_list, container, false)
      val componentsList = view.findViewById<View>(R.id.componentsList) as RecyclerView
      val allItems = mutableListOf<StoriesRecyclerViewAdapter.ListItem>()
      allItems.addAll(stories.map { StoriesRecyclerViewAdapter.StoryItem(it) })

      componentsList.adapter = StoriesRecyclerViewAdapter(allItems)
      componentsList.addItemDecoration(StoryItemDecoration(requireContext()))
      return view
    } else {
      throw IllegalStateException("Story has not been property initialized")
    }
  }

  companion object {
    const val STORIES = "stories"

    infix fun of(stories: Array<String>) = SubStory().apply {
      arguments = Bundle()
      arguments?.putStringArray(STORIES, stories)
    }
  }
}
