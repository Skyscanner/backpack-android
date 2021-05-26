package net.skyscanner.backpack.calendar2.adapter

import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import net.skyscanner.backpack.R
import net.skyscanner.backpack.calendar2.CalendarCellStyle
import net.skyscanner.backpack.calendar2.data.CalendarDay
import net.skyscanner.backpack.util.Consumer
import net.skyscanner.backpack.util.ItemHolder

internal class CalendarCellDay(
  parent: ViewGroup,
  private val output: Consumer<CalendarDay>,
) : ItemHolder<CalendarDay>(parent, R.layout.view_bpk_calendar_day) {

  private val day = findViewById<TextView>(R.id.bpk_calendar_cell_date)
  private val label = findViewById<TextView>(R.id.bpk_calendar_cell_label)

  init {
    view.setOnClickListener {
      model?.let(output)
    }
  }

  override fun bind(model: CalendarDay) {
    view.isEnabled = !model.disabled
    day.text = model.date.dayOfMonth.toString()
    label.text = model.label
    label.isVisible = !model.label.isNullOrEmpty()

    view.background = when (model.selection) {
      CalendarDay.Selection.Double -> null
      CalendarDay.Selection.End -> null
      CalendarDay.Selection.Middle -> null
      CalendarDay.Selection.Single -> null
      CalendarDay.Selection.Start -> null
      CalendarDay.Selection.None -> when (model.style) {
        is CalendarCellStyle.Custom -> null
        is CalendarCellStyle.Negative -> null
        is CalendarCellStyle.Neutral -> null
        is CalendarCellStyle.Positive -> null
        null -> null
      }
    }
  }
}
