package net.skyscanner.backpack.calendar.presenter

import net.skyscanner.backpack.calendar.model.CalendarDay
import net.skyscanner.backpack.calendar.model.CalendarRange
import java.text.SimpleDateFormat
import java.util.*

abstract class BpkCalendarController {

  open val startDate: Calendar = DEFAULT_START_DATE

  open val endDate: Calendar = DEFAULT_END_DATE

  abstract val isRtl: Boolean

  abstract val locale: Locale

  abstract fun onRangeSelected(range: CalendarRange)

  internal val selectedDay: CalendarDay = CalendarDay()

  internal val selectedRange: CalendarRange = CalendarRange()

  internal fun onDayOfMonthSelected(selectedDay: CalendarDay) {
    val currentRangeStart = selectedRange.start
    val currentRangeEnd = selectedRange.end

    if (currentRangeStart != null) {
      when {
        currentRangeStart.date == selectedDay.date && currentRangeEnd == null -> {
          selectedRange.start = selectedDay
          selectedRange.end = selectedDay
        }
        currentRangeStart.date == selectedDay.date && currentRangeEnd != null && currentRangeEnd.date == selectedDay.date -> {
          selectedRange.start = null
          selectedRange.end = null
        }
        currentRangeStart.date != selectedDay.date && currentRangeEnd != null && currentRangeEnd.date == selectedDay.date -> {
          selectedRange.start = currentRangeStart
          selectedRange.end = null
        }
        selectedDay.date.before(currentRangeStart.date) -> {
          selectedRange.start = selectedDay
          selectedRange.end = null
        }
        currentRangeEnd == null || currentRangeEnd.date != selectedDay.date -> selectedRange.end = selectedDay
      }
    } else {
      selectedRange.start = selectedDay
      selectedRange.end = null
    }

    onRangeSelected(selectedRange)
  }

  internal fun getLocalizedDate(date: Date, pattern: String): String = SimpleDateFormat(pattern, locale).format(date)

  private companion object {
    val DEFAULT_START_DATE: Calendar = Calendar.getInstance()
    val DEFAULT_END_DATE: Calendar = Calendar.getInstance().apply { add(Calendar.YEAR, 1) }
  }
}
