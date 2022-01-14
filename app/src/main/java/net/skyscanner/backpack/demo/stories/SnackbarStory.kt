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
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.snackbar.BpkSnackbar

class SnackbarStory : Story() {

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    view.findViewById<TextView>(R.id.snackbar_short).setOnClickListener {
      it as TextView
      BpkSnackbar.make(view, it.text, BpkSnackbar.LENGTH_SHORT)
        .setAction(R.string.snackbar_action) {}
        .show()
    }

    view.findViewById<TextView>(R.id.snackbar_long).setOnClickListener {
      it as TextView
      BpkSnackbar.make(view, it.text, BpkSnackbar.LENGTH_LONG)
        .setAction(R.string.snackbar_action) {}
        .show()
    }

    view.findViewById<TextView>(R.id.snackbar_indefinite).setOnClickListener {
      it as TextView
      BpkSnackbar.make(view, it.text, BpkSnackbar.LENGTH_INDEFINITE)
        .setAction(R.string.snackbar_action) {}
        .show()
    }

    view.findViewById<TextView>(R.id.snackbar_title).setOnClickListener {
      it as TextView
      BpkSnackbar.make(view, R.string.snackbar_message, BpkSnackbar.LENGTH_INDEFINITE)
        .setTitle(getString(R.string.snackbar_title))
        .setAction(R.string.snackbar_action) {}
        .show()
    }

    view.findViewById<TextView>(R.id.snackbar_icon).setOnClickListener {
      it as TextView
      BpkSnackbar.make(view, R.string.snackbar_message, BpkSnackbar.LENGTH_INDEFINITE)
        .setIcon(R.drawable.bpk_tick_circle)
        .setAction(R.string.snackbar_action) {}
        .show()
    }

    view.findViewById<TextView>(R.id.snackbar_title_icon).setOnClickListener {
      it as TextView
      BpkSnackbar.make(view, R.string.snackbar_message, BpkSnackbar.LENGTH_INDEFINITE)
        .setTitle(getString(R.string.snackbar_title))
        .setIcon(R.drawable.bpk_tick_circle)
        .setAction(R.string.snackbar_action) {}
        .show()
    }

    view.findViewById<TextView>(R.id.snackbar_iconOnly).setOnClickListener {
      it as TextView
      BpkSnackbar.make(view, R.string.snackbar_message, BpkSnackbar.LENGTH_INDEFINITE)
        .setAction(R.drawable.bpk_close, getString(R.string.snackbar_icon_content_description)) { }
        .show()
    }

    view.findViewById<TextView>(R.id.snackbar_title_iconOnly).setOnClickListener {
      it as TextView
      BpkSnackbar.make(view, R.string.snackbar_message, BpkSnackbar.LENGTH_INDEFINITE)
        .setTitle(getString(R.string.snackbar_title))
        .setAction(R.drawable.bpk_close, getString(R.string.snackbar_icon_content_description)) { }
        .show()
    }

    view.findViewById<TextView>(R.id.snackbar_icon_iconOnly).setOnClickListener {
      it as TextView
      BpkSnackbar.make(view, R.string.snackbar_message, BpkSnackbar.LENGTH_INDEFINITE)
        .setIcon(R.drawable.bpk_tick_circle)
        .setAction(R.drawable.bpk_close, getString(R.string.snackbar_icon_content_description)) { }
        .show()
    }

    view.findViewById<TextView>(R.id.snackbar_title_icon_iconOnly).setOnClickListener {
      it as TextView
      BpkSnackbar.make(view, R.string.snackbar_message, BpkSnackbar.LENGTH_INDEFINITE)
        .setTitle(getString(R.string.snackbar_title))
        .setAction(R.drawable.bpk_close, getString(R.string.snackbar_icon_content_description)) { }
        .setIcon(R.drawable.bpk_tick_circle)
        .show()
    }
  }

  companion object {
    private const val LAYOUT_ID = "fragment_id"

    infix fun of(fragmentLayout: Int) = SnackbarStory().apply {
      arguments = Bundle()
      arguments?.putInt(LAYOUT_ID, fragmentLayout)
    }
  }
}
