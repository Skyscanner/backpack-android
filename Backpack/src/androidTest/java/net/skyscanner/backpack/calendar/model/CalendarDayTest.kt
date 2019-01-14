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
  fun test_initialize_empty() {
    calendar.timeInMillis = System.currentTimeMillis()
    atStartOfDay()

    val subject = CalendarDay()

    Assert.assertEquals(
      calendar.get(Calendar.MONTH),
      subject.month)

    Assert.assertEquals(
      calendar.get(Calendar.YEAR),
      subject.year)

    Assert.assertEquals(
      calendar.get(Calendar.DAY_OF_MONTH),
      subject.day)

    Assert.assertEquals(calendar.time, subject.date)
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
  fun test_add() {
    val subject = CalendarDay(2019, 0, 1)

    subject.add(Calendar.DAY_OF_MONTH, 1)
    Assert.assertEquals(2, subject.day)

    subject.add(Calendar.MONTH, 2)
    Assert.assertEquals(2, subject.month)

    subject.add(Calendar.YEAR, 1)
    Assert.assertEquals(2020, subject.year)

    subject.add(Calendar.HOUR, 23)
    Assert.assertEquals(3, subject.day)

    calendar.apply {
      set(Calendar.YEAR, 2020)
      set(Calendar.MONTH, 2)
      set(Calendar.DAY_OF_MONTH, 3)
    }

    atStartOfDay()

    Assert.assertEquals(calendar.time, subject.date)
  }

  @Test
  fun test_setDay() {
    val subject = CalendarDay(2019, 0, 1)
    subject.setDay(2020, 1, 2)

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
