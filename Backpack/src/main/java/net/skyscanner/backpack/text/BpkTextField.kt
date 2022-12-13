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

package net.skyscanner.backpack.text

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.AppCompatEditText
import net.skyscanner.backpack.R
import net.skyscanner.backpack.util.colorStateList
import net.skyscanner.backpack.util.createContextThemeWrapper
import net.skyscanner.backpack.util.use

open class BpkTextField @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0,
) : AppCompatEditText(
  createContextThemeWrapper(
    createContextThemeWrapper(context, attrs, androidx.appcompat.R.attr.editTextStyle),
    attrs, R.attr.bpkTextFieldStyle,
  ),
  attrs,
  defStyleAttr,
) {

  private var iconTintColor: Int = 0
    set(value) {
      field = value
      iconStart?.setTint(value)
      iconEnd?.setTint(value)
    }

  var iconStart: Drawable? = null
    set(value) {
      unscheduleDrawable(field)
      field = value
        ?.mutate()
        ?.also { it.setTint(iconTintColor) }
      setCompoundDrawablesRelativeWithIntrinsicBounds(iconStart, null, iconEnd, null)
    }

  var iconEnd: Drawable? = null
    set(value) {
      unscheduleDrawable(field)
      field = value
        ?.mutate()
        ?.also { it.setTint(iconTintColor) }
      setCompoundDrawablesRelativeWithIntrinsicBounds(iconStart, null, iconEnd, null)
    }

  var hasError: Boolean = false
    set(value) {
      field = value
      refreshDrawableState()
    }

  init {
    initialize(attrs, defStyleAttr)
  }

  private fun initialize(attrs: AttributeSet?, defStyleAttr: Int) {
    BpkText.getFont(context, BpkText.TextStyle.BodyDefault).applyTo(paint)

    var textColor = context.getColor(R.color.bpkTextPrimary)
    var textColorDisabled = context.getColor(R.color.bpkTextDisabled)
    var hintNormalColor = context.getColor(R.color.bpkTextDisabled)
    var hintFocusedColor = hintNormalColor
    var iconColor = context.getColor(R.color.bpkTextSecondary)

    var background: Drawable = AppCompatResources.getDrawable(context, R.drawable.bpk_text_field_background)!!

    context.theme.obtainStyledAttributes(
      attrs,
      R.styleable.BpkTextField,
      defStyleAttr,
      0,
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
        disabledColor = textColorDisabled,
      ),
    )
    setHintTextColor(
      colorStateList(
        color = hintNormalColor,
        pressedColor = hintNormalColor,
        focusedColor = hintFocusedColor,
        activatedColor = hintFocusedColor,
        disabledColor = context.getColor(R.color.bpkTextDisabled),
      ),
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

  override fun onCreateDrawableState(extraSpace: Int): IntArray {
    val drawableState = super.onCreateDrawableState(extraSpace + 1)
    if (hasError) {
      mergeDrawableStates(drawableState, ERROR_STATE_SET)
    }
    return drawableState
  }
}

private val ERROR_STATE_SET = intArrayOf(R.attr.state_error)
