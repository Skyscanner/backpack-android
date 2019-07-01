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
  empty = R.drawable.bpk_star_outline,
  half = R.drawable.bpk_star,
  full = R.drawable.bpk_star,
  starSize = context.resources.getDimensionPixelSize(R.dimen.bpkSpacingXxl)
) {

  var onRatingChangedListener: ((Float, Float) -> Unit)? = null
    set(value) {
      field = value
      value?.invoke(rating, maxRating.toFloat())
    }

  final override var rating: Float
    get() = super.rating
    set(value) {
      val newValue = Math.round(value).toFloat()
      if (newValue != super.rating) {
        super.rating = newValue
        onRatingChangedListener?.invoke(newValue, maxRating.toFloat())
      }
    }

  final override var maxRating: Int
    get() = super.maxRating
    set(value) {
      if (value != super.maxRating) {
        super.maxRating = value
        onRatingChangedListener?.invoke(rating, value.toFloat())
      }
    }

  override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
    val x = if (layoutDirection == View.LAYOUT_DIRECTION_RTL) width - ev.x else ev.x
    val itemWidth = width / maxRating
    val selectedItems = x / itemWidth
    rating = Math.max(1f, selectedItems + 0.5f)
    return true
  }

  interface OnRatingChangedListener : (Float, Float) -> Unit {
    override fun invoke(current: Float, max: Float)
  }
}
