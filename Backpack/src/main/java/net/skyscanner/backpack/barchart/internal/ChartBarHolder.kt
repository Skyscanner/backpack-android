package net.skyscanner.backpack.barchart.internal

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import net.skyscanner.backpack.R
import net.skyscanner.backpack.barchart.BpkBarChart
import net.skyscanner.backpack.util.Consumer
import net.skyscanner.backpack.util.ItemHolder

internal class ChartBarHolder(
  parent: ViewGroup,
  private val colors: BpkBarChart.Colors,
  private val onClick: Consumer<ChartBarHolder>
) : ItemHolder<BpkBarChart.Column>(parent, R.layout.view_bpk_barchart_column) {

  init {
    view.setOnClickListener {
      onClick(this)
    }
  }

  private val title = findViewById<TextView>(R.id.bpk_barchart_column_title).apply {
    setTextColor(colors.columnTitle)
  }

  private val subtitle = findViewById<TextView>(R.id.bpk_barchart_column_subtitle).apply {
    setTextColor(colors.columnSubtitle)
  }

  private val chart = ChartDrawable(colors.chartBackground, colors.chartForeground)

  private val chartView = findViewById<View>(R.id.bpk_barchart_column_chart).apply {
    background = chart
  }

  val chartRoundedTopPosition
    get() = chartView.bottom - chart.valueInPixels - chart.diameter

  override fun bind(model: BpkBarChart.Column) {
    title.text = model.title
    subtitle.text = model.subtitle
    chart.value = model.value
    view.isActivated = !model.inactive
    chartView.contentDescription = model.badge
  }
}
