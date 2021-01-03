/**
 * Backpack for Android - Skyscanner's Design System
 *
 * Copyright 2018-2021 Skyscanner Ltd
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

import android.content.Context
import android.graphics.Canvas
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.max
import net.skyscanner.backpack.R
import net.skyscanner.backpack.barchart.BpkBarChart
import net.skyscanner.backpack.util.forEach
import net.skyscanner.backpack.util.withSave

internal class ChartPopupDecoration(
  context: Context,
  colors: BpkBarChart.Colors
) : RecyclerView.ItemDecoration() {

  private val drawable = ChartPopupDrawable(context, colors)
  private val bottomSpacing = context.resources.getDimension(R.dimen.bpkSpacingSm)
  private val topSpacing = context.resources.getDimension(R.dimen.bpkSpacingMd) +
    context.resources.getDimension(R.dimen.bpkSpacingSm)

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
        parent.paddingTop.toFloat() + topSpacing
      )

      translate(dx, dy)
      drawable.draw(this)
    }
  }
}
