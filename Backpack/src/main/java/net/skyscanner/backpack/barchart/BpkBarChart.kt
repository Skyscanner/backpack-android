package net.skyscanner.backpack.barchart

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.view.Gravity
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
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
  Consumer<BpkBarChart.Model> {

  data class Column(
    val id: Long = 0L,
    val title: CharSequence,
    val subtitle: CharSequence,
    val badge: CharSequence,
    val inactive: Boolean,
    val value: Float
  )

  data class Group(
    val title: CharSequence,
    val items: List<Column>
  )

  data class Legend(
    val activeTitle: CharSequence,
    val inactiveTitle: CharSequence
  )

  data class Model(
    val groups: List<Group>,
    val legend: Legend? = null
  )

  data class Colors(
    val columnTitle: ColorStateList,
    val columnSubtitle: ColorStateList,
    val groupTitle: ColorStateList,
    val chartBackground: ColorStateList,
    val chartForeground: ColorStateList,
    val chartLine: ColorStateList,
    val popupBackground: ColorStateList,
    val popupText: ColorStateList
  )

  interface OnBarClickListener : Consumer<Column> {

    override fun invoke(column: Column)
  }

  var listener: Consumer<Column>? = null

  private val graphView: ChartGraphView
  private val legendView: ChartLegend
  private val colors: Colors

  init {
    val themedContext = this.context

    var columnTitle = ContextCompat.getColorStateList(themedContext, R.color.bpk_barchart_title_selector)!!
    var columnSubtitle = ContextCompat.getColorStateList(themedContext, R.color.bpk_barchart_subtitle_selector)!!
    var groupTitle = ContextCompat.getColorStateList(themedContext, R.color.bpkTextPrimary)!!
    var chartBackground = ContextCompat.getColorStateList(themedContext, R.color.bpkBackgroundSecondary)!!
    var chartForeground = ContextCompat.getColorStateList(themedContext, R.color.bpk_barchart_bar_selector)!!
    var chartLine = ContextCompat.getColorStateList(themedContext, R.color.__barChartActivatedColor)!!
    var popupBackground = ContextCompat.getColorStateList(themedContext, R.color.__barChartPopupBackgroundColor)!!
    var popupText = ContextCompat.getColorStateList(themedContext, R.color.__barChartPopupTextColor)!!

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
    addView(legendView, LayoutParams(LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.END or Gravity.TOP))

    addView(graphView, LayoutParams(LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))
  }

  var model: Model? = null
    set(value) {
      field = value
      graphView.invoke(model?.groups)
      legendView.invoke(model?.legend)
    }

  override fun invoke(model: Model) {
    this.model = model
  }
}
