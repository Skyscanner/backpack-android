/**
 * Backpack for Android - Skyscanner's Design System
 *
 * Copyright 2018-2020 Skyscanner Ltd
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

package net.skyscanner.backpack.chip.internal

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import net.skyscanner.backpack.R
import net.skyscanner.backpack.util.ResourcesUtil

internal fun chipRoundedRect(
  context: Context,
  background: ColorStateList,
  border: ColorStateList,
  borderWidthDp: Int,
): Drawable = GradientDrawable().apply {
  shape = GradientDrawable.RECTANGLE
  cornerRadii = FloatArray(8) { context.resources.getDimension(R.dimen.bpkBorderRadiusPill) }
  color = background
  setStroke(ResourcesUtil.dpToPx(borderWidthDp, context), border)
}

internal fun chipColors(
  selected: Int,
  default: Int,
  disabled: Int,
) = ColorStateList(
  arrayOf(
    intArrayOf(android.R.attr.state_enabled, android.R.attr.state_selected),
    intArrayOf(android.R.attr.state_enabled),
    intArrayOf(-android.R.attr.state_enabled),
  ),
  intArrayOf(
    selected,
    default,
    disabled,
  )
)
