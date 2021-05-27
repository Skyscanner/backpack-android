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

import java.time.temporal.WeekFields
import net.skyscanner.backpack.calendar2.CalendarParams
import net.skyscanner.backpack.calendar2.CalendarSelection
import net.skyscanner.backpack.calendar2.CalendarState
import net.skyscanner.backpack.calendar2.extension.findDate
import net.skyscanner.backpack.calendar2.extension.toList
import net.skyscanner.backpack.calendar2.extension.yearMonthHash

internal fun CalendarState.dispatchClick(day: Int, month: Int, year: Int): CalendarState {
  if (params.selectionMode == CalendarParams.SelectionMode.Disabled) return this

  val date = months.findDate(day, month, year) ?: return this
  if (date.info.status == CalendarParams.Status.Disabled) return this

  val selection = when (params.selectionMode) {
    CalendarParams.SelectionMode.Disabled -> selection
    CalendarParams.SelectionMode.Single -> CalendarSelection.Single(date.date)
    CalendarParams.SelectionMode.Range -> when (selection) {
      is CalendarSelection.None -> CalendarSelection.Single(date.date)
      is CalendarSelection.Single -> when {
        selection.date > date.date -> CalendarSelection.Single(date.date)
        else -> CalendarSelection.Range(start = selection.date, end = date.date)
      }
      is CalendarSelection.Range -> when (selection.end) {
        null -> CalendarSelection.Range(start = selection.start, end = date.date)
        else -> CalendarSelection.Single(date.date)
      }
    }
  }

  return copy(
    selection = selection,
    months = monthsOf(selection = selection),
  )
}

internal fun CalendarState.dispatchParamsUpdate(params: CalendarParams): CalendarState {
  val weekFields = WeekFields.of(params.locale)
  return copy(
    params = params,
    months = monthsOf(
      params = params,
      weekFields = weekFields,
    ),
    weekFields = weekFields,
  )
}

private fun CalendarState.monthsOf(
  params: CalendarParams = this.params,
  weekFields: WeekFields = this.weekFields,
  selection: CalendarSelection = this.selection,
): List<CalendarMonth> =
  params
    .range
    .toList()
    .groupBy { date -> date.yearMonthHash() }
    .toSortedMap()
    .map { entry -> entry.value.sortedBy { date -> date.dayOfMonth } }
    .map { dates ->
      CalendarMonth(
        days = dates,
        locale = params.locale,
        weekFields = weekFields,
        monthsTextStyle = params.monthsText,
        selection = selection,
        footers = params.footers,
      ) { date ->
        CalendarDay(
          date = date,
          selection = selection,
          cells = params.cells,
          locale = params.locale,
          contentDescription = params.dateAccessibilityText,
        )
      }
    }
