package net.skyscanner.backpack.util

import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.view.View
import net.skyscanner.backpack.BuildConfig

fun View.enableDebugHighlight() {
  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && BuildConfig.DEBUG) {
    foreground = ColorDrawable(0x77008c4d)
  }
}
