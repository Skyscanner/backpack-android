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

@file:OptIn(ExperimentalCoroutinesApi::class)

package net.skyscanner.backpack.compose.calendar

import androidx.compose.ui.unit.IntSize
import com.jakewharton.threetenabp.AndroidThreeTen
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import net.skyscanner.backpack.BpkTestVariant
import androidx.test.ext.junit.runners.AndroidJUnit4
import net.skyscanner.backpack.Variants
import net.skyscanner.backpack.compose.BpkSnapshotTest
import net.skyscanner.backpack.calendar2.BpkCalendarTestCases
import net.skyscanner.backpack.calendar2.CalendarParams
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BpkCalendarTest : BpkSnapshotTest() {

  @Before
  fun setup() {
    snapshotSize = IntSize(400, 700)
    AndroidThreeTen.init(testContext)
  }

  @Test
  fun default() {
    val controller = createController(BpkCalendarTestCases.Params.Default)
    snap(controller)
  }

  @Test
  fun labeled() {
    val controller = createController(BpkCalendarTestCases.Params.Labeled)
    snap(controller)
  }

  @Test
  fun past() {
    val controller = createController(BpkCalendarTestCases.Params.Past)
    snap(controller)
  }

  @Test
  fun leapFebruary() {
    val controller = createController(BpkCalendarTestCases.Params.LeapFebruary)
    controller.setSelection(BpkCalendarTestCases.Selection.LeapFebruary)
    snap(controller)
  }

  @Test
  fun nonLeapFebruary() {
    val controller = createController(BpkCalendarTestCases.Params.NonLeapFebruary)
    controller.setSelection(BpkCalendarTestCases.Selection.NonLeapFebruary)
    snap(controller)
  }

  @Test
  fun todayIsLastDayOfMonth() {
    val controller = createController(BpkCalendarTestCases.Params.TodayIsLastDayOfMonth)
    controller.setSelection(BpkCalendarTestCases.Selection.TodayIsLastDayOfMonth)
    snap(controller)
  }

  @Test
  fun todayIsNewYear() {
    val controller = createController(BpkCalendarTestCases.Params.TodayIsNewYear)
    controller.setSelection(BpkCalendarTestCases.Selection.TodayIsNewYear)
    snap(controller)
  }

  @Test
  fun withRangeSet() {
    val controller = createController(BpkCalendarTestCases.Params.WithRangeSetProgrammatically)
    controller.setSelection(BpkCalendarTestCases.Selection.WithRangeSetProgrammatically)
    snap(controller)
  }

  @Test
  fun withSingleDaySet() {
    val controller = createController(BpkCalendarTestCases.Params.WithSingleDaySetProgrammatically)
    controller.setSelection(BpkCalendarTestCases.Selection.WithSingleDaySetProgrammatically)
    snap(controller)
  }

  @Test
  fun withDisabledDates() {
    val controller = createController(BpkCalendarTestCases.Params.WithDisabledDates)
    snap(controller)
  }

  @Test
  fun withDisabledDates_SelectSingle() {
    val controller = createController(BpkCalendarTestCases.Params.WithDisabledDates_SelectSingle)
    controller.setSelection(BpkCalendarTestCases.Selection.WithDisabledDates_SelectSingle)
    snap(controller)
  }

  @Test
  fun withDisabledDates_SelectRange() {
    val controller = createController(BpkCalendarTestCases.Params.WithDisabledDates_SelectRange)
    controller.setSelection(BpkCalendarTestCases.Selection.WithDisabledDates_SelectRange)
    snap(controller)
  }

  @Test
  fun withDisabledDates_SelectDisabledDate() {
    val controller = createController(BpkCalendarTestCases.Params.WithDisabledDates_SelectDisabledDate)
    controller.setSelection(BpkCalendarTestCases.Selection.WithDisabledDates_SelectDisabledDate)
    snap(controller)
  }

  @Test
  fun withWholeMonthButtonEnabled() {
    val controller = createController(BpkCalendarTestCases.Params.WithWholeMonthButtonEnabled)
    snap(controller)
  }

  @Test
  fun withWholeMonthSet() {
    val controller = createController(BpkCalendarTestCases.Params.WithWholeMonthSetProgrammatically)
    controller.setSelection(BpkCalendarTestCases.Selection.WithWholeMonthSetProgrammatically)
    snap(controller)
  }

  private fun snap(controller: BpkCalendarController) =
    snap {
      BpkCalendar(controller = controller)
    }

  private fun createController(params: CalendarParams): BpkCalendarController =
    BpkCalendarController(initialParams = params, coroutineScope = TestScope(UnconfinedTestDispatcher()))
}
