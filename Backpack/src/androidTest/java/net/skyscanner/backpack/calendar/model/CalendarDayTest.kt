package net.skyscanner.backpack.calendar.model

import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.Calendar
import java.util.TimeZone

@RunWith(AndroidJUnit4::class)
class CalendarDayTest {

  private lateinit var calendar: Calendar

  @Before
  fun setUp() {
    calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
  }

  @Test
  fun test_initialize_with_date() {
    val subject = CalendarDay(2019, 0, 1)

    Assert.assertEquals(0, subject.month)
    Assert.assertEquals(2019, subject.year)
    Assert.assertEquals(1, subject.day)

    calendar.apply {
      set(Calendar.YEAR, 2019)
      set(Calendar.MONTH, 0)
      set(Calendar.DAY_OF_MONTH, 1)
    }

    atStartOfDay()

    Assert.assertEquals(calendar.time, subject.date)
  }

  @Test
  fun test_addDays() {
    val subject = CalendarDay(2019, 1, 1).newInstanceByAddedDays(1)

    Assert.assertEquals(2, subject.day)

    calendar.apply {
      set(Calendar.YEAR, 2019)
      set(Calendar.MONTH, 1)
      set(Calendar.DAY_OF_MONTH, 2)
    }

    atStartOfDay()

    Assert.assertEquals(calendar.time, subject.date)
  }

  @Test
  fun test_get() {
    val subject = CalendarDay(2020, 1, 2)

    Assert.assertEquals(2, subject.day)
    Assert.assertEquals(1, subject.month)
    Assert.assertEquals(2020, subject.year)

    calendar.apply {
      set(Calendar.YEAR, 2020)
      set(Calendar.MONTH, 1)
      set(Calendar.DAY_OF_MONTH, 2)
    }

    atStartOfDay()

    Assert.assertEquals(calendar.time, subject.date)
  }

  @Test
  fun test_toString() {
    val subject = CalendarDay(2019, 0, 1)
    Assert.assertEquals("2019-01-01", subject.toString())
  }

  private fun atStartOfDay() {
    calendar.apply {
      set(Calendar.HOUR_OF_DAY, 0)
      set(Calendar.MINUTE, 0)
      set(Calendar.SECOND, 0)
      set(Calendar.MILLISECOND, 0)
    }
  }
}
