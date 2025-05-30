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

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import net.skyscanner.backpack.R
import net.skyscanner.backpack.calendar2.CellLabel
import net.skyscanner.backpack.calendar2.CellStatusStyle
import net.skyscanner.backpack.calendar2.data.CalendarCell
import net.skyscanner.backpack.calendar2.data.CalendarInteraction
import net.skyscanner.backpack.calendar2.view.CalendarDayLabelContentColor
import net.skyscanner.backpack.calendar2.view.CalendarDaySelectionBackground
import net.skyscanner.backpack.calendar2.view.CalendarDaySelectionContentColor
import net.skyscanner.backpack.util.Consumer
import net.skyscanner.backpack.util.ItemHolder

internal class CalendarCellDayHolder(
    parent: ViewGroup,
    output: Consumer<CalendarInteraction>,
) : ItemHolder<CalendarCell.Day>(parent, R.layout.view_bpk_calendar_day) {

    private val day = findViewById<TextView>(R.id.bpk_calendar_cell_date)
    private val label = findViewById<TextView>(R.id.bpk_calendar_cell_label_text)
    private val icon = findViewById<ImageView>(R.id.bpk_calendar_cell_label_icon)

    private val selectionBackground = CalendarDaySelectionBackground(context)

    private val selectionContentColor = CalendarDaySelectionContentColor(context)
    private val labelColor = CalendarDayLabelContentColor(context)

    private val defaultTextColor = context.getColorStateList(R.color.bpkTextPrimary)
    private val disabledTextColor = context.getColorStateList(R.color.bpkTextDisabled)

    init {
        view.setOnClickListener {
            model?.let { CalendarInteraction.DateClicked(it) }?.let { day -> output.invoke(day) }
        }
    }

    override fun bind(model: CalendarCell.Day) {
        view.isEnabled = !model.inactive
        view.isSelected = model.selection != null

        day.text = model.text

        when (val cellLabel = model.info.label) {
            is CellLabel.Text -> label.text = cellLabel.text
            is CellLabel.Icon -> {
                icon.setImageResource(cellLabel.resId)
                cellLabel.tint?.let { tint -> icon.imageTintList = context.getColorStateList(tint) }
            }

            is CellLabel.Loading -> {}
        }
        when {
            model.selection != null -> {
                day.setTextColor(selectionContentColor(model.selection))
                day.background = selectionBackground(model.selection)
            }

            model.inactive -> {
                day.setTextColor(disabledTextColor)
                day.background = null
            }

            else -> {
                day.setTextColor(defaultTextColor)
                day.background = null
            }
        }

        when {
            model.inactive -> {
                label.isVisible = false
                icon.visibility = View.GONE
            }

            model.info.style == CellStatusStyle.Label -> {
                when (val cellLabel = model.info.label) {
                    is CellLabel.Text -> {
                        label.isVisible = cellLabel.text.isNotEmpty()
                        label.setTextColor(labelColor(model.info.status))
                        icon.visibility = View.GONE
                    }

                    is CellLabel.Icon, is CellLabel.Loading -> {
                        icon.visibility = View.VISIBLE
                        label.visibility = View.GONE
                    }
                }
            }

            else -> {
                when (val cellLabel = model.info.label) {
                    is CellLabel.Text -> label.isVisible = cellLabel.text.isNotEmpty()
                    is CellLabel.Icon -> icon.visibility = View.VISIBLE
                    is CellLabel.Loading -> {}
                }
            }
        }
    }
}
