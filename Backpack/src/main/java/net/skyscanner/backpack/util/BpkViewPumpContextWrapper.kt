package net.skyscanner.backpack.util

import android.content.Context
import android.content.ContextWrapper
import io.github.inflationx.viewpump.ViewPumpContextWrapper

class BpkViewPumpContextWrapper {
  companion object {
    fun wrap(context: Context): ContextWrapper {
      return if (android.os.Build.VERSION.SDK_INT > android.os.Build.VERSION_CODES.P) {
        ContextWrapper(context)
      } else {
        ViewPumpContextWrapper.wrap(context)
      }
    }
  }
}
