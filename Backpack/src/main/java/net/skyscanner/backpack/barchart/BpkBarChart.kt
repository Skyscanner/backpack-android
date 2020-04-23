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

  /**
   * Represents a single bar in the chart.
   */
  data class Column(

    /**
     * An optional identifier of the bar.
     * This allows to save the selected element each time the model is updated.
     *
     * If not set, the selection will be dropped each time when you set new model.
     */
    val id: Long = 0L,

    /**
     * A primary text placed just below the bar itself.
     */
    val title: CharSequence,

    /**
     * A secondary text placed just below the title.
     */
    val subtitle: CharSequence,

    /**
     * Text to be shown in the popup when the item is selected.
     */
    val badge: CharSequence,

    /**
     * Marking the item inactive means inactive colours from the palette, will be used to draw this item.
     *
     * This can be used to show that there's no data available for this bar.
     *
     * The item will remain clickable.
     */
    val inactive: Boolean,

    /**
     * The value of the bar itself, from 0.0f to any other number.
     * If the value is larger than 1.0f a peak will be drawn.
     * The range between 0.0f and 1.0f represent percentage of the bar.
     */
    val value: Float
  )

  /**
   * Represents a group of the items sharing the same title.
   * The title is being rendered above the bars and being updated when chart is being scrolled.
   */
  data class Group(

    /**
     * Label of this group to be renderer above the bars.
     */
    val title: CharSequence,

    /**
     * Bars in the group.
     */
    val items: List<Column>
  )

  /**
   * Represents a legend for the chart.
   * [Column.inactive] attribute is being used to split the bars to active/inactive sets.
   * These properties explain each set.
   */
  data class Legend(

    /**
     * A label being rendered in the legend with using active colours from the palette.
     */
    val activeTitle: CharSequence,

    /**
     * A label being rendered in the legend with using inactive colours from the palette.
     */
    val inactiveTitle: CharSequence
  )

  /**
   * Represents a view model which is needed to be feed into the component to render the data.
   *
   * @see [BpkBarChart.model]
   * @see [BpkBarChart.invoke]
   */
  data class Model(

    /**
     * Grouped bars to render.
     */
    val groups: List<Group>,

    /**
     * An optional legend. If null, no legend is being shown.
     */
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
