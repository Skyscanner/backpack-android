package net.skyscanner.backpack.calendar.model

import net.skyscanner.backpack.calendar.presenter.toCalendarDay
import java.io.Serializable
import java.util.Calendar
import java.util.TimeZone
import java.util.Date

data class CalendarDay(
  val year: Int,
  val month: Int,
  val day: Int
) : Serializable {

  companion object {
    fun of(timeInMillis: Long): CalendarDay {
      val utcCalendar = utcCalendar()
      utcCalendar.timeInMillis = timeInMillis
      return utcCalendar.toCalendarDay()
    }

    fun today(): CalendarDay = of(System.currentTimeMillis())

    fun utcCalendar() = Calendar.getInstance(TimeZone.getTimeZone("UTC"))!!
  }

  private val calendar: Calendar = utcCalendar()

  val date: Date
    get() = calendar.time

  init {
    calendar.apply {
      set(Calendar.YEAR, year)
      set(Calendar.MONTH, month)
      set(Calendar.DAY_OF_MONTH, day)
    }.trimCalendar()
  }

  private fun Calendar.trimCalendar() = apply {
    set(Calendar.HOUR_OF_DAY, 0)
    set(Calendar.MINUTE, 0)
    set(Calendar.SECOND, 0)
    set(Calendar.MILLISECOND, 0)
  }

  override fun toString(): String = String.format("%04d-%02d-%02d", year, month + 1, day)

  fun newInstanceByAddedDays(days: Int): CalendarDay {
    val utcCalendar = utcCalendar().apply {
      set(Calendar.YEAR, year)
      set(Calendar.MONTH, month)
      set(Calendar.DAY_OF_MONTH, day)
    }.trimCalendar()
    utcCalendar.add(Calendar.DAY_OF_MONTH, days)
    return utcCalendar.toCalendarDay()
  }
}
