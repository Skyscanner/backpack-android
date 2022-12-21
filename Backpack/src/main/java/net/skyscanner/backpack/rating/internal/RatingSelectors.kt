/**
 * Backpack for Android - Skyscanner's Design System
 *
 * Copyright 2018 Skyscanner Ltd
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
import android.content.res.ColorStateList
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.graphics.drawable.LayerDrawable
import android.util.AttributeSet
import android.util.TypedValue
import androidx.annotation.StyleableRes
import net.skyscanner.backpack.R
import net.skyscanner.backpack.rating.BpkRating
import net.skyscanner.backpack.util.use

internal class RatingSelectors(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0,
) {

  private val scoresCount = BpkRating.Score.values().size

  private var icons: Drawable? = null
  var icon: (BpkRating.Score) -> Drawable? = {
    icons
      ?.takeIf { d -> d is LayerDrawable && d.numberOfLayers >= scoresCount }
      ?.let { d -> (d as LayerDrawable).getDrawable(it.index) }
      ?: icons
  }

  private var titles: Array<CharSequence>? = null
  var title: (BpkRating.Score) -> CharSequence? = {
    titles?.let { array ->
      if (array.size >= scoresCount) {
        array[it.index]
      } else {
        array.getOrNull(0)
      }
    }
  }

  private var subtitles: Array<CharSequence>? = null
  var subtitle: (BpkRating.Score) -> CharSequence? = {
    subtitles?.let { array ->
      if (array.size >= scoresCount) {
        array[it.index]
      } else {
        array.getOrNull(0)
      }
    }
  }

  val backgroundColor: (BpkRating.Score) -> ColorStateList

  init {
    var colorLow = context.getColorStateList(R.color.bpkStatusDangerFill)
    var colorMedium = context.getColorStateList(R.color.bpkStatusWarningFill)
    var colorHigh = context.getColorStateList(R.color.bpkStatusSuccessFill)

    context.theme.obtainStyledAttributes(
      attrs,
      R.styleable.BpkRating,
      defStyleAttr, 0,
    ).use {
      colorLow = it.getColorStateList(R.styleable.BpkRating_ratingColorLow) ?: colorLow
      colorMedium = it.getColorStateList(R.styleable.BpkRating_ratingColorMedium) ?: colorMedium
      colorHigh = it.getColorStateList(R.styleable.BpkRating_ratingColorHigh) ?: colorHigh

      icons = it.getDrawable(R.styleable.BpkRating_ratingIcon)
      titles = it.resolveStringOrArray(R.styleable.BpkRating_ratingTitle)
      subtitles = it.resolveStringOrArray(R.styleable.BpkRating_ratingSubtitle)
    }

    val backgroundColors = arrayOf(
      colorLow,
      colorMedium,
      colorHigh,
    )

    backgroundColor = {
      backgroundColors[it.index]
    }
  }

  private fun TypedArray.resolveStringOrArray(@StyleableRes index: Int): Array<CharSequence>? {
    val tv = TypedValue()
    if (getValue(index, tv)) {

      if (tv.string != null) {
        return arrayOf(tv.string)
      }

      if (tv.resourceId != 0) {
        if (resources.getResourceTypeName(tv.resourceId) == "string") {
          return arrayOf(resources.getString(tv.resourceId))
        }
      }

      return resources.getTextArray(tv.resourceId)
    }
    return null
  }

  private val BpkRating.Score.index: Int
    get() = when (this) {
      BpkRating.Score.Low -> 0
      BpkRating.Score.Medium -> 1
      BpkRating.Score.High -> 2
    }
}
