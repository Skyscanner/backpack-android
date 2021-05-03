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

package net.skyscanner.backpack.text

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.graphics.drawable.DrawableCompat
import net.skyscanner.backpack.R
import net.skyscanner.backpack.util.colorStateList
import net.skyscanner.backpack.util.createContextThemeWrapper
import net.skyscanner.backpack.util.use

open class BpkTextField @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
) : AppCompatEditText(
  createContextThemeWrapper(
    createContextThemeWrapper(context, attrs, androidx.appcompat.R.attr.editTextStyle),
    attrs, R.attr.bpkTextFieldStyle
  ),
  attrs,
  defStyleAttr
) {

  private var iconTintColor: Int = 0
    set(value) {
      field = value
      iconStart?.let { DrawableCompat.setTint(it, value) }
      iconEnd?.let { DrawableCompat.setTint(it, value) }
    }

  var iconStart: Drawable? = null
    set(value) {
      unscheduleDrawable(field)
      field = value
        ?.mutate()
        ?.let { DrawableCompat.wrap(it) }
        ?.also { DrawableCompat.setTint(value, iconTintColor) }
      setCompoundDrawablesRelativeWithIntrinsicBounds(iconStart, null, iconEnd, null)
    }

  var iconEnd: Drawable? = null
    set(value) {
      unscheduleDrawable(field)
      field = value
        ?.mutate()
        ?.let { DrawableCompat.wrap(it) }
        ?.also { DrawableCompat.setTint(value, iconTintColor) }
      setCompoundDrawablesRelativeWithIntrinsicBounds(iconStart, null, iconEnd, null)
    }

  init {
    initialize(attrs, defStyleAttr)
  }

  private fun initialize(attrs: AttributeSet?, defStyleAttr: Int) {
    BpkText.getFont(context, BpkText.BASE, BpkText.Weight.NORMAL).applyTo(paint)

    var textColor = context.getColor(R.color.bpkTextPrimary)
    var textColorDisabled = context.getColor(R.color.__textFieldTextDisabled)
    var hintNormalColor = context.getColor(R.color.__textFieldHint)
    var hintFocusedColor = context.getColor(R.color.__textFieldHint)
    var iconColor = context.getColor(R.color.__textFieldIcon)

    var background: Drawable = AppCompatResources.getDrawable(context, R.drawable.bpk_text_field_background)!!

    context.theme.obtainStyledAttributes(
      attrs,
      R.styleable.BpkTextField,
      defStyleAttr,
      0
    ).use {
      textColor = it.getColor(R.styleable.BpkTextField_textFieldColor, textColor)
      hintNormalColor = it.getColor(R.styleable.BpkTextField_textFieldColorHintNormal, hintNormalColor)
      hintFocusedColor = it.getColor(R.styleable.BpkTextField_textFieldColorHintFocused, hintFocusedColor)
      iconColor = it.getColor(R.styleable.BpkTextField_textFieldColorIcon, iconColor)
      iconStart = it.getDrawable(R.styleable.BpkTextField_textFieldIconStart)
      iconEnd = it.getDrawable(R.styleable.BpkTextField_textFieldIconEnd)
      background = it.getDrawable(R.styleable.BpkTextField_textFieldBackground) ?: background
    }

    this.iconTintColor = iconColor
    setTextColor(
      colorStateList(
        color = textColor,
        pressedColor = textColor,
        focusedColor = textColor,
        activatedColor = textColor,
        disabledColor = textColorDisabled,
      )
    )
    setHintTextColor(
      colorStateList(
        color = hintNormalColor,
        pressedColor = hintNormalColor,
        focusedColor = hintFocusedColor,
        activatedColor = hintFocusedColor,
        disabledColor = textColorDisabled,
      )
    )

    val paddingHorizontal = resources.getDimensionPixelSize(R.dimen.bpkSpacingBase)
    val paddingVertical = resources.getDimensionPixelSize(R.dimen.bpkSpacingMd) +
      resources.getDimensionPixelSize(R.dimen.bpkSpacingSm)
    setPaddingRelative(paddingHorizontal, paddingVertical, paddingHorizontal, paddingVertical)
    compoundDrawablePadding = resources.getDimensionPixelSize(R.dimen.bpkSpacingMd) +
      resources.getDimensionPixelSize(R.dimen.bpkSpacingSm)

    gravity = Gravity.START or Gravity.CENTER_VERTICAL
    this.background = background
    this.minHeight = resources.getDimensionPixelSize(R.dimen.bpkSpacingXxl) +
      resources.getDimensionPixelSize(R.dimen.bpkSpacingMd)
  }

  override fun onRtlPropertiesChanged(layoutDirection: Int) {
    super.onRtlPropertiesChanged(layoutDirection)
    textDirection = if (layoutDirection == View.LAYOUT_DIRECTION_RTL) View.TEXT_DIRECTION_RTL else View.TEXT_DIRECTION_LTR
  }
}
