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

@file:Suppress("DEPRECATION")

package net.skyscanner.backpack.calendar.presenter

import android.content.Context
import android.database.DataSetObserver
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import net.skyscanner.backpack.calendar.model.CalendarDrawingParams
import net.skyscanner.backpack.calendar.view.MonthView
import org.threeten.bp.LocalDate
import org.threeten.bp.Period

internal class MonthAdapter(
  private val context: Context,
  private val controller: BpkCalendarController,
) : BaseAdapter(), MonthView.OnDayClickListener {

  private var selectedDay: LocalDate? = null
    set(value) {
      field = value
      notifyDataSetChanged()
    }

  private var positionMetadata = computeMetadata()

  init {
    registerDataSetObserver(object : DataSetObserver() {
      override fun onChanged() {
        positionMetadata = computeMetadata()
      }
    })
  }

  override fun onDayClick(view: MonthView?, day: LocalDate) {
    onDayTapped(day)
  }

  override fun getCount() = positionMetadata.size

  override fun getItem(position: Int) = null

  override fun getItemId(position: Int) = position.toLong()

  override fun hasStableIds() = true

  override fun getViewTypeCount() = 2

  override fun getItemViewType(position: Int) = positionMetadata[position].viewType

  @Suppress("UNCHECKED_CAST")
  override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
    val metadata = positionMetadata[position]
    val month = metadata.month
    val year = metadata.year

    return if (getItemViewType(position) == VIEW_TYPE_FOOTER) {
      getFooterView(convertView, month, year)
    } else {
      getMothView(convertView, month, year)
    }
  }

  private fun createMonthView(context: Context) =
    MonthView(context).also {
      it.controller = controller
      it.isClickable = true
      it.onDayClickListener = this@MonthAdapter
    }

  private fun isSelectedDayInMonth(calendarDay: LocalDate, year: Int, month: Int) =
    calendarDay.year == year && calendarDay.month.value == month

  private fun onDayTapped(day: LocalDate) {
    controller.onDayOfMonthSelected(day)
    selectedDay = day
  }

  private fun getFooterView(convertView: View?, month: Int, year: Int): View {
    val monthFooterAdapter = controller.monthFooterAdapter!!
    return if (convertView != null) {
      monthFooterAdapter.onBindView(convertView, month, year)
      convertView
    } else {
      val newFooter = monthFooterAdapter.onCreateView(month, year)
      monthFooterAdapter.onBindView(newFooter, month, year)
      newFooter
    }
  }

  private fun getMothView(convertView: View?, month: Int, year: Int): View {
    val selectedDay = selectedDay?.let {
      if (isSelectedDayInMonth(it, year, month)) it.dayOfMonth else null
    }

    val view = if (convertView != null) {
      convertView as MonthView
    } else {
      createMonthView(context)
    }

    view.reuse()
    view.setMonthParams(
      CalendarDrawingParams(
        year,
        month,
        selectedDay,
        controller.calendarColoring,
        controller::isDateDisabled,
        controller.calendarLabels,
      )
    )
    view.invalidate()

    return view
  }

  private fun computeMetadata(): List<PositionMetadata> {
    // We add one because we always want to include months even if there ins't a full month
    // difference between them. E.g. `01-01-2020 - 03-03-2020` would return only 2 months,
    // be we want to show all three here.
    val totalMonths = Period.between(controller.startDate, controller.endDate).toTotalMonths().toInt() + 1

    return (0 until totalMonths).fold(mutableListOf()) { acc, position ->
      val positionWithStart = position + controller.startDate.month.value - 1
      val month = positionWithStart % MONTHS_IN_YEAR + 1
      val year = positionWithStart / MONTHS_IN_YEAR + controller.startDate.year

      acc.add(PositionMetadata(month, year, VIEW_TYPE_MONTH))

      if (controller.monthFooterAdapter?.hasFooterForMonth(month, year) == true) {
        acc.add(PositionMetadata(month, year, VIEW_TYPE_FOOTER))
      }

      acc
    }
  }

  internal companion object {
    const val MONTHS_IN_YEAR = 12
    const val VIEW_TYPE_MONTH = 0
    const val VIEW_TYPE_FOOTER = 1

    data class PositionMetadata(val month: Int, val year: Int, val viewType: Int)
  }
}
