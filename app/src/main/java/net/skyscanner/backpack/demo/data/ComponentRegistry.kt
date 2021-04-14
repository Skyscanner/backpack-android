/**
 * Backpack for Android - Skyscanner's Design System
 *
 * Copyright 2018-2021 Skyscanner Ltd
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

package net.skyscanner.backpack.demo.data

import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.demo.stories.BarChartStory
import net.skyscanner.backpack.demo.stories.BottomNavStory
import net.skyscanner.backpack.demo.stories.ChangeableButtonsStory
import net.skyscanner.backpack.demo.stories.ChipStory
import net.skyscanner.backpack.demo.stories.ColorStory
import net.skyscanner.backpack.demo.stories.ColoredCalendarStory
import net.skyscanner.backpack.demo.stories.DefaultCalendarStory
import net.skyscanner.backpack.demo.stories.DialogStory
import net.skyscanner.backpack.demo.stories.DisabledCalendarStory
import net.skyscanner.backpack.demo.stories.ElevationStory
import net.skyscanner.backpack.demo.stories.FooterViewCalendarStory
import net.skyscanner.backpack.demo.stories.GradientStoryCustom
import net.skyscanner.backpack.demo.stories.GradientStoryPrimary
import net.skyscanner.backpack.demo.stories.GradientStoryWithDirection
import net.skyscanner.backpack.demo.stories.HorizontalNavStory
import net.skyscanner.backpack.demo.stories.IconType
import net.skyscanner.backpack.demo.stories.IconsStory
import net.skyscanner.backpack.demo.stories.InteractiveStarRatingStory
import net.skyscanner.backpack.demo.stories.LabeledCalendarStory
import net.skyscanner.backpack.demo.stories.LoadingButtonStory
import net.skyscanner.backpack.demo.stories.MapStory
import net.skyscanner.backpack.demo.stories.NavBarStory
import net.skyscanner.backpack.demo.stories.SnackbarStory
import net.skyscanner.backpack.demo.stories.SpacingStory
import net.skyscanner.backpack.demo.stories.Story
import net.skyscanner.backpack.demo.stories.Story.Companion.Direction
import net.skyscanner.backpack.demo.stories.Story.Companion.scrollable
import net.skyscanner.backpack.demo.stories.Story.Companion.with
import net.skyscanner.backpack.demo.stories.StyleableButtonStory
import net.skyscanner.backpack.demo.stories.SubStory
import net.skyscanner.backpack.demo.stories.TextSpansStory
import net.skyscanner.backpack.demo.stories.ToastStory

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
  items: Map<String, RegistryItem> = emptyMap(),
) : RegistryItem {

  private var parent: RegistryItem? = null
  val subItems = items.mapValues {
    it.value.setParent(this)
    it.value
  }

  override fun createStory() = creator(subItems.map { it.value.getFullyQualifiedName() }.toTypedArray())

  override fun getParent() = parent
  override fun setParent(parent: RegistryItem) {
    this.parent = parent
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
  val items: Map<String, RegistryItem> = emptyMap(),
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
    "Bar Chart" story NodeData { BarChartStory of R.layout.fragment_bar_chart },
    "Bottom Nav" story NodeData { BottomNavStory of R.layout.fragment_bottom_nav },
    "Bottom Sheet" story NodeData { Story of R.layout.fragment_bottom_sheet scrollable false },
    "Button" story NodeData(
      { children -> SubStory of children },
      mapOf(
        "Primary" story NodeData { Story of R.layout.fragment_button_primary },
        "Secondary" story NodeData { Story of R.layout.fragment_button_secondary },
        "Destructive" story NodeData { Story of R.layout.fragment_button_destructive },
        "Featured" story NodeData { Story of R.layout.fragment_button_featured },
        "Outline" story NodeData { Story of R.layout.fragment_button_outline },
        "Icon alignment" story NodeData { Story of R.layout.fragment_button_icon_alignment },
        "Icon alignment with RTL" story NodeData { Story of R.layout.fragment_button_icon_alignment with Direction.RTL },
        "Loading" story NodeData { LoadingButtonStory of R.layout.fragment_button_loading },
        "Changeable" story NodeData { ChangeableButtonsStory of R.layout.fragment_buttons_changeable },
        "Styleable" story NodeData { StyleableButtonStory of R.layout.fragment_buttons_styleable }
      )
    ),
    "ButtonLink" story NodeData(
      { children -> SubStory of children },
      mapOf(
        "Default" story NodeData { Story of R.layout.fragment_button_link },
        "Icon alignment" story NodeData { Story of R.layout.fragment_button_link_icon_alignment }
      )
    ),
    "Card" story NodeData(
      { children -> SubStory of children },
      mapOf(
        "Default" story NodeData { Story of R.layout.fragment_card },
        "Without padding" story NodeData { Story of R.layout.fragment_card_without_padding },
        "Selected" story NodeData { Story of R.layout.fragment_card_selected },
        "Corner style large" story NodeData { Story of R.layout.fragment_card_cornerstyle_large },
        "With divider" story NodeData { Story of R.layout.fragment_card_with_divider },
        "With divider arranged vertically" story NodeData { Story of R.layout.fragment_card_with_divider_vertical },
        "With divider without padding" story NodeData { Story of R.layout.fragment_card_with_divider_no_padding },
        "With divider and corner style large" story NodeData {
          Story of R.layout.fragment_card_with_divider_cornerstyle_large
        }
      )
    ),
    "Calendar" story NodeData(
      { children -> SubStory of children },
      mapOf(
        "Default" story NodeData { DefaultCalendarStory of R.layout.fragment_calendar_default },
        "Colored" story NodeData { ColoredCalendarStory of R.layout.fragment_calendar_colored },
        "Disabled Dates" story NodeData { DisabledCalendarStory of R.layout.fragment_calendar_disabled },
        "Footer view" story NodeData { FooterViewCalendarStory of R.layout.fragment_calendar_footer_view },
        "Footer view RTL" story NodeData {
          FooterViewCalendarStory of R.layout.fragment_calendar_footer_view with Direction.RTL
        },
        "Labeled" story NodeData { LabeledCalendarStory of R.layout.fragment_calendar_default },
      )
    ),
    "Chip" story NodeData(
      { children -> SubStory of children },
      mapOf(
        "Default" story NodeData { ChipStory of R.layout.fragment_chip },
        "Outline" story NodeData { ChipStory of R.layout.fragment_outline_chip },
        "Custom" story NodeData { ChipStory of R.layout.fragment_chip_custom },
        "With icon" story NodeData { ChipStory of R.layout.fragment_chip_with_icon },
        "With icon RTL" story NodeData { ChipStory of R.layout.fragment_chip_with_icon with Direction.RTL }
      )
    ),
    "Checkbox" story NodeData { Story of R.layout.fragment_checkbox },
    "Dialog" story NodeData(
      { children -> SubStory of children },
      mapOf(
        "With call to action" story NodeData { DialogStory of "Normal" },
        "Warning" story NodeData { DialogStory of "Warning" },
        "Delete confirmation" story NodeData { DialogStory of "Delete" },
        "Success" story NodeData { DialogStory of "Confirmation" },
        "With Links" story NodeData { DialogStory of "Links" },
        "Long Text" story NodeData { DialogStory of "Long" },
        "Flare" story NodeData { DialogStory of "Flare" },
        "Flare with image" story NodeData { DialogStory of "FlareWithImage" }
      )
    ),
    "Flare" story NodeData(
      { children -> SubStory of children },
      mapOf(
        "Default" story NodeData { Story of R.layout.fragment_flare },
        "Pointing up" story NodeData { Story of R.layout.fragment_flare_up },
        "Pointer offset" story NodeData { Story of R.layout.fragment_flare_pointer_offset },
        "Pointer offset RTL" story NodeData { Story of R.layout.fragment_flare_pointer_offset with Direction.RTL },
        "Rounded" story NodeData { Story of R.layout.fragment_flare_rounded },
        "Inset padding mode" story NodeData { Story of R.layout.fragment_flare_inset_padding_mode }
      )
    ),
    "Horizontal Nav" story NodeData { HorizontalNavStory of R.layout.fragment_horizontal_nav_default },
    "Floating Action Button" story NodeData { Story of R.layout.fragment_fab },
    "Map Markers" story NodeData(
      { children -> SubStory of children },
      mapOf(
        "Pointers" story NodeData { MapStory of MapStory.Type.PointersOnly },
        "Badges " story NodeData { MapStory of MapStory.Type.Badges },
        "With icons" story NodeData { MapStory of MapStory.Type.BadgesWithIcons },
      )
    ),
    "Nav Bar" story NodeData(
      { children -> SubStory of children },
      mapOf(
        "Default" story NodeData { NavBarStory of R.layout.fragment_nav_bar },
        "RTL" story NodeData { NavBarStory of R.layout.fragment_nav_bar with Direction.RTL },
        "With Icon" story NodeData { NavBarStory of R.layout.fragment_nav_bar_with_icon },
        "With Icon RTL" story NodeData { NavBarStory of R.layout.fragment_nav_bar_with_icon with Direction.RTL },
        "With Menu" story NodeData { NavBarStory of R.layout.fragment_nav_bar_with_menu },
        "With Menu RTL" story NodeData { NavBarStory of R.layout.fragment_nav_bar_with_menu with Direction.RTL }
      )
    ),
    "Overlay" story NodeData { Story of R.layout.fragment_overlay },
    "Panel" story NodeData { Story of R.layout.fragment_panel },
    "RadioButton" story NodeData { Story of R.layout.fragment_radio_button },
    "Rating" story NodeData(
      { children -> SubStory of children },
      mapOf(
        "Default" story NodeData { Story of R.layout.fragment_rating_default },
        "Icons" story NodeData { Story of R.layout.fragment_rating_icons },
        "No selectors" story NodeData { Story of R.layout.fragment_rating_no_selectors },
        "Horizontal" story NodeData { Story of R.layout.fragment_rating_sizes },
        "Horizontal RTL" story NodeData { Story of R.layout.fragment_rating_sizes with Direction.RTL },
        "Vertical" story NodeData { Story of R.layout.fragment_rating_sizes_vertical },
        "Pill" story NodeData { Story of R.layout.fragment_rating_sizes_pill }
      )
    ),
    "Snackbar" story NodeData { SnackbarStory of R.layout.fragment_snackbar },
    "Spinner" story NodeData(
      { children -> SubStory of children },
      mapOf(
        "Default" story NodeData { Story of R.layout.fragment_spinner },
        "Small" story NodeData { Story of R.layout.fragment_spinner_small }
      )
    ),
    "Star Rating" story NodeData(
      { children -> SubStory of children },
      mapOf(
        "Default" story NodeData { Story of R.layout.fragment_star_rating_default },
        "RTL" story NodeData { Story of R.layout.fragment_star_rating_default with Direction.RTL },
        "Different values" story NodeData { Story of R.layout.fragment_star_rating_values },
        "Custom Max Rating" story NodeData { Story of R.layout.fragment_star_rating_max }
      )
    ),
    "Star Rating Interactive" story NodeData { InteractiveStarRatingStory of R.layout.fragment_star_rating_interactive },
    "Switch" story NodeData { Story of R.layout.fragment_switch },
    "Text" story NodeData(
      { children -> SubStory of children },
      mapOf(
        "Default" story NodeData { Story of R.layout.fragment_text },
        "Emphasized" story NodeData { Story of R.layout.fragment_text_emphasized },
        "Heavy" story NodeData { Story of R.layout.fragment_text_heavy },
        "With drawables" story NodeData { Story of R.layout.fragment_text_drawables },
        "With links" story NodeData { Story of R.layout.fragment_text_links }
      )
    ),
    "Text Field" story NodeData { Story of R.layout.fragment_text_fields },
    "Text Spans" story NodeData { TextSpansStory of R.layout.fragment_text_spans },
    "Toast" story NodeData { ToastStory of R.layout.fragment_toasts }
  )

  private val TOKENS_MAP = mapOf(
    "All Icons" story NodeData(
      { children -> SubStory of children },
      mapOf(
        "Default" story NodeData { IconsStory of IconType.Default },
        "RTL" story NodeData { IconsStory of IconType.Default with Direction.RTL },
        "Small" story NodeData { IconsStory of IconType.Small },
        "Small RTL" story NodeData { IconsStory of IconType.Small with Direction.RTL }
      )
    ),
    "Color" story NodeData { ColorStory() },
    "Elevation" story NodeData { ElevationStory() },
    "Gradient" story NodeData(
      { children -> SubStory of children },
      mapOf(
        "Primary" story NodeData { GradientStoryPrimary() },
        "With direction" story NodeData { GradientStoryWithDirection() },
        "Custom" story NodeData { GradientStoryCustom() }
      )
    ),
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
