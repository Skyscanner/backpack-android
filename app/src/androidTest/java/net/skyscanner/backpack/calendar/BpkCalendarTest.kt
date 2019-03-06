package net.skyscanner.backpack.calendar

import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.FlakyTest
import androidx.test.rule.ActivityTestRule
import net.skyscanner.backpack.BpkSnapshotTest
import net.skyscanner.backpack.R
import net.skyscanner.backpack.calendar.model.CalendarColoring
import net.skyscanner.backpack.calendar.model.CalendarDay
import net.skyscanner.backpack.calendar.model.CalendarRange
import net.skyscanner.backpack.calendar.presenter.BpkCalendarController
import net.skyscanner.backpack.demo.MainActivity
import org.hamcrest.CoreMatchers
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.Locale

private class BpkCalendarControllerImpl(
  override val isRtl: Boolean,
  override val locale: Locale,
  private val initialStartDate: CalendarDay? = null,
  private val initialEndDate: CalendarDay? = null
) : BpkCalendarController() {
  override val startDate: CalendarDay
    get() = initialStartDate ?: super.startDate

  override val endDate: CalendarDay
    get() = initialEndDate ?: super.endDate

  override fun onRangeSelected(range: CalendarRange) {}

  override fun isToday(year: Int, month: Int, day: Int): Boolean {
    return day == 2 && month == 0 && year == 2019
  }

  override val calendarColoring: CalendarColoring?
    get() = null
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
  }

  @Test
  @FlakyTest
  fun screenshotTestCalendarDefault() {
    val calendar = BpkCalendar(testContext)
    val controller = BpkCalendarControllerImpl(
      false,
      Locale.UK,
      getDate(2019, 0, 2),
      getDate(2019, 11, 31))

    calendar.setController(controller)
    snap(wrapWithBackground(calendar))
  }

  @Test
  @FlakyTest
  fun screenshotTestCalendarPast() {
    val calendar = BpkCalendar(testContext)
    val controller = BpkCalendarControllerImpl(
      false,
      Locale.UK,
      getDate(2017, 0, 2),
      getDate(2017, 11, 31))

    calendar.setController(controller)
    snap(wrapWithBackground(calendar))
  }

  @Test
  @FlakyTest
  fun screenshotTestCalendarWithStartDateSelected() {
    val calendar = BpkCalendar(testContext)
    val controller = BpkCalendarControllerImpl(
      false,
      Locale.UK,
      getDate(2019, 0, 2),
      getDate(2019, 11, 31))

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
  @FlakyTest
  fun screenshotTestCalendarWithSameStartAndEndDateSelected() {
    val calendar = BpkCalendar(testContext)
    val controller = BpkCalendarControllerImpl(
      false,
      Locale.UK,
      getDate(2019, 0, 2),
      getDate(2019, 11, 31))

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
  @FlakyTest
  fun screenshotTestCalendarWithStartAndEndDateSelected() {
    val calendar = BpkCalendar(testContext)
    val controller = BpkCalendarControllerImpl(
      false,
      Locale.UK,
      getDate(2019, 0, 2),
      getDate(2019, 11, 31))

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

    Espresso.onData(CoreMatchers.anything())
      .atPosition(0)
      .perform(ViewActions.scrollTo())
      .check { _, _ ->
        setupView(wrapped)
        asyncScreenshot.record(wrapped)
      }
  }

  @Test
  @FlakyTest
  fun screenshotTestCalendarWithRangeSetProgrammatically() {
    val calendar = BpkCalendar(testContext)
    val controller = BpkCalendarControllerImpl(
      false,
      Locale.UK,
      getDate(2019, 0, 2),
      getDate(2019, 11, 31))

    calendar.setController(controller)
    controller.updateSelection(CalendarRange(CalendarDay(2019, 0, 4), CalendarDay(2019, 0, 9)))
    snap(wrapWithBackground(calendar))
  }

  private fun getDate(year: Int, month: Int, day: Int) = CalendarDay(year, month, day)

  private fun wrapWithBackground(view: View): FrameLayout {
    return FrameLayout(testContext).apply {
      layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)
      setBackgroundColor(ResourcesCompat.getColor(resources, R.color.bpkWhite, null))
      addView(view)
    }
  }
}
