package net.skyscanner.backpack.starrating.internal

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.Px
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.content.res.ResourcesCompat
import net.skyscanner.backpack.R
import net.skyscanner.backpack.util.use

@SuppressLint("ViewConstructor")
open class BpkStarRatingBase internal constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0,
  @DrawableRes empty: Int,
  @DrawableRes half: Int,
  @DrawableRes full: Int,
  @Px private val starSize: Int
) : LinearLayoutCompat(context, attrs, defStyleAttr) {

  private val empty: Drawable
  private val half: HalfStarDrawable
  private val full: Drawable

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
  open var rating: Float
    get() = _rating
    set(value) {
      _rating = clamp(value, 0f, maxRating.toFloat())
      update()
    }

  init {
    orientation = HORIZONTAL
    var starColor = ResourcesCompat.getColor(resources, R.color.bpkSkyGrayTint06, null)
    var starFilledColor: Int = ResourcesCompat.getColor(resources, R.color.bpkErfoud, null)

    context.theme.obtainStyledAttributes(
      attrs,
      R.styleable.BpkStarRating,
      defStyleAttr, 0
    ).use {
      _maxRating = it.getInt(R.styleable.BpkStarRating_maxRating, maxRating)
      _rating = it.getFloat(R.styleable.BpkStarRating_rating, maxRating / 2f)
      starColor = it.getColor(R.styleable.BpkStarRating_starColor, starColor)
      starFilledColor = it.getColor(R.styleable.BpkStarRating_starFilledColor, starFilledColor)
    }

    this.empty = getDrawable(empty, starColor)
    this.half = HalfStarDrawable(
      this.empty,
      getDrawable(half, starFilledColor)
    )
    this.full = getDrawable(full, starFilledColor)
    update()
  }

  override fun onRtlPropertiesChanged(layoutDirection: Int) {
    super.onRtlPropertiesChanged(layoutDirection)
    update()
  }

  private fun update() {
    half.rtl = layoutDirection == View.LAYOUT_DIRECTION_RTL

    val diff = maxRating - childCount
    if (diff > 0) {
      for (i in 0 until diff) {
        val view = View(context)
        view.background = BpkStar(empty, half, full)
        addView(view, starSize, starSize)
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
      background.value = when {
        (value >= 0.0f && value < 0.5f) -> BpkStar.Value.Empty
        (value >= 0.5f && value < 1.0f) -> BpkStar.Value.Half
        else -> BpkStar.Value.Full
      }
    }
  }

  private fun getDrawable(@DrawableRes id: Int, @ColorInt tint: Int) =
    DrawableCompat.wrap(ContextCompat.getDrawable(context, id)!!.mutate())
      .apply { DrawableCompat.setTint(this, tint) }

  private fun clamp(value: Float, min: Float, max: Float) =
    Math.min(max, Math.max(min, value))
}
