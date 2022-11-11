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

import android.text.Spannable
import android.text.style.TtsSpan
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.core.text.buildSpannedString
import net.skyscanner.backpack.calendar2.CalendarParams
import net.skyscanner.backpack.calendar2.CalendarSelection
import net.skyscanner.backpack.calendar2.CellInfo
import net.skyscanner.backpack.util.InternalBackpackApi
import org.threeten.bp.LocalDate
import org.threeten.bp.YearMonth

@Stable
@InternalBackpackApi
sealed class CalendarCell {

  abstract val yearMonth: YearMonth

  @Immutable
  @InternalBackpackApi
  data class Space internal constructor(
    val selected: Boolean,
    val position: Int,
    override val yearMonth: YearMonth,
  ) : CalendarCell()

  @Immutable
  @InternalBackpackApi
  data class Header internal constructor(
    val title: String,
    val calendarSelectionMode: CalendarParams.SelectionMode,
    val monthSelectionMode: CalendarParams.MonthSelectionMode,
    override val yearMonth: YearMonth,
  ) : CalendarCell()

  @Immutable
  @InternalBackpackApi
  data class Day internal constructor(
    val date: LocalDate,
    val info: CellInfo,
    val selection: Selection?,
    val text: CharSequence,
    val outOfRange: Boolean,
    override val yearMonth: YearMonth,
  ) : CalendarCell() {

    val inactive: Boolean
      get() = info.disabled || outOfRange
  }

  enum class Selection {
    Single,
    Double,
    Start,
    Middle,
    End,
    StartMonth,
    EndMonth
  }
}

@Stable
@InternalBackpackApi
sealed interface CalendarInteraction {

  @Immutable
  @InternalBackpackApi
  data class DateClicked(val day: CalendarCell.Day) : CalendarInteraction

  @Immutable
  @InternalBackpackApi
  data class SelectMonthClicked(val header: CalendarCell.Header) : CalendarInteraction
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
  text = buildSpannedString {
    val span = TtsSpan.DateBuilder()
      .setDay(date.dayOfMonth)
      .setMonth(date.month.ordinal)
      .setWeekday(date.dayOfWeek.value)
      .build()
    append(date.dayOfMonth.toString(), span, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
  },
  selection = when (selection) {
    is CalendarSelection.None -> null
    is CalendarSelection.Single -> when (date) {
      selection.date -> CalendarCell.Selection.Single
      else -> null
    }
    is CalendarSelection.Dates -> when {
      selection.start == date && selection.end == date -> CalendarCell.Selection.Double
      selection.start == date && selection.end == null -> CalendarCell.Selection.Single
      selection.start == date && selection.end != null -> CalendarCell.Selection.Start
      selection.end == date -> CalendarCell.Selection.End
      selection.end != null && date in selection -> CalendarCell.Selection.Middle
      else -> null
    }
    is CalendarSelection.Month -> when {
      selection.start == date -> CalendarCell.Selection.StartMonth
      selection.end == date -> CalendarCell.Selection.EndMonth
      date in selection -> CalendarCell.Selection.Middle
      else -> null
    }
  },
)
