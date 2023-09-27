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
import net.skyscanner.backpack.calendar2.extension.firstDay
import net.skyscanner.backpack.calendar2.extension.lastDay
import org.threeten.bp.LocalDate
import org.threeten.bp.YearMonth
import java.io.Serializable

/**
 * Describes the current selection in the calendar
 */
@Stable
sealed class CalendarSelection : Serializable {

    /**
     * Check whether selection contains the date
     */
    abstract operator fun contains(date: LocalDate): Boolean

    /**
     * No dates are selected
     */
    data object None : CalendarSelection() {
        override fun contains(date: LocalDate): Boolean =
            false
    }

    /**
     * Single [date] is selected
     */
    @Immutable
    data class Single(
        val date: LocalDate,
    ) : CalendarSelection() {

        override fun contains(date: LocalDate): Boolean =
            this.date == date
    }

    /**
     * Describes the current range selection in the calendar.
     */
    @Stable
    sealed class Range : CalendarSelection() {
        abstract val start: LocalDate
        abstract val end: LocalDate?

        override fun contains(date: LocalDate): Boolean =
            when (end) {
                null -> date == start
                else -> date >= start && date <= end
            }
    }

    /**
     * A whole [month] is selected.
     */
    @Immutable
    data class Month(
        val month: YearMonth,
        override val start: LocalDate = month.firstDay(),
        override val end: LocalDate = month.lastDay(),
    ) : Range()

    /**
     * A range of dates is selected.
     * @param start of range
     * @param end end of range. May be null if user haven't selected the end date yet
     */
    @Immutable
    data class Dates(override val start: LocalDate, override val end: LocalDate?) : Range()
}
