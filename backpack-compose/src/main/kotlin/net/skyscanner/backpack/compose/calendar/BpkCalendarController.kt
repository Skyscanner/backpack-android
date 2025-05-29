/*
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

package net.skyscanner.backpack.compose.calendar

import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import net.skyscanner.backpack.calendar2.CalendarParams
import net.skyscanner.backpack.calendar2.CalendarSelection
import net.skyscanner.backpack.calendar2.CalendarState
import net.skyscanner.backpack.calendar2.CellInfo
import net.skyscanner.backpack.calendar2.data.CalendarInteraction
import net.skyscanner.backpack.calendar2.data.dispatchClick
import net.skyscanner.backpack.calendar2.data.dispatchSetSelection
import java.io.Serializable
import java.time.LocalDate
import java.time.YearMonth

class BpkCalendarController(
    initialParams: CalendarParams,
    initialSelection: CalendarSelection = CalendarSelection.None,
    internal val lazyGridState: LazyGridState = LazyGridState(),
    internal val onSelectionChanged: (CalendarSelection) -> Unit,
) {
    private var _state by mutableStateOf(CalendarState(initialParams, initialSelection))

    val state by derivedStateOf { _state }

    /**
     * Sets the selection of a calendar.
     */
    fun setSelection(selection: CalendarSelection) {
        _state = _state.dispatchSetSelection(selection)
    }

    /**
     * Scrolls to a specific date in a calendar.
     * Does nothing if the date is out of range.
     */
    suspend fun scrollToDate(date: LocalDate) {
        val index = _state.cells.indexOf(date)
        if (index >= 0) lazyGridState.scrollToItem(index)
    }

    /**
     * Scrolls with animation to a specific date in a calendar.
     * Does nothing if the date is out of range.
     */
    suspend fun smoothScrollToDate(date: LocalDate) {
        val index = _state.cells.indexOf(date)
        if (index >= 0) lazyGridState.animateScrollToItem(index)
    }

    /**
     * Updates the parameters of the calendar.
     */
    fun setParams(value: CalendarParams) {
        _state = CalendarState(value, _state.selection)
    }

    /**
     * Updates the state of the calendar.
     */
    private fun updateState(newState: CalendarState) {
        if (newState != _state) {
            _state = newState
            onSelectionChanged(_state.selection)
        }
    }

    private val firstVisibleItemMonth: YearMonth
        get() = _state.cells[lazyGridState.firstVisibleItemIndex].yearMonth

    internal val visibleMonths: Set<YearMonth>
        get() = lazyGridState.layoutInfo.visibleItemsInfo.map { _state.cells[it.index].yearMonth }.toSet()

    /**
     * Returns the first visible item year.
     */
    internal val firstVisibleItemYear: Int
        get() = firstVisibleItemMonth.year

    /**
     * Handles the click events of a calendar.
     */
    internal fun onClick(calendarInteraction: CalendarInteraction) {
        when (calendarInteraction) {
            is CalendarInteraction.DateClicked -> updateState(_state.dispatchClick(calendarInteraction.day))
            is CalendarInteraction.SelectMonthClicked -> updateState(_state.dispatchClick(calendarInteraction.header))
        }
    }
}

@Composable
fun rememberCalendarController(
    initialParams: CalendarParams,
    initialSelection: CalendarSelection = CalendarSelection.None,
    lazyGridState: LazyGridState = rememberLazyGridState(),
    onSelectionChanged: (CalendarSelection) -> Unit,
): BpkCalendarController =
    rememberSaveable(
        inputs = arrayOf(initialParams),
        saver = Saver<BpkCalendarController, Serializable>(
            save = {
                with(it.state.params) {
                    CalendarSavableData(
                        it.state.selection,
                        cellsInfo,
                        selectionMode,
                    )
                }
            },
            restore = { savedData ->
                val (savedSelection, cellsInfo, selectionMode) = savedData as CalendarSavableData
                BpkCalendarController(
                    initialParams.copy(cellsInfo = cellsInfo, selectionMode = selectionMode),
                    savedSelection,
                    lazyGridState,
                    onSelectionChanged,
                )
            },
        ),
        init = {
            BpkCalendarController(initialParams, initialSelection, lazyGridState, onSelectionChanged)
        },
    )

internal data class CalendarSavableData(
    val selection: CalendarSelection,
    val cellsInfo: Map<LocalDate, CellInfo>,
    val selectionMode: CalendarParams.SelectionMode,
) : Serializable
