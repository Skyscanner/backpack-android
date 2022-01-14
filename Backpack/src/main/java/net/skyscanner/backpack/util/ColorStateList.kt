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

package net.skyscanner.backpack.util

import android.content.res.ColorStateList
import androidx.annotation.ColorInt

internal fun ColorStateList.getColorForState(state: IntArray) =
  getColorForState(state, defaultColor)

internal inline fun colorStateList(
  @ColorInt color: Int,
  @ColorInt pressedColor: Int = color,
  @ColorInt focusedColor: Int = pressedColor,
  @ColorInt activatedColor: Int = pressedColor,
  @ColorInt disabledColor: Int
) = ColorStateList(
  arrayOf(
    intArrayOf(-android.R.attr.state_enabled),
    intArrayOf(android.R.attr.state_pressed),
    intArrayOf(android.R.attr.state_focused),
    intArrayOf(android.R.attr.state_activated),
    intArrayOf()
  ),
  intArrayOf(disabledColor, pressedColor, focusedColor, activatedColor, color)
)
