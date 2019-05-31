package net.skyscanner.backpack.starrating

import android.content.Context
import android.graphics.Canvas
import android.graphics.ColorFilter
import android.graphics.PixelFormat
import android.graphics.Rect
import android.graphics.drawable.Drawable
import androidx.annotation.ColorInt
import androidx.annotation.FloatRange
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import net.skyscanner.backpack.R

private const val MIN_VALUE = 0.0f
private const val HALF_VALUE = 0.5f
private const val MAX_VALUE = 1.0f

internal class BpkStar(
  context: Context,
  @ColorInt private var starFilledColor: Int,
  @ColorInt private var starColor: Int,
  private var rtl: Boolean
) : Drawable() {

  private val star = DrawableCompat.wrap(ContextCompat.getDrawable(context, R.drawable.bpk_star)!!.mutate())
  private val half = DrawableCompat.wrap(ContextCompat.getDrawable(context, R.drawable.bpk_star_half)!!.mutate())

  @FloatRange(from = 0.0, to = 1.0)
  private var value: Float = MIN_VALUE

  init {
    invalidate()
  }

  fun update(
    @FloatRange(from = 0.0, to = 1.0) value: Float,
    @ColorInt starFilledColor: Int,
    @ColorInt starColor: Int,
    rtl: Boolean
  ) {
    if (value !in MIN_VALUE..MAX_VALUE) {
      throw IllegalArgumentException("Invalid value $value")
    }

    var invalidate = false

    if (this.value != value) {
      this.value = value
      invalidate = true
    }

    if (this.starFilledColor != starFilledColor) {
      this.starFilledColor = starFilledColor
      invalidate = true
    }

    if (this.starColor != starColor) {
      this.starColor = starColor
      invalidate = true
    }

    if (this.rtl != rtl) {
      this.rtl = rtl
      invalidate = true
    }

    if (invalidate) {
      invalidate()
    }
  }

  override fun draw(canvas: Canvas) {
    star.draw(canvas)
    if (value >= HALF_VALUE && value < MAX_VALUE) {
      if (rtl) {
        val halfStarSize = bounds.width() / 2f
        canvas.translate(halfStarSize, 0f)
        canvas.scale(-1f, 1f, halfStarSize / 2, halfStarSize / 2)
      }
      half.draw(canvas)
    }
  }

  override fun getIntrinsicWidth() = star.intrinsicWidth

  override fun getIntrinsicHeight() = star.intrinsicHeight

  override fun setAlpha(alpha: Int) {
    star.alpha = alpha
    half.alpha = alpha
  }

  override fun getOpacity() = PixelFormat.TRANSLUCENT

  override fun setColorFilter(colorFilter: ColorFilter?) {
    star.colorFilter = colorFilter
    half.colorFilter = colorFilter
  }

  override fun onBoundsChange(bounds: Rect) {
    star.bounds = bounds
    half.bounds = bounds
  }

  private fun invalidate() {
    DrawableCompat.setTint(star, if (value == MAX_VALUE) starFilledColor else starColor)
    DrawableCompat.setTint(half, starFilledColor)
    invalidateSelf()
  }
}
