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
import org.threeten.bp.LocalDate
import org.threeten.bp.chrono.IsoChronology
import org.threeten.bp.format.DateTimeFormatterBuilder
import org.threeten.bp.format.FormatStyle
import org.threeten.bp.format.TextStyle
import org.threeten.bp.temporal.WeekFields
import java.text.SimpleDateFormat
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
    val contentDescriptionText: FormatStyle = FormatStyle.LONG,
    val now: LocalDate = LocalDate.now(),
    val monthSelectionMode: MonthSelectionMode = MonthSelectionMode.Disabled,
) {

    @InternalBackpackApi
    val weekFields = WeekFields.of(locale)

    internal val monthsFormatter = SimpleDateFormat("LLLL", locale)

    internal val daysFormatter = DateTimeFormatterBuilder()
        .appendLocalized(FormatStyle.FULL, null)
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
        object Disabled : SelectionMode

        /**
         * Only a single, non-disabled date can be selected.
         */
        object Single : SelectionMode

        /**
         * A range of dates can be selected.
         */
        object Range : SelectionMode
    }

    /**
     * Describes the month selection behaviour
     */
    @Stable
    sealed interface MonthSelectionMode {
        /**
         * No whole month selection is allowed.
         */
        object Disabled : MonthSelectionMode

        /**
         * Only an entire month can be selected, by tapping on the [label] next to its name.
         */
        data class SelectWholeMonth(val label: String) : MonthSelectionMode
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
    val label: String? = null,
    val style: CellStatusStyle = CellStatusStyle.Label,
) {

    internal companion object {
        val Default = CellInfo()
    }
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
