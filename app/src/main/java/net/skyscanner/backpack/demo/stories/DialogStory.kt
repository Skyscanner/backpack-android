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
import androidx.core.view.forEach
import com.squareup.picasso.Picasso
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.dialog.BpkDialog

class DialogStory : Story() {

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? = inflater.inflate(R.layout.fragment_dialog, container, false)

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    view.findViewById<ViewGroup>(R.id.dialog_root).forEach { view ->
      view.setOnClickListener {
        handleOnClick(view)
      }
    }
  }

  private fun handleOnClick(view: View) {
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

  private fun successOneButtonDialogExample() {
    BpkDialog(requireContext(), BpkDialog.Type.Success).apply {
      title = getString(R.string.dialog_title)
      description = getString(R.string.dialog_text)
      icon = BpkDialog.Icon(R.drawable.bpk_tick)
      addActionButton(
        BpkDialog.Button(getString(R.string.dialog_confirmation)) {
          dismiss()
        }
      )
    }.show()
  }

  private fun successTwoButtonsDialogExample() {
    BpkDialog(requireContext(), BpkDialog.Type.Success).apply {
      title = getString(R.string.dialog_title)
      description = getString(R.string.dialog_text)
      icon = BpkDialog.Icon(R.drawable.bpk_tick)
      addActionButton(
        BpkDialog.Button(getString(R.string.dialog_confirmation)) {
          dismiss()
        }
      )
      addActionButton(
        BpkDialog.Button(getString(R.string.dialog_skip)) {
          dismiss()
        }
      )
    }.show()
  }

  private fun successThreeButtonsDialogExample() {
    BpkDialog(requireContext(), BpkDialog.Type.Success).apply {
      title = getString(R.string.dialog_title)
      description = getString(R.string.dialog_text)
      icon = BpkDialog.Icon(R.drawable.bpk_tick)
      addActionButton(
        BpkDialog.Button(getString(R.string.dialog_confirmation)) {
          dismiss()
        }
      )
      addActionButton(
        BpkDialog.Button(getString(R.string.dialog_skip)) {
          dismiss()
        }
      )
      addActionButton(
        BpkDialog.Button(getString(R.string.dialog_link_optional)) {
          dismiss()
        }
      )
    }.show()
  }

  private fun warningDialogExample() {
    BpkDialog(requireContext(), BpkDialog.Type.Warning).apply {
      title = getString(R.string.dialog_title)
      description = getString(R.string.dialog_text)
      icon = BpkDialog.Icon(R.drawable.bpk_alert__add)
      addActionButton(
        BpkDialog.Button(getString(R.string.dialog_confirmation)) {
          dismiss()
        }
      )
      addActionButton(
        BpkDialog.Button(getString(R.string.dialog_skip)) {
          dismiss()
        }
      )
      addActionButton(
        BpkDialog.Button(getString(R.string.dialog_link_optional)) {
          dismiss()
        }
      )
    }.show()
  }

  private fun destructiveDialogExample() {
    BpkDialog(requireContext(), BpkDialog.Type.Danger).apply {
      title = getString(R.string.dialog_title)
      description = getString(R.string.dialog_text)
      icon = BpkDialog.Icon(R.drawable.bpk_trash)
      addActionButton(
        BpkDialog.Button(getString(R.string.dialog_delete)) {
          dismiss()
        }
      )
      addActionButton(
        BpkDialog.Button(getString(R.string.dialog_cancel)) {
          dismiss()
        }
      )
    }.show()
  }

  fun longDialogExample() {
    BpkDialog(requireContext(), BpkDialog.Type.NoIcon).apply {
      title = getString(R.string.dialog_title)
      description = getString(R.string.stub).repeat(3)
      addActionButton(
        BpkDialog.Button(getString(R.string.dialog_continue)) {
          dismiss()
        }
      )
      addActionButton(
        BpkDialog.Button(getString(R.string.dialog_skip)) {
          dismiss()
        }
      )
    }.show()
  }

  fun noIconDialogExample() {
    BpkDialog(requireContext(), BpkDialog.Type.NoIcon).apply {
      title = getString(R.string.dialog_title)
      description = getString(R.string.dialog_text)
      addActionButton(
        BpkDialog.Button(getString(R.string.dialog_confirmation)) {
          dismiss()
        }
      )
      addActionButton(
        BpkDialog.Button(getString(R.string.dialog_skip)) {
          dismiss()
        }
      )
    }.show()
  }

  fun flareDialogExample() {
    BpkDialog(requireContext(), BpkDialog.Type.Flare).apply {
      title = getString(R.string.dialog_title)
      description = getString(R.string.dialog_text)
      Picasso.get().load("file:///android_asset/dialog_sample.jpg").into(image)

      addActionButton(
        BpkDialog.Button(getString(R.string.dialog_confirmation)) {
          dismiss()
        }
      )
      addActionButton(
        BpkDialog.Button(getString(R.string.dialog_skip)) {
          dismiss()
        }
      )
    }.show()
  }
}
