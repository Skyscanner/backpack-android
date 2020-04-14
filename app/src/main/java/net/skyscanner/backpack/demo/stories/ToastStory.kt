package net.skyscanner.backpack.demo.stories

import android.os.Bundle
import android.view.View
import android.widget.TextView
import net.skyscanner.backpack.barchart.BpkBarChartView
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.toast.BpkToast
import java.util.*
import kotlin.collections.ArrayList

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

  private val random = Random()

  private fun createGroup(month: Int) = BpkBarChartView.Group(
    title = arrayOf("January", "February", "March", "April", "May", "June", "Jule")[month % 6],
    items = ArrayList<BpkBarChartView.Bar>(10).apply {
      for (dayOfTheMonth in 0 until 30) {
        add(createColumn(month * 30 + dayOfTheMonth))
      }
    }
  )

  private fun createColumn(dayOfTheYear: Int) = BpkBarChartView.Bar(
    title = arrayOf("Mo", "Tu", "We", "Th", "Fr", "Sa", "Su")[dayOfTheYear % 7],
    subtitle = (dayOfTheYear % 30 + 1).toString(),
    badge = "",
    value = random.nextInt(120) / 100f,
    disabled = random.nextInt(5) == 0
  )

  companion object {
    private const val LAYOUT_ID = "fragment_id"

    infix fun of(fragmentLayout: Int) = ToastStory().apply {
      arguments = Bundle()
      arguments?.putInt(LAYOUT_ID, fragmentLayout)
    }
  }
}
