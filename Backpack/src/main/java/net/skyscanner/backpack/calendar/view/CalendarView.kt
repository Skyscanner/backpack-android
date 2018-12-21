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

package net.skyscanner.backpack.calendar.view

import android.content.Context
import android.util.AttributeSet
import android.view.ViewConfiguration
import android.widget.AbsListView
import android.widget.AbsListView.OnScrollListener
import android.widget.ListView
import androidx.core.content.ContextCompat
import net.skyscanner.backpack.R
import net.skyscanner.backpack.calendar.model.CalendarDay
import net.skyscanner.backpack.calendar.presenter.BpkCalendarController
import net.skyscanner.backpack.calendar.presenter.MonthAdapter

/**
 * This displays a list of months in a calendar format with selectable days.
 */
internal class CalendarView constructor(context: Context, attr: AttributeSet?) : ListView(context, attr),
  OnScrollListener {

  override fun onScrollStateChanged(view: AbsListView?, scrollState: Int) {
    // Do nothing
  }

  private var scrollFriction = 1.0f
  private var selectedDay: CalendarDay = CalendarDay()
  private var previousScrollPosition: Long = 0
  private var previousScrollState = OnScrollListener.SCROLL_STATE_IDLE
  private var currentScrollState = OnScrollListener.SCROLL_STATE_IDLE

  var controller: BpkCalendarController? = null
    set(value) {
      field = value
      if (value != null) {
        changeAdapter(value)
        selectedDay = value.selectedDay
      }
    }
  private var adapter: MonthAdapter? = null

  init {
    this.layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
    this.setDrawSelectorOnTop(false)

    setUpListView()
  }

  private fun changeAdapter(controller: BpkCalendarController) {
    adapter = createMonthAdapter(context, controller)
    setAdapter(adapter)
  }

  private fun createMonthAdapter(context: Context, controller: BpkCalendarController) =
    MonthAdapter(context, controller)

  private fun setUpListView() {
    cacheColorHint = 0
    divider = null
    itemsCanFocus = true
    isFastScrollEnabled = false
    isVerticalScrollBarEnabled = false
    setOnScrollListener(this)
    setFadingEdgeLength(0)
    setFriction(ViewConfiguration.getScrollFriction() * scrollFriction)
    setBackgroundColor(ContextCompat.getColor(context, R.color.bpkWhite))
  }

  /**
   * Updates the title and selected month if the view has moved to a new month.
   */
  override fun onScroll(view: AbsListView, firstVisibleItem: Int, visibleItemCount: Int, totalItemCount: Int) {
    val child = view.getChildAt(0) as MonthView? ?: return

    val currScroll = (view.firstVisiblePosition * child.height - child.bottom).toLong()
    previousScrollPosition = currScroll
    previousScrollState = currentScrollState
  }
}
