/**
 * Backpack for Android - Skyscanner's Design System
 *
 * Copyright 2025 Skyscanner Ltd
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
import androidx.annotation.Dimension
import net.skyscanner.backpack.R
import net.skyscanner.backpack.util.use

class BpkCircleSkeleton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    @AttrRes defStyleAttr: Int = 0,
) : View(context, attrs, defStyleAttr) {
    val paint = Paint().apply {
        color = context.getColor(R.color.bpkSurfaceHighlight)
        style = Paint.Style.FILL
        isAntiAlias = true
    }

    enum class CircleSize(
        internal val id: Int,
    ) {
        /**
         * Small size: 32.dp
         */
        Small(0),

        /**
         * Large size: 48.dp
         */
        Large(1),

        /**
         * Custom. Need decide the size by diameter property.
         */
        Custom(2),
    }

    /**
     * Only for CircleSize.Custom
     */
    @Dimension
    var diameter = 0
        set(value) {
            field = value
            invalidate()
        }

    /**
     * Small: 32dp, Large: 48dp, or use Custom and then set diameter property.
     */
    var size = CircleSize.Small
        set(value) {
            field = value
            invalidate()
        }

    init {
        context.obtainStyledAttributes(attrs, R.styleable.BpkCircleSkeleton, defStyleAttr, 0).use {
            diameter = parseDiameterAttribute(it, diameter)
            size = parseCircleSizeAttribute(it, size)
        }
    }

    @Dimension
    private fun getInternalSize(): Int {
        return when (size) {
            CircleSize.Custom -> diameter
            CircleSize.Small -> context.resources.getDimensionPixelSize(R.dimen.bpkSpacingXl)
            CircleSize.Large -> context.resources.getDimensionPixelSize(R.dimen.bpkSpacingLg) * 2
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val internalSize = getInternalSize()
        setMeasuredDimension(internalSize, internalSize)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        val internalSize = getInternalSize()
        super.onLayout(changed, 0, 0, internalSize, internalSize)
    }

    override fun onDraw(canvas: Canvas) {
        val internalSize = getInternalSize()
        val radius = internalSize.toFloat().div(2)
        canvas.drawCircle(radius, radius, radius, paint)
    }

    private companion object {
        @Dimension
        private fun parseDiameterAttribute(it: TypedArray, fallback: Int) =
            it.getDimensionPixelSize(R.styleable.BpkCircleSkeleton_skeletonDiameter, fallback)

        private fun parseCircleSizeAttribute(it: TypedArray, fallback: CircleSize) =
            it.getInt(R.styleable.BpkCircleSkeleton_skeletonCircleSize, fallback.id).let { id ->
                CircleSize.entries.find { it.id == id } ?: fallback
            }
    }
}
