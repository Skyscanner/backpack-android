/*
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

package net.skyscanner.backpack.barchart

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.view.Gravity
import android.view.ViewGroup
import android.widget.FrameLayout
import net.skyscanner.backpack.R
import net.skyscanner.backpack.barchart.internal.ChartGraphView
import net.skyscanner.backpack.barchart.internal.ChartLegend
import net.skyscanner.backpack.util.Consumer
import net.skyscanner.backpack.util.createContextThemeWrapper
import net.skyscanner.backpack.util.use

open class BpkBarChart @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
) : FrameLayout(createContextThemeWrapper(context, attrs, R.attr.bpkBarChartStyle), attrs, defStyleAttr),
  Consumer<BpkBarChartModel> {

  data class Colors(
    val columnTitle: ColorStateList,
    val columnSubtitle: ColorStateList,
    val groupTitle: ColorStateList,
    val chartBackground: ColorStateList,
    val chartForeground: ColorStateList,
    val chartLine: ColorStateList,
    val popupBackground: ColorStateList,
    val popupText: ColorStateList,
  )

  interface OnBarClickListener : Consumer<BpkBarChartModel.Item> {

    override fun invoke(item: BpkBarChartModel.Item)
  }

  var listener: Consumer<BpkBarChartModel.Item>? = null

  private val graphView: ChartGraphView
  private val legendView: ChartLegend
  private val colors: Colors

  init {
    val themedContext = this.context

    var columnTitle = context.getColorStateList(R.color.bpk_barchart_title_selector)
    var columnSubtitle = context.getColorStateList(R.color.bpk_barchart_subtitle_selector)
    var groupTitle = context.getColorStateList(R.color.bpkTextPrimary)
    var chartBackground = context.getColorStateList(R.color.bpkSurfaceHighlight)
    var chartForeground = context.getColorStateList(R.color.bpk_barchart_bar_selector)
    var chartLine = context.getColorStateList(R.color.bpkCoreAccent)
    var popupBackground = context.getColorStateList(R.color.bpkCoreAccent)
    var popupText = context.getColorStateList(R.color.bpkTextPrimaryInverse)

    themedContext.theme.obtainStyledAttributes(
      attrs,
      R.styleable.BpkBarChart,
      defStyleAttr, 0
    ).use {
      columnTitle = it.getColorStateList(R.styleable.BpkBarChart_barChartColumnTitleColor)
        ?: columnTitle
      columnSubtitle = it.getColorStateList(R.styleable.BpkBarChart_barChartColumnSubtitleColor)
        ?: columnSubtitle
      groupTitle = it.getColorStateList(R.styleable.BpkBarChart_barChartGroupTitleColor)
        ?: groupTitle
      chartBackground = it.getColorStateList(R.styleable.BpkBarChart_barChartBarBackgroundColor)
        ?: chartBackground
      chartForeground = it.getColorStateList(R.styleable.BpkBarChart_barChartBarForegroundColor)
        ?: chartForeground
      chartLine = it.getColorStateList(R.styleable.BpkBarChart_barChartLineColor) ?: chartLine
      popupBackground = it.getColorStateList(R.styleable.BpkBarChart_barChartPopupBackgroundColor)
        ?: popupBackground
      popupText = it.getColorStateList(R.styleable.BpkBarChart_barChartPopupTextColor) ?: popupText
    }

    colors = Colors(
      columnTitle = columnTitle,
      columnSubtitle = columnSubtitle,
      groupTitle = groupTitle,
      chartBackground = chartBackground,
      chartForeground = chartForeground,
      chartLine = chartLine,
      popupBackground = popupBackground,
      popupText = popupText
    )

    graphView = ChartGraphView(themedContext, colors) {
      listener?.invoke(it)
    }

    legendView = ChartLegend(themedContext, colors)
    addView(
      legendView,
      LayoutParams(
        LayoutParams.WRAP_CONTENT,
        ViewGroup.LayoutParams.WRAP_CONTENT,
        Gravity.END or Gravity.TOP
      )
    )

    addView(graphView, LayoutParams(LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))
  }

  var model: BpkBarChartModel? = null
    set(value) {
      field = value
      graphView.invoke(model?.items ?: emptyList())
      legendView.invoke(model?.legend)
    }

  override fun invoke(model: BpkBarChartModel) {
    this.model = model
  }
}
