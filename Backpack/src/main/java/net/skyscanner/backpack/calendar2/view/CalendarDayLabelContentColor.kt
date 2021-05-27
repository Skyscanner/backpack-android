package net.skyscanner.backpack.calendar2.view

import android.content.Context
import android.content.res.ColorStateList
import net.skyscanner.backpack.R
import net.skyscanner.backpack.calendar2.CalendarParams

internal typealias CalendarDayLabelContentColor = (CalendarParams.Status?) -> ColorStateList

internal fun CalendarDayLabelContentColor(
  context: Context,
): CalendarDayLabelContentColor {

  val default = context.getColorStateList(R.color.bpkTextSecondary)
  val positive = context.getColorStateList(R.color.bpkMonteverde)
  val disabled = context.getColorStateList(R.color.__calendarCellDisabledTextColor)

  return { status ->
    when (status) {
      CalendarParams.Status.Disabled -> disabled
      CalendarParams.Status.Highlighted -> default
      CalendarParams.Status.Positive -> positive
      CalendarParams.Status.Neutral -> default
      CalendarParams.Status.Negative -> default
      CalendarParams.Status.Empty -> disabled
      null -> default
    }
  }
}
