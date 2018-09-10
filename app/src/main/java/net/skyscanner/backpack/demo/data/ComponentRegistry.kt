package net.skyscanner.backpack.demo.data

import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.demo.stories.Story

/**
 * Helper class to register the fragments for components
 */
object ComponentRegistry {

  val ITEMS get() = ITEM_MAP.keys.toList()

  val ITEM_MAP = mapOf(
    "Panel" to { Story.of(R.layout.fragment_panel) },
    "Badge" to { Story.of(R.layout.fragment_badge) },
    "Text" to { Story.of(R.layout.fragment_text) }
  )
}
