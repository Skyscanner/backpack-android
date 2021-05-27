package net.skyscanner.backpack.calendar2.data

import net.skyscanner.backpack.calendar2.CalendarParams
import net.skyscanner.backpack.calendar2.CalendarSelection
import org.threeten.bp.LocalDate
import org.threeten.bp.YearMonth

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
  val selection: Selection,
) : CalendarItem() {

  enum class Selection {
    None,
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
    is CalendarSelection.None -> CalendarDay.Selection.None
    is CalendarSelection.Single -> when (date) {
      selection.date -> CalendarDay.Selection.Single
      else -> CalendarDay.Selection.None
    }
    is CalendarSelection.Range -> when {
      selection.start == date && selection.end == date -> CalendarDay.Selection.Double
      selection.start == date -> CalendarDay.Selection.Start
      selection.end == date -> CalendarDay.Selection.End
      date in selection -> CalendarDay.Selection.Middle
      else -> CalendarDay.Selection.None
    }
  },
)
