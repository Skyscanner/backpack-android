/**
 * Backpack for Android - Skyscanner's Design System
 *
 * Copyright 2018-2021 Skyscanner Ltd
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.plus
import net.skyscanner.backpack.calendar2.CalendarComponent
import net.skyscanner.backpack.calendar2.CalendarParams
import net.skyscanner.backpack.calendar2.CalendarSelection
import net.skyscanner.backpack.calendar2.CalendarState
import net.skyscanner.backpack.calendar2.extension.findDate
import net.skyscanner.backpack.calendar2.monthsOf
import net.skyscanner.backpack.util.MutableStateMachine
import net.skyscanner.backpack.util.StateMachine

internal interface CalendarStateMachine : CalendarComponent, StateMachine<CalendarState, Nothing> {

  fun onClick(day: Int, month: Int, year: Int)
}

internal fun CalendarStateMachine(
  scope: CoroutineScope,
  initialParams: CalendarParams,
  dispatcher: CoroutineDispatcher = Dispatchers.Default,
): CalendarStateMachine {

  val fsm = MutableStateMachine<CalendarState, Nothing>(scope + dispatcher, CalendarState(initialParams))

  return object : CalendarStateMachine, StateMachine<CalendarState, Nothing> by fsm {

    override fun setParams(value: CalendarParams) {
      fsm.commit {
        it.dispatchParamsUpdate(value)
      }
    }

    override fun onClick(day: Int, month: Int, year: Int) {
      fsm.commit {
        it.dispatchClick(day, month, year)
      }
    }
  }
}

internal fun CalendarState.dispatchClick(day: Int, month: Int, year: Int): CalendarState {
  if (params.selectionMode == CalendarParams.SelectionMode.Disabled) return this

  val date = months.findDate(day, month, year) ?: return this
  if (date.info.status == CalendarParams.Status.Disabled) return this

  val selection = when (params.selectionMode) {
    CalendarParams.SelectionMode.Disabled -> selection
    CalendarParams.SelectionMode.Single -> CalendarSelection.Single(date.date)
    CalendarParams.SelectionMode.Range -> when (selection) {
      is CalendarSelection.None -> CalendarSelection.Single(date.date)
      is CalendarSelection.Single -> when {
        selection.date > date.date -> CalendarSelection.Single(date.date)
        else -> CalendarSelection.Range(start = selection.date, end = date.date)
      }
      is CalendarSelection.Range -> when (selection.end) {
        null -> CalendarSelection.Range(start = selection.start, end = date.date)
        else -> CalendarSelection.Single(date.date)
      }
    }
  }

  return copy(
    selection = selection,
    months = monthsOf(params = params, selection = selection),
  )
}

internal fun CalendarState.dispatchParamsUpdate(params: CalendarParams): CalendarState =
  copy(
    params = params,
    months = monthsOf(
      params = params,
      selection = selection,
    ),
  )
