package net.skyscanner.backpack.calendar2.data

import net.skyscanner.backpack.calendar2.CalendarParams
import net.skyscanner.backpack.calendar2.CalendarSelection
import net.skyscanner.backpack.calendar2.extension.toList
import net.skyscanner.backpack.calendar2.extension.yearMonth
import org.threeten.bp.LocalDate

internal data class CalendarCells(
  private val months: List<CalendarMonth> = emptyList(),
) {

  val size: Int = months.sumBy { it.cells.size }

  operator fun get(position: Int): CalendarCell {
    var month: CalendarMonth? = null
    var localIndex = position
    for (it in months) {
      if (localIndex in it.cells.indices) {
        month = it
        break
      }
      localIndex -= it.cells.size
    }

    return month?.cells?.getOrNull(localIndex) ?: error("Unable to find a month for index $position")
  }

  fun indexOf(date: LocalDate): Int {
    var accumulated = 0
    months.forEach { month ->
      val indexInMonth = month.cells.indexOfFirst { (it as? CalendarCellDay)?.date == date }
      if (indexInMonth < 0) {
        accumulated += month.cells.size
      } else {
        return indexInMonth + accumulated
      }
    }
    return -1
  }
}

internal fun CalendarCells(
  params: CalendarParams,
  selection: CalendarSelection,
): CalendarCells {
  val months = params
    .range
    .toList()
    .groupBy { date -> date.yearMonth() }
    .toSortedMap()
    .map { entry ->
      CalendarMonth(
        days = entry.value.sortedBy { date -> date.dayOfMonth },
        yearMonth = entry.key,
        locale = params.locale,
        weekFields = params.weekFields,
        monthsTextStyle = params.monthsText,
        selection = selection,
        footers = params.footers,
      ) { yearMonth, date ->
        CalendarCellDay(
          date = date,
          yearMonth = yearMonth,
          selection = selection,
          cells = params.info,
          locale = params.locale,
          contentDescription = params.dateAccessibilityText,
        )
      }
    }

  return CalendarCells(months = months)
}
