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

import java.text.SimpleDateFormat
import java.util.Locale
import net.skyscanner.backpack.util.ExperimentalBackpackApi
import org.threeten.bp.LocalDate
import org.threeten.bp.format.TextStyle
import org.threeten.bp.temporal.WeekFields

@ExperimentalBackpackApi
data class CalendarParams(
  val range: ClosedRange<LocalDate>,
  val selectionMode: SelectionMode,
  val cellsInfo: Map<LocalDate, CellInfo> = emptyMap(),
  val locale: Locale = Locale.getDefault(),
  val dayOfWeekText: TextStyle = TextStyle.NARROW,
  val dayOfWeekAccessibilityText: TextStyle = TextStyle.FULL_STANDALONE,
//  val monthsText: TextStyle = TextStyle.FULL_STANDALONE, inaccessible for now,
//  see https://github.com/ThreeTen/threetenbp/issues/55
  val dateAccessibilityText: TextStyle = TextStyle.FULL,
  val now: LocalDate = LocalDate.now(),
) {

  internal val weekFields = WeekFields.of(locale)

  internal val monthsFormatter = SimpleDateFormat("LLLL", locale)

  @ExperimentalBackpackApi
  enum class SelectionMode {
    Disabled,
    Single,
    Range,
  }
}

@ExperimentalBackpackApi
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

@ExperimentalBackpackApi
enum class CellStatus {
  Positive,
  Neutral,
  Negative,
  Empty,
}

@ExperimentalBackpackApi
enum class CellStatusStyle {
  Background,
  Label,
}
