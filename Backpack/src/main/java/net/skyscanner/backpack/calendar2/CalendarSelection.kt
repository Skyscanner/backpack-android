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

package net.skyscanner.backpack.calendar2

import net.skyscanner.backpack.util.ExperimentalBackpackApi
import org.threeten.bp.LocalDate

/**
 * Describes the current selection in the calendar
 */
@ExperimentalBackpackApi
sealed class CalendarSelection {

  /**
   * Check whether selection contains the date
   */
  abstract operator fun contains(date: LocalDate): Boolean

  /**
   * No dates are selected
   */
  @ExperimentalBackpackApi
  object None : CalendarSelection() {
    override fun contains(date: LocalDate): Boolean =
      false
  }

  /**
   * Single [date] is selected
   */
  @ExperimentalBackpackApi
  data class Single(
    val date: LocalDate,
  ) : CalendarSelection() {

    override fun contains(date: LocalDate): Boolean =
      this.date == date
  }

  /**
   * A range of dates is selected.
   * @param start of range
   * @param end end of range. May be null if user haven't selected the end date yet
   */
  @ExperimentalBackpackApi
  data class Range(
    val start: LocalDate,
    val end: LocalDate?,
  ) : CalendarSelection() {

    override fun contains(date: LocalDate): Boolean =
      when (end) {
        null -> date == start
        else -> date >= start && date <= end
      }
  }
}
