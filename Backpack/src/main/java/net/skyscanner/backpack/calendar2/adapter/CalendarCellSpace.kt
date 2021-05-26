package net.skyscanner.backpack.calendar2.adapter

import android.view.View
import android.view.ViewGroup
import net.skyscanner.backpack.R
import net.skyscanner.backpack.calendar2.data.CalendarDay.Selection
import net.skyscanner.backpack.calendar2.data.CalendarSpace
import net.skyscanner.backpack.calendar2.view.CalendarSelectionDrawable
import net.skyscanner.backpack.util.ItemHolder

internal class CalendarCellSpace(
  parent: ViewGroup,
) : ItemHolder<CalendarSpace>(parent, R.layout.view_bpk_calendar_space) {

  private val background = CalendarSelectionDrawable(context)

  init {
    view.findViewById<View>(R.id.bpk_calendar_space).background = background
  }

  override fun bind(model: CalendarSpace) {
    background.selection = if (model.selected) Selection.Middle else Selection.None
  }
}
