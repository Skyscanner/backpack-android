package net.skyscanner.backpack.calendar2.data

import java.util.Locale
import net.skyscanner.backpack.calendar2.CalendarSettings
import net.skyscanner.backpack.calendar2.testCalendarWith
import org.junit.Assert.assertEquals
import org.junit.Test
import org.threeten.bp.DayOfWeek

class CalendarLocalisationTests {

  private val russianLocale = CalendarSettings.Default.copy(
    locale = Locale.forLanguageTag("ru-RU"),
  )

  @Test
  fun `month titles depend on locale`() {
    testCalendarWith(russianLocale) {
      verify {
        assertEquals("Январь", (state.cells[0] as CalendarCell.Header).title)
      }
    }
  }

  @Test
  fun `week fields order depends on locale`() {
    testCalendarWith(CalendarSettings.Default) {
      verify {
        assertEquals(DayOfWeek.SUNDAY, state.params.weekFields.firstDayOfWeek)
      }
    }
  }

  @Test
  fun `when locale changes week fields order is updated`() {
    testCalendarWith(CalendarSettings.Default) {
      stateMachine.onLocaleChanged(russianLocale.locale)
      verify {
        assertEquals(DayOfWeek.MONDAY, state.params.weekFields.firstDayOfWeek)
      }
    }
  }

  @Test
  fun `days content description depends on locale`() {
    testCalendarWith(russianLocale) {
      verify {
        assertEquals("1 января", (state.cells[6] as CalendarCell.Day).contentDescription)
      }
    }
  }
}
