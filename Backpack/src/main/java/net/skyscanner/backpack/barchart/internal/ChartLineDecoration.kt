package net.skyscanner.backpack.barchart.internal

import android.graphics.Canvas
import android.graphics.Paint
import android.util.TypedValue
import androidx.recyclerview.widget.RecyclerView
import net.skyscanner.backpack.barchart.BpkBarChart
import net.skyscanner.backpack.util.Consumer

internal class ChartLineDecoration(
  private val parent: RecyclerView,
  private val colors: BpkBarChart.Colors
) : RecyclerView.ItemDecoration(), Consumer<Float> {

  private val paint = Paint().apply {
    strokeWidth = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1f, parent.resources.displayMetrics)
    style = Paint.Style.FILL_AND_STROKE
  }

  private var position = Float.MIN_VALUE

  override fun invoke(position: Float) {
    this.position = position
    parent.invalidate()
  }

  override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
    super.onDraw(c, parent, state)
    if (position == Float.MIN_VALUE) return

    val lineY = position + parent.paddingTop

    paint.color = colors.chartLine.run { getColorForState(parent.drawableState, defaultColor) }
    c.drawLine(0f, lineY, parent.width.toFloat(), lineY, paint)
  }
}
