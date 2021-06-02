package net.skyscanner.backpack.calendar2.data

import net.skyscanner.backpack.calendar2.CalendarParams
import net.skyscanner.backpack.calendar2.CalendarSelection
import net.skyscanner.backpack.calendar2.extension.toList
import net.skyscanner.backpack.calendar2.extension.yearMonthHash

internal data class CalendarCells(
  private val months: List<CalendarMonth> = emptyList(),
) {

  val size: Int = months.sumBy { it.cells.size }

  operator fun get(position: Int): CalendarCell =
    getOrNull(position) ?: error("Unable to find a month for index $position")

  fun getOrNull(position: Int): CalendarCell? {
    var month: CalendarMonth? = null
    var localIndex = position

    for (it in months) {
      if (localIndex in it.cells.indices) {
        month = it
        break
      }
      localIndex -= it.cells.size
    }

    return month?.cells?.getOrNull(localIndex)
  }
}

internal fun CalendarCells(
  params: CalendarParams,
  selection: CalendarSelection,
): CalendarCells {
  val months = params
    .range
    .toList()
    .groupBy { date -> date.yearMonthHash() }
    .toSortedMap()
    .map { entry ->
      CalendarMonth(
        days = entry.value.sortedBy { date -> date.dayOfMonth },
        locale = params.locale,
        weekFields = params.weekFields,
        monthsTextStyle = params.monthsText,
        selection = selection,
        footers = params.footers,
      ) { yearMonth, date ->
        CalendarDay(
          date = date,
          yearMonth = yearMonth,
          selection = selection,
          cells = params.cells,
          locale = params.locale,
          contentDescription = params.dateAccessibilityText,
        )
      }
    }

  return CalendarCells(months = months)
}
