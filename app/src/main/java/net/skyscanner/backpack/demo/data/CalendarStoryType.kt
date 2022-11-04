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

import net.skyscanner.backpack.calendar2.CalendarParams
import net.skyscanner.backpack.calendar2.CalendarSelection
import net.skyscanner.backpack.calendar2.CellInfo
import net.skyscanner.backpack.calendar2.CellStatus
import net.skyscanner.backpack.calendar2.CellStatusStyle
import net.skyscanner.backpack.calendar2.extension.toIterable
import org.threeten.bp.DayOfWeek
import org.threeten.bp.LocalDate
import org.threeten.bp.Month
import org.threeten.bp.Period
import org.threeten.bp.YearMonth
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
  WithColors,
  PreselectedRange;

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
          selectionMode = CalendarParams.SelectionMode.Single,
        )

        SelectionRange -> CalendarParams(
          now = now,
          range = range,
          selectionMode = CalendarParams.SelectionMode.Range,
        )

        SelectionWholeMonth -> CalendarParams(
          now = now,
          range = range,
          selectionMode = CalendarParams.SelectionMode.Range,
          monthSelectionMode = CalendarParams.MonthSelectionMode.SelectWholeMonth("Select whole month")
        )

        WithDisabledDates -> CalendarParams(
          now = now,
          range = range,
          selectionMode = CalendarParams.SelectionMode.Range,
          cellsInfo = range
            .toIterable()
            .associateWith { CellInfo(disabled = it.dayOfWeek == DayOfWeek.SATURDAY || it.dayOfWeek == DayOfWeek.SUNDAY) },
        )

        WithLabels -> CalendarParams(
          now = now,
          range = range,
          selectionMode = CalendarParams.SelectionMode.Range,
          cellsInfo = range
            .toIterable()
            .associateWith {
              val price = it.dayOfMonth % maxPrice
              CellInfo(
                label = when (price) {
                  in minPrice..noPriceThreshold -> "-"
                  else -> "£${(it.dayOfMonth * 2.35f).roundToInt()}"
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

        WithColors -> CalendarParams(
          now = now,
          range = range,
          selectionMode = CalendarParams.SelectionMode.Range,
          cellsInfo = range
            .toIterable()
            .associateWith {
              val price = it.dayOfMonth % maxPrice
              CellInfo(
                status = when (price) {
                  noPriceThreshold -> null
                  emptyPriceThreshold -> CellStatus.Empty
                  positivePriceThreshold -> CellStatus.Positive
                  neutralPriceThreshold -> CellStatus.Neutral
                  negativePriceThreshold -> CellStatus.Negative
                  else -> CellStatus.Empty
                },
                style = CellStatusStyle.Background,
              )
            }
        )

        PreselectedRange -> CalendarParams(
          now = now,
          range = range,
          selectionMode = CalendarParams.SelectionMode.Range,
        )
      }
  }
}

object CalendarStorySelection {
  val PreselectedRange = CalendarSelection.Dates(
    start = range.start.plusDays(10),
    end = range.start.plusDays(20),
  )

  val WholeMonthRange = CalendarSelection.Month(
    month = YearMonth.of(2019, Month.JANUARY),
  )
}
