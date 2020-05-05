/**
 * Backpack for Android - Skyscanner's Design System
 *
 * Copyright 2018-2020 Skyscanner Ltd
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

  private val activated = findViewById<BpkBadge>(R.id.bpk_barchart_legend_activated).apply {
    isActivated = true
    setBackground(colors.chartForeground)
  }
  private val inactivated = findViewById<BpkBadge>(R.id.bpk_barchart_legend_inactivated).apply {
    isActivated = false
    setBackground(colors.chartForeground)
  }

  override fun invoke(legend: BpkBarChart.Legend?) {
    if (legend == null) {
      activated.visibility = View.GONE
      inactivated.visibility = View.GONE
    } else {
      activated.visibility = View.VISIBLE
      inactivated.visibility = View.VISIBLE
      activated.text = legend.activeTitle
      inactivated.text = legend.inactiveTitle
    }
  }
}
