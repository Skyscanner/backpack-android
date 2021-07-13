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
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CalendarSettersTests {

  @Test
  fun `setParams() changes params`() {
    testCalendarWith(CalendarSettings.Default) {
      stateMachine.setParams(CalendarSettings.Default.copy(selectionMode = CalendarParams.SelectionMode.Disabled))
      verify {
        assertEquals(CalendarParams.SelectionMode.Disabled, state.params.selectionMode)
      }
    }
  }

  @Test
  fun `setSelection(Single) changes selection`() {
    val disabledParams = CalendarSettings.Default.copy(
      selectionMode = CalendarParams.SelectionMode.Single,
    )
    testCalendarWith(disabledParams) {
      stateMachine.setSelection(CalendarSelection.Single(firstDay.date))

      verify {
        assertEquals(CalendarSelection.Single(firstDay.date), state.selection)
      }
    }
  }

  @Test
  fun `setSelection(Range) changes selection`() {
    val disabledParams = CalendarSettings.Default.copy(
      selectionMode = CalendarParams.SelectionMode.Range,
    )
    testCalendarWith(disabledParams) {
      stateMachine.setSelection(CalendarSelection.Range(firstDay.date, lastDay.date))

      verify {
        assertEquals(CalendarSelection.Range(firstDay.date, lastDay.date), state.selection)
      }
    }
  }

  @Test
  fun `setSelection(Single) ignores change when selectionMode is disabled`() {
    val disabledParams = CalendarSettings.Default.copy(
      selectionMode = CalendarParams.SelectionMode.Disabled,
    )
    testCalendarWith(disabledParams) {
      stateMachine.setSelection(CalendarSelection.Single(firstDay.date))

      verify {
        assertEquals(CalendarSelection.None, state.selection)
        assertEquals(CalendarParams.SelectionMode.Disabled, state.params.selectionMode)
      }
    }
  }

  @Test
  fun `setSelection(Range) ignores change when selectionMode is disabled`() {
    val disabledParams = CalendarSettings.Default.copy(
      selectionMode = CalendarParams.SelectionMode.Disabled,
    )
    testCalendarWith(disabledParams) {
      stateMachine.setSelection(CalendarSelection.Range(firstDay.date, lastDay.date))

      verify {
        assertEquals(CalendarSelection.None, state.selection)
        assertEquals(CalendarParams.SelectionMode.Disabled, state.params.selectionMode)
      }
    }
  }

  @Test
  fun `setSelection(Single) ignores change when date is disabled`() {
    val disabledParams = CalendarSettings.Default.copy(
      selectionMode = CalendarParams.SelectionMode.Single,
      cellsInfo = mapOf(
        CalendarSettings.Default.range.start to CellInfo(disabled = true),
      )
    )
    testCalendarWith(disabledParams) {
      stateMachine.setSelection(CalendarSelection.Single(firstDay.date))

      verify {
        assertEquals(CalendarSelection.None, state.selection)
      }
    }
  }

  @Test
  fun `setSelection(Range) ignores change when start date is disabled`() {
    val disabledParams = CalendarSettings.Default.copy(
      selectionMode = CalendarParams.SelectionMode.Range,
      cellsInfo = mapOf(
        CalendarSettings.Default.range.start to CellInfo(disabled = true),
      )
    )
    testCalendarWith(disabledParams) {
      stateMachine.setSelection(CalendarSelection.Range(start = firstDay.date, end = null))

      verify {
        assertEquals(CalendarSelection.None, state.selection)
      }
    }
  }

  @Test
  fun `setSelection(Range) ignores change when end date is disabled`() {
    val disabledParams = CalendarSettings.Default.copy(
      selectionMode = CalendarParams.SelectionMode.Range,
      cellsInfo = mapOf(
        CalendarSettings.Default.range.endInclusive to CellInfo(disabled = true),
      )
    )
    testCalendarWith(disabledParams) {
      stateMachine.setSelection(CalendarSelection.Range(start = firstDay.date, end = lastDay.date))

      verify {
        assertEquals(CalendarSelection.None, state.selection)
      }
    }
  }

  @Test
  fun `setSelection(Single) ignores change when date is out of range`() {
    val disabledParams = CalendarSettings.Default.copy(
      selectionMode = CalendarParams.SelectionMode.Single,
    )
    testCalendarWith(disabledParams) {
      stateMachine.setSelection(CalendarSelection.Single(firstDay.date.minusMonths(1)))

      verify {
        assertEquals(CalendarSelection.None, state.selection)
      }
    }
  }

  @Test
  fun `setSelection(Range) ignores change when start date is out of range`() {
    val disabledParams = CalendarSettings.Default.copy(
      selectionMode = CalendarParams.SelectionMode.Range,
    )
    testCalendarWith(disabledParams) {
      stateMachine.setSelection(CalendarSelection.Range(start = firstDay.date.minusMonths(1), end = null))

      verify {
        assertEquals(CalendarSelection.None, state.selection)
      }
    }
  }

  @Test
  fun `setSelection(Range) ignores change when end date is out of range`() {
    val disabledParams = CalendarSettings.Default.copy(
      selectionMode = CalendarParams.SelectionMode.Range,
    )
    testCalendarWith(disabledParams) {
      stateMachine.setSelection(CalendarSelection.Range(start = firstDay.date, end = lastDay.date.plusMonths(1)))

      verify {
        assertEquals(CalendarSelection.None, state.selection)
      }
    }
  }
}
