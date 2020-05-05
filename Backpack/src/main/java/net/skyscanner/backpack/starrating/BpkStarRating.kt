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
