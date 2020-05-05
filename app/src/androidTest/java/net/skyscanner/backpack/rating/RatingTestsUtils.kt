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

package net.skyscanner.backpack.rating

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import net.skyscanner.backpack.demo.R

internal fun createTestRating(
  context: Context,
  orientation: BpkRating.Orientation = BpkRating.Orientation.Horizontal,
  size: BpkRating.Size = BpkRating.Size.Base,
  value: Float = 7f,
  rtl: Boolean = false
) =
  BpkRating(context, orientation, size).apply {
    icon = {
      when (it) {
        BpkRating.Score.Low -> context.getDrawable(R.drawable.bpk_star_outline)
        BpkRating.Score.Medium -> context.getDrawable(R.drawable.bpk_star_half)
        BpkRating.Score.High -> context.getDrawable(R.drawable.bpk_star)
      }
    }
    title = {
      when (it) {
        BpkRating.Score.Low -> "Low"
        BpkRating.Score.Medium -> "Medium"
        BpkRating.Score.High -> "High"
      }
    }
    subtitle = {
      when (it) {
        BpkRating.Score.Low -> "Sub Low"
        BpkRating.Score.Medium -> "Sub Medium"
        BpkRating.Score.High -> "Sub High"
      }
    }
    this.value = value
    layoutDirection = if (rtl) View.LAYOUT_DIRECTION_RTL else View.LAYOUT_DIRECTION_LTR
    background = ColorDrawable(Color.WHITE)
  }
