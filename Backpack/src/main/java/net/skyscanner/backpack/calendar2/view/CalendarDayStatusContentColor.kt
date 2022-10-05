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

package net.skyscanner.backpack.calendar2.view

import android.content.Context
import android.content.res.ColorStateList
import net.skyscanner.backpack.R
import net.skyscanner.backpack.calendar2.CellStatus

internal typealias CalendarDayStatusContentColor = (CellStatus?) -> ColorStateList

internal fun CalendarDayStatusContentColor(
  context: Context,
): CalendarDayStatusContentColor {

  val default = context.getColorStateList(R.color.bpkTextPrimary)
  val positive = context.getColorStateList(R.color.bpkTextPrimaryInverse)
  val neutral = context.getColorStateList(R.color.bpkTextOnLight)
  val negative = context.getColorStateList(R.color.bpkTextPrimaryInverse)
  val empty = context.getColorStateList(R.color.bpkTextPrimary)

  return { status ->
    when (status) {
      CellStatus.Positive -> positive
      CellStatus.Neutral -> neutral
      CellStatus.Negative -> negative
      CellStatus.Empty -> empty
      null -> default
    }
  }
}
