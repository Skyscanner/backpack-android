package net.skyscanner.backpack.demo.data

import net.skyscanner.backpack.demo.ComponentDetailFragment
import net.skyscanner.backpack.demo.stories.BadgeFragment
import net.skyscanner.backpack.demo.stories.PanelFragment
import net.skyscanner.backpack.demo.stories.TextFragment

import java.util.ArrayList
import java.util.HashMap

/**
 * Helper class to register the fragments for components
 */
object ComponentRegistry {

  /**
   * An array of sample (dummy) items.
   */
  val ITEMS: MutableList<Component> = ArrayList()

  /**
   * A map of sample (dummy) items, by ID.
   */
  val ITEM_MAP: MutableMap<String, Component> = HashMap()

  init {
    addItem(Component("Panel", PanelFragment::class.java))
    addItem(Component("Badge", BadgeFragment::class.java))
    addItem(Component("Text", TextFragment::class.java))
  }

  private fun addItem(item: Component) {
    ITEMS.add(item)
    ITEM_MAP[item.id] = item
  }

  /**
   * A dummy item representing a piece of content.
   */
  class Component(val id: String, val fragmentClass: Class<out ComponentDetailFragment>)
}
