package net.skyscanner.backpack.starrating

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.annotation.ColorInt
import androidx.appcompat.widget.LinearLayoutCompat
import net.skyscanner.backpack.R
import net.skyscanner.backpack.util.BpkTheme
import net.skyscanner.backpack.util.createContextThemeWrapper

open class BpkStarRating @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
) : LinearLayoutCompat(createContextThemeWrapper(context, attrs, R.attr.bpkStarRatingStyle), attrs, defStyleAttr) {

  private var _maxRating: Int = 5
  var maxRating: Int
    get() = _maxRating
    set(value) {
      if (value < 0) {
        throw IllegalArgumentException("Invalid maxRating=$value")
      }
      _maxRating = value
      update()
    }

  private var _rating: Float = maxRating / 2f
  var rating: Float
    get() = _rating
    set(value) {
      _rating = clamp(value, 0f, maxRating.toFloat())
      update()
    }

  @ColorInt
  private var starColor: Int = BpkTheme.getColor(context, R.color.bpkGray100)

  @ColorInt
  private var starFilledColor: Int = BpkTheme.getColor(context, R.color.bpkYellow500)

  init {
    initialize(attrs, defStyleAttr)
  }

  private fun initialize(attrs: AttributeSet?, defStyleAttr: Int) {
    orientation = HORIZONTAL
    context.theme.obtainStyledAttributes(
      attrs,
      R.styleable.BpkStarRating,
      defStyleAttr, 0
    )
      ?.also {
        _maxRating = it.getInt(R.styleable.BpkStarRating_maxRating, maxRating)
        _rating = it.getFloat(R.styleable.BpkStarRating_rating, maxRating / 2f)
        starColor = it.getColor(R.styleable.BpkStarRating_starColor, starColor)
        starFilledColor = it.getColor(R.styleable.BpkStarRating_starFilledColor, starFilledColor)
      }
      ?.recycle()
    update()
  }

  override fun onRtlPropertiesChanged(layoutDirection: Int) {
    super.onRtlPropertiesChanged(layoutDirection)
    update()
  }

  private fun update() {
    val rtl = layoutDirection == View.LAYOUT_DIRECTION_RTL
    val diff = maxRating - childCount
    if (diff > 0) {
      for (i in 0 until diff) {
        val view = View(context)
        view.background = BpkStar(
          context = context,
          starFilledColor = starFilledColor,
          starColor = starColor,
          rtl = rtl
        )
        val dimension = resources.getDimensionPixelSize(R.dimen.bpkSpacingBase)
        addView(view, dimension, dimension)
      }
    } else if (diff < 0) {
      for (i in 0 until -diff) {
        removeViewAt(0)
      }
    }

    for (i in 0 until maxRating) {
      val child = getChildAt(i)
      val background = child.background as BpkStar
      val value = clamp(rating - i, 0f, 1f)
      background.update(
        value = value,
        starFilledColor = starFilledColor,
        starColor = starColor,
        rtl = rtl
      )
    }
  }
}

private fun clamp(value: Float, min: Float, max: Float) =
  Math.min(max, Math.max(min, value))
