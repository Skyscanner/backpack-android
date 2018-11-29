package net.skyscanner.backpack.util

import android.graphics.drawable.ColorDrawable
import io.github.inflationx.viewpump.InflateResult
import io.github.inflationx.viewpump.Interceptor

class BpkInterceptor(private val enable: Boolean = false) : Interceptor {

  override fun intercept(chain: Interceptor.Chain): InflateResult {
    val result = chain.proceed(chain.request())
    val view = result.view()

    if (enable && view != null && view.javaClass.name.contains("Bpk")) {
      view.background = ColorDrawable(0x77ac650c)
    }
    return result
  }
}
