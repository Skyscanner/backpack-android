package net.skyscanner.backpack.demo.data

import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.demo.stories.ButtonStory
import net.skyscanner.backpack.demo.stories.CalendarStory
import net.skyscanner.backpack.demo.stories.DialogStory
import net.skyscanner.backpack.demo.stories.GradientStory
import net.skyscanner.backpack.demo.stories.IconsStory
import net.skyscanner.backpack.demo.stories.Story
import net.skyscanner.backpack.demo.stories.SubStory
import net.skyscanner.backpack.demo.stories.ColorStory

interface RegistryItem {
  val name: String
  fun getParent(): RegistryItem?
  fun setParent(parent: RegistryItem)
  fun createStory(): Story
  fun getFullyQualifiedName(): String
}

class NodeItem(
  override val name: String,
  private val creator: (items: Array<String>) -> Story,
  items: Map<String, RegistryItem> = emptyMap()
) : RegistryItem {

  private var parent: RegistryItem? = null
  val subItems = items.mapValues {
    it.value.setParent(this)
    it.value
  }

  override fun createStory() = creator(subItems.map { it.value.getFullyQualifiedName() }.toTypedArray())

  override fun getParent() = parent
  override fun setParent(newParent: RegistryItem) {
    parent = newParent
  }

  override fun getFullyQualifiedName(): String {
    var parent = this.parent
    var fullName = this.name

    while (parent != null) {
      fullName = "${parent.name} - $name"
      parent = parent.getParent()
    }

    return fullName
  }
}

private class NodeData(
  val creator: (items: Array<String>) -> Story,
  val items: Map<String, RegistryItem> = emptyMap()
) {

  constructor(creator: () -> Story) : this({ _ -> creator() })
}

private infix fun String.story(story: NodeData): Pair<String, NodeItem> {
  return Pair(this, NodeItem(this, story.creator, story.items))
}

/**
 * Helper class to register the fragments for components
 */
object ComponentRegistry {

  private val COMPONENTS_TREE = mapOf(
    "Badge" story NodeData { Story of R.layout.fragment_badge },
    "Button" story NodeData({ children -> SubStory of children },
      mapOf(
        "Primary" story NodeData { ButtonStory of "primary" },
        "Secondary" story NodeData { ButtonStory of "secondary" },
        "Destructive" story NodeData { ButtonStory of "destructive" },
        "Featured" story NodeData { ButtonStory of "featured" },
        "Outline" story NodeData { ButtonStory of "outline" }
      )),
    "Card" story NodeData({ children -> SubStory of children },
      mapOf(
        "Default" story NodeData { Story of R.layout.fragment_card },
        "Without padding" story NodeData { Story of R.layout.fragment_card_without_padding },
        "Selected" story NodeData { Story of R.layout.fragment_card_selected },
        "Corner style large" story NodeData { Story of R.layout.fragment_card_cornerstyle_large },
        "With divider" story NodeData { Story of R.layout.fragment_card_with_divider },
        "With divider arranged vertically" story NodeData { Story of R.layout.fragment_card_with_divider_vertical },
        "With divider without padding" story NodeData { Story of R.layout.fragment_card_with_divider_no_padding },
        "With divider and corner style large" story NodeData { Story of R.layout.fragment_card_with_divider_cornerstyle_large }
      )),
    "Calendar" story NodeData { CalendarStory of R.layout.fragment_calendar },
    "Chip" story NodeData { Story of R.layout.fragment_chip },
    "Dialog" story NodeData({ children -> SubStory of children },
      mapOf(
        "With call to action" story NodeData { DialogStory of "Normal" },
        "Warning" story NodeData { DialogStory of "Warning" },
        "Delete confirmation" story NodeData { DialogStory of "Delete" },
        "Success" story NodeData { DialogStory of "Confirmation" }
      )),
    "Panel" story NodeData { Story of R.layout.fragment_panel },
    "Spinner" story NodeData({ children -> SubStory of children },
      mapOf(
        "Default" story NodeData { Story of R.layout.fragment_spinner },
        "Small" story NodeData { Story of R.layout.fragment_spinner_small }
      )),
    "Switch" story NodeData { Story of R.layout.fragment_switch },
    "Text" story NodeData({ children -> SubStory of children },
      mapOf(
        "Default" story NodeData { Story of R.layout.fragment_text },
        "Emphasized" story NodeData { Story of R.layout.fragment_text_emphasized },
        "Heavy" story NodeData { Story of R.layout.fragment_text_heavy },
        "With drawables" story NodeData { Story of R.layout.fragment_text_drawables }
      ))
  )

  val COMPONENTS by lazy { COMPONENTS_TREE.map { it.value.name } }

  val TOKENS by lazy { TOKENS_MAP.keys.toList() }
  private val TOKENS_MAP = mapOf(
    "All Icons" story NodeData { IconsStory() },
    "Color" story NodeData { ColorStory() },
    "Gradient" story NodeData { GradientStory() },
    "Icons" story NodeData { Story of R.layout.fragment_icons },
    "Radii" story NodeData { Story of R.layout.fragment_radii }
  )

  fun getStoryCreator(fullyQualifiedName: String): RegistryItem {
    val parts = fullyQualifiedName.split(" - ")
    val first = parts[0]
    val rest = parts.drop(1)

    val token = TOKENS_MAP[fullyQualifiedName]
    if (token != null) {
      return token
    }

    return rest.fold(COMPONENTS_TREE[first]!!) { result, item ->
      return result.subItems[item]
        ?: throw IllegalArgumentException("Invalid story name - $fullyQualifiedName")
    }
  }

  fun getStoryName(fullyQualifiedName: String): String {
    return fullyQualifiedName.split(" - ").last()
  }
}
