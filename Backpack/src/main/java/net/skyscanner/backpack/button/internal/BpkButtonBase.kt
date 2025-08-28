/**
 * Backpack for Android - Skyscanner's Design System
 *
 * Copyright 2018 - 2025 Skyscanner Ltd
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

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.text.TextUtils
import android.util.AttributeSet
import android.view.Gravity
import com.google.android.material.button.MaterialButton
import net.skyscanner.backpack.R

internal const val ICON_POSITION_START = 0
internal const val ICON_POSITION_END = 1
internal const val ICON_POSITION_ICON_ONLY = 2

abstract class BpkButtonBase internal constructor(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttr: Int,
) : MaterialButton(context, attrs, defStyleAttr) {

    internal var iconDrawablePosition: Int = ICON_POSITION_END
        set(value) {
            field = value
            iconGravity = when (value) {
                ICON_POSITION_START -> ICON_GRAVITY_TEXT_START
                else -> ICON_GRAVITY_TEXT_END
            }
            if (value == ICON_POSITION_ICON_ONLY) {
                @SuppressLint("SetTextI18n")
                text = ""
            }
        }

    abstract var iconPosition: Int

    @SuppressLint("SetTextI18n")
    override fun setText(text: CharSequence, type: BufferType) {
        if (iconDrawablePosition == ICON_POSITION_ICON_ONLY) {
            super.setText("", type)
        } else {
            super.setText(text, type)
        }
    }

    override fun setTextColor(color: Int) {
        super.setTextColor(color)
        iconTint = ColorStateList.valueOf(color)
    }

    override fun setTextColor(colors: ColorStateList) {
        super.setTextColor(colors)
        iconTint = colors
    }

    init {
        maxLines = 1
        gravity = Gravity.CENTER
        ellipsize = TextUtils.TruncateAt.END
        isClickable = isEnabled
        iconSize = resources.getDimensionPixelSize(R.dimen.bpkSpacingBase)
        backgroundTintList = null
    }
}
