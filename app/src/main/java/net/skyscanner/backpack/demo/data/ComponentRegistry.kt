package net.skyscanner.backpack.demo.data

import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.demo.stories.*

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
        "Primary" story NodeData { Story of R.layout.fragment_button_primary },
        "Secondary" story NodeData { Story of R.layout.fragment_button_secondary },
        "Destructive" story NodeData { Story of R.layout.fragment_button_destructive },
        "Featured" story NodeData { Story of R.layout.fragment_button_featured },
        "Outline" story NodeData { Story of R.layout.fragment_button_outline },
        "With rounder corners" story NodeData { Story of R.layout.fragment_button_with_corners },
        "Icon alignment" story NodeData { Story of R.layout.fragment_button_icon_alignment },
        "Icon alignment with RTL" story NodeData { Story of R.layout.fragment_button_icon_alignment_rtl }
      )),
    "ButtonLink" story NodeData({ children -> SubStory of children },
      mapOf(
        "Default" story NodeData { Story of R.layout.fragment_button_link },
        "Icon alignment" story NodeData { Story of R.layout.fragment_button_link_icon_alignment }
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
    "Calendar" story NodeData({ children -> SubStory of children },
      mapOf(
        "Default" story NodeData { DefaultCalendarStory of R.layout.fragment_calendar_default },
        "Colored" story NodeData { ColoredCalendarStory of R.layout.fragment_calendar_colored }
      )),
    "Chip" story NodeData { ChipStory() },
    "Checkbox" story NodeData { Story of R.layout.fragment_checkbox },
    "Dialog" story NodeData({ children -> SubStory of children },
      mapOf(
        "With call to action" story NodeData { DialogStory of "Normal" },
        "Warning" story NodeData { DialogStory of "Warning" },
        "Delete confirmation" story NodeData { DialogStory of "Delete" },
        "Success" story NodeData { DialogStory of "Confirmation" },
        "With Links" story NodeData { DialogStory of "Links" }
      )),
    "Horizontal Nav" story NodeData { HorizontalNavStory of R.layout.fragment_horizontal_nav_default },
    "Floating Action Button" story NodeData { Story of R.layout.fragment_fab },
    "Panel" story NodeData { Story of R.layout.fragment_panel },
    "Rating" story NodeData({ children -> SubStory of children },
      mapOf(
        "Default" story NodeData { Story of R.layout.fragment_rating_default },
        "Icons" story NodeData { Story of R.layout.fragment_rating_icons },
        "No selectors" story NodeData { Story of R.layout.fragment_rating_no_selectors },
        "Sizes" story NodeData { Story of R.layout.fragment_rating_sizes },
        "Sizes RTL" story NodeData { Story ofRtl R.layout.fragment_rating_sizes },
        "Size Vertical" story NodeData { Story of R.layout.fragment_rating_sizes_vertical }
      )),
    "Spinner" story NodeData({ children -> SubStory of children },
      mapOf(
        "Default" story NodeData { Story of R.layout.fragment_spinner },
        "Small" story NodeData { Story of R.layout.fragment_spinner_small }
      )),
    "Star Rating" story NodeData({ children -> SubStory of children },
      mapOf(
        "Default" story NodeData { Story of R.layout.fragment_star_rating_default },
        "RTL" story NodeData { Story of R.layout.fragment_star_rating_rtl },
        "Different values" story NodeData { Story of R.layout.fragment_star_rating_values },
        "Custom Max Rating" story NodeData { Story of R.layout.fragment_star_rating_max }
      )),
    "Star Rating: Interactive" story NodeData { InteractiveStarRatingStory of R.layout.fragment_star_rating_interactive },
    "Switch" story NodeData { Story of R.layout.fragment_switch },
    "Text" story NodeData({ children -> SubStory of children },
      mapOf(
        "Default" story NodeData { Story of R.layout.fragment_text },
        "Emphasized" story NodeData { Story of R.layout.fragment_text_emphasized },
        "Heavy" story NodeData { Story of R.layout.fragment_text_heavy },
        "With drawables" story NodeData { Story of R.layout.fragment_text_drawables }
      )),
    "Text Field" story NodeData { Story of R.layout.fragment_text_fields },
    "Text Spans" story NodeData { TextSpansStory of R.layout.fragment_text_spans },
    "Toast" story NodeData { ToastStory of R.layout.fragment_toasts }
  )

  private val TOKENS_MAP = mapOf(
    "All Icons" story NodeData { IconsStory() },
    "Color" story NodeData { ColorStory() },
    "Elevation" story NodeData { ElevationStory() },
    "Gradient" story NodeData({ children -> SubStory of children },
      mapOf(
        "Primary" story NodeData { GradientStoryPrimary() },
        "With direction" story NodeData { GradientStoryWithDirection() },
        "Custom" story NodeData { GradientStoryCustom() }
      )),
    "Icons" story NodeData { Story of R.layout.fragment_icons },
    "Radii" story NodeData { Story of R.layout.fragment_radii },
    "Spacing" story NodeData { SpacingStory() }
  )

  val COMPONENTS = COMPONENTS_TREE.map { it.value.name }

  val TOKENS = TOKENS_MAP.keys.toList()

  fun getStoryCreator(fullyQualifiedName: String): RegistryItem {
    val parts = fullyQualifiedName.split(" - ")
    val first = parts[0]
    val rest = parts.drop(1)

    val story = TOKENS_MAP[first] ?: COMPONENTS_TREE[first]
    story ?: throw IllegalArgumentException("Invalid story name - $fullyQualifiedName")

    return rest.fold(story) { result, item ->
      return result.subItems[item]
        ?: throw IllegalArgumentException("Invalid story name - $fullyQualifiedName")
    }
  }

  fun getStoryName(fullyQualifiedName: String): String {
    return fullyQualifiedName.split(" - ").last()
  }
}
