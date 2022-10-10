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

package net.skyscanner.backpack.demo.data

import android.content.Context
import androidx.annotation.VisibleForTesting
import net.skyscanner.backpack.calendar.model.CalendarCellStyle
import net.skyscanner.backpack.calendar.model.CalendarColoring
import net.skyscanner.backpack.calendar.model.CalendarLabel
import net.skyscanner.backpack.calendar.model.CalendarRange
import net.skyscanner.backpack.calendar.model.CalendarSelection
import net.skyscanner.backpack.calendar.model.ColoredBucket
import net.skyscanner.backpack.calendar.model.SingleDay
import net.skyscanner.backpack.calendar.presenter.BpkCalendarController
import net.skyscanner.backpack.calendar.presenter.SelectionType
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.toast.BpkToast
import org.threeten.bp.DayOfWeek
import org.threeten.bp.LocalDate
import org.threeten.bp.temporal.ChronoUnit
import java.util.Locale

open class ExampleBpkCalendarController(
  private val context: Context,
  override val selectionType: SelectionType = SelectionType.RANGE,
  private val disableDates: Boolean = false,
  private val automationMode: Boolean = false,
  override val calendarLabels: Map<LocalDate, CalendarLabel>? = null,
) : BpkCalendarController(selectionType) {
  override fun onRangeSelected(range: CalendarSelection) {
    if (automationMode) {
      return
    }
    when (range) {
      is SingleDay -> BpkToast.makeText(
        context,
        String.format("%s", range.selectedDay.toString()),
        BpkToast.LENGTH_SHORT
      ).show()
      is CalendarRange -> BpkToast.makeText(
        context,
        String.format("%s - %s", range.start.toString(), range.end.toString()),
        BpkToast.LENGTH_SHORT
      ).show()
    }
  }

  var isColoredCalendar: Boolean = false
  private var colorGenerationOffset: Long = 0L
  private var disabledWeekdays = DayOfWeek.WEDNESDAY

  override val locale: Locale = Locale.getDefault()
  override val calendarColoring: CalendarColoring?
    get() = if (isColoredCalendar) {
      multiColoredExampleCalendarColoring(colorGenerationOffset, startDate, endDate, context)
    } else {
      null
    }

  override fun isDateDisabled(date: LocalDate): Boolean {
    if (!disableDates) {
      return false
    }
    return date.dayOfWeek == disabledWeekdays
  }

  fun newColors() {
    colorGenerationOffset += 1
  }

  fun shiftDisabledDates() {
    disabledWeekdays += 1
  }
}

@VisibleForTesting
internal fun multiColoredExampleCalendarColoring(
  colorOffset: Long,
  startDate: LocalDate,
  endDate: LocalDate,
  context: Context,
): CalendarColoring {
  val daysBetweenStartAndEnd = ChronoUnit.DAYS.between(startDate, endDate) + 1
  val redSet = mutableSetOf<LocalDate>()
  val yellowSet = mutableSetOf<LocalDate>()
  val greenSet = mutableSetOf<LocalDate>()
  val greySet = mutableSetOf<LocalDate>()
  var dateIterator = LocalDate.of(startDate.year, startDate.month, startDate.dayOfMonth)
  for (i in 0 until daysBetweenStartAndEnd) {
    val shiftedIterator = i + colorOffset
    when {
      shiftedIterator % 5 == 0L -> redSet
      shiftedIterator % 5 == 1L -> yellowSet
      shiftedIterator % 5 == 2L -> greenSet
      shiftedIterator % 5 == 3L -> greySet
      else -> mutableSetOf()
    }.add(dateIterator)
    dateIterator = dateIterator.plusDays(1)
  }
  return CalendarColoring(
    setOf(
      ColoredBucket(CalendarCellStyle.Negative, redSet),
      ColoredBucket(CalendarCellStyle.Neutral, yellowSet),
      ColoredBucket(CalendarCellStyle.Positive, greenSet),
      ColoredBucket(
        CalendarCellStyle.Custom(context.getColor(R.color.bpkSurfaceHighlight)),
        greySet
      )
    )
  )
}
