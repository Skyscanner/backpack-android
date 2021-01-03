/**
 * Backpack for Android - Skyscanner's Design System
 *
 * Copyright 2018-2021 Skyscanner Ltd
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
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import net.skyscanner.backpack.R
import net.skyscanner.backpack.util.sizedDrawable

// provides internal properties for icon and its position
abstract class BpkButtonWithIcon internal constructor(
  context: Context,
  attrs: AttributeSet?,
  defStyleAttr: Int
) : BpkButtonLayout(context, attrs, defStyleAttr) {

  internal var iconDrawablePosition: Int = ICON_POSITION_END
    set(value) {
      field = value
      if (value == ICON_POSITION_ICON_ONLY) {
        text = ""
      }
      updateCompoundIcon()
    }

  internal var iconDrawable: Drawable? = null
    set(value) {
      if (field != value) {
        field = value?.let {
          sizedDrawable(
            drawable = it,
            width = resources.getDimensionPixelSize(R.dimen.bpkSpacingBase),
            height = resources.getDimensionPixelSize(R.dimen.bpkSpacingBase)
          )
        }
        updateCompoundIcon()
      }
    }

  override fun setText(text: CharSequence, type: BufferType) {
    if (iconDrawablePosition == ICON_POSITION_ICON_ONLY) {
      super.setText("", type)
    } else {
      super.setText(text, type)
    }
  }

  private fun updateCompoundIcon() {
    val icon = iconDrawable
    if (icon != null) {
      setCompoundDrawablesRelativeWithIntrinsicBounds(
        icon.takeIf { iconDrawablePosition == ICON_POSITION_START || iconDrawablePosition == ICON_POSITION_ICON_ONLY },
        null,
        icon.takeIf { iconDrawablePosition == ICON_POSITION_END },
        null
      )
    } else {
      setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, null)
    }
  }
}
