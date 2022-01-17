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

package net.skyscanner.backpack.barchart.internal

import net.skyscanner.backpack.barchart.BpkBarChart

internal class ChartData(
  groups: List<BpkBarChart.Group>? = null
) {

  private val data: List<Item> =
    groups?.flatMap { group -> group.items.map { Item(group, it) } } ?: emptyList()

  val size: Int
    get() = data.size

  fun getItem(index: Int): BpkBarChart.Column =
    data[index].column

  fun getGroup(index: Int): BpkBarChart.Group =
    data[index].group

  private data class Item(
    val group: BpkBarChart.Group,
    val column: BpkBarChart.Column
  )
}
