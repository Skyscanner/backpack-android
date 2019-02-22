package net.skyscanner.backpack.util

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.content.res.ResourcesCompat
import io.github.inflationx.viewpump.InflateResult
import io.github.inflationx.viewpump.Interceptor
import net.skyscanner.backpack.R

class ElevationInterceptor : Interceptor {

  @RequiresApi(Build.VERSION_CODES.M)
  override fun intercept(chain: Interceptor.Chain): InflateResult {
    val result = chain.proceed(chain.request())
    val view = result.view()
    if (view != null) {
      val validSpaces = listOf(0.0f,
        view.context.resources.getDimension(R.dimen.bpkElevationBase),
        view.context.resources.getDimension(R.dimen.bpkElevationLg),
        view.context.resources.getDimension(R.dimen.bpkElevationSm),
        view.context.resources.getDimension(R.dimen.bpkElevationXl),
        view.context.resources.getDimension(R.dimen.bpkElevationXs),
        view.context.resources.getDimension(R.dimen.bpkElevationXxl))

      val elevation = ResourcesUtil.pxToDp(view.elevation, view.context)
      if (elevation !in validSpaces) {
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
