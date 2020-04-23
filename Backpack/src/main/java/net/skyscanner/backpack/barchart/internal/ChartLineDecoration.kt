package net.skyscanner.backpack.barchart.internal

import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Paint
import android.util.TypedValue
import androidx.recyclerview.widget.RecyclerView
import net.skyscanner.backpack.barchart.BpkBarChart
import net.skyscanner.backpack.util.Consumer
import net.skyscanner.backpack.util.forEach
import net.skyscanner.backpack.util.getColorForState

internal class ChartLineDecoration(
  private val resources: Resources,
  private val colors: BpkBarChart.Colors
) : RecyclerView.ItemDecoration(), Consumer<ChartBarHolder> {

  private val paint = Paint().apply {
    strokeWidth = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1f, resources.displayMetrics)
    style = Paint.Style.FILL_AND_STROKE
  }

  private var position = Float.MIN_VALUE

  override fun invoke(holder: ChartBarHolder) {
    if (!holder.itemView.isSelected) {
      this.position = holder.chartRoundedTopPosition
    } else {
      this.position = Float.MIN_VALUE
    }
  }

  override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
    super.onDraw(c, parent, state)
    if (position == Float.MIN_VALUE) return
    drawLine(c, parent, 0, parent.width)
  }

  override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
    super.onDrawOver(c, parent, state)
    if (position == Float.MIN_VALUE) return

    parent.forEach {
      if (it.isSelected) {
        drawLine(c, parent, it.left, it.right)
      }
    }
  }

  private fun drawLine(c: Canvas, parent: RecyclerView, left: Int, right: Int) {
    val lineY = position + parent.paddingTop
    paint.color = colors.chartLine.getColorForState(parent.drawableState)
    c.drawLine(left.toFloat(), lineY, right.toFloat(), lineY, paint)
  }
}
