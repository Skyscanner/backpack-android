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

package net.skyscanner.backpack.calendar2.adapter

import androidx.recyclerview.widget.DiffUtil
import net.skyscanner.backpack.calendar2.data.CalendarDay
import net.skyscanner.backpack.calendar2.data.CalendarFooter
import net.skyscanner.backpack.calendar2.data.CalendarHeader
import net.skyscanner.backpack.calendar2.data.CalendarMonth
import net.skyscanner.backpack.calendar2.data.CalendarSpace
import net.skyscanner.backpack.calendar2.extension.cellByPosition
import net.skyscanner.backpack.calendar2.extension.cellsCount

internal class CalendarDiffCalculator(
  private val oldState: List<CalendarMonth>,
  private val newState: List<CalendarMonth>,
) : DiffUtil.Callback() {

  override fun getOldListSize(): Int =
    oldState.cellsCount()

  override fun getNewListSize(): Int =
    newState.cellsCount()

  override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
    val old = oldState.cellByPosition(oldItemPosition)
    val new = newState.cellByPosition(newItemPosition)

    return when (new) {
      is CalendarDay -> new.date == (old as? CalendarDay)?.date
      is CalendarFooter -> new.yearMonth == (old as? CalendarFooter)?.yearMonth
      is CalendarHeader -> new.yearMonth == (old as? CalendarHeader)?.yearMonth
      is CalendarSpace -> new.yearMonth == (old as? CalendarSpace)?.yearMonth
    }
  }

  override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
    val old = oldState.cellByPosition(oldItemPosition)
    val new = newState.cellByPosition(newItemPosition)
    return old == new
  }
}
