package net.skyscanner.backpack.calendar2.extension

import java.time.LocalDate
import java.time.YearMonth

internal fun LocalDate.yearMonthHash(): Int =
  year * 100 + monthValue

internal fun LocalDate.yearMonth(): YearMonth =
  YearMonth.of(year, month)
