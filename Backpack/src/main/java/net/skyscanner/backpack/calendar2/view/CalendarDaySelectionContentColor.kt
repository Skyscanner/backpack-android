package net.skyscanner.backpack.calendar2.view

import android.content.Context
import android.content.res.ColorStateList
import net.skyscanner.backpack.R
import net.skyscanner.backpack.calendar2.data.CalendarDay
import net.skyscanner.backpack.util.use

internal typealias CalendarDaySelectionContentColor = (CalendarDay.Selection?) -> ColorStateList

internal fun CalendarDaySelectionContentColor(
  context: Context,
): CalendarDaySelectionContentColor {

  val default: ColorStateList = context.getColorStateList(R.color.bpkTextPrimary)
  val selected: ColorStateList
  val range: ColorStateList

  context.obtainStyledAttributes(null, R.styleable.BpkCalendar, R.attr.bpkCalendarStyle, 0).use {
    selected = it.getColorStateList(R.styleable.BpkCalendar_calendarDateSelectedTextColor)
      ?: context.getColorStateList(R.color.__calendarSelectedTextColor)

    range = it.getColorStateList(R.styleable.BpkCalendar_calendarRangeTextColor)
      ?: context.getColorStateList(R.color.__calendarRangeText)
  }

  return { selection ->
    when (selection) {
      CalendarDay.Selection.Single -> selected
      CalendarDay.Selection.Double -> selected
      CalendarDay.Selection.Start -> selected
      CalendarDay.Selection.Middle -> range
      CalendarDay.Selection.End -> selected
      null -> default
    }
  }
}
