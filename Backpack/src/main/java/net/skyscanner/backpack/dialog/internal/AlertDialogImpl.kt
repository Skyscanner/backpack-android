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
