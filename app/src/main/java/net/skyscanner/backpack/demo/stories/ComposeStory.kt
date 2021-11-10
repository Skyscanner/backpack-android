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
import androidx.compose.ui.platform.ComposeView
import net.skyscanner.backpack.demo.BackpackDemoTheme
import net.skyscanner.backpack.demo.data.ComponentRegistry
import net.skyscanner.backpack.demo.data.ComposeNode

open class ComposeStory : Story() {

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    val composable = arguments?.getString(ID) ?: savedInstanceState?.getString(ID)
    if (composable != null) {
      return ComposeView(requireContext()).apply {
        setContent {
          BackpackDemoTheme {
            (ComponentRegistry.getStoryCreator(composable) as ComposeNode).composable()
          }
        }
      }
    } else {
      throw IllegalStateException("Story has not been property initialized")
    }
  }

  companion object {
    const val ID = "id"

    infix fun of(id: String) = ComposeStory().apply {
      arguments = Bundle()
      arguments?.putString(ID, id)
    }
  }
}
