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

package net.skyscanner.backpack.checkbox

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatCheckBox
import net.skyscanner.backpack.R
import net.skyscanner.backpack.text.BpkText
import net.skyscanner.backpack.util.createContextThemeWrapper
import net.skyscanner.backpack.util.use

open class BpkCheckbox @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0,
) : AppCompatCheckBox(
  createContextThemeWrapper(
    createContextThemeWrapper(context, attrs, androidx.appcompat.R.attr.checkboxStyle),
    attrs, R.attr.bpkCheckboxStyle
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
    var checkmarkDefaultColor = context.getColor(R.color.bpkTextSecondary)
    var checkmarkCheckedColor = context.getColor(R.color.bpkCoreAccent)
    var checkmarkDisabledColor = context.getColor(R.color.bpkTextDisabled)
    val textDisabledColor = context.getColor(R.color.bpkTextDisabled)
    val textEnabledColor = context.getColor(R.color.bpkTextPrimary)
    context.theme.obtainStyledAttributes(
      attrs,
      R.styleable.BpkCheckbox,
      defStyleAttr,
      0
    ).use {
      checkmarkDefaultColor = it.getInt(R.styleable.BpkCheckbox_checkboxColor, checkmarkDefaultColor)
      checkmarkCheckedColor = it.getInt(R.styleable.BpkCheckbox_checkboxColorChecked, checkmarkCheckedColor)
      checkmarkDisabledColor = it.getInt(R.styleable.BpkCheckbox_checkboxColorDisabled, checkmarkDisabledColor)
    }

    this.disabledTint = ColorStateList.valueOf(checkmarkDisabledColor)
    this.enabledTint = ColorStateList(
      arrayOf(intArrayOf(android.R.attr.state_checked), intArrayOf()),
      intArrayOf(checkmarkCheckedColor, checkmarkDefaultColor)
    )
    updateTint()
    BpkText.getFont(context, BpkText.TextStyle.Footnote).applyTo(this)
    setTextColor(
      ColorStateList(
        arrayOf(intArrayOf(-android.R.attr.state_enabled), intArrayOf()),
        intArrayOf(textDisabledColor, textEnabledColor)
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
