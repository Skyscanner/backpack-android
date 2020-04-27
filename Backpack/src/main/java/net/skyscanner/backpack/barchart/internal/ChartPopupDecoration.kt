package net.skyscanner.backpack.barchart.internal

import android.content.Context
import android.graphics.Canvas
import androidx.recyclerview.widget.RecyclerView
import net.skyscanner.backpack.R
import net.skyscanner.backpack.barchart.BpkBarChart
import net.skyscanner.backpack.util.forEach
import net.skyscanner.backpack.util.withSave
import kotlin.math.max

internal class ChartPopupDecoration(
  context: Context,
  colors: BpkBarChart.Colors
) : RecyclerView.ItemDecoration() {

  private val drawable = ChartPopupDrawable(context, colors)
  private val bottomSpacing = context.resources.getDimension(R.dimen.bpkSpacingSm)
  private val topSpacing = context.resources.getDimension(R.dimen.bpkSpacingMd) + context.resources.getDimension(R.dimen.bpkSpacingSm)

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
      val dy = max(
        parent.paddingTop + anchor.chartRoundedTopPosition - bottomSpacing - drawable.bounds.height(),
        parent.paddingTop.toFloat() + topSpacing)

      translate(dx, dy)
      drawable.draw(this)
    }
  }
}
