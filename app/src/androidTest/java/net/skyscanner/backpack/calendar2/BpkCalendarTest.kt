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

package net.skyscanner.backpack.calendar2

import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.jakewharton.threetenabp.AndroidThreeTen
import net.skyscanner.backpack.BpkSnapshotTest
import net.skyscanner.backpack.calendar2.extension.toIterable
import net.skyscanner.backpack.demo.R
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.threeten.bp.DayOfWeek
import org.threeten.bp.LocalDate
import org.threeten.bp.Month
import org.threeten.bp.Period
import org.threeten.bp.YearMonth
import java.util.Locale

@RunWith(AndroidJUnit4::class)
class BpkCalendarTest : BpkSnapshotTest() {

  private val initialStartDate = LocalDate.of(2019, 1, 2)
  private val initialEndDate = LocalDate.of(2019, 12, 31)
  private val initialRange = initialStartDate..initialEndDate
  private val now = initialStartDate

  @Before
  fun setup() {
    setDimensions(700, 400)
    AndroidThreeTen.init(testContext)
  }

  @Test
  fun screenshotTestCalendarDefault() {
    val calendar = BpkCalendar(testContext)
    val params = CalendarParams(
      locale = Locale.UK,
      selectionMode = CalendarParams.SelectionMode.Range,
      range = initialRange,
      now = now,
    )
    calendar.setParams(params)
    snap(calendar)
  }

  @Test
  fun screenshotTestColoredCalendarDefault() {
    val calendar = BpkCalendar(testContext)
    val params = CalendarParams(
      locale = Locale.UK,
      selectionMode = CalendarParams.SelectionMode.Range,
      range = initialRange,
      now = now,
      cellsInfo = multiColoredExampleCalendarColoring(initialRange),
    )
    calendar.setParams(params)
    snap(calendar)
  }

  @Test
  fun screenshotTestLabeledCalendarDefault() {
    val calendar = BpkCalendar(testContext)
    val params = CalendarParams(
      locale = Locale.UK,
      selectionMode = CalendarParams.SelectionMode.Range,
      range = initialRange,
      now = now,
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
    calendar.setParams(params)
    snap(calendar)
  }

  @Test
  fun screenshotTestCalendarPast() {
    val calendar = BpkCalendar(testContext)
    val params = CalendarParams(
      locale = Locale.UK,
      range = LocalDate.of(2017, 1, 2)..LocalDate.of(2017, 12, 31),
      selectionMode = CalendarParams.SelectionMode.Range,
      now = now,
    )

    calendar.setParams(params)
    snap(calendar)
  }

  @Test
  fun screenshotTestCalendarLeapFebruary() {
    val calendar = BpkCalendar(testContext)
    val now = LocalDate.of(2020, 2, 1)
    val params = CalendarParams(
      locale = Locale.UK,
      range = LocalDate.of(2020, 2, 1)..LocalDate.of(2020, 12, 31),
      selectionMode = CalendarParams.SelectionMode.Single,
      now = now,
    )

    calendar.setParams(params)
    calendar.setSelection(CalendarSelection.Single(now))
    snap(calendar)
  }

  @Test
  fun screenshotTestCalendarNonLeapFebruary() {
    val calendar = BpkCalendar(testContext)
    val now = LocalDate.of(2021, 2, 1)
    val params = CalendarParams(
      locale = Locale.UK,
      range = LocalDate.of(2021, 2, 1)..LocalDate.of(2021, 12, 31),
      selectionMode = CalendarParams.SelectionMode.Single,
      now = now,
    )

    calendar.setParams(params)
    calendar.setSelection(CalendarSelection.Single(now))
    snap(calendar)
  }

  @Test
  fun screenshotTestCalendarTodayIsLastDayOfMonth() {
    val calendar = BpkCalendar(testContext)
    val now = LocalDate.of(2017, 1, 31)
    val params = CalendarParams(
      locale = Locale.UK,
      range = LocalDate.of(2017, 1, 1)..LocalDate.of(2017, 12, 31),
      selectionMode = CalendarParams.SelectionMode.Single,
      now = now,
    )

    calendar.setParams(params)
    calendar.setSelection(CalendarSelection.Single(now))
    snap(calendar)
  }

  @Test
  fun screenshotTestCalendarTodayIsNewYear() {
    val calendar = BpkCalendar(testContext)
    val now = LocalDate.of(2017, 12, 31)
    val params = CalendarParams(
      locale = Locale.UK,
      range = LocalDate.of(2017, 12, 1)..LocalDate.of(2018, 12, 31),
      selectionMode = CalendarParams.SelectionMode.Single,
      now = now,
    )

    calendar.setParams(params)
    calendar.setSelection(CalendarSelection.Single(now))
    snap(calendar)
  }

  @Test
  fun screenshotTestCalendarWithStartDateSelected() {
    val calendar = BpkCalendar(testContext)
    val params = CalendarParams(
      locale = Locale.UK,
      range = initialRange,
      selectionMode = CalendarParams.SelectionMode.Range,
      now = now,
    )

    calendar.setParams(params)

    val asyncScreenshot = prepareForAsyncTest()

    val scenario = launchActivity<AppCompatActivity>()
    scenario.onActivity { activity ->
      val rootLayout = activity.findViewById(android.R.id.content) as FrameLayout
      rootLayout.addView(calendar)
    }

    val indexOfSelectedItem = 18
    Espresso
      .onView(withId(R.id.bpk_calendar_recycler_view))
      .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(indexOfSelectedItem, ViewActions.click()))
      .check { _, _ ->
        setupView(calendar)
        asyncScreenshot.record(calendar)
      }
  }

