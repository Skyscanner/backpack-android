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
import net.skyscanner.backpack.util.colorStateList
import net.skyscanner.backpack.util.use

internal sealed class BpkChipAppearances(private val context: Context) : BpkChipAppearance {

    override val background: Drawable
        get() = chipRoundedRect(
            context = context,
            borderWidth = context.resources.getDimensionPixelSize(R.dimen.bpkBorderSizeSm),
            background = chipColors(
                selected = selectedBackgroundColor,
                default = backgroundColor,
                pressed = pressedBackgroundColor,
                disabled = disabledBackgroundColor,
            ),
            border = chipColors(
                selected = Color.TRANSPARENT,
                default = strokeColor,
                pressed = pressedStrokeColor,
                disabled = Color.TRANSPARENT,
            ),
        )

    override val dismissibleBackground: Drawable
        get() = chipRoundedRect(
            context = context,
            borderWidth = 0,
            background = colorStateList(
                color = selectedBackgroundColor,
                pressedColor = selectedBackgroundColor,
                disabledColor = disabledBackgroundColor,
            ),
            border = ColorStateList.valueOf(Color.TRANSPARENT),
        )

    override val text: ColorStateList
        get() = chipColors(
            selected = selectedTextColor,
            default = textColor,
            pressed = pressedTextColor,
            disabled = context.getColor(R.color.bpkTextDisabled),
        )

    override val dismissibleText: ColorStateList
        get() = colorStateList(
            color = selectedTextColor,
            pressedColor = selectedTextColor,
            disabledColor = context.getColor(R.color.bpkTextDisabled),
        )

    override val dismissibleIcon: ColorStateList
        get() = colorStateList(
            color = dismissibleIconColor,
            pressedColor = selectedTextColor,
            disabledColor = context.getColor(R.color.bpkTextDisabled),
        )

    internal class Default(context: Context, private val typedArray: TypedArray?) : BpkChipAppearances(context) {
        constructor(
            context: Context,
            attrs: AttributeSet?,
            defStyleAttr: Int,
        ) : this(context, context.theme.obtainStyledAttributes(attrs, R.styleable.BpkChip, defStyleAttr, 0)) {
            typedArray?.recycle()
        }

        override var selectedBackgroundColor: Int = context.getColor(R.color.bpkCorePrimary)
        override var backgroundColor: Int = Color.TRANSPARENT
        override var pressedBackgroundColor: Int = Color.TRANSPARENT
        override var textColor: Int = context.getColor(R.color.bpkTextPrimary)
        override var pressedTextColor: Int = context.getColor(R.color.bpkTextPrimary)
        override var selectedTextColor: Int = context.getColor(R.color.bpkTextOnDark)
        override var dismissibleIconColor: Int = context.getColor(R.color.bpkTextDisabledOnDark)
        override var disabledBackgroundColor: Int = context.getColor(R.color.__privateChipDisabledBackground)
        override var strokeColor: Int = context.getColor(R.color.bpkLine)
        override var pressedStrokeColor: Int = context.getColor(R.color.bpkCorePrimary)
        override val style = BpkChip.Style.Default

        init {
            if (typedArray != null) {
                backgroundColor = typedArray.getColor(R.styleable.BpkChip_chipBackgroundColor, backgroundColor)
                selectedBackgroundColor =
                    typedArray.getColor(R.styleable.BpkChip_chipSelectedBackgroundColor, selectedBackgroundColor)
                disabledBackgroundColor =
                    typedArray.getColor(R.styleable.BpkChip_chipDisabledBackgroundColor, disabledBackgroundColor)
                textColor = typedArray.getColor(R.styleable.BpkChip_chipTextColor, textColor)
            }
        }
    }

    internal class OnDark(context: Context, private val typedArray: TypedArray?) : BpkChipAppearances(context) {
        constructor(
            context: Context,
            attrs: AttributeSet?,
            defStyleAttr: Int,
        ) : this(context, context.theme.obtainStyledAttributes(attrs, R.styleable.BpkChip, defStyleAttr, 0)) {
            typedArray?.recycle()
        }

