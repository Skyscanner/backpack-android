/**
 * Backpack for Android - Skyscanner's Design System
 *
 * Copyright 2018-2021 Skyscanner Ltd
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.skyscanner.backpack.calendar2.data

import net.skyscanner.backpack.calendar2.CalendarSettings
import net.skyscanner.backpack.calendar2.testCalendarWith
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
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
  fun `there are leading dates in first month of range`() {
    testCalendarWith(
      CalendarSettings.Default.copy(
        range = CalendarSettings.Default.range.start.plusDays(3)..CalendarSettings.Default.range.endInclusive,
      )
    ) {
      verify {
        assertEquals(1, (state.cells[7] as CalendarCell.Day).date.dayOfMonth)
        assertEquals(2, (state.cells[8] as CalendarCell.Day).date.dayOfMonth)
        assertEquals(3, (state.cells[9] as CalendarCell.Day).date.dayOfMonth)
        assertEquals(4, (state.cells[10] as CalendarCell.Day).date.dayOfMonth)
        assertTrue((state.cells[7] as CalendarCell.Day).outOfRange)
        assertTrue((state.cells[8] as CalendarCell.Day).outOfRange)
        assertTrue((state.cells[9] as CalendarCell.Day).outOfRange)
        assertFalse((state.cells[10] as CalendarCell.Day).outOfRange)
      }
    }
  }

  @Test
  fun `there are trailing dates in last month of range`() {
    testCalendarWith(
      CalendarSettings.Default.copy(
        range = CalendarSettings.Default.range.start..CalendarSettings.Default.range.start.plusDays(27),
      )
    ) {
      verify {
        assertEquals(31, (state.cells[37] as CalendarCell.Day).date.dayOfMonth)
        assertEquals(30, (state.cells[36] as CalendarCell.Day).date.dayOfMonth)
        assertEquals(29, (state.cells[35] as CalendarCell.Day).date.dayOfMonth)
        assertEquals(28, (state.cells[34] as CalendarCell.Day).date.dayOfMonth)
        assertTrue((state.cells[37] as CalendarCell.Day).outOfRange)
        assertTrue((state.cells[36] as CalendarCell.Day).outOfRange)
        assertTrue((state.cells[35] as CalendarCell.Day).outOfRange)
        assertFalse((state.cells[34] as CalendarCell.Day).outOfRange)
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
  fun `header is the first cell in secondary month`() {
    testCalendarWith(CalendarSettings.Default) {
      verify {
        assertEquals("February", (state.cells[43] as CalendarCell.Header).title)
      }
    }
  }
}
