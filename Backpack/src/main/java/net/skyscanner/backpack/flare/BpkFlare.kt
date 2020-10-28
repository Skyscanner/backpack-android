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

package net.skyscanner.backpack.flare

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.RectF
import android.graphics.Region
import android.os.Build
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.content.res.AppCompatResources
import net.skyscanner.backpack.R
import net.skyscanner.backpack.util.unsafeLazy
import net.skyscanner.backpack.util.use

/**
 * [BpkFlare] is designed to render a single item inside a "bubble".
 * Think of message apps and how they show content inside text bubbles.
 *
 * This view uses bitmap masking to mask the flare shape and corners.
 * When hardware acceleration is enabled this doesn't work as expected, so hardware
 * acceleration is disabled for this view.
 *
 * [BpkFlare] is designed to work with only one child and will throw an error if more than
 * one is provided.
 *
 * No background or padding should be set to this view, set it directly to its
 * child.
 */
open class BpkFlare @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

  enum class PointerPosition(internal val id: Int, internal val offset: Float) {
    START(0, 0.25f),
    MIDDLE(1, 0.5f),
    END(2, 0.75f),
  }

  enum class PointerDirection(internal val id: Int) {
    DOWN(0),
    UP(1),
  }

  enum class InsetPaddingMode(internal val id: Int) {
    NONE(0),
    BOTTOM(1),
    TOP(2),
  }

  private val pointerMask by unsafeLazy {
    val pointerDrawable = AppCompatResources.getDrawable(context, R.drawable.flare_default_pointer)!!.apply {
      setBounds(0, 0, intrinsicWidth, intrinsicHeight)
    }
    Bitmap.createBitmap(pointerDrawable.intrinsicWidth, pointerDrawable.intrinsicHeight, Bitmap.Config.ARGB_8888).apply {
      pointerDrawable.draw(Canvas(this))
    }
  }

  private val radiusMask by unsafeLazy {
    val radiiDrawable = AppCompatResources.getDrawable(context, R.drawable.flare_default_radius)!!.apply {
      setBounds(0, 0, intrinsicWidth, intrinsicHeight)
    }
    Bitmap.createBitmap(radiiDrawable.intrinsicWidth, radiiDrawable.intrinsicHeight, Bitmap.Config.ARGB_8888).apply {
      radiiDrawable.draw(Canvas(this))
    }
  }

  private val clipRect = RectF()
  private val paint = Paint().apply {
    xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_OUT)
    isAntiAlias = true
  }

  /**
   * Specify if corner radius should be added or not.
   */
  var round = false
    set(value) {
      field = value
      requestLayout()
    }

  /**
   * Specify the horizontal position of the flare.
   *
   * @see [PointerPosition]
   */
  var pointerPosition = PointerPosition.MIDDLE
    set(value) {
      field = value
      requestLayout()
    }

  /**
   * Specify the vertical direction of the flare.
   *
   * @see [PointerDirection]
   */
  var pointerDirection = PointerDirection.DOWN
    set(value) {
      field = value
      requestLayout()
    }

  /**
   * Specify if extra padding should be added to account
   * for the view clipping used to display the flare pointer.
   *
   * @see [InsetPaddingMode]
   */
  var insetPaddingMode = InsetPaddingMode.NONE
    set(value) {
      field = value
      requestLayout()
    }

  init {
    initialize(attrs, defStyleAttr)
  }

  private fun initialize(attrs: AttributeSet?, defStyleAttr: Int) {
    context.theme.obtainStyledAttributes(attrs, R.styleable.BpkFlare, defStyleAttr, 0)
      ?.use {
        round = it.getBoolean(R.styleable.BpkFlare_flareRound, round)
        pointerPosition = it.getInt(R.styleable.BpkFlare_flarePointerPosition, pointerPosition.id)
          .let(::mapXmlToPointerPosition) ?: pointerPosition

        pointerDirection = it.getInt(R.styleable.BpkFlare_flarePointerDirection, pointerDirection.id)
          .let(::mapXmlToPointerDirection) ?: pointerDirection

        insetPaddingMode = it.getInt(R.styleable.BpkFlare_flareInsetPaddingMode, insetPaddingMode.id)
          .let(::mapXmlToInsetPaddingMode) ?: insetPaddingMode
      }

    background = null
    setWillNotDraw(false)
    setPadding(0, 0, 0, 0)
    // PorterDuffXfermode doesn't work in some devices when hardware acceleration is on
    setLayerType(LAYER_TYPE_SOFTWARE, null)
  }

  override fun onViewAdded(child: View) {
    super.onViewAdded(child)
    if (this.childCount > 1) {
      throw IllegalStateException("BpkFlare should have only one child")
    }

    when (insetPaddingMode) {
      InsetPaddingMode.NONE -> {}
      InsetPaddingMode.BOTTOM ->
        setPaddingVertical(child, child.paddingTop, child.paddingBottom + pointerMask.height)
      InsetPaddingMode.TOP ->
        setPaddingVertical(child, child.paddingTop + pointerMask.height, child.paddingBottom)
    }
  }

  override fun draw(canvas: Canvas) {
    val count = canvas.saveCount

    val width = width.toFloat()
    val height = height.toFloat()

    val pointerHalfWidth = pointerMask.width / 2
    val pointerOffset = if (layoutDirection == View.LAYOUT_DIRECTION_RTL) {
      1 - pointerPosition.offset
    } else {
      pointerPosition.offset
    }

    val pointerYStart = height - pointerMask.height
    val pointerXStart = width * pointerOffset - pointerHalfWidth
    val pointerXEnd = width * pointerOffset + pointerHalfWidth

    when (pointerDirection) {
      PointerDirection.DOWN ->
        clipPointerArea(pointerYStart, pointerXStart, height, canvas, width, pointerXEnd)
      PointerDirection.UP ->
        clipPointerArea(0f, pointerXStart, pointerMask.height.toFloat(), canvas, width, pointerXEnd)
    }

    super.draw(canvas)
    canvas.restoreToCount(count)

    drawPointerMask(canvas, pointerXStart, pointerYStart)

    if (round) {
      drawRadiusMask(canvas)
    }
  }

  private fun clipPointerArea(
    pointerYStart: Float,
    pointerXStart: Float,
    height: Float,
    canvas: Canvas,
    width: Float,
    pointerXEnd: Float
  ) {
    clipRect.set(0f, pointerYStart, pointerXStart, height)
    canvas.clipOutRectCompat(clipRect)

    clipRect.set(width, pointerYStart, pointerXEnd, height)
    canvas.clipOutRectCompat(clipRect)
  }

  private fun drawPointerMask(canvas: Canvas, pointerXStart: Float, pointerYStart: Float) {
    val count = canvas.saveCount
    val width = width.toFloat()
    val height = height.toFloat()

    when (pointerDirection) {
      PointerDirection.UP -> {
        canvas.rotate(180f, width / 2, height / 2)
        canvas.drawBitmap(pointerMask, pointerXStart, pointerYStart, paint)
      }
      PointerDirection.DOWN ->
        canvas.drawBitmap(pointerMask, pointerXStart, pointerYStart, paint)
    }

    canvas.restoreToCount(count)
  }

  private fun drawRadiusMask(canvas: Canvas) {
    val width = width.toFloat()
    val height = height.toFloat()
    val radiusHeight = radiusMask.height
    val radiusWidth = radiusMask.width
    val radiusHalfHeight = radiusHeight.toFloat() / 2
    val radiusHalfWidth = radiusHeight.toFloat() / 2
    val pointerHeight = pointerMask.height

    val count = canvas.saveCount

    // bottom left corner
    canvas.drawBitmap(radiusMask, 0f, height - pointerHeight - radiusHeight, paint)

    // top right corner
    canvas.rotate(180f, radiusHalfWidth, radiusHalfHeight)
    canvas.drawBitmap(radiusMask, -(width - radiusWidth), 0f, paint)

    // top left corner
    canvas.rotate(-90f, radiusHalfWidth, radiusHalfHeight)
    canvas.drawBitmap(radiusMask, 0f, 0f, paint)

    // bottom right corner
    canvas.rotate(-180f, radiusHalfWidth, radiusHalfHeight)
    canvas.drawBitmap(radiusMask, -(height - pointerHeight - radiusHeight), width - radiusWidth, paint)

    canvas.restoreToCount(count)
  }

  private fun mapXmlToPointerPosition(id: Int) =
    PointerPosition.values().find { it.id == id }

  private fun mapXmlToPointerDirection(id: Int) =
    PointerDirection.values().find { it.id == id }

  private fun mapXmlToInsetPaddingMode(id: Int) =
    InsetPaddingMode.values().find { it.id == id }

  private fun setPaddingVertical(child: View, paddingTop: Int, paddingBottom: Int) {
    if (child.paddingStart > 0 || child.paddingEnd > 0) {
      child.setPaddingRelative(child.paddingStart, paddingTop, child.paddingEnd, paddingBottom)
    } else {
      child.setPadding(child.paddingLeft, paddingTop, child.paddingRight, paddingBottom)
    }
  }
}

private fun Canvas.clipOutRectCompat(rect: RectF) {
  if (Build.VERSION.SDK_INT >= 26) {
    this.clipOutRect(rect)
  } else {
    @Suppress("DEPRECATION")
    this.clipRect(rect, Region.Op.DIFFERENCE)
  }
}
