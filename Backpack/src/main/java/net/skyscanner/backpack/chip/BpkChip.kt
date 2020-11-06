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

package net.skyscanner.backpack.chip

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.Gravity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.view.ViewCompat
import net.skyscanner.backpack.R
import net.skyscanner.backpack.text.BpkText
import net.skyscanner.backpack.util.createContextThemeWrapper
import net.skyscanner.backpack.util.use

open class BpkChip @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
) : BpkText(createContextThemeWrapper(context, attrs, R.attr.bpkChipStyle), attrs, defStyleAttr) {

  private val iconPadding = context.resources.getDimensionPixelSize(R.dimen.bpkSpacingSm)
  private val iconSize = context.resources.getDimensionPixelSize(R.dimen.bpk_icon_size_normal)
  private val textColor = ColorStateList(
    arrayOf(
      intArrayOf(android.R.attr.state_enabled, android.R.attr.state_selected),
      intArrayOf(android.R.attr.state_enabled),
      intArrayOf(-android.R.attr.state_enabled),
    ),
    intArrayOf(
      ContextCompat.getColor(context, R.color.bpkWhite),
      ContextCompat.getColor(context, R.color.bpkTextPrimary),
      ContextCompat.getColor(context, R.color.bpkSkyGrayTint04),
    )
  )

  var disabled: Boolean = false
    set(value) {
      field = value
      this.isEnabled = !disabled
    }

  var chipBackgroundColor: Int = ContextCompat.getColor(context, R.color.__chipSolidBackground)
    set(value) {
      field = value
      updateBackground()
    }

  var selectedBackgroundColor: Int = ContextCompat.getColor(context, R.color.bpkPrimaryLight)
    set(value) {
      field = value
      updateBackground()
    }

  var icon: Drawable? = null
    set(value) {
      field = value
        ?.mutate()
        ?.let { DrawableCompat.wrap(it) }
        ?.apply { setBounds(0, 0, iconSize, iconSize) }
        ?.also {
          DrawableCompat.setTintList(it, textColor)
          this.setCompoundDrawablesRelative(it, null, null, null)
        }
    }

  init {
    compoundDrawablePadding = iconPadding
    gravity = Gravity.CENTER_VERTICAL
    textStyle = SM
    weight = Weight.NORMAL
    this.setTextColor(textColor)
    this.isSingleLine = true

    initialize(attrs, defStyleAttr)
  }

  private fun initialize(attrs: AttributeSet?, defStyleAttr: Int) {
    context.theme.obtainStyledAttributes(attrs, R.styleable.BpkChip, defStyleAttr, 0)
      .use {
        background = AppCompatResources.getDrawable(context, R.drawable.chip_background)
        disabled = it.getBoolean(R.styleable.BpkChip_disabled, false)
        isSelected = it.getBoolean(R.styleable.BpkChip_selected, false)
        chipBackgroundColor = it.getColor(R.styleable.BpkChip_chipBackgroundColor, chipBackgroundColor)
        selectedBackgroundColor = it.getColor(R.styleable.BpkChip_chipSelectedBackgroundColor, selectedBackgroundColor)

        val iconId = it.getResourceId(R.styleable.BpkChip_chipIcon, 0)
        if (iconId != 0) {
          icon = AppCompatResources.getDrawable(context, iconId)
        }
      }

    updateBackground()
  }

  fun toggle() {
    if (!disabled) {
      isSelected = !isSelected
    }
  }

  internal open fun updateBackground() {
    val backgroundTintList = ColorStateList(
      arrayOf(
        intArrayOf(android.R.attr.state_enabled, android.R.attr.state_selected),
        intArrayOf(android.R.attr.state_enabled),
        intArrayOf(-android.R.attr.state_enabled),
      ),
      intArrayOf(
        selectedBackgroundColor,
        chipBackgroundColor,
        chipBackgroundColor,
      )
    )
    ViewCompat.setBackgroundTintList(this, backgroundTintList)
  }
}
