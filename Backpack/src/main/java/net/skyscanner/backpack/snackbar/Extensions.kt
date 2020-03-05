package net.skyscanner.backpack.snackbar

import android.graphics.drawable.Drawable
import android.view.View
import androidx.annotation.AnyRes

inline fun BpkSnackbar.setAction(text: CharSequence, crossinline listener: (View) -> Unit): BpkSnackbar =
  setAction(text, View.OnClickListener { listener(it) })

inline fun BpkSnackbar.setAction(@AnyRes resId: Int, crossinline listener: (View) -> Unit): BpkSnackbar =
  setAction(resId, View.OnClickListener { listener(it) })

inline fun BpkSnackbar.setAction(icon: Drawable, crossinline listener: (View) -> Unit): BpkSnackbar =
  setAction(icon, View.OnClickListener { listener(it) })

fun BpkSnackbar.setOnDismissed(ignoreDismissAfterAction: Boolean = true, callback: () -> Unit) =
  addCallback(object : BpkSnackbar.Callback() {
    override fun onDismissed(transientBottomBar: BpkSnackbar?, event: Int) {
      super.onDismissed(transientBottomBar, event)
      if (ignoreDismissAfterAction && event == DISMISS_EVENT_ACTION) return
      callback()
    }
  })
