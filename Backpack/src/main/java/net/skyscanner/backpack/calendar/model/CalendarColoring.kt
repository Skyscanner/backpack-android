package net.skyscanner.backpack.calendar.model

import android.content.Context
import android.graphics.Color
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import androidx.core.graphics.ColorUtils
import net.skyscanner.backpack.R
import org.threeten.bp.LocalDate

data class CalendarColoring(
  val coloredBuckets: Set<ColoredBucket>
)

data class ColoredBucket(
  val calendarCellStyle: CalendarCellStyle,
  val days: Set<LocalDate>
)

abstract class CalendarCellStyle {

  /**
   * Text style of the calendar cell. Where [Light] and [Dark] refer to the background colour.
   * I.e.
   *  [Light] should be used to indicate the background is light and black text should be used.
   *  [Dark] should be used to indicate the background is dark and white text should be used.
   */
  enum class TextStyle {
    Light,
    Dark
  }

  companion object {
    /**
     * A positive cell style which is suitable to indicate for example
     * a date which has a comparatively low price among the dates in
     * the calendar.
     */
    @JvmStatic
    val positive = object : CalendarCellStyle() {
      override fun color(context: Context) = ContextCompat.getColor(context, R.color.bpkGlencoe)
    }

    /**
     * A neutral cell style which is suitable to indicate for example
     * a date which has a comparatively average price among the dates in
     * the calendar.
     */
    @JvmStatic
    val neutral = object : CalendarCellStyle() {
      override fun color(context: Context) = ContextCompat.getColor(context, R.color.bpkErfoud)
    }

    /**
     * A negative cell style which is suitable to indicate for example
     * a date which has a comparatively high price among the dates in
     * the calendar.
     */
    @JvmStatic
    val negative = object : CalendarCellStyle() {
      override fun color(context: Context) = ContextCompat.getColor(context, R.color.bpkHillier)
      override fun textStyle(context: Context) = TextStyle.Light
    }

    /**
     * A custom cell style.
     */
    @JvmStatic
    fun custom(@ColorInt color: Int?, textStyle: TextStyle? = null) = object : CalendarCellStyle() {
      override fun color(context: Context) = color ?: Color.TRANSPARENT
      override fun textStyle(context: Context): TextStyle {
        return textStyle ?: super.textStyle(context)
      }
    }
  }

  @ColorInt
  abstract fun color(context: Context): Int

  open fun textStyle(context: Context): TextStyle =
    if (ColorUtils.calculateLuminance(color(context)) < 0.5f)
      TextStyle.Dark
    else
      TextStyle.Light
}
