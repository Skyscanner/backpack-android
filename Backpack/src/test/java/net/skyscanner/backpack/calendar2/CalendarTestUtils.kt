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

package net.skyscanner.backpack.calendar2

import net.skyscanner.backpack.calendar2.data.CalendarCell
import net.skyscanner.backpack.calendar2.data.CalendarStateMachine
import net.skyscanner.backpack.util.TestStateMachineResult
import net.skyscanner.backpack.util.TestStateMachineScope
import net.skyscanner.backpack.util.testStateMachine
import org.threeten.bp.LocalDate
import org.threeten.bp.Month
import java.util.Locale

internal typealias CalendarTestScope = TestStateMachineScope<CalendarStateMachine, CalendarState>

internal val CalendarTestScope.firstDay: CalendarCell.Day
  get() {
    val index = state.cells.indexOf(CalendarSettings.Default.range.start)
    return state.cells[index] as CalendarCell.Day
  }

internal val CalendarTestScope.lastDay: CalendarCell.Day
  get() {
    val index = state.cells.indexOf(CalendarSettings.Default.range.endInclusive)
    return state.cells[index] as CalendarCell.Day
  }

internal fun CalendarTestScope.rangeOf(start: CalendarCell.Day, end: CalendarCell.Day): IntRange {
  val indexOfFirst = state.cells.indexOf(start.date)
  val indexOfLast = state.cells.indexOf(end.date)
  return indexOfFirst..indexOfLast
}

internal fun testCalendarWith(
  params: CalendarParams,
  block: suspend CalendarTestScope.() -> TestStateMachineResult,
) =
  testStateMachine(creator = { CalendarStateMachine(this, params, it) }, block = block)

object CalendarSettings {

  val Default = CalendarParams(
    range = LocalDate.of(2000, Month.JANUARY, 1)..LocalDate.of(2000, Month.DECEMBER, 31),
    selectionMode = CalendarParams.SelectionMode.Single,
    locale = Locale.ENGLISH,
  )
}
