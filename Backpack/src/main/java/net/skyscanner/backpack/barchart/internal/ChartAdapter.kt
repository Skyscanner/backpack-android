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
  private var selectedId: Long = -1L
  private var selectedPosition: Int = -1

  private val onClickWrapper = object : Consumer<ChartBarHolder> {
    override fun invoke(holder: ChartBarHolder) {
      selectedId = holder.model?.id ?: -1L
      selectedPosition = holder.adapterPosition
      notifyDataSetChanged()
      onClick(holder)
    }
  }

  override fun invoke(model: ChartData) {
    this.data = model
    selectedPosition = -1
    notifyDataSetChanged()
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChartBarHolder =
    ChartBarHolder(parent, colors, onClickWrapper)

  override fun getItemCount(): Int =
    data.size

  override fun onBindViewHolder(holder: ChartBarHolder, position: Int) {
    val item = data.getItem(position)
    holder(item)
    val sameId = item.id != 0L && selectedId == item.id
    val samePosition = position == selectedPosition
    holder.itemView.isSelected = samePosition || sameId
  }
}
