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

import android.view.View
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ViewRootForTest
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.junit4.createEmptyComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onLast
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToIndex
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.jakewharton.threetenabp.AndroidThreeTen
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import net.skyscanner.backpack.BpkSnapshotTest
import net.skyscanner.backpack.BpkTestVariant
import net.skyscanner.backpack.calendar2.BpkCalendarTestCases
import net.skyscanner.backpack.calendar2.CalendarParams
import net.skyscanner.backpack.compose.calendar.internal.CALENDAR_GRID_TEST_TAG
import net.skyscanner.backpack.demo.compose.BackpackPreview
import net.skyscanner.backpack.util.InternalBackpackApi
import org.junit.Assume
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@OptIn(InternalBackpackApi::class)
@RunWith(AndroidJUnit4::class)
class BpkCalendarTest : BpkSnapshotTest() {

  @get:Rule
  val rule = activityScenarioRule<AppCompatActivity>()

  @get:Rule
  val composeTestRule = createEmptyComposeRule()

  @Before
  fun setup() {
    setDimensions(700, 400)
    AndroidThreeTen.init(testContext)
  }

  @Test
  fun screenshotTestCalendarDefault() {
    val controller = createController(BpkCalendarTestCases.Params.Default)
    snap(controller)
  }

  @Test
  fun screenshotTestColoredCalendarDefault() {
    val controller = createController(BpkCalendarTestCases.Params.Colored)
    snap(controller)
  }

  @Test
  fun screenshotTestLabeledCalendarDefault() {
    val controller = createController(BpkCalendarTestCases.Params.Labeled)
    snap(controller)
  }

  @Test
  fun screenshotTestCalendarPast() {
    val controller = createController(BpkCalendarTestCases.Params.Past)
    snap(controller)
  }

  @Test
  fun screenshotTestCalendarLeapFebruary() {
    val controller = createController(BpkCalendarTestCases.Params.LeapFebruary)
    controller.setSelection(BpkCalendarTestCases.Selection.LeapFebruary)
    snap(controller)
  }

  @Test
  fun screenshotTestCalendarNonLeapFebruary() {
    val controller = createController(BpkCalendarTestCases.Params.NonLeapFebruary)
    controller.setSelection(BpkCalendarTestCases.Selection.NonLeapFebruary)
    snap(controller)
  }

  @Test
  fun screenshotTestCalendarTodayIsLastDayOfMonth() {
    val controller = createController(BpkCalendarTestCases.Params.TodayIsLastDayOfMonth)
    controller.setSelection(BpkCalendarTestCases.Selection.TodayIsLastDayOfMonth)
    snap(controller)
  }

  @Test
  fun screenshotTestCalendarTodayIsNewYear() {
    val controller = createController(BpkCalendarTestCases.Params.TodayIsNewYear)
    controller.setSelection(BpkCalendarTestCases.Selection.TodayIsNewYear)
    snap(controller)
  }

  @Test
  fun screenshotTestCalendarWithStartDateSelected() {
    val controller = createController(BpkCalendarTestCases.Params.WithStartDateSelected)

    snap(controller) {
      it.onAllNodesWithText("17")
        .onFirst()
        .performClick()
        .assertIsDisplayed()
    }
  }

  @Test
  fun screenshotTestCalendarWithSameStartAndEndDateSelected() {
    val controller = createController(BpkCalendarTestCases.Params.WithSameStartAndEndDateSelected)

    snap(controller) {

      it.onAllNodesWithText("17")
        .onFirst()
        .performClick()
        .assertIsDisplayed()

      it.onAllNodesWithText("17")
        .onFirst()
        .performClick()
        .assertIsDisplayed()
    }
  }

  @Test
  fun screenshotTestCalendarWithStartAndEndDateSelected() {
    val controller = createController(BpkCalendarTestCases.Params.WithStartAndEndDateSelected)
    selectStartEnd(controller)
  }

  @Test
  fun screenshotTestColoredCalendarWithStartAndEndDateSelected() {
    val controller = createController(BpkCalendarTestCases.Params.ColoredWithStartAndEndDateSelected)
    selectStartEnd(controller)
  }

