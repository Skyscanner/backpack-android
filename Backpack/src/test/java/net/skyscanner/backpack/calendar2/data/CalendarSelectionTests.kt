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
class CalendarSelectionTests {

  @Test
  fun byDefault_noDateSelected() {
    testCalendarWith(CalendarSettings.Default) {
      verify {
        assertTrue(state.selection is CalendarSelection.None)
      }
    }
  }

  @Test
  fun whenSelectionIsDisabled_noDateCanBeSelected() {
    testCalendarWith(CalendarSettings.DisabledSelection) {
      stateMachine.onClick(firstDay)

      verify {
        assertTrue(state.selection is CalendarSelection.None)
      }
    }
  }

  @Test
  fun whenSelectionIsSingle_singleDateCanBeSelected() {
    testCalendarWith(CalendarSettings.SingleSelection) {
      stateMachine.onClick(firstDay)

      verify {
        assertEquals(CalendarSelection.Single(firstDay.date), state.selection)
      }
    }
  }

  @Test
  fun whenSelectionIsSingle_cellsHaveSelection() {
    testCalendarWith(CalendarSettings.SingleSelection) {
      stateMachine.onClick(firstDay)

      verify {
        assertEquals(CalendarCell.Selection.Single, firstDay.selection)
      }
    }
  }

  @Test
  fun whenSelectionIsSingle_singleDateCanBeChanged() {
    testCalendarWith(CalendarSettings.SingleSelection) {
      stateMachine.onClick(firstDay)
      stateMachine.onClick(lastDay)

      verify {
        assertEquals(CalendarSelection.Single(lastDay.date), state.selection)
      }
    }
  }

  @Test
  fun whenSelectionIsSingle_AndDateIsDisabled_dateCannotBeSelected() {
    val info = mapOf(
      CalendarSettings.SingleSelection.range.start to CellInfo(disabled = true),
    )

    testCalendarWith(CalendarSettings.SingleSelection.copy(cellsInfo = info)) {
      stateMachine.onClick(firstDay)

      verify {
        assertTrue(state.selection is CalendarSelection.None)
      }
    }
  }

  @Test
  fun whenRangeIsOpened_selectionIsCorrect() {
    testCalendarWith(CalendarSettings.RangeSelection) {
      stateMachine.onClick(firstDay)

      verify {
        assertEquals(CalendarSelection.Single(firstDay.date), state.selection)
      }
    }
  }

  @Test
  fun whenRangeIsClosed_selectionIsCorrect() {
    testCalendarWith(CalendarSettings.RangeSelection) {
      stateMachine.onClick(firstDay)
      stateMachine.onClick(lastDay)

      verify {
        assertEquals(CalendarSelection.Range(firstDay.date, lastDay.date), state.selection)
      }
    }
  }

  @Test
  fun whenRangeIsInSameDate_selectionIsCorrect() {
    testCalendarWith(CalendarSettings.RangeSelection) {
      stateMachine.onClick(firstDay)
      stateMachine.onClick(firstDay)

      verify {
        assertEquals(CalendarSelection.Range(firstDay.date, firstDay.date), state.selection)
      }
    }
  }

  @Test
  fun whenSecondRangeDateIsBeforeFirst_rangeIsReselected() {
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
  fun whenRangeIsSelected_isCanBeReselected() {
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
  fun whenRangeIsClosed_cellsHaveSelection() {
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
  fun whenRangeIsInSameDate_cellsHaveSelection() {
    testCalendarWith(CalendarSettings.RangeSelection) {
      stateMachine.onClick(firstDay)
      stateMachine.onClick(firstDay)

      verify {
        assertEquals(CalendarCell.Selection.Double, firstDay.selection)
      }
    }
  }
}
