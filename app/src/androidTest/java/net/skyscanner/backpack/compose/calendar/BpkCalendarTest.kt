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
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.jakewharton.threetenabp.AndroidThreeTen
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import net.skyscanner.backpack.BpkSnapshotTest
import net.skyscanner.backpack.BpkTestVariant
import net.skyscanner.backpack.calendar2.BpkCalendarTestCases
import net.skyscanner.backpack.compose.calendar2.BpkCalendar
import net.skyscanner.backpack.compose.calendar2.BpkCalendarController
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

  val scope = TestScope(UnconfinedTestDispatcher())

  @Before
  fun setup() {
    setDimensions(700, 400)
    AndroidThreeTen.init(testContext)
  }

  @Test
  fun screenshotTestCalendarDefault() {
    val controller = BpkCalendarController(BpkCalendarTestCases.Params.Default, scope)
    snap(controller)
  }

  @Test
  fun screenshotTestColoredCalendarDefault() {
    val controller = BpkCalendarController(BpkCalendarTestCases.Params.Colored, scope)
    snap(controller)
  }

  @Test
  fun screenshotTestLabeledCalendarDefault() {
    val controller = BpkCalendarController(BpkCalendarTestCases.Params.Labeled, scope)
    snap(controller)
  }

  @Test
  fun screenshotTestCalendarPast() {
    val controller = BpkCalendarController(BpkCalendarTestCases.Params.Past, scope)
    snap(controller)
  }

  @Test
  fun screenshotTestCalendarLeapFebruary() {
    val controller = BpkCalendarController(BpkCalendarTestCases.Params.LeapFebruary, scope)
    controller.setSelection(BpkCalendarTestCases.Selection.LeapFebruary)
    snap(controller)
  }

  @Test
  fun screenshotTestCalendarNonLeapFebruary() {
    val controller = BpkCalendarController(BpkCalendarTestCases.Params.NonLeapFebruary, scope)
    controller.setSelection(BpkCalendarTestCases.Selection.NonLeapFebruary)
    snap(controller)
  }

  @Test
  fun screenshotTestCalendarTodayIsLastDayOfMonth() {
    val controller = BpkCalendarController(BpkCalendarTestCases.Params.TodayIsLastDayOfMonth, scope)
    controller.setSelection(BpkCalendarTestCases.Selection.TodayIsLastDayOfMonth)
    snap(controller)
  }

  @Test
  fun screenshotTestCalendarTodayIsNewYear() {
    val controller = BpkCalendarController(BpkCalendarTestCases.Params.TodayIsNewYear, scope)
    controller.setSelection(BpkCalendarTestCases.Selection.TodayIsNewYear)
    snap(controller)
  }

  @Test
  fun screenshotTestCalendarWithStartDateSelected() {
    val controller = BpkCalendarController(BpkCalendarTestCases.Params.WithStartDateSelected, scope)

    snap(controller) {
      it.onAllNodesWithText("17")
        .onFirst()
        .performClick()
        .assertIsDisplayed()
    }
  }

  @Test
  fun screenshotTestCalendarWithSameStartAndEndDateSelected() {
    val controller = BpkCalendarController(BpkCalendarTestCases.Params.WithSameStartAndEndDateSelected, scope)

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
    val controller = BpkCalendarController(BpkCalendarTestCases.Params.WithStartAndEndDateSelected, scope)
    selectStartEnd(controller)
  }

  @Test
  fun screenshotTestColoredCalendarWithStartAndEndDateSelected() {
    val controller = BpkCalendarController(BpkCalendarTestCases.Params.ColoredWithStartAndEndDateSelected, scope)
    selectStartEnd(controller)
  }

  @Test
  fun screenshotTestCalendarWithSingleDaySelected() {
    val controller = BpkCalendarController(BpkCalendarTestCases.Params.WithSingleDaySelected, scope)

    snap(controller) {

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
    val controller = BpkCalendarController(BpkCalendarTestCases.Params.WithRangeSetProgrammatically, scope)
    controller.setSelection(BpkCalendarTestCases.Selection.WithRangeSetProgrammatically)
    snap(controller)
  }

  @Test
  fun screenshotTestCalendarWithSingleDaySetProgrammatically() {
    val controller = BpkCalendarController(BpkCalendarTestCases.Params.WithSingleDaySetProgrammatically, scope)
    controller.setSelection(BpkCalendarTestCases.Selection.WithSingleDaySetProgrammatically)
    snap(controller)
  }

  @Test
  fun screenshotTestCalendarWithDisabledDates() {
    val controller = BpkCalendarController(BpkCalendarTestCases.Params.WithDisabledDates, scope)
    snap(controller)
  }

  @Test
  fun screenshotTestCalendarWithDisabledDates_SelectSingle() {
    val controller = BpkCalendarController(BpkCalendarTestCases.Params.WithDisabledDates_SelectSingle, scope)
    controller.setSelection(BpkCalendarTestCases.Selection.WithDisabledDates_SelectSingle)
    snap(controller)
  }

  @Test
  fun screenshotTestCalendarWithDisabledDates_SelectRange() {
    val controller = BpkCalendarController(BpkCalendarTestCases.Params.WithDisabledDates_SelectRange, scope)
    controller.setSelection(BpkCalendarTestCases.Selection.WithDisabledDates_SelectRange)
    snap(controller)
  }

  @Test
  fun screenshotTestCalendarWithDisabledDates_SelectDisabledDate() {
    val controller = BpkCalendarController(BpkCalendarTestCases.Params.WithDisabledDates_SelectDisabledDate, scope)
    controller.setSelection(BpkCalendarTestCases.Selection.WithDisabledDates_SelectDisabledDate)
    snap(controller)
  }

  @Test
  fun screenshotTestCalendarWithWholeMonthButtonEnabled() {
    val controller = BpkCalendarController(BpkCalendarTestCases.Params.WithWholeMonthButtonEnabled, scope)
    snap(controller)
  }

  @Test
  fun screenshotTestCalendarWithWholeMonthSetProgrammatically() {
    val controller = BpkCalendarController(BpkCalendarTestCases.Params.WithWholeMonthSetProgrammatically, scope)
    controller.setSelection(BpkCalendarTestCases.Selection.WithWholeMonthSetProgrammatically)
    snap(controller)
  }

  private fun selectStartEnd(controller: BpkCalendarController) {
    snap(controller) {
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
}
