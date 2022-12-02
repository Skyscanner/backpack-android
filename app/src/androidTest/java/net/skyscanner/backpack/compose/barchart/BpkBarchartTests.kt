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

package net.skyscanner.backpack.compose.barchart

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.test.ext.junit.runners.AndroidJUnit4
import net.skyscanner.backpack.BpkSnapshotTest
import net.skyscanner.backpack.BpkTestVariant
import net.skyscanner.backpack.demo.data.BpkBarChartData
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.threeten.bp.Month

@RunWith(AndroidJUnit4::class)
class BpkBarchartTests : BpkSnapshotTest() {

  @Before
  fun setup() {
    setDimensions(400, 400)
  }

  @Test
  fun default() {
    composed {
      BpkBarChart(
        model = BpkBarChartModel(
          caption = "Bar chart",
          items = createMonth(Month.JANUARY, value = 0.0f),
        ),
        selected = null,
        onSelectionChange = {},
      )
    }
  }

  @Test
  fun halfFilled() {
    assumeVariant(BpkTestVariant.Default)
    composed {
      BpkBarChart(
        model = BpkBarChartModel(
          caption = "Bar chart",
          items = createMonth(Month.JANUARY, value = 0.5f),
        ),
        selected = null,
        onSelectionChange = {},
      )
    }
  }

  @Test
  fun fullyFilled() {
    assumeVariant(BpkTestVariant.Default, BpkTestVariant.DarkMode)
    composed {
      BpkBarChart(
        model = BpkBarChartModel(
          caption = "Bar chart",
          items = createMonth(Month.JANUARY, value = 1.0f),
        ),
        selected = null,
        onSelectionChange = {},
      )
    }
  }

  @Test
  fun overfilled() {
    assumeVariant(BpkTestVariant.Default)
    composed {
      BpkBarChart(
        model = BpkBarChartModel(
          caption = "Bar chart",
          items = createMonth(Month.JANUARY, value = 1.1f),
        ),
        selected = null,
        onSelectionChange = {},
      )
    }
  }

  @Test
  fun inactive() {
    assumeVariant(BpkTestVariant.Default, BpkTestVariant.DarkMode)
    composed {
      BpkBarChart(
        model = BpkBarChartModel(
          caption = "Bar chart",
          items = createMonth(Month.JANUARY, inactive = true),
        ),
        selected = null,
        onSelectionChange = {},
      )
    }
  }

  @Test
  fun withLegend() {
    composed {
      BpkBarChart(
        model = BpkBarChartModel(
          caption = "Bar chart",
          legend = BpkBarChartModel.Legend(
            selectedTitle = "Selected",
            activeTitle = "Enabled",
            inactiveTitle = "Disabled",
          ),
          items = createMonth(Month.JANUARY),
        ),
        selected = null,
        onSelectionChange = {},
      )
    }
  }

  @Test
  fun selected() {
    composed {
      var selectedItem by remember { mutableStateOf<BpkBarChartModel.Item?>(null) }
      BpkBarChart(
        model = BpkBarChartModel(
          caption = "Bar chart",
          items = createMonth(Month.JANUARY),
        ),
        selected = selectedItem,
        onSelectionChange = { selectedItem = it },
      )
    }
  }

  private fun createMonth(
    month: Month,
    badge: Int = 100,
    value: Float = 0.5f,
    inactive: Boolean = false,
  ): List<BpkBarChartModel.Item> =
    BpkBarChartData.createMonth(month) {
      BpkBarChartData.createBar(
        date = it,
        badge = badge.toString(),
        value = value,
        inactive = inactive
      )
    }
}
