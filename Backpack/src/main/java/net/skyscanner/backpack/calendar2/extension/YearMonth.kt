package net.skyscanner.backpack.calendar2.extension

import org.threeten.bp.YearMonth

internal fun YearMonth.yearMonthHash(): Int =
  year * 100 + monthValue
