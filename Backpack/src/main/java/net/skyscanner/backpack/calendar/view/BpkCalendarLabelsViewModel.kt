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

package net.skyscanner.backpack.calendar.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.text.TextPaint
import android.text.TextUtils
import androidx.core.content.ContextCompat
import net.skyscanner.backpack.R
import net.skyscanner.backpack.calendar.model.CalendarLabel
import net.skyscanner.backpack.text.BpkText
import org.threeten.bp.LocalDate

internal class BpkCalendarLabelsViewModel(
  context: Context,
) {

  private val paints: Map<CalendarLabel.Style, TextPaint> =
    CalendarLabel.Style.values().associateWith { it.createTextPaint(context) }

  private var labels: Map<LocalDate, BpkCalendarLabelViewModel> = emptyMap()

  fun update(data: Map<LocalDate, CalendarLabel>?) {
    labels = data?.mapValues { BpkCalendarLabelViewModel(it.value, paints.getValue(it.value.style)) } ?: emptyMap()
    if (cellWidth > 0) {
      labels.forEach { it.value.width = cellWidth }
    }
  }

  var cellWidth: Float = 0f
    set(value) {
      if (field != value) {
        field = value
        labels.forEach { it.value.width = cellWidth }
      }
    }

  fun draw(canvas: Canvas, date: LocalDate, x: Float, y: Float, disabled: Boolean) {
    if (disabled) {
      return
    }
    val label = labels[date]
    if (label != null) {
      label.draw(canvas, x, y)
    } else if (labels.isNotEmpty()) {
      canvas.drawText("-", x, y, paints.getValue(CalendarLabel.Style.PriceMedium))
    }
  }

  class BpkCalendarLabelViewModel(
    private val label: CalendarLabel,
    private val paint: TextPaint,
  ) {

    private lateinit var ellipsizedText: String

    var width: Float = 0f
      set(value) {
        if (field != value) {
          field = value
          ellipsizedText = TextUtils.ellipsize(label.text, paint, width, TextUtils.TruncateAt.END).toString()
        }
      }

    fun draw(canvas: Canvas, x: Float, y: Float) {
      canvas.drawText(ellipsizedText, x, y, paint)
    }
  }

  private fun CalendarLabel.Style.createTextPaint(
    context: Context,
  ): TextPaint = TextPaint().apply {
    isAntiAlias = true
    style = Paint.Style.FILL
    textAlign = Paint.Align.CENTER

    BpkText.getFont(
      context, BpkText.XS,
      when (this@createTextPaint) {
        CalendarLabel.Style.PriceLow -> BpkText.Weight.EMPHASIZED
        CalendarLabel.Style.PriceMedium -> BpkText.Weight.NORMAL
        CalendarLabel.Style.PriceHigh -> BpkText.Weight.NORMAL
      }
    ).applyTo(this)

    color = ContextCompat.getColor(
      context,
      when (this@createTextPaint) {
        CalendarLabel.Style.PriceLow -> R.color.bpkMonteverde
        CalendarLabel.Style.PriceMedium -> R.color.bpkTextSecondary
        CalendarLabel.Style.PriceHigh -> R.color.bpkTextSecondary
      }
    )
  }
}
