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

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.jakewharton.threetenabp.AndroidThreeTen
import net.skyscanner.backpack.BpkSnapshotTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BpkCalendarTest : BpkSnapshotTest() {

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
  fun screenshotTestCalendarWithRangeSet() {
    val calendar = BpkCalendar(testContext)
    calendar.setParams(BpkCalendarTestCases.Params.WithRangeSetProgrammatically)
    calendar.setSelection(BpkCalendarTestCases.Selection.WithRangeSetProgrammatically)
    snap(calendar)
  }

  @Test
  fun screenshotTestCalendarWithSingleDaySet() {
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
  fun screenshotTestCalendarWithWholeMonthSet() {
    val calendar = BpkCalendar(testContext)
    calendar.setParams(BpkCalendarTestCases.Params.WithWholeMonthSetProgrammatically)
    calendar.setSelection(BpkCalendarTestCases.Selection.WithWholeMonthSetProgrammatically)
    snap(calendar)
  }
}
