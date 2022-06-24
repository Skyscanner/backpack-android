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

import org.threeten.bp.LocalDate
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
 * @param dayOfWeekText [TextStyle] to format days of week in calendar header
 * @param dayOfWeekAccessibilityText [TextStyle] to format accessibility description of days of week in calendar header
 * @param dateAccessibilityText [TextStyle] to format accessibility description of a date cell
 */
data class CalendarParams(
  val selectionMode: SelectionMode,
  val range: ClosedRange<LocalDate> = LocalDate.now()..LocalDate.now().plusYears(1),
  val cellsInfo: Map<LocalDate, CellInfo> = emptyMap(),
  val locale: Locale = Locale.getDefault(),
  val dayOfWeekText: TextStyle = TextStyle.NARROW,
  val now: LocalDate = LocalDate.now(),
  val wholeMonthSelectionLabel: String = "",
) {

  internal val weekFields = WeekFields.of(locale)

  internal val monthsFormatter = SimpleDateFormat("LLLL", locale)

  /**
   * Describes the selection behaviour
   */
  enum class SelectionMode {
    /**
     * No date can be selected
     */
    Disabled,

    /**
     * Only a single, non-disabled date can be selected.
     */
    Single,

    /**
     * A range of dates can be selected.
     */
    Range
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
data class CellInfo(
  val disabled: Boolean = false,
  val status: CellStatus? = null,
  val label: String? = null,
  val style: CellStatusStyle = CellStatusStyle.Background,
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
   * The colour will be used for background
   */
  Background,

  /**
   * The colour will be used as [CellInfo.label] text colour
   */
  Label,
}
