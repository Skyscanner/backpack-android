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

package net.skyscanner.backpack.calendar.presenter

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.jakewharton.threetenabp.AndroidThreeTen
import net.skyscanner.backpack.calendar.model.CalendarRange
import net.skyscanner.backpack.calendar.model.CalendarSelection
import net.skyscanner.backpack.calendar.model.SingleDay
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.threeten.bp.LocalDate
import java.util.Locale

internal open class BpkCalendarControllerTestImpl(
  selectionType: SelectionType = SelectionType.RANGE
) : BpkCalendarController(selectionType) {

  var currentRange: CalendarSelection? = null

  override val locale: Locale = Locale.forLanguageTag("pt-br")
  override fun onRangeSelected(range: CalendarSelection) { currentRange = range }
}

@RunWith(AndroidJUnit4::class)
class BpkCalendarControllerTest {

  private lateinit var subject: BpkCalendarControllerTestImpl

  @Before
  fun setUp() {
    AndroidThreeTen.init(InstrumentationRegistry.getInstrumentation().targetContext)
    subject = BpkCalendarControllerTestImpl()
  }

  // region selection type Range
  @Test
  fun test_default_dates() {
    val today = LocalDate.now()
    val nextYear = LocalDate.now().plusYears(1)

    Assert.assertEquals(today, subject.startDate)
    Assert.assertEquals(nextYear, subject.endDate)
  }

  @Test
  fun test_get_localized_date() {
    val date = LocalDate.of(2019, 2, 1)

    Assert.assertEquals("2019-fev-01", subject.getLocalizedDate(date, "yyyy-MMM-dd"))
  }

  @Test
  fun test_onDayOfMonthSelected_when_first_selected() {
    val day = LocalDate.of(2019, 1, 1)
    val expectedRange = CalendarRange(day, null)

    subject.onDayOfMonthSelected(day)
    Assert.assertEquals(expectedRange, subject.currentRange)
  }

  @Test
  fun test_onDayOfMonthSelected_when_selecting_range_end() {
    val start = LocalDate.of(2019, 1, 1)
    val end = LocalDate.of(2019, 1, 4)
    val expectedRange = CalendarRange(start, end)

    subject.onDayOfMonthSelected(start)
    subject.onDayOfMonthSelected(end)

    Assert.assertEquals(expectedRange, subject.currentRange)
  }

  @Test
  fun test_onDayOfMonthSelected_when_selecting_same_day() {
    val start = LocalDate.of(2019, 1, 1)
    val expectedRange = CalendarRange(start, start)

    subject.onDayOfMonthSelected(start)
    subject.onDayOfMonthSelected(start)

    Assert.assertEquals(expectedRange, subject.currentRange)
  }

  @Test
  fun test_onDayOfMonthSelected_when_selecting_same_day_thrice() {
    val day = LocalDate.of(2019, 1, 1)
    val expectedRange = CalendarRange(null, null)

    subject.onDayOfMonthSelected(day)
    subject.onDayOfMonthSelected(day)
    subject.onDayOfMonthSelected(day)

    Assert.assertEquals(expectedRange, subject.currentRange)
  }

  @Test
  fun test_onDayOfMonthSelected_when_selecting_day_before_start_day() {
    val start = LocalDate.of(2019, 1, 2)
    val end = LocalDate.of(2019, 1, 1)

    val expectedRange = CalendarRange(end, null)

    subject.onDayOfMonthSelected(start)
    subject.onDayOfMonthSelected(end)

    Assert.assertEquals(expectedRange, subject.currentRange)
  }

  @Test
  fun test_onDayOfMonthSelected_when_selecting_different_range_end() {
    val start = LocalDate.of(2019, 1, 1)
    val end1 = LocalDate.of(2019, 1, 4)
    val start2 = LocalDate.of(2019, 1, 3)

    val expectedRange = CalendarRange(start2, null)

    subject.onDayOfMonthSelected(start)
    subject.onDayOfMonthSelected(end1)
    subject.onDayOfMonthSelected(start2)

    Assert.assertEquals(expectedRange, subject.currentRange)
  }

  @Test
  fun test_isToday() {
    val today = LocalDate.now()

    val dayOfMonth = today.dayOfMonth % 28 + 1
    Assert.assertTrue(subject.isToday(today.year, today.monthValue, today.dayOfMonth))
    Assert.assertFalse(subject.isToday(today.year, today.monthValue, dayOfMonth))
    Assert.assertFalse(subject.isToday(today.year, today.monthValue % 12 + 1, dayOfMonth))
    Assert.assertFalse(subject.isToday(today.year + 1, today.monthValue, today.dayOfMonth))
  }

  // endregion

  // region selection type Single day
  @Test
  fun test_onDayOfMonthSelected_whenSingleDaySelection() {
    val subject = BpkCalendarControllerTestImpl(SelectionType.SINGLE)
    val selectedDay = LocalDate.of(2019, 4, 16)

    subject.onDayOfMonthSelected(selectedDay)

    Assert.assertEquals(SingleDay(selectedDay), subject.currentRange)
  }

  // endregion
}
