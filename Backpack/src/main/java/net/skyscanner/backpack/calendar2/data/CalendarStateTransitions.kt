package net.skyscanner.backpack.calendar2.data

import net.skyscanner.backpack.calendar2.CalendarParams
import net.skyscanner.backpack.calendar2.CalendarSelection
import net.skyscanner.backpack.calendar2.CalendarState
import net.skyscanner.backpack.calendar2.extension.findDate
import net.skyscanner.backpack.calendar2.extension.toList
import net.skyscanner.backpack.calendar2.extension.yearMonthHash
import org.threeten.bp.temporal.WeekFields

internal fun CalendarState.dispatchClick(day: Int, month: Int, year: Int): CalendarState {
  if (params.selectionMode == CalendarParams.SelectionMode.Disabled) return this

  val date = months.findDate(day, month, year) ?: return this
  if (date.disabled) return this

  val selection = when (params.selectionMode) {
    CalendarParams.SelectionMode.Disabled -> selection
    CalendarParams.SelectionMode.Single -> CalendarSelection.Single(date.date)
    CalendarParams.SelectionMode.Range -> when (selection) {
      is CalendarSelection.None -> CalendarSelection.Single(date.date)
      is CalendarSelection.Single -> when {
        selection.date > date.date -> CalendarSelection.Single(date.date)
        else -> CalendarSelection.Range(start = selection.date, end = date.date)
      }
      is CalendarSelection.Range -> when (selection.end) {
        null -> CalendarSelection.Range(start = selection.start, end = date.date)
        else -> CalendarSelection.Single(date.date)
      }
    }
  }

  return copy(
    selection = selection,
    months = monthsOf(selection = selection),
  )
}

internal fun CalendarState.dispatchParamsUpdate(params: CalendarParams): CalendarState {
  val weekFields = WeekFields.of(params.locale)
  return copy(
    params = params,
    months = monthsOf(
      params = params,
      weekFields = weekFields,
    ),
    weekFields = weekFields,
  )
}

private fun CalendarState.monthsOf(
  params: CalendarParams = this.params,
  weekFields: WeekFields = this.weekFields,
  selection: CalendarSelection = this.selection,
): List<CalendarMonth> =
  params
    .range
    .toList()
    .groupBy { date -> date.yearMonthHash() }
    .toSortedMap()
    .map { entry -> entry.value.sortedBy { date -> date.dayOfMonth } }
    .map { dates ->
      CalendarMonth(
        days = dates,
        locale = params.locale,
        weekFields = weekFields,
        monthsTextStyle = params.monthsTextStyle,
        selection = selection,
        footers = params.footers,
      ) { date ->
        CalendarDay(date, selection, params.styles, params.labels, params.disabledDates)
      }
    }
