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

package net.skyscanner.backpack.dialog.internal

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.ViewGroup.LayoutParams
import android.view.WindowManager
import net.skyscanner.backpack.R

internal class AlertDialogImpl(
  dialog: Dialog,
  bottomSheet: Boolean
) : BpkDialogImpl.Base(R.layout.bpk_dialog, dialog) {

  init {
    dialog.window?.let {
      it.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
      it.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
      it.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
      it.setLayout(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
      it.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
      if (bottomSheet) {
        it.setWindowAnimations(R.style.Bpk_dialog_animation)
      }
    }

    root.findViewById<DialogWindowLayout>(R.id.dialog_window_layout).apply {
      verticalGravity = when {
        bottomSheet -> DialogWindowLayout.Gravity.Bottom
        else -> DialogWindowLayout.Gravity.Center
      }
      dismissListener = {
        if (isCanceledOnTouchOutside) {
          dialog.dismiss()
        }
      }
    }
  }
}
