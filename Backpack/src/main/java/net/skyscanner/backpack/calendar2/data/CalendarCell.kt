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
import net.skyscanner.backpack.calendar2.CellInfo
import org.threeten.bp.LocalDate
import org.threeten.bp.YearMonth

internal sealed class CalendarCell {

  abstract val yearMonth: YearMonth

  internal data class Space(
    val selected: Boolean,
    val position: Int,
    override val yearMonth: YearMonth,
  ) : CalendarCell()

  internal data class Header(
    val title: String,
    override val yearMonth: YearMonth,
  ) : CalendarCell()

  internal data class Day(
    val date: LocalDate,
    val info: CellInfo,
    val selection: Selection?,
    val contentDescription: String,
    val outOfRange: Boolean,
    override val yearMonth: YearMonth,
  ) : CalendarCell()

  enum class Selection {
    Single,
    Double,
    Start,
    Middle,
    End,
  }
}

internal fun CalendarCellDay(
  date: LocalDate,
  yearMonth: YearMonth,
  selection: CalendarSelection,
  params: CalendarParams,
): CalendarCell.Day = CalendarCell.Day(
  date = date,
  yearMonth = yearMonth,
  info = params.cellsInfo[date] ?: CellInfo.Default,
  outOfRange = date !in params.range,
  contentDescription = "${date.dayOfMonth} ${date.month.getDisplayName(params.dateAccessibilityText, params.locale)}",
  selection = when (selection) {
    is CalendarSelection.None -> null
    is CalendarSelection.Single -> when (date) {
      selection.date -> CalendarCell.Selection.Single
      else -> null
    }
    is CalendarSelection.Range -> when {
      selection.start == date && selection.end == date -> CalendarCell.Selection.Double
      selection.start == date && selection.end == null -> CalendarCell.Selection.Single
      selection.start == date && selection.end != null -> CalendarCell.Selection.Start
      selection.end == date -> CalendarCell.Selection.End
      selection.end != null && date in selection -> CalendarCell.Selection.Middle
      else -> null
    }
  },
)
