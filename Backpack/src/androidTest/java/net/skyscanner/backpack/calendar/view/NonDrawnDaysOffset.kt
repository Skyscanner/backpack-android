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

@file:Suppress("DEPRECATION")

package net.skyscanner.backpack.calendar.view

import android.content.Context
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.jakewharton.threetenabp.AndroidThreeTen
import net.skyscanner.backpack.calendar.model.CalendarDrawingParams
import net.skyscanner.backpack.calendar.model.CalendarSelection
import net.skyscanner.backpack.calendar.presenter.BpkCalendarController
import net.skyscanner.backpack.calendar.presenter.SelectionType
import net.skyscanner.backpack.util.TestActivity
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.threeten.bp.LocalDate
import java.util.Locale

@RunWith(AndroidJUnit4::class)
class NonDrawnDaysOffset {

  @get:Rule
  internal var activityRule: ActivityTestRule<TestActivity> =
    ActivityTestRule(TestActivity::class.java)

  private lateinit var context: Context

  @Before
  fun setUp() {
    context = activityRule.activity
    AndroidThreeTen.init(context)
  }

  @Test
  fun givenFirstFewDaysInMonth_whenGetOffset_thenNoOffset() {
    val monthView = givenMonthView(
      locale = Locale.US,
      startDate = LocalDate.of(2019, 1, 2),
      drawMonth = 2
    )

    val offset = monthView.getNonDrawnDaysOffset()

    Assert.assertEquals(0, offset)
  }

  @Test
  fun givenMidMonthAndSunWeekStart_whenGetOffset_thenOffsetToPreviousSat() {
    val monthView = givenMonthView(
      locale = Locale.US,
      startDate = LocalDate.of(2019, 2, 13),
      drawMonth = 2
    )

    val offset = monthView.getNonDrawnDaysOffset()

    Assert.assertEquals(9, offset)
  }

  @Test
  fun givenMidMonthAndMonWeekStart_whenGetOffset_thenOffsetToPreviousSun() {
    val monthView = givenMonthView(
      locale = Locale.GERMANY,
      startDate = LocalDate.of(2019, 2, 13),
      drawMonth = 2
    )

    val offset = monthView.getNonDrawnDaysOffset()

    Assert.assertEquals(10, offset)
  }

  @Test
  fun givenLastDayInMonthOnSunAndSunWeekStart_whenGetOffset_thenOffsetToPreviousSun() {
    val monthView = givenMonthView(
      locale = Locale.US,
      startDate = LocalDate.of(2019, 3, 31),
      drawMonth = 3
    )

    val offset = monthView.getNonDrawnDaysOffset()

    Assert.assertEquals(30, offset)
  }

  @Test
  fun givenLastDayInMonthOnSunAndMonWeekStart_whenGetOffset_thenOffsetToPreviousMon() {
    val monthView = givenMonthView(
      locale = Locale.GERMANY,
      startDate = LocalDate.of(2019, 3, 31),
      drawMonth = 3
    )

    val offset = monthView.getNonDrawnDaysOffset()

    Assert.assertEquals(24, offset)
  }

  @Test
  fun givenOtherMonthIsDrawn_whenGetOffset_thenNoOffset() {
    val monthView = givenMonthView(
      locale = Locale.GERMANY,
      startDate = LocalDate.of(2019, 3, 31),
      drawMonth = 4
    )

    val offset = monthView.getNonDrawnDaysOffset()

    Assert.assertEquals(0, offset)
  }

  private fun givenMonthView(locale: Locale, startDate: LocalDate, drawMonth: Int): MonthView {
    val monthView = MonthView(context = context)

    monthView.controller = object : TestBpkCalendarController() {
      override val locale: Locale
        get() {
          return locale
        }
      override val startDate: LocalDate
        get() {
          return startDate
        }
    }
    monthView.calendarDrawingParams = CalendarDrawingParams(2019, drawMonth, null, null, labels = null)
    return monthView
  }

  abstract class TestBpkCalendarController : BpkCalendarController(SelectionType.RANGE) {

    override fun onRangeSelected(range: CalendarSelection) {
      // unused
    }
  }
}
