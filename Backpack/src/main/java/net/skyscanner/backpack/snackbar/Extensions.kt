package net.skyscanner.backpack.snackbar

import android.view.View
import androidx.annotation.StringRes

inline fun BpkSnackbar.setAction(text: CharSequence, crossinline listener: (View) -> Unit): BpkSnackbar =
  setAction(text, View.OnClickListener { listener(it) })

inline fun BpkSnackbar.setAction(@StringRes resId: Int, crossinline listener: (View) -> Unit): BpkSnackbar =
  setAction(resId, View.OnClickListener { listener(it) })
