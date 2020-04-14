package net.skyscanner.backpack.barchart.internal

import net.skyscanner.backpack.barchart.BpkBarChartView

internal class ChartData(
  groups: List<BpkBarChartView.Group>? = null
) {

  private var data: List<Item> =
    groups?.flatMap { group -> group.items.map { Item(group, it) } } ?: emptyList()

  val size: Int
    get() = data.size

  fun getItem(index: Int): BpkBarChartView.Bar =
    data[index].bar

  fun getGroup(index: Int): BpkBarChartView.Group =
    data[index].group

  private data class Item(
    val group: BpkBarChartView.Group,
    val bar: BpkBarChartView.Bar
  )
}
