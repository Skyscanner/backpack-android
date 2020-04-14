package net.skyscanner.backpack.barchart

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import net.skyscanner.backpack.R
import net.skyscanner.backpack.barchart.internal.ChartGraphView
import net.skyscanner.backpack.util.Consumer
import net.skyscanner.backpack.util.createContextThemeWrapper
import net.skyscanner.backpack.util.use

class BpkBarChartView @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
) : ConstraintLayout(createContextThemeWrapper(context, attrs, R.attr.bpkBarChartStyle), attrs, defStyleAttr),
  (BpkBarChartView.Model) -> Unit {

  enum class Type {
    Undefined,
    Primary
  }

  data class Bar(
    val title: CharSequence,
    val subtitle: CharSequence,
    val badge: CharSequence,
    val type: Type,
    val value: Float
  )

  data class Group(
    val title: CharSequence,
    val items: List<Bar>
  )

  data class Legend(
    val type: Type,
    val label: CharSequence
  )

  data class Model(
    val groups: List<Group>,
    val legend: List<Legend>? = null
  )

  data class Colors(
    val columnTitle: ColorStateList,
    val columnSubtitle: ColorStateList,
    val groupTitle: ColorStateList,
    val chartBackground: ColorStateList,
    val chartLine: ColorStateList,
    val types: Map<Type, ColorStateList>
  )

  interface OnBarClickListener : Consumer<Bar> {

    override fun invoke(bar: Bar)
  }

  var listener: Consumer<Bar>? = null

  private val graphView: ChartGraphView

  init {
    var columnTitle = ContextCompat.getColorStateList(context, R.color.__barChartColumnTitleColor)!!
    var columnSubtitle = ContextCompat.getColorStateList(context, R.color.__barChartColumnSubtitleColor)!!
    var groupTitle = ContextCompat.getColorStateList(context, R.color.__barChartGroupTitleColor)!!
    var chartBackground = ContextCompat.getColorStateList(context, R.color.__barChartBarBackgroundColor)!!
    var chartLine = ContextCompat.getColorStateList(context, R.color.__barChartLineColor)!!
    var undefined = ContextCompat.getColorStateList(context, R.color.__barChartUndefinedColor)!!
    var primary = ContextCompat.getColorStateList(context, R.color.__barChartPrimaryColor)!!

    context.theme.obtainStyledAttributes(
      attrs,
      R.styleable.BpkBarChartView,
      defStyleAttr, 0
    ).use {
      columnTitle = it.getColorStateList(R.styleable.BpkBarChartView_barChartColumnTitleColor)
        ?: columnTitle
      columnSubtitle = it.getColorStateList(R.styleable.BpkBarChartView_barChartColumnSubtitleColor)
        ?: columnSubtitle
      groupTitle = it.getColorStateList(R.styleable.BpkBarChartView_barChartGroupTitleColor)
        ?: groupTitle
      chartBackground = it.getColorStateList(R.styleable.BpkBarChartView_barChartBarBackgroundColor)
        ?: chartBackground
      chartLine = it.getColorStateList(R.styleable.BpkBarChartView_barChartLineColor) ?: chartLine
      undefined = it.getColorStateList(R.styleable.BpkBarChartView_barChartUndefinedColor)
        ?: undefined
      primary = it.getColorStateList(R.styleable.BpkBarChartView_barChartPrimaryColor) ?: primary
    }

    val colors = Colors(
      columnTitle = columnTitle,
      columnSubtitle = columnSubtitle,
      groupTitle = groupTitle,
      chartBackground = chartBackground,
      chartLine = chartLine,
      types = mapOf(
        Type.Undefined to undefined,
        Type.Primary to primary
      )
    )

    graphView = ChartGraphView(context, colors) {
      listener?.invoke(it)
    }

    addView(graphView, LayoutParams(LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))
  }

  override fun invoke(model: Model) {
    graphView(model.groups)
  }
}
