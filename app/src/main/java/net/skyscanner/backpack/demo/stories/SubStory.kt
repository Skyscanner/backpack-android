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

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.platform.ComposeView
import net.skyscanner.backpack.demo.BackpackDemoTheme
import net.skyscanner.backpack.demo.ComponentDetailActivity
import net.skyscanner.backpack.demo.compose.ComponentItem

open class SubStory : Story() {

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?,
  ): View? {
    val stories = arguments?.getStringArray(STORIES) ?: savedInstanceState?.getStringArray(STORIES)
    if (stories != null) {
      return ComposeView(requireContext()).apply {
        setContent {
          BackpackDemoTheme {
            LazyColumn {
              items(stories) {
                ComponentItem(title = it) {
                  val intent = Intent(context, ComponentDetailActivity::class.java)
                  intent.putExtra(ARG_ITEM_ID, it)
                  startActivity(intent)
                }
              }
            }
          }
        }
      }
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
