package net.skyscanner.backpack.calendar

import android.view.View
import android.widget.FrameLayout
import androidx.annotation.VisibleForTesting
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.jakewharton.threetenabp.AndroidThreeTen
import net.skyscanner.backpack.BpkSnapshotTest
import net.skyscanner.backpack.calendar.model.CalendarColoring
import net.skyscanner.backpack.calendar.model.CalendarRange
import net.skyscanner.backpack.calendar.model.CalendarSelection
import net.skyscanner.backpack.calendar.model.SingleDay
import net.skyscanner.backpack.calendar.presenter.*
import net.skyscanner.backpack.createThemedContext
import net.skyscanner.backpack.demo.MainActivity
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.demo.data.multiColoredExampleCalendarColoring
import org.hamcrest.CoreMatchers
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.threeten.bp.DayOfWeek
import org.threeten.bp.LocalDate
import java.util.*

private val today = LocalDate.of(2019, 1, 2)

@VisibleForTesting
class MockDateProvider(private val value: LocalDate) : CurrentDateProvider {

  override fun invoke(): LocalDate = value
}

private class BpkCalendarControllerImpl(
  override val isRtl: Boolean,
  override val locale: Locale,
  private val initialStartDate: LocalDate? = null,
  private val initialEndDate: LocalDate? = null,
  override val selectionType: SelectionType = SelectionType.RANGE,
  override val calendarColoring: CalendarColoring? = null,
  private val disabledDayOfTheWeek: DayOfWeek? = null,
  override val monthFooterAdapter: MonthFooterAdapter? = null
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
  var activityRule: ActivityTestRule<MainActivity> =
    ActivityTestRule(MainActivity::class.java)

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
      false,
      Locale.UK,
      LocalDate.of(2019, 1, 2),
      LocalDate.of(2019, 12, 31)
    )

    calendar.setController(controller)
    snap(wrapWithBackground(calendar))
  }

  @Test
  fun screenshotTestColoredCalendarDefault() {
    val calendar = BpkCalendar(testContext)
    val initialStartDate = LocalDate.of(2019, 1, 2)
    val initialEndDate = LocalDate.of(2019, 12, 31)
    val controller = BpkCalendarControllerImpl(
      false,
      Locale.UK,
      initialStartDate,
      initialEndDate,
      SelectionType.RANGE,
      multiColoredExampleCalendarColoring(0, initialStartDate, initialEndDate, testContext))

    calendar.setController(controller)
    snap(wrapWithBackground(calendar))
  }

  @Test
  fun screenshotTestCalendarPast() {
    val calendar = BpkCalendar(testContext)
    val controller = BpkCalendarControllerImpl(
      false,
      Locale.UK,
      LocalDate.of(2017, 1, 2),
      LocalDate.of(2017, 12, 31)
    )

    calendar.setController(controller)
    snap(wrapWithBackground(calendar))
  }

  @Test
  fun screenshotTestCalendarWithStartDateSelected() {
    val calendar = BpkCalendar(testContext)
    val controller = BpkCalendarControllerImpl(
      false,
      Locale.UK,
      LocalDate.of(2019, 1, 2),
      LocalDate.of(2019, 12, 31)
    )

    calendar.setController(controller)
    val wrapped = wrapWithBackground(calendar)

    val asyncScreenshot = prepareForAsyncTest()

    activity.runOnUiThread {
      val rootLayout = activity.findViewById(android.R.id.content) as FrameLayout
      rootLayout.addView(wrapped)
    }

    Espresso.onData(CoreMatchers.anything())
      .atPosition(0)
      .perform(ViewActions.click())
      .check { _, _ ->
        setupView(wrapped)
        asyncScreenshot.record(wrapped)
      }
  }

  @Test
  fun screenshotTestCalendarWithSameStartAndEndDateSelected() {
    val calendar = BpkCalendar(testContext)
    val controller = BpkCalendarControllerImpl(
      false,
      Locale.UK,
      LocalDate.of(2019, 1, 2),
      LocalDate.of(2019, 12, 31)
    )

    calendar.setController(controller)
    val wrapped = wrapWithBackground(calendar)

    val asyncScreenshot = prepareForAsyncTest()

    activity.runOnUiThread {
      val rootLayout = activity.findViewById(android.R.id.content) as FrameLayout
      rootLayout.addView(wrapped)
    }

    Espresso.onData(CoreMatchers.anything())
      .atPosition(0)
      .perform(ViewActions.click())

    Espresso.onData(CoreMatchers.anything())
      .atPosition(0)
      .perform(ViewActions.click())
      .check { _, _ ->
        setupView(wrapped)
        asyncScreenshot.record(wrapped)
      }
  }

  @Test
  fun screenshotTestCalendarWithStartAndEndDateSelected() {
    val calendar = BpkCalendar(testContext)
    val controller = BpkCalendarControllerImpl(
      false,
      Locale.UK,
      LocalDate.of(2019, 1, 2),
      LocalDate.of(2019, 12, 31)
    )

    calendar.setController(controller)
    val wrapped = wrapWithBackground(calendar)

    val asyncScreenshot = prepareForAsyncTest()

    selectStartEnd(wrapped, asyncScreenshot)
  }

  @Test
  fun screenshotTestColoredCalendarWithStartAndEndDateSelected() {
    val calendar = BpkCalendar(testContext)
    val initialStartDate = LocalDate.of(2019, 1, 2)
    val initialEndDate = LocalDate.of(2019, 12, 31)
    val controller = BpkCalendarControllerImpl(
      false,
      Locale.UK,
      initialStartDate,
      initialEndDate,
      SelectionType.RANGE,
      multiColoredExampleCalendarColoring(0, initialStartDate, initialEndDate, testContext)
    )

    calendar.setController(controller)
    val wrapped = wrapWithBackground(calendar)

    val asyncScreenshot = prepareForAsyncTest()

    selectStartEnd(wrapped, asyncScreenshot)
  }

  @Test
  fun screenshotTestCalendarWithSingleDaySelected() {
    val calendar = BpkCalendar(testContext)
    val controller = BpkCalendarControllerImpl(
      false,
      Locale.UK,
      LocalDate.of(2019, 1, 2),
      LocalDate.of(2019, 12, 31),
      SelectionType.SINGLE
    )
    calendar.setController(controller)
    val wrapped = wrapWithBackground(calendar)

    val asyncScreenshot = prepareForAsyncTest()

    activity.runOnUiThread {
      val rootLayout = activity.findViewById(android.R.id.content) as FrameLayout
      rootLayout.addView(wrapped)
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
        setupView(wrapped)
        asyncScreenshot.record(wrapped)
      }
  }

  @Test
  fun screenshotTestCalendarWithRangeSetProgrammatically() {
    val calendar = BpkCalendar(testContext)
    val controller = BpkCalendarControllerImpl(
      false,
      Locale.UK,
      LocalDate.of(2019, 1, 2),
      LocalDate.of(2019, 12, 31)
    )
    calendar.setController(controller)
    controller.updateSelection(CalendarRange(LocalDate.of(2019, 1, 4), LocalDate.of(2019, 1, 9)))
    snap(wrapWithBackground(calendar))
  }

  @Test
  fun screenshotTestCalendarWithSingleDaySetProgrammatically() {
    val calendar = BpkCalendar(testContext)
    val controller = BpkCalendarControllerImpl(
      false,
      Locale.UK,
      LocalDate.of(2019, 1, 2),
      LocalDate.of(2019, 12, 31),
      SelectionType.SINGLE
    )

    calendar.setController(controller)
    controller.updateSelection(SingleDay(LocalDate.of(2019, 1, 16)))
    snap(wrapWithBackground(calendar))
  }

  @Test
  fun screenshotTestCalendarWithDisabledDates() {
    val calendar = BpkCalendar(testContext)
    val controller = BpkCalendarControllerImpl(
      false,
      Locale.UK,
      LocalDate.of(2019, 1, 2),
      LocalDate.of(2019, 12, 31),
      SelectionType.SINGLE,
      disabledDayOfTheWeek = DayOfWeek.WEDNESDAY
    )

    calendar.setController(controller)
    snap(wrapWithBackground(calendar))
  }

  @Test
  fun screenshotTestCalendarWithDisabledDates_SelectSingle() {
    val calendar = BpkCalendar(testContext)
    val controller = BpkCalendarControllerImpl(
      false,
      Locale.UK,
      LocalDate.of(2019, 1, 2),
      LocalDate.of(2019, 12, 31),
      SelectionType.SINGLE,
      disabledDayOfTheWeek = DayOfWeek.WEDNESDAY
    )

    calendar.setController(controller)
    controller.updateSelection(SingleDay(LocalDate.of(2019, 1, 10)))
    snap(wrapWithBackground(calendar))
  }

  @Test
  fun screenshotTestCalendarWithDisabledDates_SelectRange() {
    val calendar = BpkCalendar(testContext)
    val controller = BpkCalendarControllerImpl(
      false,
      Locale.UK,
      LocalDate.of(2019, 1, 2),
      LocalDate.of(2019, 12, 31),
      disabledDayOfTheWeek = DayOfWeek.WEDNESDAY
    )
    calendar.setController(controller)
    controller.updateSelection(CalendarRange(LocalDate.of(2019, 1, 4), LocalDate.of(2019, 1, 10)))
    snap(wrapWithBackground(calendar))
  }

  @Test
  fun screenshotTestCalendarWithDisabledDates_SelectDisabledDate() {
    val calendar = BpkCalendar(testContext)
    val controller = BpkCalendarControllerImpl(
      false,
      Locale.UK,
      LocalDate.of(2019, 1, 2),
      LocalDate.of(2019, 12, 31),
      SelectionType.SINGLE,
      disabledDayOfTheWeek = DayOfWeek.WEDNESDAY
    )

    calendar.setController(controller)
    controller.updateSelection(SingleDay(LocalDate.of(2019, 1, 9)))
    snap(wrapWithBackground(calendar))
  }

  @Test
  fun screenshotTestCalendarWithStartAndEndDateSelected_withTheme() {
    val calendar = BpkCalendar(createThemedContext(testContext))
    val controller = BpkCalendarControllerImpl(
      false,
      Locale.UK,
      LocalDate.of(2019, 1, 2),
      LocalDate.of(2019, 12, 31)
    )

    calendar.setController(controller)
    val wrapped = wrapWithBackground(calendar)

    val asyncScreenshot = prepareForAsyncTest()

    selectStartEnd(wrapped, asyncScreenshot)
  }

  @Test
  fun screenshotTestCalendarPast_withTheme() {
    val calendar = BpkCalendar(createThemedContext(testContext))
    val controller = BpkCalendarControllerImpl(
      false,
      Locale.UK,
      LocalDate.of(2017, 1, 2),
      LocalDate.of(2017, 12, 31)
    )

    calendar.setController(controller)
    snap(wrapWithBackground(calendar))
  }

  @Test
  fun screenshotTestCalendarPast_cutPreviousWeeks() {
    val calendar = BpkCalendar(testContext)
    val controller = BpkCalendarControllerImpl(
      false,
      Locale.GERMAN,
      LocalDate.of(2019, 6, 8)
    )
    calendar.setController(controller)
    snap(wrapWithBackground(calendar))
  }

  @Test
  fun screenshotTestCalendarSetSelectionFromTop() {
    val calendar = BpkCalendar(testContext)
    val controller = BpkCalendarControllerImpl(
      false,
      Locale.UK,
      LocalDate.of(2017, 1, 2),
      LocalDate.of(2017, 12, 31)
    )

    calendar.setController(controller)
    calendar.setSelectionFromTop(2)
    snap(wrapWithBackground(calendar))
  }

  // NOTE: the "dot" before the holiday doesn't render because the test lib can't handle
  // relative drawables
  @Test
  fun screenshotTestCalendarWithHighlightedDaysFooter() {
    val monthFooterAdapter = HighlightedDaysAdapter(
      testContext,
      Locale.UK,
      setOf(
        HighlightedDaysAdapter.HighlightedDay(
          LocalDate.of(2017, 1, 1), "New Year's Day"),
        HighlightedDaysAdapter.HighlightedDay(
          LocalDate.of(2017, 1, 2), "Bank Holiday")
      )
    )

    val calendar = BpkCalendar(testContext)
    val controller = BpkCalendarControllerImpl(
      false,
      Locale.UK,
      LocalDate.of(2017, 1, 2),
      LocalDate.of(2017, 12, 31),
      SelectionType.RANGE,
      null,
      null,
      monthFooterAdapter
    )

    calendar.setController(controller)
    snap(wrapWithBackground(calendar))
  }

  private fun selectStartEnd(wrapped: FrameLayout, asyncScreenshot: AsyncSnapshot) {
    activity.runOnUiThread {
      val rootLayout = activity.findViewById(android.R.id.content) as FrameLayout
      rootLayout.addView(wrapped)
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
        setupView(wrapped)
        asyncScreenshot.record(wrapped)
      }
  }

  private fun wrapWithBackground(view: View): FrameLayout {
    return FrameLayout(testContext).apply {
      layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)
      setBackgroundColor(ResourcesCompat.getColor(resources, R.color.bpkWhite, null))
      addView(view)
    }
  }
}
