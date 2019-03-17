package net.skyscanner.backpack.calendar.presenter

import net.skyscanner.backpack.calendar.model.CalendarColoring
import net.skyscanner.backpack.calendar.model.CalendarDay
import net.skyscanner.backpack.calendar.model.CalendarRange
import net.skyscanner.backpack.calendar.view.CalendarUpdateCallback
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.Date

abstract class BpkCalendarController {

  open val startDate: CalendarDay =
    Calendar.getInstance().toCalendarDay()

  open val endDate: CalendarDay =
    Calendar.getInstance()
      .apply {
        add(Calendar.YEAR, 1)
      }
      .toCalendarDay()

  open val calendarColoring: CalendarColoring? = null

  abstract val isRtl: Boolean

  abstract val locale: Locale

  var selectionType: SelectionType = SelectionType.RANGE

  abstract fun onRangeSelected(range: CalendarRange)

  abstract fun onSingleDaySelected(day: CalendarDay)

  internal val selectedDay: CalendarDay? = null

  internal val selectedRange: CalendarRange = CalendarRange()

  internal var updateContentCallback: CalendarUpdateCallback? = null

  internal fun onDayOfMonthSelected(selectedDay: CalendarDay) {
    when(selectionType) {
      SelectionType.SINGLE -> handleForSingle(selectedDay)
      SelectionType.RANGE -> handleForRange(selectedDay)
    }
  }

  private fun handleForSingle(selectedDay: CalendarDay) {
    selectedRange.start = selectedDay
    selectedRange.end = selectedDay
    onSingleDaySelected(selectedDay)
  }

  private fun handleForRange(selectedDay: CalendarDay) {
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

  fun updateContent() = updateContentCallback?.updateContent()
}

internal fun Calendar.toCalendarDay() = CalendarDay(year = get(Calendar.YEAR), month = get(Calendar.MONTH), day = get(Calendar.DAY_OF_MONTH))

enum class SelectionType {
  RANGE,
  SINGLE
}
