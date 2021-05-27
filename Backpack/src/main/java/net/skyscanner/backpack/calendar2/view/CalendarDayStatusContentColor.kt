package net.skyscanner.backpack.calendar2.view

import android.content.Context
import android.content.res.ColorStateList
import net.skyscanner.backpack.R
import net.skyscanner.backpack.calendar2.CalendarParams

internal typealias CalendarDayStatusContentColor = (CalendarParams.Status?) -> ColorStateList

internal fun CalendarDayStatusContentColor(
  context: Context,
): CalendarDayStatusContentColor {

  val default = context.getColorStateList(R.color.bpkTextPrimary)
  val disabled = context.getColorStateList(R.color.__calendarCellDisabledTextColor)
  val positive = context.getColorStateList(R.color.__calendarCellPositiveTextColor)
  val neutral = context.getColorStateList(R.color.__calendarCellNeutralTextColor)
  val negative = context.getColorStateList(R.color.__calendarCellNegativeTextColor)
  val empty = context.getColorStateList(R.color.__calendarCellEmptyTextColor)

  return { status ->
    when (status) {
      CalendarParams.Status.Disabled -> disabled
      CalendarParams.Status.Highlighted -> default
      CalendarParams.Status.Positive -> positive
      CalendarParams.Status.Neutral -> neutral
      CalendarParams.Status.Negative -> negative
      CalendarParams.Status.Empty -> empty
      null -> default
    }
  }
}
