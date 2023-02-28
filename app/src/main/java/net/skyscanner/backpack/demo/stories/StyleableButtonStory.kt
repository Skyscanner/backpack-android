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
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import net.skyscanner.backpack.button.BpkButton
import net.skyscanner.backpack.button.BpkButton.Type
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.demo.components.ButtonComponent
import net.skyscanner.backpack.demo.meta.ViewStory
import net.skyscanner.backpack.demo.ui.AndroidLayout

@Composable
@ButtonComponent
@ViewStory("Styleable")
fun StyleableButtonStory(modifier: Modifier = Modifier) =
  AndroidLayout(R.layout.fragment_buttons_styleable, modifier.fillMaxSize()) {
    findViewById<View>(R.id.primary).setOnClickListener { setButtonType(this, Type.Primary) }
    findViewById<View>(R.id.secondary).setOnClickListener { setButtonType(this, Type.Secondary) }
    findViewById<View>(R.id.destructive).setOnClickListener { setButtonType(this, Type.Destructive) }
    findViewById<View>(R.id.featured).setOnClickListener { setButtonType(this, Type.Featured) }
    findViewById<View>(R.id.primaryOnDark).setOnClickListener { setButtonType(this, Type.PrimaryOnDark) }
    findViewById<View>(R.id.primaryOnLight).setOnClickListener { setButtonType(this, Type.PrimaryOnLight) }
  }

class StyleableButtonFragment : Story() {

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    view.findViewById<View>(R.id.primary).setOnClickListener { setButtonType(view, Type.Primary) }
    view.findViewById<View>(R.id.secondary).setOnClickListener { setButtonType(view, Type.Secondary) }
    view.findViewById<View>(R.id.destructive).setOnClickListener { setButtonType(view, Type.Destructive) }
    view.findViewById<View>(R.id.featured).setOnClickListener { setButtonType(view, Type.Featured) }
    view.findViewById<View>(R.id.primaryOnDark).setOnClickListener { setButtonType(view, Type.PrimaryOnDark) }
    view.findViewById<View>(R.id.primaryOnLight).setOnClickListener { setButtonType(view, Type.PrimaryOnLight) }
  }

  companion object {
    private const val LAYOUT_ID = "fragment_id"

    infix fun of(fragmentLayout: Int) = StyleableButtonFragment().apply {
      arguments = Bundle()
      arguments?.putInt(LAYOUT_ID, fragmentLayout)
    }
  }
}

private fun setButtonType(view: View, type: Type) {
  view.findViewById<ViewGroup>(R.id.buttonsContainer).run {
    for (i in 0 until childCount) {
      (getChildAt(i) as? BpkButton?)?.type = type
    }
  }
}
