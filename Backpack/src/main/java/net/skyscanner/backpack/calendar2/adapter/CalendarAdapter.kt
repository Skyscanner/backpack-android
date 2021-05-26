package net.skyscanner.backpack.calendar2.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import net.skyscanner.backpack.calendar2.CalendarFooterAdapter
import net.skyscanner.backpack.calendar2.CalendarState
import net.skyscanner.backpack.calendar2.data.CalendarDay
import net.skyscanner.backpack.calendar2.data.CalendarFooter
import net.skyscanner.backpack.calendar2.data.CalendarHeader
import net.skyscanner.backpack.calendar2.data.CalendarItem
import net.skyscanner.backpack.calendar2.data.CalendarMonth
import net.skyscanner.backpack.calendar2.data.CalendarSpace
import net.skyscanner.backpack.calendar2.extension.getItem
import net.skyscanner.backpack.calendar2.extension.yearMonthHash
import net.skyscanner.backpack.util.Consumer
import net.skyscanner.backpack.util.ItemHolder
import org.threeten.bp.temporal.ChronoField

internal class CalendarAdapter(
  scope: CoroutineScope,
  input: StateFlow<CalendarState>,
  private val footers: CalendarFooterAdapter<*>,
  private val output: Consumer<CalendarDay>,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

  private var data: List<CalendarMonth> = input.value.months

  init {
    setHasStableIds(true)
    input.onEach {
      this.data = it.months
      notifyDataSetChanged()
    }.launchIn(scope)
  }

  override fun getItemCount(): Int =
    data.sumBy { it.items.size }

  override fun getItemViewType(position: Int): Int = when (data.getItem(position)) {
    is CalendarDay -> TYPE_DAY
    is CalendarFooter -> TYPE_FOOTER
    is CalendarHeader -> TYPE_HEADER
    is CalendarSpace -> TYPE_SPACE
  }

  override fun getItemId(position: Int): Long = when (val item = data.getItem(position)) {
    is CalendarDay -> item.date.getLong(ChronoField.EPOCH_DAY)
    is CalendarFooter -> item.yearMonth.yearMonthHash() * -10L - 1
    is CalendarHeader -> item.yearMonth.yearMonthHash() * -10L - 2
    is CalendarSpace -> RecyclerView.NO_ID
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
      holder.invoke(data.getItem(position))
    } else {
      footers as CalendarFooterAdapter<RecyclerView.ViewHolder>
      val item = data.getItem(position) as CalendarFooter
      footers.onBindViewHolder(holder, item.yearMonth.monthValue, item.yearMonth.year)
    }

  private companion object {

    const val TYPE_SPACE = 0
    const val TYPE_HEADER = 1
    const val TYPE_DAY = 2
    const val TYPE_FOOTER = 3
  }
}
