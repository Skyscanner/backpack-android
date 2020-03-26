package net.skyscanner.backpack.calendar.model

import android.content.Context
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import androidx.core.graphics.ColorUtils
import net.skyscanner.backpack.R
import org.threeten.bp.LocalDate
import net.skyscanner.backpack.calendar.presenter.HighlightedDaysAdapter

data class CalendarColoring(
  val coloredBuckets: Set<ColoredBucket>
)

data class ColoredBucket(
  val calendarCellStyle: CalendarCellStyle,
  val days: Set<LocalDate>
)

sealed class CalendarCellStyle {

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

  /**
   * A positive cell style which is suitable to indicate for example
   * a date which has a comparatively low price among the dates in
   * the calendar.
   */
  object Positive : CalendarCellStyle() {
    override fun color(context: Context) = ContextCompat.getColor(context, R.color.bpkGlencoe)
  }

  /**
   * A neutral cell style which is suitable to indicate for example
   * a date which has a comparatively average price among the dates in
   * the calendar.
   */
  object Neutral : CalendarCellStyle() {
    override fun color(context: Context) = ContextCompat.getColor(context, R.color.bpkErfoud)
  }

  /**
   * A negative cell style which is suitable to indicate for example
   * a date which has a comparatively high price among the dates in
   * the calendar.
   */
  object Negative : CalendarCellStyle() {
    override fun color(context: Context) = ContextCompat.getColor(context, R.color.bpkHillier)
    override fun textStyle(context: Context) = TextStyle.Light
  }

  /**
   * A cell style which is suitable to indicate a holiday.
   * Use this in conjunction with [HighlightedDaysAdapter] to
   * show a footer with the list of holidays for the month.
   *
   * @see HighlightedDaysAdapter
   */
  object Hightlight : CalendarCellStyle() {
    override fun color(context: Context) =
      ContextCompat.getColor(context, R.color.__calendarHighlightedDayDot)
  }

  /**
   * A custom cell style.
   */
  data class Custom(
    @ColorInt private val color: Int,
    private val textStyle: TextStyle? = null
  ) : CalendarCellStyle() {
    override fun color(context: Context) = color
    override fun textStyle(context: Context): TextStyle {
      return textStyle ?: super.textStyle(context)
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
