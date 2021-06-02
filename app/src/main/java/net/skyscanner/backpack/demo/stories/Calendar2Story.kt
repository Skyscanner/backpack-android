/**
 * Backpack for Android - Skyscanner's Design System
 *
 * Copyright 2018-2021 Skyscanner Ltd
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

package net.skyscanner.backpack.demo.stories

import android.os.Bundle
import android.view.View
import kotlin.random.Random
import net.skyscanner.backpack.calendar2.BpkCalendar
import net.skyscanner.backpack.calendar2.CalendarParams
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.util.unsafeLazy
import org.threeten.bp.DayOfWeek
import org.threeten.bp.LocalDate
import org.threeten.bp.Period
import org.threeten.bp.YearMonth

class Calendar2Story : Story() {

  enum class Type(
    val selection: CalendarParams.SelectionMode = CalendarParams.SelectionMode.Range,
    val hasFooters: Boolean = false,
    val labels: Boolean = false,
    val colored: Boolean = false,
    val disableWeekends: Boolean = false,
    val highlight: Boolean = false,
  ) {

    SelectionDisabled(selection = CalendarParams.SelectionMode.Disabled),
    SelectionSingle(selection = CalendarParams.SelectionMode.Single),
    SelectionRange(selection = CalendarParams.SelectionMode.Range),
    WithFooters(hasFooters = true),
    WithDisabledDates(disableWeekends = true),
    WithLabels(labels = true),
    WithColors(colored = true),
    WithHighlight(highlight = true),
  }

  private val calendar by unsafeLazy { view!!.findViewById<BpkCalendar>(R.id.calendar2)!! }
  private val type by unsafeLazy { arguments!!.getSerializable(TYPE) as Type }
  private val now = LocalDate.now()
  private val range = now - Period.ofYears(1)..(now + Period.ofYears(1))
  private val random = Random(0)

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    val period = Period.between(range.start, range.endInclusive)

    val footers = if (type.hasFooters) List(period.months) {
      val date = range.start + Period.ofMonths(it)
      YearMonth.of(date.year, date.month)
    } else emptyList()

    var cells = emptyMap<LocalDate, CalendarParams.Info>()

    if (type.disableWeekends) {
      cells = range.toList()
        .filter { it.dayOfWeek == DayOfWeek.SATURDAY || it.dayOfWeek == DayOfWeek.SUNDAY }
        .associateBy({ it }) {
          CalendarParams.Info(status = CalendarParams.Status.Disabled)
        }
    } else if (type.labels) {
      cells = range.toList()
        .associateBy({ it }) {
          val price = random.nextInt(100)
          CalendarParams.Info(
            label = when (price) {
              in 0..25 -> "–"
              else -> "£$price"
            },
            status = when (price) {
              in 0..25 -> CalendarParams.Status.Empty
              in 25..50 -> CalendarParams.Status.Positive
              in 50..75 -> CalendarParams.Status.Neutral
              in 75..100 -> CalendarParams.Status.Negative
              else -> CalendarParams.Status.Empty
            }
          )
        }
    } else if (type.colored) {
      val random = Random(0)
      cells = range.toList()
        .associateBy({ it }) {
          val price = random.nextInt(100)
          CalendarParams.Info(
            status = when (price) {
              in 0..25 -> CalendarParams.Status.Empty
              in 25..50 -> CalendarParams.Status.Positive
              in 50..75 -> CalendarParams.Status.Neutral
              in 75..100 -> CalendarParams.Status.Negative
              else -> CalendarParams.Status.Empty
            }
          )
        }
    }

    calendar.setParams(
      CalendarParams(
        range = range,
        selectionMode = type.selection,
        footers = footers,
        cells = cells,
      )
    )
  }

  companion object {
    private const val TYPE = "TYPE"

    infix fun of(type: Type) = Calendar2Story().apply {
      arguments = Bundle()
      arguments?.putInt(LAYOUT_ID, R.layout.fragment_calendar_2)
      arguments?.putBoolean(SCROLLABLE, false)
      arguments?.putSerializable(TYPE, type)
    }
  }

  private fun ClosedRange<LocalDate>.toList(): List<LocalDate> {
    val result = mutableListOf<LocalDate>()
    var current = start
    while (current != endInclusive) {
      result += current
      current = current.plusDays(1)
    }
    return result
  }
}
