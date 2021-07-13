package net.skyscanner.backpack.calendar2

import java.util.Locale
import net.skyscanner.backpack.calendar2.data.CalendarCell
import net.skyscanner.backpack.calendar2.data.CalendarStateMachine
import net.skyscanner.backpack.util.TestStateMachineResult
import net.skyscanner.backpack.util.TestStateMachineScope
import net.skyscanner.backpack.util.testStateMachine
import org.threeten.bp.LocalDate
import org.threeten.bp.Month

internal typealias CalendarTestScope = TestStateMachineScope<CalendarStateMachine, CalendarState>

internal val CalendarTestScope.firstDay: CalendarCell.Day
  get() {
    val index = state.cells.indexOf(CalendarSettings.Default.range.start)
    return state.cells[index] as CalendarCell.Day
  }

internal val CalendarTestScope.lastDay: CalendarCell.Day
  get() {
    val index = state.cells.indexOf(CalendarSettings.Default.range.endInclusive.minusDays(1))
    return state.cells[index] as CalendarCell.Day
  }

internal fun CalendarTestScope.rangeOf(start: CalendarCell.Day, end: CalendarCell.Day): IntRange {
  val indexOfFirst = state.cells.indexOf(start.date)
  val indexOfLast = state.cells.indexOf(end.date)
  return indexOfFirst..indexOfLast
}

internal fun testCalendarWith(
  params: CalendarParams,
  block: suspend CalendarTestScope.() -> TestStateMachineResult,
) =
  testStateMachine(creator = { CalendarStateMachine(this, params, it) }, block = block)

object CalendarSettings {

  val Default = CalendarParams(
    range = LocalDate.of(2000, Month.JANUARY, 1)..LocalDate.of(2000, Month.DECEMBER, 31),
    selectionMode = CalendarParams.SelectionMode.Single,
    locale = Locale.ENGLISH,
  )
}
