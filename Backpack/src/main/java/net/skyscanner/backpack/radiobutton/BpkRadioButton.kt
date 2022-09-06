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

package net.skyscanner.backpack.radiobutton

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatRadioButton
import net.skyscanner.backpack.R
import net.skyscanner.backpack.text.BpkText
import net.skyscanner.backpack.util.createContextThemeWrapper
import net.skyscanner.backpack.util.use

open class BpkRadioButton @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
) : AppCompatRadioButton(
  createContextThemeWrapper(
    createContextThemeWrapper(context, attrs, androidx.appcompat.R.attr.radioButtonStyle),
    attrs, R.attr.bpkRadioButtonStyle
  ),
  attrs,
  defStyleAttr
) {

  private lateinit var disabledTint: ColorStateList
  private lateinit var enabledTint: ColorStateList

  init {
    initialize(attrs, defStyleAttr)
  }

  private fun initialize(attrs: AttributeSet?, defStyleAttr: Int) {
    var radioButtonDefaultColor = context.getColor(R.color.bpkTextSecondary)
    var radioButtonCheckedColor = context.getColor(R.color.bpkCoreAccent)
    var radioButtonDisabledColor = context.getColor(R.color.bpkTextDisabled)
    val textEnabledColor = context.getColor(R.color.bpkTextPrimary)
    context.theme.obtainStyledAttributes(
      attrs,
      R.styleable.BpkRadioButton,
      defStyleAttr,
      0
    ).use {
      radioButtonDefaultColor = it.getInt(R.styleable.BpkRadioButton_radioButtonColor, radioButtonDefaultColor)
      radioButtonCheckedColor = it.getInt(R.styleable.BpkRadioButton_radioButtonColorChecked, radioButtonCheckedColor)
      radioButtonDisabledColor = it.getInt(R.styleable.BpkRadioButton_radioButtonColorDisabled, radioButtonDisabledColor)
    }

    this.disabledTint = ColorStateList.valueOf(radioButtonDisabledColor)
    this.enabledTint = ColorStateList(
      arrayOf(intArrayOf(android.R.attr.state_checked), intArrayOf()),
      intArrayOf(radioButtonCheckedColor, radioButtonDefaultColor)
    )
    updateTint()
    BpkText.getFont(context, BpkText.TextStyle.Footnote).applyTo(this)
    setTextColor(
      ColorStateList(
        arrayOf(intArrayOf(-android.R.attr.state_enabled), intArrayOf()),
        intArrayOf(radioButtonDisabledColor, textEnabledColor),
      )
    )

    setPaddingRelative(resources.getDimensionPixelSize(R.dimen.bpkSpacingSm), paddingTop, paddingEnd, paddingBottom)
  }

  override fun setEnabled(enabled: Boolean) {
    super.setEnabled(enabled)
    updateTint()
  }

  private fun updateTint() {
    if (!isTintInitialized()) {
      return
    }
    buttonTintList = if (isEnabled) enabledTint else disabledTint
  }

  private fun isTintInitialized() = ::disabledTint.isInitialized && ::enabledTint.isInitialized
}
