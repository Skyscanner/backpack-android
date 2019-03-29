package net.skyscanner.backpack.util

import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RectShape
import android.os.Build
import androidx.annotation.RequiresApi
import io.github.inflationx.viewpump.InflateResult
import io.github.inflationx.viewpump.Interceptor

class BpkInterceptor : Interceptor {

  @RequiresApi(Build.VERSION_CODES.M)
  override fun intercept(chain: Interceptor.Chain): InflateResult {
    val result = chain.proceed(chain.request())
    val view = result.view()
    // brown marker highlight color
    val highlightColor = 0x77ac650c

    if (view != null && isBpkView(view.javaClass)) {
      val drawable = ShapeDrawable(RectShape())
      drawable.paint.color = highlightColor
      view.viewTreeObserver.addOnPreDrawListener {
        drawable.setBounds(0, 0, view.width, view.height)
        view.overlay.clear()
        view.overlay.add(drawable)
        true
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
