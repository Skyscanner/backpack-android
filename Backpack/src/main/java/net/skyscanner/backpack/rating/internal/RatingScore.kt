package net.skyscanner.backpack.rating.internal

import android.content.Context
import android.util.AttributeSet
import androidx.annotation.FloatRange
import androidx.core.math.MathUtils.clamp
import net.skyscanner.backpack.R
import net.skyscanner.backpack.rating.BpkRating
import net.skyscanner.backpack.util.use

internal class RatingScore(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
) : () -> BpkRating.Score {

  @FloatRange(from = 0.0, to = 10.0)
  var rating: Float = 0f
    set(value) {
      val clamped = clamp(value, 0.0f, 10.0f)
      field = (clamped * 10).toInt() / 10f // rounding to one decimal
    }

  override fun toString() =
    rating.toString()

  override fun invoke(): BpkRating.Score = when {
    rating >= 0f && rating < 6f -> BpkRating.Score.Low
    rating >= 6 && rating < 8f -> BpkRating.Score.Medium
    rating in 8f..10f -> BpkRating.Score.High
    else -> throw IllegalArgumentException("Invalid rating=$rating")
  }

  init {
    context.theme.obtainStyledAttributes(
      attrs,
      R.styleable.BpkRating,
      defStyleAttr, 0
    ).use {
      rating = it.getFloat(R.styleable.BpkRating_ratingValue, rating)
    }
  }
}
