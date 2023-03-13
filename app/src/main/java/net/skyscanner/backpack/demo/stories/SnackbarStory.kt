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

import android.view.View
import android.widget.TextView
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.demo.components.SnackbarComponent
import net.skyscanner.backpack.demo.meta.StoryKind
import net.skyscanner.backpack.demo.meta.ViewStory
import net.skyscanner.backpack.demo.ui.AndroidLayout
import net.skyscanner.backpack.demo.ui.LocalAutomationMode
import net.skyscanner.backpack.snackbar.BpkSnackbar

@Composable
@SnackbarComponent
@ViewStory
fun SnackbarStory(
  modifier: Modifier = Modifier,
  init: View.() -> Unit = {},
) {
  val automationMode = LocalAutomationMode.current
  SnackbarDemo(modifier) {
    if (automationMode) {
      findViewById<View>(R.id.snackbar_indefinite).performClick()
    }
  }
}

@Composable
@SnackbarComponent
@ViewStory("Icon", StoryKind.ScreenshotOnly)
fun SnackbarScreenshotWithActionAndIcon(modifier: Modifier = Modifier) {
  val automationMode = LocalAutomationMode.current
  SnackbarDemo(modifier) {
    if (automationMode) {
      findViewById<View>(R.id.snackbar_title_icon_iconOnly).performClick()
    }
  }
}

@Composable
private fun SnackbarDemo(
  modifier: Modifier = Modifier,
  init: View.() -> Unit = {},
) =
  AndroidLayout(R.layout.fragment_snackbar, modifier.fillMaxSize()) {

    findViewById<TextView>(R.id.snackbar_short).setOnClickListener {
      it as TextView
      BpkSnackbar.make(this, it.text, BpkSnackbar.LENGTH_SHORT)
        .setAction(R.string.snackbar_action) {}
        .show()
    }

    findViewById<TextView>(R.id.snackbar_long).setOnClickListener {
      it as TextView
      BpkSnackbar.make(this, it.text, BpkSnackbar.LENGTH_LONG)
        .setAction(R.string.snackbar_action) {}
        .show()
    }

    findViewById<TextView>(R.id.snackbar_indefinite).setOnClickListener {
      it as TextView
      BpkSnackbar.make(this, it.text, BpkSnackbar.LENGTH_INDEFINITE)
        .setAction(R.string.snackbar_action) {}
        .show()
    }

    findViewById<TextView>(R.id.snackbar_title).setOnClickListener {
      it as TextView
      BpkSnackbar.make(this, R.string.snackbar_message, BpkSnackbar.LENGTH_INDEFINITE)
        .setTitle(context.getString(R.string.snackbar_title))
        .setAction(R.string.snackbar_action) {}
        .show()
    }

    findViewById<TextView>(R.id.snackbar_icon).setOnClickListener {
      it as TextView
      BpkSnackbar.make(this, R.string.snackbar_message, BpkSnackbar.LENGTH_INDEFINITE)
        .setIcon(R.drawable.bpk_tick_circle)
        .setAction(R.string.snackbar_action) {}
        .show()
    }

    findViewById<TextView>(R.id.snackbar_title_icon).setOnClickListener {
      it as TextView
      BpkSnackbar.make(this, R.string.snackbar_message, BpkSnackbar.LENGTH_INDEFINITE)
        .setTitle(context.getString(R.string.snackbar_title))
        .setIcon(R.drawable.bpk_tick_circle)
        .setAction(R.string.snackbar_action) {}
        .show()
    }

    findViewById<TextView>(R.id.snackbar_iconOnly).setOnClickListener {
      it as TextView
      BpkSnackbar.make(this, R.string.snackbar_message, BpkSnackbar.LENGTH_INDEFINITE)
        .setAction(R.drawable.bpk_close, context.getString(R.string.snackbar_icon_content_description)) { }
        .show()
    }

    findViewById<TextView>(R.id.snackbar_title_iconOnly).setOnClickListener {
      it as TextView
      BpkSnackbar.make(this, R.string.snackbar_message, BpkSnackbar.LENGTH_INDEFINITE)
        .setTitle(context.getString(R.string.snackbar_title))
        .setAction(R.drawable.bpk_close, context.getString(R.string.snackbar_icon_content_description)) { }
        .show()
    }

    findViewById<TextView>(R.id.snackbar_icon_iconOnly).setOnClickListener {
      it as TextView
      BpkSnackbar.make(this, R.string.snackbar_message, BpkSnackbar.LENGTH_INDEFINITE)
        .setIcon(R.drawable.bpk_tick_circle)
        .setAction(R.drawable.bpk_close, context.getString(R.string.snackbar_icon_content_description)) { }
        .show()
    }

    findViewById<TextView>(R.id.snackbar_title_icon_iconOnly).setOnClickListener {
      it as TextView
      BpkSnackbar.make(this, R.string.snackbar_message, BpkSnackbar.LENGTH_INDEFINITE)
        .setTitle(context.getString(R.string.snackbar_title))
        .setAction(R.drawable.bpk_close, context.getString(R.string.snackbar_icon_content_description)) { }
        .setIcon(R.drawable.bpk_tick_circle)
        .show()
    }

    init()
  }
