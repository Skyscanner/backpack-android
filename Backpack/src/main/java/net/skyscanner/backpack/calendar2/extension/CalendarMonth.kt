package net.skyscanner.backpack.calendar2.extension

import net.skyscanner.backpack.calendar2.data.CalendarDay
import net.skyscanner.backpack.calendar2.data.CalendarItem
import net.skyscanner.backpack.calendar2.data.CalendarMonth

internal fun List<CalendarMonth>.getItem(globalIndex: Int): CalendarItem {
  var month: CalendarMonth? = null
  var localIndex = globalIndex

  for (it in this) {
    if (localIndex in it.items.indices) {
      month = it
      break
    }
    localIndex -= it.items.size
  }

  return month?.items?.get(localIndex) ?: error("Unable to find a month for index $globalIndex")
}

internal fun List<CalendarMonth>.findDate(day: Int, month: Int, year: Int): CalendarDay? =
  find { it.yearMonth.year == year && it.yearMonth.monthValue == month }
    ?.items
    ?.find { it is CalendarDay && it.date.dayOfMonth == day }
    as? CalendarDay?
