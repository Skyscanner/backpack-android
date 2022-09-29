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

package net.skyscanner.backpack.calendar

import android.view.View
import android.widget.FrameLayout
import androidx.annotation.VisibleForTesting
import androidx.appcompat.app.AppCompatActivity
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.jakewharton.threetenabp.AndroidThreeTen
import net.skyscanner.backpack.BpkSnapshotTest
import net.skyscanner.backpack.calendar.model.CalendarColoring
import net.skyscanner.backpack.calendar.model.CalendarLabel
import net.skyscanner.backpack.calendar.model.CalendarRange
import net.skyscanner.backpack.calendar.model.CalendarSelection
import net.skyscanner.backpack.calendar.model.SingleDay
import net.skyscanner.backpack.calendar.presenter.BpkCalendarController
import net.skyscanner.backpack.calendar.presenter.CurrentDateProvider
import net.skyscanner.backpack.calendar.presenter.HighlightedDaysAdapter
import net.skyscanner.backpack.calendar.presenter.MonthFooterAdapter
import net.skyscanner.backpack.calendar.presenter.SelectionType
import net.skyscanner.backpack.demo.data.multiColoredExampleCalendarColoring
import org.hamcrest.CoreMatchers
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.threeten.bp.DayOfWeek
import org.threeten.bp.LocalDate
import java.util.Locale

private val today = LocalDate.of(2019, 1, 2)

@VisibleForTesting
class MockDateProvider(private val value: LocalDate) : CurrentDateProvider {

  override fun invoke(): LocalDate = value
}

private class BpkCalendarControllerImpl(
  override val locale: Locale,
  private val initialStartDate: LocalDate? = null,
  private val initialEndDate: LocalDate? = null,
  override val selectionType: SelectionType = SelectionType.RANGE,
  override val calendarColoring: CalendarColoring? = null,
  private val disabledDayOfTheWeek: DayOfWeek? = null,
  override val monthFooterAdapter: MonthFooterAdapter? = null,
  override val calendarLabels: Map<LocalDate, CalendarLabel>? = null,
) : BpkCalendarController(selectionType, MockDateProvider(today)) {
  override val startDate: LocalDate
    get() = initialStartDate ?: super.startDate

  override val endDate: LocalDate
    get() = initialEndDate ?: super.endDate

  override fun onRangeSelected(range: CalendarSelection) {}

  override fun isDateDisabled(date: LocalDate): Boolean {
    return date.dayOfWeek == disabledDayOfTheWeek
  }
}

@RunWith(AndroidJUnit4::class)
class BpkCalendarTest : BpkSnapshotTest() {
  private lateinit var activity: AppCompatActivity

  @get:Rule
  var activityRule: ActivityTestRule<AppCompatActivity> =
    ActivityTestRule(AppCompatActivity::class.java)

  @Before
  fun setup() {
    setDimensions(700, 400)
    activity = activityRule.activity
    AndroidThreeTen.init(activity)
  }

  @Test
  fun screenshotTestCalendarDefault() {
    val calendar = BpkCalendar(testContext)
    val controller = BpkCalendarControllerImpl(
      Locale.UK,
      LocalDate.of(2019, 1, 2),
      LocalDate.of(2019, 12, 31)
    )

    calendar.setController(controller)
    snap(calendar)
  }

  @Test
  fun screenshotTestColoredCalendarDefault() {
    val calendar = BpkCalendar(testContext)
    val initialStartDate = LocalDate.of(2019, 1, 2)
    val initialEndDate = LocalDate.of(2019, 12, 31)
    val controller = BpkCalendarControllerImpl(
      Locale.UK,
      initialStartDate,
      initialEndDate,
      SelectionType.RANGE,
      multiColoredExampleCalendarColoring(0, initialStartDate, initialEndDate, testContext)
    )

    calendar.setController(controller)
    snap(calendar)
  }

  @Test
  fun screenshotTestLabeledCalendarDefault() {
    val calendar = BpkCalendar(testContext)
    val initialStartDate = LocalDate.of(2019, 1, 2)
    val initialEndDate = LocalDate.of(2019, 12, 31)
    val controller = BpkCalendarControllerImpl(
      Locale.UK,
      initialStartDate,
      initialEndDate,
      SelectionType.RANGE,
      calendarLabels = mapOf(
        LocalDate.of(initialStartDate.year, initialStartDate.month, initialStartDate.dayOfMonth + 1) to
          CalendarLabel(text = "£10", style = CalendarLabel.Style.PriceHigh),
        LocalDate.of(initialStartDate.year, initialStartDate.month, initialStartDate.dayOfMonth + 2) to
          CalendarLabel(text = "£11", style = CalendarLabel.Style.PriceMedium),
        LocalDate.of(initialStartDate.year, initialStartDate.month, initialStartDate.dayOfMonth + 3) to
          CalendarLabel(text = "£12", style = CalendarLabel.Style.PriceLow),
        LocalDate.of(initialStartDate.year, initialStartDate.month, initialStartDate.dayOfMonth + 4) to
          CalendarLabel(text = "£900000000000000", style = CalendarLabel.Style.PriceLow),
        LocalDate.of(initialStartDate.year, initialStartDate.month, initialStartDate.dayOfMonth + 5) to
          CalendarLabel(text = "£900000", style = CalendarLabel.Style.PriceLow),
      ),
    )

    calendar.setController(controller)
    snap(calendar)
  }