        override var selectedBackgroundColor: Int = context.getColor(R.color.__privateChipOnDarkOnBackground)
        override var backgroundColor: Int = Color.TRANSPARENT
        override var pressedBackgroundColor: Int = Color.TRANSPARENT
        override var textColor: Int = context.getColor(R.color.bpkTextOnDark)
        override var pressedTextColor: Int = context.getColor(R.color.bpkTextOnDark)
        override var selectedTextColor: Int = context.getColor(R.color.bpkTextPrimary)
        override var dismissibleIconColor: Int = context.getColor(R.color.__privateChipOnDarkOnDismissIcon)
        override var disabledBackgroundColor: Int = context.getColor(R.color.__privateChipDisabledBackground)
        override var strokeColor: Int = context.getColor(R.color.bpkLineOnDark)
        override var pressedStrokeColor: Int = context.getColor(R.color.__privateChipOnDarkPressedStroke)
        override val style = BpkChip.Style.OnDark

        init {
            if (typedArray != null) {
                backgroundColor = typedArray.getColor(R.styleable.BpkChip_chipBackgroundColor, backgroundColor)
                selectedBackgroundColor =
                    typedArray.getColor(R.styleable.BpkChip_chipSelectedBackgroundColor, selectedBackgroundColor)
                disabledBackgroundColor =
                    typedArray.getColor(R.styleable.BpkChip_chipDisabledBackgroundColor, disabledBackgroundColor)
                textColor = typedArray.getColor(R.styleable.BpkChip_chipTextColor, textColor)
            }
        }
    }

    internal class OnImage(context: Context, private val typedArray: TypedArray?) : BpkChipAppearances(context) {
        constructor(
            context: Context,
            attrs: AttributeSet?,
            defStyleAttr: Int,
        ) : this(context, context.theme.obtainStyledAttributes(attrs, R.styleable.BpkChip, defStyleAttr, 0)) {
            typedArray?.recycle()
        }

        override var selectedBackgroundColor: Int = context.getColor(R.color.bpkCorePrimary)
        override var backgroundColor: Int = context.getColor(R.color.bpkSurfaceDefault)
        override var pressedBackgroundColor: Int = context.getColor(R.color.bpkSurfaceContrast)
        override var textColor: Int = context.getColor(R.color.bpkTextPrimary)
        override var pressedTextColor: Int = context.getColor(R.color.bpkTextPrimary)
        override var selectedTextColor: Int = context.getColor(R.color.bpkTextOnDark)
        override var dismissibleIconColor: Int = context.getColor(R.color.bpkTextDisabledOnDark)
        override var disabledBackgroundColor: Int = context.getColor(R.color.__privateChipDisabledBackground)
        override var strokeColor: Int = Color.TRANSPARENT
        override var pressedStrokeColor: Int = Color.TRANSPARENT
        override val style = BpkChip.Style.OnImage

        init {
            if (typedArray != null) {
                backgroundColor = typedArray.getColor(R.styleable.BpkChip_chipBackgroundColor, backgroundColor)
                selectedBackgroundColor =
                    typedArray.getColor(R.styleable.BpkChip_chipSelectedBackgroundColor, selectedBackgroundColor)
                disabledBackgroundColor =
                    typedArray.getColor(R.styleable.BpkChip_chipDisabledBackgroundColor, disabledBackgroundColor)
                textColor = typedArray.getColor(R.styleable.BpkChip_chipTextColor, textColor)
            }
        }
    }

    companion object {
        fun fromAttrs(
            context: Context,
            attrs: AttributeSet?,
            defStyleAttr: Int,
        ): BpkChipAppearance =
            context.theme.obtainStyledAttributes(attrs, R.styleable.BpkChip, defStyleAttr, 0)
                .use {
                    when (BpkChip.Style.fromAttr(it.getInt(R.styleable.BpkChip_chipsStyle, 0))) {
                        BpkChip.Style.Default -> Default(context, attrs, defStyleAttr)
                        BpkChip.Style.OnDark -> OnDark(context, attrs, defStyleAttr)
                        BpkChip.Style.OnImage -> OnImage(context, attrs, defStyleAttr)
                    }
                }

        fun fromTheme(
            context: Context,
            style: BpkChip.Style,
        ): BpkChipAppearance {
            var typedArray: TypedArray? = null
            try {
                val tv = TypedValue()
                if (context.theme.resolveAttribute(R.attr.bpkChipStyle, tv, true)) {
                    typedArray = context.obtainStyledAttributes(tv.resourceId, R.styleable.BpkChip)
                }
                return when (style) {
                    BpkChip.Style.Default -> Default(context, typedArray)
                    BpkChip.Style.OnDark -> OnDark(context, typedArray)
                    BpkChip.Style.OnImage -> OnImage(context, typedArray)
                }
            } finally {
                typedArray?.recycle()
            }
        }
    }
}
