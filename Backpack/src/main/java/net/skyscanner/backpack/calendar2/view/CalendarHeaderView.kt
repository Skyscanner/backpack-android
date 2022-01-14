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

package net.skyscanner.backpack.calendar2.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.Gravity
import android.widget.TextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.view.forEach
import net.skyscanner.backpack.R
import net.skyscanner.backpack.calendar2.CalendarParams
import net.skyscanner.backpack.text.BpkText
import net.skyscanner.backpack.util.Consumer
import org.threeten.bp.DayOfWeek

internal class CalendarHeaderView @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0,
) : LinearLayoutCompat(context, attrs, defStyleAttr), Consumer<CalendarParams> {

  private val linePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
    style = Paint.Style.FILL_AND_STROKE
    color = context.getColor(R.color.bpkLine)
    strokeWidth = resources.getDimension(R.dimen.bpk_calendar_border_width)
  }

  init {
    orientation = HORIZONTAL
    repeat(DayOfWeek.values().size) {
      val text = BpkText(context)
      text.textStyle = BpkText.SM
      text.weight = BpkText.Weight.EMPHASIZED
      text.setTextColor(context.getColorStateList(R.color.bpkTextSecondary))
      text.gravity = Gravity.CENTER
      text.maxLines = 1
      text.isSingleLine = true
      text.isAllCaps = true
      addView(text, LayoutParams(0, LayoutParams.MATCH_PARENT, 1f))
    }
  }

  override fun invoke(params: CalendarParams) {
    var current = params.weekFields.firstDayOfWeek
    forEach {
      it as TextView
      it.text = current.getDisplayName(params.dayOfWeekText, params.locale)
      it.contentDescription = current.getDisplayName(params.dayOfWeekAccessibilityText, params.locale)
      current += 1
    }
  }

  override fun dispatchDraw(canvas: Canvas) {
    super.dispatchDraw(canvas)
    val y = height.toFloat() - linePaint.strokeWidth / 2
    canvas.drawLine(0f, y, width.toFloat(), y, linePaint)
  }
}
