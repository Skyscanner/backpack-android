package net.skyscanner.backpack.horisontalnav.internal

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.text.style.ImageSpan
import androidx.core.content.ContextCompat
import net.skyscanner.backpack.R

internal class NotificationDotSpan(context: Context) : ImageSpan(
  ContextCompat.getDrawable(context, R.drawable.bpk_horizontal_nav_dot)!!.apply {
    setBounds(0, 0, intrinsicWidth, intrinsicHeight)
  },
  ALIGN_BOTTOM
) {

  override fun getSize(paint: Paint, text: CharSequence?, start: Int, end: Int, fm: Paint.FontMetricsInt?): Int {
    super.getSize(paint, text, start, end, fm)
    return drawable.bounds.width() * 3
  }

  override fun draw(canvas: Canvas, text: CharSequence?, start: Int, end: Int, x: Float, top: Int, y: Int, bottom: Int, paint: Paint) {
    val count = canvas.save()
    canvas.translate(drawable.bounds.width().toFloat(), -y.toFloat())
    super.draw(canvas, text, start, end, x, top, y, bottom, paint)
    canvas.restoreToCount(count)
  }
}
