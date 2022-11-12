/**
 * Backpack for Android - Skyscanner's Design System
 *
 * Copyright 2018 Skyscanner Ltd
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
import android.view.Gravity
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import net.skyscanner.backpack.R
import net.skyscanner.backpack.barchart.BpkBarChart
import net.skyscanner.backpack.barchart.BpkBarChartModel
import net.skyscanner.backpack.text.BpkText
import net.skyscanner.backpack.util.Consumer

@SuppressLint("ViewConstructor")
internal class ChartGraphView constructor(
  context: Context,
  colors: BpkBarChart.Colors,
  onClick: Consumer<BpkBarChartModel.Item>
) : FrameLayout(context), Consumer<List<BpkBarChartModel.Item>> {

  private val onClickWrapper = { holder: ChartBarHolder ->
    onClick(holder.model!!)
    lineDecoration(holder)
    recyclerView.invalidateItemDecorations()
  }

  private val titleHeight = resources.getDimensionPixelSize(R.dimen.bpkSpacingXl)
  private val titleSpacing = resources.getDimensionPixelSize(R.dimen.bpkSpacingMd) +
    resources.getDimensionPixelSize(R.dimen.bpkSpacingSm)

  private val title = BpkText(context).also {
    it.setTextColor(colors.groupTitle)
    it.textStyle = BpkText.TextStyle.Heading4
    it.gravity = Gravity.START or Gravity.CENTER_VERTICAL
    addView(it, LayoutParams(LayoutParams.WRAP_CONTENT, titleHeight))
  }

  private val recyclerView: RecyclerView = RecyclerView(context).also {
    it.clipToPadding = false
    it.clipChildren = false
    it.setPadding(0, titleHeight + titleSpacing, 0, 0)
    it.addOnScrollListener(object : RecyclerView.OnScrollListener() {
      override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        val position = layoutManager.findFirstVisibleItemPosition()
        val group = model[position].group
        if (title.text != group) {
          title.text = group
        }
      }
    })
    it.addItemDecoration(ChartPopupDecoration(context, colors))
    addView(it, LayoutParams(LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))
  }

  private val layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false).also {
    recyclerView.layoutManager = it
  }

  private val lineDecoration = ChartLineDecoration(resources, colors).also {
    recyclerView.addItemDecoration(it)
  }

  private val popupDecoration = ChartPopupDecoration(context, colors).also {
    recyclerView.addItemDecoration(it)
  }

  private val adapter = ChartAdapter(colors, onClickWrapper).also {
    recyclerView.adapter = it
  }

  private var model: List<BpkBarChartModel.Item> = emptyList()

  override fun invoke(items: List<BpkBarChartModel.Item>) {
    this.model = items
    adapter.invoke(model)
  }
}
