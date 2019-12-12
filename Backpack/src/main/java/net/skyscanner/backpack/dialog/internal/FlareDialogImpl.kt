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
import kotlin.math.min

internal class FlareDialogImpl(
  dialog: Dialog
) : BpkDialogImpl.Base(R.layout.bpk_dialog_flare, dialog) {

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
