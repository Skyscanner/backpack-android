package net.skyscanner.backpack.demo.data

import android.content.Context
import android.text.TextUtils.getLayoutDirectionFromLocale
import android.widget.Toast
import androidx.annotation.VisibleForTesting
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat.LAYOUT_DIRECTION_RTL
import net.skyscanner.backpack.calendar.model.CalendarColoring
import net.skyscanner.backpack.calendar.model.CalendarRange
import net.skyscanner.backpack.calendar.model.CalendarSelection
import net.skyscanner.backpack.calendar.model.ColoredBucket
import net.skyscanner.backpack.calendar.model.SingleDay
import net.skyscanner.backpack.calendar.presenter.BpkCalendarController
import net.skyscanner.backpack.calendar.presenter.SelectionType
import net.skyscanner.backpack.demo.R
import java.util.Locale
import org.threeten.bp.LocalDate
import org.threeten.bp.temporal.ChronoUnit

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
  startDate: LocalDate,
  endDate: LocalDate,
  context: Context
): CalendarColoring {
  val daysBetweenStartAndEnd = ChronoUnit.DAYS.between(startDate, endDate) + 1
  val redSet = mutableSetOf<LocalDate>()
  val yellowSet = mutableSetOf<LocalDate>()
  val greenSet = mutableSetOf<LocalDate>()
  val greySet = mutableSetOf<LocalDate>()
  val emptySet = mutableSetOf<LocalDate>()
  var dateIterator = LocalDate.of(startDate.year, startDate.month, startDate.dayOfMonth)
  for (i in 0 until daysBetweenStartAndEnd) {
    val shiftedIterator = i + colorOffset
    when {
      shiftedIterator % 5 == 0L -> redSet
      shiftedIterator % 5 == 1L -> yellowSet
      shiftedIterator % 5 == 2L -> greenSet
      shiftedIterator % 5 == 3L -> greySet
      shiftedIterator % 5 == 4L -> emptySet
      else -> mutableSetOf()
    }.add(dateIterator)
    dateIterator = dateIterator.plusDays(1)
  }
  return CalendarColoring(
    setOf(
      ColoredBucket(ContextCompat.getColor(context, R.color.bpkPetra), redSet),
      ColoredBucket(ContextCompat.getColor(context, R.color.bpkBagan), yellowSet),
      ColoredBucket(ContextCompat.getColor(context, R.color.bpkSagano), greenSet),
      ColoredBucket(ContextCompat.getColor(context, R.color.bpkSkyGrayTint07), greySet),
      ColoredBucket(null, emptySet, ContextCompat.getColor(context, R.color.bpkPetra))
    )
  )
}
