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

import java.util.Locale
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.plus
import net.skyscanner.backpack.calendar2.CalendarComponent
import net.skyscanner.backpack.calendar2.CalendarParams
import net.skyscanner.backpack.calendar2.CalendarSelection
import net.skyscanner.backpack.calendar2.CalendarState
import net.skyscanner.backpack.util.MutableStateMachine
import net.skyscanner.backpack.util.StateMachine

internal interface CalendarStateMachine : CalendarComponent, StateMachine<CalendarState, Nothing> {

  fun onClick(date: CalendarCell.Day)

  fun onLocaleChanged(locale: Locale)
}

internal fun CalendarStateMachine(
  scope: CoroutineScope,
  initialParams: CalendarParams,
  dispatcher: CoroutineDispatcher,
): CalendarStateMachine {

  val fsm = MutableStateMachine<CalendarState, Nothing>(scope + dispatcher, CalendarState(initialParams))

  return object : CalendarStateMachine, StateMachine<CalendarState, Nothing> by fsm {

    override fun setParams(value: CalendarParams) {
      fsm.commit {
        it.dispatchParamsUpdate(value)
      }
    }

    override fun onClick(date: CalendarCell.Day) {
      fsm.commit {
        it.dispatchClick(date)
      }
    }

    override fun setSelection(selection: CalendarSelection) {
      fsm.commit {
        it.dispatchSetSelection(selection)
      }
    }

    override fun onLocaleChanged(locale: Locale) {
      fsm.commit {
        it.dispatchParamsUpdate(it.params.copy(locale = locale))
      }
    }
  }
}

internal fun CalendarState.dispatchClick(date: CalendarCell.Day): CalendarState {
  if (date.inactive) return this

  val selection = when (params.selectionMode) {
    CalendarParams.SelectionMode.Disabled -> selection
    CalendarParams.SelectionMode.Single -> CalendarSelection.Single(date.date)
    CalendarParams.SelectionMode.Range -> {
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

internal fun CalendarState.dispatchParamsUpdate(params: CalendarParams): CalendarState =
  copy(
    params = params,
    cells = CalendarCells(
      params = params,
      selection = selection,
    ),
  )

internal fun CalendarState.dispatchSetSelection(selection: CalendarSelection): CalendarState {
  when (selection) {
    is CalendarSelection.None -> Unit
    is CalendarSelection.Range -> when {
      params.selectionMode != CalendarParams.SelectionMode.Range -> return this
      params.cellsInfo[selection.start]?.disabled == true -> return this
      params.cellsInfo[selection.end]?.disabled == true -> return this
      selection.start !in params.range -> return this
      selection.end != null && selection.end !in params.range -> return this
    }
    is CalendarSelection.Single -> when {
      params.selectionMode != CalendarParams.SelectionMode.Single -> return this
      params.cellsInfo[selection.date]?.disabled == true -> return this
      selection.date !in params.range -> return this
    }
  }
  return copy(
    selection = selection,
    cells = CalendarCells(params, selection),
  )
}
