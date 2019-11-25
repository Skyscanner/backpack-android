package net.skyscanner.backpack.calendar.model

import org.threeten.bp.LocalDate

internal data class CalendarDrawingParams(
  val year: Int,
  val month: Int,
  val selectedDay: Int?,
  val calendarColoring: CalendarColoring?,
  val disabledDatesDefinition: (LocalDate) -> Boolean
)
