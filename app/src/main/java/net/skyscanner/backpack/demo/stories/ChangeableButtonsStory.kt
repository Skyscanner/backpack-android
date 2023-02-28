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
import android.widget.TextView
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.demo.components.ButtonComponent
import net.skyscanner.backpack.demo.meta.ViewStory
import net.skyscanner.backpack.demo.ui.AndroidLayout
import kotlin.math.max

@Composable
@ButtonComponent
@ViewStory("Changeable")
fun ChangeableButtonsStory(modifier: Modifier = Modifier) =
  AndroidLayout(R.layout.fragment_buttons_changeable, modifier.fillMaxSize()) {

    findViewById<TextView>(R.id.button_increase).setOnClickListener {
      it as TextView
      it.text = context.getString(R.string.button_increased, it.text.toString())
    }

    findViewById<TextView>(R.id.button_decrease).setOnClickListener {
      it as TextView
      it.text = it.text.substring(0, max(0, it.length() - 1))
    }
  }

class ChangeableButtonsFragment : Story() {

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    view.findViewById<TextView>(R.id.button_increase).setOnClickListener {
      it as TextView
      it.text = getString(R.string.button_increased, it.text.toString())
    }

    view.findViewById<TextView>(R.id.button_decrease).setOnClickListener {
      it as TextView
      it.text = it.text.substring(0, max(0, it.length() - 1))
    }
  }

  companion object {
    private const val LAYOUT_ID = "fragment_id"

    infix fun of(fragmentLayout: Int) = ChangeableButtonsFragment().apply {
      arguments = Bundle()
      arguments?.putInt(LAYOUT_ID, fragmentLayout)
    }
  }
}
