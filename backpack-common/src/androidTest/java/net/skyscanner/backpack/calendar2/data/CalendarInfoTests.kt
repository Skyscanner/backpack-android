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

import net.skyscanner.backpack.calendar2.CalendarSettings
import net.skyscanner.backpack.calendar2.CellInfo
import net.skyscanner.backpack.calendar2.CellStatus
import net.skyscanner.backpack.calendar2.CellStatusStyle
import net.skyscanner.backpack.calendar2.extension.toIterable
import net.skyscanner.backpack.calendar2.testCalendarWith
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class CalendarInfoTests {

  @Test
  fun if_date_is_disabled_cell_has_correct_state() {
    val params = CalendarSettings.Default.copy(
      cellsInfo = CalendarSettings.Default.range.toIterable().associateWith {
        CellInfo(disabled = true)
      }
    )
    testCalendarWith(params) {
      verify {
        for (i in 0 until state.cells.size) {
          val cell = state.cells[i]
          if (cell is CalendarCell.Day) {
            assertTrue(cell.info.disabled)
          }
        }
      }
    }
  }

  @Test
  fun if_date_with_label_cell_has_correct_state() {
    val params = CalendarSettings.Default.copy(
      cellsInfo = CalendarSettings.Default.range.toIterable().associateWith {
        CellInfo(label = it.dayOfMonth.toString())
      }
    )

    testCalendarWith(params) {
      verify {
        for (i in 0 until state.cells.size) {
          val cell = state.cells[i]
          if (cell is CalendarCell.Day) {
            assertEquals(cell.date.dayOfMonth.toString(), cell.info.label)
          }
        }
      }
    }
  }

  @Test
  fun if_date_with_status_as_label_cell_has_correct_state() {
    val statuses = CellStatus.values()
    val params = CalendarSettings.Default.copy(
      cellsInfo = CalendarSettings.Default.range.toIterable().associateWith {
        CellInfo(status = statuses[it.dayOfMonth % statuses.size], style = CellStatusStyle.Label)
      }
    )

    testCalendarWith(params) {
      verify {
        for (i in 0 until state.cells.size) {
          val cell = state.cells[i]
          if (cell is CalendarCell.Day) {
            assertEquals(statuses[cell.date.dayOfMonth % statuses.size], cell.info.status)
            assertEquals(CellStatusStyle.Label, cell.info.style)
          }
        }
      }
    }
  }

  @Test
  fun if_date_with_status_as_background_cell_has_correct_state() {
    val statuses = CellStatus.values()
    val params = CalendarSettings.Default.copy(
      cellsInfo = CalendarSettings.Default.range.toIterable().associateWith {
        CellInfo(status = statuses[it.dayOfMonth % statuses.size], style = CellStatusStyle.Background)
      }
    )

    testCalendarWith(params) {
      verify {
        for (i in 0 until state.cells.size) {
          val cell = state.cells[i]
          if (cell is CalendarCell.Day) {
            assertEquals(statuses[cell.date.dayOfMonth % statuses.size], cell.info.status)
            assertEquals(CellStatusStyle.Background, cell.info.style)
          }
        }
      }
    }
  }
}
