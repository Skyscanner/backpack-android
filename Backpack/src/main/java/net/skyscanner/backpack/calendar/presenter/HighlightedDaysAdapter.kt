/**
 * Backpack for Android - Skyscanner's Design System
 *
 * Copyright 2018 Skyscanner Ltd
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

@file:Suppress("DEPRECATION")

package net.skyscanner.backpack.calendar.presenter

import android.content.Context
import android.view.View
import androidx.annotation.ColorInt
import net.skyscanner.backpack.calendar.view.HighlightedDaysMonthFooter
import net.skyscanner.backpack.util.unsafeLazy
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter
import java.util.Locale

@Deprecated("Use Calendar2 instead")
typealias DateFormatter = (LocalDate) -> String

@Deprecated("Use Calendar2 instead")
open class HighlightedDaysAdapter(
  val context: Context,
  val locale: Locale,
  holidays: Set<HighlightedDay>,
  private val formatDate: DateFormatter? = null,
) : MonthFooterAdapter {

  private val defaultDateFormatter by unsafeLazy {
    DateTimeFormatter.ofPattern("dd LLL", locale)
  }

  private val groupedHolidays by unsafeLazy {
    holidays.fold(mutableMapOf<String, MutableSet<HighlightedDay>>()) { grouped, holiday ->
      val key = getId(holiday)
      val group = grouped.getOrElse(key) { mutableSetOf() }
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

  @Deprecated("Use Calendar2 instead")
  data class HighlightedDay(
    val date: LocalDate,
    val description: String,
    @ColorInt val color: Int? = null,
    /**
     * Shows only the description provided and not the date.
     */
    val descriptionOnly: Boolean = false,
  ) {
    constructor(date: LocalDate, description: String, descriptionOnly: Boolean) :
      this(date, description, null, descriptionOnly)
  }
}
