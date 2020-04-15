package net.skyscanner.backpack.demo.stories

import android.os.Bundle
import android.view.View
import net.skyscanner.backpack.barchart.BpkBarChart
import net.skyscanner.backpack.demo.R
import java.util.*
import kotlin.collections.ArrayList

class BarChartStory : Story() {

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    view.findViewById<BpkBarChart>(R.id.bar_chart).apply {
      invoke(BpkBarChart.Model(
        groups = listOf(
          createMonth(0),
          createMonth(1),
          createMonth(2),
          createMonth(3),
          createMonth(4),
          createMonth(5)
        )
      ))
    }
  }

  private val random = Random()

  private fun createMonth(month: Int) = BpkBarChart.Group(
    title = arrayOf("January", "February", "March", "April", "May", "June", "Jule")[month % 6],
    items = ArrayList<BpkBarChart.Bar>(10).apply {
      for (dayOfTheMonth in 0 until 30) {
        add(createBar(month * 30 + dayOfTheMonth))
      }
    }
  )

  private fun createBar(dayOfTheYear: Int) = BpkBarChart.Bar(
    title = arrayOf("Mo", "Tu", "We", "Th", "Fr", "Sa", "Su")[dayOfTheYear % 7],
    subtitle = (dayOfTheYear % 30 + 1).toString(),
    badge = "",
    value = random.nextInt(120) / 100f,
    disabled = random.nextInt(5) == 0
  )

  companion object {
    private const val LAYOUT_ID = "fragment_id"

    infix fun of(fragmentLayout: Int) = BarChartStory().apply {
      arguments = Bundle()
      arguments?.putInt(LAYOUT_ID, fragmentLayout)
    }
  }
}
