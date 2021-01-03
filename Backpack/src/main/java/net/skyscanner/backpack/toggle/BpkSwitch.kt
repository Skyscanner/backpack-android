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

package net.skyscanner.backpack.toggle

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import androidx.appcompat.widget.SwitchCompat
import androidx.core.content.ContextCompat
import androidx.core.graphics.ColorUtils
import net.skyscanner.backpack.R
import net.skyscanner.backpack.util.createContextThemeWrapper
import net.skyscanner.backpack.util.getColor
import net.skyscanner.backpack.util.use

private fun wrapContext(context: Context, attrs: AttributeSet?): Context {
  val withBaseStyle = createContextThemeWrapper(context, attrs, androidx.appcompat.R.attr.switchStyle)
  return createContextThemeWrapper(withBaseStyle, attrs, R.attr.bpkSwitchStyle)
}

// taken from https://github.com/material-components/material-components-android/blob/master/lib/java/com/google/android/material/color/MaterialColors.java#L42
private const val CHECKED_TRACK_COLOR_ALPHA = (0.38f * 255f).toInt()

/**
 * BpkSwitch allow users to toggle between two states, on or off.
 *
 * This class extends [SwitchCompat] directly and thus follows the same interface and design,
 * with the exception of [SwitchCompat.getTrackTintList] and [SwitchCompat.getThumbTintList] that are set
 * according to Backpack's design.
 *
 * @see SwitchCompat
 */
open class BpkSwitch @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
) : SwitchCompat(wrapContext(context, attrs), attrs, defStyleAttr) {

  init {
    initialize(attrs, defStyleAttr)
  }

  fun initialize(attrs: AttributeSet?, defStyleAttr: Int) {
    val textDisabledColor = ContextCompat.getColor(context, R.color.bpkSkyGrayTint04)
    val textEnabledColor = ContextCompat.getColor(context, R.color.bpkTextPrimary)
    context.theme.obtainStyledAttributes(attrs, R.styleable.BpkSwitch, defStyleAttr, 0).use {
      val checkedColor = it.getColor(R.styleable.BpkSwitch_switchPrimaryColor, getColor(R.color.bpkPrimary))
      val trackCheckedColor = ColorUtils.setAlphaComponent(checkedColor, CHECKED_TRACK_COLOR_ALPHA)

      trackTintList = getColorStateList(trackCheckedColor, ContextCompat.getColor(context, R.color.__switchTrackDisabled))
      thumbTintList = getColorStateList(checkedColor, ContextCompat.getColor(context, R.color.__switchThumbDisabled))
    }
    setTextColor(
      ColorStateList(
        arrayOf(intArrayOf(-android.R.attr.state_enabled), intArrayOf()),
        intArrayOf(textDisabledColor, textEnabledColor)
      )
    )
  }

  private fun getColorStateList(checkedColor: Int, uncheckedColor: Int) =
    ColorStateList(
      arrayOf(
        intArrayOf(android.R.attr.state_checked),
        intArrayOf(-android.R.attr.state_checked)
      ),
      intArrayOf(
        checkedColor,
        uncheckedColor
      )
    )
}
