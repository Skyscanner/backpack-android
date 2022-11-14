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
import org.threeten.bp.YearMonth

/**
 * Describes any side effects from the calendar.
 */
@Stable
sealed interface CalendarEffect {
  /**
   * A Month [date] [CalendarEffect] is selected
   */
  @Immutable
  data class MonthSelected(val date: YearMonth) : CalendarEffect
}
