package net.skyscanner.backpack.calendar2

import java.util.Locale
import org.threeten.bp.LocalDate
import org.threeten.bp.YearMonth
import org.threeten.bp.format.TextStyle

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
    val rank: Rank? = null,
  ) {

    internal companion object {
      val Default = Info()
    }
  }

  enum class Rank {
    Low,
    Medium,
    High,
  }

  enum class Status {
    Disabled,
    Highlighted,
  }
}
