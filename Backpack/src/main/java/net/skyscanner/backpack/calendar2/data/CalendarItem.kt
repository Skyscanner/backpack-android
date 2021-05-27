package net.skyscanner.backpack.calendar2.data

import java.time.LocalDate
import java.time.YearMonth
import net.skyscanner.backpack.calendar2.CalendarParams
import net.skyscanner.backpack.calendar2.CalendarSelection

internal sealed class CalendarItem

internal data class CalendarSpace(
  val selected: Boolean,
  val yearMonth: YearMonth,
) : CalendarItem()

internal data class CalendarHeader(
  val title: String,
  val yearMonth: YearMonth,
) : CalendarItem()

internal data class CalendarFooter(
  val yearMonth: YearMonth,
) : CalendarItem()

internal data class CalendarDay(
  val date: LocalDate,
  val info: CalendarParams.Info,
  val selection: Selection?,
) : CalendarItem() {

  enum class Selection {
    Single,
    Double,
    Start,
    Middle,
    End,
  }
}

internal fun CalendarDay(
  date: LocalDate,
  selection: CalendarSelection,
  cells: Map<LocalDate, CalendarParams.Info>,
): CalendarDay = CalendarDay(
  date = date,
  info = cells[date] ?: CalendarParams.Info.Default,
  selection = when (selection) {
    is CalendarSelection.None -> null
    is CalendarSelection.Single -> when (date) {
      selection.date -> CalendarDay.Selection.Single
      else -> null
    }
    is CalendarSelection.Range -> when {
      selection.start == date && selection.end == date -> CalendarDay.Selection.Double
      selection.start == date -> CalendarDay.Selection.Start
      selection.end == date -> CalendarDay.Selection.End
      date in selection -> CalendarDay.Selection.Middle
      else -> null
    }
  },
)