  @Test
  fun screenshotTestCalendarPast() {
    val calendar = BpkCalendar(testContext)
    val controller = BpkCalendarControllerImpl(
      Locale.UK,
      LocalDate.of(2017, 1, 2),
      LocalDate.of(2017, 12, 31)
    )

    calendar.setController(controller)
    snap(calendar)
  }

  @Test
  fun screenshotTestCalendarWithStartDateSelected() {
    val calendar = BpkCalendar(testContext)
    val controller = BpkCalendarControllerImpl(
      Locale.UK,
      LocalDate.of(2019, 1, 2),
      LocalDate.of(2019, 12, 31)
    )

    calendar.setController(controller)

    val asyncScreenshot = prepareForAsyncTest()

    activity.runOnUiThread {
      val rootLayout = activity.findViewById(android.R.id.content) as FrameLayout
      rootLayout.addView(calendar)
    }

    Espresso.onData(CoreMatchers.anything())
      .atPosition(0)
      .perform(ViewActions.click())
      .check { _, _ ->
        setupView(calendar)
        asyncScreenshot.record(calendar)
      }
  }

  @Test
  fun screenshotTestCalendarWithSameStartAndEndDateSelected() {
    val calendar = BpkCalendar(testContext)
    val controller = BpkCalendarControllerImpl(
      Locale.UK,
      LocalDate.of(2019, 1, 2),
      LocalDate.of(2019, 12, 31)
    )

    calendar.setController(controller)

    val asyncScreenshot = prepareForAsyncTest()

    activity.runOnUiThread {
      val rootLayout = activity.findViewById(android.R.id.content) as FrameLayout
      rootLayout.addView(calendar)
    }

    Espresso.onData(CoreMatchers.anything())
      .atPosition(0)
      .perform(ViewActions.click())

    Espresso.onData(CoreMatchers.anything())
      .atPosition(0)
      .perform(ViewActions.click())
      .check { _, _ ->
        setupView(calendar)
        asyncScreenshot.record(calendar)
      }
  }

  @Test
  fun screenshotTestCalendarWithStartAndEndDateSelected() {
    val calendar = BpkCalendar(testContext)
    val controller = BpkCalendarControllerImpl(
      Locale.UK,
      LocalDate.of(2019, 1, 2),
      LocalDate.of(2019, 12, 31)
    )

    calendar.setController(controller)

    val asyncScreenshot = prepareForAsyncTest()

    selectStartEnd(calendar, asyncScreenshot)
  }

  @Test
  fun screenshotTestColoredCalendarWithStartAndEndDateSelected() {
    val calendar = BpkCalendar(testContext)
    val initialStartDate = LocalDate.of(2019, 1, 2)
    val initialEndDate = LocalDate.of(2019, 12, 31)
    val controller = BpkCalendarControllerImpl(
      Locale.UK,
      initialStartDate,
      initialEndDate,
      SelectionType.RANGE,
      multiColoredExampleCalendarColoring(0, initialStartDate, initialEndDate, testContext)
    )

    calendar.setController(controller)

    val asyncScreenshot = prepareForAsyncTest()

    selectStartEnd(calendar, asyncScreenshot)
  }

  @Test
  fun screenshotTestCalendarWithSingleDaySelected() {
    val calendar = BpkCalendar(testContext)
    val controller = BpkCalendarControllerImpl(
      Locale.UK,
      LocalDate.of(2019, 1, 2),
      LocalDate.of(2019, 12, 31),
      SelectionType.SINGLE
    )
    calendar.setController(controller)

    val asyncScreenshot = prepareForAsyncTest()

    activity.runOnUiThread {
      val rootLayout = activity.findViewById(android.R.id.content) as FrameLayout
      rootLayout.addView(calendar)
    }

    Espresso.onData(CoreMatchers.anything())
      .atPosition(0)
      .perform(ViewActions.click())

    Espresso.onData(CoreMatchers.anything())
      .atPosition(1)
      .perform(ViewActions.click())

    Espresso.onData(CoreMatchers.anything()) // Clicking on multiple dates should result in only one selected
      .atPosition(1)
      .perform(ViewActions.scrollTo())
      .check { _, _ ->
        setupView(calendar)
        asyncScreenshot.record(calendar)
      }
  }

  @Test
  fun screenshotTestCalendarWithRangeSetProgrammatically() {
    val calendar = BpkCalendar(testContext)
    val controller = BpkCalendarControllerImpl(
      Locale.UK,
      LocalDate.of(2019, 1, 2),
      LocalDate.of(2019, 12, 31)
    )
    calendar.setController(controller)
    controller.updateSelection(CalendarRange(LocalDate.of(2019, 1, 4), LocalDate.of(2019, 1, 9)))
    snap(calendar)
  }

