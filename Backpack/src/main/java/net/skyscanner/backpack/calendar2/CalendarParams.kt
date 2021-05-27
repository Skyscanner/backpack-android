package net.skyscanner.backpack.calendar2

import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

data class CalendarParams(
  val range: ClosedRange<LocalDate>,
  val selectionMode: SelectionMode,
  val cells: Map<LocalDate, Info> = emptyMap(),
  val footers: List<YearMonth> = emptyList(),
  val locale: Locale = Locale.getDefault(),
  val dayOfWeekTextStyle: TextStyle = TextStyle.NARROW,
  val monthsTextStyle: TextStyle = TextStyle.FULL,
) {

  enum class SelectionMode {
    Disabled,
    Single,
    Range,
  }

  data class Info(
    val status: Status? = null,
    val label: String? = null,
  ) {

    internal companion object {
      val Default = Info()
    }
  }

  enum class Status {
    Disabled,
    Highlighted,
    Positive,
    Neutral,
    Negative,
    Empty,
  }
}
