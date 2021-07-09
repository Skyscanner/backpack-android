/**
 * Backpack for Android - Skyscanner's Design System
 *
 * Copyright 2018-2021 Skyscanner Ltd
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.skyscanner.backpack.calendar2.list

import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import net.skyscanner.backpack.R
import net.skyscanner.backpack.calendar2.CellStatusStyle
import net.skyscanner.backpack.calendar2.data.CalendarCell
import net.skyscanner.backpack.calendar2.view.CalendarDayLabelContentColor
import net.skyscanner.backpack.calendar2.view.CalendarDaySelectionBackground
import net.skyscanner.backpack.calendar2.view.CalendarDaySelectionContentColor
import net.skyscanner.backpack.calendar2.view.CalendarDayStatusBackground
import net.skyscanner.backpack.calendar2.view.CalendarDayStatusContentColor
import net.skyscanner.backpack.util.Consumer
import net.skyscanner.backpack.util.ItemHolder

internal class CalendarCellDayHolder(
  parent: ViewGroup,
  private val output: Consumer<CalendarCell.Day>,
) : ItemHolder<CalendarCell.Day>(parent, R.layout.view_bpk_calendar_day) {

  private val day = findViewById<TextView>(R.id.bpk_calendar_cell_date)
  private val label = findViewById<TextView>(R.id.bpk_calendar_cell_label)

  private val selectionBackground = CalendarDaySelectionBackground(context)
  private val statusBackground = CalendarDayStatusBackground(context)

  private val selectionContentColor = CalendarDaySelectionContentColor(context)
  private val statusContentColor = CalendarDayStatusContentColor(context)
  private val labelColor = CalendarDayLabelContentColor(context)

  private val disabledTextColor = context.getColorStateList(R.color.__calendarCellDisabledTextColor)

  init {
    view.setOnClickListener {
      model?.let(output)
    }
  }

  override fun bind(model: CalendarCell.Day) {
    view.isEnabled = !model.info.disabled && !model.outOfRange
    view.isSelected = model.selection != null
    view.contentDescription = model.contentDescription + " " + (model.info.label ?: "")

    day.text = model.date.dayOfMonth.toString()
    label.text = model.info.label

    when {
      model.selection != null -> {
        day.setTextColor(selectionContentColor(model.selection))
        day.background = selectionBackground(model.selection)
      }
      model.info.disabled || model.outOfRange -> {
        day.setTextColor(disabledTextColor)
        day.background = null
      }
      model.info.style == CellStatusStyle.Background -> {
        day.setTextColor(statusContentColor(model.info.status))
        day.background = statusBackground(model.info.status)
      }
      else -> {
        day.setTextColor(statusContentColor(null))
        day.background = null
      }
    }

    when {
      model.info.disabled || model.outOfRange -> {
        label.isVisible = false
      }
      model.info.style == CellStatusStyle.Label -> {
        label.isVisible = !model.info.label.isNullOrEmpty()
        label.setTextColor(labelColor(model.info.status))
      }
      else -> {
        label.isVisible = !model.info.label.isNullOrEmpty()
        label.setTextColor(labelColor(null))
      }
    }
  }
}
