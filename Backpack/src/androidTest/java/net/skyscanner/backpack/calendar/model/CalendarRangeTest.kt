package net.skyscanner.backpack.calendar.model

import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.threeten.bp.LocalDate

@RunWith(AndroidJUnit4::class)
class CalendarRangeTest {

  @Test
  fun test_isOnTheSameDate_when_same_date() {
    Assert.assertTrue(
      CalendarRange(
        LocalDate.of(2019, 2, 10),
        LocalDate.of(2019, 2, 10)
      ).isOnTheSameDate
    )
  }

  @Test
  fun test_isOnTheSameDate_when_not_same_date() {
    Assert.assertFalse(
      CalendarRange(
        LocalDate.of(2019, 1, 10),
        LocalDate.of(2019, 2, 10)
      ).isOnTheSameDate
    )

    Assert.assertFalse(
      CalendarRange(
        LocalDate.of(2019, 1, 10),
        LocalDate.of(2020, 1, 10)
      ).isOnTheSameDate
    )

    Assert.assertFalse(
      CalendarRange(
        LocalDate.of(2019, 1, 10),
        LocalDate.of(2019, 1, 11)
      ).isOnTheSameDate
    )

    Assert.assertFalse(
      CalendarRange(
        LocalDate.of(2019, 1, 10),
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
        LocalDate.of(2019, 1, 10),
        LocalDate.of(2020, 1, 10)
      ).isRange
    )

    Assert.assertFalse(
      CalendarRange(
        LocalDate.of(2019, 1, 10),
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
      LocalDate.of(2019, 1, 10),
      LocalDate.of(2019, 2, 10))

    Assert.assertEquals(
      CalendarRange.DrawType.SELECTED,
      range.getDrawType(LocalDate.of(2019, 1, 10)))

    Assert.assertEquals(
      CalendarRange.DrawType.SELECTED,
      range.getDrawType(LocalDate.of(2019, 2, 10)))
  }

  @Test
  fun test_getDrawType_when_range_and_in_range() {
    val range = CalendarRange(
      LocalDate.of(2019, 1, 10),
      LocalDate.of(2019, 2, 10))

    Assert.assertEquals(
      CalendarRange.DrawType.RANGE,
      range.getDrawType(LocalDate.of(2019, 1, 11)))

    Assert.assertEquals(
      CalendarRange.DrawType.RANGE,
      range.getDrawType(LocalDate.of(2019, 1, 20)))

    Assert.assertEquals(
      CalendarRange.DrawType.RANGE,
      range.getDrawType(LocalDate.of(2019, 2, 9)))
  }

  @Test
  fun test_getDrawType_when_range_and_not_selected_nor_in_range() {
    val range = CalendarRange(
      LocalDate.of(2019, 1, 10),
      LocalDate.of(2019, 2, 10))

    Assert.assertEquals(
      CalendarRange.DrawType.NONE,
      range.getDrawType(LocalDate.of(2019, 1, 9)))

    Assert.assertEquals(
      CalendarRange.DrawType.NONE,
      range.getDrawType(LocalDate.of(2019, 3, 11)))

    Assert.assertEquals(
      CalendarRange.DrawType.NONE,
      range.getDrawType(LocalDate.of(2019, 2, 11)))
  }

  @Test
  fun test_getDrawType_when_not_range_and_selected() {
    var range = CalendarRange(
      LocalDate.of(2019, 1, 10),
      null)

    Assert.assertEquals(
      CalendarRange.DrawType.SELECTED,
      range.getDrawType(LocalDate.of(2019, 1, 10)))

    range = CalendarRange(
      null,
      LocalDate.of(2019, 2, 10))

    Assert.assertEquals(
      CalendarRange.DrawType.SELECTED,
      range.getDrawType(LocalDate.of(2019, 2, 10)))
  }

  @Test
  fun test_getDrawType_when_not_range_and_not_selected() {
    var range = CalendarRange(
      LocalDate.of(2019, 1, 10),
      null)

    Assert.assertEquals(
      CalendarRange.DrawType.NONE,
      range.getDrawType(LocalDate.of(2019, 1, 11)))

    range = CalendarRange(
      null,
      LocalDate.of(2019, 2, 10))

    Assert.assertEquals(
      CalendarRange.DrawType.NONE,
      range.getDrawType(LocalDate.of(2019, 2, 11)))
  }
}
