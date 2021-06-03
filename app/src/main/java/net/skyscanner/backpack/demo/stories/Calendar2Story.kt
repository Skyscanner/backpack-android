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
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlin.random.Random
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import net.skyscanner.backpack.calendar2.BpkCalendar
import net.skyscanner.backpack.calendar2.CalendarFooterAdapter
import net.skyscanner.backpack.calendar2.CalendarParams
import net.skyscanner.backpack.calendar2.CalendarSelection
import net.skyscanner.backpack.calendar2.CellInfo
import net.skyscanner.backpack.calendar2.CellStatus
import net.skyscanner.backpack.calendar2.CellStatusStyle
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.text.BpkText
import net.skyscanner.backpack.toast.BpkToast
import net.skyscanner.backpack.util.ExperimentalBackpackApi
import net.skyscanner.backpack.util.unsafeLazy
import org.threeten.bp.DayOfWeek
import org.threeten.bp.LocalDate
import org.threeten.bp.Period
import org.threeten.bp.YearMonth

@OptIn(ExperimentalBackpackApi::class)
class Calendar2Story : Story() {

  enum class Type {
    SelectionDisabled,
    SelectionSingle,
    SelectionRange,
    WithFooters,
    WithDisabledDates,
    WithLabels,
    WithColors,
    PreselectedRange,
  }

  private val calendar by unsafeLazy { view!!.findViewById<BpkCalendar>(R.id.calendar2)!! }
  private val type by unsafeLazy { arguments!!.getSerializable(TYPE) as Type }
  private val now = LocalDate.now()
  private val range = now - Period.ofYears(1)..(now + Period.ofYears(1))
  private val random = Random(0)

  private val footersAdapter = object : CalendarFooterAdapter<RecyclerView.ViewHolder> {

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder =
      object : RecyclerView.ViewHolder(BpkText(parent.context)) {}

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, yearMonth: YearMonth) {
      val textView = holder.itemView as BpkText
      textView.text = "Month footer for: $yearMonth"
      textView.setTextColor(requireContext().getColorStateList(R.color.bpkTextPrimary))
    }
  }

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
            range = range,
            selectionMode = CalendarParams.SelectionMode.Disabled,
          )
        )
      }
      Type.SelectionSingle -> {
        calendar.setParams(
          CalendarParams(
            range = range,
            selectionMode = CalendarParams.SelectionMode.Single,
          )
        )
      }
      Type.SelectionRange -> {
        calendar.setParams(
          CalendarParams(
            range = range,
            selectionMode = CalendarParams.SelectionMode.Range,
          )
        )
      }
      Type.WithFooters -> {
        calendar.footersAdapter = footersAdapter
        calendar.setParams(
          CalendarParams(
            range = range,
            selectionMode = CalendarParams.SelectionMode.Range,
            footers = range
              .toMap { YearMonth.of(it.year, it.month) }
              .values
              .toSet(),
          )
        )
      }
      Type.WithDisabledDates -> {
        calendar.setParams(
          CalendarParams(
            range = range,
            selectionMode = CalendarParams.SelectionMode.Range,
            disabledDates = range
              .toMap { }
              .filter { it.key.dayOfWeek == DayOfWeek.SATURDAY || it.key.dayOfWeek == DayOfWeek.SUNDAY }
              .keys,
          )
        )
      }
      Type.WithLabels -> {
        calendar.setParams(
          CalendarParams(
            range = range,
            selectionMode = CalendarParams.SelectionMode.Range,
            defaultCellInfo = CellInfo(label = "-"),
            cellsInfo = range.toMap {
              val price = random.nextInt(100)
              CellInfo(
                label = when (price) {
                  in 0..25 -> null
                  else -> "£$price"
                },
                status = when (price) {
                  in 25..50 -> CellStatus.Positive
                  in 50..75 -> CellStatus.Neutral
                  in 75..100 -> CellStatus.Negative
                  else -> null
                },
                style = CellStatusStyle.Label,
              )
            }.filter { it.value.label != null },
          )
        )
      }
      Type.WithColors -> {
        calendar.setParams(
          CalendarParams(
            range = range,
            selectionMode = CalendarParams.SelectionMode.Range,
            cellsInfo = range.toMap {
              val price = random.nextInt(100)
              CellInfo(
                status = when (price) {
                  in 0..25 -> CellStatus.Empty
                  in 25..50 -> CellStatus.Positive
                  in 50..75 -> CellStatus.Neutral
                  in 75..100 -> CellStatus.Negative
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

    infix fun of(type: Type) = Calendar2Story().apply {
      arguments = Bundle()
      arguments?.putInt(LAYOUT_ID, R.layout.fragment_calendar_2)
      arguments?.putBoolean(SCROLLABLE, false)
      arguments?.putSerializable(TYPE, type)
    }
  }

  private fun <R> ClosedRange<LocalDate>.toMap(valueSelector: (LocalDate) -> R): Map<LocalDate, R> {
    val result = mutableMapOf<LocalDate, R>()
    var current = start
    while (current != endInclusive) {
      result[current] = valueSelector(current)
      current = current.plusDays(1)
    }
    return result
  }
}
