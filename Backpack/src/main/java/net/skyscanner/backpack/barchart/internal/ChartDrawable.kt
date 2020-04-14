package net.skyscanner.backpack.barchart.internal

import android.content.res.ColorStateList
import android.graphics.*
import android.graphics.drawable.Drawable
import kotlin.math.min
import kotlin.math.roundToInt

internal class ChartDrawable(
  private val background: ColorStateList,
  private val foreground: ColorStateList
) : Drawable() {

  private val paint = Paint().apply {
    isAntiAlias = true
    isDither = true
    style = Paint.Style.FILL_AND_STROKE
  }

  private val cap = Path()

  private val rect = RectF()

  var value: Float = 0f
    set(value) {
      if (field != value) {
        field = value
        invalidateSelf()
      }
    }

  val valueInPixels: Float
    get() {
      val height = bounds.height()
      val radius = bounds.width() / 2
      val totalRange = height - radius - radius
      return totalRange * value + radius
    }

  override fun getAlpha(): Int =
    paint.alpha

  override fun setAlpha(alpha: Int) {
    paint.alpha = alpha
  }

  override fun getOpacity(): Int =
    PixelFormat.TRANSPARENT

  override fun setColorFilter(colorFilter: ColorFilter?) {
    paint.colorFilter = colorFilter
  }

  override fun getColorFilter(): ColorFilter? =
    paint.colorFilter

  override fun isStateful(): Boolean =
    background.isStateful || foreground.isStateful

  override fun onStateChange(state: IntArray?): Boolean =
    isStateful

  @Suppress("UnnecessaryVariable")
  override fun draw(canvas: Canvas) {
    val width = bounds.width()
    val height = bounds.height()

    val diameter = width
    val radius = diameter / 2f

    // drawing the background
    rect.set(bounds)
    paint.color = background.getColorForState(state, background.defaultColor)
    canvas.drawRoundRect(rect, radius, radius, paint)

    // drawing the foreground
    val minRange = diameter
    val maxRange = height
    val totalRange = maxRange - minRange
    val activeRange = (totalRange * min(1.0f, value)).roundToInt()
    val inactiveRange = totalRange - activeRange
    paint.color = foreground.getColorForState(state, foreground.defaultColor)
    rect.top = rect.top + inactiveRange
    canvas.drawRoundRect(rect, radius, radius, paint)

    // drawing the cap
    if (value > 1.0f) {
      cap.reset()
      val capCy = bounds.top.toFloat()
      val capCx = bounds.centerX().toFloat()

      val capLeft = bounds.left.toFloat()
      val capTop = capCy - radius
      val capRight = bounds.right.toFloat()
      val capBottom = capCy + radius

      cap.moveTo(capLeft, capBottom)

      cap.lineTo(capLeft, capCy)
      cap.lineTo(capCx, capTop)
      cap.lineTo(capRight, capCy)
      cap.lineTo(capRight, capBottom)
      cap.lineTo(capLeft, capBottom)
      canvas.drawPath(cap, paint)
    }
  }
}
