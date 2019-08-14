package net.skyscanner.backpack.snackbar

import android.content.res.ColorStateList
import android.graphics.drawable.ColorDrawable
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.IdRes
import androidx.core.graphics.drawable.DrawableCompat
import com.google.android.material.snackbar.Snackbar
import net.skyscanner.backpack.R
import net.skyscanner.backpack.text.BpkFontSpan
import java.lang.ClassCastException

internal fun Snackbar.setBackgroundColorCompat(@ColorInt color: Int) {
  var background = view.background
  if (background != null) {
    background = DrawableCompat.wrap(background.mutate())
    DrawableCompat.setTintList(background, ColorStateList.valueOf(color))
  } else {
    background = ColorDrawable(color)
  }
  view.background = background
}

internal fun Snackbar.setMessageAppearanceCompat(font: BpkFontSpan, color: ForegroundColorSpan) =
  setAppearanceCompat(R.id.snackbar_text, font, color)

internal fun Snackbar.setActionAppearanceCompat(font: BpkFontSpan, color: ForegroundColorSpan) =
  setActionTextColor(color.foregroundColor)
    .also { setAppearanceCompat(R.id.snackbar_action, font, color) }

internal fun Snackbar.customiseText(text: CharSequence, font: BpkFontSpan, color: ForegroundColorSpan): CharSequence =
  SpannableStringBuilder(text).apply {
    setSpan(font, 0, length, Spanned.SPAN_INCLUSIVE_INCLUSIVE)
    setSpan(color, 0, length, Spanned.SPAN_INCLUSIVE_INCLUSIVE)
  }

private fun Snackbar.setAppearanceCompat(@IdRes id: Int, font: BpkFontSpan, color: ForegroundColorSpan) {
  // we have to use this customization method
  // because spannable are not working with action in API 21
  try {
    view.findViewById<TextView>(id)?.let {
      font.updateDrawState(it.paint)
      it.setTextColor(color.foregroundColor)
    }
  } catch (ignored: ClassCastException) {
  }
}
