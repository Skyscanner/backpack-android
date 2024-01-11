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

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithText
import net.skyscanner.backpack.BpkTestVariant
import net.skyscanner.backpack.Variants
import net.skyscanner.backpack.compose.BpkSnapshotTest
import net.skyscanner.backpack.demo.data.BpkBarChartData
import org.junit.Test
import org.threeten.bp.Month
import kotlin.math.roundToInt

class BpkBarchartTest : BpkSnapshotTest() {

    @Test
    fun default() {
        snap {
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
    @Variants(BpkTestVariant.Default)
    fun halfFilled() {
        snap {
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
    @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode)
    fun fullyFilled() {
        snap {
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
    @Variants(BpkTestVariant.Default)
    fun overfilled() {
        snap {
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
    @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode)
    fun inactive() {
        snap {
            BpkBarChart(
                model = BpkBarChartModel(
                    caption = "Bar chart",
                    items = createMonth(Month.JANUARY, value = null),
                ),
                selected = null,
                onSelectionChange = {},
            )
        }
    }

    @Test
    fun withLegend() {
        snap {
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
        val model = BpkBarChartModel(caption = "Bar chart", items = createMonth(Month.JANUARY))
        snap(assertion = { onNodeWithText(model.items[9].values?.text!!).assertIsDisplayed() }) {
            BpkBarChart(
                model = model,
                selected = model.items[9],
                onSelectionChange = {},
            )
        }
    }

    private fun createMonth(
        month: Month,
        value: Float? = 0.5f,
    ): List<BpkBarChartModel.Item> =
        BpkBarChartData.createMonth(month, testContext.resources) { date ->
            BpkBarChartData.createBar(
                date = date,
                resources = testContext.resources,
                values = value?.let {
                    BpkBarChartModel.Values(
                        percent = it,
                        text = (it * 100).roundToInt().toString(),
                    )
                },
            )
        }
}
