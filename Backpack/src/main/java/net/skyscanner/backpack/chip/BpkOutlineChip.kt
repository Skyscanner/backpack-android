package net.skyscanner.backpack.chip

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.StateListDrawable
import android.util.AttributeSet
import android.util.StateSet
import androidx.annotation.ColorInt
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import net.skyscanner.backpack.R
import net.skyscanner.backpack.util.createContextThemeWrapper

class BpkOutlineChip @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
) : BpkChip(createContextThemeWrapper(context, attrs, R.attr.bpkOutlineChipStyle), attrs, defStyleAttr) {

  private val textColor = ColorStateList(
    arrayOf(
      intArrayOf(android.R.attr.state_enabled, android.R.attr.state_selected),
      intArrayOf(android.R.attr.state_enabled),
      intArrayOf(-android.R.attr.state_enabled)
    ),
    intArrayOf(
      ContextCompat.getColor(context, R.color.bpkWhite),
      ContextCompat.getColor(context, R.color.bpkWhite),
      ContextCompat.getColor(context, R.color.bpkBlackTint05)
    )
  )

  init {
    setTextColor(textColor)
  }

  override fun updateBackground() {
    background = getStateListDrawable(selectedBackgroundColor)
    height = resources.getDimensionPixelSize(R.dimen.bpk_chip_height)
  }

  private fun getStateListDrawable(selectedBackgroundColor: Int): StateListDrawable? {
    val stateListDrawable = StateListDrawable()
    stateListDrawable.addState(intArrayOf(android.R.attr.state_selected), corneredDrawable(selectedBackgroundColor))
    stateListDrawable.addState(
      StateSet.WILD_CARD,
      AppCompatResources.getDrawable(context, R.drawable.chip_outline_background)
    )
    return stateListDrawable
  }

  private fun corneredDrawable(
    @ColorInt color: Int
  ): Drawable {
    val gd = GradientDrawable()
    gd.setColor(color)
    gd.cornerRadius = resources.getDimension(R.dimen.bpkBorderRadiusPill)
    return gd
  }
}
