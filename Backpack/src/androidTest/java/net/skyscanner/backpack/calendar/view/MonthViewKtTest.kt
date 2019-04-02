package net.skyscanner.backpack.calendar.view

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.jakewharton.threetenabp.AndroidThreeTen
import net.skyscanner.backpack.calendar.model.CalendarColoring
import net.skyscanner.backpack.calendar.model.CalendarDrawingParams
import net.skyscanner.backpack.calendar.model.ColoredBucket
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.threeten.bp.LocalDate

@RunWith(AndroidJUnit4::class)
class MonthViewKtTest {

  @Before
  fun setUp() {
    AndroidThreeTen.init(InstrumentationRegistry.getInstrumentation().targetContext)
  }

  @Test
  fun givenCalendarColoring_whenConvertedToDrawingPaintMap_thenCorrectColors() {
    val day_group1_1 = LocalDate.of(2019, 1, 1)
    val day_group1_2 = LocalDate.of(2019, 1, 2)
    val day_group2_1 = LocalDate.of(2019, 2, 1)
    val day_group2_2 = LocalDate.of(2019, 2, 2)
    val group1Color = 1
    val group2Color = 2
    val calendarDrawingParams = CalendarDrawingParams(
      2019, 1, 1, CalendarColoring(
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

    val drawingPaintMap = calendarDrawingParams.toDrawingPaintMap(isSelectedColor = false)

    assertEquals(drawingPaintMap.getValue(day_group1_1).color, group1Color)
    assertEquals(drawingPaintMap.getValue(day_group1_2).color, group1Color)
    assertEquals(drawingPaintMap.getValue(day_group2_1).color, group2Color)
    assertEquals(drawingPaintMap.getValue(day_group2_2).color, group2Color)
  }
}
