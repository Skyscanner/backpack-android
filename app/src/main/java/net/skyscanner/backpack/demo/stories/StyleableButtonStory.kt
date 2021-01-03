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
import android.view.View
import android.view.ViewGroup
import net.skyscanner.backpack.button.BpkButton
import net.skyscanner.backpack.button.BpkButton.Type
import net.skyscanner.backpack.demo.R

class StyleableButtonStory : Story() {

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    view.findViewById<View>(R.id.primary).setOnClickListener { setButtonType(Type.Primary) }
    view.findViewById<View>(R.id.secondary).setOnClickListener { setButtonType(Type.Secondary) }
    view.findViewById<View>(R.id.destructive).setOnClickListener { setButtonType(Type.Destructive) }
    view.findViewById<View>(R.id.featured).setOnClickListener { setButtonType(Type.Featured) }
    view.findViewById<View>(R.id.outline).setOnClickListener { setButtonType(Type.Outline) }
  }

  private fun setButtonType(type: Type) {
    view!!.findViewById<ViewGroup>(R.id.buttonsContainer).run {
      for (i in 0 until childCount) {
        (getChildAt(i) as? BpkButton?)?.type = type
      }
    }
  }

  companion object {
    private const val LAYOUT_ID = "fragment_id"

    infix fun of(fragmentLayout: Int) = StyleableButtonStory().apply {
      arguments = Bundle()
      arguments?.putInt(LAYOUT_ID, fragmentLayout)
    }
  }
}
