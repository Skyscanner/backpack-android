package net.skyscanner.backpack.calendar2.data

import java.util.Locale
import net.skyscanner.backpack.calendar2.extension.lastDayOfWeek
import org.threeten.bp.LocalDate
import org.threeten.bp.YearMonth
import org.threeten.bp.format.TextStyle
import org.threeten.bp.temporal.WeekFields

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
  day: (LocalDate) -> CalendarDay,
): CalendarMonth {

  val firstDay = days.first()
  val yearMonth = YearMonth.of(firstDay.year, firstDay.month)

  val items = mutableListOf<CalendarItem>()
  items += CalendarHeader(title = yearMonth.month.getDisplayName(monthsTextStyle, locale), yearMonth = yearMonth)

  var currentDayOfWeek = weekFields.firstDayOfWeek
  while (currentDayOfWeek != firstDay.dayOfWeek) {
    items += CalendarSpace
    currentDayOfWeek += 1
  }

  days.mapTo(items, day)

  currentDayOfWeek = days.last().dayOfWeek
  while (currentDayOfWeek != weekFields.lastDayOfWeek) {
    items += CalendarSpace
    currentDayOfWeek += 1
  }

  if (yearMonth in footers) {
    items += CalendarFooter(yearMonth = yearMonth)
  }

  return CalendarMonth(yearMonth = yearMonth, items = items)
}
