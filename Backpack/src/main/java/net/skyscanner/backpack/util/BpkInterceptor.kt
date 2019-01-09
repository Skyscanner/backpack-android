package net.skyscanner.backpack.util

import android.graphics.drawable.ColorDrawable
import android.os.Build
import androidx.annotation.RequiresApi
import io.github.inflationx.viewpump.InflateResult
import io.github.inflationx.viewpump.Interceptor

class BpkInterceptor(private val enable: Boolean = false) : Interceptor {

  @RequiresApi(Build.VERSION_CODES.M)
  override fun intercept(chain: Interceptor.Chain): InflateResult {
    val result = chain.proceed(chain.request())
    val view = result.view()

    if (enable && view != null && isBpkView(view.javaClass)) {
      if (view.background == null) {

        view.background = ColorDrawable(0x77ac650c)
      } else {
        view.foreground = ColorDrawable(0x77ac650c)
      }
    }
    return result
  }

  private fun isBpkView(javaClass: Class<Any>): Boolean {
    var inClass = javaClass
    while (inClass.superclass != null) {
      if (inClass.name.toLowerCase().contains("bpk")) {
        return true
      }
      inClass = (inClass.superclass as Class<Any>?)!!
    }
    return false
  }
}
