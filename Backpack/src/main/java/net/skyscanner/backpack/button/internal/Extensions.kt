package net.skyscanner.backpack.button.internal

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.RippleDrawable
import android.graphics.drawable.StateListDrawable
import android.util.StateSet
import androidx.annotation.ColorInt
import androidx.annotation.Dimension
import androidx.core.content.ContextCompat
import net.skyscanner.backpack.R
import net.skyscanner.backpack.util.resolveThemeColor

internal typealias StrokeWidth = Pair<Int, Int>

/**
 * Utility method to create a ripple drawable for buttons.
 *
 * @param normalColor used as main background color on the drawable
 * @param cornerRadius used as the radius on the corners (use 100 for square)
 * @param strokeColor color integer for the stroke
 * @param strokeWidth a [Pair] containing the stroke width in px of normal and selected, in that order.
 *                    To use the same stroke width for all state null can be provided as the selected stroke width
 *
 * @return Drawable
 */
internal fun BpkButtonBase.getRippleDrawable(
  @ColorInt normalColor: Int,
  @Dimension cornerRadius: Float? = null,
  @ColorInt strokeColor: ColorStateList? = null,
  strokeWidth: StrokeWidth? = null
) = RippleDrawable(
  ColorStateList.valueOf(resolveThemeColor(context, R.attr.colorControlHighlight)
    ?: ContextCompat.getColor(context, net.skyscanner.backpack.R.color.bpkSkyGrayTint06)),
  getContentDrawable(normalColor, cornerRadius, strokeColor, strokeWidth),
  corneredDrawable(Color.WHITE, cornerRadius, strokeColor, strokeWidth?.first)
)

/**
 * Utility function to state list for a drawable
 *
 * @param normalColor required, used as the color for any non specified special state
 * @param pressedColor required, used as the color for the pressed, focused and activated state
 * @param disabledColor required, used as the color for the disabled state
 *
 * @return ColorStateList
 */
internal fun getColorSelector(
  @ColorInt normalColor: Int,
  @ColorInt pressedColor: Int,
  @ColorInt disabledColor: Int
) = ColorStateList(
  arrayOf(
    intArrayOf(-android.R.attr.state_enabled),
    intArrayOf(android.R.attr.state_pressed),
    intArrayOf(android.R.attr.state_focused),
    intArrayOf(android.R.attr.state_activated),
    intArrayOf()
  ),
  intArrayOf(disabledColor, pressedColor, pressedColor, pressedColor, normalColor)
)

private fun BpkButtonBase.getContentDrawable(
  @ColorInt normalColor: Int,
  @Dimension cornerRadius: Float? = null,
  @ColorInt strokeColor: ColorStateList? = null,
  strokeWidth: StrokeWidth? = null
): Drawable {
  val strokeWidthNormal = strokeWidth?.first
  val strokeWidthSelected = strokeWidth?.second

  return if (strokeWidthSelected == null) {
    corneredDrawable(normalColor, cornerRadius, strokeColor, strokeWidthNormal)
  } else {
    val strokeAnimation = context.resources.getInteger(net.skyscanner.backpack.R.integer.bpkAnimationDurationSm)

    StateListDrawable().apply {
      addState(
        intArrayOf(android.R.attr.state_pressed),
        corneredDrawable(normalColor, cornerRadius, strokeColor, strokeWidthSelected))
      addState(
        StateSet.WILD_CARD,
        corneredDrawable(normalColor, cornerRadius, strokeColor, strokeWidthNormal))

      setEnterFadeDuration(strokeAnimation)
      setExitFadeDuration(strokeAnimation)
    }
  }
}

/**
 * Utility function to create a cornered (or circle) drawable given a corner radius.
 * Additional stroke width and color can be defined
 *
 * @param color required, used as the background for the drawable
 * @param cornerRadius optional, if set used as the radius on the drawable
 * @param strokeColor optional, color integer for the stroke
 * @param strokeWidth optional, width in px for the stroke
 *
 * @return Drawable
 */
private fun corneredDrawable(
  @ColorInt color: Int,
  @Dimension cornerRadius: Float? = null,
  @ColorInt strokeColor: ColorStateList? = null,
  strokeWidth: Int? = null
): Drawable {
  val gd = GradientDrawable()
  gd.setColor(color)
  cornerRadius?.let { gd.cornerRadius = it }
  if (strokeWidth != null && strokeColor != null) {
    gd.setStroke(strokeWidth, strokeColor)
  } else {
    // This is required otherwise the ripple effect leaks outside the button
    gd.setStroke(0, -1)
  }
  return gd
}
