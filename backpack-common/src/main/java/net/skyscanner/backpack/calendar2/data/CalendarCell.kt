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
        val contentDescription: String,
        val stateDescription: String?,
        val onClickLabel: String?,
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
        EndMonth,
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
): CalendarCell.Day {
    return CalendarCell.Day(
        date = date,
        yearMonth = yearMonth,
        info = params.cellsInfo[date] ?: CellInfo.Default,
        outOfRange = date !in params.range,
        contentDescription = date.format(params.dateContentDescriptionFormatter),
        stateDescription = stateDescription(date, selection),
        onClickLabel = onClickLabel(date, params.selectionMode, selection),
        text = buildSpannedString {
            val span = TtsSpan.DateBuilder()
                .setDay(date.dayOfMonth)
                .setMonth(date.month.ordinal)
                .setYear(date.year)
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
}

private fun stateDescription(date: LocalDate, selection: CalendarSelection): String {
    return when (selection) {
        is CalendarSelection.None -> "No selection"
        is CalendarSelection.Single -> when (date) {
            selection.date -> "Current selection"
            else -> "Not selected"
        }

        is CalendarSelection.Dates -> when {
            selection.start == date && selection.end == date -> "Selected as departure and return date"
            selection.start == date && selection.end == null -> "Selected as departure date"
            selection.start == date && selection.end != null -> "Selected as departure date"
            selection.end == date -> "Selected as return date"
            selection.end != null && date in selection -> "Between departure and return dates"
            else -> "Not selected"
        }

        is CalendarSelection.Month -> when {
            selection.start == date -> "Current month"
            selection.end == date -> "Current month"
            date in selection -> "Current month"
            else -> "Not selected"
        }
    }
}

private fun onClickLabel(
    date: LocalDate,
    selectionMode: CalendarParams.SelectionMode,
    selection: CalendarSelection,
): String? {
    return when (selectionMode) {
        is CalendarParams.SelectionMode.Single -> when (selection) {
            CalendarSelection.None -> selectionMode.startSelectionHint
            CalendarSelection.Single(date) -> selectionMode.startSelectionHint
            else -> null
        }

        is CalendarParams.SelectionMode.Range -> when (selection) {
            is CalendarSelection.None -> selectionMode.startSelectionHint
            is CalendarSelection.Dates ->
                when {
                    selection.start == null || selection.end != null -> selectionMode.startSelectionHint
                    else -> selectionMode.endSelectionHint
                }

            else -> null
        }

        is CalendarParams.SelectionMode.Disabled -> null
    }
}
