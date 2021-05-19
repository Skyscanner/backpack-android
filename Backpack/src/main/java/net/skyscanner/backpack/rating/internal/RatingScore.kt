/**
 * Backpack for Android - Skyscanner's Design System
 *
 * Copyright 2018-2021 Skyscanner Ltd
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.skyscanner.backpack.rating.internal

import android.content.Context
import android.util.AttributeSet
import androidx.core.math.MathUtils.clamp
import net.skyscanner.backpack.R
import net.skyscanner.backpack.rating.BpkRating
import net.skyscanner.backpack.util.use

internal class RatingScore(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0,
  defaultScale: BpkRating.Scale
) : () -> BpkRating.Score {

  var rating: Float = 0f
    set(value) {
      val clamped = clamp(value, minRating, maxRating)
      field = (clamped * 10).toInt() / 10f // rounding to one decimal
    }

  var scale: BpkRating.Scale = defaultScale

  override fun toString() =
    rating.toString()

  override fun invoke(): BpkRating.Score = when {
    rating >= minRating && rating < mediumRatingThreshold -> BpkRating.Score.Low
    rating >= mediumRatingThreshold && rating < highRatingThreshold -> BpkRating.Score.Medium
    rating in highRatingThreshold..maxRating -> BpkRating.Score.High
    else -> throw IllegalArgumentException("Invalid rating=$rating")
  }

  init {
    context.theme.obtainStyledAttributes(
      attrs,
      R.styleable.BpkRating,
      defStyleAttr, 0
    ).use {
      scale = it.getInt(R.styleable.BpkRating_ratingScale, scale.xmlId)
        .let(::mapXmlToScale) ?: scale
      rating = it.getFloat(R.styleable.BpkRating_ratingValue, rating)
    }
  }

  private fun mapXmlToScale(id: Int) =
    BpkRating.Scale.values().find { it.xmlId == id }

  private val BpkRating.Scale.xmlId
    get() = when (this) {
      BpkRating.Scale.ZeroToTen -> 0
      BpkRating.Scale.ZeroToFive -> 1
    }

  // currently always 0
  private val minRating: Float = 0.0f

  private val maxRating: Float
    get() = when (scale) {
      BpkRating.Scale.ZeroToTen -> 10.0f
      BpkRating.Scale.ZeroToFive -> 5.0f
    }

  private val mediumRatingThreshold: Float
    get() = maxRating * 0.6f

  private val highRatingThreshold: Float
    get() = maxRating * 0.8f
}
