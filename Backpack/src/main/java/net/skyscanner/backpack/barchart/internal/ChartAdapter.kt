package net.skyscanner.backpack.barchart.internal

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import net.skyscanner.backpack.barchart.BpkBarChartView
import net.skyscanner.backpack.util.Consumer

internal class ChartAdapter(
  private val colors: BpkBarChartView.Colors,
  private val onClick: Consumer<BpkBarChartView.Bar>,
  private val onLineChanged: Consumer<Float>
) : RecyclerView.Adapter<ChartBarHolder>(), Consumer<ChartData> {

  private var data: ChartData = ChartData()

  override fun invoke(model: ChartData) {
    this.data = model
    notifyDataSetChanged()
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChartBarHolder =
    ChartBarHolder(parent, colors, onClick, onLineChanged)

  override fun getItemCount(): Int =
    data.size

  override fun onBindViewHolder(holder: ChartBarHolder, position: Int) =
    holder.invoke(data.getItem(position))
}
