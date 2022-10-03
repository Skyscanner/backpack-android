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

package net.skyscanner.backpack.chip

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.Gravity
import androidx.appcompat.content.res.AppCompatResources
import net.skyscanner.backpack.R
import net.skyscanner.backpack.chip.internal.BpkChipAppearance
import net.skyscanner.backpack.chip.internal.BpkChipAppearances
import net.skyscanner.backpack.text.BpkText
import net.skyscanner.backpack.util.createContextThemeWrapper
import net.skyscanner.backpack.util.use

open class BpkChip @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0,
) : BpkText(createContextThemeWrapper(context, attrs, R.attr.bpkChipStyle), attrs, defStyleAttr) {

  private val iconPadding = context.resources.getDimensionPixelSize(R.dimen.bpkSpacingMd)
  private val iconSize = context.resources.getDimensionPixelSize(R.dimen.bpk_icon_size_small)

  var disabled: Boolean = false
    set(value) {
      field = value
      this.isEnabled = !disabled
    }

  private var appearance: BpkChipAppearance

  var style: Style
    get() = appearance.style
    set(value) {
      appearance = BpkChipAppearances.Solid.fromTheme(context, value)
      updateStyle()
    }

  var type: Type = Type.Option
    set(value) {
      field = value
      updateIcons()
    }

  var icon: Drawable? = null
    set(value) {
      field = value
        ?.mutate()
        ?.apply {
          setBounds(0, 0, iconSize, iconSize)
          setTintList(textColors)
        }
      updateIcons()
    }

  init {
    this.appearance = provideAppearance(this.context, attrs, defStyleAttr)
    this.compoundDrawablePadding = iconPadding
    this.gravity = Gravity.CENTER_VERTICAL
    this.textStyle = TextStyle.Footnote
    this.setTextColor(appearance.text)
    this.isSingleLine = true
    this.height = resources.getDimensionPixelSize(R.dimen.bpk_chip_height)

    setPadding(
      resources.getDimensionPixelSize(R.dimen.bpkSpacingBase),
      0,
      resources.getDimensionPixelSize(R.dimen.bpkSpacingBase),
      0
    )
    initialize(attrs, defStyleAttr)
  }

  private fun initialize(attrs: AttributeSet?, defStyleAttr: Int) {
    context.theme.obtainStyledAttributes(attrs, R.styleable.BpkChip, defStyleAttr, 0)
      .use {
        disabled = it.getBoolean(R.styleable.BpkChip_disabled, false)
        isSelected = it.getBoolean(R.styleable.BpkChip_selected, false)
        val iconId = it.getResourceId(R.styleable.BpkChip_chipIcon, 0)
        if (iconId != 0) {
          icon = AppCompatResources.getDrawable(context, iconId)
        }
        type = Type.fromAttr(it.getInt(R.styleable.BpkChip_chipType, 0))
      }

    updateStyle()
  }

  fun toggle() {
    if (!disabled) {
      isSelected = !isSelected
    }
  }

  internal open fun provideAppearance(context: Context, attrs: AttributeSet?, defStyleAttr: Int): BpkChipAppearance =
    BpkChipAppearances.Solid.fromAttrs(context, attrs, defStyleAttr)

  private fun updateStyle() {
    this.background = appearance.background
    setTextColor(appearance.text)
    compoundDrawableTintList = appearance.text
  }

  private fun updateIcons() {
    val endIcon = when (type) {
      Type.Option -> null
      Type.Select -> AppCompatResources.getDrawable(context, R.drawable.bpk_tick)
      Type.Dismiss -> AppCompatResources.getDrawable(context, R.drawable.bpk_close_circle)
    }?.mutate()
      ?.apply {
        setBounds(0, 0, iconSize, iconSize)
        setTintList(textColors)
      }
    this.setCompoundDrawablesRelative(icon, null, endIcon, null)
  }

  enum class Style {
    Default,
    OnDark,
    ;

    companion object {
      internal fun fromAttr(value: Int): Style =
        when (value) {
          0 -> Default
          1 -> OnDark
          else -> throw IllegalStateException("Unknown chip style")
        }
    }
  }

  enum class Type {
    Option,
    Select,
    Dismiss,
    ;

    companion object {
      internal fun fromAttr(value: Int): Type =
        when (value) {
          0 -> Option
          1 -> Select
          2 -> Dismiss
          else -> throw IllegalStateException("Unknown chip type")
        }
    }
  }
}
