/**
 * Backpack for Android - Skyscanner's Design System
 *
 * Copyright 2018 Skyscanner Ltd
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
import net.skyscanner.backpack.button.BpkButton
import net.skyscanner.backpack.calendar2.CalendarParams
import net.skyscanner.backpack.calendar2.data.CalendarCell
import net.skyscanner.backpack.calendar2.data.CalendarInteraction
import net.skyscanner.backpack.util.Consumer
import net.skyscanner.backpack.util.ItemHolder

internal class CalendarCellHeaderHolder(
  parent: ViewGroup,
  private val output: Consumer<CalendarInteraction>
) : ItemHolder<CalendarCell.Header>(parent, R.layout.view_bpk_calendar_header) {

  private val month = findViewById<TextView>(R.id.bpk_calendar_cell_month)
  private val btnSelectWholeMonth = findViewById<BpkButton>(R.id.bpk_calendar_cell_whole_month_selection)

  init {
    btnSelectWholeMonth.setOnClickListener {
      model?.let {
        CalendarInteraction.SelectMonthClicked(it)
      }?.let { header ->
        output.invoke(header)
      }
    }
  }
  override fun bind(model: CalendarCell.Header) {
    month.text = model.title
    btnSelectWholeMonth.apply {
      when {
        model.calendarSelectionMode == CalendarParams.SelectionMode.Disabled ||
          model.monthSelectionMode == CalendarParams.MonthSelectionMode.Disabled -> {
          isVisible = false
        }
        model.monthSelectionMode is CalendarParams.MonthSelectionMode.SelectWholeMonth -> {
          isVisible = true
          text = model.monthSelectionMode.label
        }
      }
    }
  }
}
