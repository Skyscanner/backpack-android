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

package net.skyscanner.backpack.toggle

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.util.AttributeSet
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.view.ContextThemeWrapper
import com.google.android.material.materialswitch.MaterialSwitch
import net.skyscanner.backpack.R
import net.skyscanner.backpack.util.colorStateList
import net.skyscanner.backpack.util.createContextThemeWrapper
import net.skyscanner.backpack.util.use

/**
 * BpkSwitch allow users to toggle between two states, on or off.
 *
 * This class extends [MaterialSwitch] directly and thus follows the same interface and design,
 * with the exception of [MaterialSwitch.getTrackTintList] and [MaterialSwitch.getThumbTintList] that are set
 * according to Backpack's design.
 *
 * @see MaterialSwitch
 */
open class BpkSwitch @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : MaterialSwitch(
    createContextThemeWrapper(
        ContextThemeWrapper(context, R.style.Widget_Material3_CompoundButton_MaterialSwitch),
        attrs,
        R.attr.bpkSwitchStyle,
    ),
    attrs,
    defStyleAttr,
) {

    init {
        initialize(attrs, defStyleAttr)
    }

    private fun initialize(attrs: AttributeSet?, defStyleAttr: Int) {
        val textDisabledColor = context.getColor(R.color.bpkTextDisabled)
        val textEnabledColor = context.getColor(R.color.bpkTextPrimary)
        val textSecondaryColor = context.getColor(R.color.bpkTextSecondary)
        val coreAccentColor = context.getColor(R.color.bpkCoreAccent)
        val canvasContrastColor = context.getColor(R.color.bpkCanvasContrast)
        var primaryInverseColor = context.getColor(R.color.bpkTextPrimaryInverse)
        context.theme.obtainStyledAttributes(attrs, R.styleable.BpkSwitch, defStyleAttr, 0).use {
            primaryInverseColor = it.getColor(R.styleable.BpkSwitch_switchPrimaryColor, primaryInverseColor)
        }

        thumbTintList = colorStateList(
            disabledUncheckedColor = textDisabledColor,
            disabledCheckedColor = textDisabledColor,
            checkedColor = primaryInverseColor,
            uncheckedColor = textSecondaryColor,
        )
        trackTintList = colorStateList(
            disabledUncheckedColor = textDisabledColor,
            disabledCheckedColor = textDisabledColor,
            checkedColor = coreAccentColor,
            uncheckedColor = canvasContrastColor,
        )
        trackDecorationDrawable = AppCompatResources.getDrawable(context, R.drawable.bpk_switch_decoration)
        trackDecorationTintList = colorStateList(
            disabledUncheckedColor = textDisabledColor,
            disabledCheckedColor = textDisabledColor,
            checkedColor = coreAccentColor,
            uncheckedColor = textSecondaryColor,
        )
        thumbIconTintList = colorStateList(
            Color.TRANSPARENT,
            Color.TRANSPARENT,
            Color.TRANSPARENT,
            Color.TRANSPARENT,
        )

        setTextColor(
            ColorStateList(
                arrayOf(intArrayOf(-android.R.attr.state_enabled), intArrayOf()),
                intArrayOf(textDisabledColor, textEnabledColor),
            ),
        )
    }
}
