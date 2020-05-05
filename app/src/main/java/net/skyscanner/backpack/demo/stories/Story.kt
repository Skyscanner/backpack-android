/**
 * Backpack for Android - Skyscanner's Design System
 *
 * Copyright 2018-2020 Skyscanner Ltd
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
import net.skyscanner.backpack.demo.ComponentDetailFragment

open class Story : ComponentDetailFragment() {

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    val layoutId = arguments?.getInt(LAYOUT_ID) ?: savedInstanceState?.getInt(LAYOUT_ID)
    if (layoutId != null) {
      return inflater.inflate(layoutId, container, false).apply {
        if (isRtl) {
          layoutDirection = View.LAYOUT_DIRECTION_RTL
        }
      }
    } else {
      throw IllegalStateException("Story has not been property initialized")
    }
  }

  protected val isRtl
    get() = arguments?.getBoolean(RTL) == true

  companion object {
    const val LAYOUT_ID = "fragment_id"
    const val RTL = "rtl"

    infix fun of(fragmentLayout: Int) = Story().apply {
      arguments = Bundle().apply {
        putInt(LAYOUT_ID, fragmentLayout)
        putBoolean(RTL, false)
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
  }
}
