package net.skyscanner.backpack.barchart.internal

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import net.skyscanner.backpack.R
import net.skyscanner.backpack.badge.BpkBadge
import net.skyscanner.backpack.barchart.BpkBarChart
import net.skyscanner.backpack.util.Consumer

@SuppressLint("ViewConstructor")
internal class ChartLegend constructor(
  context: Context,
  colors: BpkBarChart.Colors
) : LinearLayout(context), Consumer<BpkBarChart.Legend?> {

  init {
    orientation = HORIZONTAL
    LayoutInflater.from(context).inflate(R.layout.view_bpk_barchart_legend, this, true)
  }

  private val enabled = findViewById<BpkBadge>(R.id.bpk_barchart_legend_enabled).apply {
    setBackground(colors.chartForeground)
  }
  private val disabled = findViewById<BpkBadge>(R.id.bpk_barchart_legend_disabled).apply {
    setBackground(colors.chartForeground)
  }

  override fun invoke(legend: BpkBarChart.Legend?) {
    if (legend == null) {
      enabled.visibility = View.GONE
      disabled.visibility = View.GONE
    } else {
      enabled.visibility = View.VISIBLE
      disabled.visibility = View.VISIBLE
      enabled.text = legend.enabledTitle
      disabled.text = legend.disabledTitle
    }
  }
}
