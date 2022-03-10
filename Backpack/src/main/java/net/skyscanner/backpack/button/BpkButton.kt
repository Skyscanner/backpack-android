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

package net.skyscanner.backpack.button

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.annotation.Dimension
import androidx.annotation.IntDef
import androidx.appcompat.content.res.AppCompatResources
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import net.skyscanner.backpack.R
import net.skyscanner.backpack.button.internal.BpkButtonBase
import net.skyscanner.backpack.button.internal.ButtonStyle
import net.skyscanner.backpack.button.internal.ButtonStyles
import net.skyscanner.backpack.button.internal.ICON_POSITION_END
import net.skyscanner.backpack.button.internal.ICON_POSITION_ICON_ONLY
import net.skyscanner.backpack.button.internal.ICON_POSITION_START
import net.skyscanner.backpack.text.BpkText
import net.skyscanner.backpack.util.unsafeLazy
import net.skyscanner.backpack.util.use
import kotlin.math.roundToInt

open class BpkButton(
  context: Context,
  attrs: AttributeSet?,
  defStyleAttr: Int,
  type: Type,
  private val size: BpkButtonSize = BpkButtonSize.Standard,
) : BpkButtonBase(context, attrs, defStyleAttr) {

  constructor(
    context: Context,
  ) : this(context, null, 0, Type.Primary, BpkButtonSize.Standard)

  constructor(
    context: Context,
    type: Type,
    size: BpkButtonSize = BpkButtonSize.Standard,
  ) : this(context, null, 0, type, size)

  constructor(
    context: Context,
    attrs: AttributeSet?,
  ) : this(context, attrs, 0, getButtonType(context, attrs), getButtonSize(context, attrs))

  constructor(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttr: Int,
  ) : this(context, attrs, defStyleAttr, Type.Primary, BpkButtonSize.Standard)

  companion object {
    const val START = ICON_POSITION_START
    const val END = ICON_POSITION_END
    const val ICON_ONLY = ICON_POSITION_ICON_ONLY
  }

  @IntDef(START, END, ICON_ONLY)
  annotation class IconPosition

  @BpkButton.IconPosition
  override var iconPosition
    get() = iconDrawablePosition
    set(value) {
      iconDrawablePosition = value
      var paddingHorizontal = paddingHorizontal

      if (value == ICON_ONLY) {
        val requiredWidth = resources.getDimension(size.minHeight)
        val iconSize = resources.getDimension(size.iconSize)
        paddingHorizontal = (requiredWidth - iconSize) / 2
        iconPadding = 0
      } else {
        iconPadding = resources.getDimensionPixelSize(size.horizontalSpacing)
      }

      setPaddingRelative(paddingHorizontal.roundToInt(), 0, paddingHorizontal.roundToInt(), 0)
      applyStyle(type.createStyle(context))
    }

  private var iconDrawable: Drawable? = icon
    set(value) {
      field = value
      updateIconState()
    }

  @Dimension
  private val paddingHorizontal = resources.getDimension(size.horizontalPadding)

  override fun setIcon(icon: Drawable?) {
    super.setIcon(icon)
    iconDrawable = icon
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
      if (this::style.isInitialized) {
        applyStyle(style)
      }
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
    var icon = getIcon()
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
    this.minHeight = resources.getDimensionPixelSize(size.minHeight)
    this.iconSize = resources.getDimensionPixelSize(size.iconSize)
    BpkText.getFont(context, size.textStyle).applyTo(this)
    applyStyle(style)
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
    background = style.getButtonBackground(isEnabled)
    setTextColor(style.getContentColor())
  }

  private fun updateIconState() {
    super.setIcon(
      if (loading) {
        val disabledColour = textColors.getColorForState(intArrayOf(-android.R.attr.state_enabled), textColors.defaultColor)
        progress.setColorSchemeColors(disabledColour)
        progress
      } else {
        iconDrawable
      }
    )
    iconTint = textColors
  }

  enum class Type(
    internal val id: Int,
    internal val createStyle: (Context) -> ButtonStyle,
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
    PrimaryOnDark(
      id = 4,
      createStyle = ButtonStyles.PrimaryOnDark,
    ),
    PrimaryOnLight(
      id = 5,
      createStyle = ButtonStyles.PrimaryOnLight,
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

private fun getButtonSize(context: Context, attrs: AttributeSet?): BpkButtonSize {
  val attr = context.theme.obtainStyledAttributes(attrs, R.styleable.BpkButton, 0, 0)
  return BpkButtonSize.fromId(attr.getInt(R.styleable.BpkButton_bpkButtonSize, 0))
}
