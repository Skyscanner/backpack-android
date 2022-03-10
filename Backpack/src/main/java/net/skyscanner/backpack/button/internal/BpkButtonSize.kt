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
import android.util.AttributeSet
import androidx.annotation.DimenRes
import net.skyscanner.backpack.R
import net.skyscanner.backpack.button.BpkButton
import net.skyscanner.backpack.text.BpkText

internal fun BpkButton.Size.Companion.fromAttrs(context: Context, attrs: AttributeSet?): BpkButton.Size {
  val attr = context.theme.obtainStyledAttributes(attrs, R.styleable.BpkButton, 0, 0)
  return when (attr.getInt(R.styleable.BpkButton_bpkButtonSize, 0)) {
    0 -> BpkButton.Size.Standard
    1 -> BpkButton.Size.Large
    else -> throw IllegalArgumentException()
  }
}

internal val BpkButton.Size.textStyle: BpkText.TextStyle
  get() =
    when (this) {
      BpkButton.Size.Standard -> BpkText.TextStyle.Label2
      BpkButton.Size.Large -> BpkText.TextStyle.Label1
    }

@get:DimenRes
internal val BpkButton.Size.iconSize: Int
  get() =
    when (this) {
      BpkButton.Size.Standard -> R.dimen.bpkSpacingBase
      BpkButton.Size.Large -> R.dimen.bpkSpacingLg
    }

@get:DimenRes
internal val BpkButton.Size.minHeight: Int
  get() =
    when (this) {
      BpkButton.Size.Standard -> R.dimen.bpk_button_default_height
      BpkButton.Size.Large -> R.dimen.bpk_button_large_height
    }

@get:DimenRes
internal val BpkButton.Size.horizontalPadding: Int
  get() =
    R.dimen.bpkSpacingBase

@get:DimenRes
internal val BpkButton.Size.horizontalSpacing: Int
  get() =
    R.dimen.bpkSpacingMd
