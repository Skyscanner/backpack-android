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

package net.skyscanner.backpack.calendar2.list

import androidx.recyclerview.widget.GridLayoutManager
import net.skyscanner.backpack.calendar2.data.CalendarCellDay
import net.skyscanner.backpack.calendar2.data.CalendarCellFooter
import net.skyscanner.backpack.calendar2.data.CalendarCellHeader
import net.skyscanner.backpack.calendar2.data.CalendarCellSpace
import net.skyscanner.backpack.calendar2.data.CalendarCells
import net.skyscanner.backpack.util.Consumer

internal class CalendarSpanSizeLookup : GridLayoutManager.SpanSizeLookup(), Consumer<CalendarCells> {

  private var data: CalendarCells = CalendarCells()
  val totalSpans = NUM_COLUMNS

  override fun invoke(data: CalendarCells) {
    this.data = data
  }

  override fun getSpanSize(position: Int): Int = when (data[position]) {
    is CalendarCellDay -> 1
    is CalendarCellFooter -> NUM_COLUMNS
    is CalendarCellHeader -> NUM_COLUMNS
    is CalendarCellSpace -> 1
  }

  private companion object {
    const val NUM_COLUMNS = 7
  }
}
