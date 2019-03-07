package net.skyscanner.backpack.calendar.view

import androidx.test.ext.junit.runners.AndroidJUnit4
import net.skyscanner.backpack.calendar.model.CalendarColoring
import net.skyscanner.backpack.calendar.model.CalendarDay
import net.skyscanner.backpack.calendar.model.CalendarDrawingParams
import net.skyscanner.backpack.calendar.model.ColoredBucket
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MonthViewKtTest {

  @Test
  fun givenCalendarColoring_whenConvertedToDrawingPaintMap_thenCorrectColors() {
    val day_group1_1 = CalendarDay(2019, 0, 1)
    val day_group1_2 = CalendarDay(2019, 0, 2)
    val day_group2_1 = CalendarDay(2019, 1, 1)
    val day_group2_2 = CalendarDay(2019, 1, 2)
    val group1Color = 1
    val group2Color = 2
    val calendarDrawingParams = CalendarDrawingParams(
      2019, 0, 1, CalendarColoring(
      setOf(
        ColoredBucket(
          group1Color,
          setOf(
            day_group1_1,
            day_group1_2
          )
        ),
        ColoredBucket(
          group2Color,
          setOf(
            day_group2_1,
            day_group2_2
          )
        )
      )
    )
    )

    val drawingPaintMap = calendarDrawingParams.toDrawingPaintMap()

    assertEquals(drawingPaintMap.getValue(day_group1_1).color, group1Color)
    assertEquals(drawingPaintMap.getValue(day_group1_2).color, group1Color)
    assertEquals(drawingPaintMap.getValue(day_group2_1).color, group2Color)
    assertEquals(drawingPaintMap.getValue(day_group2_2).color, group2Color)
  }
}