  @Test
  fun screenshotTestCalendarWithSameStartAndEndDateSelected() {
    val calendar = BpkCalendar(testContext)
    val params = CalendarParams(
      locale = Locale.UK,
      range = initialRange,
      selectionMode = CalendarParams.SelectionMode.Range,
      now = now,
    )

    calendar.setParams(params)

    val asyncScreenshot = prepareForAsyncTest()

    val scenario = launchActivity<AppCompatActivity>()
    scenario.onActivity { activity ->
      val rootLayout = activity.findViewById(android.R.id.content) as FrameLayout
      rootLayout.addView(calendar)
    }

    val indexOfSelectedItem = 18
    Espresso
      .onView(withId(R.id.bpk_calendar_recycler_view))
      .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(indexOfSelectedItem, ViewActions.click()))

    Espresso
      .onView(withId(R.id.bpk_calendar_recycler_view))
      .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(indexOfSelectedItem, ViewActions.click()))
      .check { _, _ ->
        setupView(calendar)
        asyncScreenshot.record(calendar)
      }
  }

  @Test
  fun screenshotTestCalendarWithStartAndEndDateSelected() {
    val calendar = BpkCalendar(testContext)
    val params = CalendarParams(
      locale = Locale.UK,
      range = initialRange,
      selectionMode = CalendarParams.SelectionMode.Range,
      now = now,
    )

    calendar.setParams(params)

    val asyncScreenshot = prepareForAsyncTest()

    selectStartEnd(calendar, asyncScreenshot)
  }

  @Test
  fun screenshotTestColoredCalendarWithStartAndEndDateSelected() {
    val calendar = BpkCalendar(testContext)
    val params = CalendarParams(
      locale = Locale.UK,
      range = initialRange,
      selectionMode = CalendarParams.SelectionMode.Range,
      cellsInfo = multiColoredExampleCalendarColoring(initialRange),
      now = now,
    )

    calendar.setParams(params)

    val asyncScreenshot = prepareForAsyncTest()

    selectStartEnd(calendar, asyncScreenshot)
  }

  @Test
  fun screenshotTestCalendarWithSingleDaySelected() {
    val calendar = BpkCalendar(testContext)
    val params = CalendarParams(
      locale = Locale.UK,
      range = initialRange,
      selectionMode = CalendarParams.SelectionMode.Single,
      now = now,
    )
    calendar.setParams(params)

    val asyncScreenshot = prepareForAsyncTest()

    val scenario = launchActivity<AppCompatActivity>()
    scenario.onActivity { activity ->
      val rootLayout = activity.findViewById(android.R.id.content) as FrameLayout
      rootLayout.addView(calendar)
    }

    val indexOfInitialSelectedItem = 53
    val indexOfFinalSelectedItem = 54
    Espresso
      .onView(withId(R.id.bpk_calendar_recycler_view))
      .perform(
        RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
          indexOfInitialSelectedItem,
          ViewActions.click()
        )
      )

    Espresso
      .onView(withId(R.id.bpk_calendar_recycler_view))
      .perform(
        RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
          indexOfFinalSelectedItem,
          ViewActions.click()
        )
      )

    Espresso // Clicking on multiple dates should result in only one selected
      .onView(withId(R.id.bpk_calendar_recycler_view))
      .perform(
        RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
          indexOfFinalSelectedItem,
          ViewActions.scrollTo()
        )
      )
      .check { _, _ ->
        setupView(calendar)
        asyncScreenshot.record(calendar)
      }
  }

  @Test
  fun screenshotTestCalendarWithRangeSetProgrammatically() {
    val calendar = BpkCalendar(testContext)
    val params = CalendarParams(
      locale = Locale.UK,
      range = initialRange,
      selectionMode = CalendarParams.SelectionMode.Range,
      now = now,
    )
    calendar.setParams(params)
    calendar.setSelection(CalendarSelection.Dates(LocalDate.of(2019, 1, 4), LocalDate.of(2019, 1, 9)))
    snap(calendar)
  }

  @Test
  fun screenshotTestCalendarWithSingleDaySetProgrammatically() {
    val calendar = BpkCalendar(testContext)
    val params = CalendarParams(
      locale = Locale.UK,
      range = initialRange,
      selectionMode = CalendarParams.SelectionMode.Single,
      now = now,
    )

    calendar.setParams(params)
    calendar.setSelection(CalendarSelection.Single(LocalDate.of(2019, 1, 16)))
    snap(calendar)
  }

  @Test
  fun screenshotTestCalendarWithDisabledDates() {
    val calendar = BpkCalendar(testContext)
    val params = CalendarParams(
      locale = Locale.UK,
      range = initialRange,
      selectionMode = CalendarParams.SelectionMode.Single,
      cellsInfo = disabledDayOfTheWeekInfo(initialRange, DayOfWeek.WEDNESDAY),
      now = now,
    )

    calendar.setParams(params)
    snap(calendar)
  }

  @Test
  fun screenshotTestCalendarWithDisabledDates_SelectSingle() {
    val calendar = BpkCalendar(testContext)
    val params = CalendarParams(
      locale = Locale.UK,
      range = initialRange,
      selectionMode = CalendarParams.SelectionMode.Single,
      cellsInfo = disabledDayOfTheWeekInfo(initialRange, DayOfWeek.WEDNESDAY),
      now = now,
    )

    calendar.setParams(params)
    calendar.setSelection(CalendarSelection.Single(LocalDate.of(2019, 1, 10)))
    snap(calendar)
  }

  @Test
  fun screenshotTestCalendarWithDisabledDates_SelectRange() {
    val calendar = BpkCalendar(testContext)
    val params = CalendarParams(
      locale = Locale.UK,
      range = initialRange,
      selectionMode = CalendarParams.SelectionMode.Range,
      cellsInfo = disabledDayOfTheWeekInfo(initialRange, DayOfWeek.WEDNESDAY),
      now = now,
    )
    calendar.setParams(params)
    calendar.setSelection(CalendarSelection.Dates(LocalDate.of(2019, 1, 4), LocalDate.of(2019, 1, 10)))
    snap(calendar)
  }

  @Test
  fun screenshotTestCalendarWithDisabledDates_SelectDisabledDate() {
    val calendar = BpkCalendar(testContext)
    val params = CalendarParams(
      locale = Locale.UK,
      range = initialRange,
      selectionMode = CalendarParams.SelectionMode.Single,
      cellsInfo = disabledDayOfTheWeekInfo(initialRange, DayOfWeek.WEDNESDAY),
      now = now,
    )

    calendar.setParams(params)
    calendar.setSelection(CalendarSelection.Single(LocalDate.of(2019, 1, 9)))
    snap(calendar)
  }

  @Test
  fun screenshotTestCalendarWithWholeMonthButtonEnabled() {
    val calendar = BpkCalendar(testContext)
    val params = CalendarParams(
      locale = Locale.UK,
      range = initialRange,
      selectionMode = CalendarParams.SelectionMode.Range,
      now = now,
      monthSelectionMode = CalendarParams.MonthSelectionMode.SelectWholeMonth("Select whole month")
    )

    calendar.setParams(params)
    snap(calendar)
  }

  @Test
  fun screenshotTestCalendarWithWholeMonthSetProgrammatically() {
    val calendar = BpkCalendar(testContext)
    val now = LocalDate.of(2019, 1, 1)
    val range = now..(now + Period.ofYears(2))

    val params = CalendarParams(
      locale = Locale.UK,
      range = range,
      selectionMode = CalendarParams.SelectionMode.Range,
      now = now,
      monthSelectionMode = CalendarParams.MonthSelectionMode.SelectWholeMonth("Select whole month")
    )

    calendar.setParams(params)
    calendar.setSelection(CalendarSelection.Month(YearMonth.of(2019, Month.JANUARY)))
    snap(calendar)
  }

  private fun selectStartEnd(view: View, asyncScreenshot: AsyncSnapshot) {
    val scenario = launchActivity<AppCompatActivity>()
    scenario.onActivity { activity ->
      val rootLayout = activity.findViewById(android.R.id.content) as FrameLayout
      rootLayout.addView(view)
    }

    val indexOfRangeStart = 18
    val indexOfRangeEnd = 54
    Espresso
      .onView(withId(R.id.bpk_calendar_recycler_view))
      .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(indexOfRangeStart, ViewActions.click()))

    Espresso
      .onView(withId(R.id.bpk_calendar_recycler_view))
      .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(indexOfRangeEnd, ViewActions.click()))

    Espresso
      .onView(withId(R.id.bpk_calendar_recycler_view))
      .perform(
        RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
          indexOfRangeStart,
          ViewActions.scrollTo()
        )
      )
      .check { _, _ ->
        setupView(view)
        asyncScreenshot.record(view)
      }
  }

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
