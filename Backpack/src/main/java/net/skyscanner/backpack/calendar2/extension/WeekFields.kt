package net.skyscanner.backpack.calendar2.extension

import java.time.DayOfWeek
import java.time.temporal.WeekFields

internal val WeekFields.lastDayOfWeek: DayOfWeek
  get() = firstDayOfWeek - 1
