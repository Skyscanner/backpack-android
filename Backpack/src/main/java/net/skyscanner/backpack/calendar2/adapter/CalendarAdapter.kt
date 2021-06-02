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

package net.skyscanner.backpack.calendar2.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import net.skyscanner.backpack.calendar2.CalendarFooterAdapter
import net.skyscanner.backpack.calendar2.DefaultCalendarFooterAdapter
import net.skyscanner.backpack.calendar2.data.CalendarCell
import net.skyscanner.backpack.calendar2.data.CalendarCells
import net.skyscanner.backpack.calendar2.data.CalendarDay
import net.skyscanner.backpack.calendar2.data.CalendarFooter
import net.skyscanner.backpack.calendar2.data.CalendarHeader
import net.skyscanner.backpack.calendar2.data.CalendarSpace
import net.skyscanner.backpack.util.Consumer
import net.skyscanner.backpack.util.ItemHolder

internal class CalendarAdapter(
  private val scope: CoroutineScope,
  private val output: Consumer<CalendarDay>,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), Consumer<CalendarCells> {

  private var data: CalendarCells = CalendarCells()

  var footerAdapter: CalendarFooterAdapter<*> = DefaultCalendarFooterAdapter
    set(value) {
      field = value
      notifyDataSetChanged()
    }

  init {
    setHasStableIds(true)
  }

  override fun invoke(data: CalendarCells) {
    val calculator = CalendarDiffCalculator(this.data, data)
    scope.launch(Dispatchers.Default) {
      val diff = DiffUtil.calculateDiff(calculator, false)
      withContext(Dispatchers.Main) {
        this@CalendarAdapter.data = data
        diff.dispatchUpdatesTo(this@CalendarAdapter)
      }
    }
  }

  override fun getItemCount(): Int =
    data.size

  override fun getItemId(position: Int): Long =
    data[position].id

  override fun getItemViewType(position: Int): Int = when (data[position]) {
    is CalendarDay -> TYPE_DAY
    is CalendarFooter -> TYPE_FOOTER
    is CalendarHeader -> TYPE_HEADER
    is CalendarSpace -> TYPE_SPACE
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = when (viewType) {
    TYPE_HEADER -> CalendarCellHeader(parent)
    TYPE_DAY -> CalendarCellDay(parent, output)
    TYPE_FOOTER -> footerAdapter.onCreateViewHolder(parent)
    else -> CalendarCellSpace(parent)
  }

  override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) =
    if (holder is ItemHolder<*>) {
      holder as ItemHolder<CalendarCell>
      holder.invoke(data[position])
    } else {
      val footers = footerAdapter as CalendarFooterAdapter<RecyclerView.ViewHolder>
      val item = data[position] as CalendarFooter
      footers.onBindViewHolder(holder, item.yearMonth)
    }

  private companion object {
    const val TYPE_SPACE = 0
    const val TYPE_HEADER = 1
    const val TYPE_DAY = 2
    const val TYPE_FOOTER = 3
  }
}
