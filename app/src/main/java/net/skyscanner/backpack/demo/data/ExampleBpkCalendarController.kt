package net.skyscanner.backpack.demo.data

import android.content.Context
import android.text.TextUtils.getLayoutDirectionFromLocale
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat.LAYOUT_DIRECTION_RTL
import net.skyscanner.backpack.calendar.model.CalendarColoring
import net.skyscanner.backpack.calendar.model.CalendarDay
import net.skyscanner.backpack.calendar.model.CalendarRange
import net.skyscanner.backpack.calendar.model.ColoredBucket
import net.skyscanner.backpack.calendar.presenter.BpkCalendarController
import net.skyscanner.backpack.demo.R
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone
import java.util.concurrent.TimeUnit

class ExampleBpkCalendarController(private val context: Context) : BpkCalendarController() {
  override fun onRangeSelected(range: CalendarRange) {
    Toast.makeText(context, String.format("%s - %s", range.start.toString(), range.end.toString()), Toast.LENGTH_SHORT)
      .show()
  }

  var isColoredCalendar: Boolean = false
  var colorGenerationOffset: Long = 0L

  override val isRtl: Boolean = getLayoutDirectionFromLocale(Locale.getDefault()) == LAYOUT_DIRECTION_RTL
  override val locale: Locale = Locale.getDefault()
  override val calendarColoring: CalendarColoring?
    get() = if (isColoredCalendar) {
      multiColoredExampleCalendarColoring()
    } else {
      null
    }

  fun newColors() {
    colorGenerationOffset += 1
  }

  private fun multiColoredExampleCalendarColoring(): CalendarColoring {
    val daysBetweenStartAndEnd = TimeUnit.DAYS.convert(endDate.date.time - startDate.date.time, TimeUnit.MILLISECONDS)
    val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
    val redSet = mutableSetOf<CalendarDay>()
    val yellowSet = mutableSetOf<CalendarDay>()
    val greenSet = mutableSetOf<CalendarDay>()
    val greySet = mutableSetOf<CalendarDay>()
    for (i in 0 until daysBetweenStartAndEnd) {
      val shiftedIterator = i + colorGenerationOffset
      when {
        shiftedIterator % 4 == 0L -> redSet
        shiftedIterator % 4 == 1L -> yellowSet
        shiftedIterator % 4 == 2L -> greenSet
        shiftedIterator % 4 == 3L -> greySet
        else -> mutableSetOf()
      }.add(CalendarDay(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)))
      calendar.add(Calendar.DATE, 1)
    }
    return CalendarColoring(
      setOf(
        ColoredBucket(ContextCompat.getColor(context, R.color.bpkRed500), redSet),
        ColoredBucket(ContextCompat.getColor(context, R.color.bpkYellow500), yellowSet),
        ColoredBucket(ContextCompat.getColor(context, R.color.bpkGreen500), greenSet),
        ColoredBucket(ContextCompat.getColor(context, R.color.bpkGray100), greySet)
      )
    )
  }
}
