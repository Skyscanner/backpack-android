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
import androidx.recyclerview.widget.RecyclerView
import net.skyscanner.backpack.calendar2.CalendarFooterAdapter
import net.skyscanner.backpack.calendar2.data.CalendarDay
import net.skyscanner.backpack.calendar2.data.CalendarFooter
import net.skyscanner.backpack.calendar2.data.CalendarHeader
import net.skyscanner.backpack.calendar2.data.CalendarItem
import net.skyscanner.backpack.calendar2.data.CalendarMonth
import net.skyscanner.backpack.calendar2.data.CalendarSpace
import net.skyscanner.backpack.calendar2.extension.getItemByGlobalIndex
import net.skyscanner.backpack.calendar2.extension.yearMonthHash
import net.skyscanner.backpack.util.Consumer
import net.skyscanner.backpack.util.ItemHolder
import org.threeten.bp.temporal.ChronoField

internal class CalendarAdapter(
  private val footers: CalendarFooterAdapter<*>,
  private val output: Consumer<CalendarDay>,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), Consumer<List<CalendarMonth>> {

  private var data: List<CalendarMonth> = emptyList()

  init {
    setHasStableIds(true)
  }

  override fun invoke(data: List<CalendarMonth>) {
    this.data = data
    notifyDataSetChanged()
  }

  override fun getItemCount(): Int =
    data.sumBy { it.items.size }

  override fun getItemViewType(position: Int): Int = when (data.getItemByGlobalIndex(position)) {
    is CalendarDay -> TYPE_DAY
    is CalendarFooter -> TYPE_FOOTER
    is CalendarHeader -> TYPE_HEADER
    is CalendarSpace -> TYPE_SPACE
  }

  override fun getItemId(position: Int): Long = when (val item = data.getItemByGlobalIndex(position)) {
    is CalendarDay -> item.date.getLong(ChronoField.EPOCH_DAY)
    is CalendarFooter -> item.yearMonth.yearMonthHash() * -10L - 1
    is CalendarHeader -> item.yearMonth.yearMonthHash() * -10L - 2
    is CalendarSpace -> item.yearMonth.yearMonthHash() * -10L - 3
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = when (viewType) {
    TYPE_HEADER -> CalendarCellHeader(parent)
    TYPE_DAY -> CalendarCellDay(parent, output)
    TYPE_FOOTER -> footers.onCreateViewHolder(parent)
    else -> CalendarCellSpace(parent)
  }

  override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) =
    if (holder is ItemHolder<*>) {
      holder as ItemHolder<CalendarItem>
      holder.invoke(data.getItemByGlobalIndex(position))
    } else {
      footers as CalendarFooterAdapter<RecyclerView.ViewHolder>
      val item = data.getItemByGlobalIndex(position) as CalendarFooter
      footers.onBindViewHolder(holder, item.yearMonth)
    }

  private companion object {
    const val TYPE_SPACE = 0
    const val TYPE_HEADER = 1
    const val TYPE_DAY = 2
    const val TYPE_FOOTER = 3
  }
}
