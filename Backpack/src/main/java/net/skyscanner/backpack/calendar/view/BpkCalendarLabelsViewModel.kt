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

@file:Suppress("DEPRECATION")

package net.skyscanner.backpack.calendar.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.text.Layout
import android.text.StaticLayout
import android.text.TextDirectionHeuristics
import android.text.TextPaint
import android.text.TextUtils
import net.skyscanner.backpack.R
import net.skyscanner.backpack.calendar.model.CalendarLabel
import net.skyscanner.backpack.text.BpkText
import net.skyscanner.backpack.util.withSave
import org.threeten.bp.LocalDate
import kotlin.math.roundToInt

internal class BpkCalendarLabelsViewModel(
  context: Context,
) {

  private val paints: Map<CalendarLabel.Style, TextPaint> =
    CalendarLabel.Style.values().associateWith { it.createTextPaint(context) }

  private var labels: Map<LocalDate, BpkCalendarLabelViewModel> = emptyMap()

  private val placeholder = BpkCalendarLabelViewModel(
    label = CalendarLabel(
      text = "–",
      style = CalendarLabel.Style.PriceMedium,
    ),
    paints.getValue(CalendarLabel.Style.PriceMedium),
  )

  fun isEmpty(): Boolean =
    labels.isEmpty()

  fun update(data: Map<LocalDate, CalendarLabel>?) {
    labels = data?.mapValues { BpkCalendarLabelViewModel(it.value, paints.getValue(it.value.style)) } ?: emptyMap()
    if (cellWidth > 0) {
      labels.forEach { it.value.width = cellWidth }
      placeholder.width = cellWidth
    }
  }

  var cellWidth: Float = 0f
    set(value) {
      if (field != value) {
        field = value
        labels.forEach { it.value.width = cellWidth }
        placeholder.width = cellWidth
      }
    }

  fun draw(canvas: Canvas, date: LocalDate, x: Float, y: Float, disabled: Boolean) {
    if (disabled || isEmpty()) {
      return
    }
    val label = labels.getOrElse(date) { placeholder }
    label.draw(canvas, x, y)
  }

  class BpkCalendarLabelViewModel(
    private val label: CalendarLabel,
    private val paint: TextPaint,
  ) {

    private lateinit var text: StaticLayout

    var width: Float = 0f
      set(value) {
        if (field != value) {
          field = value
          val outerWidth = value.roundToInt()
          text = StaticLayout.Builder.obtain(
            label.text,
            0,
            label.text.length,
            paint,
            outerWidth
          )
            .setAlignment(Layout.Alignment.ALIGN_NORMAL)
            .setTextDirection(TextDirectionHeuristics.FIRSTSTRONG_LTR)
            .setLineSpacing(
              0f,
              1f
            )
            .setIncludePad(true)
            .setEllipsize(TextUtils.TruncateAt.END)
            .setEllipsizedWidth(outerWidth)
            .setMaxLines(2)
            .build()
        }
      }

    fun draw(canvas: Canvas, x: Float, y: Float) {
      canvas.withSave {
        translate(x, y)
        text.draw(canvas)
      }
    }
  }

  private fun CalendarLabel.Style.createTextPaint(
    context: Context,
  ): TextPaint = TextPaint().apply {
    isAntiAlias = true
    style = Paint.Style.FILL
    textAlign = Paint.Align.CENTER

    BpkText.getFont(context, BpkText.TextStyle.Caption).applyTo(this)

    color = context.getColor(
      when (this@createTextPaint) {
        CalendarLabel.Style.PriceLow -> R.color.bpkStatusSuccessSpot
        CalendarLabel.Style.PriceMedium -> R.color.bpkTextSecondary
        CalendarLabel.Style.PriceHigh -> R.color.bpkTextSecondary
      }
    )
  }
}
