package net.skyscanner.backpack.calendar2.extension

import org.threeten.bp.LocalDate

internal fun ClosedRange<LocalDate>.toList(): List<LocalDate> {
  val result = mutableListOf<LocalDate>()
  var current = start
  while (current != endInclusive) {
    result += current
    current = current.plusDays(1)
  }
  return result
}
