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
import kotlin.math.roundToInt
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import net.skyscanner.backpack.calendar2.BpkCalendar
import net.skyscanner.backpack.calendar2.CalendarParams
import net.skyscanner.backpack.calendar2.CalendarSelection
import net.skyscanner.backpack.calendar2.CellInfo
import net.skyscanner.backpack.calendar2.CellStatus
import net.skyscanner.backpack.calendar2.CellStatusStyle
import net.skyscanner.backpack.calendar2.extension.toIterable
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.toast.BpkToast
import net.skyscanner.backpack.util.ExperimentalBackpackApi
import net.skyscanner.backpack.util.unsafeLazy
import org.threeten.bp.DayOfWeek
import org.threeten.bp.LocalDate
import org.threeten.bp.Period

@OptIn(ExperimentalBackpackApi::class)
class Calendar2Story : Story() {

  enum class Type {
    SelectionDisabled,
    SelectionSingle,
    SelectionRange,
    WithDisabledDates,
    WithLabels,
    WithColors,
    PreselectedRange,
  }

  private val calendar by unsafeLazy { requireView().findViewById<BpkCalendar>(R.id.calendar2)!! }
  private val type by unsafeLazy { requireArguments().getSerializable(TYPE) as Type }
  private val now = LocalDate.of(2019, 1, 1)
  private val range = now..(now + Period.ofYears(2))

  private var scope: CoroutineScope? = null

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    scope?.cancel()
    scope = CoroutineScope(Dispatchers.Main)

    calendar.state
      .filter { it.selection !is CalendarSelection.None }
      .onEach {
        BpkToast.makeText(requireContext(), it.selection.toString(), BpkToast.LENGTH_SHORT).show()
      }
      .launchIn(scope!!)

    when (type) {
      Type.SelectionDisabled -> {
        calendar.setParams(
          CalendarParams(
            now = now,
            range = range,
            selectionMode = CalendarParams.SelectionMode.Disabled,
          )
        )
      }
      Type.SelectionSingle -> {
        calendar.setParams(
          CalendarParams(
            now = now,
            range = range,
            selectionMode = CalendarParams.SelectionMode.Single,
          )
        )
      }
      Type.SelectionRange -> {
        calendar.setParams(
          CalendarParams(
            now = now,
            range = range,
            selectionMode = CalendarParams.SelectionMode.Range,
          )
        )
      }
      Type.WithDisabledDates -> {
        calendar.setParams(
          CalendarParams(
            now = now,
            range = range,
            selectionMode = CalendarParams.SelectionMode.Range,
            cellsInfo = range
              .toIterable()
              .associateWith { CellInfo(disabled = it.dayOfWeek == DayOfWeek.SATURDAY || it.dayOfWeek == DayOfWeek.SUNDAY) },
          )
        )
      }
      Type.WithLabels -> {
        calendar.setParams(
          CalendarParams(
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
        )
      }
      Type.WithColors -> {
        calendar.setParams(
          CalendarParams(
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
        )
      }
      Type.PreselectedRange -> {
        calendar.setParams(
          CalendarParams(
            now = now,
            range = range,
            selectionMode = CalendarParams.SelectionMode.Range,
          )
        )
        calendar.setSelection(
          CalendarSelection.Range(
            start = range.start.plusDays(10),
            end = range.start.plusDays(20),
          )
        )
      }
    }
  }

  override fun onDestroyView() {
    super.onDestroyView()
    scope?.cancel()
    scope = null
  }

  companion object {
    private const val TYPE = "TYPE"
    private const val minPrice = 0
    private const val maxPrice = 5
    private const val noPriceThreshold = 0
    private const val emptyPriceThreshold = 1
    private const val positivePriceThreshold = 2
    private const val neutralPriceThreshold = 3
    private const val negativePriceThreshold = 4

    infix fun of(type: Type) = Calendar2Story().apply {
      arguments = Bundle()
      arguments?.putInt(LAYOUT_ID, R.layout.fragment_calendar_2)
      arguments?.putBoolean(SCROLLABLE, false)
      arguments?.putSerializable(TYPE, type)
    }
  }
}
