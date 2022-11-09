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
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.jakewharton.threetenabp.AndroidThreeTen
import net.skyscanner.backpack.BpkSnapshotTest
import net.skyscanner.backpack.demo.R
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BpkCalendarTest : BpkSnapshotTest() {

  @get:Rule
  val rule = activityScenarioRule<AppCompatActivity>()

  @Before
  fun setup() {
    setDimensions(700, 400)
    AndroidThreeTen.init(testContext)
  }

  @Test
  fun screenshotTestCalendarDefault() {
    val calendar = BpkCalendar(testContext)
    calendar.setParams(BpkCalendarTestCases.Params.Default)
    snap(calendar)
  }

  @Test
  fun screenshotTestColoredCalendarDefault() {
    val calendar = BpkCalendar(testContext)
    calendar.setParams(BpkCalendarTestCases.Params.Colored)
    snap(calendar)
  }

  @Test
  fun screenshotTestLabeledCalendarDefault() {
    val calendar = BpkCalendar(testContext)
    calendar.setParams(BpkCalendarTestCases.Params.Labeled)
    snap(calendar)
  }

  @Test
  fun screenshotTestCalendarPast() {
    val calendar = BpkCalendar(testContext)
    calendar.setParams(BpkCalendarTestCases.Params.Past)
    snap(calendar)
  }

  @Test
  fun screenshotTestCalendarLeapFebruary() {
    val calendar = BpkCalendar(testContext)
    calendar.setParams(BpkCalendarTestCases.Params.LeapFebruary)
    calendar.setSelection(BpkCalendarTestCases.Selection.LeapFebruary)
    snap(calendar)
  }

  @Test
  fun screenshotTestCalendarNonLeapFebruary() {
    val calendar = BpkCalendar(testContext)
    calendar.setParams(BpkCalendarTestCases.Params.NonLeapFebruary)
    calendar.setSelection(BpkCalendarTestCases.Selection.NonLeapFebruary)
    snap(calendar)
  }

  @Test
  fun screenshotTestCalendarTodayIsLastDayOfMonth() {
    val calendar = BpkCalendar(testContext)
    calendar.setParams(BpkCalendarTestCases.Params.TodayIsLastDayOfMonth)
    calendar.setSelection(BpkCalendarTestCases.Selection.TodayIsLastDayOfMonth)
    snap(calendar)
  }

  @Test
  fun screenshotTestCalendarTodayIsNewYear() {
    val calendar = BpkCalendar(testContext)
    calendar.setParams(BpkCalendarTestCases.Params.TodayIsNewYear)
    calendar.setSelection(BpkCalendarTestCases.Selection.TodayIsNewYear)
    snap(calendar)
  }

  @Test
  fun screenshotTestCalendarWithStartDateSelected() {
    val calendar = BpkCalendar(testContext)

    calendar.setParams(BpkCalendarTestCases.Params.WithStartDateSelected)

    val asyncScreenshot = prepareForAsyncTest()

    rule.scenario.onActivity { activity ->
      val rootLayout = activity.findViewById(android.R.id.content) as FrameLayout
      rootLayout.addView(calendar)
    }

    val indexOfSelectedItem = BpkCalendarTestCases.Indices.WithStartDateSelected_OfSelectedItem
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

    calendar.setParams(BpkCalendarTestCases.Params.WithSameStartAndEndDateSelected)

    val asyncScreenshot = prepareForAsyncTest()

    rule.scenario.onActivity { activity ->
      val rootLayout = activity.findViewById(android.R.id.content) as FrameLayout
      rootLayout.addView(calendar)
    }

    val indexOfSelectedItem = BpkCalendarTestCases.Indices.WithSameStartAndEndDateSelected_OfSelectedItem
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
    calendar.setParams(BpkCalendarTestCases.Params.WithStartAndEndDateSelected)
    selectStartEnd(calendar, prepareForAsyncTest())
  }

  @Test
  fun screenshotTestColoredCalendarWithStartAndEndDateSelected() {
    val calendar = BpkCalendar(testContext)
    calendar.setParams(BpkCalendarTestCases.Params.ColoredWithStartAndEndDateSelected)
    selectStartEnd(calendar, prepareForAsyncTest())
  }

  @Test
  fun screenshotTestCalendarWithSingleDaySelected() {
    val calendar = BpkCalendar(testContext)
    calendar.setParams(BpkCalendarTestCases.Params.WithSingleDaySelected)

    val asyncScreenshot = prepareForAsyncTest()

    rule.scenario.onActivity { activity ->
      val rootLayout = activity.findViewById(android.R.id.content) as FrameLayout
      rootLayout.addView(calendar)
    }

    val indexOfInitialSelectedItem = BpkCalendarTestCases.Indices.WithSingleDaySelectedParams_OfInitialSelectedItem
    val indexOfFinalSelectedItem = BpkCalendarTestCases.Indices.WithSingleDaySelectedParams_OfFinalSelectedItem

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
    calendar.setParams(BpkCalendarTestCases.Params.WithRangeSetProgrammatically)
    calendar.setSelection(BpkCalendarTestCases.Selection.WithRangeSetProgrammatically)
    snap(calendar)
  }

  @Test
  fun screenshotTestCalendarWithSingleDaySetProgrammatically() {
    val calendar = BpkCalendar(testContext)
    calendar.setParams(BpkCalendarTestCases.Params.WithSingleDaySetProgrammatically)
    calendar.setSelection(BpkCalendarTestCases.Selection.WithSingleDaySetProgrammatically)
    snap(calendar)
  }

  @Test
  fun screenshotTestCalendarWithDisabledDates() {
    val calendar = BpkCalendar(testContext)
    calendar.setParams(BpkCalendarTestCases.Params.WithDisabledDates)
    snap(calendar)
  }

  @Test
  fun screenshotTestCalendarWithDisabledDates_SelectSingle() {
    val calendar = BpkCalendar(testContext)
    calendar.setParams(BpkCalendarTestCases.Params.WithDisabledDates_SelectSingle)
    calendar.setSelection(BpkCalendarTestCases.Selection.WithDisabledDates_SelectSingle)
    snap(calendar)
  }

  @Test
  fun screenshotTestCalendarWithDisabledDates_SelectRange() {
    val calendar = BpkCalendar(testContext)
    calendar.setParams(BpkCalendarTestCases.Params.WithDisabledDates_SelectRange)
    calendar.setSelection(BpkCalendarTestCases.Selection.WithDisabledDates_SelectRange)
    snap(calendar)
  }

  @Test
  fun screenshotTestCalendarWithDisabledDates_SelectDisabledDate() {
    val calendar = BpkCalendar(testContext)
    calendar.setParams(BpkCalendarTestCases.Params.WithDisabledDates_SelectDisabledDate)
    calendar.setSelection(BpkCalendarTestCases.Selection.WithDisabledDates_SelectDisabledDate)
    snap(calendar)
  }

  @Test
  fun screenshotTestCalendarWithWholeMonthButtonEnabled() {
    val calendar = BpkCalendar(testContext)
    calendar.setParams(BpkCalendarTestCases.Params.WithWholeMonthButtonEnabled)
    snap(calendar)
  }

  @Test
  fun screenshotTestCalendarWithWholeMonthSetProgrammatically() {
    val calendar = BpkCalendar(testContext)
    calendar.setParams(BpkCalendarTestCases.Params.WithWholeMonthSetProgrammatically)
    calendar.setSelection(BpkCalendarTestCases.Selection.WithWholeMonthSetProgrammatically)
    snap(calendar)
  }

  private fun selectStartEnd(view: View, asyncScreenshot: AsyncSnapshot) {
    rule.scenario.onActivity { activity ->
      val rootLayout = activity.findViewById(android.R.id.content) as FrameLayout
      rootLayout.addView(view)
    }

    val indexOfRangeStart = BpkCalendarTestCases.Indices.SelectStartEnd_OfRangeStart
    val indexOfRangeEnd = BpkCalendarTestCases.Indices.SelectStartEnd_OfRangeEnd

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
}
