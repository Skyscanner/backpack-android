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

package net.skyscanner.backpack.demo.data

import net.skyscanner.backpack.barchart.BpkBarChartModel
import java.util.Random

object BarChartData {

  fun generateModel(): BpkBarChartModel =
    BpkBarChartModel(
      groups = listOf(
        createMonth(0),
        createMonth(1),
        createMonth(2),
        createMonth(3),
        createMonth(4),
        createMonth(5)
      ),
      legend = BpkBarChartModel.Legend(
        selectedTitle = "Selected",
        inactiveTitle = "No Price",
        activeTitle = "Price",
      )
    )

  private val random = Random(18735)

  private fun createMonth(month: Int) = BpkBarChartModel.Group(
    title = arrayOf("January", "February", "March", "April", "May", "June", "July")[month % 6],
    items = mutableListOf<BpkBarChartModel.Column>().apply {
      for (dayOfTheMonth in 0 until 30) {
        add(createBar(month * 30 + dayOfTheMonth))
      }
    }
  )

  private fun createBar(dayOfTheYear: Int) = BpkBarChartModel.Column(
    title = arrayOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")[dayOfTheYear % 7],
    subtitle = (dayOfTheYear % 30 + 1).toString(),
    badge = "£" + random.nextInt(100),
    value = random.nextInt(100) / 100f,
    inactive = random.nextInt(5) == 0,
    id = dayOfTheYear, // todo: use proper ids
  )
}
