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

package net.skyscanner.backpack.barchart.internal

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import net.skyscanner.backpack.barchart.BpkBarChart
import net.skyscanner.backpack.util.Consumer

internal class ChartAdapter(
  private val colors: BpkBarChart.Colors,
  private val onClick: Consumer<ChartBarHolder>
) : RecyclerView.Adapter<ChartBarHolder>(), Consumer<ChartData> {

  private var data: ChartData = ChartData()
  private var selectedId: Long = UNSELECTED_ID
  private var selectedPosition: Int = UNSELECTED_POSITION

  private val onClickWrapper = { holder: ChartBarHolder ->
    if (!holder.itemView.isSelected) {
      selectedId = holder.model?.id ?: UNSELECTED_ID
      selectedPosition = holder.adapterPosition
    } else {
      selectedId = UNSELECTED_ID
      selectedPosition = UNSELECTED_POSITION
    }

    notifyDataSetChanged()
    onClick(holder)
  }

  override fun invoke(model: ChartData) {
    this.data = model
    selectedPosition = UNSELECTED_POSITION
    notifyDataSetChanged()
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChartBarHolder =
    ChartBarHolder(parent, colors, onClickWrapper)

  override fun getItemCount(): Int =
    data.size

  override fun onBindViewHolder(holder: ChartBarHolder, position: Int) {
    val item = data.getItem(position)
    holder(item)
    val sameId = item.id != NO_ID && selectedId == item.id
    val samePosition = position == selectedPosition
    holder.itemView.isSelected = samePosition || sameId
  }

  private companion object {
    const val NO_ID = 0L
    const val UNSELECTED_ID = -1L
    const val UNSELECTED_POSITION = -1
  }
}
