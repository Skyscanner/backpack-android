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

package net.skyscanner.backpack.calendar2

import android.content.Context
import android.content.res.Configuration
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerViewAccessibilityDelegate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import net.skyscanner.backpack.R
import net.skyscanner.backpack.calendar2.data.CalendarStateMachine
import net.skyscanner.backpack.calendar2.list.CalendarAdapter
import net.skyscanner.backpack.calendar2.list.CalendarLayoutManager
import net.skyscanner.backpack.calendar2.list.CalendarSpanSizeLookup
import net.skyscanner.backpack.calendar2.view.CalendarHeaderView
import net.skyscanner.backpack.util.unsafeLazy
import org.threeten.bp.LocalDate
import org.threeten.bp.Period
import org.threeten.bp.YearMonth

class BpkCalendar private constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0,
  private val scope: CoroutineScope,
  private val stateMachine: CalendarStateMachine = CalendarStateMachine(
    scope = scope,
    initialParams = CalendarParams(
      range = LocalDate.now() - Period.ofYears(1)..LocalDate.now() + Period.ofYears(1),
      selectionMode = CalendarParams.SelectionMode.Range,
    )
  ),
) : ConstraintLayout(context, attrs, defStyleAttr), CalendarComponent by stateMachine {

  @JvmOverloads
  constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
  ) : this(context, attrs, defStyleAttr, CoroutineScope(Dispatchers.Unconfined))

  init {
    inflate(context, R.layout.view_bpk_calendar_2, this)
  }

  private val headerView by unsafeLazy { findViewById<CalendarHeaderView>(R.id.bpk_calendar_header) }
  private val recyclerView by unsafeLazy { findViewById<RecyclerView>(R.id.bpk_calendar_recycler_view) }
  private val badge by unsafeLazy { findViewById<TextView>(R.id.bpk_calendar_badge) }

  private val scrollListeners = mutableListOf<(YearMonth) -> Unit>()
  private val calendarAdapter = CalendarAdapter(stateMachine::onClick)
  private val spanSizeLookup = CalendarSpanSizeLookup(calendarAdapter)
  private val calendarLayoutManager = CalendarLayoutManager(context, spanSizeLookup)

  init {
    recyclerView.layoutManager = calendarLayoutManager
    recyclerView.adapter = calendarAdapter
    recyclerView.itemAnimator = null
    recyclerView.setAccessibilityDelegateCompat(NoCellPositionAccessibilityInfo(recyclerView))
    recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
      override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {

        val firstItemPosition = calendarLayoutManager.findFirstVisibleItemPosition()
        val item = state.value.cells[firstItemPosition]

        scrollListeners.forEach {
          it.invoke(item.yearMonth)
        }
      }
    })

    state.onEach {
      headerView(it.params)
      calendarAdapter(it.cells)
    }.launchIn(scope)

    scrollListeners += {
      badge.text = it.year.toString()
      badge.isVisible = it.year != state.value.params.now.year
    }
  }

  override fun onConfigurationChanged(newConfig: Configuration) {
    super.onConfigurationChanged(newConfig)
    stateMachine.onLocaleChanged(newConfig.locales.get(0))
  }

  /**
   * Scrolls to a specific date in a calendar.
   * Does nothing if the date is out of range.
   */
  fun scrollToDate(date: LocalDate) {
    val index = state.value.cells.indexOf(date)
    if (index >= 0) {
      recyclerView.scrollToPosition(index)
    }
  }

  /**
   * Scrolls with animation to a specific date in a calendar.
   * Does nothing if the date is out of range.
   */
  fun smoothScrollToDate(date: LocalDate) {
    val index = state.value.cells.indexOf(date)
    if (index >= 0) {
      recyclerView.smoothScrollToPosition(index)
    }
  }

  private class NoCellPositionAccessibilityInfo(
    recyclerView: RecyclerView
  ) : RecyclerViewAccessibilityDelegate(recyclerView) {

    override fun onInitializeAccessibilityNodeInfo(host: View, info: AccessibilityNodeInfoCompat) {
      super.onInitializeAccessibilityNodeInfo(host, info)
      info.setCollectionInfo(null)
    }
  }
}
