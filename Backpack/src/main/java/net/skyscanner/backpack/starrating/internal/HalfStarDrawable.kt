package net.skyscanner.backpack.starrating.internal

import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.graphics.drawable.LayerDrawable
import android.os.Build

internal class HalfStarDrawable(
  private val background: Drawable,
  private val foreground: Drawable
) : LayerDrawable(arrayOf(
  background,
  foreground
)) {

  var rtl = false
    set(value) {
      field = value
      invalidateSelf()
    }

  override fun draw(canvas: Canvas) {
    val count = canvas.save()
    background.draw(canvas)

    if (rtl && Build.VERSION.SDK_INT < 23) {
      val halsSize = bounds.width() / 2f
      canvas.translate(halsSize, 0f)
      canvas.scale(-1f, 1f, halsSize / 2, halsSize / 2)
    }

    foreground.draw(canvas)
    canvas.restoreToCount(count)
  }
}
