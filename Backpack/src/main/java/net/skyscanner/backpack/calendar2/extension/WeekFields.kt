package net.skyscanner.backpack.calendar2.extension

import org.threeten.bp.DayOfWeek
import org.threeten.bp.temporal.WeekFields

internal val WeekFields.lastDayOfWeek: DayOfWeek
  get() = firstDayOfWeek - 1
