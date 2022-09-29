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
import net.skyscanner.backpack.calendar2.CalendarSelection
import net.skyscanner.backpack.calendar2.CalendarSettings
import net.skyscanner.backpack.calendar2.CellInfo
import net.skyscanner.backpack.calendar2.firstDay
import net.skyscanner.backpack.calendar2.lastDay
import net.skyscanner.backpack.calendar2.testCalendarWith
import org.junit.Assert.assertEquals
import org.junit.Test

class CalendarSettersTests {

  @Test
  fun setParams_changes_params() {
    testCalendarWith(CalendarSettings.Default) {
      stateMachine.setParams(CalendarSettings.Default.copy(selectionMode = CalendarParams.SelectionMode.Disabled))
      verify {
        assertEquals(CalendarParams.SelectionMode.Disabled, state.params.selectionMode)
      }
    }
  }

  @Test
  fun setSelection_of_single_changes_selection() {
    val disabledParams = CalendarSettings.Default.copy(
      selectionMode = CalendarParams.SelectionMode.Single,
    )
    testCalendarWith(disabledParams) {
      stateMachine.setSelection(CalendarSelection.Single(firstDay.date))

      verify {
        assertEquals(CalendarSelection.Single(firstDay.date), state.selection)
      }
    }
  }

  @Test
  fun setSelection_of_range_changes_selection() {
    val disabledParams = CalendarSettings.Default.copy(
      selectionMode = CalendarParams.SelectionMode.Range,
    )
    testCalendarWith(disabledParams) {
      stateMachine.setSelection(CalendarSelection.Dates(firstDay.date, lastDay.date))

      verify {
        assertEquals(CalendarSelection.Dates(firstDay.date, lastDay.date), state.selection)
      }
    }
  }

  @Test
  fun setSelection_of_single_ignores_change_when_selectionMode_is_disabled() {
    val disabledParams = CalendarSettings.Default.copy(
      selectionMode = CalendarParams.SelectionMode.Disabled,
    )
    testCalendarWith(disabledParams) {
      stateMachine.setSelection(CalendarSelection.Single(firstDay.date))

      verify {
        assertEquals(CalendarSelection.None, state.selection)
        assertEquals(CalendarParams.SelectionMode.Disabled, state.params.selectionMode)
      }
    }
  }

  @Test
  fun setSelection_of_range_ignores_change_when_selectionMode_is_disabled() {
    val disabledParams = CalendarSettings.Default.copy(
      selectionMode = CalendarParams.SelectionMode.Disabled,
    )
    testCalendarWith(disabledParams) {
      stateMachine.setSelection(CalendarSelection.Dates(firstDay.date, lastDay.date))

      verify {
        assertEquals(CalendarSelection.None, state.selection)
        assertEquals(CalendarParams.SelectionMode.Disabled, state.params.selectionMode)
      }
    }
  }

  @Test
  fun setSelection_of_single_ignores_change_when_date_is_disabled() {
    val disabledParams = CalendarSettings.Default.copy(
      selectionMode = CalendarParams.SelectionMode.Single,
      cellsInfo = mapOf(
        CalendarSettings.Default.range.start to CellInfo(disabled = true),
      )
    )
    testCalendarWith(disabledParams) {
      stateMachine.setSelection(CalendarSelection.Single(firstDay.date))

      verify {
        assertEquals(CalendarSelection.None, state.selection)
      }
    }
  }

  @Test
  fun setSelection_of_range_ignores_change_when_start_date_is_disabled() {
    val disabledParams = CalendarSettings.Default.copy(
      selectionMode = CalendarParams.SelectionMode.Range,
      cellsInfo = mapOf(
        CalendarSettings.Default.range.start to CellInfo(disabled = true),
      )
    )
    testCalendarWith(disabledParams) {
      stateMachine.setSelection(CalendarSelection.Dates(start = firstDay.date, end = null))

      verify {
        assertEquals(CalendarSelection.None, state.selection)
      }
    }
  }

  @Test
  fun setSelection_of_range_ignores_change_when_end_date_is_disabled() {
    val disabledParams = CalendarSettings.Default.copy(
      selectionMode = CalendarParams.SelectionMode.Range,
      cellsInfo = mapOf(
        CalendarSettings.Default.range.endInclusive to CellInfo(disabled = true),
      )
    )
    testCalendarWith(disabledParams) {
      stateMachine.setSelection(CalendarSelection.Dates(start = firstDay.date, end = lastDay.date))

      verify {
        assertEquals(CalendarSelection.None, state.selection)
      }
    }
  }

  @Test
  fun setSelection_of_single_ignores_change_when_date_is_out_of_range() {
    val disabledParams = CalendarSettings.Default.copy(
      selectionMode = CalendarParams.SelectionMode.Single,
    )
    testCalendarWith(disabledParams) {
      stateMachine.setSelection(CalendarSelection.Single(firstDay.date.minusMonths(1)))

      verify {
        assertEquals(CalendarSelection.None, state.selection)
      }
    }
  }

  @Test
  fun setSelection_of_range_ignores_change_when_start_date_is_out_of_range() {
    val disabledParams = CalendarSettings.Default.copy(
      selectionMode = CalendarParams.SelectionMode.Range,
    )
    testCalendarWith(disabledParams) {
      stateMachine.setSelection(CalendarSelection.Dates(start = firstDay.date.minusMonths(1), end = null))

      verify {
        assertEquals(CalendarSelection.None, state.selection)
      }
    }
  }

  @Test
  fun setSelection_of_range_ignores_change_when_end_date_is_out_of_range() {
    val disabledParams = CalendarSettings.Default.copy(
      selectionMode = CalendarParams.SelectionMode.Range,
    )
    testCalendarWith(disabledParams) {
      stateMachine.setSelection(CalendarSelection.Dates(start = firstDay.date, end = lastDay.date.plusMonths(1)))

      verify {
        assertEquals(CalendarSelection.None, state.selection)
      }
    }
  }

  @Test
  fun setSelection_of_month_changes_selection() {
    testCalendarWith(CalendarSettings.Default) {
      stateMachine.setParams(
        CalendarSettings.Default.copy(
          selectionMode = CalendarParams.SelectionMode.Range
        )
      )
      verify {
        assertEquals(CalendarParams.SelectionMode.Range, state.params.selectionMode)
      }
    }
  }
}
