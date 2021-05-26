package net.skyscanner.backpack.calendar2.view

import android.view.ViewGroup
import android.widget.TextView
import net.skyscanner.backpack.R
import net.skyscanner.backpack.calendar2.data.CalendarHeader
import net.skyscanner.backpack.util.ItemHolder

internal class CalendarCellHeader(
  parent: ViewGroup,
) : ItemHolder<CalendarHeader>(parent, R.layout.view_bpk_calendar_header) {

  override fun bind(model: CalendarHeader) {
    (itemView as TextView).text = model.title
  }
}
