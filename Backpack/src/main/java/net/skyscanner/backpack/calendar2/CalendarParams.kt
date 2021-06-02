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

import java.util.Locale
import org.threeten.bp.LocalDate
import org.threeten.bp.YearMonth
import org.threeten.bp.format.TextStyle
import org.threeten.bp.temporal.WeekFields

data class CalendarParams(
  val range: ClosedRange<LocalDate>,
  val selectionMode: SelectionMode,
  val info: Map<LocalDate, Info> = emptyMap(),
  val footers: Set<YearMonth> = emptySet(),
  val locale: Locale = Locale.getDefault(),
  val dayOfWeekText: TextStyle = TextStyle.NARROW,
  val dayOfWeekAccessibilityText: TextStyle = TextStyle.FULL,
  val monthsText: TextStyle = TextStyle.FULL,
  val dateAccessibilityText: TextStyle = TextStyle.FULL,
  val now: LocalDate = LocalDate.now(),
) {

  internal val weekFields = WeekFields.of(locale)

  enum class SelectionMode {
    Disabled,
    Single,
    Range,
  }

  data class Info(
    val status: Status? = null,
    val label: String? = null,
  ) {

    internal companion object {
      val Default = Info()
    }
  }

  enum class Status {
    Disabled,
    Highlighted,
    Positive,
    Neutral,
    Negative,
    Empty,
  }
}
