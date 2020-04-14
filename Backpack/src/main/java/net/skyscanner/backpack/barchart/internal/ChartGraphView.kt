package net.skyscanner.backpack.barchart.internal

import android.annotation.SuppressLint
import android.content.Context
import android.view.Gravity
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import net.skyscanner.backpack.R
import net.skyscanner.backpack.barchart.BpkBarChartView
import net.skyscanner.backpack.text.BpkText
import net.skyscanner.backpack.util.Consumer

@SuppressLint("ViewConstructor")
internal class ChartGraphView constructor(
  context: Context,
  colors: BpkBarChartView.Colors,
  onClick: Consumer<BpkBarChartView.Bar>
) : FrameLayout(context), Consumer<List<BpkBarChartView.Group>> {

  private val titleHeight = resources.getDimensionPixelSize(R.dimen.bpkSpacingXl)

  private val title = BpkText(context).also {
    it.setTextColor(colors.groupTitle)
    it.textStyle = BpkText.LG
    it.weight = BpkText.Weight.EMPHASIZED
    it.gravity = Gravity.START or Gravity.CENTER_VERTICAL
    addView(it, LayoutParams(LayoutParams.WRAP_CONTENT, titleHeight))
  }

  private val recyclerView: RecyclerView = RecyclerView(context).also {
    it.setPadding(0, titleHeight, 0, 0)
    it.addOnScrollListener(object : RecyclerView.OnScrollListener() {
      override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        val position = layoutManager.findFirstVisibleItemPosition()
        val group = model.getGroup(position)
        if (title.text != group.title) {
          title.text = group.title
        }
      }
    })

    addView(it, LayoutParams(LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))
  }

  private val layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false).also {
    recyclerView.layoutManager = it
  }

  private val lineDecoration = ChartLineDecoration(recyclerView, colors).also {
    recyclerView.addItemDecoration(it)
  }

  private val adapter = ChartAdapter(colors, onClick, lineDecoration).also {
    recyclerView.adapter = it
  }

  private var model: ChartData = ChartData()

  override fun invoke(groups: List<BpkBarChartView.Group>) {
    this.model = ChartData(groups)
    adapter.invoke(model)
  }
}
