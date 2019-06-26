package net.skyscanner.backpack.starrating

import android.content.Context
import android.util.AttributeSet
import net.skyscanner.backpack.R
import net.skyscanner.backpack.starrating.internal.BpkStarRatingBase
import net.skyscanner.backpack.util.createContextThemeWrapper

open class BpkStarRating @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
) : BpkStarRatingBase(
  context = createContextThemeWrapper(context, attrs, R.attr.bpkStarRatingStyle),
  attrs = attrs,
  defStyleAttr = defStyleAttr,
  empty = R.drawable.bpk_star,
  half = R.drawable.bpk_star_half,
  full = R.drawable.bpk_star,
  starSize = context.resources.getDimensionPixelSize(R.dimen.bpkSpacingBase)
) {

  final override var rating: Float
    get() = super.rating
    set(value) {
      super.rating = value
    }
}
