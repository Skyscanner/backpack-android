package net.skyscanner.backpack.demo.data

import android.content.Context
import android.text.TextUtils.getLayoutDirectionFromLocale
import android.widget.Toast
import androidx.annotation.VisibleForTesting
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat.LAYOUT_DIRECTION_RTL
import net.skyscanner.backpack.calendar.model.CalendarColoring
import net.skyscanner.backpack.calendar.model.CalendarDay
import net.skyscanner.backpack.calendar.model.CalendarRange
import net.skyscanner.backpack.calendar.model.CalendarSelection
import net.skyscanner.backpack.calendar.model.ColoredBucket
import net.skyscanner.backpack.calendar.model.SingleDay
import net.skyscanner.backpack.calendar.presenter.BpkCalendarController
import net.skyscanner.backpack.calendar.presenter.SelectionType
import net.skyscanner.backpack.demo.R
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone
import java.util.concurrent.TimeUnit

class ExampleBpkCalendarController(
  private val context: Context,
  override val selectionType: SelectionType = SelectionType.RANGE
) : BpkCalendarController(selectionType) {
  override fun onRangeSelected(range: CalendarSelection) {
    when (range) {
      is SingleDay -> Toast.makeText(context, String.format("%s", range.selectedDay.toString()), Toast.LENGTH_SHORT).show()
      is CalendarRange -> Toast.makeText(context, String.format("%s - %s", range.start.toString(), range.end.toString()), Toast.LENGTH_SHORT).show()
    }
  }

  var isColoredCalendar: Boolean = false
  private var colorGenerationOffset: Long = 0L

  override val isRtl: Boolean = getLayoutDirectionFromLocale(Locale.getDefault()) == LAYOUT_DIRECTION_RTL
  override val locale: Locale = Locale.getDefault()
  override val calendarColoring: CalendarColoring?
    get() = if (isColoredCalendar) {
      multiColoredExampleCalendarColoring(colorGenerationOffset, startDate, endDate, context)
    } else {
      null
    }

  fun newColors() {
    colorGenerationOffset += 1
  }
}

@VisibleForTesting
internal fun multiColoredExampleCalendarColoring(
  colorOffset: Long,
  startDate: CalendarDay,
  endDate: CalendarDay,
  context: Context
): CalendarColoring {
  val daysBetweenStartAndEnd = TimeUnit.DAYS.convert(endDate.date.time - startDate.date.time, TimeUnit.MILLISECONDS) + 1
  val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
  calendar.set(Calendar.YEAR, startDate.year)
  calendar.set(Calendar.MONTH, startDate.month)
  calendar.set(Calendar.DAY_OF_MONTH, startDate.day)
  val redSet = mutableSetOf<CalendarDay>()
  val yellowSet = mutableSetOf<CalendarDay>()
  val greenSet = mutableSetOf<CalendarDay>()
  val greySet = mutableSetOf<CalendarDay>()
  val emptySet = mutableSetOf<CalendarDay>()
  for (i in 0 until daysBetweenStartAndEnd) {
    val shiftedIterator = i + colorOffset
    when {
      shiftedIterator % 5 == 0L -> redSet
      shiftedIterator % 5 == 1L -> yellowSet
      shiftedIterator % 5 == 2L -> greenSet
      shiftedIterator % 5 == 3L -> greySet
      shiftedIterator % 5 == 4L -> emptySet
      else -> mutableSetOf()
    }.add(CalendarDay(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)))
    calendar.add(Calendar.DATE, 1)
  }
  return CalendarColoring(
    setOf(
      ColoredBucket(ContextCompat.getColor(context, R.color.bpkRed500), redSet),
      ColoredBucket(ContextCompat.getColor(context, R.color.bpkYellow500), yellowSet),
      ColoredBucket(ContextCompat.getColor(context, R.color.bpkGreen500), greenSet, ContextCompat.getColor(context, R.color.bpkGreen900)),
      ColoredBucket(ContextCompat.getColor(context, R.color.bpkGray100), greySet),
      ColoredBucket(null, emptySet, ContextCompat.getColor(context, R.color.bpkPink500))
    )
  )
}
