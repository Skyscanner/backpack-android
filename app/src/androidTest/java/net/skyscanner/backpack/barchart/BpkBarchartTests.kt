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

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import net.skyscanner.backpack.BpkSnapshotTest
import net.skyscanner.backpack.BpkTestVariant
import net.skyscanner.backpack.Variants
import net.skyscanner.backpack.demo.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BpkBarchartTests : BpkSnapshotTest() {

  @get:Rule
  val rule = activityScenarioRule<AppCompatActivity>()

  @Test
  fun default() {
    init {
      model = BpkBarChart.Model(
        groups = listOf(
          createMonth(0, value = 0.0f)
        )
      )
    }
    capture()
  }

  @Test
  @Variants(BpkTestVariant.Default)
  fun halfFilled() {
    init {
      model = BpkBarChart.Model(
        groups = listOf(
          createMonth(0, value = 0.5f)
        )
      )
    }
    capture()
  }

  @Test
  @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode)
  fun fullyFilled() {
    init {
      model = BpkBarChart.Model(
        groups = listOf(
          createMonth(0, value = 1.0f)
        )
      )
    }
    capture()
  }

  @Test
  @Variants(BpkTestVariant.Default)
  fun overfilled() {
    init {
      model = BpkBarChart.Model(
        groups = listOf(
          createMonth(0, value = 1.1f)
        )
      )
    }
    capture()
  }

  @Test
  @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode)
  fun inactive() {
    init {
      model = BpkBarChart.Model(
        groups = listOf(
          createMonth(0, inactive = true)
        )
      )
    }
    capture()
  }

  @Test
  fun withLegend() {
    init {
      model = BpkBarChart.Model(
        groups = listOf(
          createMonth(0)
        ),
        legend = BpkBarChart.Legend(
          selectedTitle = "Selected",
          activeTitle = "Enabled",
          inactiveTitle = "Disabled",
        )
      )
    }
    capture()
  }

  @Test
  fun selected() {
    init {
      model = BpkBarChart.Model(
        groups = listOf(
          createMonth(0)
        )
      )
    }
    capture {
      perform(ViewActions.click())
    }
  }

  @Test
  @Variants(BpkTestVariant.Default)
  fun unselected() {
    init {
      model = BpkBarChart.Model(
        groups = listOf(
          createMonth(0)
        )
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
    var view: View? = null
    onView(ViewMatchers.withId(R.id.bar_chart))
      .block()
      .check { v, _ -> view = v }

    snap(view!!, width = 400)
  }

  private fun createMonth(
    month: Int,
    badge: Int = 100,
    value: Float = 0.5f,
    inactive: Boolean = false
  ) = BpkBarChart.Group(
    title = arrayOf("January", "February", "March", "April", "May", "June", "July")[month % 6],
    items = ArrayList<BpkBarChart.Column>(10).apply {
      for (dayOfTheMonth in 0 until 30) {
        add(
          createBar(
            month * 30 + dayOfTheMonth,
            badge, value, inactive
          )
        )
      }
    }
  )

  private fun createBar(
    dayOfTheYear: Int,
    badge: Int = 100,
    value: Float = 0.5f,
    inactive: Boolean = false
  ) = BpkBarChart.Column(
    title = arrayOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")[dayOfTheYear % 7],
    subtitle = (dayOfTheYear % 30 + 1).toString(),
    badge = "£$badge",
    value = value,
    inactive = inactive
  )
}
