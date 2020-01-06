package net.skyscanner.backpack.calendar.presenter

import androidx.annotation.VisibleForTesting
import net.skyscanner.backpack.calendar.model.CalendarColoring
import net.skyscanner.backpack.calendar.model.CalendarRange
import net.skyscanner.backpack.calendar.model.CalendarSelection
import net.skyscanner.backpack.calendar.model.SingleDay
import net.skyscanner.backpack.calendar.view.CalendarUpdateCallback
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter
import java.util.Locale

abstract class BpkCalendarController(
  open val selectionType: SelectionType = SelectionType.RANGE,
  val currentDateProvider: CurrentDateProvider = LocalDateProvider
) {

  open val startDate: LocalDate = currentDateProvider.invoke()

  open val endDate: LocalDate = currentDateProvider.invoke().plusYears(1)

  open val calendarColoring: CalendarColoring? = null

  open fun isDateDisabled(date: LocalDate): Boolean {
    return false
  }

  abstract val isRtl: Boolean

  abstract val locale: Locale

  abstract fun onRangeSelected(range: CalendarSelection)

  internal val selectedDay: LocalDate? = null

  internal val selectedRange: CalendarRange = CalendarRange()

  internal var updateContentCallback: CalendarUpdateCallback? = null

  internal fun onDayOfMonthSelected(selectedDay: LocalDate) {
    when (selectionType) {
      SelectionType.SINGLE -> handleForSingle(selectedDay)
      SelectionType.RANGE -> handleForRange(selectedDay)
    }
  }

  private fun handleForSingle(selectedDay: LocalDate) {
    selectedRange.start = selectedDay
    selectedRange.end = null
    onRangeSelected(SingleDay(selectedDay))
  }

  private fun handleForRange(selectedDay: LocalDate) {
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
        currentRangeEnd != null || selectedDay.isBefore(currentRangeStart) -> {
          selectedRange.start = selectedDay
          selectedRange.end = null
        }
        else -> {
          selectedRange.end = selectedDay
        }
      }
    } else {
      selectedRange.start = selectedDay
      selectedRange.end = null
    }

    onRangeSelected(selectedRange)
  }

  internal fun getLocalizedDate(date: LocalDate, pattern: String): String = DateTimeFormatter.ofPattern(pattern, locale).format(date)

  open fun isToday(year: Int, month: Int, day: Int): Boolean {
    return LocalDate.of(year, month, day) == currentDateProvider.invoke()
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
        selectedRange.end = null
        onRangeSelected(SingleDay(selection.selectedDay))
      }
    }
  }

  fun updateContent() = updateContentCallback?.updateContent()
}

enum class SelectionType {
  RANGE,
  SINGLE
}

typealias CurrentDateProvider = () -> LocalDate

@VisibleForTesting
private object LocalDateProvider : CurrentDateProvider {

  override fun invoke(): LocalDate = LocalDate.now()
}

@VisibleForTesting
class MockDateProvider(private val value: LocalDate) : CurrentDateProvider {

  override fun invoke(): LocalDate = value
}
