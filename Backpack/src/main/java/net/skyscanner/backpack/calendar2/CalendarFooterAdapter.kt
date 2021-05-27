package net.skyscanner.backpack.calendar2

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import java.time.YearMonth

interface CalendarFooterAdapter<VH : RecyclerView.ViewHolder> {

  fun onCreateViewHolder(parent: ViewGroup): VH

  fun onBindViewHolder(holder: VH, data: YearMonth)
}

internal object DefaultCalendarFooterAdapter : CalendarFooterAdapter<RecyclerView.ViewHolder> {

  override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder =
    error("A custom footer adapter has to be assigned!")

  override fun onBindViewHolder(holder: RecyclerView.ViewHolder, data: YearMonth) =
    error("A custom footer adapter has to be assigned!")
}
