package net.skyscanner.backpack.calendar2.extension

import org.threeten.bp.LocalDate
import org.threeten.bp.YearMonth

internal fun LocalDate.yearMonthHash(): Int =
  year * 100 + monthValue

internal fun LocalDate.yearMonth(): YearMonth =
  YearMonth.of(year, month)
