package net.skyscanner.backpack.demo.stories

import android.os.Bundle
import android.view.View
import android.widget.TextView
import net.skyscanner.backpack.barchart.BpkBarChartView
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.toast.BpkToast

class ToastStory : Story() {

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    view.findViewById<TextView>(R.id.toast_short).setOnClickListener {
      it as TextView
      BpkToast.makeText(activity!!, it.text, BpkToast.LENGTH_SHORT).show()
    }

    view.findViewById<TextView>(R.id.toast_long).setOnClickListener {
      it as TextView
      BpkToast.makeText(activity!!, it.text, BpkToast.LENGTH_LONG).show()
    }

    view.findViewById<BpkBarChartView>(R.id.test).apply {
      invoke(BpkBarChartView.Model(
        groups = listOf(
          createGroup(0),
          createGroup(1),
          createGroup(2),
          createGroup(3),
          createGroup(4),
          createGroup(5)
        )
      ))
    }

//    view.findViewById<View>(R.id.test1)?.background = ChartDrawable().apply {
//      value = 0.0f
//    }
//    view.findViewById<View>(R.id.test2)?.background = ChartDrawable().apply {
//      value = 0.5f
//    }
//    view.findViewById<View>(R.id.test3)?.background = ChartDrawable().apply {
//      value = 1.0f
//    }
//    view.findViewById<View>(R.id.test4)?.background = ChartDrawable().apply {
//      value = 1.1f
//    }
  }

  private fun createGroup(number: Int) = BpkBarChartView.Group(
    title = "Group $number",
    items = ArrayList<BpkBarChartView.Bar>(10).apply {
      for (i in 0 until 10) {
        add(createColumn(i))
      }
    }
  )

  private fun createColumn(progress: Int) = BpkBarChartView.Bar(
    title = arrayOf("A", "B", "C", "D", "E", "F", "G", "I", "J", "K")[progress],
    subtitle = progress.toString(),
    badge = "",
    value = progress / 10f,
    type = BpkBarChartView.Type.Primary
  )

  companion object {
    private const val LAYOUT_ID = "fragment_id"

    infix fun of(fragmentLayout: Int) = ToastStory().apply {
      arguments = Bundle()
      arguments?.putInt(LAYOUT_ID, fragmentLayout)
    }
  }
}
