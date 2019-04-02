package net.skyscanner.backpack.calendar.presenter

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.jakewharton.threetenabp.AndroidThreeTen
import com.nhaarman.mockitokotlin2.spy
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import net.skyscanner.backpack.calendar.model.CalendarRange
import net.skyscanner.backpack.calendar.model.CalendarSelection
import net.skyscanner.backpack.calendar.model.SingleDay
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.threeten.bp.LocalDate
import java.util.Locale

internal open class BpkCalendarControllerTestImpl(selectionType: SelectionType = SelectionType.RANGE) : BpkCalendarController(selectionType) {

  override val isRtl = false
  override val locale: Locale = Locale.forLanguageTag("pt-br")
  override fun onRangeSelected(range: CalendarSelection) {}
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
    val spy = spy(subject)
    val day = LocalDate.of(2019, 1, 1)
    val expectedRange = CalendarRange(day, null)

    spy.onDayOfMonthSelected(day)
    verify(spy, times(1)).onRangeSelected(expectedRange)
  }

  @Test
  fun test_onDayOfMonthSelected_when_selecting_range_end() {
    val spy = spy(subject)
    val start = LocalDate.of(2019, 1, 1)
    val end = LocalDate.of(2019, 1, 4)
    val expectedRange = CalendarRange(start, end)

    spy.onDayOfMonthSelected(start)
    spy.onDayOfMonthSelected(end)

    verify(spy, times(2)).onRangeSelected(expectedRange)
  }

  @Test
  fun test_onDayOfMonthSelected_when_selecting_same_day() {
    val spy = spy(subject)
    val start = LocalDate.of(2019, 1, 1)
    val expectedRange = CalendarRange(start, start)

    spy.onDayOfMonthSelected(start)
    spy.onDayOfMonthSelected(start)

    verify(spy, times(2)).onRangeSelected(expectedRange)
  }

  @Test
  fun test_onDayOfMonthSelected_when_selecting_same_day_thrice() {
    val spy = spy(subject)
    val day = LocalDate.of(2019, 1, 1)
    val expectedRange = CalendarRange(null, null)

    spy.onDayOfMonthSelected(day)
    spy.onDayOfMonthSelected(day)
    spy.onDayOfMonthSelected(day)

    verify(spy, times(3)).onRangeSelected(expectedRange)
  }

  @Test
  fun test_onDayOfMonthSelected_when_selecting_day_before_start_day() {
    val spy = spy(subject)
    val start = LocalDate.of(2019, 1, 2)
    val end = LocalDate.of(2019, 1, 1)

    val expectedRange = CalendarRange(end, null)

    spy.onDayOfMonthSelected(start)
    spy.onDayOfMonthSelected(end)

    verify(spy, times(2)).onRangeSelected(expectedRange)
  }

  @Test
  fun test_onDayOfMonthSelected_when_selecting_different_range_end() {
    val spy = spy(subject)
    val start = LocalDate.of(2019, 1, 1)
    val end1 = LocalDate.of(2019, 1, 4)
    val start2 = LocalDate.of(2019, 1, 3)

    val expectedRange = CalendarRange(start2, null)

    spy.onDayOfMonthSelected(start)
    spy.onDayOfMonthSelected(end1)
    spy.onDayOfMonthSelected(start2)

    verify(spy, times(3)).onRangeSelected(expectedRange)
  }

  @Test
  fun test_isToday() {
    val today = LocalDate.now()

    Assert.assertTrue(subject.isToday(today.year, today.monthValue, today.dayOfMonth))
    Assert.assertFalse(subject.isToday(today.year, today.monthValue, today.dayOfMonth + 1))
    Assert.assertFalse(subject.isToday(today.year, today.monthValue + 1, today.dayOfMonth))
    Assert.assertFalse(subject.isToday(today.year + 1, today.monthValue, today.dayOfMonth))
  }

  // endregion

  // region selection type Single day
  @Test
  fun test_onDayOfMonthSelected_whenSingleDaySelection() {
    val spy = spy(BpkCalendarControllerTestImpl(SelectionType.SINGLE))
    val selectedDay = LocalDate.of(2019, 4, 16)

    spy.onDayOfMonthSelected(selectedDay)

    verify(spy, times(1)).onRangeSelected(SingleDay(selectedDay))
  }

  // endregion
}
