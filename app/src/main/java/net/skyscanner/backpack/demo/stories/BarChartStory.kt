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

package net.skyscanner.backpack.demo.stories

import android.os.Bundle
import android.view.View
import java.util.Random
import net.skyscanner.backpack.barchart.BpkBarChart
import net.skyscanner.backpack.demo.R

class BarChartStory : Story() {

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    view.findViewById<BpkBarChart>(R.id.bar_chart).apply {
      model = BpkBarChart.Model(
        groups = listOf(
          createMonth(0),
          createMonth(1),
          createMonth(2),
          createMonth(3),
          createMonth(4),
          createMonth(5)
        ),
        legend = BpkBarChart.Legend(
          "£",
          "No Price"
        )
      )
    }
  }

  private val random = Random()

  private fun createMonth(month: Int) = BpkBarChart.Group(
    title = arrayOf("January", "February", "March", "April", "May", "June", "July")[month % 6],
    items = mutableListOf<BpkBarChart.Column>().apply {
      for (dayOfTheMonth in 0 until 30) {
        add(createBar(month * 30 + dayOfTheMonth))
      }
    }
  )

  private fun createBar(dayOfTheYear: Int) = BpkBarChart.Column(
    title = arrayOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")[dayOfTheYear % 7],
    subtitle = (dayOfTheYear % 30 + 1).toString(),
    badge = "£" + random.nextInt(100),
    value = random.nextInt(120) / 100f,
    inactive = random.nextInt(5) == 0
  )

  companion object {
    private const val LAYOUT_ID = "fragment_id"

    infix fun of(fragmentLayout: Int) = BarChartStory().apply {
      arguments = Bundle()
      arguments?.putInt(LAYOUT_ID, fragmentLayout)
    }
  }
}
