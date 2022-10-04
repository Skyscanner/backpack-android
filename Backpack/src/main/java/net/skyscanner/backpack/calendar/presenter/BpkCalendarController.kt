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

/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

@file:Suppress("DEPRECATION")

package net.skyscanner.backpack.calendar.presenter

import androidx.annotation.VisibleForTesting
import net.skyscanner.backpack.calendar.model.CalendarColoring
import net.skyscanner.backpack.calendar.model.CalendarLabel
import net.skyscanner.backpack.calendar.model.CalendarRange
import net.skyscanner.backpack.calendar.model.CalendarSelection
import net.skyscanner.backpack.calendar.model.SingleDay
import net.skyscanner.backpack.calendar.view.CalendarUpdateCallback
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter
import java.util.Locale

@Deprecated("Use Calendar2 instead")
abstract class BpkCalendarController(
  open val selectionType: SelectionType = SelectionType.RANGE,
  val currentDateProvider: CurrentDateProvider = LocalDateProvider,
) {

  open val startDate: LocalDate = currentDateProvider.invoke()

  open val endDate: LocalDate = currentDateProvider.invoke().plusYears(1)

  open val calendarColoring: CalendarColoring? = null

  open val calendarLabels: Map<LocalDate, CalendarLabel>? = null

  open fun isDateDisabled(date: LocalDate): Boolean {
    return false
  }

  /**
   * When provided, will be used to create footer views for the calendar.
   *
   * If your implementation allows this property to be changed or your [MonthFooterAdapter]
   * implementation can be updated in a way that requires the calendar to update, call
   * [updateContent] after such update.
   *
   * @see [MonthFooterAdapter]
   * @see [updateContent]
   */
  open val monthFooterAdapter: MonthFooterAdapter? = null

  abstract val locale: Locale

  abstract fun onRangeSelected(range: CalendarSelection)

  internal val selectedDay: LocalDate? = null

  internal val selectedRange: CalendarRange = CalendarRange()

  internal var updateContentCallback: CalendarUpdateCallback? = null

  internal fun onDayOfMonthSelected(selectedDay: LocalDate) {
    when (selectionType) {
      SelectionType.SINGLE -> handleForSingle(selectedDay)
      SelectionType.RANGE -> handleForRange(selectedDay)
    }
  }

  private fun handleForSingle(selectedDay: LocalDate) {
    selectedRange.start = selectedDay
    selectedRange.end = null
    onRangeSelected(SingleDay(selectedDay))
  }

  private fun handleForRange(selectedDay: LocalDate) {
    val currentRangeStart = selectedRange.start
    val currentRangeEnd = selectedRange.end

    if (currentRangeStart != null) {
      when {
        currentRangeStart == selectedDay && currentRangeEnd == null -> {
          selectedRange.start = selectedDay
          selectedRange.end = selectedDay
        }
        currentRangeStart == selectedDay && currentRangeEnd != null && currentRangeEnd == selectedDay -> {
          selectedRange.start = null
          selectedRange.end = null
        }
        currentRangeEnd != null || selectedDay.isBefore(currentRangeStart) -> {
          selectedRange.start = selectedDay
          selectedRange.end = null
        }
        else -> {
          selectedRange.end = selectedDay
        }
      }
    } else {
      selectedRange.start = selectedDay
      selectedRange.end = null
    }

    onRangeSelected(selectedRange)
  }

  internal fun getLocalizedDate(date: LocalDate, pattern: String): String =
    DateTimeFormatter.ofPattern(pattern, locale).format(date)

  open fun isToday(year: Int, month: Int, day: Int): Boolean {
    return LocalDate.of(year, month, day) == currentDateProvider.invoke()
  }

  fun updateSelection(selection: CalendarSelection) {
    when (selection) {
      is CalendarRange -> {
        selectedRange.start = selection.start
        selectedRange.end = selection.end
        onRangeSelected(selectedRange)
      }
      is SingleDay -> {
        selectedRange.start = selection.selectedDay
        selectedRange.end = null
        onRangeSelected(SingleDay(selection.selectedDay))
      }
    }
  }

  fun updateContent() = updateContentCallback?.updateContent()
}

@Deprecated("Use Calendar2 instead")
enum class SelectionType {
  RANGE,
  SINGLE
}

@Deprecated("Use Calendar2 instead")
typealias CurrentDateProvider = () -> LocalDate

@VisibleForTesting
@Deprecated("Use Calendar2 instead")
private object LocalDateProvider : CurrentDateProvider {

  override fun invoke(): LocalDate = LocalDate.now()
}
