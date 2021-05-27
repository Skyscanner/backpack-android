package net.skyscanner.backpack.calendar2.data

import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.time.temporal.WeekFields
import java.util.Locale
import net.skyscanner.backpack.calendar2.CalendarSelection
import net.skyscanner.backpack.calendar2.extension.firstDay
import net.skyscanner.backpack.calendar2.extension.lastDay
import net.skyscanner.backpack.calendar2.extension.lastDayOfWeek
import net.skyscanner.backpack.calendar2.extension.nextMonth
import net.skyscanner.backpack.calendar2.extension.prevMonth
import net.skyscanner.backpack.calendar2.extension.yearMonth

internal data class CalendarMonth(
  val yearMonth: YearMonth,
  val items: List<CalendarItem>,
)

internal inline fun CalendarMonth(
  days: List<LocalDate>,
  locale: Locale,
  monthsTextStyle: TextStyle,
  weekFields: WeekFields,
  footers: List<YearMonth>,
  selection: CalendarSelection,
  day: (LocalDate) -> CalendarDay,
): CalendarMonth {

  val firstDay = days.first()

  val yearMonth = firstDay.yearMonth()
  val prevMonth = yearMonth.prevMonth()
  val nextMonth = yearMonth.nextMonth()

  val items = mutableListOf<CalendarItem>()
  items += CalendarHeader(title = yearMonth.month.getDisplayName(monthsTextStyle, locale), yearMonth = yearMonth)

  var currentDayOfWeek = weekFields.firstDayOfWeek
  val selectSpacingBefore = prevMonth.lastDay() in selection && firstDay in selection
  while (currentDayOfWeek != firstDay.dayOfWeek) {
    items += CalendarSpace(selected = selectSpacingBefore, yearMonth = prevMonth)
    currentDayOfWeek += 1
  }

  days.mapTo(items, day)

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

  return CalendarMonth(yearMonth = yearMonth, items = items)
}
