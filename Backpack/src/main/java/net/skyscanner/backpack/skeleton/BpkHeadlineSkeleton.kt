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
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.annotation.AttrRes
import androidx.annotation.DimenRes
import net.skyscanner.backpack.R
import net.skyscanner.backpack.overlay.internal.CornerRadiusViewOutlineProvider
import net.skyscanner.backpack.util.use

class BpkHeadlineSkeleton @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  @AttrRes defStyleAttr: Int = 0,
) : View(context, attrs, defStyleAttr) {
  val paint = Paint().apply {
    color = context.getColor(R.color.__skeletonBackground)
    style = Paint.Style.FILL
  }

  enum class SkeletonHeightSizeType(
    internal val id: Int,
  ) {
    Small(0),
    Medium(1),
    Large(2),
    Custom(3),
  }

  var heightSize = SkeletonHeightSizeType.Small

  @DimenRes
  private fun getHeightSize(size: SkeletonHeightSizeType): Int {
    return when (size) {
      SkeletonHeightSizeType.Custom -> 0
      SkeletonHeightSizeType.Small -> R.dimen.bpkSpacingMd
      SkeletonHeightSizeType.Medium -> R.dimen.bpkSpacingBase
      SkeletonHeightSizeType.Large -> R.dimen.bpkSpacingXl
    }
  }

  init {
    context.obtainStyledAttributes(attrs, R.styleable.BpkHeadlineSkeleton, defStyleAttr, 0).use {
      heightSize = parseHeightTypeAttribute(it, heightSize)
    }

    outlineProvider = CornerRadiusViewOutlineProvider(R.dimen.bpkBorderRadiusXs)
    clipToOutline = true
  }

  override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec)

    if (heightSize === SkeletonHeightSizeType.Custom) return
    val heightSize = context.resources.getDimensionPixelSize(getHeightSize(heightSize))
    setMeasuredDimension(widthMeasureSpec, heightSize)
  }

  override fun onDraw(canvas: Canvas?) {
    val borderRadius = context.resources.getDimensionPixelSize(R.dimen.bpkBorderRadiusXs)
    canvas?.drawRoundRect(0f, 0f, width.toFloat(), height.toFloat(), borderRadius.toFloat(), borderRadius.toFloat(), paint)
  }

  private companion object {
    private fun parseHeightTypeAttribute(it: TypedArray, fallback: SkeletonHeightSizeType) =
      it.getInt(R.styleable.BpkHeadlineSkeleton_skeletonHeadlineHeightSize, fallback.id).let { id ->
        SkeletonHeightSizeType.values().find { it.id == id } ?: fallback
      }
  }
}
