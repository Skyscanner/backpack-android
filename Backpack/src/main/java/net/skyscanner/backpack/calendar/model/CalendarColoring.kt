package net.skyscanner.backpack.calendar.model

import androidx.annotation.ColorInt
import androidx.core.graphics.ColorUtils
import org.threeten.bp.LocalDate

data class CalendarColoring(
  val coloredBuckets: Set<ColoredBucket>
)

data class ColoredBucket(
  @ColorInt val color: Int?,
  val days: Set<LocalDate>,
  @ColorInt val selectedColor: Int? = color,
  val textStyle: TextStyle? = color?.let { if (ColorUtils.calculateLuminance(it) < 0.5f) TextStyle.Dark else TextStyle.Light }
) {

  enum class TextStyle {
    Light,
    Dark
  }
}
