package net.skyscanner.backpack.calendar.model

import org.threeten.bp.LocalDate
import java.io.Serializable

open class CalendarSelection : Serializable

data class SingleDay(val selectedDay: LocalDate) : CalendarSelection()

data class CalendarRange(var start: LocalDate? = null, var end: LocalDate? = null) : CalendarSelection() {
    internal val isOnTheSameDate: Boolean
        get() = isRange && start == end

    internal val isRange: Boolean
        get() = start != null && end != null

    enum class DrawType {
        NONE, RANGE, SELECTED
    }

    internal fun getDrawType(calendarDay: LocalDate): DrawType {
        return if (isRange) {
            when {
                isSelected(calendarDay) -> DrawType.SELECTED
                isBetweenRange(calendarDay) -> DrawType.RANGE
                else -> DrawType.NONE
            }
        } else {
            if (isStartIsInSelectedMonth(calendarDay.year, calendarDay.month.value) &&
                isSelected(start, calendarDay) ||
                isEndIsInSelectedMonth(calendarDay.year, calendarDay.month.value) &&
                isSelected(end, calendarDay)
            ) {
              DrawType.SELECTED
            } else {
              DrawType.NONE
            }
        }
    }

    private fun isSelected(currentDayTime: LocalDate) =
        currentDayTime == start || currentDayTime == end

    private fun isBetweenRange(currentDayTime: LocalDate) =
        currentDayTime.isAfter(start) && currentDayTime.isBefore(end)

    private fun isSelected(day: LocalDate?, currentDayTime: LocalDate) =
        if (day != null) currentDayTime == day else false

    private fun isStartIsInSelectedMonth(year: Int, month: Int) =
        start != null && start?.year == year && start?.month?.value == month

    private fun isEndIsInSelectedMonth(year: Int, month: Int) =
        end != null && end?.year == year && end?.month?.value == month
}
