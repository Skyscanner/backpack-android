package net.skyscanner.backpack.calendar2

import net.skyscanner.backpack.calendar2.data.CalendarMonth
import org.threeten.bp.temporal.WeekFields

data class CalendarState internal constructor(
  val params: CalendarParams,
  val selection: CalendarSelection = CalendarSelection.None,
  internal val months: List<CalendarMonth> = emptyList(),
  internal val weekFields: WeekFields = WeekFields.of(params.locale),
)
