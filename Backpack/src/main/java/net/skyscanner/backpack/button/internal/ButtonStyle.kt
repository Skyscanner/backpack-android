package net.skyscanner.backpack.button.internal

import android.animation.AnimatorInflater
import android.animation.StateListAnimator
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.util.TypedValue
import androidx.annotation.*
import androidx.core.content.ContextCompat
import net.skyscanner.backpack.R
import net.skyscanner.backpack.button.BpkButton
import net.skyscanner.backpack.util.*
import net.skyscanner.backpack.util.ResourcesUtil
import net.skyscanner.backpack.util.darken
import net.skyscanner.backpack.util.use

internal class ButtonStyle(
  private val context: Context,
  @AttrRes style: Int,
  @ColorRes bgColorRes: Int,
  @ColorRes textColorRes: Int,
  @ColorRes strokeColorRes: Int,
  @ColorRes strokeColorPressedRes: Int,
  @ColorRes disabledBgColorRes: Int,
  @ColorRes disabledTextColorRes: Int,
  @DimenRes strokeWidthRes: Int = 0,
  @DrawableRes private val stateListAnimatorRes: Int = 0
) {

  private val bgColor: Int
  private val textColor: Int
  private val strokeColor: Int
  private val strokeColorPressed: Int
  private val disabledBgColor = ContextCompat.getColor(context, disabledBgColorRes)
  private val disabledTextColor = ContextCompat.getColor(context, disabledTextColorRes)
  private val strokeWidth = if (strokeWidthRes != 0) {
    context.resources.getDimensionPixelSize(strokeWidthRes)
  } else {
    0
  }
  private val strokeWidthPressed = if (strokeWidth != 0) {
    strokeWidth + ResourcesUtil.dpToPx(1, context)
  } else {
    0
  }

  init {
    val tv = TypedValue()
    context.theme.resolveAttribute(style, tv, true)

    var bgColorInt = ContextCompat.getColor(context, bgColorRes)
    var textColorInt = ContextCompat.getColor(context, textColorRes)
    var strokeColorInt = ContextCompat.getColor(context, strokeColorRes)
    var strokeColorPressedInt = ContextCompat.getColor(context, strokeColorPressedRes)

    context.obtainStyledAttributes(tv.resourceId, R.styleable.BpkButton).use {
      bgColorInt = it.getColor(R.styleable.BpkButton_buttonBackgroundColor, bgColorInt)
      textColorInt = it.getColor(R.styleable.BpkButton_buttonTextColor, textColorInt)
      strokeColorInt = it.getColor(R.styleable.BpkButton_buttonStrokeColor, strokeColorInt)
      strokeColorPressedInt = it.getColor(R.styleable.BpkButton_buttonStrokeColorPressed, strokeColorPressedInt)
    }
    this.bgColor = bgColorInt
    this.textColor = textColorInt
    this.strokeColor = strokeColorInt
    this.strokeColorPressed = strokeColorPressedInt
  }

  private val pressedTextColor = darken(textColor, .1f)

  val contentColor: ColorStateList = colorStateList(
    color = textColor,
    pressedColor = pressedTextColor,
    disabledColor = disabledTextColor
  )

  fun getStateListAnimator(): StateListAnimator? =
    if (stateListAnimatorRes != 0) {
      AnimatorInflater.loadStateListAnimator(context, stateListAnimatorRes)
    } else {
      null
    }

  fun getButtonBackground(@BpkButton.IconPosition iconPosition: Int): Drawable? {

    val resources = context.resources

    val radius = if (iconPosition == BpkButton.ICON_ONLY) {
      resources.getDimension(R.dimen.bpkSpacingLg)
    } else {
      resources.getDimension(R.dimen.bpkSpacingSm)
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

    return rippleDrawable(
        context = context,
        mask = roundRectDrawable(Color.WHITE),
        content = stateListDrawable(
          pressed = roundRectDrawable(
            color = bgColor,
            strokeColor = strokeColorPressed,
            strokeWidth = strokeWidthPressed
          ),
          disabled = roundRectDrawable(disabledBgColor),
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
  }
}
