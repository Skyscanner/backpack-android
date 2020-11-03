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

package net.skyscanner.backpack.button.internal

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.graphics.drawable.LayerDrawable
import android.os.Build
import android.text.TextUtils
import android.util.AttributeSet
import android.view.Gravity
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.AppCompatButton
import androidx.core.graphics.drawable.DrawableCompat
import kotlin.math.max
import kotlin.math.min
import net.skyscanner.backpack.R
import net.skyscanner.backpack.text.BpkText
import net.skyscanner.backpack.util.use
import net.skyscanner.backpack.util.wrapContextWithDefaults

private const val ICON_POSITION_START = 0
private const val ICON_POSITION_END = 1
private const val ICON_POSITION_ICON_ONLY = 2

internal const val INVALID_RES = -1

abstract class BpkButtonBase internal constructor(
  context: Context,
  attrs: AttributeSet?,
  defStyleAttr: Int
) : AppCompatButton(wrapContextWithDefaults(context), attrs, defStyleAttr) {

  private var isInitialized = false
  internal val tokens = ButtonTokens(this.context)

  private val font = BpkText.getFont(this.context, BpkText.SM, BpkText.Weight.EMPHASIZED)

  private var minPaddingStart = tokens.bpkSpacingBase - tokens.bpkSpacingSm

  private var minPaddingEnd = tokens.bpkSpacingBase - tokens.bpkSpacingSm

  private var maxIconSpacing = tokens.bpkSpacingSm

  private var _iconPosition: Int = ICON_POSITION_END
  open var iconPosition: Int
    get() = _iconPosition
    set(value) {
      _iconPosition = value
      updateIcon()
      update()
    }

  private var _icon: Drawable? = null
  open var icon: Drawable? = null
    get() = _icon
    set(value) {
      if (value != field) {
        field = value
        _icon = value?.let(::adjustDrawableSize)
        updateIcon()
        update()
      }
    }

  init {
    maxLines = 1
    isAllCaps = true
    gravity = Gravity.CENTER
    isClickable = isEnabled

    this.context.theme.obtainStyledAttributes(attrs, R.styleable.BpkButton, defStyleAttr, 0)
      .use {
        _iconPosition = it.getInt(R.styleable.BpkButton_buttonIconPosition, ICON_POSITION_END)
        it.getResourceId(R.styleable.BpkButton_buttonIcon, INVALID_RES).let { res ->
          if (res != INVALID_RES) {
            _icon = AppCompatResources.getDrawable(context, res)?.let(::adjustDrawableSize)
          }
        }
      }

    font.applyTo(this)
    updateIcon()
    isInitialized = true
    ellipsize = TextUtils.TruncateAt.END
    super.setCompoundDrawablePadding(maxIconSpacing)
    super.setPaddingRelative(minPaddingStart, paddingTop, minPaddingEnd, paddingBottom)
  }

  protected abstract fun update()

  override fun setEnabled(enabled: Boolean) {
    if (enabled != isEnabled) {
      super.setEnabled(enabled)

      isClickable = isEnabled

      if (isInitialized) {
        updateIcon()
        update()
      }
    }
  }

  override fun setTextColor(color: Int) {
    super.setTextColor(color)
    updateIcon()
  }

  override fun setTextColor(colors: ColorStateList?) {
    super.setTextColor(colors)
    updateIcon()
  }

  override fun setCompoundDrawablesWithIntrinsicBounds(
    @DrawableRes left: Int,
    @DrawableRes top: Int,
    @DrawableRes right: Int,
    @DrawableRes bottom: Int
  ) {
    super.setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom)
    layoutComponents()
  }

  override fun setCompoundDrawablesWithIntrinsicBounds(
    left: Drawable?,
    top: Drawable?,
    right: Drawable?,
    bottom: Drawable?
  ) {
    super.setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom)
    layoutComponents()
  }

  override fun setText(text: CharSequence, type: TextView.BufferType) {
    super.setText(text, type)
    layoutComponents()
  }

  override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
    super.onSizeChanged(w, h, oldw, oldh)
    layoutComponents()
  }

  override fun setPadding(left: Int, top: Int, right: Int, bottom: Int) {
    super.setPadding(left, top, right, bottom)
    minPaddingStart = left
    minPaddingEnd = right
    layoutComponents()
  }

  override fun setPaddingRelative(start: Int, top: Int, end: Int, bottom: Int) {
    super.setPaddingRelative(start, top, end, bottom)
    minPaddingStart = start
    minPaddingEnd = end
    layoutComponents()
  }

  override fun setCompoundDrawablePadding(pad: Int) {
    maxIconSpacing = pad
    layoutComponents()
  }

  private val actualWidth: Int
    get() {
      val layoutWidth = width
      if (layoutWidth != 0) {
        return layoutWidth
      }
      return measuredWidth
    }

  private fun layoutComponents() {
    val width = actualWidth
    if (width == 0) {
      return
    }

    val iconWidth = icon?.intrinsicWidth ?: 0
    val iconSpacing = if (icon != null && text.isNotEmpty()) maxIconSpacing else 0
    val maxLineWidth = max(0, width - minPaddingStart - minPaddingEnd - iconWidth - iconSpacing)
    val textToMeasure = transformationMethod?.getTransformation(text, this) ?: text
    val totalTextWidth = paint.measureText(textToMeasure, 0, length()).toInt()
    val singleLineTextWidth = min(maxLineWidth, totalTextWidth)
    val padding = (width - singleLineTextWidth - iconWidth - iconSpacing) / 2

    super.setCompoundDrawablePadding(iconSpacing)
    super.setPaddingRelative(padding, paddingTop, padding, paddingBottom)
  }

  private fun updateIcon() {
    val icon = _icon
    if (icon != null) {
      DrawableCompat.setTintList(icon, textColors)
      this.setCompoundDrawablesRelativeWithIntrinsicBounds(
        icon.takeIf { iconPosition == ICON_POSITION_START || iconPosition == ICON_POSITION_ICON_ONLY },
        null,
        icon.takeIf { iconPosition == ICON_POSITION_END },
        null
      )
    } else {
      this.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, null)
    }
  }

  private fun adjustDrawableSize(drawable: Drawable) = object : LayerDrawable(
    arrayOf(drawable)
  ) {

    init {
      setBounds(0, 0, intrinsicWidth, intrinsicHeight)
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        setLayerSize(0, intrinsicWidth, intrinsicHeight)
      }
    }

    override fun getIntrinsicWidth(): Int =
      tokens.bpkSpacingBase

    override fun getIntrinsicHeight(): Int =
      tokens.bpkSpacingBase
  }
}
