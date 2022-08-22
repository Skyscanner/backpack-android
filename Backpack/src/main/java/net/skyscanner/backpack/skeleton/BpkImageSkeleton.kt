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

package net.skyscanner.backpack.skeleton

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.View
import androidx.annotation.AttrRes
import net.skyscanner.backpack.R
import net.skyscanner.backpack.overlay.internal.CornerRadiusViewOutlineProvider
import net.skyscanner.backpack.overlay.internal.EmptyViewOutlineProvider
import net.skyscanner.backpack.util.use

class BpkImageSkeleton @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  @AttrRes defStyleAttr: Int = 0,
) : View(context, attrs, defStyleAttr) {

  enum class CornerType(
    internal val id: Int,
  ) {
    Square(0),
    Rounded(1),
  }

  var cornerType: CornerType = CornerType.Square
    set(value) {
      field = value
      if (value === CornerType.Rounded) {
        outlineProvider = CornerRadiusViewOutlineProvider(R.dimen.bpkBorderRadiusSm)
        clipToOutline = true
      } else {
        outlineProvider = EmptyViewOutlineProvider
        clipToOutline = false
      }
    }

  init {
    context.obtainStyledAttributes(attrs, R.styleable.BpkImageSkeleton, defStyleAttr, 0).use {
      cornerType = parseCornerAttribute(it, cornerType)
    }
    this.setBackgroundColor(context.getColor(R.color.__skeletonBackground))
  }

  private companion object {
    private fun parseCornerAttribute(it: TypedArray, fallback: CornerType) =
      it.getInt(R.styleable.BpkImageSkeleton_skeletonCornerType, fallback.id).let { id ->
        CornerType.values().find { it.id == id } ?: fallback
      }
  }
}
