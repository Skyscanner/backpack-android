package net.skyscanner.backpack.calendar2.data

import kotlinx.coroutines.ExperimentalCoroutinesApi
import net.skyscanner.backpack.calendar2.CalendarSettings
import net.skyscanner.backpack.calendar2.CellInfo
import net.skyscanner.backpack.calendar2.CellStatus
import net.skyscanner.backpack.calendar2.CellStatusStyle
import net.skyscanner.backpack.calendar2.extension.toList
import net.skyscanner.backpack.calendar2.testCalendarWith
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CalendarInfoTests {

  @Test
  fun `if date is disabled cell has correct state`() {
    val params = CalendarSettings.Default.copy(
      cellsInfo = CalendarSettings.Default.range.toList().associateWith {
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
  fun `if date with label cell has correct state`() {
    val params = CalendarSettings.Default.copy(
      cellsInfo = CalendarSettings.Default.range.toList().associateWith {
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
  fun `if date with status as label cell has correct state`() {
    val statuses = CellStatus.values()
    val params = CalendarSettings.Default.copy(
      cellsInfo = CalendarSettings.Default.range.toList().associateWith {
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
  fun `if date with status as background cell has correct state`() {
    val statuses = CellStatus.values()
    val params = CalendarSettings.Default.copy(
      cellsInfo = CalendarSettings.Default.range.toList().associateWith {
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
