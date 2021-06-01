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

package net.skyscanner.backpack.calendar2

import net.skyscanner.backpack.calendar2.data.CalendarDay
import net.skyscanner.backpack.calendar2.data.CalendarMonth
import net.skyscanner.backpack.calendar2.extension.toList
import net.skyscanner.backpack.calendar2.extension.yearMonthHash

data class CalendarState internal constructor(
  val params: CalendarParams,
  val selection: CalendarSelection = CalendarSelection.None,
  internal val months: List<CalendarMonth> = monthsOf(params, selection),
)

internal fun monthsOf(
  params: CalendarParams,
  selection: CalendarSelection,
): List<CalendarMonth> =
  params
    .range
    .toList()
    .groupBy { date -> date.yearMonthHash() }
    .toSortedMap()
    .map { entry ->
      CalendarMonth(
        days = entry.value.sortedBy { date -> date.dayOfMonth },
        locale = params.locale,
        weekFields = params.weekFields,
        monthsTextStyle = params.monthsText,
        selection = selection,
        footers = params.footers,
      ) { yearMonth, date ->
        CalendarDay(
          date = date,
          yearMonth = yearMonth,
          selection = selection,
          cells = params.cells,
          locale = params.locale,
          contentDescription = params.dateAccessibilityText,
        )
      }
    }
