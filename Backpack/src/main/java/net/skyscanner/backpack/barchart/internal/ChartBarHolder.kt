package net.skyscanner.backpack.barchart.internal

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import net.skyscanner.backpack.R
import net.skyscanner.backpack.barchart.BpkBarChartView
import net.skyscanner.backpack.util.Consumer
import net.skyscanner.backpack.util.ItemHolder

internal class ChartBarHolder(
  parent: ViewGroup,
  private val colors: BpkBarChartView.Colors,
  private val onClick: Consumer<BpkBarChartView.Bar>,
  private val onLineChanged: Consumer<Float>
) : ItemHolder<BpkBarChartView.Bar>(parent, R.layout.view_bpk_barchart_column) {

  init {
    view.setOnClickListener {
      model?.let {
        onClick(it)
        onLineChanged(chartView.bottom - chart.valueInPixels)
      }
    }
  }

  private val title = findViewById<TextView>(R.id.bpk_barchart_column_title).apply {
    setTextColor(colors.columnTitle)
  }

  private val subtitle = findViewById<TextView>(R.id.bpk_barchart_column_subtitle).apply {
    setTextColor(colors.columnSubtitle)
  }

  private val chart = ChartDrawable()
    .apply { background = colors.chartBackground }

  private val chartView = findViewById<View>(R.id.bpk_barchart_column_chart).apply {
    background = chart
  }

  override fun bind(model: BpkBarChartView.Bar) {
    title.text = model.title
    subtitle.text = model.subtitle
    chart.value = model.value
    chart.foreground = colors.types.getValue(model.type)
  }
}
