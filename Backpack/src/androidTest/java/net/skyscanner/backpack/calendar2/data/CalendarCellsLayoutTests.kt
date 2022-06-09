/**
 * Backpack for Android - Skyscanner's Design System
 *
 * Copyright 2018 Skyscanner Ltd
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

import net.skyscanner.backpack.calendar2.CalendarParams
import net.skyscanner.backpack.calendar2.CalendarSettings
import net.skyscanner.backpack.calendar2.extension.yearMonth
import net.skyscanner.backpack.calendar2.testCalendarWith
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.threeten.bp.Month

class CalendarCellsLayoutTests {

  @Test
  fun header_is_the_first_cell_in_month() {
    testCalendarWith(CalendarSettings.Default) {
      verify {
        assertEquals("January", (state.cells[0] as CalendarCell.Header).title)
      }
    }
  }

  @Test
  fun there_are_leading_spaces_in_month() {
    testCalendarWith(CalendarSettings.Default) {
      verify {
        for (i in 1..6) {
          assertTrue(state.cells[i] is CalendarCell.Space)
          assertEquals(CalendarSettings.Default.range.start.yearMonth(), state.cells[i].yearMonth)
        }
        assertTrue(state.cells[7] is CalendarCell.Day)
      }
    }
  }

  @Test
  fun there_are_trailing_spaces_in_month() {
    testCalendarWith(CalendarSettings.Default) {
      verify {
        assertTrue(state.cells[37] is CalendarCell.Day)
        for (i in 38..42) {
          assertTrue(state.cells[i] is CalendarCell.Space)
          assertEquals(CalendarSettings.Default.range.start.yearMonth(), state.cells[i].yearMonth)
        }
      }
    }
  }

  @Test
  fun there_are_leading_dates_in_first_month_of_range() {
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
  fun there_are_trailing_dates_in_last_month_of_range() {
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
  fun there_are_correct_days_in_month() {
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
  fun header_is_the_first_cell_in_secondary_month() {
    testCalendarWith(CalendarSettings.Default) {
      verify {
        assertEquals("February", (state.cells[43] as CalendarCell.Header).title)
      }
    }
  }

  @Test
  fun select_whole_month_button_is_shown_when_whole_month_selection_is_enabled() {
    val label = "Select whole month"
    val calenderParams = CalendarSettings.Default.copy(
      selectionMode = CalendarParams.SelectionMode.Month(label),
    )
    testCalendarWith(calenderParams) {
      verify {
        assertEquals(label, (state.cells[0] as CalendarCell.Header).selectWholeMonthLabel)
      }
    }
  }

  @Test
  fun select_whole_month_button_is_hidden_when_whole_month_selection_is_disabled() {
    val calenderParams = CalendarSettings.Default.copy(
      selectionMode = CalendarParams.SelectionMode.Dates,
    )
    testCalendarWith(calenderParams) {
      verify {
        assertNull((state.cells[0] as CalendarCell.Header).selectWholeMonthLabel)
      }
    }
  }
}
