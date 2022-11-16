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

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.scrollTo
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.jakewharton.threetenabp.AndroidThreeTen
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import net.skyscanner.backpack.R
import net.skyscanner.backpack.util.TestActivity
import net.skyscanner.backpack.util.TestContext
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.threeten.bp.LocalDate
import java.util.Locale

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
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

  private val context = TestContext

  @get:Rule
  val rule = activityScenarioRule<TestActivity>()

  @Before
  fun setup() {
    AndroidThreeTen.init(context)
  }

  @Test
  fun withStartDateSelected() = runTest {
    val expected = CalendarSelection.Dates(
      start = LocalDate.of(2019, 1, 17),
      end = null,
    )
    val calendar = BpkCalendar(context)
    calendar.setParams(DefaultRange)

    rule.scenario.onActivity { activity ->
      activity.setContentView(calendar)
    }

    Espresso
      .onView(withId(R.id.bpk_calendar_recycler_view))
      .perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(Indices.WithStartDateSelected, click()))

    val state = calendar.state.first()

    assertEquals(expected, state.selection)
  }

  @Test
  fun withSameStartAndEndDateSelected() = runTest {
    val expected = CalendarSelection.Dates(
      start = LocalDate.of(2019, 1, 17),
      end = LocalDate.of(2019, 1, 17),
    )

    val calendar = BpkCalendar(context)
    calendar.setParams(DefaultRange)

    rule.scenario.onActivity { activity ->
      activity.setContentView(calendar)
    }

    Espresso
      .onView(withId(R.id.bpk_calendar_recycler_view))
      .perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(Indices.WithSameStartAndEndDateSelected, click()))

    Espresso
      .onView(withId(R.id.bpk_calendar_recycler_view))
      .perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(Indices.WithSameStartAndEndDateSelected, click()))

    val state = calendar.state.first()

    assertEquals(expected, state.selection)
  }

  @Test
  fun withStartAndEndDateSelected() = runTest {
    val expected = CalendarSelection.Dates(
      start = LocalDate.of(2019, 1, 17),
      end = LocalDate.of(2019, 2, 14)
    )

    val calendar = BpkCalendar(context)
    calendar.setParams(DefaultRange)

    rule.scenario.onActivity { activity ->
      activity.setContentView(calendar)
    }

    Espresso
      .onView(withId(R.id.bpk_calendar_recycler_view))
      .perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(Indices.SelectStartEnd_OfRangeStart, click()))

    Espresso
      .onView(withId(R.id.bpk_calendar_recycler_view))
      .perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(Indices.SelectStartEnd_OfRangeEnd, click()))

    Espresso
      .onView(withId(R.id.bpk_calendar_recycler_view))
      .perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(Indices.SelectStartEnd_OfRangeStart, scrollTo()))

    val state = calendar.state.first()

    assertEquals(expected, state.selection)
  }

  @Test
  fun withSingleDaySelected() = runTest {
    val expected = CalendarSelection.Single(LocalDate.of(2019, 2, 14))

    val calendar = BpkCalendar(context)
    calendar.setParams(DefaultSingle)

    rule.scenario.onActivity { activity ->
      activity.setContentView(calendar)
    }

    Espresso
      .onView(withId(R.id.bpk_calendar_recycler_view))
      .perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(Indices.WithSingleDaySelectedParams_Initial, click()))

    Espresso
      .onView(withId(R.id.bpk_calendar_recycler_view))
      .perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(Indices.WithSingleDaySelectedParams_Final, click()))

    Espresso // Clicking on multiple dates should result in only one selected
      .onView(withId(R.id.bpk_calendar_recycler_view))
      .perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(Indices.WithSingleDaySelectedParams_Final, scrollTo()))

    val state = calendar.state.first()

    assertEquals(expected, state.selection)
  }

  private object Indices {
    const val WithStartDateSelected = 18

    const val WithSameStartAndEndDateSelected = WithStartDateSelected

    const val SelectStartEnd_OfRangeStart = WithStartDateSelected
    const val SelectStartEnd_OfRangeEnd = 54

    const val WithSingleDaySelectedParams_Initial = 53
    const val WithSingleDaySelectedParams_Final = SelectStartEnd_OfRangeEnd
  }
}
