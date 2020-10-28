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

package net.skyscanner.backpack.starrating.internal

import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.graphics.drawable.LayerDrawable

internal class BpkStar(
  private val empty: Drawable,
  private val half: Drawable,
  private val full: Drawable
) : LayerDrawable(
  arrayOf(
    empty,
    half,
    full
  )
) {

  enum class Value {
    Empty,
    Half,
    Full
  }

  internal var value = Value.Empty
    set(value) {
      if (field != value) {
        field = value
        invalidateSelf()
      }
    }

  override fun draw(canvas: Canvas) {
    when (value) {
      Value.Empty -> empty.draw(canvas)
      Value.Half -> half.draw(canvas)
      Value.Full -> full.draw(canvas)
    }
  }
}
