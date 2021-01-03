/**
 * Backpack for Android - Skyscanner's Design System
 *
 * Copyright 2018-2021 Skyscanner Ltd
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
