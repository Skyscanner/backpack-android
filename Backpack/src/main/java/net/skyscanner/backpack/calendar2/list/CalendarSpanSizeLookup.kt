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

package net.skyscanner.backpack.calendar2.list

import androidx.recyclerview.widget.GridLayoutManager
import net.skyscanner.backpack.calendar2.data.CalendarCell

internal class CalendarSpanSizeLookup(
  private val adapter: CalendarAdapter,
) : GridLayoutManager.SpanSizeLookup() {

  val totalSpans = NUM_COLUMNS

  override fun getSpanSize(position: Int): Int = when (adapter[position]) {
    is CalendarCell.Day -> 1
    is CalendarCell.Header -> NUM_COLUMNS
    is CalendarCell.Space -> 1
  }

  private companion object {
    const val NUM_COLUMNS = 7
  }
}
