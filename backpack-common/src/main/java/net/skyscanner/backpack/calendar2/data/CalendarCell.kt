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
import net.skyscanner.backpack.calendar2.CalendarParams.DayCellAccessibilityLabel
import net.skyscanner.backpack.calendar2.CalendarSelection
import net.skyscanner.backpack.calendar2.CellInfo
import net.skyscanner.backpack.util.InternalBackpackApi
import java.time.LocalDate
import java.time.YearMonth

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
sealed interface CalendarInteraction {

    @Immutable
    data class DateClicked(val day: CalendarCell.Day) : CalendarInteraction

    @Immutable
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
    contentDescription = date.format(params.dateContentDescriptionFormatter) + contentDescription(date, params.selectionMode),
    stateDescription = stateDescription(date, params.selectionMode, selection),
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

private fun contentDescription(
    date: LocalDate,
    selectionMode: CalendarParams.SelectionMode,
): String = when (selectionMode) {
    is CalendarParams.SelectionMode.Single -> selectionMode.contentDescription?.let { it(date) } ?: ""
    else -> ""
}

private fun stateDescription(
    date: LocalDate,
    selectionMode: CalendarParams.SelectionMode,
    selection: CalendarSelection,
): String? = when (selectionMode) {
    is CalendarParams.SelectionMode.Single -> when (selection) {
        is CalendarSelection.None -> selectionMode.noSelectionState
        is CalendarSelection.Single ->
            when (selection.date) {
                date -> selectionMode.startSelectionState.getAccessibilityLabel(date)
                else -> null
            }
        else -> null
    }

    is CalendarParams.SelectionMode.Range -> when (selection) {
        is CalendarSelection.Dates ->
            when {
                selection.start == date && selection.end == date -> selectionMode.startAndEndSelectionState
                selection.start == date && selection.end == null -> selectionMode.startSelectionState
                selection.start == date && selection.end != null -> selectionMode.startSelectionState
                selection.end == date -> selectionMode.endSelectionState
                selection.end != null && date in selection -> selectionMode.betweenSelectionState
                else -> null
            }

        else -> null
    }

    is CalendarParams.SelectionMode.Disabled -> null
}

private fun onClickLabel(
    date: LocalDate,
    selectionMode: CalendarParams.SelectionMode,
    selection: CalendarSelection,
): String? = when (selectionMode) {
    is CalendarParams.SelectionMode.Single -> selectionMode.startSelectionHint.getAccessibilityLabel(date)
    is CalendarParams.SelectionMode.Range -> when (selection) {
        is CalendarSelection.None -> selectionMode.startSelectionHint
        is CalendarSelection.Dates ->
            when {
                selection.end != null || date < selection.start -> selectionMode.startSelectionHint
                else -> selectionMode.endSelectionHint
            }

        else -> null
    }

    is CalendarParams.SelectionMode.Disabled -> null
}

fun DayCellAccessibilityLabel?.getAccessibilityLabel(date: LocalDate): String? = when (this) {
    is DayCellAccessibilityLabel.Static -> label
    is DayCellAccessibilityLabel.Costume -> label(date)
    else -> null
}
