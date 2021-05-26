package net.skyscanner.backpack.calendar2.extension

import org.threeten.bp.LocalDate
import org.threeten.bp.YearMonth

internal fun YearMonth.yearMonthHash(): Int =
  year * 100 + monthValue

internal fun YearMonth.firstDay(): LocalDate =
  atDay(1)

internal fun YearMonth.lastDay(): LocalDate =
  atDay(lengthOfMonth())

internal fun YearMonth.prevMonth(): YearMonth =
  minusMonths(1)

internal fun YearMonth.nextMonth(): YearMonth =
  plusMonths(1)
