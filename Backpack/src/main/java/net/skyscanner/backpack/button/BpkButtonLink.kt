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
import android.util.AttributeSet
import androidx.annotation.IntDef
import net.skyscanner.backpack.R
import net.skyscanner.backpack.button.internal.*
import net.skyscanner.backpack.button.internal.ICON_POSITION_END
import net.skyscanner.backpack.button.internal.ICON_POSITION_START
import net.skyscanner.backpack.util.createContextThemeWrapper
import net.skyscanner.backpack.util.use

open class BpkButtonLink @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
) : BpkButtonBase(
  createContextThemeWrapper(context, attrs, R.attr.bpkButtonLinkStyle),
  attrs,
  defStyleAttr
) {

  companion object {
    const val START = ICON_POSITION_START
    const val END = ICON_POSITION_END
  }

  @IntDef(START, END)
  annotation class IconPosition

  @BpkButtonLink.IconPosition
  override var iconPosition
    get() = super.iconPosition
    set(value) {
      super.iconPosition = value
    }

  private var _uppercase = true
  var uppercase: Boolean
    get() = _uppercase
    set(value) {
      _uppercase = value
      update()
    }

  final override var icon: Drawable?
    get() = super.icon
    set(value) {
      super.icon = value
    }

  init {

    compoundDrawablePadding = tokens.bpkSpacingSm

    this.context.theme.obtainStyledAttributes(attrs, R.styleable.BpkButtonLink, defStyleAttr, 0)
      .use {
        _uppercase = it.getBoolean(R.styleable.BpkButtonLink_uppercase, true)
      }

    val style = ButtonStyles.Link(context)
    background = style.getButtonBackground(iconPosition)
    setTextColor(style.contentColor)
    update()
  }

  override fun update() {
    val paddingVertical = tokens.bpkSpacingMd + (tokens.bpkBorderSizeLg / 2)
    setPadding(0, paddingVertical, 0, paddingVertical)
    isAllCaps = _uppercase
  }
}
