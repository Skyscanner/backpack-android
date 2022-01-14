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
import androidx.core.widget.NestedScrollView
import net.skyscanner.backpack.demo.ComponentDetailFragment
import net.skyscanner.backpack.demo.R

open class Story : ComponentDetailFragment() {

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?,
  ): View? {
    val scrollable = arguments?.getBoolean(SCROLLABLE, true) ?: true
    val layoutId = arguments?.getInt(LAYOUT_ID) ?: savedInstanceState?.getInt(LAYOUT_ID)
      ?: throw IllegalStateException("Story has not been property initialized")

    val rootView = if (scrollable) {
      val scrollView = NestedScrollView(requireContext())
      scrollView.id = R.id.scrollable_container
      scrollView.isFillViewport = true
      inflater.inflate(layoutId, scrollView, true)
    } else {
      inflater.inflate(layoutId, container, false)
    }

    if (isRtl) {
      rootView.layoutDirection = View.LAYOUT_DIRECTION_RTL
    }
    return rootView
  }

  protected val isRtl
    get() = arguments?.getBoolean(RTL) == true

  companion object {
    const val LAYOUT_ID = "fragment_id"
    const val RTL = "rtl"
    const val SCROLLABLE = "scrollable"

    infix fun of(fragmentLayout: Int) = Story().apply {
      arguments = Bundle().apply {
        putInt(LAYOUT_ID, fragmentLayout)
        putBoolean(RTL, false)
        putBoolean(SCROLLABLE, true)
      }
    }

    enum class Direction {
      LTR,
      RTL,
    }

    infix fun Story.with(direction: Direction): Story {
      if (arguments == null) {
        arguments = Bundle()
      }
      arguments?.putBoolean(RTL, direction == Companion.Direction.RTL)
      return this
    }

    infix fun Story.scrollable(value: Boolean): Story {
      if (arguments == null) {
        arguments = Bundle()
      }
      arguments?.putBoolean(SCROLLABLE, value)
      return this
    }
  }
}
