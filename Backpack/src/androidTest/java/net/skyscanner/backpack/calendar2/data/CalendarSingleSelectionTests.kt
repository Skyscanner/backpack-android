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
import org.junit.Assert.assertTrue
import org.junit.Test

class CalendarSingleSelectionTests {

  private val singleSelection = CalendarSettings.Default.copy(
    selectionMode = CalendarParams.SelectionMode.Single,
  )

  @Test
  fun date_can_be_selected() {
    testCalendarWith(singleSelection) {
      stateMachine.onClick(CalendarInteraction.DateClicked(firstDay))

      verify {
        assertEquals(CalendarSelection.Single(firstDay.date), state.selection)
      }
    }
  }

  @Test
  fun when_selection_is_in_place_cells_have_correct_state() {
    testCalendarWith(singleSelection) {
      stateMachine.onClick(CalendarInteraction.DateClicked(firstDay))

      verify {
        assertEquals(CalendarCell.Selection.Single, firstDay.selection)
      }
    }
  }

  @Test
  fun selected_date_can_be_changed() {
    testCalendarWith(singleSelection) {
      stateMachine.onClick(CalendarInteraction.DateClicked(firstDay))
      stateMachine.onClick(CalendarInteraction.DateClicked(lastDay))

      verify {
        assertEquals(CalendarSelection.Single(lastDay.date), state.selection)
      }
    }
  }

  @Test
  fun disabled_date_cannot_be_selected() {
    val disabledDates = mapOf(
      singleSelection.range.start to CellInfo(disabled = true),
    )

    testCalendarWith(singleSelection.copy(cellsInfo = disabledDates)) {
      stateMachine.onClick(CalendarInteraction.DateClicked(firstDay))

      verify {
        assertTrue(state.selection is CalendarSelection.None)
      }
    }
  }
}
