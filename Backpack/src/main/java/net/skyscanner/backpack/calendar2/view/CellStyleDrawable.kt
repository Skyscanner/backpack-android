package net.skyscanner.backpack.calendar2.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.ColorFilter
import android.graphics.Paint
import android.graphics.PixelFormat
import android.graphics.drawable.Drawable
import net.skyscanner.backpack.R
import net.skyscanner.backpack.calendar2.CalendarCellStyle
import net.skyscanner.backpack.calendar2.data.CalendarDay

internal class CellStyleDrawable(context: Context) : Drawable() {

  var selection: CalendarDay.Selection = CalendarDay.Selection.None
    set(value) {
      field = value
      invalidateSelf()
    }

  var style: CalendarCellStyle? = null
    set(value) {
      field = value
      invalidateSelf()
    }

  private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
  private val singleSelectionColor = context.getColorStateList(R.color.__calendarSameDayBackground)

  override fun draw(canvas: Canvas) {
    TODO("Not yet implemented")
  }

  override fun setAlpha(alpha: Int) {
    paint.alpha = alpha
  }

  override fun getAlpha(): Int =
    paint.alpha

  override fun getColorFilter(): ColorFilter? =
    paint.colorFilter

  override fun setColorFilter(colorFilter: ColorFilter?) {
    paint.colorFilter = colorFilter
  }

  override fun getOpacity(): Int =
    PixelFormat.TRANSLUCENT

  override fun isStateful(): Boolean =
    true

  override fun onStateChange(state: IntArray): Boolean {
    invalidateSelf()
    return true
  }
}
