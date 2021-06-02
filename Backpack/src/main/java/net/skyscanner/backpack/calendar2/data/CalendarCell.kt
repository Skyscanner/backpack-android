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
import net.skyscanner.backpack.calendar2.extension.yearMonthHash
import org.threeten.bp.LocalDate
import org.threeten.bp.YearMonth
import org.threeten.bp.format.TextStyle
import org.threeten.bp.temporal.ChronoField

internal sealed class CalendarCell {

  abstract val yearMonth: YearMonth

  abstract val id: Long
}

internal data class CalendarCellSpace(
  val selected: Boolean,
  override val yearMonth: YearMonth,
) : CalendarCell() {

  override val id: Long =
    -(yearMonth.yearMonthHash() * 10L + 1L)
}

internal data class CalendarCellHeader(
  val title: String,
  override val yearMonth: YearMonth,
) : CalendarCell() {

  override val id: Long =
    -(yearMonth.yearMonthHash() * 10L + 2L)
}

internal data class CalendarCellFooter(
  override val yearMonth: YearMonth,
) : CalendarCell() {

  override val id: Long =
    -(yearMonth.yearMonthHash() * 10L + 3L)
}

internal data class CalendarCellDay(
  val date: LocalDate,
  val info: CalendarParams.Info,
  val selection: Selection?,
  val contentDescription: String,
  override val yearMonth: YearMonth,
) : CalendarCell() {

  override val id: Long =
    date.getLong(ChronoField.EPOCH_DAY)

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
  cells: Map<LocalDate, CalendarParams.Info>,
  locale: Locale,
  contentDescription: TextStyle,
): CalendarCellDay = CalendarCellDay(
  date = date,
  yearMonth = yearMonth,
  info = cells[date] ?: CalendarParams.Info.Default,
  contentDescription = "${date.dayOfMonth} ${date.month.getDisplayName(contentDescription, locale)}",
  selection = when (selection) {
    is CalendarSelection.None -> null
    is CalendarSelection.Single -> when (date) {
      selection.date -> CalendarCellDay.Selection.Single
      else -> null
    }
    is CalendarSelection.Range -> when {
      selection.start == date && selection.end == date -> CalendarCellDay.Selection.Double
      selection.start == date -> CalendarCellDay.Selection.Start
      selection.end == date -> CalendarCellDay.Selection.End
      date in selection -> CalendarCellDay.Selection.Middle
      else -> null
    }
  },
)
