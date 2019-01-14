package net.skyscanner.backpack.calendar.model

import java.io.Serializable
import java.util.Calendar
import java.util.TimeZone

data class CalendarRange(var start: CalendarDay? = null, var end: CalendarDay? = null) : Serializable {
    private val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))

    internal val isOnTheSameDate: Boolean
        get() = start != null && end != null && start?.year == end?.year && start?.month == end?.month && start?.day == end?.day

    internal val isRange: Boolean
        get() = start != null && end != null

    enum class DrawType {
        NONE, RANGE, SELECTED
    }

    internal fun getDrawType(year: Int, month: Int, day: Int): DrawType {
        calendar.timeZone = TimeZone.getTimeZone("UTC")
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, month)
        calendar.set(Calendar.DAY_OF_MONTH, day)
        calendar.trimCalendar()
        val time = calendar.timeInMillis
        return if (isRange) {
            when {
                isSelected(time) -> DrawType.SELECTED
                isBetweenRange(time) -> DrawType.RANGE
                else -> DrawType.NONE
            }
        } else {
            if (isStartIsInSelectedMonth(year, month) &&
                isSelected(start, time) ||
                isEndIsInSelectedMonth(year, month) &&
                isSelected(end, time)
            ) {
              DrawType.SELECTED
            } else {
              DrawType.NONE
            }
        }
    }

    // TODO: this is only being used in MonthView:300 and seems unnecessary. Remove/review
    internal fun isInRange(selectedDay: CalendarDay?, month: Int, day: Int): Boolean {
        var isInRange = selectedDay != null && month == selectedDay.month && day == selectedDay.day
        if (isInRange) {
            isInRange = isRangeOutsideOther(selectedDay!!, month, day)
        }
        return isInRange
    }

    private fun Calendar.trimCalendar() {
        this.apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
    }

    private fun isRangeOutsideOther(selectedDay: CalendarDay, month: Int, day: Int): Boolean {
        val otherDay = if (start === selectedDay) end else start
        return otherDay == null || (month == otherDay.month && day != otherDay.day || month != otherDay.month)
    }

    private fun isSelected(currentDayTime: Long) =
        currentDayTime == start?.date?.time || end?.date?.time == currentDayTime

    private fun isBetweenRange(currentDayTime: Long) =
        currentDayTime > start?.date?.time ?: 0L && end?.date?.time ?: 0L > currentDayTime

    private fun isSelected(day: CalendarDay?, currentDayTime: Long) =
        if (day != null) currentDayTime == day.date.time else false

    private fun isStartIsInSelectedMonth(year: Int, month: Int) =
        start != null && start?.year == year && start?.month == month

    private fun isEndIsInSelectedMonth(year: Int, month: Int) =
        end != null && end?.year == year && end?.month == month
}
