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
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.plus
import net.skyscanner.backpack.calendar2.adapter.CalendarAdapter
import net.skyscanner.backpack.calendar2.adapter.CalendarSpanSizeLookup
import net.skyscanner.backpack.calendar2.data.CalendarStateMachine
import net.skyscanner.backpack.calendar2.extension.getItem
import net.skyscanner.backpack.calendar2.view.CalendarHeaderView
import net.skyscanner.backpack.util.ResourcesUtil
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
) : LinearLayoutCompat(context, attrs, defStyleAttr), CalendarComponent by stateMachine {

  @JvmOverloads
  constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
  ) : this(context, attrs, defStyleAttr, GlobalScope + Dispatchers.Main)

  private val headerView = CalendarHeaderView(context)
  private val recyclerView = RecyclerView(context)
  private val layoutManager = GridLayoutManager(context, 7)
  private val scrollListeners = mutableListOf<CalendarOnScrollListener>()

  var footerAdapter: CalendarFooterAdapter<RecyclerView.ViewHolder> = DefaultCalendarFooterAdapter

  init {
    orientation = VERTICAL
    recyclerView.layoutManager = layoutManager
    addView(headerView, LayoutParams(LayoutParams.MATCH_PARENT, ResourcesUtil.dpToPx(50, context)))
    addView(recyclerView, LayoutParams(LayoutParams.MATCH_PARENT, 0, 1f))

    layoutManager.spanSizeLookup = CalendarSpanSizeLookup(scope, stateMachine.state)

    recyclerView.adapter = CalendarAdapter(scope, stateMachine.state, footerAdapter) {
      stateMachine.onClick(it.date.dayOfMonth, it.date.monthValue, it.date.year)
    }

    recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
      override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        val firstItemPosition = layoutManager.findFirstVisibleItemPosition()
        val item = stateMachine.state.value.months.getItem(firstItemPosition)
        scrollListeners.forEach {
          it.invoke(item.yearMonth)
        }
      }
    })

    stateMachine.state.onEach {
      headerView.invoke(it.params)
    }.launchIn(scope)
  }

  fun addOnScrollListener(listener: CalendarOnScrollListener) {
    scrollListeners += listener
  }

  fun removeOnScrollListener(listener: CalendarOnScrollListener) {
    scrollListeners -= listener
  }
}
