/**
 * Backpack for Android - Skyscanner's Design System
 *
 * Copyright 2018-2020 Skyscanner Ltd
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

package net.skyscanner.backpack.starrating

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import net.skyscanner.backpack.R
import net.skyscanner.backpack.starrating.internal.BpkStarRatingBase
import net.skyscanner.backpack.util.createContextThemeWrapper
import kotlin.math.max
import kotlin.math.roundToInt

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

  final override var rating: Float
    get() = super.rating
    set(value) {
      val newValue = value.roundToInt().toFloat()
      if (newValue != super.rating) {
        super.rating = newValue
        onRatingChangedListener?.invoke(newValue, maxRating.toFloat())
      }
    }

  private var lastRating: Float = rating

  override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
    if (ev.action == MotionEvent.ACTION_DOWN) {
      lastRating = rating
    }

    val x = if (layoutDirection == View.LAYOUT_DIRECTION_RTL) width - ev.x else ev.x
    val itemWidth = width / maxRating
    val selectedItems = x / itemWidth
    super.rating = max(1f, selectedItems + 0.5f).roundToInt().toFloat()

    if (ev.action == MotionEvent.ACTION_UP) {
      onRatingChangedListener?.invoke(rating, maxRating.toFloat())
    }
    if (ev.action == MotionEvent.ACTION_CANCEL) {
      super.rating = lastRating
    }

    return true
  }

  interface OnRatingChangedListener : (Float, Float) -> Unit {
    override fun invoke(current: Float, max: Float)
  }
}
