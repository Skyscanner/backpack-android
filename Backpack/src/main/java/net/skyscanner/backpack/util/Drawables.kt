/**
 * Backpack for Android - Skyscanner's Design System
 *
 * Copyright 2018 Skyscanner Ltd
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

package net.skyscanner.backpack.util

import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.RippleDrawable
import android.graphics.drawable.StateListDrawable
import android.util.StateSet
import androidx.annotation.ColorInt

internal inline fun stateListDrawable(
  drawable: Drawable,
  disabled: Drawable? = null,
  pressed: Drawable? = null,
  selected: Drawable? = null,
  block: StateListDrawable.() -> Unit = {},
): StateListDrawable = StateListDrawable().apply {
  if (disabled != null) {
    addState(intArrayOf(-android.R.attr.state_enabled), disabled)
  }
  if (pressed != null) {
    addState(intArrayOf(android.R.attr.state_pressed), pressed)
  }
  if (selected != null) {
    addState(intArrayOf(android.R.attr.state_selected), selected)
  }
  addState(StateSet.WILD_CARD, drawable)
  block()
}

internal fun rippleDrawable(
  content: Drawable,
  mask: Drawable,
  @ColorInt rippleColor: Int,
): RippleDrawable {

  val rippleColorStateList = ColorStateList.valueOf(rippleColor)

  return RippleDrawable(
    rippleColorStateList,
    content,
    mask
  )
}

internal fun Drawable.rasterize(
  width: Int = intrinsicWidth,
  height: Int = intrinsicHeight,
  config: Bitmap.Config = Bitmap.Config.ARGB_8888,
): Bitmap {
  setBounds(0, 0, width, height)
  val bitmap = Bitmap.createBitmap(width, height, config)
  bitmap.eraseColor(Color.TRANSPARENT)
  draw(Canvas(bitmap))
  return bitmap
}
