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

import kotlinx.coroutines.ExperimentalCoroutinesApi
import net.skyscanner.backpack.calendar2.CalendarParams
import net.skyscanner.backpack.calendar2.CalendarSelection
import net.skyscanner.backpack.calendar2.CalendarSettings
import net.skyscanner.backpack.calendar2.CellInfo
import net.skyscanner.backpack.calendar2.firstDay
import net.skyscanner.backpack.calendar2.lastDay
import net.skyscanner.backpack.calendar2.rangeOf
import net.skyscanner.backpack.calendar2.testCalendarWith
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CalendarRangeSelectionTests {

  private val rangeSelection = CalendarSettings.Default.copy(
    selectionMode = CalendarParams.SelectionMode.Range,
  )

  @Test
  fun `when range is opened selection is correct`() {
    testCalendarWith(rangeSelection) {
      stateMachine.onClick(firstDay)

      verify {
        assertEquals(CalendarSelection.Range(start = firstDay.date, end = null), state.selection)
      }
    }
  }

  @Test
  fun `when range is closed selection is correct`() {
    testCalendarWith(rangeSelection) {
      stateMachine.onClick(firstDay)
      stateMachine.onClick(lastDay)

      verify {
        assertEquals(CalendarSelection.Range(firstDay.date, lastDay.date), state.selection)
      }
    }
  }

  @Test
  fun `when range is withing the same date selection is correct`() {
    testCalendarWith(rangeSelection) {
      stateMachine.onClick(firstDay)
      stateMachine.onClick(firstDay)

      verify {
        assertEquals(CalendarSelection.Range(firstDay.date, firstDay.date), state.selection)
      }
    }
  }

  @Test
  fun `when range is closing before start a new range is created`() {
    testCalendarWith(rangeSelection) {
      stateMachine.onClick(lastDay)
      stateMachine.onClick(firstDay)
      stateMachine.onClick(lastDay)
      verify {
        assertEquals(CalendarSelection.Range(firstDay.date, lastDay.date), state.selection)
      }
    }
  }

  @Test
  fun `range can be reselected`() {
    testCalendarWith(rangeSelection) {
      stateMachine.onClick(firstDay)
      stateMachine.onClick(lastDay)
      stateMachine.onClick(firstDay)
      stateMachine.onClick(firstDay)
      verify {
        assertEquals(CalendarSelection.Range(firstDay.date, firstDay.date), state.selection)
      }
    }
  }

  @Test
  fun `when range is selected cells have correct state`() {
    val disabledDates = mapOf(
      rangeSelection.range.start.plusDays(1) to CellInfo(disabled = true),
    )
    testCalendarWith(rangeSelection.copy(cellsInfo = disabledDates)) {
      stateMachine.onClick(firstDay)
      stateMachine.onClick(lastDay)

      verify {
        for (i in rangeOf(firstDay, lastDay)) {
          when (val cell = state.cells[i]) {
            is CalendarCell.Day -> when (cell) {
              firstDay -> assertEquals(CalendarCell.Selection.Start, cell.selection)
              lastDay -> assertEquals(CalendarCell.Selection.End, cell.selection)
              else -> assertEquals(CalendarCell.Selection.Middle, cell.selection)
            }
            is CalendarCell.Space -> assertTrue(cell.selected)
          }
        }
      }
    }
  }

  @Test
  fun `when range is withing the same date cells have correct state`() {
    testCalendarWith(rangeSelection) {
      stateMachine.onClick(firstDay)
      stateMachine.onClick(firstDay)

      verify {
        assertEquals(CalendarCell.Selection.Double, firstDay.selection)
      }
    }
  }

  @Test
  fun `disabled date cannot be selected`() {
    val disabledDates = mapOf(
      rangeSelection.range.start to CellInfo(disabled = true),
    )

    testCalendarWith(rangeSelection.copy(cellsInfo = disabledDates)) {
      stateMachine.onClick(firstDay)

      verify {
        assertTrue(state.selection is CalendarSelection.None)
      }
    }
  }
}
