package net.skyscanner.backpack.calendar.model

internal data class CalendarDrawingParams(
  val year: Int,
  val month: Int,
  val selectedDay: Int?,
  val calendarColoring: CalendarColoring?
)
