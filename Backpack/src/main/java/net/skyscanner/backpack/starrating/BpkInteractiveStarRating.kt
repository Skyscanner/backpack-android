package net.skyscanner.backpack.starrating

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import net.skyscanner.backpack.R
import net.skyscanner.backpack.starrating.internal.BpkStarRatingBase
import net.skyscanner.backpack.util.createContextThemeWrapper

open class BpkInteractiveStarRating @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
) : BpkStarRatingBase(
  context = createContextThemeWrapper(context, attrs, R.attr.bpkInteractiveStarRatingStyle),
  attrs = attrs,
  defStyleAttr = defStyleAttr,
  empty = R.drawable.bpk_star,
  half = R.drawable.bpk_star_half,
  full = R.drawable.bpk_star,
  starSize = context.resources.getDimensionPixelSize(R.dimen.bpkSpacingXxl)
) {

  var onRatingChangedListener: ((Float, Float) -> Unit)? = null

  final override var rating: Float
    get() = super.rating
    set(value) {
      super.rating = Math.round(value).toFloat()
    }

  override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
    val x = if (layoutDirection == View.LAYOUT_DIRECTION_RTL) width - ev.x else ev.x
    val itemWidth = width / maxRating
    val selectedItems = x / itemWidth
    rating = Math.max(1f, selectedItems + 0.5f)
    onRatingChangedListener?.invoke(rating, maxRating.toFloat())
    return true
  }

  interface OnRatingChangedListener : (Float, Float) -> Unit {

    override fun invoke(current: Float, max: Float)
  }
}
