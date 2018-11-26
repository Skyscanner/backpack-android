package net.skyscanner.backpack.util

import android.os.Build
import android.view.View
import net.skyscanner.backpack.R

interface Tooling {
  fun highlight(v: View)
}

class BpkView : Tooling {
  override fun highlight(v: View) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      v.foreground = v.context.resources.getDrawable(R.color.bpkRed500, v.context.theme)
    }
  }
}

