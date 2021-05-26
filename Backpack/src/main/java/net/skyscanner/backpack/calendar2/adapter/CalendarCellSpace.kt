package net.skyscanner.backpack.calendar2.adapter

import android.view.ViewGroup
import android.widget.Space
import net.skyscanner.backpack.calendar2.data.CalendarSpace
import net.skyscanner.backpack.util.ItemHolder

internal class CalendarCellSpace(
  parent: ViewGroup,
) : ItemHolder<CalendarSpace>(Space(parent.context)) {

  override fun bind(model: CalendarSpace) = Unit
}
