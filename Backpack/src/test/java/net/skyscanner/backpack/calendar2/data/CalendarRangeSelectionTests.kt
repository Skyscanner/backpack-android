package net.skyscanner.backpack.calendar2.data

import kotlinx.coroutines.ExperimentalCoroutinesApi
import net.skyscanner.backpack.calendar2.CalendarSelection
import net.skyscanner.backpack.calendar2.CalendarSettings
import net.skyscanner.backpack.calendar2.CellInfo
import net.skyscanner.backpack.calendar2.firstDay
import net.skyscanner.backpack.calendar2.lastDay
import net.skyscanner.backpack.calendar2.rangeOf
import net.skyscanner.backpack.calendar2.testCalendarWith
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CalendarRangeSelectionTests {

  @Test
  fun `when range is opened selection is correct`() {
    testCalendarWith(CalendarSettings.RangeSelection) {
      stateMachine.onClick(firstDay)

      verify {
        assertEquals(CalendarSelection.Single(firstDay.date), state.selection)
      }
    }
  }

  @Test
  fun `when range is closed selection is correct`() {
    testCalendarWith(CalendarSettings.RangeSelection) {
      stateMachine.onClick(firstDay)
      stateMachine.onClick(lastDay)

      verify {
        assertEquals(CalendarSelection.Range(firstDay.date, lastDay.date), state.selection)
      }
    }
  }

  @Test
  fun `when range is withing ther same date selection is correct`() {
    testCalendarWith(CalendarSettings.RangeSelection) {
      stateMachine.onClick(firstDay)
      stateMachine.onClick(firstDay)

      verify {
        assertEquals(CalendarSelection.Range(firstDay.date, firstDay.date), state.selection)
      }
    }
  }

  @Test
  fun `when range is closing before start a new range is created`() {
    testCalendarWith(CalendarSettings.RangeSelection) {
      stateMachine.onClick(lastDay)
      stateMachine.onClick(firstDay)
      stateMachine.onClick(firstDay)
      verify {
        assertEquals(CalendarSelection.Range(firstDay.date, firstDay.date), state.selection)
      }
    }
  }

  @Test
  fun `range can be reselected`() {
    testCalendarWith(CalendarSettings.RangeSelection) {
      stateMachine.onClick(firstDay)
      stateMachine.onClick(lastDay)
      stateMachine.onClick(firstDay)
      stateMachine.onClick(firstDay)
      verify {
        assertEquals(CalendarSelection.Range(firstDay.date, firstDay.date), state.selection)
      }
    }
  }

  @Test
  fun `when range is selected cells have correct state`() {
    testCalendarWith(CalendarSettings.RangeSelection) {
      stateMachine.onClick(firstDay)
      stateMachine.onClick(lastDay)

      verify {
        for (i in rangeOf(firstDay, lastDay)) {
          when (val cell = state.cells[i]) {
            is CalendarCell.Day -> when (cell) {
              firstDay -> assertEquals(CalendarCell.Selection.Start, cell.selection)
              lastDay -> assertEquals(CalendarCell.Selection.End, cell.selection)
              else -> assertEquals(CalendarCell.Selection.Middle, cell.selection)
            }
            is CalendarCell.Space -> assertTrue(cell.selected)
          }
        }
      }
    }
  }

  @Test
  fun `when range is withing the same date cells have correct state`() {
    testCalendarWith(CalendarSettings.RangeSelection) {
      stateMachine.onClick(firstDay)
      stateMachine.onClick(firstDay)

      verify {
        assertEquals(CalendarCell.Selection.Double, firstDay.selection)
      }
    }
  }

  @Test
  fun `disabled date cannot be selected`() {
    val info = mapOf(
      CalendarSettings.RangeSelection.range.start to CellInfo(disabled = true),
    )

    testCalendarWith(CalendarSettings.RangeSelection.copy(cellsInfo = info)) {
      stateMachine.onClick(firstDay)

      verify {
        assertTrue(state.selection is CalendarSelection.None)
      }
    }
  }
}
