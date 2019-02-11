package net.skyscanner.backpack.util

import android.content.Context
import android.content.res.Resources
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.Dimension
import androidx.core.content.res.ResourcesCompat
import android.util.DisplayMetrics

internal object ResourcesUtil {

  @ColorInt
  fun getColor(view: View, @ColorRes id: Int): Int {
    return getColor(view.resources, id, view.context.theme)
  }

  @ColorInt
  fun getColor(res: Resources, @ColorRes id: Int, theme: Resources.Theme? = null): Int {
    return ResourcesCompat.getColor(res, id, theme)
  }

  @Dimension
  fun dpToPx(@Dimension dp: Int, context: Context): Int {
    return Math.round(dp.toFloat() * context.resources.displayMetrics.density)
  }

  @Dimension
  fun pxToDp(px: Float, context: Context): Float {
    return (px / (context.resources.displayMetrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT))
  }
}

internal fun View.getColor(@ColorRes id: Int): Int {
  return ResourcesUtil.getColor(this, id)
}
