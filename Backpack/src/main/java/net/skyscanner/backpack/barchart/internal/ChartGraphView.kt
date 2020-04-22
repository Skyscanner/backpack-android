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
import net.skyscanner.backpack.text.BpkText
import net.skyscanner.backpack.util.Consumer

@SuppressLint("ViewConstructor")
internal class ChartGraphView constructor(
  context: Context,
  colors: BpkBarChart.Colors,
  onClick: Consumer<BpkBarChart.Bar>
) : FrameLayout(context), Consumer<List<BpkBarChart.Group>> {

  private val onClickWrapper = object : Consumer<ChartBarHolder> {
    override fun invoke(holder: ChartBarHolder) {
      onClick(holder.model!!)
      lineDecoration.invoke(holder)
      recyclerView.invalidateItemDecorations()
    }
  }

  private val titleHeight = resources.getDimensionPixelSize(R.dimen.bpkSpacingXl)
  private val titleSpacing = resources.getDimensionPixelSize(R.dimen.bpkSpacingXl)

  private val title = BpkText(context).also {
    it.setTextColor(colors.groupTitle)
    it.textStyle = BpkText.LG
    it.weight = BpkText.Weight.EMPHASIZED
    it.gravity = Gravity.START or Gravity.CENTER_VERTICAL
    addView(it, LayoutParams(LayoutParams.WRAP_CONTENT, titleHeight))
  }

  private val recyclerView: RecyclerView = RecyclerView(context).also {
    it.setPadding(0, titleHeight + titleSpacing, 0, 0)
    it.addOnScrollListener(object : RecyclerView.OnScrollListener() {
      override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        val position = layoutManager.findFirstVisibleItemPosition()
        val group = model.getGroup(position)
        if (title.text != group.title) {
          title.text = group.title
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

  private var model: ChartData = ChartData()

  override fun invoke(groups: List<BpkBarChart.Group>) {
    this.model = ChartData(groups)
    adapter.invoke(model)
  }
}
