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

package net.skyscanner.backpack.calendar

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.AbsListView
import androidx.constraintlayout.widget.ConstraintLayout
import net.skyscanner.backpack.R
import net.skyscanner.backpack.badge.BpkBadge
import net.skyscanner.backpack.calendar.presenter.BpkCalendarController
import net.skyscanner.backpack.calendar.view.BpkCalendarScrollListener
import net.skyscanner.backpack.calendar.view.CalendarView
import net.skyscanner.backpack.calendar.view.WeekdayHeaderView
import net.skyscanner.backpack.util.unsafeLazy

@Deprecated("Use Calendar2 instead")
open class BpkCalendar @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyle: Int = 0,
) : ConstraintLayout(context, attrs, defStyle), BpkCalendarScrollListener {

  init {
    inflate(this.context, R.layout.view_bpk_calendar, this)
  }

  private val calendarView by unsafeLazy { findViewById<CalendarView>(R.id.calendar_view) }
  private val weekdayHeaderView by unsafeLazy { findViewById<WeekdayHeaderView>(R.id.weekday_header_view) }
  private val yearPillView by unsafeLazy { findViewById<BpkBadge>(R.id.year_pill_view) }

  val controller: BpkCalendarController?
    get() = calendarView.controller

  fun setController(controller: BpkCalendarController) {
    weekdayHeaderView.initializeWithLocale(controller.locale)
    calendarView.controller = controller
    controller.updateContentCallback = calendarView
    calendarView.addBpkCalendarScrollListener(this)

    updateYearPill(controller.startDate.year)
  }

  fun addOnScrollListener(listener: BpkCalendarScrollListener) {
    calendarView.addBpkCalendarScrollListener(listener)
  }

  fun removeOnScrollListener(listener: BpkCalendarScrollListener) {
    calendarView.removeBpkCalendarScrollListener(listener)
  }

  fun setSelectionFromTop(position: Int, y: Int = 0) {
    calendarView.setSelectionFromTop(position, y)
  }

  override fun onScroll(
    view: AbsListView,
    firstVisibleItem: Int,
    visibleItemCount: Int,
    totalItemCount: Int,
    year: Int,
  ) {
    updateYearPill(year)
  }

  private fun updateYearPill(year: Int) {
    yearPillView.message = year.toString()
    val controller = calendarView.controller
    yearPillView.visibility = when {
      controller == null -> View.GONE
      controller.currentDateProvider.invoke().year != year -> View.VISIBLE
      else -> View.GONE
    }
  }
}
