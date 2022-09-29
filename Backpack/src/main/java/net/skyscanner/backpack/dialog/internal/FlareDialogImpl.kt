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

package net.skyscanner.backpack.dialog.internal

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.InsetDrawable
import android.util.DisplayMetrics
import android.view.ViewGroup.LayoutParams
import android.view.WindowManager
import net.skyscanner.backpack.R
import net.skyscanner.backpack.dialog.BpkDialog
import kotlin.math.min

internal class FlareDialogImpl(
  dialog: Dialog,
) : BpkDialogImpl.Base(R.layout.bpk_dialog_flare, dialog, BpkDialog.Type.Flare) {

  init {
    dialog.window?.let {
      val maxWidth = dialog.context.resources.getDimensionPixelSize(R.dimen.bpk_dialog_flare_max_width)
      val displayWidth = getScreenWidth(dialog)
      it.setLayout(min(displayWidth, maxWidth), LayoutParams.WRAP_CONTENT)

      val background = ColorDrawable(Color.TRANSPARENT)
      val margin = dialog.context.resources.getDimensionPixelSize(R.dimen.bpkSpacingBase)
      it.setBackgroundDrawable(InsetDrawable(background, margin))
    }
  }

  private fun getScreenWidth(dialog: Dialog): Int {
    val metrics = DisplayMetrics()
    val windowManager = dialog.context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    windowManager.defaultDisplay.getMetrics(metrics)
    return metrics.widthPixels
  }
}
