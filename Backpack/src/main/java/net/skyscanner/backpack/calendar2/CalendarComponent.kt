package net.skyscanner.backpack.calendar2

import kotlinx.coroutines.flow.StateFlow

interface CalendarComponent {

  val state: StateFlow<CalendarState>?

  fun setParams(value: CalendarParams)
}
