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

import androidx.compose.runtime.Immutable
import net.skyscanner.backpack.calendar2.CalendarParams
import net.skyscanner.backpack.calendar2.CalendarSelection
import net.skyscanner.backpack.calendar2.extension.firstDay
import net.skyscanner.backpack.calendar2.extension.lastDay
import net.skyscanner.backpack.calendar2.extension.toIterable
import net.skyscanner.backpack.calendar2.extension.yearMonth
import net.skyscanner.backpack.util.InternalBackpackApi
import org.threeten.bp.LocalDate

@Immutable
@InternalBackpackApi
data class CalendarCells(
  private val months: List<CalendarMonth>,
) {

  val size: Int = months.sumOf { it.cells.size }

  operator fun get(position: Int): CalendarCell {
    var month: CalendarMonth? = null
    var localIndex = position
    for (it in months) {
      if (localIndex in it.cells.indices) {
        month = it
        break
      }
      localIndex -= it.cells.size
    }

    return month?.cells?.getOrNull(localIndex) ?: error("Unable to find a month for index $position")
  }

  fun indexOf(date: LocalDate): Int {
    var accumulated = 0
    months.forEach { month ->
      val indexInMonth = month.cells.indexOfFirst { (it as? CalendarCell.Day)?.date == date }
      if (indexInMonth < 0) {
        accumulated += month.cells.size
      } else {
        return indexInMonth + accumulated
      }
    }
    return -1
  }
}

internal fun CalendarCells(
  params: CalendarParams,
  selection: CalendarSelection,
): CalendarCells {
  val months = params
    .range
    .toIterable()
    .let { daysBeforeRangeStarts(params) + it + daysAfterRangeEnds(params) }
    .groupBy { date -> date.yearMonth() }
    .toSortedMap()
    .map { entry ->
      CalendarMonth(
        days = entry.value.sortedBy { date -> date.dayOfMonth },
        yearMonth = entry.key,
        locale = params.locale,
        monthsFormatter = params.monthsFormatter,
        weekFields = params.weekFields,
        selection = selection,
        monthSelectionMode = params.monthSelectionMode,
        calendarSelectionMode = params.selectionMode
      ) { yearMonth, date ->
        CalendarCellDay(
          date = date,
          yearMonth = yearMonth,
          selection = selection,
          params = params,
        )
      }
    }

  return CalendarCells(months = months)
}

private fun daysBeforeRangeStarts(params: CalendarParams): List<LocalDate> {
  val result = mutableListOf<LocalDate>()
  val firstDay = params.range.start.yearMonth().firstDay()
  var current = params.range.start
  while (current != firstDay) {
    current = current.plusDays(-1)
    result += current
  }
  return result
}

private fun daysAfterRangeEnds(params: CalendarParams): List<LocalDate> {
  val result = mutableListOf<LocalDate>()
  val lastDay = params.range.endInclusive.yearMonth().lastDay()
  var current = params.range.endInclusive
  while (current != lastDay) {
    current = current.plusDays(1)
    result += current
  }
  return result
}
