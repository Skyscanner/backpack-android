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
import net.skyscanner.backpack.calendar.model.CalendarDay
import net.skyscanner.backpack.calendar.view.MonthView
import java.util.Calendar
import kotlin.collections.HashMap
import kotlin.collections.set

internal class MonthAdapter(
  private val context: Context,
  private val controller: BpkCalendarController
) : BaseAdapter(), MonthView.OnDayClickListener {

    private var selectedDay: CalendarDay = CalendarDay()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onDayClick(view: MonthView?, day: CalendarDay) {
        onDayTapped(day)
    }

    override fun getCount() =
        (controller.endDate.get(Calendar.YEAR) - controller.startDate.get(Calendar.YEAR)) *
                MONTHS_IN_YEAR + controller.endDate.get(Calendar.MONTH) - controller.startDate.get(Calendar.MONTH) + 1

    override fun getItem(position: Int) = null

    override fun getItemId(position: Int) = position.toLong()

    override fun hasStableIds() = true

    @Suppress("UNCHECKED_CAST")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: MonthView
        var drawingParams: HashMap<String, Int>? = null

        if (convertView != null) {
            view = convertView as MonthView
            drawingParams = view.tag as HashMap<String, Int>
        } else {
            view = createMonthView(context).apply {
                layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
                isClickable = true
            }
            view.onDayClickListener = this@MonthAdapter
        }

        if (drawingParams == null) {
            drawingParams = HashMap()
        }

        drawingParams.clear()

        val positionWithStart = position + controller.startDate.get(Calendar.MONTH)
        val month = positionWithStart % MONTHS_IN_YEAR
        val year = positionWithStart / MONTHS_IN_YEAR + controller.startDate.get(Calendar.YEAR)
        val day = if (isSelectedDayInMonth(selectedDay, year, month)) selectedDay.day else -1

        view.reuse()

        drawingParams[MonthView.VIEW_PARAMS_SELECTED_DAY] = day
        drawingParams[MonthView.VIEW_PARAMS_YEAR] = year
        drawingParams[MonthView.VIEW_PARAMS_MONTH] = month

        view.setMonthParams(drawingParams)
        view.invalidate()

        return view
    }

    private fun createMonthView(context: Context) = MonthView(context).also {
        it.controller = controller
    }

    private fun isSelectedDayInMonth(calendarDay: CalendarDay, year: Int, month: Int) =
        calendarDay.year == year && calendarDay.month == month

    private fun onDayTapped(day: CalendarDay) {
        controller.onDayOfMonthSelected(day)
        selectedDay = day
    }

    internal companion object {
        const val MONTHS_IN_YEAR = 12
    }
}
