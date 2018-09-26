package net.skyscanner.backpack.demo.data

import net.skyscanner.backpack.demo.ComponentDetailFragment
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.demo.stories.ButtonStory
import net.skyscanner.backpack.demo.stories.IconsStory
import net.skyscanner.backpack.demo.stories.Story

/**
 * Helper class to register the fragments for components
 */
object ComponentRegistry {

  val COMPONENTS by lazy { COMPONENT_MAP.keys.toList() }
  private val COMPONENT_MAP = mapOf(
    "Panel" to { Story.of(R.layout.fragment_panel) },
    "Badge" to { Story.of(R.layout.fragment_badge) },
    "Text" to { Story.of(R.layout.fragment_text) },
    "Button Primary" to { ButtonStory.of(R.layout.fragment_button,"primary")},
    "Button Secondary" to { ButtonStory.of(R.layout.fragment_button,"secondary")},
    "Button Destructive" to { ButtonStory.of(R.layout.fragment_button,"destructive")},
    "Button Featured" to { ButtonStory.of(R.layout.fragment_button,"featured")}
  )

  val TOKENS by lazy { TOKENS_MAP.keys.toList() }
  private val TOKENS_MAP = mapOf(
    "Radii" to { Story.of(R.layout.fragment_radii) },
    "Icons" to { Story.of(R.layout.fragment_icons) },
    "All Icons" to { IconsStory() }
  )

  val ALL by lazy {
    val all = mutableMapOf<String, () -> ComponentDetailFragment>()
    all.putAll(TOKENS_MAP)
    all.putAll(COMPONENT_MAP)
    all
  }
}
