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
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.AttributeSet
import androidx.annotation.Dimension
import androidx.annotation.IntDef
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import net.skyscanner.backpack.R
import net.skyscanner.backpack.button.internal.*
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
    const val START = ICON_POSITION_START
    const val END = ICON_POSITION_END
    const val ICON_ONLY = ICON_POSITION_ICON_ONLY
  }

  @IntDef(START, END, ICON_ONLY)
  annotation class IconPosition

  @BpkButton.IconPosition
  override var iconPosition
    get() = super.iconPosition
    set(value) {
      super.iconPosition = value
    }

  @Dimension
  private val paddingHorizontal = tokens.bpkSpacingBase - tokens.bpkSpacingSm

  @Dimension
  private val paddingVertical = tokens.bpkSpacingMd + (tokens.bpkBorderSizeLg / 2)

  private var _icon: Drawable? = super.icon
  final override var icon: Drawable?
    get() = super.icon
    set(value) {
      if (_icon != value) {
        _icon = value
        super.icon = value
      }
    }

  private var _progress: CircularProgressDrawable? = null
  private val progress: CircularProgressDrawable
    get() {
      if (_progress == null) {
        _progress = CircularProgressDrawable(context).apply {
          setStyle(CircularProgressDrawable.DEFAULT)
          centerRadius = resources.getDimension(R.dimen.bpkSpacingSm) * 1.3f
          strokeWidth = resources.getDimension(R.dimen.bpkSpacingSm) * 0.5f
          start()
        }
      }
      return _progress!!
    }

  private var _loading = false
  var loading: Boolean = false
    get() = _loading
    set(value) {
      _loading = value
      if (_loading != field) {
        field = value
        update()
      }
    }

  var type: Type = type
    set(value) {
      field = value
      applyStyle(type.createStyle(context))
      update()
    }

  init {
    var type = type
    var style: ButtonStyle = type.createStyle(context)

    context.theme.obtainStyledAttributes(attrs, R.styleable.BpkButton, defStyleAttr, 0)
      .use {
        if (it.hasValue(R.styleable.BpkButton_buttonType)) {
          type = Type.fromId(it.getInt(R.styleable.BpkButton_buttonType, 0))
          style = type.createStyle(context)
        }

        style = ButtonStyle.fromAttributes(context, it, style)
        _loading = it.getBoolean(R.styleable.BpkButton_buttonLoading, _loading)
      }

    this.clipToOutline = true
    this.type = type
    applyStyle(style)
  }

  override fun update() {
    if (iconPosition == ICON_ONLY) {
      text = ""
    }

    var paddingHorizontal = paddingHorizontal
    val paddingVertical = paddingVertical

    if (iconPosition == ICON_ONLY) {
      paddingHorizontal = tokens.bpkSpacingMd
    }

    setPadding(paddingHorizontal, paddingVertical, paddingHorizontal, paddingVertical)

    if (!text.isNullOrEmpty()) {
      compoundDrawablePadding = tokens.bpkSpacingSm
    }

    if (loading) {
      val disabledColour = textColors.getColorForState(intArrayOf(-android.R.attr.state_enabled), textColors.defaultColor)
      progress.setColorSchemeColors(disabledColour)
      super.icon = progress
      super.setEnabled(false)
    } else {
      super.icon = _icon
      super.setEnabled(enabled != false)
    }
  }

  private var enabled: Boolean? = null

  override fun setEnabled(enabled: Boolean) {
    // we want to store the enabling state set
    // by the used in order to recover to it when loading is set to false.
    // the null values used to detect the initialization
    if (enabled != isEnabled) {
      this.enabled = enabled
      super.setEnabled(enabled)
    }
  }

  private fun applyStyle(style: ButtonStyle) {
    background = style.getButtonBackground(iconPosition)
    setTextColor(style.contentColor)

    if (isEnabled && isStateListAnimatorSupported()) {
      this.stateListAnimator = style.getStateListAnimator()
    } else {
      this.stateListAnimator = null
    }
    update()
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
