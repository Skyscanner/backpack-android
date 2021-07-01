package net.skyscanner.backpack.calendar2.data

import kotlinx.coroutines.ExperimentalCoroutinesApi
import net.skyscanner.backpack.calendar2.CalendarParams
import net.skyscanner.backpack.calendar2.CalendarSelection
import net.skyscanner.backpack.calendar2.CalendarSettings
import net.skyscanner.backpack.calendar2.CalendarSettings.Default
import net.skyscanner.backpack.calendar2.firstDay
import net.skyscanner.backpack.calendar2.testCalendarWith
import org.junit.Assert.assertTrue
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CalendarSelectionTests {

  @Test
  fun `no date selected by default`() {
    testCalendarWith(CalendarSettings.Default) {
      verify {
        assertTrue(state.selection is CalendarSelection.None)
      }
    }
  }

  @Test
  fun `if selection is disabled no date can be selected`() {
    testCalendarWith(
      Default.copy(
        selectionMode = CalendarParams.SelectionMode.Disabled,
      )
    ) {
      stateMachine.onClick(firstDay)

      verify {
        assertTrue(state.selection is CalendarSelection.None)
      }
    }
  }
}
