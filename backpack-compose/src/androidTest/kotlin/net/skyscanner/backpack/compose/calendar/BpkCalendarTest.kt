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

package net.skyscanner.backpack.compose.calendar

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onLast
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToIndex
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import net.skyscanner.backpack.calendar2.CalendarParams
import net.skyscanner.backpack.calendar2.CalendarSelection
import net.skyscanner.backpack.compose.calendar.internal.CALENDAR_GRID_TEST_TAG
import net.skyscanner.backpack.compose.theme.BpkTheme
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.threeten.bp.LocalDate
import java.util.*

@OptIn(ExperimentalCoroutinesApi::class)
class BpkCalendarTest {

  private val initialStartDate = LocalDate.of(2019, 1, 2)
  private val initialEndDate = LocalDate.of(2019, 12, 31)
  private val initialRange = initialStartDate..initialEndDate
  private val now = initialStartDate

  private val DefaultSingle = CalendarParams(
    locale = Locale.UK,
    selectionMode = CalendarParams.SelectionMode.Single,
    range = initialRange,
    now = now,
  )

  private val DefaultRange = DefaultSingle.copy(selectionMode = CalendarParams.SelectionMode.Range)

  @get:Rule
  val composeTestRule = createComposeRule()

  @Test
  fun withSameStartAndEndDateSelected() = runTest {
    val expected = CalendarSelection.Dates(
      start = LocalDate.of(2019, 1, 17),
      end = LocalDate.of(2019, 1, 17),
    )
    val controller = createController(DefaultRange)

    composeTestRule.setContent { BpkTheme { BpkCalendar(controller) } }

    composeTestRule.onAllNodesWithText("17")
      .onFirst()
      .performClick()

    composeTestRule.onAllNodesWithText("17")
      .onFirst()
      .performClick()

    val state = controller.state.first()

    assertEquals(expected, state.selection)
  }

  @Test
  fun withSingleDaySelected() = runTest {
    val expected = CalendarSelection.Single(LocalDate.of(2019, 2, 14))
    val controller = createController(DefaultSingle)

    composeTestRule.setContent { BpkTheme { BpkCalendar(controller) } }

    composeTestRule.onNodeWithTag(CALENDAR_GRID_TEST_TAG)
      .performScrollToIndex(8)

    composeTestRule.onAllNodesWithText("13")
      .onLast()
      .performClick()

    composeTestRule.onAllNodesWithText("14")
      .onLast()
      .performClick()

    val state = controller.state.first()

    assertEquals(expected, state.selection)
  }

  @Test
  fun withStartAndEndDateSelected() = runTest {
    val expected = CalendarSelection.Dates(
      start = LocalDate.of(2019, 1, 17),
      end = LocalDate.of(2019, 2, 14)
    )
    val controller = createController(DefaultRange)

    composeTestRule.setContent { BpkTheme { BpkCalendar(controller) } }

    composeTestRule.onNodeWithTag(CALENDAR_GRID_TEST_TAG)
      .performScrollToIndex(8)

    composeTestRule.onAllNodesWithText("17")
      .onFirst()
      .performClick()

    composeTestRule.onAllNodesWithText("14")
      .onLast()
      .performClick()

    val state = controller.state.first()

    assertEquals(expected, state.selection)
  }


  @Test
  fun withStartDateSelected() = runTest {
    val expected = CalendarSelection.Dates(
      start = LocalDate.of(2019, 1, 17),
      end = null,
    )

    val controller = createController(DefaultRange)

    composeTestRule.setContent { BpkTheme { BpkCalendar(controller) } }

    composeTestRule.onAllNodesWithText("17")
      .onFirst()
      .performClick()

    val state = controller.state.first()

    assertEquals(expected, state.selection)
  }

  private fun createController(params: CalendarParams): BpkCalendarController =
    BpkCalendarController(initialParams = params, coroutineScope = TestScope(UnconfinedTestDispatcher()))
}
