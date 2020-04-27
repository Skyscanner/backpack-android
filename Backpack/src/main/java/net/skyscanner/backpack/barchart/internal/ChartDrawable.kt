package net.skyscanner.backpack.barchart.internal

import android.content.res.ColorStateList
import android.graphics.*
import android.graphics.drawable.Drawable
import net.skyscanner.backpack.util.getColorForState
import net.skyscanner.backpack.util.withSave
import kotlin.math.min

internal class ChartDrawable(
  private val background: ColorStateList,
  private val foreground: ColorStateList
) : Drawable() {

  private val backgroundPaint = Paint().apply {
    isAntiAlias = true
    isDither = true
    style = Paint.Style.FILL_AND_STROKE
  }

  private val foregroundPaint = Paint().apply {
    isAntiAlias = true
    isDither = true
    style = Paint.Style.FILL_AND_STROKE
  }

  private val triangle = Path()

  var value: Float = 0f
    set(value) {
      if (field != value) {
        field = value
        invalidateSelf()
      }
    }

  private val totalRange: Float
    get() {
      val minRange = diameter
      val maxRange = bounds.height()
      return maxRange - minRange
    }

  val diameter: Float
    get() = bounds.width().toFloat()

  val radius: Float
    get() = diameter / 2

  val valueInPixels: Float
    get() = totalRange * min(1.0f, value)

  override fun getAlpha(): Int =
    backgroundPaint.alpha

  override fun setAlpha(alpha: Int) {
    backgroundPaint.alpha = alpha
    foregroundPaint.alpha = alpha
  }

  override fun getOpacity(): Int =
    PixelFormat.TRANSPARENT

  override fun setColorFilter(colorFilter: ColorFilter?) {
    backgroundPaint.colorFilter = colorFilter
    foregroundPaint.colorFilter = colorFilter
  }

  override fun getColorFilter(): ColorFilter? =
    backgroundPaint.colorFilter

  override fun isStateful(): Boolean =
    background.isStateful || foreground.isStateful

  override fun onStateChange(state: IntArray?): Boolean =
    isStateful

  override fun onBoundsChange(bounds: Rect) {
    super.onBoundsChange(bounds)

    val width = bounds.width().toFloat()

    triangle.reset()
    triangle.moveTo(0f, radius)
    triangle.lineTo(bounds.centerX().toFloat(), 0f)
    triangle.lineTo(width, radius)
    triangle.lineTo(0f, radius)
  }

  override fun draw(canvas: Canvas) {
    val width = bounds.width().toFloat()
    val height = bounds.height().toFloat()

    backgroundPaint.color = background.getColorForState(state)
    foregroundPaint.color = foreground.getColorForState(state)

    canvas.withSave {

      if (value > 1.0f) {
        canvas.drawPath(triangle, foregroundPaint)
        canvas.clipRect(0f, radius, width, height)
      }

      canvas.drawRoundRect(0f, 0f, width, height, radius, radius, backgroundPaint)
      val foregroundTop = totalRange - valueInPixels
      canvas.drawRoundRect(0f, foregroundTop, width, height, radius, radius, foregroundPaint)
    }
  }
}
