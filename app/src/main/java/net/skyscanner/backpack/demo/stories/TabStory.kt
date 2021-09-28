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

package net.skyscanner.backpack.demo.stories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.demo.data.ComponentRegistry
import net.skyscanner.backpack.horisontalnav.BpkHorizontalNav

open class TabStory : Story() {

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    val stories = arguments?.getStringArray(STORIES) ?: savedInstanceState?.getStringArray(STORIES)
    if (stories != null) {
      val view = inflater.inflate(R.layout.fragment_tabs, container, false)
      val tabBar = view.findViewById<BpkHorizontalNav>(R.id.tab_bar)
      val viewPager = view.findViewById<ViewPager2>(R.id.view_pager)

      viewPager.adapter = object : FragmentStateAdapter(this) {
        override fun getItemCount(): Int = stories.size

        override fun createFragment(position: Int): Fragment =
          ComponentRegistry.getStoryCreator(stories[position]).createStory()
      }

      TabLayoutMediator(tabBar, viewPager) { tab, position ->
        tab.text = ComponentRegistry.getStoryCreator(stories[position]).name
      }.attach()

      return view
    } else {
      throw IllegalStateException("Story has not been property initialized")
    }
  }

  companion object {
    const val STORIES = "stories"

    infix fun of(stories: Array<String>) = TabStory().apply {
      arguments = Bundle()
      arguments?.putStringArray(STORIES, stories)
    }
  }
}
