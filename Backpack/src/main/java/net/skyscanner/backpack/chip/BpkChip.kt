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

  private var appearance: BpkChipAppearance

  var style: Style
    get() = appearance.style
    set(value) {
      appearance = BpkChipAppearances.fromTheme(context, value)
      updateStyle()
    }

  var type: Type = Type.Option
    set(value) {
      field = value
      updateStyle()
      updateIcons()
    }

  var icon: Drawable? = null
    set(value) {
      field = value
        ?.mutate()
        ?.apply {
          setBounds(0, 0, iconSize, iconSize)
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

    initialize(attrs, defStyleAttr)
  }

  private fun initialize(attrs: AttributeSet?, defStyleAttr: Int) {
    context.theme.obtainStyledAttributes(attrs, R.styleable.BpkChip, defStyleAttr, 0)
      .use {
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
    if (isEnabled) {
      isSelected = !isSelected
    }
  }

  internal open fun provideAppearance(context: Context, attrs: AttributeSet?, defStyleAttr: Int): BpkChipAppearance =
    BpkChipAppearances.fromAttrs(context, attrs, defStyleAttr)

  private fun updateStyle() {
    elevation = if (style == Style.OnImage) resources.getDimension(R.dimen.bpkElevationSm) else 0f
    if (type == Type.Dismiss) {
      setTextColor(appearance.dismissibleText)
      this.background = appearance.dismissibleBackground
    } else {
      setTextColor(appearance.text)
      this.background = appearance.background
    }
    updateIcons()
  }

  private fun updateIcons() {
    val endIcon = when (type) {
      Type.Option -> null
      Type.Dropdown -> AppCompatResources.getDrawable(context, R.drawable.bpk_chevron_down)
      Type.Dismiss -> AppCompatResources.getDrawable(context, R.drawable.bpk_close_circle)
    }?.mutate()
      ?.apply {
        setBounds(0, 0, iconSize, iconSize)
        if (type == Type.Dismiss) {
          setTintList(appearance.dismissibleIcon)
        } else {
          setTintList(appearance.text)
        }
      }
    val startIcon = icon?.apply {
      if (type == Type.Dismiss) {
        setTintList(appearance.dismissibleText)
      } else {
        setTintList(appearance.text)
      }
    }
    this.setCompoundDrawablesRelative(startIcon, null, endIcon, null)

    setPadding(
      resources.getDimensionPixelSize(R.dimen.bpkSpacingBase),
      0,
      resources.getDimensionPixelSize(if (type == Type.Option) R.dimen.bpkSpacingBase else R.dimen.bpkSpacingMd),
      0,
    )
  }

  enum class Style {
    Default,
    OnDark,
    OnImage,
    ;

    companion object {
      internal fun fromAttr(value: Int): Style =
        when (value) {
          0 -> Default
          1 -> OnDark
          2 -> OnImage
          else -> throw IllegalStateException("Unknown chip style")
        }
    }
  }

  enum class Type {
    Option,
    Dropdown,
    Dismiss,
    ;

    companion object {
      internal fun fromAttr(value: Int): Type =
        when (value) {
          0 -> Option
          1 -> Dropdown
          2 -> Dismiss
          else -> throw IllegalStateException("Unknown chip type")
        }
    }
  }
}
