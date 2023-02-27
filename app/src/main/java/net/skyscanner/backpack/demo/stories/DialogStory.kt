/*
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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.view.forEach
import com.squareup.picasso.Picasso
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.demo.components.DialogComponent
import net.skyscanner.backpack.demo.meta.ViewStory
import net.skyscanner.backpack.demo.ui.AndroidLayout
import net.skyscanner.backpack.dialog.BpkDialog

@Composable
@DialogComponent
@ViewStory
fun DialogStory(modifier: Modifier = Modifier) =
  AndroidLayout(R.layout.fragment_dialog, modifier) {
    findViewById<ViewGroup>(R.id.dialog_root).forEach { buttonView ->
      buttonView.setOnClickListener {
        it.handleOnClick(buttonView)
      }
    }
  }

class DialogStory : Story() {

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?,
  ): View? = inflater.inflate(R.layout.fragment_dialog, container, false)

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    view.findViewById<ViewGroup>(R.id.dialog_root).forEach { buttonView ->
      buttonView.setOnClickListener {
        it.handleOnClick(buttonView)
      }
    }
  }
}

private fun View.handleOnClick(view: View) {
  when (view.id) {
    R.id.dialog_success_one_button -> successOneButtonDialogExample()
    R.id.dialog_success_two_buttons -> successTwoButtonsDialogExample()
    R.id.dialog_success_three_buttons -> successThreeButtonsDialogExample()
    R.id.dialog_warning -> warningDialogExample()
    R.id.dialog_destructive -> destructiveDialogExample()
    R.id.dialog_no_icon -> noIconDialogExample()
    R.id.dialog_long_text -> longDialogExample()
    R.id.dialog_flare -> flareDialogExample()
  }
}

private fun View.successOneButtonDialogExample() {
  BpkDialog(context, BpkDialog.Type.Success).apply {
    title = context.getString(R.string.dialog_title)
    description = context.getString(R.string.dialog_text)
    icon = BpkDialog.Icon(R.drawable.bpk_tick)
    addActionButton(
      BpkDialog.Button(context.getString(R.string.dialog_confirmation)) {
        dismiss()
      },
    )
  }.show()
}

private fun View.successTwoButtonsDialogExample() {
  BpkDialog(context, BpkDialog.Type.Success).apply {
    title = context.getString(R.string.dialog_title)
    description = context.getString(R.string.dialog_text)
    icon = BpkDialog.Icon(R.drawable.bpk_tick)
    addActionButton(
      BpkDialog.Button(context.getString(R.string.dialog_confirmation)) {
        dismiss()
      },
    )
    addActionButton(
      BpkDialog.Button(context.getString(R.string.dialog_skip)) {
        dismiss()
      },
    )
  }.show()
}

private fun View.successThreeButtonsDialogExample() {
  BpkDialog(context, BpkDialog.Type.Success).apply {
    title = context.getString(R.string.dialog_title)
    description = context.getString(R.string.dialog_text)
    icon = BpkDialog.Icon(R.drawable.bpk_tick)
    addActionButton(
      BpkDialog.Button(context.getString(R.string.dialog_confirmation)) {
        dismiss()
      },
    )
    addActionButton(
      BpkDialog.Button(context.getString(R.string.dialog_skip)) {
        dismiss()
      },
    )
    addActionButton(
      BpkDialog.Button(context.getString(R.string.dialog_link_optional)) {
        dismiss()
      },
    )
  }.show()
}

private fun View.warningDialogExample() {
  BpkDialog(context, BpkDialog.Type.Warning).apply {
    title = context.getString(R.string.dialog_title)
    description = context.getString(R.string.dialog_text)
    icon = BpkDialog.Icon(R.drawable.bpk_alert__add)
    addActionButton(
      BpkDialog.Button(context.getString(R.string.dialog_confirmation)) {
        dismiss()
      },
    )
    addActionButton(
      BpkDialog.Button(context.getString(R.string.dialog_skip)) {
        dismiss()
      },
    )
    addActionButton(
      BpkDialog.Button(context.getString(R.string.dialog_link_optional)) {
        dismiss()
      },
    )
  }.show()
}

private fun View.destructiveDialogExample() {
  BpkDialog(context, BpkDialog.Type.Destructive).apply {
    title = context.getString(R.string.dialog_title)
    description = context.getString(R.string.dialog_text)
    icon = BpkDialog.Icon(R.drawable.bpk_trash)
    addActionButton(
      BpkDialog.Button(context.getString(R.string.dialog_delete)) {
        dismiss()
      },
    )
    addActionButton(
      BpkDialog.Button(context.getString(R.string.dialog_cancel)) {
        dismiss()
      },
    )
  }.show()
}

private fun View.longDialogExample() {
  BpkDialog(context, BpkDialog.Type.Success).apply {
    title = context.getString(R.string.dialog_title)
    description = context.getString(R.string.stub).repeat(3)
    addActionButton(
      BpkDialog.Button(context.getString(R.string.dialog_continue)) {
        dismiss()
      },
    )
    addActionButton(
      BpkDialog.Button(context.getString(R.string.dialog_skip)) {
        dismiss()
      },
    )
  }.show()
}

private fun View.noIconDialogExample() {
  BpkDialog(context, BpkDialog.Type.Success).apply {
    title = context.getString(R.string.dialog_title)
    description = context.getString(R.string.dialog_text)
    addActionButton(
      BpkDialog.Button(context.getString(R.string.dialog_confirmation)) {
        dismiss()
      },
    )
    addActionButton(
      BpkDialog.Button(context.getString(R.string.dialog_skip)) {
        dismiss()
      },
    )
  }.show()
}

private fun View.flareDialogExample() {
  BpkDialog(context, BpkDialog.Type.Flare).apply {
    title = context.getString(R.string.dialog_title)
    description = context.getString(R.string.dialog_text)
    Picasso.get().load("file:///android_asset/dialog_sample.jpg").noFade().into(image)

    addActionButton(
      BpkDialog.Button(context.getString(R.string.dialog_confirmation)) {
        dismiss()
      },
    )
    addActionButton(
      BpkDialog.Button(context.getString(R.string.dialog_skip)) {
        dismiss()
      },
    )
  }.show()
}
