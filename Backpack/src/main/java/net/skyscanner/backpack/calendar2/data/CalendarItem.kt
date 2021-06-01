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

import java.util.Locale
import net.skyscanner.backpack.calendar2.CalendarParams
import net.skyscanner.backpack.calendar2.CalendarSelection
import org.threeten.bp.LocalDate
import org.threeten.bp.YearMonth
import org.threeten.bp.format.TextStyle

internal sealed class CalendarItem {

  abstract val yearMonth: YearMonth
}

internal data class CalendarSpace(
  val selected: Boolean,
  override val yearMonth: YearMonth,
) : CalendarItem()

internal data class CalendarHeader(
  val title: String,
  override val yearMonth: YearMonth,
) : CalendarItem()

internal data class CalendarFooter(
  override val yearMonth: YearMonth,
) : CalendarItem()

internal data class CalendarDay(
  val date: LocalDate,
  val info: CalendarParams.Info,
  val selection: Selection?,
  val contentDescription: String,
  override val yearMonth: YearMonth,
) : CalendarItem() {

  enum class Selection {
    Single,
    Double,
    Start,
    Middle,
    End,
  }
}

internal fun CalendarDay(
  date: LocalDate,
  yearMonth: YearMonth,
  selection: CalendarSelection,
  cells: Map<LocalDate, CalendarParams.Info>,
  locale: Locale,
  contentDescription: TextStyle,
): CalendarDay = CalendarDay(
  date = date,
  yearMonth = yearMonth,
  info = cells[date] ?: CalendarParams.Info.Default,
  contentDescription = "${date.dayOfMonth} ${date.month.getDisplayName(contentDescription, locale)}",
  selection = when (selection) {
    is CalendarSelection.None -> null
    is CalendarSelection.Single -> when (date) {
      selection.date -> CalendarDay.Selection.Single
      else -> null
    }
    is CalendarSelection.Range -> when {
      selection.start == date && selection.end == date -> CalendarDay.Selection.Double
      selection.start == date -> CalendarDay.Selection.Start
      selection.end == date -> CalendarDay.Selection.End
      date in selection -> CalendarDay.Selection.Middle
      else -> null
    }
  },
)
