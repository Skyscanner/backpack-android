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

package net.skyscanner.backpack.demo.data

import net.skyscanner.backpack.R
import net.skyscanner.backpack.calendar2.CalendarParams
import net.skyscanner.backpack.calendar2.CalendarParams.DayCellAccessibilityLabel
import net.skyscanner.backpack.calendar2.CalendarSelection
import net.skyscanner.backpack.calendar2.CellInfo
import net.skyscanner.backpack.calendar2.CellLabel
import net.skyscanner.backpack.calendar2.CellStatus
import net.skyscanner.backpack.calendar2.CellStatusStyle
import net.skyscanner.backpack.calendar2.extension.toIterable
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.Month
import java.time.Period
import java.time.YearMonth
import kotlin.math.roundToInt

private val now = LocalDate.of(2019, 1, 1)
private val range = now..(now + Period.ofYears(2))

enum class CalendarStoryType {
    SelectionDisabled,
    SelectionSingle,
    SelectionRange,
    SelectionWholeMonth,
    WithDisabledDates,
    WithLabels,
    WithIconAsLabels,
    PreselectedRange,
    YearLabelInMonthHeader,
    MultiSelection,
    Loading,
    ;

    companion object {

        private const val minPrice = 0
        private const val maxPrice = 5
        private const val noPriceThreshold = 0
        private const val emptyPriceThreshold = 1
        private const val positivePriceThreshold = 2
        private const val neutralPriceThreshold = 3
        private const val negativePriceThreshold = 4

        fun createInitialParams(type: CalendarStoryType): CalendarParams =
            when (type) {
                SelectionDisabled -> CalendarParams(
                    now = now,
                    range = range,
                    selectionMode = CalendarParams.SelectionMode.Disabled,
                )

                SelectionSingle -> CalendarParams(
                    now = now,
                    range = range,
                    selectionMode = singleSelectionModeWithAccessibilityLabels(),
                )

                SelectionRange -> CalendarParams(
                    now = now,
                    range = range,
                    selectionMode = rangeSelectionModeWithAccessibilityLabels(),
                )

                SelectionWholeMonth -> CalendarParams(
                    now = now,
                    range = range,
                    selectionMode = rangeSelectionModeWithAccessibilityLabels(),
                    monthSelectionMode = CalendarParams.MonthSelectionMode.SelectWholeMonth(
                        label = "Select whole month",
                        selectableMonthRange = YearMonth.of(2019, 1).plusMonths(1)..YearMonth.now().plusMonths(3),
                    ),
                )

                WithDisabledDates -> CalendarParams(
                    now = now,
                    range = range,
                    selectionMode = rangeSelectionModeWithAccessibilityLabels(),
                    cellsInfo = range
                        .toIterable()
                        .associateWith { CellInfo(disabled = it.dayOfWeek == DayOfWeek.SATURDAY || it.dayOfWeek == DayOfWeek.SUNDAY) },
                )

                WithLabels -> CalendarParams(
                    now = now,
                    range = range,
                    selectionMode = rangeSelectionModeWithAccessibilityLabels(),
                    cellsInfo = range
                        .toIterable()
                        .associateWith {
                            val price = it.dayOfMonth % maxPrice
                            CellInfo(
                                label = when (price) {
                                    in minPrice..noPriceThreshold -> CellLabel.Text(text = "-")
                                    else -> CellLabel.Text("£${(it.dayOfMonth * 2.35f).roundToInt()}")
                                },
                                status = when (price) {
                                    noPriceThreshold -> null
                                    emptyPriceThreshold -> CellStatus.Empty
                                    positivePriceThreshold -> CellStatus.Positive
                                    neutralPriceThreshold -> CellStatus.Neutral
                                    negativePriceThreshold -> CellStatus.Negative
                                    else -> CellStatus.Empty
                                },
                                style = CellStatusStyle.Label,
                            )
                        },
                )

                PreselectedRange -> CalendarParams(
                    now = now,
                    range = range,
                    selectionMode = CalendarParams.SelectionMode.Range(),
                )

                WithIconAsLabels ->
                    CalendarParams(
                        now = now,
                        range = range,
                        selectionMode = rangeSelectionModeWithAccessibilityLabels(),
                        cellsInfo = range
                            .toIterable()
                            .associateWith {
                                val price = it.dayOfMonth % maxPrice
                                CellInfo(
                                    label = when (price) {
                                        in minPrice..noPriceThreshold -> CellLabel.Icon(
                                            resId = R.drawable.bpk_search_sm,
                                            tint = R.color.bpkCoreAccent,
                                        )

                                        else -> CellLabel.Text("£${(it.dayOfMonth * 2.35f).roundToInt()}")
                                    },
                                    status = when (price) {
                                        noPriceThreshold -> null
                                        emptyPriceThreshold -> CellStatus.Empty
                                        positivePriceThreshold -> CellStatus.Positive
                                        neutralPriceThreshold -> CellStatus.Neutral
                                        negativePriceThreshold -> CellStatus.Negative
                                        else -> CellStatus.Empty
                                    },
                                    style = CellStatusStyle.Label,
                                )
                            },
                    )

                YearLabelInMonthHeader -> CalendarParams(
                    now = now,
                    range = range,
                    selectionMode = CalendarParams.SelectionMode.Range(),
                    yearLabelInMonthHeader = true,
                )
                MultiSelection -> CalendarParams(
                    now = now,
                    range = range,
                    selectionMode = multipleSelectionModeWithDynamicAccessibilityLabels(),
                    cellsInfo = createCombinedCellsInfo(range),
                )

                Loading ->
                    CalendarParams(
                        now = now,
                        range = range,
                        selectionMode = rangeSelectionModeWithAccessibilityLabels(),
                        cellsInfo = range
                            .toIterable()
                            .associateWith {
                                CellInfo(
                                    label = CellLabel.Loading("Loading"),
                                    status = CellStatus.Neutral,
                                    style = CellStatusStyle.Label,
                                )
                            },
                    )
            }

        private fun rangeSelectionModeWithAccessibilityLabels() = CalendarParams.SelectionMode.Range(
            startSelectionHint = DayCellAccessibilityLabel.Static("Select as departure date"),
            endSelectionHint = DayCellAccessibilityLabel.Static("Select as return date"),
            startSelectionState = DayCellAccessibilityLabel.Static("Selected as departure date"),
            endSelectionState = DayCellAccessibilityLabel.Static("Selected as return date"),
            startAndEndSelectionState = "Selected as departure and return date",
            betweenSelectionState = "Between departure and return date",
        )

        private fun multipleSelectionModeWithDynamicAccessibilityLabels() = CalendarParams.SelectionMode.Single(
            startSelectionHint = DayCellAccessibilityLabel.Dynamic {
                "Select as departure from Taiwan $it"
            },
            startSelectionState = DayCellAccessibilityLabel.Dynamic {
                "Selected as departure from Taiwan $it"
            },
            noSelectionState = "No selection for departure from Taiwan",
            contentDescription = {
                "Selected as departure from Tokyo, Japan"
            },
        )

        private fun singleSelectionModeWithAccessibilityLabels() = CalendarParams.SelectionMode.Single(
            startSelectionHint = DayCellAccessibilityLabel.Static("Select as departure date"),
            startSelectionState = DayCellAccessibilityLabel.Static("Selected as departure date"),
            noSelectionState = "No selection",
            contentDescription = {
                "Selected as departure from Tokyo, Japan"
            },
        )
    }
}

