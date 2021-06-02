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

package net.skyscanner.backpack.calendar2.view

import android.content.Context
import android.content.res.ColorStateList
import net.skyscanner.backpack.R
import net.skyscanner.backpack.calendar2.data.CalendarCellDay
import net.skyscanner.backpack.util.use

internal typealias CalendarDaySelectionContentColor = (CalendarCellDay.Selection?) -> ColorStateList

internal fun CalendarDaySelectionContentColor(
  context: Context,
): CalendarDaySelectionContentColor {

  val default: ColorStateList = context.getColorStateList(R.color.bpkTextPrimary)
  val selected: ColorStateList
  val range: ColorStateList

  context.obtainStyledAttributes(null, R.styleable.BpkCalendar, R.attr.bpkCalendarStyle, 0).use {
    selected = it.getColorStateList(R.styleable.BpkCalendar_calendarDateSelectedTextColor)
      ?: context.getColorStateList(R.color.__calendarSelectedTextColor)

    range = it.getColorStateList(R.styleable.BpkCalendar_calendarRangeTextColor)
      ?: context.getColorStateList(R.color.__calendarRangeText)
  }

  return { selection ->
    when (selection) {
      CalendarCellDay.Selection.Single -> selected
      CalendarCellDay.Selection.Double -> selected
      CalendarCellDay.Selection.Start -> selected
      CalendarCellDay.Selection.Middle -> range
      CalendarCellDay.Selection.End -> selected
      null -> default
    }
  }
}
