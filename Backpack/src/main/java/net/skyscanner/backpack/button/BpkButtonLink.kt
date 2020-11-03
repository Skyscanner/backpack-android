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

package net.skyscanner.backpack.button

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.graphics.drawable.DrawableCompat
import net.skyscanner.backpack.R
import net.skyscanner.backpack.button.internal.BpkButtonWithIcon
import net.skyscanner.backpack.button.internal.ButtonStyles
import net.skyscanner.backpack.text.BpkText
import net.skyscanner.backpack.util.createContextThemeWrapper
import net.skyscanner.backpack.util.use

open class BpkButtonLink @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
) : BpkButtonWithIcon(
  createContextThemeWrapper(context, attrs, R.attr.bpkButtonLinkStyle),
  attrs,
  defStyleAttr
) {

  companion object {
    const val START = 0
    const val END = 1
  }

  var uppercase: Boolean = true
    set(value) {
      field = value
      isAllCaps = value
    }

  var icon: Drawable?
    get() = iconDrawable
    set(value) {
      this.iconDrawable = value
    }

  private val font = BpkText.getFont(this.context, BpkText.SM, BpkText.Weight.EMPHASIZED)

  init {
    var iconPosition: Int = iconPosition
    var icon: Drawable? = null

    this.context.theme.obtainStyledAttributes(attrs, R.styleable.BpkButton, defStyleAttr, 0)
      .use {
        iconPosition = it.getInt(R.styleable.BpkButton_buttonIconPosition, iconPosition)
        it.getResourceId(R.styleable.BpkButton_buttonIcon, 0).let { res ->
          if (res != 0) {
            icon = AppCompatResources.getDrawable(context, res)
          }
        }
      }

    this.iconPosition = iconPosition
    this.iconDrawable = icon

    isClickable = isEnabled
    font.applyTo(this)

    var uppercase = true

    this.context.theme.obtainStyledAttributes(attrs, R.styleable.BpkButtonLink, defStyleAttr, 0)
      .use {
        uppercase = it.getBoolean(R.styleable.BpkButtonLink_uppercase, true)
      }
    this.uppercase = uppercase

    compoundDrawablePadding = resources.getDimensionPixelSize(R.dimen.bpkSpacingSm)

    val style = ButtonStyles.Link(context)
    background = style.getButtonBackground(isEnabled, iconPosition)
    setTextColor(style.contentColor)

    val paddingVertical = resources.getDimensionPixelSize(R.dimen.bpkSpacingMd) + (resources.getDimensionPixelSize(R.dimen.bpkBorderSizeLg) / 2)
    setPadding(0, paddingVertical, 0, paddingVertical)
  }

  override fun setTextColor(color: Int) {
    super.setTextColor(color)
    updateIconColor(ColorStateList.valueOf(color))
  }

  override fun setTextColor(colors: ColorStateList) {
    super.setTextColor(colors)
    updateIconColor(colors)
  }

  private fun updateIconColor(colors: ColorStateList) {
    val icon = icon
    if (icon != null) {
      DrawableCompat.setTintList(icon, colors)
    }
  }
}
