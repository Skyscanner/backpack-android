package net.skyscanner.backpack.calendar2

import java.util.Locale
import org.threeten.bp.LocalDate
import org.threeten.bp.YearMonth
import org.threeten.bp.format.TextStyle

data class CalendarParams(
  val range: ClosedRange<LocalDate>,
  val selectionMode: SelectionMode,
  val styles: Map<LocalDate, CalendarCellStyle> = emptyMap(),
  val labels: Map<LocalDate, String> = emptyMap(),
  val disabledDates: List<LocalDate> = emptyList(),
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
}
