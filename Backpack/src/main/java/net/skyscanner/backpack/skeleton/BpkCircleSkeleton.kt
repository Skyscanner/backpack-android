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
import net.skyscanner.backpack.R
import net.skyscanner.backpack.util.use

class BpkCircleSkeleton @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  @AttrRes defStyleAttr: Int = 0,
) : View(context, attrs, defStyleAttr) {
  val paint = Paint().apply {
    color = context.getColor(R.color.__skeletonBackground)
    style = Paint.Style.FILL
  }

  enum class CircleSize(
    internal val id: Int,
  ) {
    Small(0),
    Large(1),
    Custom(2),
  }

  var diameter = 0
    set(value) {
      field = value
      invalidate()
    }

  var size = CircleSize.Small
    set(value) {
      field = value
      diameter = when (value) {
        CircleSize.Custom -> diameter
        CircleSize.Small -> context.resources.getDimensionPixelSize(R.dimen.bpkSpacingXl)
        CircleSize.Large -> context.resources.getDimensionPixelSize(R.dimen.bpkSpacingLg) * 2
      }
      invalidate()
    }

  init {
    context.obtainStyledAttributes(attrs, R.styleable.BpkCircleSkeleton, defStyleAttr, 0).use {
      diameter = parseDiameterAttribute(it, diameter)
      size = parseCircleSizeAttribute(it, size)
    }
  }

  override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    setMeasuredDimension(diameter, diameter)
  }

  override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
    super.onLayout(changed, 0, 0, diameter, diameter)
  }

  override fun onDraw(canvas: Canvas?) {
    val radius = diameter.toFloat().div(2)
    canvas?.drawCircle(radius, radius, radius, paint)
  }

  private companion object {
    private fun parseDiameterAttribute(it: TypedArray, fallback: Int) =
      it.getDimensionPixelSize(R.styleable.BpkCircleSkeleton_skeletonDiameter, fallback)

    private fun parseCircleSizeAttribute(it: TypedArray, fallback: CircleSize) =
      it.getInt(R.styleable.BpkCircleSkeleton_skeletonCircleSize, fallback.id).let { id ->
        CircleSize.values().find { it.id == id } ?: fallback
      }
  }
}
