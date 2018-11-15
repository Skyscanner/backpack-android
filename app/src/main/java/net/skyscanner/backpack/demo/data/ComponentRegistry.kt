package net.skyscanner.backpack.demo.data

import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.demo.stories.Story
import net.skyscanner.backpack.demo.stories.SubStory
import net.skyscanner.backpack.demo.stories.GradientStory
import net.skyscanner.backpack.demo.stories.DialogStory
import net.skyscanner.backpack.demo.stories.ButtonStory
import net.skyscanner.backpack.demo.stories.IconsStory
import java.lang.IllegalArgumentException

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
    "Dialog" story NodeData({ children -> SubStory.of(children) },
      mapOf(
        "Normal" story NodeData { DialogStory.of(R.layout.fragment_dialog, "Normal") },
        "Warning" story NodeData { DialogStory.of(R.layout.fragment_dialog, "Warning") },
        "Delete" story NodeData { DialogStory.of(R.layout.fragment_dialog, "Delete") },
        "Confirmation" story NodeData { DialogStory.of(R.layout.fragment_dialog, "Confirmation") }
      )),
    "Panel" story NodeData { Story.of(R.layout.fragment_panel) },
    "Badge" story NodeData { Story.of(R.layout.fragment_badge) },
    "Chip" story NodeData({ children -> SubStory.of(children) },
      mapOf(
        "Default" story NodeData { Story.of(R.layout.fragment_chip) },
        "Dismissible" story NodeData { Story.of(R.layout.fragment_chip_dismissible) }
      )),
    "Text" story NodeData({ children -> SubStory.of(children) },
      mapOf(
        "Default" story NodeData { Story.of(R.layout.fragment_text) },
        "Emphasized" story NodeData { Story.of(R.layout.fragment_text_emphasized) },
        "Heavy" story NodeData { Story.of(R.layout.fragment_text_heavy) }
      )),
    "Button" story NodeData({ children -> SubStory.of(children) },
      mapOf(
        "Primary" story NodeData { ButtonStory.of(R.layout.fragment_button, "primary") },
        "Secondary" story NodeData { ButtonStory.of(R.layout.fragment_button, "secondary") },
        "Destructive" story NodeData { ButtonStory.of(R.layout.fragment_button, "destructive") },
        "Featured" story NodeData { ButtonStory.of(R.layout.fragment_button, "featured") }
      )),
    "Card" story NodeData { Story.of(R.layout.fragment_card) },
    "Spinner" story NodeData({ children -> SubStory.of(children) },
      mapOf(
        "Default" story NodeData { Story.of(R.layout.fragment_spinner) },
        "Small" story NodeData { Story.of(R.layout.fragment_spinner_small) }
      ))
  )

  val COMPONENTS by lazy { COMPONENTS_TREE.map { it.value.name } }

  val TOKENS by lazy { TOKENS_MAP.keys.toList() }
  private val TOKENS_MAP = mapOf(
    "Radii" story NodeData { Story.of(R.layout.fragment_radii) },
    "Icons" story NodeData { Story.of(R.layout.fragment_icons) },
    "Gradient" story NodeData { GradientStory() },
    "All Icons" story NodeData { IconsStory() }
  )

  fun getStoryCreator(fullyQualifiedName: String): RegistryItem {
    val parts = fullyQualifiedName.split(" - ")
    val first = parts[0]
    val rest = parts.drop(1)

    val token = TOKENS_MAP[fullyQualifiedName]
    if (token != null) {
      return token
    }

    return rest.fold(COMPONENTS_TREE[first]!!, { result, item ->
      return result.subItems[item]
        ?: throw IllegalArgumentException("Invalid story name - $fullyQualifiedName")
    })
  }

  fun getStoryName(fullyQualifiedName: String): String {
    return fullyQualifiedName.split(" - ").last()
  }
}
