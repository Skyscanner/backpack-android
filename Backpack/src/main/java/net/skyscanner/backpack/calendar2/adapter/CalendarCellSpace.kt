package net.skyscanner.backpack.calendar2.adapter

import android.view.View
import android.view.ViewGroup
import net.skyscanner.backpack.R
import net.skyscanner.backpack.calendar2.data.CalendarDay.Selection
import net.skyscanner.backpack.calendar2.data.CalendarSpace
import net.skyscanner.backpack.calendar2.view.CalendarDaySelectionBackground
import net.skyscanner.backpack.util.ItemHolder

internal class CalendarCellSpace(
  parent: ViewGroup,
) : ItemHolder<CalendarSpace>(parent, R.layout.view_bpk_calendar_space) {

  private val background = CalendarDaySelectionBackground(context)
  private val space = view.findViewById<View>(R.id.bpk_calendar_space)

  override fun bind(model: CalendarSpace) {
    space.background = background(if (model.selected) Selection.Middle else null)
  }
}
