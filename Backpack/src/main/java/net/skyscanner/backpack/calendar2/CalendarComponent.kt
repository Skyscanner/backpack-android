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

import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * Calendar public API.
 *
 * It works like a state machine – calling each method updates its state in background and emits new state to [state].
 * The state could also be updated when some UI event (such as date selection) happens.
 */
interface CalendarComponent {

  /**
   * The current state the calendar in.
   */
  val state: StateFlow<CalendarState>

  /**
   * The side effects the calendar emits.
   */
  val effects: SharedFlow<CalendarEffect>

  /**
   * Updates the params used to configure the calendar.
   * Please note the changes won't apply immediately – the new state will be calculated in background thread.
   */
  fun setParams(value: CalendarParams)

  /**
   * Sets custom dates selection programmatically.
   *
   * [CalendarSelection] needs to meet to validity criteria, otherwise this method will have no effect.
   *
   * Validity criteria:
   * – selection is within the [CalendarParams.range]
   * – selection boundaries are not disabled dates
   * – selection type corresponds to [CalendarParams.selectionMode]
   *
   * If [selection] is [CalendarSelection.Range]:
   * - range has start date set
   * – start date is lower than end date (if end date is present)
   *
   * Please note the changes won't apply immediately – the new state will be calculated in background thread.
   */

  fun setSelection(selection: CalendarSelection)
}
