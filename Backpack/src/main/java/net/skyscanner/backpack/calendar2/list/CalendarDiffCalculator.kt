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

import androidx.recyclerview.widget.DiffUtil
import net.skyscanner.backpack.calendar2.data.CalendarCell
import net.skyscanner.backpack.calendar2.data.CalendarCells

internal class CalendarDiffCalculator(
    private val oldState: CalendarCells,
    private val newState: CalendarCells,
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int =
        oldState.size

    override fun getNewListSize(): Int =
        newState.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val old = oldState[oldItemPosition]

        return when (val new = newState[newItemPosition]) {
            is CalendarCell.Day -> new.date == (old as? CalendarCell.Day)?.date
            is CalendarCell.Header -> new.yearMonth == (old as? CalendarCell.Header)?.yearMonth
            is CalendarCell.Space -> new.yearMonth == (old as? CalendarCell.Space)?.yearMonth && new.position == old.position
        }
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        newState[newItemPosition] == oldState[oldItemPosition]
}
