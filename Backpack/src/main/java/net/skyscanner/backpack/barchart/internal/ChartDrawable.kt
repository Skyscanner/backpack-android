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

package net.skyscanner.backpack.barchart.internal

import android.content.res.ColorStateList
import android.graphics.Canvas
import android.graphics.ColorFilter
import android.graphics.Paint
import android.graphics.PixelFormat
import android.graphics.drawable.Drawable
import net.skyscanner.backpack.util.getColorForState
import net.skyscanner.backpack.util.withSave
import kotlin.math.min

internal class ChartDrawable(
  private val background: ColorStateList,
  private val foreground: ColorStateList
) : Drawable() {

  private val backgroundPaint = Paint().apply {
    isAntiAlias = true
    isDither = true
    style = Paint.Style.FILL_AND_STROKE
  }

  private val foregroundPaint = Paint().apply {
    isAntiAlias = true
    isDither = true
    style = Paint.Style.FILL_AND_STROKE
  }

  var value: Float = 0f
    set(value) {
      if (field != value) {
        field = value
        invalidateSelf()
      }
    }

  private val totalRange: Float
    get() {
      val minRange = diameter
      val maxRange = bounds.height()
      return maxRange - minRange
    }

  val diameter: Float
    get() = bounds.width().toFloat()

  val radius: Float
    get() = diameter / 2

  val valueInPixels: Float
    get() = totalRange * min(1.0f, value)

  override fun getAlpha(): Int =
    backgroundPaint.alpha

  override fun setAlpha(alpha: Int) {
    backgroundPaint.alpha = alpha
    foregroundPaint.alpha = alpha
  }

  @Deprecated("Deprecated in Java")
  override fun getOpacity(): Int =
    PixelFormat.TRANSPARENT

  override fun setColorFilter(colorFilter: ColorFilter?) {
    backgroundPaint.colorFilter = colorFilter
    foregroundPaint.colorFilter = colorFilter
  }

  override fun getColorFilter(): ColorFilter? =
    backgroundPaint.colorFilter

  override fun isStateful(): Boolean =
    background.isStateful || foreground.isStateful

  override fun onStateChange(state: IntArray): Boolean =
    isStateful

  override fun draw(canvas: Canvas) {
    val width = bounds.width().toFloat()
    val height = bounds.height().toFloat()

    backgroundPaint.color = background.getColorForState(state)
    foregroundPaint.color = foreground.getColorForState(state)

    canvas.withSave {
      canvas.drawRoundRect(0f, 0f, width, height, radius, radius, backgroundPaint)
      val foregroundTop = totalRange - valueInPixels
      canvas.drawRoundRect(0f, foregroundTop, width, height, radius, radius, foregroundPaint)
    }
  }
}
