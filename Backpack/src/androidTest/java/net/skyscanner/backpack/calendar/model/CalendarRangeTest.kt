package net.skyscanner.backpack.calendar.model

import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CalendarRangeTest {

  @Test
  fun test_isOnTheSameDate_when_same_date() {
    Assert.assertTrue(
      CalendarRange(
        CalendarDay(2019, 0, 10),
        CalendarDay(2019, 0, 10)
      ).isOnTheSameDate
    )
  }

  @Test
  fun test_isOnTheSameDate_when_not_same_date() {
    Assert.assertFalse(
      CalendarRange(
        CalendarDay(2019, 0, 10),
        CalendarDay(2019, 1, 10)
      ).isOnTheSameDate
    )

    Assert.assertFalse(
      CalendarRange(
        CalendarDay(2019, 0, 10),
        CalendarDay(2020, 0, 10)
      ).isOnTheSameDate
    )

    Assert.assertFalse(
      CalendarRange(
        CalendarDay(2019, 0, 10),
        CalendarDay(2019, 0, 11)
      ).isOnTheSameDate
    )

    Assert.assertFalse(
      CalendarRange(
        CalendarDay(2019, 0, 10),
        null
      ).isOnTheSameDate
    )

    Assert.assertFalse(
      CalendarRange(
        null,
        null
      ).isOnTheSameDate
    )
  }

  @Test
  fun test_isRange() {
    Assert.assertTrue(
      CalendarRange(
        CalendarDay(2019, 0, 10),
        CalendarDay(2020, 0, 10)
      ).isRange
    )

    Assert.assertFalse(
      CalendarRange(
        CalendarDay(2019, 0, 10),
        null
      ).isRange
    )

    Assert.assertFalse(
      CalendarRange(
        null,
        null
      ).isRange
    )
  }

  @Test
  fun test_getDrawType_when_range_and_selected() {
    val range = CalendarRange(
      CalendarDay(2019, 0, 10),
      CalendarDay(2019, 1, 10))

    Assert.assertEquals(
      CalendarRange.DrawType.SELECTED,
      range.getDrawType(2019, 0, 10))

    Assert.assertEquals(
      CalendarRange.DrawType.SELECTED,
      range.getDrawType(2019, 1, 10))
  }

  @Test
  fun test_getDrawType_when_range_and_in_range() {
    val range = CalendarRange(
      CalendarDay(2019, 0, 10),
      CalendarDay(2019, 1, 10))

    Assert.assertEquals(
      CalendarRange.DrawType.RANGE,
      range.getDrawType(2019, 0, 11))

    Assert.assertEquals(
      CalendarRange.DrawType.RANGE,
      range.getDrawType(2019, 0, 20))

    Assert.assertEquals(
      CalendarRange.DrawType.RANGE,
      range.getDrawType(2019, 1, 9))
  }

  @Test
  fun test_getDrawType_when_range_and_not_selected_nor_in_range() {
    val range = CalendarRange(
      CalendarDay(2019, 0, 10),
      CalendarDay(2019, 1, 10))

    Assert.assertEquals(
      CalendarRange.DrawType.NONE,
      range.getDrawType(2019, 0, 9))

    Assert.assertEquals(
      CalendarRange.DrawType.NONE,
      range.getDrawType(2019, 2, 11))

    Assert.assertEquals(
      CalendarRange.DrawType.NONE,
      range.getDrawType(2019, 1, 11))
  }

  @Test
  fun test_getDrawType_when_not_range_and_selected() {
    var range = CalendarRange(
      CalendarDay(2019, 0, 10),
      null)

    Assert.assertEquals(
      CalendarRange.DrawType.SELECTED,
      range.getDrawType(2019, 0, 10))

    range = CalendarRange(
      null,
      CalendarDay(2019, 1, 10))

    Assert.assertEquals(
      CalendarRange.DrawType.SELECTED,
      range.getDrawType(2019, 1, 10))
  }

  @Test
  fun test_getDrawType_when_not_range_and_not_selected() {
    var range = CalendarRange(
      CalendarDay(2019, 0, 10),
      null)

    Assert.assertEquals(
      CalendarRange.DrawType.NONE,
      range.getDrawType(2019, 0, 11))

    range = CalendarRange(
      null,
      CalendarDay(2019, 1, 10))

    Assert.assertEquals(
      CalendarRange.DrawType.NONE,
      range.getDrawType(2019, 1, 11))
  }
}
