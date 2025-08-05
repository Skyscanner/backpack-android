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

package net.skyscanner.backpack.compose.calendar.data

import net.skyscanner.backpack.compose.calendar.CalendarSettings
import net.skyscanner.backpack.compose.calendar.firstDay
import net.skyscanner.backpack.compose.calendar.internal.CalendarParams
import net.skyscanner.backpack.compose.calendar.internal.CalendarSelection
import net.skyscanner.backpack.compose.calendar.internal.CellInfo
import net.skyscanner.backpack.compose.calendar.lastDay
import net.skyscanner.backpack.compose.calendar.testCalendarWith
import org.junit.Assert.assertEquals
import org.junit.Test

class CalendarSettersTests {

    @Test
    fun setParams_changes_params() {
        testCalendarWith(CalendarSettings.Default) {
            setParams(CalendarSettings.Default.copy(selectionMode = CalendarParams.SelectionMode.Disabled))
            assertEquals(CalendarParams.SelectionMode.Disabled, currentState.params.selectionMode)
        }
    }

    @Test
    fun setSelection_of_single_changes_selection() {
        val disabledParams = CalendarSettings.Default.copy(
            selectionMode = CalendarParams.SelectionMode.Single(),
        )
        testCalendarWith(disabledParams) {
            setSelection(CalendarSelection.Single(firstDay.date))

            assertEquals(CalendarSelection.Single(firstDay.date), currentState.selection)
        }
    }

    @Test
    fun setSelection_of_range_changes_selection() {
        val disabledParams = CalendarSettings.Default.copy(
            selectionMode = CalendarParams.SelectionMode.Range(),
        )
        testCalendarWith(disabledParams) {
            setSelection(CalendarSelection.Range(firstDay.date, lastDay.date))

            assertEquals(CalendarSelection.Range(firstDay.date, lastDay.date), currentState.selection)
        }
    }

    @Test
    fun setSelection_of_single_ignores_change_when_selectionMode_is_disabled() {
        val disabledParams = CalendarSettings.Default.copy(
            selectionMode = CalendarParams.SelectionMode.Disabled,
        )
        testCalendarWith(disabledParams) {
            setSelection(CalendarSelection.Single(firstDay.date))

            assertEquals(CalendarSelection.None, currentState.selection)
            assertEquals(CalendarParams.SelectionMode.Disabled, currentState.params.selectionMode)
        }
    }

    @Test
    fun setSelection_of_range_ignores_change_when_selectionMode_is_disabled() {
        val disabledParams = CalendarSettings.Default.copy(
            selectionMode = CalendarParams.SelectionMode.Disabled,
        )
        testCalendarWith(disabledParams) {
            setSelection(CalendarSelection.Range(firstDay.date, lastDay.date))

            assertEquals(CalendarSelection.None, currentState.selection)
            assertEquals(CalendarParams.SelectionMode.Disabled, currentState.params.selectionMode)
        }
    }

    @Test
    fun setSelection_of_single_ignores_change_when_date_is_disabled() {
        val disabledParams = CalendarSettings.Default.copy(
            selectionMode = CalendarParams.SelectionMode.Single(),
            cellsInfo = mapOf(
                CalendarSettings.Default.range.start to CellInfo(disabled = true),
            ),
        )
        testCalendarWith(disabledParams) {
            setSelection(CalendarSelection.Single(firstDay.date))

            assertEquals(CalendarSelection.None, currentState.selection)
        }
    }

    @Test
    fun setSelection_of_range_ignores_change_when_start_date_is_disabled() {
        val disabledParams = CalendarSettings.Default.copy(
            selectionMode = CalendarParams.SelectionMode.Range(),
            cellsInfo = mapOf(
                CalendarSettings.Default.range.start to CellInfo(disabled = true),
            ),
        )
        testCalendarWith(disabledParams) {
            setSelection(CalendarSelection.Range(start = firstDay.date, end = null))

            assertEquals(CalendarSelection.None, currentState.selection)
        }
    }

    @Test
    fun setSelection_of_range_ignores_change_when_end_date_is_disabled() {
        val disabledParams = CalendarSettings.Default.copy(
            selectionMode = CalendarParams.SelectionMode.Range(),
            cellsInfo = mapOf(
                CalendarSettings.Default.range.endInclusive to CellInfo(disabled = true),
            ),
        )
        testCalendarWith(disabledParams) {
            setSelection(CalendarSelection.Range(start = firstDay.date, end = lastDay.date))

            assertEquals(CalendarSelection.None, currentState.selection)
        }
    }

    @Test
    fun setSelection_of_single_ignores_change_when_date_is_out_of_range() {
        val disabledParams = CalendarSettings.Default.copy(
            selectionMode = CalendarParams.SelectionMode.Single(),
        )
        testCalendarWith(disabledParams) {
            setSelection(CalendarSelection.Single(firstDay.date.minusMonths(1)))

            assertEquals(CalendarSelection.None, currentState.selection)
        }
    }

    @Test
    fun setSelection_of_range_ignores_change_when_start_date_is_out_of_range() {
        val disabledParams = CalendarSettings.Default.copy(
            selectionMode = CalendarParams.SelectionMode.Range(),
        )
        testCalendarWith(disabledParams) {
            setSelection(CalendarSelection.Range(start = firstDay.date.minusMonths(1), end = null))

            assertEquals(CalendarSelection.None, currentState.selection)
        }
    }

    @Test
    fun setSelection_of_range_ignores_change_when_end_date_is_out_of_range() {
        val disabledParams = CalendarSettings.Default.copy(
            selectionMode = CalendarParams.SelectionMode.Range(),
        )
        testCalendarWith(disabledParams) {
            setSelection(CalendarSelection.Range(start = firstDay.date, end = lastDay.date.plusMonths(1)))

            assertEquals(CalendarSelection.None, currentState.selection)
        }
    }

    @Test
    fun setSelection_of_month_changes_selection() {
        testCalendarWith(CalendarSettings.Default) {
            setParams(
                CalendarSettings.Default.copy(
                    selectionMode = CalendarParams.SelectionMode.Range(),
                ),
            )
            assertEquals(CalendarParams.SelectionMode.Range(), currentState.params.selectionMode)
        }
    }
}
