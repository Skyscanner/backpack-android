package net.skyscanner.backpack.util

import android.graphics.drawable.ColorDrawable
import io.github.inflationx.viewpump.InflateResult
import io.github.inflationx.viewpump.Interceptor


class BpkInterceptor : Interceptor {
  override fun intercept(chain: Interceptor.Chain): InflateResult {
    val result = chain.proceed(chain.request())
    val view = result.view()

    if (view != null && view.javaClass.name.contains("Bpk")) {
      view.background = ColorDrawable(0x77008c4d)
    }
    return result
  }
}
