package net.skyscanner.backpack.chip.internal

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import net.skyscanner.backpack.R
import net.skyscanner.backpack.util.ResourcesUtil

internal fun chipRoundedRect(
  context: Context,
  background: ColorStateList,
  border: ColorStateList,
  borderWidthDp: Int,
): Drawable = GradientDrawable().apply {
  shape = GradientDrawable.RECTANGLE
  cornerRadii = FloatArray(8) { context.resources.getDimension(R.dimen.bpkBorderRadiusPill) }
  color = background
  setStroke(ResourcesUtil.dpToPx(borderWidthDp, context), border)
}

internal fun chipColors(
  selected: Int,
  default: Int,
  disabled: Int,
) = ColorStateList(
  arrayOf(
    intArrayOf(android.R.attr.state_enabled, android.R.attr.state_selected),
    intArrayOf(android.R.attr.state_enabled),
    intArrayOf(-android.R.attr.state_enabled),
  ),
  intArrayOf(
    selected,
    default,
    disabled,
  )
)
