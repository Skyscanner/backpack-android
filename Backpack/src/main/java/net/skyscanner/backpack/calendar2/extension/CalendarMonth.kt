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

package net.skyscanner.backpack.calendar2.extension

import net.skyscanner.backpack.calendar2.data.CalendarItem
import net.skyscanner.backpack.calendar2.data.CalendarMonth

internal fun List<CalendarMonth>.cellByPosition(globalIndex: Int): CalendarItem {
  var month: CalendarMonth? = null
  var localIndex = globalIndex

  for (it in this) {
    if (localIndex in it.items.indices) {
      month = it
      break
    }
    localIndex -= it.items.size
  }

  return month?.items?.get(localIndex) ?: error("Unable to find a month for index $globalIndex")
}

internal fun List<CalendarMonth>.cellsCount(): Int =
  sumBy { it.items.size }
