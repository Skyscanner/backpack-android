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
import net.skyscanner.backpack.compose.calendar.firstDay
import net.skyscanner.backpack.compose.calendar.CalendarParams
import net.skyscanner.backpack.compose.calendar.CalendarSelection
import net.skyscanner.backpack.compose.calendar.CellInfo
import net.skyscanner.backpack.compose.calendar.internal.data.CalendarCell
import net.skyscanner.backpack.compose.calendar.CalendarInteraction
import net.skyscanner.backpack.compose.calendar.lastDay
import net.skyscanner.backpack.compose.calendar.rangeOf
import net.skyscanner.backpack.compose.calendar.testCalendarWith
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class CalendarRangeSelectionTests {

    private val rangeSelection = CalendarSettings.Default.copy(
        selectionMode = CalendarParams.SelectionMode.Range(),
    )

    @Test
    fun when_range_is_opened_selection_is_correct() {
        testCalendarWith(rangeSelection) {
            onClick(CalendarInteraction.DateClicked(firstDay))

            assertEquals(CalendarSelection.Range(start = firstDay.date, end = null), currentState.selection)
        }
    }

    @Test
    fun when_range_is_closed_selection_is_correct() {
        testCalendarWith(rangeSelection) {
            onClick(CalendarInteraction.DateClicked(firstDay))
            onClick(CalendarInteraction.DateClicked(lastDay))

            assertEquals(CalendarSelection.Range(firstDay.date, lastDay.date), currentState.selection)
        }
    }

    @Test
    fun when_range_is_within_the_same_date_selection_is_correct() {
        testCalendarWith(rangeSelection) {
            onClick(CalendarInteraction.DateClicked(firstDay))
            onClick(CalendarInteraction.DateClicked(firstDay))

            assertEquals(CalendarSelection.Range(firstDay.date, firstDay.date), currentState.selection)
        }
    }

    @Test
    fun when_range_is_closing_before_start_a_new_range_is_created() {
        testCalendarWith(rangeSelection) {
            onClick(CalendarInteraction.DateClicked(lastDay))
            onClick(CalendarInteraction.DateClicked(firstDay))
            onClick(CalendarInteraction.DateClicked(lastDay))
            assertEquals(CalendarSelection.Range(firstDay.date, lastDay.date), currentState.selection)
        }
    }

    @Test
    fun range_can_be_reselected() {
        testCalendarWith(rangeSelection) {
            onClick(CalendarInteraction.DateClicked(firstDay))
            onClick(CalendarInteraction.DateClicked(lastDay))
            onClick(CalendarInteraction.DateClicked(firstDay))
            onClick(CalendarInteraction.DateClicked(firstDay))
            assertEquals(CalendarSelection.Range(firstDay.date, firstDay.date), currentState.selection)
        }
    }

    @Test
    fun when_range_is_selected_cells_have_correct_state() {
        val disabledDates = mapOf(
            rangeSelection.range.start.plusDays(1) to CellInfo(disabled = true),
        )
        testCalendarWith(rangeSelection.copy(cellsInfo = disabledDates)) {
            onClick(CalendarInteraction.DateClicked(firstDay))
            onClick(CalendarInteraction.DateClicked(lastDay))

            for (i in rangeOf(firstDay, lastDay)) {
                when (val cell = currentState.cells[i]) {
                    is CalendarCell.Day -> when (cell) {
                        firstDay -> assertEquals(CalendarCell.Selection.Start, cell.selection)
                        lastDay -> assertEquals(CalendarCell.Selection.End, cell.selection)
                        else -> assertEquals(CalendarCell.Selection.Middle, cell.selection)
                    }

                    is CalendarCell.Space -> assertTrue(cell.selected)
                    is CalendarCell.Header -> {}
                }
            }
        }
    }

    @Test
    fun when_range_is_within_the_same_date_cells_have_correct_state() {
        testCalendarWith(rangeSelection) {
            onClick(CalendarInteraction.DateClicked(firstDay))
            onClick(CalendarInteraction.DateClicked(firstDay))

            assertEquals(CalendarCell.Selection.Double, firstDay.selection)
        }
    }

    @Test
    fun disabled_date_cannot_be_selected() {
        val disabledDates = mapOf(
            rangeSelection.range.start to CellInfo(disabled = true),
        )

        testCalendarWith(rangeSelection.copy(cellsInfo = disabledDates)) {
            onClick(CalendarInteraction.DateClicked(firstDay))

            assertTrue(currentState.selection is CalendarSelection.None)
        }
    }
}
