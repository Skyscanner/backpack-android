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
import androidx.annotation.IntDef
import androidx.appcompat.content.res.AppCompatResources
import net.skyscanner.backpack.R
import net.skyscanner.backpack.button.internal.BpkButtonBase
import net.skyscanner.backpack.button.internal.ButtonStyles
import net.skyscanner.backpack.button.internal.ICON_POSITION_END
import net.skyscanner.backpack.button.internal.ICON_POSITION_START
import net.skyscanner.backpack.button.internal.fromAttrs
import net.skyscanner.backpack.button.internal.iconSize
import net.skyscanner.backpack.button.internal.minHeight
import net.skyscanner.backpack.button.internal.textStyle
import net.skyscanner.backpack.text.BpkText
import net.skyscanner.backpack.util.createContextThemeWrapper
import net.skyscanner.backpack.util.use

open class BpkButtonLink(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0,
  size: BpkButton.Size = BpkButton.Size.Standard,
) : BpkButtonBase(
  createContextThemeWrapper(context, attrs, R.attr.bpkButtonLinkStyle),
  attrs,
  defStyleAttr
) {

  constructor(
    context: Context,
  ) : this(context, null, 0, BpkButton.Size.Standard)

  constructor(
    context: Context,
    size: BpkButton.Size = BpkButton.Size.Standard,
  ) : this(context, null, 0, size)

  constructor(
    context: Context,
    attrs: AttributeSet?,
  ) : this(context, attrs, 0, BpkButton.Size.fromAttrs(context, attrs))

  constructor(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttr: Int,
  ) : this(context, attrs, defStyleAttr, BpkButton.Size.Standard)

  companion object {
    const val START = ICON_POSITION_START
    const val END = ICON_POSITION_END
  }

  @IntDef(START, END)
  annotation class IconPosition

  @BpkButtonLink.IconPosition
  override var iconPosition
    get() = iconDrawablePosition
    set(value) {
      iconDrawablePosition = value
    }

  var uppercase: Boolean = true
    set(value) {
      field = value
    }

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
    this.icon = icon

    var uppercase = true
    this.context.theme.obtainStyledAttributes(attrs, R.styleable.BpkButtonLink, defStyleAttr, 0)
      .use {
        uppercase = it.getBoolean(R.styleable.BpkButtonLink_uppercase, true)
      }
    this.uppercase = uppercase

    iconPadding = resources.getDimensionPixelSize(R.dimen.bpkSpacingMd)

    val style = ButtonStyles.Link(context)
    background = style.getButtonBackground(isEnabled)
    setTextColor(style.getContentColor())

    val paddingVertical = resources.getDimensionPixelSize(R.dimen.bpkSpacingMd) +
      (resources.getDimensionPixelSize(R.dimen.bpkBorderSizeLg) / 2)

    setPadding(0, paddingVertical, 0, paddingVertical)

    this.minHeight = resources.getDimensionPixelSize(size.minHeight)
    this.iconSize = resources.getDimensionPixelSize(size.iconSize)
    BpkText.getFont(context, size.textStyle).applyTo(this)
  }
}
