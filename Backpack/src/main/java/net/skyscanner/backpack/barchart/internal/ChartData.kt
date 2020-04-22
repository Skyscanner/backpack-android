package net.skyscanner.backpack.barchart.internal

import net.skyscanner.backpack.barchart.BpkBarChart

internal class ChartData(
  groups: List<BpkBarChart.Group>? = null
) {

  private var data: List<Item> =
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
