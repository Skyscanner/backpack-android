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
import net.skyscanner.backpack.calendar2.CellStatus

internal typealias CalendarDayLabelContentColor = (CellStatus?) -> ColorStateList

internal fun CalendarDayLabelContentColor(
  context: Context,
): CalendarDayLabelContentColor {

  val default = context.getColorStateList(R.color.bpkTextSecondary)
  val positive = context.getColorStateList(R.color.bpkMonteverde)
  val empty = context.getColorStateList(R.color.__calendarCellDisabledTextColor)

  return { status ->
    when (status) {
      CellStatus.Positive -> positive
      CellStatus.Neutral -> default
      CellStatus.Negative -> default
      CellStatus.Empty -> empty
      null -> default
    }
  }
}
