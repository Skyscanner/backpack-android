package net.skyscanner.backpack.calendar.presenter

import net.skyscanner.backpack.calendar.model.CalendarColoring
import net.skyscanner.backpack.calendar.model.CalendarDay
import net.skyscanner.backpack.calendar.model.CalendarRange
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.Date

abstract class BpkCalendarController {

  open val startDate: CalendarDay = DEFAULT_START_DATE

  open val endDate: CalendarDay = DEFAULT_END_DATE

  abstract val isRtl: Boolean

  abstract val locale: Locale

  abstract val calendarColoring: CalendarColoring?

  abstract fun onRangeSelected(range: CalendarRange)

  internal val selectedDay: CalendarDay? = null

  internal val selectedRange: CalendarRange = CalendarRange()

  internal fun onDayOfMonthSelected(selectedDay: CalendarDay) {
    val currentRangeStart = selectedRange.start
    val currentRangeEnd = selectedRange.end

    if (currentRangeStart != null) {
      when {
        currentRangeStart == selectedDay && currentRangeEnd == null -> {
          selectedRange.start = selectedDay
          selectedRange.end = selectedDay
        }
        currentRangeStart == selectedDay && currentRangeEnd != null && currentRangeEnd == selectedDay -> {
          selectedRange.start = null
          selectedRange.end = null
        }
        currentRangeEnd != null || selectedDay.date.before(currentRangeStart.date) -> {
          selectedRange.start = selectedDay
          selectedRange.end = null
        }
        currentRangeEnd == null -> {
          selectedRange.end = selectedDay
        }
      }
    } else {
      selectedRange.start = selectedDay
      selectedRange.end = null
    }

    onRangeSelected(selectedRange)
  }

  // TODO: This will not respect differences in the order of the fields for each country.
  // E.g. US = mm/dd/yyyy and UK = dd/mm/yyyy
  // Shall we use DateFormat.getDateInstance instead?
  internal fun getLocalizedDate(date: Date, pattern: String): String = SimpleDateFormat(pattern, locale).format(date)

  open fun isToday(year: Int, month: Int, day: Int): Boolean {
    return CalendarDay(year, month, day).date == CalendarDay.today().date
  }

  fun updateSelection(range: CalendarRange) {
    selectedRange.start = range.start
    selectedRange.end = range.end

    onRangeSelected(selectedRange)
  }

  private companion object {
    val DEFAULT_START_DATE: CalendarDay = Calendar.getInstance().toCalendarDay()
    val DEFAULT_END_DATE: CalendarDay = Calendar.getInstance()
      .apply {
        add(Calendar.YEAR, 1)
        add(Calendar.DATE, -1)
      }
      .toCalendarDay()
  }
}

fun Calendar.toCalendarDay() = CalendarDay(year = get(Calendar.YEAR), month = get(Calendar.MONTH), day = get(Calendar.DAY_OF_MONTH))
