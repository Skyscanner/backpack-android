/**
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

package net.skyscanner.backpack.barchart

import androidx.appcompat.app.AppCompatActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import net.skyscanner.backpack.BpkSnapshotTest
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.demo.data.BpkBarChartData
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.threeten.bp.Month

@RunWith(AndroidJUnit4::class)
class BpkBarchartTests : BpkSnapshotTest() {

  @get:Rule
  val rule = activityScenarioRule<AppCompatActivity>()

  @Before
  fun setup() {
    setDimensions(400, 400)
  }

  @Test
  fun screenshotTestBarChart_Empty() {
    init {
      model = BpkBarChartModel(
        items = createMonth(Month.JANUARY, value = 0.0f),
      )
    }
    capture()
  }

  @Test
  fun screenshotTestBarChart_Half() {
    init {
      model = BpkBarChartModel(
        items = createMonth(Month.JANUARY, value = 0.5f),
      )
    }
    capture()
  }

  @Test
  fun screenshotTestBarChart_Full() {
    init {
      model = BpkBarChartModel(
        items = createMonth(Month.JANUARY, value = 1.0f),
      )
    }
    capture()
  }

  @Test
  fun screenshotTestBarChart_Overfilled() {
    init {
      model = BpkBarChartModel(
        items = createMonth(Month.JANUARY, value = 1.1f),
      )
    }
    capture()
  }

  @Test
  fun screenshotTestBarChart_Inactive() {
    init {
      model = BpkBarChartModel(
        items = createMonth(Month.JANUARY, inactive = true),
      )
    }
    capture()
  }

  @Test
  fun screenshotTestBarChart_WithLegend() {
    init {
      model = BpkBarChartModel(
        items = createMonth(Month.JANUARY),
        legend = BpkBarChartModel.Legend(
          selectedTitle = "Selected",
          activeTitle = "Enabled",
          inactiveTitle = "Disabled",
        )
      )
    }
    capture()
  }

  @Test
  fun screenshotTestBarChart_WithBadge() {
    init {
      model = BpkBarChartModel(
        items = createMonth(Month.JANUARY),
      )
    }
    capture {
      perform(ViewActions.click())
    }
  }

  @Test
  fun screenshotTestBarChart_WithoutBadge() {
    init {
      model = BpkBarChartModel(
        items = createMonth(Month.JANUARY),
      )
    }
    capture {
      perform(ViewActions.click())
      perform(ViewActions.click())
    }
  }

  private inline fun init(crossinline block: BpkBarChart.() -> Unit) {
    rule.scenario.onActivity { activity ->
      activity.runOnUiThread {
        activity.setContentView(R.layout.fragment_bar_chart)
        val barChart = activity.findViewById<BpkBarChart>(R.id.bar_chart)
        block(barChart)
      }
    }
  }

  private inline fun capture(crossinline block: ViewInteraction.() -> ViewInteraction = { this }) {
    val asyncSnapshot = prepareForAsyncTest()
    onView(ViewMatchers.withId(R.id.bar_chart))
      .block()
      .check { v, _ ->
        asyncSnapshot.record(v)
      }
  }

  private fun createMonth(
    month: Month,
    badge: Int = 100,
    value: Float = 0.5f,
    inactive: Boolean = false,
  ): List<BpkBarChartModel.Item> =
    BpkBarChartData.createMonth(month) {
      BpkBarChartData.createBar(it, badge.toString(), value, inactive)
    }
}
