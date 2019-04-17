package net.skyscanner.backpack.util

import android.content.Context
import android.content.ContextWrapper
import io.github.inflationx.viewpump.ViewPumpContextWrapper

object BpkViewPumpContextWrapper {
  /**
   * Disables Viewpump on Android Q and above.
   * See https://github.com/InflationX/ViewPump/issues/21
   */
  fun wrap(context: Context): Context {
    return if (android.os.Build.VERSION.SDK_INT > android.os.Build.VERSION_CODES.P) {
      ContextWrapper(context)
    } else {
      ViewPumpContextWrapper.wrap(context)
    }
  }
}
