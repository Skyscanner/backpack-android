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
import net.skyscanner.backpack.calendar2.header
import net.skyscanner.backpack.calendar2.lastDay
import net.skyscanner.backpack.calendar2.rangeOf
import net.skyscanner.backpack.calendar2.testCalendarWith
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class CalendarRangeSelectionTests {

  private val rangeSelection = CalendarSettings.Default.copy(
    selectionMode = CalendarParams.SelectionMode.Range,
  )

  private val monthSelection = CalendarSettings.Default.copy(
    monthSelectionMode = CalendarParams.MonthSelectionMode.SelectWholeMonth("Select whole month"),
    selectionMode = CalendarParams.SelectionMode.Range
  )

  @Test
  fun when_range_is_opened_selection_is_correct() {
    testCalendarWith(rangeSelection) {
      stateMachine.onClick(CalendarInteraction.DateClicked(firstDay))

      verify {
        assertEquals(CalendarSelection.Dates(start = firstDay.date, end = null), state.selection)
      }
    }
  }

  @Test
  fun when_range_is_closed_selection_is_correct() {
    testCalendarWith(rangeSelection) {
      stateMachine.onClick(CalendarInteraction.DateClicked(firstDay))
      stateMachine.onClick(CalendarInteraction.DateClicked(lastDay))

      verify {
        assertEquals(CalendarSelection.Dates(firstDay.date, lastDay.date), state.selection)
      }
    }
  }

  @Test
  fun when_range_is_within_the_same_date_selection_is_correct() {
    testCalendarWith(rangeSelection) {
      stateMachine.onClick(CalendarInteraction.DateClicked(firstDay))
      stateMachine.onClick(CalendarInteraction.DateClicked(firstDay))

      verify {
        assertEquals(CalendarSelection.Dates(firstDay.date, firstDay.date), state.selection)
      }
    }
  }

  @Test
  fun when_range_is_closing_before_start_a_new_range_is_created() {
    testCalendarWith(rangeSelection) {
      stateMachine.onClick(CalendarInteraction.DateClicked(lastDay))
      stateMachine.onClick(CalendarInteraction.DateClicked(firstDay))
      stateMachine.onClick(CalendarInteraction.DateClicked(lastDay))
      verify {
        assertEquals(CalendarSelection.Dates(firstDay.date, lastDay.date), state.selection)
      }
    }
  }

  @Test
  fun range_can_be_reselected() {
    testCalendarWith(rangeSelection) {
      stateMachine.onClick(CalendarInteraction.DateClicked(firstDay))
      stateMachine.onClick(CalendarInteraction.DateClicked(lastDay))
      stateMachine.onClick(CalendarInteraction.DateClicked(firstDay))
      stateMachine.onClick(CalendarInteraction.DateClicked(firstDay))
      verify {
        assertEquals(CalendarSelection.Dates(firstDay.date, firstDay.date), state.selection)
      }
    }
  }

  @Test
  fun when_range_is_selected_cells_have_correct_state() {
    val disabledDates = mapOf(
      rangeSelection.range.start.plusDays(1) to CellInfo(disabled = true),
    )
    testCalendarWith(rangeSelection.copy(cellsInfo = disabledDates)) {
      stateMachine.onClick(CalendarInteraction.DateClicked(firstDay))
      stateMachine.onClick(CalendarInteraction.DateClicked(lastDay))

      verify {
        for (i in rangeOf(firstDay, lastDay)) {
          when (val cell = state.cells[i]) {
            is CalendarCell.Day -> when (cell) {
              firstDay -> assertEquals(CalendarCell.Selection.Start, cell.selection)
              lastDay -> assertEquals(CalendarCell.Selection.End, cell.selection)
              else -> assertEquals(CalendarCell.Selection.Middle, cell.selection)
            }
            is CalendarCell.Space -> assertTrue(cell.selected)
            is CalendarCell.Header -> {}
          }
        }
      }
    }
  }

  @Test
  fun when_range_is_within_the_same_date_cells_have_correct_state() {
    testCalendarWith(rangeSelection) {
      stateMachine.onClick(CalendarInteraction.DateClicked(firstDay))
      stateMachine.onClick(CalendarInteraction.DateClicked(firstDay))

      verify {
        assertEquals(CalendarCell.Selection.Double, firstDay.selection)
      }
    }
  }

  @Test
  fun disabled_date_cannot_be_selected() {
    val disabledDates = mapOf(
      rangeSelection.range.start to CellInfo(disabled = true),
    )

    testCalendarWith(rangeSelection.copy(cellsInfo = disabledDates)) {
      stateMachine.onClick(CalendarInteraction.DateClicked(firstDay))

      verify {
        assertTrue(state.selection is CalendarSelection.None)
      }
    }
  }

  @Test
  fun when_select_whole_month_is_within_the_same_date_selection_is_correct() {
    testCalendarWith(monthSelection) {
      stateMachine.onClick(CalendarInteraction.SelectMonthClicked(header))
      verify {
        assertEquals(CalendarSelection.Month(header.yearMonth), state.selection)
      }
    }
  }

  @Test
  fun given_whole_month_is_selected_and_a_range_selection_is_made_on_same_date_then_correct_state_should_return() {
    testCalendarWith(monthSelection) {
      stateMachine.onClick(CalendarInteraction.DateClicked(firstDay))
      stateMachine.onClick(CalendarInteraction.DateClicked(firstDay))

      verify {
        assertEquals(CalendarCell.Selection.Double, firstDay.selection)
      }
    }
  }

  @Test
  fun given_whole_month_is_selected_and_a_range_selection_is_made_selection_then_correct_state_should_return() {
    testCalendarWith(monthSelection) {
      stateMachine.onClick(CalendarInteraction.SelectMonthClicked(header))
      stateMachine.onClick(CalendarInteraction.DateClicked(firstDay))
      stateMachine.onClick(CalendarInteraction.DateClicked(lastDay))

      verify {
        assertEquals(CalendarSelection.Dates(firstDay.date, lastDay.date), state.selection)
      }
    }
  }
}
