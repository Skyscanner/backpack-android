package net.skyscanner.backpack.calendar2.data

import kotlinx.coroutines.ExperimentalCoroutinesApi
import net.skyscanner.backpack.calendar2.CalendarParams
import net.skyscanner.backpack.calendar2.CalendarSelection
import net.skyscanner.backpack.calendar2.CalendarSettings
import net.skyscanner.backpack.calendar2.CellInfo
import net.skyscanner.backpack.calendar2.firstDay
import net.skyscanner.backpack.calendar2.lastDay
import net.skyscanner.backpack.calendar2.testCalendarWith
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CalendarSingleSelectionTests {

  private val singleSelection = CalendarSettings.Default.copy(
    selectionMode = CalendarParams.SelectionMode.Single,
  )

  @Test
  fun `date can be selected`() {
    testCalendarWith(singleSelection) {
      stateMachine.onClick(firstDay)

      verify {
        assertEquals(CalendarSelection.Single(firstDay.date), state.selection)
      }
    }
  }

  @Test
  fun `when selection is in place cells have correct state`() {
    testCalendarWith(singleSelection) {
      stateMachine.onClick(firstDay)

      verify {
        assertEquals(CalendarCell.Selection.Single, firstDay.selection)
      }
    }
  }

  @Test
  fun `selected date can be changed`() {
    testCalendarWith(singleSelection) {
      stateMachine.onClick(firstDay)
      stateMachine.onClick(lastDay)

      verify {
        assertEquals(CalendarSelection.Single(lastDay.date), state.selection)
      }
    }
  }

  @Test
  fun `disabled date cannot be selected`() {
    val disabledDates = mapOf(
      singleSelection.range.start to CellInfo(disabled = true),
    )

    testCalendarWith(singleSelection.copy(cellsInfo = disabledDates)) {
      stateMachine.onClick(firstDay)

      verify {
        assertTrue(state.selection is CalendarSelection.None)
      }
    }
  }
}
