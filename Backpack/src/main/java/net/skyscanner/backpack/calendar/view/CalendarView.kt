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
import android.view.View
import android.view.ViewConfiguration
import android.widget.AbsListView
import android.widget.ListView
import androidx.core.content.ContextCompat
import net.skyscanner.backpack.R
import net.skyscanner.backpack.calendar.model.CalendarDay
import net.skyscanner.backpack.calendar.presenter.BpkCalendarController
import net.skyscanner.backpack.calendar.presenter.MonthAdapter

interface OnYearChangedListener {
  fun onYearChanged(year: Int)
}

/**
 * This displays a list of months in a calendar format with selectable days.
 */
internal class CalendarView constructor(
  context: Context,
  attr: AttributeSet?
) : ListView(context, attr), AbsListView.OnScrollListener {

  var controller: BpkCalendarController? = null
    set(value) {
      field = value
      if (value != null) {
        changeAdapter(value)
        selectedDay = value.selectedDay
      }
    }

  var listener: OnYearChangedListener? = null

  private var scrollFriction = 1.0f
  private var selectedDay: CalendarDay? = null
  private var previousScrollPosition: Long = 0
  private var previousScrollState = OnScrollListener.SCROLL_STATE_IDLE
  private var currentScrollState = OnScrollListener.SCROLL_STATE_IDLE
  private var adapter: MonthAdapter? = null

  init {
    this.layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
    this.setDrawSelectorOnTop(false)

    setUpListView()
  }

  private val measureAndLayout = Runnable {
    measure(
      View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY),
      View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.UNSPECIFIED))
    layout(left, top, right, bottom)
  }

  override fun requestLayout() {
    super.requestLayout()

    // This is required for the bridged component to render correctly
    // based on: https://github.com/facebook/react-native/blob/1151c096dab17e5d9a6ac05b61aacecd4305f3db/ReactAndroid/src/main/java/com/facebook/react/views/picker/ReactPicker.java#L75
    this.post(measureAndLayout)
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

  // TODO: Updates the title and selected month if the view has moved to a new month.
  override fun onScroll(view: AbsListView, firstVisibleItem: Int, visibleItemCount: Int, totalItemCount: Int) {
    val child = view.getChildAt(0) as MonthView? ?: return

    val currScroll = (view.firstVisiblePosition * child.height - child.bottom).toLong()
    previousScrollPosition = currScroll
    previousScrollState = currentScrollState

    listener?.onYearChanged(child.getYear())
  }

  override fun onScrollStateChanged(view: AbsListView?, scrollState: Int) {
  }
}
