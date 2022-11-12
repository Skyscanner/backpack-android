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
 * Represents the view model used to provide data to the bar chart.
 * @param items list of bar items to render.
 * @param legend an optional legend.
 * @param caption an optional caption.
 */
@Immutable
data class BpkBarChartModel(
  val items: List<Item>,
  val legend: Legend? = null,
  val caption: String? = null,
) {

  /**
   * Represents a single bar in the chart.
   * @param key an unique identifier of the bar. This allows to save the selected element each time the model is updated.
   * @param title a primary text placed just below the bar itself.
   * @param subtitle a secondary text placed just below the title.
   * @param badge text to be shown in the popup when the item is selected.
   * @param group name of the group in which the item exists. The name is rendered above the bars and updated as the chart scrolls horizontally.
   * @param inactive indicates whether the item is inactive. The item remains clickable.
   * @param value the value of the bar itself, should be a range between 0.0f and 1.0f. If it exceeds, it'll be clamped
   */
  @Immutable
  data class Item(
    @Stable val key: Any,
    val title: String,
    val subtitle: String,
    val badge: String,
    val group: String,
    val inactive: Boolean,
    @FloatRange(from = 0.0, to = 1.0) val value: Float,
  ) {

    override fun equals(other: Any?): Boolean =
      this === other || (other as? Item)?.key == key

    override fun hashCode(): Int =
      key.hashCode()
  }

  /**
   * Represents a legend for the chart.
   * Two types of legends are available, `active` and `inactive`. Those relate to the `inactive` prop of the bar.
   * `Inactive` bars will have the same style as the `inactive` legend and `active` bars the same style as the
   * `active` legend.
   *
   * @param selectedTitle label to represent selected bars and will use the selected colours from the palette.
   * @param activeTitle label to represent inactive bars and will use the inactive colours from the palette.
   * @param inactiveTitle label to represent active bars and will use the active colours from the palette.
   * @see Item.inactive
   */
  @Immutable
  data class Legend(
    val selectedTitle: String,
    val activeTitle: String,
    val inactiveTitle: String,
  )
}
