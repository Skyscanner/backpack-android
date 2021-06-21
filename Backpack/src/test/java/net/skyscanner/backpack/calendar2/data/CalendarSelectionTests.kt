package net.skyscanner.backpack.calendar2.data

import kotlinx.coroutines.ExperimentalCoroutinesApi
import net.skyscanner.backpack.calendar2.CalendarSelection
import net.skyscanner.backpack.calendar2.CalendarSettings
import net.skyscanner.backpack.calendar2.firstDay
import net.skyscanner.backpack.calendar2.testCalendarWith
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CalendarSelectionTests {

  @Test
  fun byDefault_noDateSelected() {
    testCalendarWith(CalendarSettings.Default) {
      verify {
        assert(state.selection is CalendarSelection.None)
      }
    }
  }

  @Test
  fun whenSelectionIsDisabled_noDateCanBeSelected() {
    testCalendarWith(CalendarSettings.DisabledSelection) {
      stateMachine.onClick(firstDay)

      verify {
        assert(state.selection is CalendarSelection.None)
      }
    }
  }

  @Test
  fun whenSelectionIsSingle_singleDateCanBeSelected() {
    testCalendarWith(CalendarSettings.SingleSelection) {
      stateMachine.onClick(firstDay)

      verify {
        assert(state.selection == CalendarSelection.Single(firstDay.date))
      }
    }
  }
}
