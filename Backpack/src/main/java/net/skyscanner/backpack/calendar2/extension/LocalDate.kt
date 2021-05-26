package net.skyscanner.backpack.calendar2.extension

import org.threeten.bp.LocalDate

internal fun LocalDate.yearMonthHash(): Int =
  year * 100 + monthValue
