package net.skyscanner.backpack.calendar2

import java.time.LocalDate

sealed class CalendarSelection {

  abstract operator fun contains(date: LocalDate): Boolean

  object None : CalendarSelection() {
    override fun contains(date: LocalDate): Boolean =
      false
  }

  data class Single(
    val date: LocalDate,
  ) : CalendarSelection() {

    override fun contains(date: LocalDate): Boolean =
      this.date == date
  }

  data class Range(
    val start: LocalDate,
    val end: LocalDate?,
  ) : CalendarSelection() {

    override fun contains(date: LocalDate): Boolean =
      when (end) {
        null -> date == start
        else -> date >= start && date <= end
      }
  }
}
