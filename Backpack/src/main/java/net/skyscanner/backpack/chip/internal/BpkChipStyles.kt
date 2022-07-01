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

package net.skyscanner.backpack.chip.internal

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.TypedArray
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.TypedValue
import net.skyscanner.backpack.R
import net.skyscanner.backpack.chip.BpkChip
import net.skyscanner.backpack.util.use

internal sealed class BpkChipStyles : BpkChipStyle {

  internal class Stroke(
    private val context: Context,
    attrs: AttributeSet?,
    defStyleAttr: Int,
  ) : BpkChipStyles() {

    override var selectedBackgroundColor: Int = context.getColor(R.color.bpkPrimaryLight)
    override var backgroundColor: Int = context.getColor(R.color.bpkBackgroundElevation03)
    override var textColor: Int = context.getColor(R.color.bpkTextPrimary)
    override var disabledBackgroundColor: Int = Color.TRANSPARENT
    override val style: BpkChip.Style? = null

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
          default = context.getColor(R.color.bpkTextTertiary),
          disabled = context.getColor(R.color.__chipDisabled),
        ),
      )

    override val text: ColorStateList
      get() = chipColors(
        selected = context.getColor(R.color.bpkWhite),
        default = textColor,
        disabled = context.getColor(R.color.__chipDisabled),
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

  internal sealed class Solid(
    private val context: Context,
  ) : BpkChipStyles() {

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
        selected = context.getColor(R.color.bpkWhite),
        default = textColor,
        disabled = context.getColor(R.color.__chipDisabled),
      )

    internal class Default(context: Context, typedArray: TypedArray?) : Solid(context) {
      constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int,
      ) : this(context, context.theme.obtainStyledAttributes(attrs, R.styleable.BpkChip, defStyleAttr, 0))

      override var selectedBackgroundColor: Int = context.getColor(R.color.bpkPrimaryLight)
      override var backgroundColor: Int = context.getColor(R.color.__chipSolidBackground)
      override var textColor: Int = context.getColor(R.color.bpkTextPrimary)
      override var disabledBackgroundColor: Int = context.getColor(R.color.__chipSolidBackground)
      override val style = BpkChip.Style.Default

      init {
        if (typedArray != null) {
          backgroundColor = typedArray.getColor(R.styleable.BpkChip_chipBackgroundColor, backgroundColor)
          selectedBackgroundColor =
            typedArray.getColor(R.styleable.BpkChip_chipSelectedBackgroundColor, selectedBackgroundColor)
          disabledBackgroundColor =
            typedArray.getColor(R.styleable.BpkChip_chipDisabledBackgroundColor, disabledBackgroundColor)
          textColor = typedArray.getColor(R.styleable.BpkChip_chipTextColor, textColor)
          typedArray.recycle()
        }
      }
    }

    internal class OnDark(context: Context, typedArray: TypedArray?) : Solid(context) {
      constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int,
      ) : this(context, context.theme.obtainStyledAttributes(attrs, R.styleable.BpkChip, defStyleAttr, 0))

      override var selectedBackgroundColor: Int = context.getColor(R.color.bpkPrimaryLight)
      override var backgroundColor: Int = context.getColor(R.color.__chipOnDarkBackground)
      override var textColor: Int = context.getColor(R.color.bpkTextPrimary)
      override var disabledBackgroundColor: Int = context.getColor(R.color.__chipSolidBackground)
      override val style = BpkChip.Style.Default

      init {
        if (typedArray != null) {
          backgroundColor = typedArray.getColor(R.styleable.BpkChip_chipBackgroundColor, backgroundColor)
          selectedBackgroundColor =
            typedArray.getColor(R.styleable.BpkChip_chipSelectedBackgroundColor, selectedBackgroundColor)
          disabledBackgroundColor =
            typedArray.getColor(R.styleable.BpkChip_chipDisabledBackgroundColor, disabledBackgroundColor)
          textColor = typedArray.getColor(R.styleable.BpkChip_chipTextColor, textColor)
          typedArray.recycle()
        }
      }
    }

    companion object {
      fun fromAttrs(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int,
      ): BpkChipStyle =
        context.theme.obtainStyledAttributes(attrs, R.styleable.BpkChip, defStyleAttr, 0)
          .use {
            when (BpkChip.Style.fromAttr(it.getInt(R.styleable.BpkChip_chipsStyle, 0))) {
              BpkChip.Style.Default -> Default(context, attrs, defStyleAttr)
              BpkChip.Style.OnDark -> OnDark(context, attrs, defStyleAttr)
            }
          }

      fun fromTheme(
        context: Context,
        style: BpkChip.Style,
      ): BpkChipStyle {
        var typedArray: TypedArray? = null
        try {
          val tv = TypedValue()
          if (context.theme.resolveAttribute(R.attr.bpkChipStyle, tv, true)) {
            typedArray = context.obtainStyledAttributes(tv.resourceId, R.styleable.BpkChip)
          }
          return when (style) {
            BpkChip.Style.Default -> Default(context, typedArray)
            BpkChip.Style.OnDark -> OnDark(context, typedArray)
          }
        } finally {
          typedArray?.recycle()
        }
      }
    }
  }
}
