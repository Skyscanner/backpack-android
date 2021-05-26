package net.skyscanner.backpack.calendar2

import android.content.Context
import android.graphics.Color
import androidx.annotation.ColorInt
import androidx.core.graphics.ColorUtils
import net.skyscanner.backpack.R

sealed class CalendarCellStyle {

  @ColorInt
  abstract fun getBackgroundColor(context: Context): Int

  @ColorInt
  open fun getContentColor(context: Context): Int =
    if (ColorUtils.calculateContrast(Color.WHITE, getBackgroundColor(context)) > 0.5f) {
      Color.WHITE
    } else {
      Color.BLACK
    }

  object Negative : CalendarCellStyle() {

    override fun getBackgroundColor(context: Context): Int =
      context.getColor(R.color.__calendarCellStyleNegativeColor)
  }

  object Neutral : CalendarCellStyle() {

    override fun getBackgroundColor(context: Context): Int =
      context.getColor(R.color.__calendarCellStyleNeutralColor)
  }

  object Positive : CalendarCellStyle() {

    override fun getBackgroundColor(context: Context): Int =
      context.getColor(R.color.__calendarCellStylePositiveColor)
  }

  data class Custom(
    private val background: (Context) -> Int,
    private val content: ((Context) -> Int)? = null,
  ) : CalendarCellStyle() {

    override fun getContentColor(context: Context): Int =
      content?.invoke(context) ?: super.getContentColor(context)

    override fun getBackgroundColor(context: Context): Int =
      background.invoke(context)
  }
}
