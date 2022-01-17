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

import android.view.View
import android.view.ViewGroup
import net.skyscanner.backpack.R
import net.skyscanner.backpack.calendar2.data.CalendarCell
import net.skyscanner.backpack.calendar2.view.CalendarDaySelectionBackground
import net.skyscanner.backpack.util.ItemHolder

internal class CalendarCellSpaceHolder(
  parent: ViewGroup,
) : ItemHolder<CalendarCell.Space>(parent, R.layout.view_bpk_calendar_space) {

  private val background = CalendarDaySelectionBackground(context)
  private val space = view.findViewById<View>(R.id.bpk_calendar_space)

  override fun bind(model: CalendarCell.Space) {
    space.background = background(if (model.selected) CalendarCell.Selection.Middle else null)
  }
}