  @Test
  fun screenshotTestCalendarWithSingleDaySetProgrammatically() {
    val calendar = BpkCalendar(testContext)
    val controller = BpkCalendarControllerImpl(
      Locale.UK,
      LocalDate.of(2019, 1, 2),
      LocalDate.of(2019, 12, 31),
      SelectionType.SINGLE
    )

    calendar.setController(controller)
    controller.updateSelection(SingleDay(LocalDate.of(2019, 1, 16)))
    snap(calendar)
  }

  @Test
  fun screenshotTestCalendarWithDisabledDates() {
    val calendar = BpkCalendar(testContext)
    val controller = BpkCalendarControllerImpl(
      Locale.UK,
      LocalDate.of(2019, 1, 2),
      LocalDate.of(2019, 12, 31),
      SelectionType.SINGLE,
      disabledDayOfTheWeek = DayOfWeek.WEDNESDAY
    )

    calendar.setController(controller)
    snap(calendar)
  }

  @Test
  fun screenshotTestCalendarWithDisabledDates_SelectSingle() {
    val calendar = BpkCalendar(testContext)
    val controller = BpkCalendarControllerImpl(
      Locale.UK,
      LocalDate.of(2019, 1, 2),
      LocalDate.of(2019, 12, 31),
      SelectionType.SINGLE,
      disabledDayOfTheWeek = DayOfWeek.WEDNESDAY
    )

    calendar.setController(controller)
    controller.updateSelection(SingleDay(LocalDate.of(2019, 1, 10)))
    snap(calendar)
  }

  @Test
  fun screenshotTestCalendarWithDisabledDates_SelectRange() {
    val calendar = BpkCalendar(testContext)
    val controller = BpkCalendarControllerImpl(
      Locale.UK,
      LocalDate.of(2019, 1, 2),
      LocalDate.of(2019, 12, 31),
      disabledDayOfTheWeek = DayOfWeek.WEDNESDAY
    )
    calendar.setController(controller)
    controller.updateSelection(CalendarRange(LocalDate.of(2019, 1, 4), LocalDate.of(2019, 1, 10)))
    snap(calendar)
  }

  @Test
  fun screenshotTestCalendarWithDisabledDates_SelectDisabledDate() {
    val calendar = BpkCalendar(testContext)
    val controller = BpkCalendarControllerImpl(
      Locale.UK,
      LocalDate.of(2019, 1, 2),
      LocalDate.of(2019, 12, 31),
      SelectionType.SINGLE,
      disabledDayOfTheWeek = DayOfWeek.WEDNESDAY
    )

    calendar.setController(controller)
    controller.updateSelection(SingleDay(LocalDate.of(2019, 1, 9)))
    snap(calendar)
  }

  @Test
  fun screenshotTestCalendarPast_cutPreviousWeeks() {
    val calendar = BpkCalendar(testContext)
    val controller = BpkCalendarControllerImpl(
      Locale.GERMAN,
      LocalDate.of(2019, 6, 8)
    )
    calendar.setController(controller)
    snap(calendar)
  }

  @Test
  fun screenshotTestCalendarSetSelectionFromTop() {
    val calendar = BpkCalendar(testContext)
    val controller = BpkCalendarControllerImpl(
      Locale.UK,
      LocalDate.of(2017, 1, 2),
      LocalDate.of(2017, 12, 31)
    )

    calendar.setController(controller)
    calendar.setSelectionFromTop(2)
    snap(calendar)
  }

  @Test
  fun screenshotTestCalendarWithHighlightedDaysFooter() {
    val monthFooterAdapter = HighlightedDaysAdapter(
      testContext,
      Locale.UK,
      setOf(
        HighlightedDaysAdapter.HighlightedDay(
          LocalDate.of(2017, 1, 1), "New Year's Day"
        ),
        HighlightedDaysAdapter.HighlightedDay(
          date = LocalDate.of(2017, 1, 2),
          description = "Bank Holiday",
          descriptionOnly = true
        )
      )
    )

    val calendar = BpkCalendar(testContext)
    val controller = BpkCalendarControllerImpl(
      Locale.UK,
      LocalDate.of(2017, 1, 2),
      LocalDate.of(2017, 12, 31),
      SelectionType.RANGE,
      null,
      null,
      monthFooterAdapter
    )

    calendar.setController(controller)
    snap(calendar)
  }

  private fun selectStartEnd(view: View, asyncScreenshot: AsyncSnapshot) {
    activity.runOnUiThread {
      val rootLayout = activity.findViewById(android.R.id.content) as FrameLayout
      rootLayout.addView(view)
    }

    Espresso.onData(CoreMatchers.anything())
      .atPosition(0)
      .perform(ViewActions.click())

    Espresso.onData(CoreMatchers.anything())
      .atPosition(1)
      .perform(ViewActions.click())

    Espresso.onData(CoreMatchers.anything())
      .atPosition(0)
      .perform(ViewActions.scrollTo())
      .check { _, _ ->
        setupView(view)
        asyncScreenshot.record(view)
      }
  }
}
