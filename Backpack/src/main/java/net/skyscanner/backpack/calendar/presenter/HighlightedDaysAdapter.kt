package net.skyscanner.backpack.calendar.presenter

import android.content.Context
import android.view.View
import androidx.annotation.ColorInt
import net.skyscanner.backpack.calendar.view.HighlightedDaysMonthFooter
import net.skyscanner.backpack.util.unsafeLazy
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter
import java.util.*

typealias DateFormatter = (LocalDate) -> String

open class HighlightedDaysAdapter(
  val context: Context,
  val locale: Locale,
  holidays: Set<HighlightedDay>,
  private val formatDate: DateFormatter? = null
) : MonthFooterAdapter {

  private val defaultDateFormatter by unsafeLazy {
    DateTimeFormatter.ofPattern("dd LLL", locale)
  }

  private val groupedHolidays by unsafeLazy {
    holidays.fold(mutableMapOf<String, MutableSet<HighlightedDay>>()) { grouped, holiday ->
      val key = getId(holiday)
      val group = grouped.getOrElse(key, { mutableSetOf<HighlightedDay>() })
      group.add(holiday)
      grouped[key] = group
      grouped
    }
  }

  override fun hasFooterForMonth(month: Int, year: Int): Boolean {
    return groupedHolidays.containsKey(getId(month, year))
  }

  override fun onCreateView(month: Int, year: Int): View {
    val formatter = formatDate ?: { defaultDateFormatter.format(it) }
    return HighlightedDaysMonthFooter(context, formatter)
  }

  override fun onBindView(view: View, month: Int, year: Int) {
    view as HighlightedDaysMonthFooter
    view.holidays = groupedHolidays[getId(month, year)]
  }

  private fun getId(holiday: HighlightedDay) =
    getId(holiday.date.monthValue, holiday.date.year)

  private fun getId(month: Int, year: Int): String {
    return "$month-$year"
  }

  data class HighlightedDay(
    val date: LocalDate,
    val description: String,
    @ColorInt val color: Int? = null,
    /**
     * Shows only the description provided and not the date.
     */
    val descriptionOnly: Boolean = false
  ) {
    constructor(date: LocalDate, description: String, descriptionOnly: Boolean) :
      this(date, description, null, descriptionOnly)
  }
}
