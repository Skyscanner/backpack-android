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
package net.skyscanner.backpack.compose.calendar.internal

import androidx.compose.runtime.Immutable
import net.skyscanner.backpack.compose.calendar.CalendarParams
import net.skyscanner.backpack.compose.calendar.CalendarSelection
import net.skyscanner.backpack.compose.calendar.CalendarParams.SelectionMode
import net.skyscanner.backpack.compose.calendar.internal.data.CalendarCell
import net.skyscanner.backpack.compose.calendar.internal.data.CalendarCells
import net.skyscanner.backpack.util.InternalBackpackApi
import kotlin.collections.get

fun CalendarState.dispatchClick(date: CalendarCell.Day): CalendarState {
    if (date.inactive) return this

    val selection = when (params.selectionMode) {
        is SelectionMode.Disabled -> selection
        is SelectionMode.Single -> CalendarSelection.Single(date.date)
        is SelectionMode.Range -> {
            val rangeStart = (selection as? CalendarSelection.Range)?.start
            val rangeEnd = (selection as? CalendarSelection.Range)?.end
            when {
                rangeStart != null && rangeEnd != null -> CalendarSelection.Range(start = date.date, end = null)
                rangeStart == null -> CalendarSelection.Range(start = date.date, end = null)
                date.date < rangeStart -> CalendarSelection.Range(start = date.date, end = null)
                else -> CalendarSelection.Range(start = rangeStart, end = date.date)
            }
        }
    }

    return copy(
        selection = selection,
        cells = CalendarCells(params = params, selection = selection),
    )
}

fun CalendarState.dispatchParamsUpdate(params: CalendarParams): CalendarState =
    copy(
        params = params,
        cells = CalendarCells(
            params = params,
            selection = selection,
        ),
    )

fun CalendarState.dispatchSetSelection(selection: CalendarSelection): CalendarState {
    when (selection) {
        is CalendarSelection.None -> Unit
        is CalendarSelection.Range -> when {
            params.selectionMode !is SelectionMode.Range -> return this
            params.cellsInfo[selection.start]?.disabled == true -> return this
            params.cellsInfo[selection.end]?.disabled == true -> return this
            selection.start !in params.range -> return this
            selection.end?.let { it !in params.range } == true -> return this
        }

        is CalendarSelection.Single -> when {
            params.selectionMode !is SelectionMode.Single -> return this
            params.cellsInfo[selection.date]?.disabled == true -> return this
            selection.date !in params.range -> return this
        }
    }
    return copy(
        selection = selection,
        cells = CalendarCells(params, selection),
    )
}
@Immutable
data class CalendarState(
    val params: CalendarParams,
    val selection: CalendarSelection = CalendarSelection.None,
    @InternalBackpackApi val cells: CalendarCells = CalendarCells(params, selection),
)
