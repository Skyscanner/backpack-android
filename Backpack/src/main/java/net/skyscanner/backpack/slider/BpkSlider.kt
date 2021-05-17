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

package net.skyscanner.backpack.slider

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import com.google.android.material.slider.RangeSlider
import net.skyscanner.backpack.R
import net.skyscanner.backpack.util.BpkTheme
import net.skyscanner.backpack.util.createContextThemeWrapper
import net.skyscanner.backpack.util.use

open class BpkSlider @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0,
) : RangeSlider(
  createContextThemeWrapper(
    createContextThemeWrapper(context, attrs, com.google.android.material.R.attr.sliderStyle),
    attrs, R.attr.bpkSliderStyle
  ),
  attrs,
  defStyleAttr
) {

  var value: Float
    set(value) = setValues(value)
    get() = values[0]

  init {
    initialize(attrs, defStyleAttr)
  }

  private fun initialize(attrs: AttributeSet?, defStyleAttr: Int) {
    context.theme.obtainStyledAttributes(
      attrs,
      R.styleable.BpkSlider,
      defStyleAttr,
      0
    ).use {
      val primaryColor = ColorStateList.valueOf(BpkTheme.getPrimaryColor(context))
      val lineColor = ColorStateList.valueOf(context.getColor(R.color.bpkLine))

      thumbTintList = it.getColorStateList(R.styleable.BpkSlider_sliderThumbColor) ?: primaryColor
      trackActiveTintList = it.getColorStateList(R.styleable.BpkSlider_sliderTrackColorActive) ?: primaryColor
      trackInactiveTintList = it.getColorStateList(R.styleable.BpkSlider_sliderTrackColorInactive) ?: lineColor

      if (it.hasValue(R.styleable.BpkSlider_android_value)) {
        value = it.getFloat(R.styleable.BpkSlider_android_value, 0.0f)
      }

      isTickVisible = false
    }
  }
}
