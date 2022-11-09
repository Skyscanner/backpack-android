/*
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

package net.skyscanner.backpack.calendar2

import net.skyscanner.backpack.calendar2.extension.toIterable
import org.threeten.bp.DayOfWeek
import org.threeten.bp.LocalDate
import org.threeten.bp.Month
import org.threeten.bp.Period
import org.threeten.bp.YearMonth
import java.util.Locale

object BpkCalendarTestCases {

  object Params {

    private val initialStartDate = LocalDate.of(2019, 1, 2)
    private val initialEndDate = LocalDate.of(2019, 12, 31)
    private val initialRange = initialStartDate..initialEndDate
    private val now = initialStartDate

    private val DefaultSingle = CalendarParams(
      locale = Locale.UK,
      selectionMode = CalendarParams.SelectionMode.Single,
      range = initialRange,
      now = now,
    )

    private val DefaultRange = DefaultSingle.copy(selectionMode = CalendarParams.SelectionMode.Range)

    val Default = DefaultRange

    val Colored = DefaultRange.copy(
      cellsInfo = multiColoredExampleCalendarColoring(initialRange),
    )

    val Labeled = DefaultRange.copy(
      cellsInfo = mapOf(
        LocalDate.of(initialStartDate.year, initialStartDate.month, initialStartDate.dayOfMonth + 1) to
          CellInfo(style = CellStatusStyle.Label, label = "£10", status = CellStatus.Negative),
        LocalDate.of(initialStartDate.year, initialStartDate.month, initialStartDate.dayOfMonth + 2) to
          CellInfo(style = CellStatusStyle.Label, label = "£11", status = CellStatus.Neutral),
        LocalDate.of(initialStartDate.year, initialStartDate.month, initialStartDate.dayOfMonth + 3) to
          CellInfo(style = CellStatusStyle.Label, label = "£12", status = CellStatus.Positive),
        LocalDate.of(initialStartDate.year, initialStartDate.month, initialStartDate.dayOfMonth + 4) to
          CellInfo(style = CellStatusStyle.Label, label = "£900000000000000", status = CellStatus.Positive),
        LocalDate.of(initialStartDate.year, initialStartDate.month, initialStartDate.dayOfMonth + 5) to
          CellInfo(style = CellStatusStyle.Label, label = "£900000", status = CellStatus.Positive),
      ),
    )

    val Past = DefaultRange.copy(
      range = LocalDate.of(2017, 1, 2)..LocalDate.of(2017, 12, 31),
    )

    val LeapFebruary = DefaultSingle.copy(
      range = LocalDate.of(2020, 2, 1)..LocalDate.of(2020, 12, 31),
      now = LocalDate.of(2020, 2, 1),
    )

    val NonLeapFebruary = DefaultSingle.copy(
      range = LocalDate.of(2021, 2, 1)..LocalDate.of(2021, 12, 31),
      now = LocalDate.of(2021, 2, 1),
    )

    val TodayIsLastDayOfMonth = DefaultSingle.copy(
      range = LocalDate.of(2017, 1, 1)..LocalDate.of(2017, 12, 31),
      now = LocalDate.of(2017, 1, 31),
    )

    val TodayIsNewYear = DefaultSingle.copy(
      range = LocalDate.of(2017, 12, 1)..LocalDate.of(2018, 12, 31),
      now = LocalDate.of(2017, 12, 31),
    )

    val WithStartDateSelected = Default

    val WithSameStartAndEndDateSelected = Default

    val WithStartAndEndDateSelected = Default

    val ColoredWithStartAndEndDateSelected = DefaultRange.copy(
      cellsInfo = multiColoredExampleCalendarColoring(initialRange),
    )

    val WithSingleDaySelected = DefaultSingle

    val WithRangeSetProgrammatically = Default

    val WithSingleDaySetProgrammatically = WithSingleDaySelected

    val WithDisabledDates = DefaultSingle.copy(
      cellsInfo = disabledDayOfTheWeekInfo(initialRange, DayOfWeek.WEDNESDAY),
    )

    val WithDisabledDates_SelectSingle = WithDisabledDates

    val WithDisabledDates_SelectRange = DefaultRange.copy(
      cellsInfo = disabledDayOfTheWeekInfo(initialRange, DayOfWeek.WEDNESDAY),
    )

    val WithDisabledDates_SelectDisabledDate = WithDisabledDates

    val WithWholeMonthButtonEnabled = DefaultRange.copy(
      monthSelectionMode = CalendarParams.MonthSelectionMode.SelectWholeMonth("Select whole month")
    )

    val WithWholeMonthSetProgrammatically = DefaultRange.copy(
      range = LocalDate.of(2019, 1, 1)..(LocalDate.of(2019, 1, 1) + Period.ofYears(2)),
      now = LocalDate.of(2019, 1, 1),
      monthSelectionMode = CalendarParams.MonthSelectionMode.SelectWholeMonth("Select whole month")
    )

    private fun multiColoredExampleCalendarColoring(range: ClosedRange<LocalDate>): Map<LocalDate, CellInfo> =
      range
        .toIterable()
        .associateWith {
          CellInfo(
            status = when (it.dayOfYear % 5) {
              0 -> CellStatus.Negative
              1 -> CellStatus.Neutral
              2 -> CellStatus.Positive
              3 -> CellStatus.Empty
              else -> null
            }
          )
        }

    private fun disabledDayOfTheWeekInfo(
      range: ClosedRange<LocalDate>,
      disabledDayOfWeek: DayOfWeek
    ): Map<LocalDate, CellInfo> =
      range
        .toIterable()
        .filter { it.dayOfWeek == disabledDayOfWeek }
        .associateWith { CellInfo(disabled = true) }
  }

  object Selection {

    val LeapFebruary = CalendarSelection.Single(LocalDate.of(2020, 2, 1))

    val NonLeapFebruary = CalendarSelection.Single(LocalDate.of(2021, 2, 1))

    val TodayIsLastDayOfMonth = CalendarSelection.Single(LocalDate.of(2017, 1, 31))

    val TodayIsNewYear = CalendarSelection.Single(LocalDate.of(2017, 12, 31))

    val WithRangeSetProgrammatically =
      CalendarSelection.Dates(LocalDate.of(2019, 1, 4), LocalDate.of(2019, 1, 9))

    val WithSingleDaySetProgrammatically = CalendarSelection.Single(LocalDate.of(2019, 1, 16))

    val WithDisabledDates_SelectSingle = CalendarSelection.Single(LocalDate.of(2019, 1, 10))

    val WithDisabledDates_SelectRange = CalendarSelection.Dates(LocalDate.of(2019, 1, 4), LocalDate.of(2019, 1, 10))

    val WithDisabledDates_SelectDisabledDate = CalendarSelection.Single(LocalDate.of(2019, 1, 9))

    val WithWholeMonthSetProgrammatically = CalendarSelection.Month(YearMonth.of(2019, Month.JANUARY))
  }

  object Indices {

    val WithStartDateSelected_OfSelectedItem = 18

    val WithSameStartAndEndDateSelected_OfSelectedItem = WithStartDateSelected_OfSelectedItem

    val SelectStartEnd_OfRangeStart = WithStartDateSelected_OfSelectedItem
    val SelectStartEnd_OfRangeEnd = 54

    val WithSingleDaySelectedParams_OfInitialSelectedItem = 53
    val WithSingleDaySelectedParams_OfFinalSelectedItem = SelectStartEnd_OfRangeEnd
  }
}