fun createCombinedCellsInfo(range: ClosedRange<LocalDate>): Map<LocalDate, CellInfo> {
    val maxPrice = 5
    val noPriceThreshold = 0
    val emptyPriceThreshold = 1
    val positivePriceThreshold = 2
    val neutralPriceThreshold = 3
    val negativePriceThreshold = 4
    val minPrice = 0

    val baseCellsInfo = range.toIterable().associateWith {
        val price = it.dayOfMonth % maxPrice
        CellInfo(
            label = when (price) {
                in minPrice..noPriceThreshold -> CellLabel.Icon(
                    resId = R.drawable.bpk_search_sm,
                    tint = R.color.bpkCoreAccent,
                )

                else -> CellLabel.Text("£${(it.dayOfMonth * 2.35f).roundToInt()}")
            },
            status = when (price) {
                noPriceThreshold -> null
                emptyPriceThreshold -> CellStatus.Empty
                positivePriceThreshold -> CellStatus.Positive
                neutralPriceThreshold -> CellStatus.Neutral
                negativePriceThreshold -> CellStatus.Negative
                else -> CellStatus.Empty
            },
            style = CellStatusStyle.Label,
        )
    }

    val highlightedCells = mapOf(
        LocalDate.of(2019, 1, 6) to CellInfo(
            highlighted = true,
            label = CellLabel.Icon(
                resId = R.drawable.bpk_search_sm,
                tint = R.color.bpkCoreAccent,
            ),
        ),
        LocalDate.of(2019, 1, 9) to CellInfo(
            highlighted = true,
            label = CellLabel.Text("£100"),
        ),
        LocalDate.of(2019, 1, 25) to CellInfo(
            highlighted = true,
            label = CellLabel.Text("£100"),
        ),
        LocalDate.of(2019, 2, 2) to CellInfo(
            highlighted = true,
            label = CellLabel.Text("£100"),
        ),
    )

    return baseCellsInfo + highlightedCells
}

object CalendarStorySelection {
    val PreselectedRange = CalendarSelection.Dates(
        start = range.start.plusDays(10),
        end = range.start.plusDays(20),
    )

    val PreselectedDate = CalendarSelection.Single(
        date = range.start.plusDays(10),
    )

    val WholeMonthRange = CalendarSelection.Month(
        month = YearMonth.of(2019, Month.JANUARY),
    )
}
