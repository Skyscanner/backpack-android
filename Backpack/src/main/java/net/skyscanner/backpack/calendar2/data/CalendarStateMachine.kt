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

import kotlinx.coroutines.CoroutineScope
import net.skyscanner.backpack.calendar2.CalendarComponent
import net.skyscanner.backpack.calendar2.CalendarParams
import net.skyscanner.backpack.calendar2.CalendarState
import net.skyscanner.backpack.util.MutableStateMachine
import net.skyscanner.backpack.util.StateMachine

internal interface CalendarStateMachine : CalendarComponent, StateMachine<CalendarState, Nothing> {

  fun onClick(day: Int, month: Int, year: Int)
}

internal fun CalendarStateMachine(
  scope: CoroutineScope,
  params: CalendarParams,
): CalendarStateMachine {

  val fsm = MutableStateMachine<CalendarState, Nothing>(scope, CalendarState(params).dispatchParamsUpdate(params))

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
