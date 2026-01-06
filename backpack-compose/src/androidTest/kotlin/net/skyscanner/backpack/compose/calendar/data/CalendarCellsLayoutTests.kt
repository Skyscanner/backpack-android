/**
 * Backpack for Android - Skyscanner's Design System
 *
 * Copyright 2018 - 2026 Skyscanner Ltd
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

package net.skyscanner.backpack.compose.calendar.data

import net.skyscanner.backpack.compose.calendar.CalendarSettings
import net.skyscanner.backpack.compose.calendar.internal.data.CalendarCell
import net.skyscanner.backpack.compose.calendar.internal.extension.yearMonth
import net.skyscanner.backpack.compose.calendar.testCalendarWith
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import java.time.Month

class CalendarCellsLayoutTests {

    @Test
    fun header_is_the_first_cell_in_month() {
        testCalendarWith(CalendarSettings.Default) {
            assertEquals("January", (currentState.cells[0] as CalendarCell.Header).title)
        }
    }

    @Test
    fun header_has_year() {
        testCalendarWith(
            CalendarSettings.Default.copy(
                yearLabelInMonthHeader = true,
            ),
        ) {
            assertEquals("January 2000", (currentState.cells[0] as CalendarCell.Header).title)
        }
    }

    @Test
    fun there_are_leading_spaces_in_month() {
        testCalendarWith(CalendarSettings.Default) {
            for (i in 1..6) {
                assertTrue(currentState.cells[i] is CalendarCell.Space)
                assertEquals(CalendarSettings.Default.range.start.yearMonth(), currentState.cells[i].yearMonth)
            }
            assertTrue(currentState.cells[7] is CalendarCell.Day)
        }
    }

    @Test
    fun there_are_trailing_spaces_in_month() {
        testCalendarWith(CalendarSettings.Default) {
            assertTrue(currentState.cells[37] is CalendarCell.Day)
            for (i in 38..42) {
                assertTrue(currentState.cells[i] is CalendarCell.Space)
                assertEquals(CalendarSettings.Default.range.start.yearMonth(), currentState.cells[i].yearMonth)
            }
        }
    }

    @Test
    fun there_are_leading_dates_in_first_month_of_range() {
        testCalendarWith(
            CalendarSettings.Default.copy(
                range = CalendarSettings.Default.range.start.plusDays(3)..CalendarSettings.Default.range.endInclusive,
            ),
        ) {
            assertEquals(1, (currentState.cells[7] as CalendarCell.Day).date.dayOfMonth)
            assertEquals(2, (currentState.cells[8] as CalendarCell.Day).date.dayOfMonth)
            assertEquals(3, (currentState.cells[9] as CalendarCell.Day).date.dayOfMonth)
            assertEquals(4, (currentState.cells[10] as CalendarCell.Day).date.dayOfMonth)
            assertTrue((currentState.cells[7] as CalendarCell.Day).outOfRange)
            assertTrue((currentState.cells[8] as CalendarCell.Day).outOfRange)
            assertTrue((currentState.cells[9] as CalendarCell.Day).outOfRange)
            assertFalse((currentState.cells[10] as CalendarCell.Day).outOfRange)
        }
    }

    @Test
    fun there_are_trailing_dates_in_last_month_of_range() {
        testCalendarWith(
            CalendarSettings.Default.copy(
                range = CalendarSettings.Default.range.start..CalendarSettings.Default.range.start.plusDays(27),
            ),
        ) {
            assertEquals(31, (currentState.cells[37] as CalendarCell.Day).date.dayOfMonth)
            assertEquals(30, (currentState.cells[36] as CalendarCell.Day).date.dayOfMonth)
            assertEquals(29, (currentState.cells[35] as CalendarCell.Day).date.dayOfMonth)
            assertEquals(28, (currentState.cells[34] as CalendarCell.Day).date.dayOfMonth)
            assertTrue((currentState.cells[37] as CalendarCell.Day).outOfRange)
            assertTrue((currentState.cells[36] as CalendarCell.Day).outOfRange)
            assertTrue((currentState.cells[35] as CalendarCell.Day).outOfRange)
            assertFalse((currentState.cells[34] as CalendarCell.Day).outOfRange)
        }
    }

    @Test
    fun there_are_correct_days_in_month() {
        testCalendarWith(CalendarSettings.Default) {
            val numDaysInJan = 31
            val firstIndex = 7
            val lastDayIndex = firstIndex + numDaysInJan
            for (i in firstIndex..<lastDayIndex) {
                val cell = currentState.cells[i] as CalendarCell.Day
                assertEquals(i - firstIndex + 1, cell.date.dayOfMonth)
                assertEquals(Month.JANUARY, cell.date.month)
                assertEquals(2000, cell.date.year)
            }
        }
    }

    @Test
    fun header_is_the_first_cell_in_secondary_month() {
        testCalendarWith(CalendarSettings.Default) {
            assertEquals("February", (currentState.cells[43] as CalendarCell.Header).title)
        }
    }

    @Test
    fun header_has_year_in_secondary_month() {
        testCalendarWith(
            CalendarSettings.Default.copy(
                yearLabelInMonthHeader = true,
            ),
        ) {
            assertEquals("February 2000", (currentState.cells[43] as CalendarCell.Header).title)
        }
    }
}
