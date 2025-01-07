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

package net.skyscanner.backpack.calendar2

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import net.skyscanner.backpack.calendar2.CalendarParams.MonthSelectionMode
import net.skyscanner.backpack.util.InternalBackpackApi
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.YearMonth
import java.time.chrono.IsoChronology
import java.time.format.DateTimeFormatterBuilder
import java.time.format.FormatStyle
import java.time.format.TextStyle
import java.time.temporal.WeekFields
import java.util.Locale

/**
 * Describes the calendar configuration.
 *
 * @param range range of the dates available for selection (including [ClosedRange.start] and [ClosedRange.endInclusive]).
 * Please notice that Calendar can display dates outside of the range (for instance, to render months in a complete form).
 *
 * @param selectionMode setting describing the selection behaviour
 * @param cellsInfo additional information to be added to dates cell
 * @param locale locale used for formatting and locale-specific behaviour, e.g. finding first day of week
 * @param dayOfWeekText [TextStyle] to format days of week in calendar header. Beware of the fact that some Chinese languages may require SHORT style instead of NARROW.
 * @param now [LocalDate] a date for the calendar to consider as current
 * @param monthSelectionMode [MonthSelectionMode] setting describing the month selection behaviour
 */
@Immutable
data class CalendarParams(
    val selectionMode: SelectionMode,
    val range: ClosedRange<LocalDate> = LocalDate.now()..LocalDate.now().plusYears(1),
    val cellsInfo: Map<LocalDate, CellInfo> = emptyMap(),
    val locale: Locale = Locale.getDefault(),
    val dayOfWeekText: TextStyle = findBestWeekdayStyleForLocale(locale),
    val dateContentDescriptionStyle: FormatStyle = FormatStyle.FULL,
    val now: LocalDate = LocalDate.now(),
    val monthSelectionMode: MonthSelectionMode = MonthSelectionMode.Disabled,
    val yearLabelInMonthHeader: Boolean = false,
) {

    @InternalBackpackApi
    val weekFields = WeekFields.of(locale)

    internal val monthsFormatter = SimpleDateFormat(if (yearLabelInMonthHeader) "LLLL yyyy" else "LLLL", locale)

    internal val dateContentDescriptionFormatter = DateTimeFormatterBuilder()
        .appendLocalized(dateContentDescriptionStyle, null)
        .toFormatter(locale)
        .withChronology(IsoChronology.INSTANCE)

    /**
     * Describes the selection behaviour
     */
    @Stable
    sealed interface SelectionMode {
        /**
         * No date can be selected
         */
        data object Disabled : SelectionMode

        /**
         * Only a single, non-disabled date can be selected.
         */

        /**
         * Accessibility labels are NOT supported in the view version
         */
        data class Single(
            val startSelectionHint: String? = null,
            val noSelectionState: String? = null,
            val startSelectionState: String? = null,
        ) : SelectionMode

        /**
         * A range of dates can be selected.
         */
        data class Range(
            val startSelectionHint: String? = null,
            val endSelectionHint: String? = null,
            val noSelectionState: String? = null,
            val startSelectionState: String? = null,
            val startAndEndSelectionState: String? = null,
            val endSelectionState: String? = null,
            val betweenSelectionState: String? = null,
        ) : SelectionMode
    }

    /**
     * Describes the month selection behaviour
     */
    @Stable
    sealed interface MonthSelectionMode {
        /**
         * No whole month selection is allowed.
         */
        data object Disabled : MonthSelectionMode

        /**
         * Only an entire month can be selected, by tapping on the [label] next to its name.
         */
        data class SelectWholeMonth(
            val label: String,
            val selectableMonthRange: ClosedRange<YearMonth>,
        ) : MonthSelectionMode
    }
}

/**
 * Additional information to be added to dates cell
 *
 * @param disabled marks this date as disabled – it cannot be chosen as a selection boundary
 * @param status adds colouring behaviour to the cell
 * @param label adds the label to the bottom of the cell
 * @param style determines how colouring will work for this cell
 */
@Immutable
data class CellInfo(
    val disabled: Boolean = false,
    val status: CellStatus? = null,
    val label: CellLabel = CellLabel.Text(""), // Default is empty text
    val style: CellStatusStyle = CellStatusStyle.Label,
) {

    internal companion object {
        val Default = CellInfo()
    }
}

/**
 * Describes the label of the cell
 */
sealed class CellLabel {
    data class Text(val text: String) : CellLabel()
    data class Icon(
        val resId: Int,
        val tint: Int? = null,
    ) : CellLabel()
}

/**
 * Describes the colouring of the cell
 */
enum class CellStatus {

    /**
     * Positive (green) colouring
     */
    Positive,

    /**
     * Neutral (yellow) colouring
     */
    Neutral,

    /**
     * Negative (red) colouring
     */
    Negative,

    /**
     * Empty (grey) colouring
     */
    Empty,
}

/**
 * Describes the colouring behaviour of the cell
 */
enum class CellStatusStyle {

    /**
     * The colour will be used as [CellInfo.label] text colour
     */
    Label,
}

private fun findBestWeekdayStyleForLocale(locale: Locale): TextStyle =
    when (locale.language.lowercase()) {
        "zh" -> when (locale.country.lowercase()) {
            // In Traditional Chinese, the narrow form is presented by all the same hieroglyphs
            // Therefore we fallback to the short form
            "mo", "hk", "tw", "sg" -> TextStyle.SHORT
            else -> TextStyle.NARROW
        }

        else -> TextStyle.NARROW
    }
