package net.skyscanner.backpack.calendar2.adapter

import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import net.skyscanner.backpack.R
import net.skyscanner.backpack.calendar2.CalendarParams
import net.skyscanner.backpack.calendar2.data.CalendarDay
import net.skyscanner.backpack.calendar2.view.CalendarDayLabelContentColor
import net.skyscanner.backpack.calendar2.view.CalendarDaySelectionBackground
import net.skyscanner.backpack.calendar2.view.CalendarDaySelectionContentColor
import net.skyscanner.backpack.calendar2.view.CalendarDayStatusBackground
import net.skyscanner.backpack.calendar2.view.CalendarDayStatusContentColor
import net.skyscanner.backpack.util.Consumer
import net.skyscanner.backpack.util.ItemHolder

internal class CalendarCellDay(
  parent: ViewGroup,
  private val output: Consumer<CalendarDay>,
) : ItemHolder<CalendarDay>(parent, R.layout.view_bpk_calendar_day) {

  private val day = findViewById<TextView>(R.id.bpk_calendar_cell_date)
  private val label = findViewById<TextView>(R.id.bpk_calendar_cell_label)

  private val selectionBackground = CalendarDaySelectionBackground(context)
  private val statusBackground = CalendarDayStatusBackground(context)

  private val selectionContentColor = CalendarDaySelectionContentColor(context)
  private val statusContentColor = CalendarDayStatusContentColor(context)
  private val labelColor = CalendarDayLabelContentColor(context)

  init {
    view.setOnClickListener {
      model?.let(output)
    }
  }

  override fun bind(model: CalendarDay) {
    view.isEnabled = model.info.status != CalendarParams.Status.Disabled
    view.contentDescription = model.date.toString() // TODO:

    day.text = model.date.dayOfMonth.toString()

    if (model.selection != null) {
      view.isSelected = true
      day.setTextColor(selectionContentColor(model.selection))
      day.background = selectionBackground(model.selection)
    } else {
      view.isSelected = false
      day.setTextColor(statusContentColor(model.info.status))
      day.background = statusBackground(model.info.status)
    }

    label.text = model.info.label
    label.isVisible = !model.info.label.isNullOrEmpty()
    label.setTextColor(labelColor(model.info.status))
  }
}
