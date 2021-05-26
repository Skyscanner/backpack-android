package net.skyscanner.backpack.calendar2

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

interface CalendarFooterAdapter<VH : RecyclerView.ViewHolder> {

  fun onCreateViewHolder(parent: ViewGroup): VH

  fun onBindViewHolder(holder: VH, month: Int, year: Int)
}

internal object DefaultCalendarFooterAdapter : CalendarFooterAdapter<RecyclerView.ViewHolder> {

  override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder =
    error("A custom footer adapter has to be assigned!")

  override fun onBindViewHolder(holder: RecyclerView.ViewHolder, month: Int, year: Int) =
    error("A custom footer adapter has to be assigned!")
}
