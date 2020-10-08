package net.skyscanner.backpack.util

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.graphics.drawable.RippleDrawable
import android.graphics.drawable.StateListDrawable
import android.os.Build
import android.util.StateSet
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import net.skyscanner.backpack.R

internal inline fun stateListDrawable(
  drawable: Drawable,
  disabled: Drawable? = null,
  pressed: Drawable? = null,
  block: StateListDrawable.() -> Unit = {}
): StateListDrawable = StateListDrawable().apply {
  if (disabled != null) {
    addState(intArrayOf(-android.R.attr.state_enabled), disabled)
  }
  if (pressed != null) {
    addState(intArrayOf(android.R.attr.state_pressed), pressed)
  }
  addState(StateSet.WILD_CARD, drawable)
  block()
  if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M) {
    // see https://stackoverflow.com/questions/21085690/android-selector-with-fade-in-fade-out-duration-initially-invisible
    setEnterFadeDuration(0)
    setExitFadeDuration(0)
  }
}

internal inline fun rippleDrawable(
  context: Context,
  content: Drawable,
  mask: Drawable,
  @ColorInt rippleColor: Int? = null
): RippleDrawable {

  val rippleColorStateList = if (rippleColor == null) {
    val colorControlHighlight = resolveThemeColor(context, R.attr.colorControlHighlight)
      ?: ContextCompat.getColor(context, R.color.bpkSkyGrayTint06)
    ColorStateList.valueOf(colorControlHighlight)
  } else {
    ColorStateList.valueOf(rippleColor)
  }

  return RippleDrawable(
    rippleColorStateList,
    content,
    mask
  )
}
