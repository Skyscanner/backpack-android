package net.skyscanner.backpack.calendar2

import java.time.temporal.WeekFields
import net.skyscanner.backpack.calendar2.data.CalendarMonth

data class CalendarState internal constructor(
  val params: CalendarParams,
  val selection: CalendarSelection = CalendarSelection.None,
  internal val months: List<CalendarMonth> = emptyList(),
  internal val weekFields: WeekFields = WeekFields.of(params.locale),
)
