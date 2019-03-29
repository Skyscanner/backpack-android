package net.skyscanner.backpack.calendar.model

import androidx.annotation.ColorInt
import org.threeten.bp.LocalDate

data class CalendarColoring(
  val coloredBuckets: Set<ColoredBucket>
)

data class ColoredBucket(
  @ColorInt val color: Int?,
  val days: Set<LocalDate>,
  @ColorInt val selectedColor: Int? = color
)
