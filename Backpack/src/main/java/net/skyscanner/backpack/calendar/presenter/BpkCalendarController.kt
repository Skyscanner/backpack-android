package net.skyscanner.backpack.calendar.presenter

import net.skyscanner.backpack.calendar.model.CalendarDay
import net.skyscanner.backpack.calendar.model.CalendarRange
import java.text.SimpleDateFormat
import java.util.*

abstract class BpkCalendarController {
  abstract val selectedDay: CalendarDay

  abstract val range: CalendarRange

  abstract val minDate: Calendar

  abstract val maxDate: Calendar

  abstract val isRtl: Boolean

  abstract val locale: Locale

  abstract fun onRangeSelected(range: CalendarRange)

  internal fun onDayOfMonthSelected(selectedDay: CalendarDay) {
    val currentRangeStart = range.start
    val currentRangeEnd = range.end

    if (currentRangeStart != null) {
      when {
        currentRangeStart.date == selectedDay.date && currentRangeEnd == null -> {
          range.start = selectedDay
          range.end = selectedDay
        }
        currentRangeStart.date == selectedDay.date && currentRangeEnd != null && currentRangeEnd.date == selectedDay.date -> {
          range.start = null
          range.end = null
        }
        currentRangeStart.date != selectedDay.date && currentRangeEnd != null && currentRangeEnd.date == selectedDay.date -> {
          range.start = currentRangeStart
          range.end = null
        }
        selectedDay.date.before(currentRangeStart.date) -> {
          range.start = selectedDay
          range.end = null
        }
        currentRangeEnd == null || currentRangeEnd.date != selectedDay.date -> range.end = selectedDay
      }
    } else {
      range.start = selectedDay
      range.end = null
    }

    onRangeSelected(range)
  }

  internal fun getLocalizedDate(date: Date, pattern: String): String = SimpleDateFormat(pattern, locale).format(date)
}
