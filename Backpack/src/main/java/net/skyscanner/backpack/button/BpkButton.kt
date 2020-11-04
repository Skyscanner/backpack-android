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
import android.os.Build
import android.util.AttributeSet
import androidx.annotation.Dimension
import androidx.appcompat.content.res.AppCompatResources
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import net.skyscanner.backpack.R
import net.skyscanner.backpack.button.internal.BpkButtonBase
import net.skyscanner.backpack.button.internal.ButtonStyle
import net.skyscanner.backpack.button.internal.ButtonStyles
import net.skyscanner.backpack.util.unsafeLazy
import net.skyscanner.backpack.util.use

open class BpkButton(
  context: Context,
  attrs: AttributeSet?,
  defStyleAttr: Int,
  type: Type
) : BpkButtonBase(context, attrs, defStyleAttr) {

  constructor(context: Context) : this(context, null, 0, Type.Primary)

  constructor(context: Context, type: Type) : this(context, null, 0, type)

  constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0, getButtonType(context, attrs))

  constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : this(context, attrs, defStyleAttr, Type.Primary)

  companion object {
    const val START = 0
    const val END = 1
    const val ICON_ONLY = 2
  }

  @Dimension
  private val paddingHorizontal = resources.getDimensionPixelSize(R.dimen.bpkSpacingBase) -
    resources.getDimensionPixelSize(R.dimen.bpkSpacingSm)

  @Dimension
  private val paddingVertical = resources.getDimensionPixelSize(R.dimen.bpkSpacingMd) +
    (resources.getDimensionPixelSize(R.dimen.bpkBorderSizeLg) / 2)

  final override var iconPosition: Int
    get() = super.iconDrawablePosition
    set(value) {
      super.iconDrawablePosition = value
      var paddingHorizontal = paddingHorizontal
      val paddingVertical = paddingVertical

      if (iconPosition == ICON_ONLY) {
        paddingHorizontal = resources.getDimensionPixelSize(R.dimen.bpkSpacingMd)
      } else {
        compoundDrawablePadding = resources.getDimensionPixelSize(R.dimen.bpkSpacingSm)
      }

      setPadding(paddingHorizontal, paddingVertical, paddingHorizontal, paddingVertical)
    }

  final override var icon: Drawable? = null
    set(value) {
      field = value
      updateIconState()
    }

  private val progress by unsafeLazy {
    CircularProgressDrawable(context).apply {
      setStyle(CircularProgressDrawable.DEFAULT)
      centerRadius = resources.getDimension(R.dimen.bpkSpacingSm) * 1.3f
      strokeWidth = resources.getDimension(R.dimen.bpkSpacingSm) * 0.5f
      start()
    }
  }

  var loading: Boolean = false
    set(value) {
      field = value
      updateEnabledState()
      updateIconState()
    }

  private lateinit var style: ButtonStyle

  var type: Type = type
    set(value) {
      field = value
      applyStyle(type.createStyle(context))
    }

  private var enabled = isEnabled

  init {
    var type = type
    var style: ButtonStyle = type.createStyle(context)
    var loading = loading
    var icon = iconDrawable
    var iconPosition = iconPosition

    context.theme.obtainStyledAttributes(attrs, R.styleable.BpkButton, defStyleAttr, 0)
      .use {
        if (it.hasValue(R.styleable.BpkButton_buttonType)) {
          type = Type.fromId(it.getInt(R.styleable.BpkButton_buttonType, 0))
          style = type.createStyle(context)
        }

        style = ButtonStyle.fromAttributes(context, it, style)
        loading = it.getBoolean(R.styleable.BpkButton_buttonLoading, loading)
        iconPosition = it.getInt(R.styleable.BpkButton_buttonIconPosition, iconPosition)
        it.getResourceId(R.styleable.BpkButton_buttonIcon, 0).let { res ->
          if (res != 0) {
            icon = AppCompatResources.getDrawable(context, res)
          }
        }
      }

    this.clipToOutline = true
    this.type = type
    this.loading = loading
    this.icon = icon
    this.iconPosition = iconPosition
    applyStyle(style)
  }

  override fun setTextColor(color: Int) {
    super.setTextColor(color)
    icon?.setTintList(ColorStateList.valueOf(color))
  }

  override fun setTextColor(colors: ColorStateList) {
    super.setTextColor(colors)
    icon?.setTintList(colors)
  }

  override fun setEnabled(enabled: Boolean) {
    this.enabled = enabled
    updateEnabledState()
  }
  private fun updateEnabledState() {
    super.setEnabled(enabled && !loading)
    if (this::style.isInitialized) {
      applyStyle(style)
    }
  }

  private fun applyStyle(style: ButtonStyle) {
    this.style = style
    background = style.getButtonBackground(isEnabled, iconPosition)
    setTextColor(style.contentColor)

    if (isEnabled && isStateListAnimatorSupported()) {
      this.stateListAnimator = style.getStateListAnimator()
    } else {
      this.stateListAnimator = null
    }
  }

  private fun updateIconState() {
    iconDrawable = if (loading) {
      val disabledColour = textColors.getColorForState(intArrayOf(-android.R.attr.state_enabled), textColors.defaultColor)
      progress.setColorSchemeColors(disabledColour)
      progress
    } else {
      icon?.setTintList(textColors)
      icon
    }
  }

  enum class Type(
    internal val id: Int,
    internal val createStyle: (Context) -> ButtonStyle
  ) {
    Primary(
      id = 0,
      createStyle = ButtonStyles.Primary
    ),
    Secondary(
      id = 1,
      createStyle = ButtonStyles.Secondary
    ),
    Featured(
      id = 2,
      createStyle = ButtonStyles.Featured
    ),
    Destructive(
      id = 3,
      createStyle = ButtonStyles.Destructive
    ),
    Outline(
      id = 4,
      createStyle = ButtonStyles.Outline
    );

    internal companion object {
      internal fun fromId(id: Int): Type {
        for (f in values()) {
          if (f.id == id) return f
        }
        throw IllegalArgumentException()
      }
    }
  }
}

private fun getButtonType(context: Context, attrs: AttributeSet?): BpkButton.Type {
  val attr = context.theme.obtainStyledAttributes(attrs, R.styleable.BpkButton, 0, 0)
  return BpkButton.Type.fromId(attr.getInt(R.styleable.BpkButton_buttonType, 0))
}

private fun isStateListAnimatorSupported() =
  Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1 && !isSpecificDeviceBlackListed()

private fun isSpecificDeviceBlackListed() =
  Build.MANUFACTURER.equals("samsung", true) && Build.MODEL.equals("gt-i9505", true)
