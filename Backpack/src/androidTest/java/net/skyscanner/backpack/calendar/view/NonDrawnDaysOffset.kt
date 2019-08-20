package net.skyscanner.backpack.calendar.view

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.jakewharton.threetenabp.AndroidThreeTen
import net.skyscanner.backpack.calendar.model.CalendarDrawingParams
import net.skyscanner.backpack.calendar.model.CalendarSelection
import net.skyscanner.backpack.calendar.presenter.BpkCalendarController
import net.skyscanner.backpack.calendar.presenter.SelectionType
import net.skyscanner.backpack.util.BpkTheme
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.threeten.bp.LocalDate
import java.util.Locale

@RunWith(AndroidJUnit4::class)
class NonDrawnDaysOffset {

  private val context = InstrumentationRegistry.getInstrumentation().targetContext

  @Before
  fun setUp() {
    BpkTheme.applyDefaultsToContext(context)
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
    monthView.calendarDrawingParams = CalendarDrawingParams(2019, drawMonth, null, null)
    return monthView
  }

  abstract class TestBpkCalendarController : BpkCalendarController(SelectionType.RANGE) {
    override val isRtl: Boolean
      get() = false // unused

    override fun onRangeSelected(range: CalendarSelection) {
      // unused
    }
  }
}
