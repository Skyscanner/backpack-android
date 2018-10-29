package net.skyscanner.backpack.util

import android.content.res.Resources
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.core.content.res.ResourcesCompat

internal object ResourcesUtil {

  @ColorInt
  fun getColor(view: View, @ColorRes id: Int): Int {
    return getColor(view.resources, id, view.context.theme)
  }

  @ColorInt
  fun getColor(res: Resources, @ColorRes id: Int, theme: Resources.Theme? = null): Int {
    return ResourcesCompat.getColor(res, id, theme)
  }
}

internal fun View.getColor(@ColorRes id: Int): Int {
  return ResourcesUtil.getColor(this, id)
}
