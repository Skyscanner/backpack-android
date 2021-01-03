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

package net.skyscanner.backpack.chip.internal

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.core.content.ContextCompat
import net.skyscanner.backpack.R
import net.skyscanner.backpack.util.use

internal sealed class BpkChipStyles : BpkChipStyle {

  internal class Stroke(
    private val context: Context,
    attrs: AttributeSet?,
    defStyleAttr: Int,
  ) : BpkChipStyles() {

    override var selectedBackgroundColor: Int = ContextCompat.getColor(context, R.color.bpkPrimaryLight)
    override var backgroundColor: Int = ContextCompat.getColor(context, R.color.bpkBackgroundElevation03)
    override var textColor: Int = ContextCompat.getColor(context, R.color.bpkTextPrimary)
    override var disabledBackgroundColor: Int = Color.TRANSPARENT

    override val background: Drawable
      get() = chipRoundedRect(
        context = context,
        borderWidthDp = 1,
        background = chipColors(
          selected = selectedBackgroundColor,
          default = backgroundColor,
          disabled = disabledBackgroundColor,
        ),
        border = chipColors(
          selected = selectedBackgroundColor,
          default = ContextCompat.getColor(context, R.color.bpkTextTertiary),
          disabled = ContextCompat.getColor(context, R.color.__chipDisabled),
        ),
      )

    override val text: ColorStateList
      get() = chipColors(
        selected = ContextCompat.getColor(context, R.color.bpkWhite),
        default = textColor,
        disabled = ContextCompat.getColor(context, R.color.__chipDisabled),
      )

    init {
      context.theme.obtainStyledAttributes(attrs, R.styleable.BpkChip, defStyleAttr, 0)
        .use {
          backgroundColor = it.getColor(R.styleable.BpkChip_chipBackgroundColor, backgroundColor)
          selectedBackgroundColor = it.getColor(R.styleable.BpkChip_chipSelectedBackgroundColor, selectedBackgroundColor)
          disabledBackgroundColor = it.getColor(R.styleable.BpkChip_chipDisabledBackgroundColor, disabledBackgroundColor)
          textColor = it.getColor(R.styleable.BpkChip_chipTextColor, textColor)
        }
    }
  }

  internal class Solid(
    private val context: Context,
    attrs: AttributeSet?,
    defStyleAttr: Int,
  ) : BpkChipStyles() {

    override var selectedBackgroundColor: Int = ContextCompat.getColor(context, R.color.bpkPrimaryLight)
    override var backgroundColor: Int = ContextCompat.getColor(context, R.color.__chipSolidBackground)
    override var textColor: Int = ContextCompat.getColor(context, R.color.bpkTextPrimary)
    override var disabledBackgroundColor: Int = ContextCompat.getColor(context, R.color.__chipSolidBackground)

    override val background: Drawable
      get() = chipRoundedRect(
        context = context,
        borderWidthDp = 0,
        background = chipColors(
          selected = selectedBackgroundColor,
          default = backgroundColor,
          disabled = disabledBackgroundColor,
        ),
        border = ColorStateList.valueOf(Color.TRANSPARENT),
      )

    override val text: ColorStateList
      get() = chipColors(
        selected = ContextCompat.getColor(context, R.color.bpkWhite),
        default = textColor,
        disabled = ContextCompat.getColor(context, R.color.__chipDisabled),
      )

    init {
      context.theme.obtainStyledAttributes(attrs, R.styleable.BpkChip, defStyleAttr, 0)
        .use {
          backgroundColor = it.getColor(R.styleable.BpkChip_chipBackgroundColor, backgroundColor)
          selectedBackgroundColor = it.getColor(R.styleable.BpkChip_chipSelectedBackgroundColor, selectedBackgroundColor)
          disabledBackgroundColor = it.getColor(R.styleable.BpkChip_chipDisabledBackgroundColor, disabledBackgroundColor)
          textColor = it.getColor(R.styleable.BpkChip_chipTextColor, textColor)
        }
    }
  }
}
