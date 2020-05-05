/**
 * Backpack for Android - Skyscanner's Design System
 *
 * Copyright 2018-2020 Skyscanner Ltd
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

package net.skyscanner.backpack.starrating.internal

import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.graphics.drawable.LayerDrawable
import android.os.Build

internal class HalfStarDrawable(
  private val background: Drawable,
  private val foreground: Drawable
) : LayerDrawable(arrayOf(
  background,
  foreground
)) {

  var rtl = false
    set(value) {
      field = value
      invalidateSelf()
    }

  override fun draw(canvas: Canvas) {
    val count = canvas.save()
    background.draw(canvas)

    if (rtl && Build.VERSION.SDK_INT < 23) {
      val halsSize = bounds.width() / 2f
      canvas.translate(halsSize, 0f)
      canvas.scale(-1f, 1f, halsSize / 2, halsSize / 2)
    }

    foreground.draw(canvas)
    canvas.restoreToCount(count)
  }
}
