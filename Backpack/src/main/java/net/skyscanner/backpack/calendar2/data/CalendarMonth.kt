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

package net.skyscanner.backpack.calendar2.data

import net.skyscanner.backpack.calendar2.CalendarParams
import net.skyscanner.backpack.calendar2.CalendarSelection
import net.skyscanner.backpack.calendar2.extension.firstDay
import net.skyscanner.backpack.calendar2.extension.lastDay
import net.skyscanner.backpack.calendar2.extension.lastDayOfWeek
import net.skyscanner.backpack.calendar2.extension.nextMonth
import net.skyscanner.backpack.calendar2.extension.prevMonth
import org.threeten.bp.LocalDate
import org.threeten.bp.YearMonth
import org.threeten.bp.temporal.WeekFields
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

internal data class CalendarMonth(
  val yearMonth: YearMonth,
  val cells: List<CalendarCell>,
)

internal inline fun CalendarMonth(
  days: List<LocalDate>,
  yearMonth: YearMonth,
  locale: Locale,
  monthsFormatter: SimpleDateFormat,
  weekFields: WeekFields,
  selection: CalendarSelection,
  monthSelectionMode: CalendarParams.MonthSelectionMode,
  calendarSelectionMode: CalendarParams.SelectionMode,
  day: (YearMonth, LocalDate) -> CalendarCell.Day,
): CalendarMonth {

  val firstDay = days.first()

  val prevMonth = yearMonth.prevMonth()
  val nextMonth = yearMonth.nextMonth()
  val cells = mutableListOf<CalendarCell>()
  cells += CalendarCell.Header(
    title = MonthTitle(yearMonth, monthsFormatter, locale),
    yearMonth = yearMonth,
    monthSelectionMode = monthSelectionMode,
    calendarSelectionMode = calendarSelectionMode
  )

  var currentDayOfWeek = weekFields.firstDayOfWeek
  val selectSpacingBefore = prevMonth.lastDay() in selection && firstDay in selection
  var spacePosition = 0
  while (currentDayOfWeek != firstDay.dayOfWeek) {
    cells += CalendarCell.Space(selected = selectSpacingBefore, yearMonth = yearMonth, position = spacePosition++)
    currentDayOfWeek += 1
  }

  days.mapTo(cells) {
    day(yearMonth, it)
  }

  val lastDay = days.last()
  currentDayOfWeek = lastDay.dayOfWeek
  val selectSpacingAfter = nextMonth.firstDay() in selection && lastDay in selection
  spacePosition = 0
  while (currentDayOfWeek != weekFields.lastDayOfWeek) {
    cells += CalendarCell.Space(selected = selectSpacingAfter, yearMonth = yearMonth, position = spacePosition++)
    currentDayOfWeek += 1
  }

  return CalendarMonth(yearMonth = yearMonth, cells = cells)
}

// see https://github.com/ThreeTen/threetenbp/issues/55
@Suppress("DEPRECATION")
internal fun MonthTitle(yearMonth: YearMonth, formatter: SimpleDateFormat, locale: Locale): String {
  val date = Date(
    yearMonth.year - 1900,
    yearMonth.monthValue - 1,
    2 // Lock the formatter date to 2nd, so it accounts for -12h tz and the month is predictable
  )
  return formatter.format(date).capitalize(locale)
}
