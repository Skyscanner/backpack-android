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

package net.skyscanner.backpack.calendar2.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.ColorFilter
import android.graphics.Paint
import android.graphics.PixelFormat
import android.graphics.drawable.Drawable
import net.skyscanner.backpack.R
import net.skyscanner.backpack.calendar2.CellStatus
import net.skyscanner.backpack.util.getColorForState
import net.skyscanner.backpack.util.smallestDimension

internal typealias CalendarDayStatusBackground = (CellStatus?) -> Drawable

internal fun CalendarDayStatusBackground(
  context: Context,
): CalendarDayStatusBackground {
  val impl = CalendarDayStatusDrawable(context)

  return { status ->
    impl.apply {
      this.status = status
    }
  }
}

private class CalendarDayStatusDrawable(context: Context) : Drawable() {

  var status: CellStatus? = null
    set(value) {
      field = value
      invalidateSelf()
    }

  private val colorPositive = context.getColorStateList(R.color.__calendarCellStylePositiveColor)
  private val colorNeutral = context.getColorStateList(R.color.__calendarCellStyleNeutralColor)
  private val colorNegative = context.getColorStateList(R.color.__calendarCellStyleNegativeColor)
  private val colorHighlight = context.getColorStateList(R.color.__calendarHighlightedDayDot)
  private val colorEmpty = context.getColorStateList(R.color.__calendarCellStyleEmptyColor)

  private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply { style = Paint.Style.FILL }

  override fun draw(canvas: Canvas) {
    when (status) {
      CellStatus.Highlighted -> paint.color = colorHighlight.getColorForState(state)
      CellStatus.Positive -> paint.color = colorPositive.getColorForState(state)
      CellStatus.Neutral -> paint.color = colorNeutral.getColorForState(state)
      CellStatus.Negative -> paint.color = colorNegative.getColorForState(state)
      CellStatus.Empty -> paint.color = colorEmpty.getColorForState(state)
      CellStatus.Disabled, null -> return
    }

    val bounds = bounds
    canvas.drawCircle(bounds.centerX().toFloat(), bounds.centerY().toFloat(), bounds.smallestDimension() / 2f, paint)
  }

  override fun setAlpha(alpha: Int) {
    paint.alpha = alpha
  }

  override fun getAlpha(): Int =
    paint.alpha

  override fun getColorFilter(): ColorFilter? =
    paint.colorFilter

  override fun setColorFilter(colorFilter: ColorFilter?) {
    paint.colorFilter = colorFilter
  }

  override fun getOpacity(): Int =
    PixelFormat.TRANSLUCENT

  override fun isStateful(): Boolean =
    true

  override fun onStateChange(state: IntArray): Boolean {
    invalidateSelf()
    return true
  }
}
