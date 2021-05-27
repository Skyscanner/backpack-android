package net.skyscanner.backpack.calendar2.adapter

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import net.skyscanner.backpack.R
import net.skyscanner.backpack.calendar2.CalendarParams
import net.skyscanner.backpack.calendar2.data.CalendarDay
import net.skyscanner.backpack.calendar2.view.CalendarSelectionDrawable
import net.skyscanner.backpack.util.Consumer
import net.skyscanner.backpack.util.ItemHolder
import net.skyscanner.backpack.util.stateListDrawable

internal class CalendarCellDay(
  parent: ViewGroup,
  private val output: Consumer<CalendarDay>,
) : ItemHolder<CalendarDay>(parent, R.layout.view_bpk_calendar_day) {

  private val day = findViewById<TextView>(R.id.bpk_calendar_cell_date)
  private val label = findViewById<TextView>(R.id.bpk_calendar_cell_label)
  private val selectionDrawable = CalendarSelectionDrawable(context)

  init {
    view.setOnClickListener {
      model?.let(output)
    }
    day.background = stateListDrawable(
      drawable = ColorDrawable(Color.TRANSPARENT),
      selected = selectionDrawable,
    )
  }

  override fun bind(model: CalendarDay) {
    day.text = model.date.dayOfMonth.toString()
    view.isSelected = model.selection != CalendarDay.Selection.None
    selectionDrawable.selection = model.selection

    view.isEnabled = model.info.status != CalendarParams.Status.Disabled
    label.text = model.info.label
    label.isVisible = !model.info.label.isNullOrEmpty()
  }
}
