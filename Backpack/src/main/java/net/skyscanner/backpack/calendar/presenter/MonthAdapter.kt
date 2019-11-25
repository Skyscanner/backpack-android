/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.skyscanner.backpack.calendar.presenter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView.LayoutParams
import android.widget.BaseAdapter
import net.skyscanner.backpack.calendar.model.CalendarDrawingParams
import net.skyscanner.backpack.calendar.view.MonthView
import org.threeten.bp.LocalDate

internal class MonthAdapter(
  private val context: Context,
  private val controller: BpkCalendarController
) : BaseAdapter(), MonthView.OnDayClickListener {

    private var selectedDay: LocalDate? = null
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onDayClick(view: MonthView?, day: LocalDate) {
        onDayTapped(day)
    }

  override fun getCount() =
    (controller.endDate.year - controller.startDate.year) * MONTHS_IN_YEAR +
      controller.endDate.month.value - controller.startDate.month.value + 1

    override fun getItem(position: Int) = null

    override fun getItemId(position: Int) = position.toLong()

    override fun hasStableIds() = true

    @Suppress("UNCHECKED_CAST")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: MonthView

        if (convertView != null) {
            view = convertView as MonthView
        } else {
            view = createMonthView(context).apply {
                layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
                isClickable = true
            }
            view.onDayClickListener = this@MonthAdapter
        }

        val positionWithStart = position + controller.startDate.month.value - 1
        val month = positionWithStart % MONTHS_IN_YEAR + 1
        val year = positionWithStart / MONTHS_IN_YEAR + controller.startDate.year
        val selectedDay = selectedDay?.let {
          if (isSelectedDayInMonth(it, year, month)) it.dayOfMonth else null
        }

        view.reuse()

        view.setMonthParams(CalendarDrawingParams(year, month, selectedDay, controller.calendarColoring, controller::isDateDisabled))
        view.invalidate()

        return view
    }

    private fun createMonthView(context: Context) = MonthView(context).also {
        it.controller = controller
    }

    private fun isSelectedDayInMonth(calendarDay: LocalDate, year: Int, month: Int) =
        calendarDay.year == year && calendarDay.month.value == month

    private fun onDayTapped(day: LocalDate) {
        controller.onDayOfMonthSelected(day)
        selectedDay = day
    }

    internal companion object {
        const val MONTHS_IN_YEAR = 12
    }
}
