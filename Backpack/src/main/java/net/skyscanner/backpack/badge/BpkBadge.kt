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

package net.skyscanner.backpack.badge

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.TypedArray
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.Gravity
import androidx.annotation.ColorRes
import androidx.appcompat.content.res.AppCompatResources
import net.skyscanner.backpack.R
import net.skyscanner.backpack.text.BpkText

open class BpkBadge @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : BpkText(context, attrs, defStyleAttr) {

    private var initialized = false

    private val iconPadding = context.resources.getDimensionPixelSize(R.dimen.bpkSpacingSm)
    private val iconSize = context.resources.getDimensionPixelSize(R.dimen.bpk_icon_size_small)

    init {
        initialize(attrs, defStyleAttr)
        initialized = true
    }

    enum class Type(
        internal var id: Int,
        @ColorRes internal var bgColor: Int,
        @ColorRes internal var textColor: Int,
        @ColorRes internal var iconColor: Int = textColor,
    ) {
        /**
         * Style for badges with positive messages
         */
        Success(1, R.color.__privateBadgeBackgroundNormal, R.color.bpkTextPrimary, R.color.bpkStatusSuccessSpot),

        /**
         *  Style for badges with warning messages
         */
        Warning(2, R.color.__privateBadgeBackgroundNormal, R.color.bpkTextPrimary, R.color.bpkStatusWarningSpot),

        /**
         * Style for badges with error messages
         */
        Destructive(3, R.color.__privateBadgeBackgroundNormal, R.color.bpkTextPrimary, R.color.bpkStatusDangerSpot),

        /**
         *  Light themed style for badges
         */
        @Deprecated("Switch to a different badge style")
        Light(4, R.color.bpkSkyGrayTint07, R.color.bpkSkyBlueShade03),

        /**
         *  Style for badges on dark themes
         */
        Inverse(5, R.color.bpkSurfaceDefault, R.color.bpkTextPrimary),

        /**
         * Style for badges with a thin white outline
         */
        Outline(6, R.color.bpkTextOnDark, R.color.bpkTextOnDark),

        /**
         * Style for badges with a dark background
         */
        @Deprecated("Switch to a different badge style")
        Dark(7, R.color.bpkSkyGray, R.color.bpkWhite),

        /**
         * Style for badges
         */
        Normal(8, R.color.__privateBadgeBackgroundNormal, R.color.bpkTextPrimary),

        /**
         * Style for badges with emphasis
         */
        Strong(9, R.color.bpkCorePrimary, R.color.bpkTextOnDark),

        /**
         * Style for badges with brand
         */
        Brand(10, R.color.bpkCoreAccent, R.color.bpkTextPrimaryInverse),
        ;

        internal companion object {

            internal fun fromId(id: Int): Type {
                for (f in entries) {
                    if (f.id == id) return f
                }
                throw IllegalArgumentException()
            }
        }
    }

    /**
     * @property type
     * Type of badge. Default Type.Success
     */
    var type: Type = Type.Normal
        set(value) {
            field = value
            if (initialized) setup()
        }

    /**
     * @property message
     * message on the badge
     */
    var message: String? = null
        set(value) {
            field = value
            this.text = message
        }

    /**
     * @property icon
     * leading icon of the badge
     */
    var icon: Drawable? = null
        set(value) {
            field = value
            updateIcon()
        }

    private fun initialize(attrs: AttributeSet?, defStyleAttr: Int) {

        val a: TypedArray = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.BpkBadge,
            defStyleAttr,
            0,
        )

        type = Type.fromId(a.getInt(R.styleable.BpkBadge_badgeType, 8))
        message = a.getString(R.styleable.BpkBadge_message)
        a.getResourceId(R.styleable.BpkBadge_badgeIcon, 0).let { iconID ->
            icon = if (iconID != 0) {
                AppCompatResources.getDrawable(context, iconID)
            } else {
                null
            }
        }

        a.recycle()

        setup()
    }

    private fun setup() {
        this.includeFontPadding = true
        this.textStyle = TextStyle.Footnote
        this.minHeight = resources.getDimensionPixelSize(R.dimen.bpkSpacingLg)
        this.text = message
        this.icon = icon

        // set padding
        val paddingMd = resources.getDimension(R.dimen.bpkSpacingMd).toInt()
        val paddingSm = resources.getDimension(R.dimen.bpkSpacingSm).toInt()
        this.setPadding(paddingMd, paddingSm, paddingMd, paddingSm)

        // set Text color
        this.setTextColor(context.getColor(type.textColor))

        // Set background
        val bgColor = context.getColorStateList(type.bgColor)
        if (type == Type.Outline) {
            setBackground(ColorStateList.valueOf(Color.TRANSPARENT), bgColor)
        } else {
            setBackground(bgColor)
        }
        this.gravity = Gravity.CENTER

        compoundDrawablePadding = iconPadding
    }

    private fun updateIcon() {
        val currentIcon = if (icon == null) {
            when (type) {
                Type.Success -> AppCompatResources.getDrawable(context, R.drawable.bpk_tick_circle)
                Type.Warning -> AppCompatResources.getDrawable(context, R.drawable.bpk_information_circle)
                Type.Destructive -> AppCompatResources.getDrawable(context, R.drawable.bpk_exclamation)
                else -> null
            }
        } else {
            icon
        }
        currentIcon
            ?.mutate()
            ?.apply {
                setBounds(0, 0, iconSize, iconSize)
                setTint(context.getColor(type.iconColor))
                setCompoundDrawablesRelative(this, null, null, null)
            }
    }

    internal fun setBackground(
        solid: ColorStateList,
        stroke: ColorStateList = solid,
    ) {
        val drawable = GradientDrawable()
        drawable.color = solid
        drawable.setStroke(resources.getDimension(R.dimen.badge_border_size).toInt(), stroke)

        val cornerRadius = resources.getDimension(R.dimen.bpkBorderRadiusXs)
        drawable.cornerRadius = cornerRadius
        this.background = drawable
    }
}
