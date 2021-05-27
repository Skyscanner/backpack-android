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
import android.content.res.ColorStateList
import android.graphics.Canvas
import android.graphics.ColorFilter
import android.graphics.Paint
import android.graphics.Paint.Style
import android.graphics.PixelFormat
import android.graphics.drawable.Drawable
import android.util.LayoutDirection
import net.skyscanner.backpack.R
import net.skyscanner.backpack.calendar2.data.CalendarDay
import net.skyscanner.backpack.util.ResourcesUtil
import net.skyscanner.backpack.util.getColorForState
import net.skyscanner.backpack.util.smallestDimension
import net.skyscanner.backpack.util.use

internal typealias CalendarDaySelectionBackground = (CalendarDay.Selection?) -> Drawable

internal fun CalendarDaySelectionBackground(
  context: Context,
): CalendarDaySelectionBackground {
  val impl = CalendarDaySelectionDrawable(context)

  return { selection ->
    impl.apply {
      this.selection = selection
    }
  }
}

private class CalendarDaySelectionDrawable(context: Context) : Drawable() {

  var selection: CalendarDay.Selection? = null
    set(value) {
      field = value
      invalidateSelf()
    }

  private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
  private val sameDateCirclesOffset = context.resources.getDimension(R.dimen.bpkSpacingMd)
  private val selectedDayCircleFillColor: ColorStateList
  private val selectedDaySameDayCircleFillColor: ColorStateList
  private val rangeBackgroundColor: ColorStateList

  init {
    context.obtainStyledAttributes(null, R.styleable.BpkCalendar, R.attr.bpkCalendarStyle, 0).use {

      selectedDayCircleFillColor = it.getColorStateList(
        R.styleable.BpkCalendar_calendarDateSelectedBackgroundColor,
      ) ?: context.getColorStateList(R.color.bpkPrimary)

      selectedDaySameDayCircleFillColor = it.getColorStateList(
        R.styleable.BpkCalendar_calendarDateSelectedSameDayBackgroundColor,
      ) ?: context.getColorStateList(R.color.__calendarSameDayBackground)

      rangeBackgroundColor = it.getColorStateList(
        R.styleable.BpkCalendar_calendarDateSelectedRangeBackgroundColor,
      ) ?: context.getColorStateList(R.color.__calendarRangeBackground)
    }
    paint.strokeWidth = ResourcesUtil.dpToPx(1f, context)
  }

  override fun draw(canvas: Canvas) {
    val rtl = layoutDirection == LayoutDirection.RTL
    when (selection) {
      CalendarDay.Selection.Single -> {
        canvas.drawCircle(selectedDayCircleFillColor, Style.FILL_AND_STROKE)
      }
      CalendarDay.Selection.Double -> {
        val offsetX = if (rtl) +sameDateCirclesOffset else -sameDateCirclesOffset
        canvas.drawCircle(selectedDaySameDayCircleFillColor, Style.STROKE, offsetX = offsetX)
        canvas.drawCircle(selectedDayCircleFillColor, Style.FILL_AND_STROKE)
      }
      CalendarDay.Selection.Start -> {
        canvas.drawRect(rangeBackgroundColor, Style.FILL_AND_STROKE, if (rtl) 0f else 0.5f, if (rtl) 0.5f else 1f)
        canvas.drawCircle(selectedDayCircleFillColor, Style.FILL_AND_STROKE)
      }
      CalendarDay.Selection.Middle -> {
        canvas.drawRect(rangeBackgroundColor, Style.FILL_AND_STROKE, 0f, 1f)
      }
      CalendarDay.Selection.End -> {
        canvas.drawRect(rangeBackgroundColor, Style.FILL_AND_STROKE, if (rtl) 0.5f else 0f, if (rtl) 1f else 0.5f)
        canvas.drawCircle(selectedDayCircleFillColor, Style.FILL_AND_STROKE)
      }
      null -> return
    }
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

  override fun onLayoutDirectionChanged(layoutDirection: Int): Boolean {
    invalidateSelf()
    return true
  }

  override fun onStateChange(state: IntArray): Boolean {
    invalidateSelf()
    return true
  }

  private fun Canvas.drawCircle(
    color: ColorStateList,
    style: Style,
    offsetX: Float = 0f,
    offsetY: Float = 0f,
  ) {
    val bounds = bounds
    val x = bounds.centerX().toFloat()
    val y = bounds.centerY().toFloat()
    val radius = (bounds.smallestDimension() / 2f) - paint.strokeWidth

    paint.color = color.getColorForState(state)
    paint.style = style
    drawCircle(x + offsetX, y + offsetY, radius, paint)
  }

  private fun Canvas.drawRect(
    color: ColorStateList,
    style: Style,
    from: Float,
    to: Float,
  ) {
    val bounds = bounds

    val left = bounds.left
    val width = bounds.width()

    paint.color = color.getColorForState(state)
    paint.style = style

    drawRect(left + width * from, bounds.top.toFloat(), left + width * to, bounds.bottom.toFloat(), paint)
  }
}
