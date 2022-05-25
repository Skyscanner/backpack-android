package net.skyscanner.backpack.calendar2

import org.threeten.bp.YearMonth

/**
 * Describes the current effect selection in the calendar.
 */
sealed interface CalendarEffect {
  /**
   * A Month [date] [CalendarEffect] is selected
   */
  data class MonthSelected(val date: YearMonth) : CalendarEffect
}
