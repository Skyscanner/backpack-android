package net.skyscanner.backpack.chip

import android.content.Context
import android.util.AttributeSet
import net.skyscanner.backpack.R
import net.skyscanner.backpack.chip.internal.BpkChipStyle
import net.skyscanner.backpack.chip.internal.BpkChipStyles
import net.skyscanner.backpack.util.createContextThemeWrapper

class BpkOutlineChip @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
) : BpkChip(createContextThemeWrapper(context, attrs, R.attr.bpkOutlineChipStyle), attrs, defStyleAttr) {

  override fun provideStyle(context: Context, attrs: AttributeSet?, defStyleAttr: Int): BpkChipStyle =
    BpkChipStyles.Stroke(context, attrs, defStyleAttr)
}
