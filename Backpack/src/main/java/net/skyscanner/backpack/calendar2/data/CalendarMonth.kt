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

package net.skyscanner.backpack.calendar2.data

import java.util.Locale
import net.skyscanner.backpack.calendar2.CalendarSelection
import net.skyscanner.backpack.calendar2.extension.firstDay
import net.skyscanner.backpack.calendar2.extension.lastDay
import net.skyscanner.backpack.calendar2.extension.lastDayOfWeek
import net.skyscanner.backpack.calendar2.extension.nextMonth
import net.skyscanner.backpack.calendar2.extension.prevMonth
import net.skyscanner.backpack.calendar2.extension.yearMonth
import org.threeten.bp.LocalDate
import org.threeten.bp.YearMonth
import org.threeten.bp.format.TextStyle
import org.threeten.bp.temporal.WeekFields

internal data class CalendarMonth(
  val yearMonth: YearMonth,
  val cells: List<CalendarCell>,
)

internal inline fun CalendarMonth(
  days: List<LocalDate>,
  locale: Locale,
  monthsTextStyle: TextStyle,
  weekFields: WeekFields,
  footers: List<YearMonth>,
  selection: CalendarSelection,
  day: (YearMonth, LocalDate) -> CalendarDay,
): CalendarMonth {

  val firstDay = days.first()

  val yearMonth = firstDay.yearMonth()
  val prevMonth = yearMonth.prevMonth()
  val nextMonth = yearMonth.nextMonth()

  val items = mutableListOf<CalendarCell>()
  items += CalendarHeader(title = yearMonth.month.getDisplayName(monthsTextStyle, locale), yearMonth = yearMonth)

  var currentDayOfWeek = weekFields.firstDayOfWeek
  val selectSpacingBefore = prevMonth.lastDay() in selection && firstDay in selection
  while (currentDayOfWeek != firstDay.dayOfWeek) {
    items += CalendarSpace(selected = selectSpacingBefore, yearMonth = prevMonth)
    currentDayOfWeek += 1
  }

  days.mapTo(items) {
    day(yearMonth, it)
  }

  val lastDay = days.last()
  currentDayOfWeek = lastDay.dayOfWeek
  val selectSpacingAfter = nextMonth.firstDay() in selection && lastDay in selection
  while (currentDayOfWeek != weekFields.lastDayOfWeek) {
    items += CalendarSpace(selected = selectSpacingAfter, yearMonth = yearMonth)
    currentDayOfWeek += 1
  }

  if (yearMonth in footers) {
    items += CalendarFooter(yearMonth = yearMonth)
  }

  return CalendarMonth(yearMonth = yearMonth, cells = items)
}
