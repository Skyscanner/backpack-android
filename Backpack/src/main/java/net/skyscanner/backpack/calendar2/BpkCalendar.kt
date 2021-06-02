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

package net.skyscanner.backpack.calendar2

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.widget.FrameLayout
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.plus
import net.skyscanner.backpack.R
import net.skyscanner.backpack.badge.BpkBadge
import net.skyscanner.backpack.calendar2.data.CalendarStateMachine
import net.skyscanner.backpack.calendar2.list.CalendarAdapter
import net.skyscanner.backpack.calendar2.list.CalendarLayoutManager
import net.skyscanner.backpack.calendar2.list.CalendarSpanSizeLookup
import net.skyscanner.backpack.calendar2.view.CalendarHeaderView
import net.skyscanner.backpack.util.ResourcesUtil
import net.skyscanner.backpack.util.addView
import org.threeten.bp.LocalDate
import org.threeten.bp.Period

class BpkCalendar private constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0,
  private val scope: CoroutineScope,
  private val stateMachine: CalendarStateMachine = CalendarStateMachine(
    scope = scope,
    initialParams = CalendarParams(
      range = LocalDate.now() - Period.ofMonths(1)..LocalDate.now() + Period.ofYears(1),
      selectionMode = CalendarParams.SelectionMode.Range
    )
  ),
) : FrameLayout(context, attrs, defStyleAttr), CalendarComponent by stateMachine {

  @JvmOverloads
  constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
  ) : this(context, attrs, defStyleAttr, GlobalScope + Dispatchers.Main)

  private val scrollListeners = mutableListOf<CalendarOnScrollListener>()
  private val calendarAdapter = CalendarAdapter(scope, stateMachine::onClick)

  var footerAdapter: CalendarFooterAdapter<*>
    get() = calendarAdapter.footerAdapter
    set(value) {
      calendarAdapter.footerAdapter = value
    }

  init {
    val headerHeight = ResourcesUtil.dpToPx(50, context)
    initHeaderView(headerHeight)
    initRecyclerView(headerHeight)
    initYearView(headerHeight)
  }

  fun addOnScrollListener(listener: CalendarOnScrollListener) {
    scrollListeners += listener
  }

  fun removeOnScrollListener(listener: CalendarOnScrollListener) {
    scrollListeners -= listener
  }

  private fun initHeaderView(headerHeight: Int) {
    val headerView = CalendarHeaderView(context)

    addView(headerView) {
      width = LayoutParams.MATCH_PARENT
      height = headerHeight
    }

    state.onEach {
      headerView(it.params)
    }.launchIn(scope)
  }

  private fun initRecyclerView(headerHeight: Int) {
    val calendarSpanSizeLookup = CalendarSpanSizeLookup()
    val calendarLayoutManager = CalendarLayoutManager(context, calendarSpanSizeLookup)

    val recyclerView = RecyclerView(context).apply {
      layoutManager = calendarLayoutManager
      adapter = calendarAdapter
      itemAnimator = null

      addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {

          val firstItemPosition = calendarLayoutManager.findFirstVisibleItemPosition()
          val item = state.value.cells[firstItemPosition]

          scrollListeners.forEach {
            it.invoke(item.yearMonth)
          }
        }
      })
    }

    addView(recyclerView) {
      width = LayoutParams.MATCH_PARENT
      height = LayoutParams.MATCH_PARENT
      topMargin = headerHeight
    }

    state.onEach {
      calendarSpanSizeLookup(it.cells)
      calendarAdapter(it.cells)
    }.launchIn(scope)
  }

  private fun initYearView(headerHeight: Int) {
    val badge = BpkBadge(context).apply {
      type = BpkBadge.Type.Dark
      isVisible = false
    }

    addOnScrollListener {
      badge.text = it.year.toString()
      badge.isVisible = it.year != state.value.params.now.year
    }

    addView(badge) {
      width = LayoutParams.WRAP_CONTENT
      height = LayoutParams.WRAP_CONTENT
      topMargin = headerHeight + resources.getDimensionPixelSize(R.dimen.bpkSpacingBase)
      gravity = Gravity.TOP or Gravity.CENTER_HORIZONTAL
    }
  }
}
