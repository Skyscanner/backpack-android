/**
 * Backpack for Android - Skyscanner's Design System
 *
 * Copyright 2025 Skyscanner Ltd
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

package net.skyscanner.backpack.compose.calendar

import net.skyscanner.backpack.compose.calendar.internal.CalendarState
import net.skyscanner.backpack.compose.calendar.internal.data.CalendarCell
import net.skyscanner.backpack.compose.calendar.internal.dispatchClick
import net.skyscanner.backpack.compose.calendar.internal.dispatchParamsUpdate
import net.skyscanner.backpack.compose.calendar.internal.dispatchSetSelection
import java.time.LocalDate
import java.time.Month
import java.util.Locale

class CalendarTestState(
    private var state: CalendarState,
) {
    val currentState: CalendarState get() = state

    fun onClick(calendarInteraction: CalendarInteraction) {
        when (calendarInteraction) {
            is CalendarInteraction.DateClicked -> {
                state = state.dispatchClick(calendarInteraction.day)
            }
        }
    }

    fun setSelection(selection: CalendarSelection) {
        state = state.dispatchSetSelection(selection)
    }

    fun setParams(params: CalendarParams) {
        state = state.dispatchParamsUpdate(params)
    }
}

internal val CalendarTestState.firstDay: CalendarCell.Day
    get() {
        val index = currentState.cells.indexOf(CalendarSettings.Default.range.start)
        return currentState.cells[index] as CalendarCell.Day
    }

internal val CalendarTestState.lastDay: CalendarCell.Day
    get() {
        val index = currentState.cells.indexOf(CalendarSettings.Default.range.endInclusive)
        return currentState.cells[index] as CalendarCell.Day
    }

internal val CalendarTestState.header: CalendarCell.Header
    get() = currentState.cells[0] as CalendarCell.Header

internal fun CalendarTestState.rangeOf(start: CalendarCell.Day, end: CalendarCell.Day): IntRange {
    val indexOfFirst = currentState.cells.indexOf(start.date)
    val indexOfLast = currentState.cells.indexOf(end.date)
    return indexOfFirst..indexOfLast
}

internal fun testCalendarWith(
    params: CalendarParams,
    block: CalendarTestState.() -> Unit,
) {
    val testState = CalendarTestState(CalendarState(params))
    block(testState)
}

object CalendarSettings {

    val Default = CalendarParams(
        range = LocalDate.of(2000, Month.JANUARY, 1)..LocalDate.of(2000, Month.DECEMBER, 31),
        selectionMode = CalendarParams.SelectionMode.Single(),
        locale = Locale.ENGLISH,
    )
}
