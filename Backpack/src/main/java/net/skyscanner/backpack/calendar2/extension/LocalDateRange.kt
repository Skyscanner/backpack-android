package net.skyscanner.backpack.calendar2.extension

import java.time.LocalDate

internal fun ClosedRange<LocalDate>.toList(): List<LocalDate> {
  val result = mutableListOf<LocalDate>()
  var current = start
  while (current != endInclusive) {
    result += current
    current = current.plusDays(1)
  }
  return result
}
