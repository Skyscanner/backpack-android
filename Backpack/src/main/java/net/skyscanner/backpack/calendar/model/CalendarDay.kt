package net.skyscanner.backpack.calendar.model

import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.TimeZone
import java.util.Date
import java.util.Locale

class CalendarDay : Serializable {
    private val calendar: Calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))

    internal var year: Int = 0
    internal var month: Int = 0
    internal var day: Int = 0

    val date: Date
        get() {
            calendar.trimCalendar()
            return calendar.time
        }

    constructor() {
        setTime(System.currentTimeMillis())
    }

    constructor(year: Int, month: Int, day: Int) {
        setDay(year, month, day)
    }

    fun add(field: Int, value: Int) {
        calendar.set(Calendar.DAY_OF_MONTH, day)
        calendar.add(field, value)
        setTime(calendar.timeInMillis)
    }

    internal fun setDay(newYear: Int, newMonth: Int, newDay: Int) {
        year = newYear
        month = newMonth
        day = newDay

        calendar.apply {
            set(Calendar.YEAR, newYear)
            set(Calendar.MONTH, newMonth)
            set(Calendar.DAY_OF_MONTH, newDay)
        }
    }

    private fun setTime(timeInMillis: Long) {
        calendar.timeInMillis = timeInMillis
        month = calendar.get(Calendar.MONTH)
        year = calendar.get(Calendar.YEAR)
        day = calendar.get(Calendar.DAY_OF_MONTH)
    }

    private fun Calendar.trimCalendar() {
        this.apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
    }

    override fun toString(): String {
        if (dayDateFormat == null) {
            dayDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
            dayDateFormat!!.timeZone = TimeZone.getTimeZone("UTC")
        }
        return dayDateFormat!!.format(date)
    }

    private companion object {
        var dayDateFormat: SimpleDateFormat? = null
    }
}
