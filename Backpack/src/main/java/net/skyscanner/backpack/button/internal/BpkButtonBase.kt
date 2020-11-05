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
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import net.skyscanner.backpack.text.BpkText
import net.skyscanner.backpack.util.wrapContextWithDefaults

internal const val ICON_POSITION_START = 0
internal const val ICON_POSITION_END = 1
internal const val ICON_POSITION_ICON_ONLY = 2

// mainly exists here for compatibility reasons
abstract class BpkButtonBase internal constructor(
  context: Context,
  attrs: AttributeSet?,
  defStyleAttr: Int
) : BpkButtonWithIcon(wrapContextWithDefaults(context), attrs, defStyleAttr) {

  private val font = BpkText.getFont(this.context, BpkText.SM, BpkText.Weight.EMPHASIZED)

  abstract var iconPosition: Int

  abstract var icon: Drawable?

  init {
    isAllCaps = true
    isClickable = isEnabled
    font.applyTo(this)
  }
}
