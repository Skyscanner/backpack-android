package net.skyscanner.backpack.snackbar.internal

import android.content.res.ColorStateList
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.text.SpannableStringBuilder
import android.text.Spanned
import androidx.annotation.ColorInt
import androidx.core.graphics.drawable.DrawableCompat
import com.google.android.material.snackbar.Snackbar
import net.skyscanner.backpack.R
import net.skyscanner.backpack.snackbar.BpkSnackbar

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

internal fun BpkSnackbar.createIconDrawable(drawable: Drawable?, @ColorInt tint: Int) =
  drawable
    ?.mutate()
    ?.let { DrawableCompat.wrap(it) }
    ?.apply { DrawableCompat.setTint(this, tint) }
    ?.apply {
      val size = rawSnackbar.view.resources.getDimensionPixelSize(R.dimen.bpk_icon_size_small)
      setBounds(0, 0, size, size)
    }

internal fun BpkSnackbar.customiseText(text: CharSequence, span: Any): CharSequence =
  SpannableStringBuilder(text).apply {
    setSpan(span, 0, length, Spanned.SPAN_INCLUSIVE_INCLUSIVE)
  }