  @Test
  fun screenshotTestCalendarWithSingleDaySelected() {
    val controller = createController(BpkCalendarTestCases.Params.WithSingleDaySelected)

    snap(controller) {

      it
        .onNodeWithTag(CALENDAR_GRID_TEST_TAG)
        .performScrollToIndex(8)
        .assertIsDisplayed()

      it.onAllNodesWithText("13")
        .onLast()
        .performClick()
        .assertIsDisplayed()

      it.onAllNodesWithText("14")
        .onLast()
        .performClick()
        .assertIsDisplayed()
    }
  }

  @Test
  fun screenshotTestCalendarWithRangeSetProgrammatically() {
    val controller = createController(BpkCalendarTestCases.Params.WithRangeSetProgrammatically)
    controller.setSelection(BpkCalendarTestCases.Selection.WithRangeSetProgrammatically)
    snap(controller)
  }

  @Test
  fun screenshotTestCalendarWithSingleDaySetProgrammatically() {
    val controller = createController(BpkCalendarTestCases.Params.WithSingleDaySetProgrammatically)
    controller.setSelection(BpkCalendarTestCases.Selection.WithSingleDaySetProgrammatically)
    snap(controller)
  }

  @Test
  fun screenshotTestCalendarWithDisabledDates() {
    val controller = createController(BpkCalendarTestCases.Params.WithDisabledDates)
    snap(controller)
  }

  @Test
  fun screenshotTestCalendarWithDisabledDates_SelectSingle() {
    val controller = createController(BpkCalendarTestCases.Params.WithDisabledDates_SelectSingle)
    controller.setSelection(BpkCalendarTestCases.Selection.WithDisabledDates_SelectSingle)
    snap(controller)
  }

  @Test
  fun screenshotTestCalendarWithDisabledDates_SelectRange() {
    val controller = createController(BpkCalendarTestCases.Params.WithDisabledDates_SelectRange)
    controller.setSelection(BpkCalendarTestCases.Selection.WithDisabledDates_SelectRange)
    snap(controller)
  }

  @Test
  fun screenshotTestCalendarWithDisabledDates_SelectDisabledDate() {
    val controller = createController(BpkCalendarTestCases.Params.WithDisabledDates_SelectDisabledDate)
    controller.setSelection(BpkCalendarTestCases.Selection.WithDisabledDates_SelectDisabledDate)
    snap(controller)
  }

  @Test
  fun screenshotTestCalendarWithWholeMonthButtonEnabled() {
    val controller = createController(BpkCalendarTestCases.Params.WithWholeMonthButtonEnabled)
    snap(controller)
  }

  @Test
  fun screenshotTestCalendarWithWholeMonthSetProgrammatically() {
    val controller = createController(BpkCalendarTestCases.Params.WithWholeMonthSetProgrammatically)
    controller.setSelection(BpkCalendarTestCases.Selection.WithWholeMonthSetProgrammatically)
    snap(controller)
  }

  private fun selectStartEnd(controller: BpkCalendarController) {
    snap(controller) {
      it
        .onNodeWithTag(CALENDAR_GRID_TEST_TAG)
        .performScrollToIndex(8)
        .assertIsDisplayed()

      it.onAllNodesWithText("17")
        .onFirst()
        .performClick()
        .assertIsDisplayed()

      it.onAllNodesWithText("14")
        .onLast()
        .performClick()
        .assertIsDisplayed()
    }
  }

  private fun snap(controller: BpkCalendarController) =
    composed {
      BpkCalendar(controller = controller)
    }

  private fun snap(controller: BpkCalendarController, content: (ComposeTestRule) -> Unit) {

    // we don't run Compose tests in Themed variant – Compose uses it own theming engine
    Assume.assumeFalse(BpkTestVariant.current == BpkTestVariant.Themed)

    val asyncScreenshot = prepareForAsyncTest()
    rule.scenario.onActivity { activity ->
      activity.setContent {
        BackpackPreview {
          BpkCalendar(controller = controller, modifier = Modifier.testTag("CalendarRoot"))
        }
      }
    }

    content(composeTestRule)
    val view = composeTestRule.onNodeWithTag("CalendarRoot").fetchRootView()

    rule.scenario.onActivity { activity ->
      setupView(view)
      asyncScreenshot.record(view)
    }
  }

  private fun SemanticsNodeInteraction.fetchRootView(): View {
    val node = fetchSemanticsNode()
    return (node.root as ViewRootForTest).view
  }

  private fun createController(params: CalendarParams): BpkCalendarController =
    BpkCalendarController(initialParams = params, coroutineScope = TestScope(UnconfinedTestDispatcher()))
}
