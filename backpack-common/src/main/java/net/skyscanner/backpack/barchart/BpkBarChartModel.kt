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

package net.skyscanner.backpack.barchart

import androidx.annotation.FloatRange
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable

/**
 * Represents the view model used to provide data to the chart.
 * @param groups list of bar groups to render.
 * @param legend an optional legend.
 */
@Immutable
data class BpkBarChartModel(
  val groups: List<Group>,
  val legend: Legend? = null
) {

  /**
   * Represents a single bar in the chart.
   * @param id an optional identifier of the bar. This allows to save the selected element each time the model is updated.
   * @param title a primary text placed just below the bar itself.
   * @param subtitle a secondary text placed just below the title.
   * @param badge text to be shown in the popup when the item is selected.
   * @param inactive indicates whether the item is inactive. The item remains clickable.
   * @param value the value of the bar itself, should be a range between 0.0f and 1.0f. If it exceeds, it'll be clamped
   */
  @Immutable
  data class Column(
    @Stable val id: Any,
    val title: String,
    val subtitle: String,
    val badge: String,
    val inactive: Boolean,
    @FloatRange(from = 0.0, to = 1.0) val value: Float,
  ) {

    override fun equals(other: Any?): Boolean {
      if (this === other) return true
      if (javaClass != other?.javaClass) return false

      other as Column

      if (id != other.id) return false

      return true
    }

    override fun hashCode(): Int {
      return id.hashCode()
    }
  }

  /**
   * Represents a group of the items sharing the same title.
   * The title is rendered above the bars and is updated as the chart scrolls horizontally.
   * @param title label of this group to be renderer above the bars.
   * @param items bars in the group.
   */
  @Immutable
  data class Group(
    val title: String,
    val items: List<Column>
  )

  /**
   * Represents a legend for the chart.
   * Two types of legends are available, `active` and `inactive`. Those relate to the `inactive` prop of the bar.
   * `Inactive` bars will have the same style as the `inactive` legend and `active` bars the same style as the
   * `active` legend.
   *
   * @param selectedTitle label to represent selected bars and will use the selected colours from the palette.
   * @param activeTitle label to represent inactive bars and will use the inactive colours from the palette.
   * @param inactiveTitle label to represent active bars and will use the active colours from the palette.
   * @see Column.inactive
   */
  @Immutable
  data class Legend(
    val selectedTitle: String,
    val activeTitle: String,
    val inactiveTitle: String,
  )
}
