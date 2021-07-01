package net.skyscanner.backpack.calendar2.data

import net.skyscanner.backpack.calendar2.CalendarSettings
import net.skyscanner.backpack.calendar2.testCalendarWith
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.threeten.bp.Month

class CalendarCellsLayoutTests {

  @Test
  fun `header is the first cell in month`() {
    testCalendarWith(CalendarSettings.Default) {
      verify {
        assertEquals("January", (state.cells[0] as CalendarCell.Header).title)
      }
    }
  }

  @Test
  fun `there are leading spaces in month`() {
    testCalendarWith(CalendarSettings.Default) {
      verify {
        assertTrue(state.cells[1] is CalendarCell.Space)
        assertTrue(state.cells[2] is CalendarCell.Space)
        assertTrue(state.cells[3] is CalendarCell.Space)
        assertTrue(state.cells[4] is CalendarCell.Space)
        assertTrue(state.cells[5] is CalendarCell.Space)
        assertTrue(state.cells[6] is CalendarCell.Space)
        assertTrue(state.cells[7] is CalendarCell.Day)
      }
    }
  }

  @Test
  fun `there are correct days in month`() {
    testCalendarWith(CalendarSettings.Default) {
      verify {
        val numDaysInJan = 31
        val firstIndex = 7
        val lastDayIndex = firstIndex + numDaysInJan
        for (i in firstIndex until lastDayIndex) {
          val cell = state.cells[i] as CalendarCell.Day
          assertEquals(i - firstIndex + 1, cell.date.dayOfMonth)
          assertEquals(Month.JANUARY, cell.date.month)
          assertEquals(2000, cell.date.year)
        }
      }
    }
  }

  @Test
  fun `there are trailing spaces in month`() {
    testCalendarWith(CalendarSettings.Default) {
      verify {
        assertTrue(state.cells[37] is CalendarCell.Day)
        assertTrue(state.cells[38] is CalendarCell.Space)
        assertTrue(state.cells[39] is CalendarCell.Space)
        assertTrue(state.cells[40] is CalendarCell.Space)
        assertTrue(state.cells[41] is CalendarCell.Space)
        assertTrue(state.cells[42] is CalendarCell.Space)
      }
    }
  }

  @Test
  fun `header is the first cell in secondary month`() {
    testCalendarWith(CalendarSettings.Default) {
      verify {
        assertEquals("February", (state.cells[43] as CalendarCell.Header).title)
      }
    }
  }
}
