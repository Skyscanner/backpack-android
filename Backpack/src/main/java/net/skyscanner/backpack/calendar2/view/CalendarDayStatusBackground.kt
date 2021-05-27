package net.skyscanner.backpack.calendar2.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.ColorFilter
import android.graphics.Paint
import android.graphics.PixelFormat
import android.graphics.drawable.Drawable
import net.skyscanner.backpack.R
import net.skyscanner.backpack.calendar2.CalendarParams
import net.skyscanner.backpack.util.getColorForState
import net.skyscanner.backpack.util.smallestDimension

internal typealias CalendarDayStatusBackground = (CalendarParams.Status?) -> Drawable

internal fun CalendarDayStatusBackground(
  context: Context,
): CalendarDayStatusBackground {
  val impl = CalendarDayStatusDrawable(context)

  return { status ->
    impl.apply {
      this.status = status
    }
  }
}

private class CalendarDayStatusDrawable(context: Context) : Drawable() {

  var status: CalendarParams.Status? = null
    set(value) {
      field = value
      invalidateSelf()
    }

  private val colorPositive = context.getColorStateList(R.color.__calendarCellStylePositiveColor)
  private val colorNeutral = context.getColorStateList(R.color.__calendarCellStyleNeutralColor)
  private val colorNegative = context.getColorStateList(R.color.__calendarCellStyleNegativeColor)
  private val colorHighlight = context.getColorStateList(R.color.__calendarHighlightedDayDot)
  private val colorEmpty = context.getColorStateList(R.color.__calendarCellStyleEmptyColor)

  private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply { style = Paint.Style.FILL }

  override fun draw(canvas: Canvas) {
    when (status) {
      CalendarParams.Status.Highlighted -> paint.color = colorHighlight.getColorForState(state)
      CalendarParams.Status.Positive -> paint.color = colorPositive.getColorForState(state)
      CalendarParams.Status.Neutral -> paint.color = colorNeutral.getColorForState(state)
      CalendarParams.Status.Negative -> paint.color = colorNegative.getColorForState(state)
      CalendarParams.Status.Empty -> paint.color = colorEmpty.getColorForState(state)
      CalendarParams.Status.Disabled, null -> return
    }

    val bounds = bounds
    canvas.drawCircle(bounds.centerX().toFloat(), bounds.centerY().toFloat(), bounds.smallestDimension() / 2f, paint)
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
