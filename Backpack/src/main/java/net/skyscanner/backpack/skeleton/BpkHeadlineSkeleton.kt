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

  enum class SkeletonSize(
    internal val id: Int,
  ) {
    None(0),
    Small(1),
    Medium(2),
    Large(3),
  }

  var size = SkeletonSize.None

  @DimenRes
  private fun getHeightSize(size: SkeletonSize): Int {
    return when (size) {
      SkeletonSize.None -> 0
      SkeletonSize.Small -> R.dimen.bpkSpacingMd
      SkeletonSize.Medium -> R.dimen.bpkSpacingBase
      SkeletonSize.Large -> R.dimen.bpkSpacingXl
    }
  }

  init {
    context.obtainStyledAttributes(attrs, R.styleable.BpkHeadlineSkeleton, defStyleAttr, 0).use {
      size = parseSizeAttribute(it, size)
    }

    outlineProvider = CornerRadiusViewOutlineProvider(R.dimen.bpkBorderRadiusXs)
    clipToOutline = true
  }

  override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec)

    if (size === SkeletonSize.None) return
    val heightSize = context.resources.getDimensionPixelSize(getHeightSize(size))
    setMeasuredDimension(widthMeasureSpec, heightSize)
  }

  override fun onDraw(canvas: Canvas?) {
    val borderRadius = context.resources.getDimensionPixelSize(R.dimen.bpkBorderRadiusXs)
    canvas?.drawRoundRect(0f, 0f, width.toFloat(), height.toFloat(), borderRadius.toFloat(), borderRadius.toFloat(), paint)
  }

  private companion object {
    private fun parseSizeAttribute(it: TypedArray, fallback: SkeletonSize) =
      it.getInt(R.styleable.BpkHeadlineSkeleton_skeletonSize, fallback.id).let { id ->
        SkeletonSize.values().find { it.id == id } ?: fallback
      }
  }
}
