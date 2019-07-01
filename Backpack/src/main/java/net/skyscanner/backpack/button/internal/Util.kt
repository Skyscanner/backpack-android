package net.skyscanner.backpack.button.internal

import android.R
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.RippleDrawable
import androidx.annotation.ColorInt
import androidx.annotation.Dimension
import androidx.core.content.ContextCompat
import net.skyscanner.backpack.util.resolveThemeColor

/**
 * Utility method to create a ripple drawable for buttons.
 *
 * @param normalColor required, used as main background color on the drawable
 * @param cornerRadius optional, used as the radius on the corners (use 100 for square)
 * @param strokeColor optional, color integer for the stroke
 * @param strokeWidth optional, width in px for the stroke
 *
 * @return Drawable
 */
internal fun getRippleDrawable(
  context: Context,
  @ColorInt normalColor: Int,
  @Dimension cornerRadius: Float? = null,
  @ColorInt strokeColor: Int? = null,
  @Dimension strokeWidth: Int? = null
) = RippleDrawable(
  ColorStateList.valueOf(resolveThemeColor(context, R.attr.colorControlHighlight) ?: ContextCompat.getColor(context, net.skyscanner.backpack.R.color.bpkGray100)),
  corneredDrawable(normalColor, cornerRadius, strokeColor, strokeWidth),
  corneredDrawable(Color.WHITE, cornerRadius, strokeColor, strokeWidth)
)

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
internal fun corneredDrawable(
  @ColorInt color: Int,
  @Dimension cornerRadius: Float? = null,
  @ColorInt strokeColor: Int? = null,
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
    intArrayOf(-R.attr.state_enabled),
    intArrayOf(R.attr.state_pressed),
    intArrayOf(R.attr.state_focused),
    intArrayOf(R.attr.state_activated),
    intArrayOf()
  ),
  intArrayOf(disabledColor, pressedColor, pressedColor, pressedColor, normalColor)
)
