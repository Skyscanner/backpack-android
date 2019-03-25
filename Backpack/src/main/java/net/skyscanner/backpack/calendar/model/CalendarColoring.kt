package net.skyscanner.backpack.calendar.model

import androidx.annotation.ColorInt

data class CalendarColoring(
  val coloredBuckets: Set<ColoredBucket>
)

data class ColoredBucket(
  @ColorInt val color: Int?,
  val days: Set<CalendarDay>,
  @ColorInt val selectedColor: Int? = color
)
