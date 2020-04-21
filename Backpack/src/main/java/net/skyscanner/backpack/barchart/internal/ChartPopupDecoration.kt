package net.skyscanner.backpack.barchart.internal

import android.content.Context
import android.graphics.Canvas
import androidx.recyclerview.widget.RecyclerView
import net.skyscanner.backpack.barchart.BpkBarChart
import net.skyscanner.backpack.util.forEach
import net.skyscanner.backpack.util.withSave

internal class ChartPopupDecoration(
  context: Context,
  colors: BpkBarChart.Colors
) : RecyclerView.ItemDecoration() {

  private val drawable = ChartPopupDrawable(context, colors)

  override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
    super.onDrawOver(c, parent, state)

    parent.forEach {
      if (it.isSelected) {
        val holder = parent.getChildViewHolder(it) as ChartBarHolder
        drawPopup(c, parent, holder)
      }
    }
  }

  private fun drawPopup(c: Canvas, parent: RecyclerView, anchor: ChartBarHolder) {
    drawable.text = anchor.model?.badge.toString()

    c.withSave {
      val dx = anchor.itemView.left + (anchor.itemView.width - drawable.bounds.width()) / 2f
      val dy = parent.paddingTop + anchor.chartRoundedTopPosition - drawable.bounds.height()

      translate(dx, dy)
      drawable.draw(this)
    }
  }
}
