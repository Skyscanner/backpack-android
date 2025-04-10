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

import net.skyscanner.backpack.R
import net.skyscanner.backpack.calendar2.extension.toIterable
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.Month
import java.time.Period
import java.time.YearMonth
import java.util.Locale

object BpkCalendarTestCases {

    object Params {

        private val initialStartDate = LocalDate.of(2019, 1, 2)
        private val initialEndDate = LocalDate.of(2019, 12, 31)
        private val defaultHighlightedDates =
            setOf(LocalDate.of(2019, 1, 10), LocalDate.of(2019, 1, 20), LocalDate.of(2019, 1, 29))
        private val initialRange = initialStartDate..initialEndDate
        private val now = initialStartDate

        private val DefaultSingle = CalendarParams(
            locale = Locale.UK,
            selectionMode = CalendarParams.SelectionMode.Single(),
            range = initialRange,
            now = now,
        )

        private val DefaultRange = DefaultSingle.copy(selectionMode = CalendarParams.SelectionMode.Range())

        val Default = DefaultRange

        val Labeled = DefaultRange.copy(
            cellsInfo = mapOf(
                LocalDate.of(initialStartDate.year, initialStartDate.month, initialStartDate.dayOfMonth + 1) to
                    CellInfo(style = CellStatusStyle.Label, label = CellLabel.Text("£10"), status = CellStatus.Negative),
                LocalDate.of(initialStartDate.year, initialStartDate.month, initialStartDate.dayOfMonth + 2) to
                    CellInfo(style = CellStatusStyle.Label, label = CellLabel.Text("£11"), status = CellStatus.Neutral),
                LocalDate.of(initialStartDate.year, initialStartDate.month, initialStartDate.dayOfMonth + 3) to
                    CellInfo(style = CellStatusStyle.Label, label = CellLabel.Text("£12"), status = CellStatus.Positive),
                LocalDate.of(initialStartDate.year, initialStartDate.month, initialStartDate.dayOfMonth + 4) to
                    CellInfo(style = CellStatusStyle.Label, label = CellLabel.Text("£900000000000000"), status = CellStatus.Positive),
                LocalDate.of(initialStartDate.year, initialStartDate.month, initialStartDate.dayOfMonth + 5) to
                    CellInfo(style = CellStatusStyle.Label, label = CellLabel.Text("£900000"), status = CellStatus.Positive),
                LocalDate.of(initialStartDate.year, initialStartDate.month, initialStartDate.dayOfMonth + 6) to
                    CellInfo(
                        style = CellStatusStyle.Label,
                        label = CellLabel.Icon(resId = R.drawable.bpk_search_sm, tint = R.color.bpkCoreAccent),
                    ),
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

        val WithRangeSetProgrammatically = Default

        val WithSingleDaySetProgrammatically = DefaultSingle

        val WithDisabledDates = DefaultSingle.copy(
            cellsInfo = disabledDayOfTheWeekInfo(initialRange, DayOfWeek.WEDNESDAY),
        )

        val WithDisabledDates_SelectSingle = WithDisabledDates

        val WithDisabledDates_SelectRange = DefaultRange.copy(
            cellsInfo = disabledDayOfTheWeekInfo(initialRange, DayOfWeek.WEDNESDAY),
        )

        val WithDisabledDates_SelectDisabledDate = WithDisabledDates

        val WithHighLightedDates = DefaultSingle.copy(
            cellsInfo = highlightedDates(initialRange, defaultHighlightedDates),
        )

        val WithWholeMonthButtonEnabled = DefaultRange.copy(
            monthSelectionMode = CalendarParams.MonthSelectionMode.SelectWholeMonth(
                label = "Select whole month",
                selectableMonthRange = YearMonth.of(2019, 1)..YearMonth.of(2019, 2),
            ),
        )

        val WithWholeMonthSetProgrammatically = DefaultRange.copy(
            range = LocalDate.of(2019, 1, 1)..(LocalDate.of(2019, 1, 1) + Period.ofYears(2)),
            now = LocalDate.of(2019, 1, 1),
            monthSelectionMode = CalendarParams.MonthSelectionMode.SelectWholeMonth(
                label = "Select whole month",
                selectableMonthRange = YearMonth.of(2019, 1)..YearMonth.of(2019, 2),
            ),
        )

        val WithYearInMonthLabel = DefaultRange.copy(
            yearLabelInMonthHeader = true,
        )

        private fun disabledDayOfTheWeekInfo(
            range: ClosedRange<LocalDate>,
            disabledDayOfWeek: DayOfWeek,
        ): Map<LocalDate, CellInfo> =
            range
                .toIterable()
                .filter { it.dayOfWeek == disabledDayOfWeek }
                .associateWith { CellInfo(disabled = true) }

        private fun highlightedDates(
            range: ClosedRange<LocalDate>,
            highLight: Set<LocalDate>,
        ): Map<LocalDate, CellInfo> =
            range
                .toIterable()
                .filter { highLight.contains(it) }
                .associateWith { CellInfo(highlighted = true) }
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

        val WithHighlightedDates_SelectSingle = CalendarSelection.Single(LocalDate.of(2019, 1, 3))

        val WithHighlightedDates_SelectHighLightedDate = CalendarSelection.Single(LocalDate.of(2019, 1, 10))
    }
}
