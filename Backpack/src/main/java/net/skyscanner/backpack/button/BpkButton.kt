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
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.annotation.Dimension
import androidx.annotation.IntDef
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.graphics.withTranslation
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import net.skyscanner.backpack.R
import net.skyscanner.backpack.button.internal.BpkButtonBase
import net.skyscanner.backpack.button.internal.ButtonStyle
import net.skyscanner.backpack.button.internal.ICON_POSITION_END
import net.skyscanner.backpack.button.internal.ICON_POSITION_ICON_ONLY
import net.skyscanner.backpack.button.internal.ICON_POSITION_START
import net.skyscanner.backpack.button.internal.createStyle
import net.skyscanner.backpack.button.internal.fromAttrs
import net.skyscanner.backpack.button.internal.fromId
import net.skyscanner.backpack.button.internal.horizontalPadding
import net.skyscanner.backpack.button.internal.horizontalSpacing
import net.skyscanner.backpack.button.internal.iconSize
import net.skyscanner.backpack.button.internal.minHeight
import net.skyscanner.backpack.button.internal.textStyle
import net.skyscanner.backpack.text.BpkText
import net.skyscanner.backpack.util.unsafeLazy
import net.skyscanner.backpack.util.use
import kotlin.math.roundToInt

open class BpkButton(
  context: Context,
  attrs: AttributeSet?,
  defStyleAttr: Int,
  type: Type,
  size: Size = Size.Standard,
) : BpkButtonBase(context, attrs, defStyleAttr) {

  constructor(
    context: Context,
  ) : this(context, null, 0, Type.Primary, Size.Standard)

  constructor(
    context: Context,
    type: Type,
    size: Size = Size.Standard,
  ) : this(context, null, 0, type, size)

  constructor(
    context: Context,
    attrs: AttributeSet?,
  ) : this(context, attrs, 0, Type.fromAttrs(context, attrs), Size.fromAttrs(context, attrs))

  constructor(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttr: Int,
  ) : this(context, attrs, defStyleAttr, Type.Primary, Size.Standard)

  @IntDef(START, END, ICON_ONLY)
  annotation class IconPosition

  @BpkButton.IconPosition
  override var iconPosition
    get() = iconDrawablePosition
    set(value) {
      iconDrawablePosition = value
      applyStyle(type.createStyle(context))
    }

  @Dimension
  private val paddingHorizontal = resources.getDimension(size.horizontalPadding)

  private val progress by unsafeLazy {
    CircularProgressDrawable(context).apply {
      setStyle(CircularProgressDrawable.DEFAULT)
      strokeWidth = resources.getDimension(R.dimen.bpkSpacingSm) * 0.5f
      callback = this@BpkButton
    }
  }

  var loading: Boolean = false
    set(value) {
      field = value

      if (value) {
        progress.start()
      } else {
        progress.stop()
      }

      updateEnabledState()
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

  var size: Size = size
    set(value) {
      field = value
      updateSize()
    }

  private var enabled = isEnabled

  init {
    var style: ButtonStyle = type.createStyle(context)

    context.theme.obtainStyledAttributes(attrs, R.styleable.BpkButton, defStyleAttr, 0)
      .use {
        if (it.hasValue(R.styleable.BpkButton_buttonType)) {
          this.type = Type.fromId(it.getInt(R.styleable.BpkButton_buttonType, 0))
          style = this.type.createStyle(context)
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
    updateSize()
    applyStyle(style)
  }

  override fun setEnabled(enabled: Boolean) {
    this.enabled = enabled
    updateEnabledState()
  }

  override fun invalidateDrawable(drawable: Drawable) {
    super.invalidateDrawable(drawable)
    if (loading && drawable == progress) {
      invalidate()
    }
  }

  override fun verifyDrawable(who: Drawable): Boolean =
    super.verifyDrawable(who) || (loading && who == progress)

  override fun onDraw(canvas: Canvas) {
    super.onDraw(canvas)
    if (loading) {
      canvas.withTranslation(
        x = (width - progress.bounds.width()) / 2f,
        y = (height - progress.bounds.height()) / 2f,
      ) {
        progress.draw(canvas)
      }
    }
  }

  private fun updateEnabledState() {
    super.setEnabled(enabled && !loading)
    if (this::style.isInitialized) {
      applyStyle(style)
    }
  }

  private fun applyStyle(style: ButtonStyle) {
    this.style = style
    background = style.getButtonBackground(enabled = isEnabled, loading = loading)

    if (loading) {
      setTextColor(Color.TRANSPARENT)
    } else {
      setTextColor(style.getContentColor())
    }
    this.progress.setColorSchemeColors(style.getLoadingColor())

    var paddingHorizontal = paddingHorizontal

    if (iconDrawablePosition == ICON_ONLY) {
      val requiredWidth = resources.getDimension(size.minHeight)
      val iconSize = resources.getDimension(size.iconSize)
      paddingHorizontal = (requiredWidth - iconSize) / 2
      iconPadding = 0
    } else {
      iconPadding = resources.getDimensionPixelSize(size.horizontalSpacing)
      if (type == Type.Link || type == Type.LinkOnDark) {
        paddingHorizontal = 0f
      }
    }

    setPaddingRelative(paddingHorizontal.roundToInt(), 0, paddingHorizontal.roundToInt(), 0)
  }

  private fun updateSize() {
    minHeight = resources.getDimensionPixelSize(size.minHeight)
    iconSize = resources.getDimensionPixelSize(size.iconSize)
    progress.centerRadius = iconSize / 2f - progress.strokeWidth
    BpkText.getFont(context, size.textStyle).applyTo(this)
  }

  enum class Type {
    Primary,
    Secondary,
    Featured,
    Destructive,
    PrimaryOnDark,
    PrimaryOnLight,
    SecondaryOnDark,
    Link,
    LinkOnDark,
    ;

    internal companion object
  }

  enum class Size {
    Standard,
    Large,
    ;

    internal companion object
  }

  companion object {
    const val START = ICON_POSITION_START
    const val END = ICON_POSITION_END
    const val ICON_ONLY = ICON_POSITION_ICON_ONLY
  }
}
