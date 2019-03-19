package net.skyscanner.backpack.calendar.presenter

import net.skyscanner.backpack.calendar.model.CalendarColoring
import net.skyscanner.backpack.calendar.model.CalendarDay
import net.skyscanner.backpack.calendar.model.CalendarRange
import net.skyscanner.backpack.calendar.model.CalendarSelection
import net.skyscanner.backpack.calendar.model.SingleDay
import net.skyscanner.backpack.calendar.view.CalendarUpdateCallback
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

abstract class BpkCalendarController(
  open val selectionType: SelectionType = SelectionType.RANGE
) {

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

  abstract fun onRangeSelected(range: CalendarSelection)

  internal val selectedDay: CalendarDay? = null

  internal val selectedRange: CalendarRange = CalendarRange()

  internal var updateContentCallback: CalendarUpdateCallback? = null

  internal fun onDayOfMonthSelected(selectedDay: CalendarDay) {
    when (selectionType) {
      SelectionType.SINGLE -> handleForSingle(selectedDay)
      SelectionType.RANGE -> handleForRange(selectedDay)
    }
  }

  private fun handleForSingle(selectedDay: CalendarDay) {
    selectedRange.start = selectedDay
    selectedRange.end = selectedDay
    onRangeSelected(SingleDay(selectedDay))
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

  fun updateSelection(selection: CalendarSelection) {
    when (selection) {
      is CalendarRange -> {
        selectedRange.start = selection.start
        selectedRange.end = selection.end
        onRangeSelected(selectedRange)
      }
      is SingleDay -> {
        selectedRange.start = selection.selectedDay
        selectedRange.end = selection.selectedDay
        onRangeSelected(SingleDay(selection.selectedDay))
      }
    }
  }

  fun updateContent() = updateContentCallback?.updateContent()
}

internal fun Calendar.toCalendarDay() = CalendarDay(year = get(Calendar.YEAR), month = get(Calendar.MONTH), day = get(Calendar.DAY_OF_MONTH))

enum class SelectionType {
  RANGE,
  SINGLE
}
