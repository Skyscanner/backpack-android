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
import net.skyscanner.backpack.calendar2.firstDay
import net.skyscanner.backpack.calendar2.initAndroidThreeTen
import net.skyscanner.backpack.calendar2.testCalendarWith
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.threeten.bp.LocalDate
import org.threeten.bp.Month

class CalendarAccessibilityLabelTests {

    @Before
    fun setup() {
        initAndroidThreeTen()
    }

    @Test
    fun accessibility_labels_are_correct_when_single_selection_mode_and_no_date_selected() {
        val calendarParams = CalendarSettings.Default.copy(
            selectionMode = CalendarParams.SelectionMode.Single(
                startSelectionHint = "startSelectionHint",
                noSelectionState = "noSelectionState",
                startSelectionState = "startSelectionState",
            ),
        )
        testCalendarWith(calendarParams) {
            verify {
                assertEquals(LocalDate.of(2000, Month.JANUARY, 1), (state.cells[7] as CalendarCell.Day).date)
                assertEquals("noSelectionState", (state.cells[7] as CalendarCell.Day).stateDescription)
                assertEquals("startSelectionHint", (state.cells[7] as CalendarCell.Day).onClickLabel)
            }
        }
    }

    @Test
    fun accessibility_labels_are_correct_when_single_selection_mode_and_date_selected() {
        val calendarParams = CalendarSettings.Default.copy(
            selectionMode = CalendarParams.SelectionMode.Single(
                startSelectionHint = "startSelectionHint",
                noSelectionState = "noSelectionState",
                startSelectionState = "startSelectionState",
            ),
        )
        testCalendarWith(calendarParams) {
            stateMachine.onClick(CalendarInteraction.DateClicked(firstDay))
            val startDate = state.cells[7] as CalendarCell.Day // First of January

            verify {
                assertEquals(LocalDate.of(2000, Month.JANUARY, 1), startDate.date)
                assertEquals("startSelectionHint", startDate.onClickLabel)
                assertEquals("startSelectionState", startDate.stateDescription)
            }
        }
    }

    @Test
    fun accessibility_labels_are_correct_when_range_selection_mode_and_no_date_selected() {
        val calendarParams = CalendarSettings.Default.copy(
            selectionMode = CalendarParams.SelectionMode.Range(
                startSelectionHint = "startSelectionHint",
                noSelectionState = "noSelectionState",
                startSelectionState = "startSelectionState",
                endSelectionHint = "endSelectionHint",
                endSelectionState = "endSelectionState",
            ),
        )
        testCalendarWith(calendarParams) {
            verify {
                assertEquals(LocalDate.of(2000, Month.JANUARY, 1), (state.cells[7] as CalendarCell.Day).date)
                assertEquals("startSelectionHint", (state.cells[7] as CalendarCell.Day).onClickLabel)
                assertEquals(null, (state.cells[7] as CalendarCell.Day).stateDescription)
            }
        }
    }

    @Test
    fun accessibility_labels_are_correct_when_range_selection_mode_and_start_date_selected() {
        val calendarParams = CalendarSettings.Default.copy(
            selectionMode = CalendarParams.SelectionMode.Range(
                startSelectionHint = "startSelectionHint",
                noSelectionState = "noSelectionState",
                startSelectionState = "startSelectionState",
                endSelectionHint = "endSelectionHint",
                endSelectionState = "endSelectionState",
            ),
        )
        testCalendarWith(calendarParams) {
            stateMachine.onClick(CalendarInteraction.DateClicked(firstDay))

            verify {

                assertEquals(LocalDate.of(2000, Month.JANUARY, 1), (state.cells[7] as CalendarCell.Day).date)
                assertEquals("endSelectionHint", (state.cells[7] as CalendarCell.Day).onClickLabel)
                assertEquals("startSelectionState", (state.cells[7] as CalendarCell.Day).stateDescription)
                assertEquals("endSelectionHint", (state.cells[8] as CalendarCell.Day).onClickLabel)
                assertEquals(null, (state.cells[8] as CalendarCell.Day).stateDescription)
            }
        }
    }

    @Test
    fun accessibility_labels_are_correct_when_range_selection_mode_and_start_and_end_dates_selected() {
        val calendarParams = CalendarSettings.Default.copy(
            selectionMode = CalendarParams.SelectionMode.Range(
                startSelectionHint = "startSelectionHint",
                noSelectionState = "noSelectionState",
                startSelectionState = "startSelectionState",
                endSelectionHint = "endSelectionHint",
                endSelectionState = "endSelectionState",
            ),
        )
        testCalendarWith(calendarParams) {

            stateMachine.onClick(CalendarInteraction.DateClicked(firstDay))
            stateMachine.onClick(CalendarInteraction.DateClicked(state.cells[8] as CalendarCell.Day))

            val startDate = state.cells[7] as CalendarCell.Day // First of January
            val endDate = state.cells[8] as CalendarCell.Day

            verify {

                assertEquals("startSelectionHint", startDate.onClickLabel)
                assertEquals("startSelectionState", startDate.stateDescription)
                assertEquals("startSelectionHint", endDate.onClickLabel)
                assertEquals("endSelectionState", endDate.stateDescription)
                assertEquals(null, (state.cells[9] as CalendarCell.Day).stateDescription)
            }
        }
    }
}
