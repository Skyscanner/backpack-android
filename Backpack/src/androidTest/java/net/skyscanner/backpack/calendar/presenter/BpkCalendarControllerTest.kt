package net.skyscanner.backpack.calendar.presenter

import androidx.test.ext.junit.runners.AndroidJUnit4
import net.skyscanner.backpack.calendar.model.CalendarDay
import net.skyscanner.backpack.calendar.model.CalendarRange
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.Calendar
import java.util.Locale
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.spy
import com.nhaarman.mockitokotlin2.verify

internal open class BpkCalendarControllerTestImpl : BpkCalendarController() {
  override val isRtl = false
  override val locale: Locale = Locale.forLanguageTag("pt-br")
  override fun onRangeSelected(range: CalendarRange) {}
}

@RunWith(AndroidJUnit4::class)
class BpkCalendarControllerTest {

  private lateinit var subject: BpkCalendarControllerTestImpl

  @Before
  fun setUp() {
    subject = BpkCalendarControllerTestImpl()
  }

  @Test
  fun test_default_dates() {
    val today = Calendar.getInstance()
    val nextYear = Calendar.getInstance().apply { add(Calendar.YEAR, 1) }

    Assert.assertEquals(today.atStartOfDay(), subject.startDate.atStartOfDay())
    Assert.assertEquals(nextYear.atStartOfDay(), subject.endDate.atStartOfDay())
  }

  @Test
  fun test_get_localized_date() {
    val date = Calendar.getInstance().apply {
      set(Calendar.YEAR, 2019)
      set(Calendar.MONTH, 1)
      set(Calendar.DAY_OF_MONTH, 1)
    }.time

    Assert.assertEquals("2019-fev-01", subject.getLocalizedDate(date, "yyyy-MMM-dd"))
  }

  @Test
  fun test_onDayOfMonthSelected_when_first_selected() {
    val spy = spy(subject)
    val day = CalendarDay(2019, 0, 1)
    val expectedRange = CalendarRange(day, null)

    spy.onDayOfMonthSelected(day)
    verify(spy, times(1)).onRangeSelected(expectedRange)
  }

  @Test
  fun test_onDayOfMonthSelected_when_selecting_range_end() {
    val spy = spy(subject)
    val start = CalendarDay(2019, 0, 1)
    val end = CalendarDay(2019, 0, 4)
    val expectedRange = CalendarRange(start, end)

    spy.onDayOfMonthSelected(start)
    spy.onDayOfMonthSelected(end)

    verify(spy, times(2)).onRangeSelected(expectedRange)
  }

  @Test
  fun test_onDayOfMonthSelected_when_selecting_same_day() {
    val spy = spy(subject)
    val start = CalendarDay(2019, 0, 1)
    val expectedRange = CalendarRange(start, start)

    spy.onDayOfMonthSelected(start)
    spy.onDayOfMonthSelected(start)

    verify(spy, times(2)).onRangeSelected(expectedRange)
  }

  @Test
  fun test_onDayOfMonthSelected_when_selecting_same_day_thrice() {
    val spy = spy(subject)
    val day = CalendarDay(2019, 0, 1)
    val expectedRange = CalendarRange(null, null)

    spy.onDayOfMonthSelected(day)
    spy.onDayOfMonthSelected(day)
    spy.onDayOfMonthSelected(day)

    verify(spy, times(3)).onRangeSelected(expectedRange)
  }

  @Test
  fun test_onDayOfMonthSelected_when_selecting_day_before_start_day() {
    val spy = spy(subject)
    val start = CalendarDay(2019, 0, 2)
    val end = CalendarDay(2019, 0, 1)

    val expectedRange = CalendarRange(end, null)

    spy.onDayOfMonthSelected(start)
    spy.onDayOfMonthSelected(end)

    verify(spy, times(2)).onRangeSelected(expectedRange)
  }

  @Test
  fun test_onDayOfMonthSelected_when_selecting_different_range_end() {
    val spy = spy(subject)
    val start = CalendarDay(2019, 0, 1)
    val end1 = CalendarDay(2019, 0, 4)
    val end2 = CalendarDay(2019, 0, 3)

    val expectedRange = CalendarRange(start, end2)

    spy.onDayOfMonthSelected(start)
    spy.onDayOfMonthSelected(end1)
    spy.onDayOfMonthSelected(end2)

    verify(spy, times(3)).onRangeSelected(expectedRange)
  }

  private fun Calendar.atStartOfDay() {
    this.apply {
      set(Calendar.HOUR_OF_DAY, 0)
      set(Calendar.MINUTE, 0)
      set(Calendar.SECOND, 0)
      set(Calendar.MILLISECOND, 0)
    }
  }
}
