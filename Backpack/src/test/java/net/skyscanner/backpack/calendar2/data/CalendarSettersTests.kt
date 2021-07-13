package net.skyscanner.backpack.calendar2.data

import kotlinx.coroutines.ExperimentalCoroutinesApi
import net.skyscanner.backpack.calendar2.CalendarParams
import net.skyscanner.backpack.calendar2.CalendarSelection
import net.skyscanner.backpack.calendar2.CalendarSettings
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
  fun `setSelection(Range) ignores change when selectionMode=Disabled`() {
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
  fun `setSelection(Single) ignores change when selectionMode=Disabled`() {
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
}
