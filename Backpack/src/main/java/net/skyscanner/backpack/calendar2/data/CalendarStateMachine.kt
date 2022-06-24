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

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.plus
import net.skyscanner.backpack.calendar2.CalendarComponent
import net.skyscanner.backpack.calendar2.CalendarEffect
import net.skyscanner.backpack.calendar2.CalendarParams
import net.skyscanner.backpack.calendar2.CalendarParams.SelectionMode
import net.skyscanner.backpack.calendar2.CalendarSelection
import net.skyscanner.backpack.calendar2.CalendarState
import net.skyscanner.backpack.util.MutableStateMachine
import net.skyscanner.backpack.util.StateMachine
import java.util.Locale

internal interface CalendarStateMachine : CalendarComponent, StateMachine<CalendarState, CalendarEffect> {

  fun onClick(calendarInteraction: CalendarInteraction)
  fun onLocaleChanged(locale: Locale)
}

internal fun CalendarStateMachine(
  scope: CoroutineScope,
  initialParams: CalendarParams,
  dispatcher: CoroutineDispatcher,
): CalendarStateMachine {

  val fsm = MutableStateMachine<CalendarState, CalendarEffect>(scope + dispatcher, CalendarState(initialParams))

  return object : CalendarStateMachine, StateMachine<CalendarState, CalendarEffect> by fsm {

    override fun setParams(value: CalendarParams) {
      fsm.commit {
        it.dispatchParamsUpdate(value)
      }
    }

    override fun onClick(calendarInteraction: CalendarInteraction) = when (calendarInteraction) {
      is CalendarInteraction.DateClicked -> onCalendarDayCellClick(calendarInteraction.day)
      is CalendarInteraction.SelectMonthClicked -> onCalendarHeaderCellClick(calendarInteraction.header)
    }

    private fun onCalendarHeaderCellClick(header: CalendarCell.Header) {
      fsm.commit {
        emmit(CalendarEffect.MonthSelected(header.yearMonth))
        it.dispatchClick(header)
      }
    }

    private fun onCalendarDayCellClick(day: CalendarCell.Day) = fsm.commit { it.dispatchClick(day) }

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
    SelectionMode.Disabled -> selection
    SelectionMode.Single -> CalendarSelection.Single(date.date)
    SelectionMode.Range -> {
      val rangeStart = (selection as? CalendarSelection.Range)?.start
      val rangeEnd = (selection as? CalendarSelection.Range)?.end
      when {
        rangeStart != null && rangeEnd != null -> CalendarSelection.Dates(start = date.date, end = null)
        rangeStart == null -> CalendarSelection.Dates(start = date.date, end = null)
        date.date < rangeStart -> CalendarSelection.Dates(start = date.date, end = null)
        else -> CalendarSelection.Dates(start = rangeStart, end = date.date)
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
    is CalendarSelection.Dates -> when {
      params.selectionMode != SelectionMode.Range -> return this
      params.cellsInfo[selection.start]?.disabled == true -> return this
      params.cellsInfo[selection.end]?.disabled == true -> return this
      selection.start !in params.range -> return this
      selection.end?.let { it !in params.range } == true -> return this
    }
    is CalendarSelection.Single -> when {
      params.selectionMode != SelectionMode.Single -> return this
      params.cellsInfo[selection.date]?.disabled == true -> return this
      selection.date !in params.range -> return this
    }
    is CalendarSelection.Month -> {}
  }
  return copy(
    selection = selection,
    cells = CalendarCells(params, selection),
  )
}

internal fun CalendarState.dispatchClick(data: CalendarCell.Header): CalendarState {
  if (data.selectWholeMonthLabel.isEmpty()) return this
  val selection = when (params.selectionMode) {
    SelectionMode.Disabled -> selection
    else -> CalendarSelection.Month(month = data.yearMonth)
  }

  return copy(
    selection = selection,
    cells = CalendarCells(params = params, selection = selection),
  )
}
