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

package net.skyscanner.backpack.calendar.view

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.ViewConfiguration
import android.widget.AbsListView
import android.widget.ListView
import net.skyscanner.backpack.calendar.presenter.BpkCalendarController
import net.skyscanner.backpack.calendar.presenter.MonthAdapter

@Deprecated("Use Calendar2 instead")
interface CalendarUpdateCallback {
  fun updateContent()
}

@Deprecated("Use Calendar2 instead")
interface BpkCalendarScrollListener {
  fun onScroll(
    view: AbsListView,
    firstVisibleItem: Int,
    visibleItemCount: Int,
    totalItemCount: Int,
    year: Int,
  )
}

/**
 * This displays a list of months in a calendar format with selectable days.
 */
internal class CalendarView constructor(
  context: Context,
  attr: AttributeSet?,
) : ListView(context, attr), AbsListView.OnScrollListener, CalendarUpdateCallback {

  var controller: BpkCalendarController? = null
    set(value) {
      field = value
      if (value != null) {
        changeAdapter(value)
      }
    }

  private var calendarScrollListeners = mutableSetOf<BpkCalendarScrollListener>()

  private var scrollFriction = 1.0f
  private var previousScrollPosition: Long = 0
  private var previousScrollState = OnScrollListener.SCROLL_STATE_IDLE
  private var currentScrollState = OnScrollListener.SCROLL_STATE_IDLE
  private var adapter: MonthAdapter? = null
  private var lastSeenYear: Int? = null

  init {
    this.layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
    this.isDrawSelectorOnTop = false

    setUpListView()
  }

  override fun updateContent() {
    adapter?.notifyDataSetChanged()
  }

  fun addBpkCalendarScrollListener(listener: BpkCalendarScrollListener) {
    calendarScrollListeners.add(listener)
  }

  fun removeBpkCalendarScrollListener(listener: BpkCalendarScrollListener) {
    calendarScrollListeners.remove(listener)
  }

  private fun changeAdapter(controller: BpkCalendarController) {
    adapter = MonthAdapter(context, controller)
    setAdapter(adapter)
  }

  private fun setUpListView() {
    cacheColorHint = 0
    divider = null
    itemsCanFocus = true
    isFastScrollEnabled = false
    isVerticalScrollBarEnabled = false
    setOnScrollListener(this)
    setFadingEdgeLength(0)
    setFriction(ViewConfiguration.getScrollFriction() * scrollFriction)
    setBackgroundColor(Color.TRANSPARENT)
  }

  // TODO: Updates the title and selected month if the view has moved to a new month.
  override fun onScroll(
    view: AbsListView,
    firstVisibleItem: Int,
    visibleItemCount: Int,
    totalItemCount: Int,
  ) {
    val child = view.getChildAt(0) ?: return

    if (child is MonthView) {
      lastSeenYear = child.getYear()
    }

    val year = lastSeenYear ?: controller?.startDate?.year
    val currScroll = (view.firstVisiblePosition * child.height - child.bottom).toLong()
    previousScrollPosition = currScroll
    previousScrollState = currentScrollState

    if (year != null) {
      calendarScrollListeners.forEach {
        it.onScroll(this, firstVisibleItem, visibleItemCount, totalItemCount, year)
      }
    }
  }

  override fun onScrollStateChanged(view: AbsListView?, scrollState: Int) {
  }
}
