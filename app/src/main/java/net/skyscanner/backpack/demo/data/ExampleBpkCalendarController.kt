package net.skyscanner.backpack.demo.data

import android.content.Context
import android.text.TextUtils.getLayoutDirectionFromLocale
import android.widget.Toast
import androidx.annotation.VisibleForTesting
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat.LAYOUT_DIRECTION_RTL
import net.skyscanner.backpack.calendar.model.*
import net.skyscanner.backpack.calendar.presenter.BpkCalendarController
import net.skyscanner.backpack.calendar.presenter.SelectionType
import net.skyscanner.backpack.demo.R
import org.threeten.bp.DayOfWeek
import java.util.Locale
import org.threeten.bp.LocalDate
import org.threeten.bp.temporal.ChronoUnit

open class ExampleBpkCalendarController(
  private val context: Context,
  override val selectionType: SelectionType = SelectionType.RANGE,
  private val disableDates: Boolean = false,
  private val automationMode: Boolean = false
) : BpkCalendarController(selectionType) {
  override fun onRangeSelected(range: CalendarSelection) {
    if (automationMode) {
      return
    }
    when (range) {
      is SingleDay -> Toast.makeText(context, String.format("%s", range.selectedDay.toString()), Toast.LENGTH_SHORT).show()
      is CalendarRange -> Toast.makeText(context, String.format("%s - %s", range.start.toString(), range.end.toString()), Toast.LENGTH_SHORT).show()
    }
  }

  var isColoredCalendar: Boolean = false
  private var colorGenerationOffset: Long = 0L
  private var disabledWeekdays = DayOfWeek.WEDNESDAY

  override val isRtl: Boolean = getLayoutDirectionFromLocale(Locale.getDefault()) == LAYOUT_DIRECTION_RTL
  override val locale: Locale = Locale.getDefault()
  override val calendarColoring: CalendarColoring?
    get() = if (isColoredCalendar) {
      multiColoredExampleCalendarColoring(colorGenerationOffset, startDate, endDate, context)
    } else {
      null
    }

  override fun isDateDisabled(date: LocalDate): Boolean {
    if (!disableDates) {
      return false
    }
    return date.dayOfWeek == disabledWeekdays
  }

  fun newColors() {
    colorGenerationOffset += 1
  }

  fun shiftDisabledDates() {
    disabledWeekdays += 1
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
  var dateIterator = LocalDate.of(startDate.year, startDate.month, startDate.dayOfMonth)
  for (i in 0 until daysBetweenStartAndEnd) {
    val shiftedIterator = i + colorOffset
    when {
      shiftedIterator % 5 == 0L -> redSet
      shiftedIterator % 5 == 1L -> yellowSet
      shiftedIterator % 5 == 2L -> greenSet
      shiftedIterator % 5 == 3L -> greySet
      else -> mutableSetOf()
    }.add(dateIterator)
    dateIterator = dateIterator.plusDays(1)
  }
  return CalendarColoring(
    setOf(
      ColoredBucket(CalendarCellStyle.Negative, redSet),
      ColoredBucket(CalendarCellStyle.Neutral, yellowSet),
      ColoredBucket(CalendarCellStyle.Positive, greenSet),
      ColoredBucket(
        CalendarCellStyle.Custom(ContextCompat.getColor(context, R.color.bpkBackgroundSecondary)),
        greySet
      )
    )
  )
}
