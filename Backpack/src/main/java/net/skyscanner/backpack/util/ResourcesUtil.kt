package net.skyscanner.backpack.util

import android.content.Context
import android.content.res.Resources
import android.content.res.TypedArray
import android.graphics.drawable.LayerDrawable
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.util.DisplayMetrics
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.Dimension
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

  @Dimension
  fun dpToPx(@Dimension dp: Int, context: Context): Int {
    return dpToPx(dp.toFloat(), context)
  }

  @Dimension
  fun dpToPx(@Dimension dp: Float, context: Context): Int {
    return Math.round(dp * context.resources.displayMetrics.density)
  }

  @Dimension
  fun pxToDp(px: Float, context: Context): Float {
    return (px / (context.resources.displayMetrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT))
  }
}

internal fun View.getColor(@ColorRes id: Int): Int {
  return ResourcesUtil.getColor(this, id)
}

internal inline fun <R> TypedArray?.use(block: (TypedArray) -> R): R? {
  try {
    return this?.let(block)
  } finally {
    this?.recycle()
  }
}

internal operator fun LayerDrawable.get(index: Int) =
  getDrawable(index)

internal fun SpannableStringBuilder.append(text: CharSequence?, what: Any): SpannableStringBuilder {
  return this.append(text ?: "", what, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
}

internal fun SpannableStringBuilder.append(text: CharSequence?, vararg what: Any): SpannableStringBuilder {
  val start = length
  this.append(text ?: "")
  what.forEach {
    setSpan(it, start, length, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
  }
  return this
}
