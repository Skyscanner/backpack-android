package net.skyscanner.backpack.bottomsheet

import android.content.Context
import android.util.AttributeSet
import androidx.cardview.widget.CardView
import net.skyscanner.backpack.R

open class BpkBottomSheet @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0,
) : CardView(context, attrs, defStyleAttr) {

  init {
    cardElevation = resources.getDimension(R.dimen.bpkElevationLg)
  }
}
