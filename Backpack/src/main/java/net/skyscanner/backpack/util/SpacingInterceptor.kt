package net.skyscanner.backpack.util

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.content.res.ResourcesCompat
import io.github.inflationx.viewpump.InflateResult
import io.github.inflationx.viewpump.Interceptor
import net.skyscanner.backpack.R

class SpacingInterceptor : Interceptor {

  @RequiresApi(Build.VERSION_CODES.M)
  override fun intercept(chain: Interceptor.Chain): InflateResult {
    val result = chain.proceed(chain.request())
    val view = result.view()
    if (view != null) {
      val padding = listOf(view.paddingBottom, view.paddingLeft, view.paddingRight, view.paddingTop)
      val validSpaces = listOf(0,
        view.context.resources.getDimension(R.dimen.bpkSpacingBase).toInt(),
        view.context.resources.getDimension(R.dimen.bpkSpacingLg).toInt(),
        view.context.resources.getDimension(R.dimen.bpkSpacingMd).toInt(),
        view.context.resources.getDimension(R.dimen.bpkSpacingSm).toInt(),
        view.context.resources.getDimension(R.dimen.bpkSpacingXl).toInt(),
        view.context.resources.getDimension(R.dimen.bpkSpacingXxl).toInt())

      if (!validSpaces.containsAll(padding)) {
        if (view.background == null) {
          view.background = ResourcesCompat.getDrawable(view.resources, R.drawable.bpk_internal_elevation_highlight, view.context.theme)
        } else {
          view.foreground = ResourcesCompat.getDrawable(view.resources, R.drawable.bpk_internal_elevation_highlight, view.context.theme)
        }
      }
    }
    return result
  }
}
