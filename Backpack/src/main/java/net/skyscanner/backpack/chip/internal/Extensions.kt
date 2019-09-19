package net.skyscanner.backpack.chip.internal

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.RippleDrawable
import android.graphics.drawable.StateListDrawable
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import net.skyscanner.backpack.R
import net.skyscanner.backpack.chip.BpkChip
import net.skyscanner.backpack.util.resolveThemeColor

/**
 * Utility method to create a drawable for chips.
 *
 * @param color required, used as main background color on the drawable
 * @param selectedColor required, used as secondary color when chip is selected
 *
 * @return Drawable
 */
internal fun BpkChip.getChipBackground(
  @ColorInt normalColor: Int,
  @ColorInt selectedColor: Int
): StateListDrawable {
  return StateListDrawable().apply {
    addState(intArrayOf(android.R.attr.state_pressed, android.R.attr.state_selected), getRippleDrawable(selectedColor))
    addState(intArrayOf(-android.R.attr.state_selected), getRippleDrawable(normalColor))
    addState(intArrayOf(android.R.attr.state_selected), corneredDrawable(context, selectedColor))
  }
}

private fun BpkChip.getRippleDrawable(
  @ColorInt normalColor: Int
): Drawable {
  return RippleDrawable(
    ColorStateList.valueOf(
      resolveThemeColor(context, R.attr.colorControlHighlight)
        ?: ContextCompat.getColor(context, R.color.bpkGray100)
    ), corneredDrawable(context, normalColor), null
  )
}

/**
 * Utility function to create a drawable for chip with given background color
 * @param color required, used as the background for the drawable
 *
 * @return Drawable
 */
private fun corneredDrawable(
  context: Context,
  @ColorInt color: Int
): Drawable {
  return ContextCompat.getDrawable(context, R.drawable.chip_background)?.apply {
    if (this is GradientDrawable) {
      setColor(ColorStateList.valueOf(color))
    }
  }!!
}
