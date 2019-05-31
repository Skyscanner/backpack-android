package net.skyscanner.backpack.starrating

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.annotation.ColorInt
import androidx.appcompat.widget.LinearLayoutCompat
import net.skyscanner.backpack.R
import net.skyscanner.backpack.util.BpkTheme
import net.skyscanner.backpack.util.BpkTheme.Companion.wrapContextWithDefaults
import net.skyscanner.backpack.util.createContextThemeWrapper

open class BpkStarRating @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
) : LinearLayoutCompat(wrapContext(context, attrs), attrs, defStyleAttr) {

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

  private var _starColor: Int = BpkTheme.getColor(context, R.color.bpkGray100)
  var starColor: Int
    @ColorInt
    get() = _starColor
    set(@ColorInt value) {
      if (_starColor != value) {
        _starColor = value
        update()
      }
    }

  private var _starFilledColor: Int = BpkTheme.getColor(context, R.color.bpkYellow500)
  var starFilledColor: Int
    @ColorInt
    get() = _starFilledColor
    set(@ColorInt value) {
      if (_starFilledColor != value) {
        _starFilledColor = value
        update()
      }
    }

  init {
    initialize(attrs, defStyleAttr)
  }

  private fun initialize(attrs: AttributeSet?, defStyleAttr: Int) {
    orientation = HORIZONTAL
    attrs
      ?.let {
        context.theme.obtainStyledAttributes(
          it,
          R.styleable.BpkStarRating,
          defStyleAttr, 0)
      }
      ?.also {
        _maxRating = it.getInt(R.styleable.BpkStarRating_maxRating, maxRating)
        _rating = it.getFloat(R.styleable.BpkStarRating_rating, maxRating / 2f)
        _starColor = it.getColor(R.styleable.BpkStarRating_starColor, starColor)
        _starFilledColor = it.getColor(R.styleable.BpkStarRating_starFilledColor, starFilledColor)
      }
      ?.recycle()
    update()
  }

  private fun update() {
    val diff = maxRating - childCount
    if (diff > 0) {
      for (i in 0 until diff) {
        val view = View(context)
        view.background = BpkStar(
          context = context,
          starFilledColor = starFilledColor,
          starColor = starColor
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
        starColor = starColor
      )
    }
  }
}

private fun wrapContext(context: Context, attrs: AttributeSet?): Context {
  val withBaseStyle = wrapContextWithDefaults(context)
  return createContextThemeWrapper(withBaseStyle, attrs, R.attr.bpkStarRatingStyle)
}

private fun clamp(value: Float, min: Float, max: Float) =
  Math.min(max, Math.max(min, value))
