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
import android.graphics.Outline
import android.util.AttributeSet
import android.view.View
import android.view.ViewOutlineProvider
import android.widget.RelativeLayout
import androidx.annotation.AttrRes
import androidx.annotation.DimenRes
import androidx.core.view.updateLayoutParams
import net.skyscanner.backpack.R
import net.skyscanner.backpack.util.use

class BpkCircleSkeleton @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  @AttrRes defStyleAttr: Int = 0,
) : BpkSkeletonBase(context, attrs, defStyleAttr) {
  enum class CircleSize(
    internal val id: Int,
  ) {
    Small(0),
    Large(1),
  }

  var size = CircleSize.Small
    set(value) {
      field = value

      val skeletonView = findViewById<RelativeLayout>(R.id.bpk_skeleton_container)
      val diameterSize = context.resources.getDimensionPixelSize(getDiameterSize(value))
      skeletonView.updateLayoutParams {
        width = diameterSize
        height = diameterSize
      }
      outlineProvider = object : ViewOutlineProvider() {
        override fun getOutline(view: View?, outline: Outline?) {
          outline?.setRoundRect(0, 0, view!!.width, view!!.height, diameterSize.toFloat() / 2)
        }
      }
      clipToOutline = true
    }

  @DimenRes
  private fun getDiameterSize(size: CircleSize): Int {
    return when (size) {
      CircleSize.Small -> R.dimen.bpk_circle_skeleton_sm_width
      CircleSize.Large -> R.dimen.bpk_circle_skeleton_lg_width
    }
  }

  init {
    inflate(this.context, R.layout.view_bpk_skeleton_circle, this)

    setShimmerBackgroundColor(this)
    context.obtainStyledAttributes(attrs, R.styleable.BpkCircleSkeleton, defStyleAttr, 0).use {
      size = parseSizeAttribute(it, size)
    }

    startShimmer(findViewById(R.id.bpk_skeleton_shimmer))
  }

  private companion object {
    private fun parseSizeAttribute(it: TypedArray, fallback: CircleSize) =
      it.getInt(R.styleable.BpkCircleSkeleton_circleSize, fallback.id).let { id ->
        CircleSize.values().find { it.id == id } ?: fallback
      }
  }
}
