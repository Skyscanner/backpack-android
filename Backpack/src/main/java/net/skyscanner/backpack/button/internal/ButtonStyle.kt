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

import android.animation.AnimatorInflater
import android.animation.StateListAnimator
import android.content.Context
import android.content.res.ColorStateList
import android.content.res.TypedArray
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.util.TypedValue
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.annotation.Px
import androidx.core.content.ContextCompat
import net.skyscanner.backpack.R
import net.skyscanner.backpack.button.BpkButton
import net.skyscanner.backpack.util.ResourcesUtil
import net.skyscanner.backpack.util.colorStateList
import net.skyscanner.backpack.util.darken
import net.skyscanner.backpack.util.rippleDrawable
import net.skyscanner.backpack.util.stateListDrawable

internal class ButtonStyle(
  private val context: Context,
  @ColorInt private val bgColor: Int,
  @ColorInt private val textColor: Int,
  @ColorInt private val strokeColor: Int,
  @ColorInt private val strokeColorPressed: Int,
  @ColorInt private val disabledBgColor: Int,
  @ColorInt private val disabledTextColor: Int,
  @Px private val strokeWidth: Int,
  @DrawableRes private val stateListAnimatorRes: Int
) {

  val contentColor: ColorStateList = colorStateList(
    color = textColor,
    pressedColor = darken(textColor, .1f),
    disabledColor = disabledTextColor
  )

  fun getStateListAnimator(): StateListAnimator? =
    AnimatorInflater.loadStateListAnimator(context, stateListAnimatorRes)

  fun getButtonBackground(enabled: Boolean, @BpkButton.IconPosition iconPosition: Int): Drawable {

    val radius = if (iconPosition == BpkButton.ICON_ONLY) {
      context.resources.getDimension(R.dimen.bpkBorderRadiusLg)
    } else {
      context.resources.getDimension(R.dimen.bpkBorderRadiusSm)
    }

    val strokeWidthPressed = if (strokeWidth > 0) {
      strokeWidth + ResourcesUtil.dpToPx(1, context)
    } else {
      0
    }

    fun roundRectDrawable(
      @ColorInt color: Int,
      @ColorInt strokeColor: Int = Color.TRANSPARENT,
      @Px strokeWidth: Int = 0
    ): Drawable = GradientDrawable().apply {
      setColor(color)
      cornerRadius = radius
      setStroke(strokeWidth, strokeColor)
    }

    if (enabled) {
      return rippleDrawable(
        context = context,
        mask = roundRectDrawable(Color.WHITE),
        content = stateListDrawable(
          pressed = roundRectDrawable(
            color = bgColor,
            strokeColor = strokeColorPressed,
            strokeWidth = strokeWidthPressed
          ),
          drawable = roundRectDrawable(
            color = bgColor,
            strokeColor = strokeColor,
            strokeWidth = strokeWidth
          )
        ) {
          val strokeAnimation = context.resources.getInteger(R.integer.bpkAnimationDurationSm)
          setEnterFadeDuration(strokeAnimation)
          setExitFadeDuration(strokeAnimation)
        }
      )
    } else {
      return roundRectDrawable(disabledBgColor)
    }
  }

  companion object {

    private fun fromTypedArray(
      context: Context,
      typedArray: TypedArray?,
      @ColorInt defaultBgColor: Int,
      @ColorInt defaultTextColor: Int,
      @ColorInt defaultStrokeColor: Int,
      @ColorInt defaultStrokeColorPressed: Int,
      @ColorInt disabledBgColor: Int,
      @ColorInt disabledTextColor: Int,
      @Px strokeWidth: Int,
      @DrawableRes stateListAnimatorRes: Int
    ): ButtonStyle {
      var bgColor = defaultBgColor
      var textColor = defaultTextColor
      var strokeColor = defaultStrokeColor
      var strokeColorPressed = defaultStrokeColorPressed

      typedArray?.let {
        bgColor = it.getColor(R.styleable.BpkButton_buttonBackgroundColor, bgColor)
        textColor = it.getColor(R.styleable.BpkButton_buttonTextColor, textColor)
        strokeColor = it.getColor(R.styleable.BpkButton_buttonStrokeColor, strokeColor)
        strokeColorPressed = it.getColor(R.styleable.BpkButton_buttonStrokeColorPressed, strokeColorPressed)
      }

      return ButtonStyle(
        context = context,
        bgColor = bgColor,
        textColor = textColor,
        strokeColor = strokeColor,
        strokeColorPressed = strokeColorPressed,
        disabledBgColor = disabledBgColor,
        disabledTextColor = disabledTextColor,
        stateListAnimatorRes = stateListAnimatorRes,
        strokeWidth = strokeWidth
      )
    }

    fun fromTheme(
      context: Context,
      @AttrRes style: Int,
      @ColorRes bgColorRes: Int,
      @ColorRes textColorRes: Int,
      @ColorRes strokeColorRes: Int = android.R.color.transparent,
      @ColorRes strokeColorPressedRes: Int = strokeColorRes,
      @ColorRes disabledBgColorRes: Int,
      @ColorRes disabledTextColorRes: Int,
      @DimenRes strokeWidthRes: Int = R.dimen.bpk_internal_spacing_zero,
      @DrawableRes stateListAnimatorRes: Int = R.drawable.bpk_button_state_animator_zero
    ): ButtonStyle {

      var typedArray: TypedArray? = null
      try {

        val tv = TypedValue()
        if (context.theme.resolveAttribute(style, tv, true)) {
          typedArray = context.obtainStyledAttributes(tv.resourceId, R.styleable.BpkButton)
        }

        return fromTypedArray(
          context = context,
          typedArray = typedArray,
          defaultBgColor = ContextCompat.getColor(context, bgColorRes),
          defaultTextColor = ContextCompat.getColor(context, textColorRes),
          defaultStrokeColor = ContextCompat.getColor(context, strokeColorRes),
          defaultStrokeColorPressed = ContextCompat.getColor(context, strokeColorPressedRes),
          disabledBgColor = ContextCompat.getColor(context, disabledBgColorRes),
          disabledTextColor = ContextCompat.getColor(context, disabledTextColorRes),
          strokeWidth = context.resources.getDimensionPixelSize(strokeWidthRes),
          stateListAnimatorRes = stateListAnimatorRes
        )
      } finally {
        typedArray?.recycle()
      }
    }

    fun fromAttributes(
      context: Context,
      typedArray: TypedArray?,
      fallback: ButtonStyle
    ): ButtonStyle = fromTypedArray(
      context = context,
      typedArray = typedArray,
      defaultBgColor = fallback.bgColor,
      defaultTextColor = fallback.textColor,
      defaultStrokeColor = fallback.strokeColor,
      defaultStrokeColorPressed = fallback.strokeColorPressed,
      disabledBgColor = fallback.disabledBgColor,
      disabledTextColor = fallback.disabledTextColor,
      strokeWidth = fallback.strokeWidth,
      stateListAnimatorRes = fallback.stateListAnimatorRes
    )
  }
}
