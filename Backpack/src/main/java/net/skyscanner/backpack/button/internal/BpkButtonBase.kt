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

package net.skyscanner.backpack.button.internal

import android.content.Context
import android.text.TextUtils
import android.util.AttributeSet
import android.view.Gravity
import net.skyscanner.backpack.R
import net.skyscanner.backpack.text.BpkText

// mainly exists here for compatibility reasons
abstract class BpkButtonBase internal constructor(
  context: Context,
  attrs: AttributeSet?,
  defStyleAttr: Int
) : BpkButtonWithIcon(context, attrs, defStyleAttr) {

  private val font = BpkText.getFont(this.context, BpkText.TextStyle.Label2)

  abstract var iconPosition: Int

  init {
    maxLines = 1
    gravity = Gravity.CENTER
    ellipsize = TextUtils.TruncateAt.END
    isClickable = isEnabled
    iconSize = resources.getDimensionPixelSize(R.dimen.bpkSpacingBase)
    backgroundTintList = null
    font.applyTo(this)
  }
}
