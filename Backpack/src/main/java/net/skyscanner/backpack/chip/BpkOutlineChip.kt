package net.skyscanner.backpack.chip

import android.content.Context
import android.util.AttributeSet
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import net.skyscanner.backpack.R
import net.skyscanner.backpack.util.createContextThemeWrapper

class BpkOutlineChip @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
) : BpkChip(createContextThemeWrapper(context, attrs, R.attr.bpkOutlineChipStyle), attrs, defStyleAttr) {

  init {
    this.setTextColor(ContextCompat.getColor(context, R.color.bpkWhite))
  }

  override fun updateBackground() {
    ViewCompat.setBackground(this, ContextCompat.getDrawable(context, R.drawable.chip_outline_background))
  }
}
