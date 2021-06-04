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

import net.skyscanner.backpack.calendar2.CalendarParams
import net.skyscanner.backpack.calendar2.CalendarSelection
import net.skyscanner.backpack.calendar2.extension.toList
import net.skyscanner.backpack.calendar2.extension.yearMonth
import org.threeten.bp.LocalDate

internal data class CalendarCells(
  private val months: List<CalendarMonth> = emptyList(),
) {

  val size: Int = months.sumBy { it.cells.size }

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
    .toList()
    .groupBy { date -> date.yearMonth() }
    .toSortedMap()
    .map { entry ->
      CalendarMonth(
        days = entry.value.sortedBy { date -> date.dayOfMonth },
        yearMonth = entry.key,
        locale = params.locale,
        weekFields = params.weekFields,
        monthsTextStyle = params.monthsText,
        selection = selection,
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
