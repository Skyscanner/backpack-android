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

package net.skyscanner.backpack.demo.data

import androidx.compose.runtime.Composable
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.demo.compose.BadgeStory
import net.skyscanner.backpack.demo.compose.BodyStyleStory
import net.skyscanner.backpack.demo.compose.BottomSheetStory
import net.skyscanner.backpack.demo.compose.ButtonDrawableIconStory
import net.skyscanner.backpack.demo.compose.ButtonLinkStory
import net.skyscanner.backpack.demo.compose.ButtonsDefaultSizeStory
import net.skyscanner.backpack.demo.compose.ButtonsLargeSizeStory
import net.skyscanner.backpack.demo.compose.CalendarDayLabels
import net.skyscanner.backpack.demo.compose.CalendarDisabledWeekends
import net.skyscanner.backpack.demo.compose.CalendarPreSelectedRange
import net.skyscanner.backpack.demo.compose.CalendarSelectionDisabledStory
import net.skyscanner.backpack.demo.compose.CalendarSelectionRangeStory
import net.skyscanner.backpack.demo.compose.CalendarSelectionSingleStory
import net.skyscanner.backpack.demo.compose.CalendarSelectionWholeMonthStory
import net.skyscanner.backpack.demo.compose.CardButtonDefaultStory
import net.skyscanner.backpack.demo.compose.CardButtonSmallStory
import net.skyscanner.backpack.demo.compose.CardStory
import net.skyscanner.backpack.demo.compose.CardWrapperStory
import net.skyscanner.backpack.demo.compose.CheckboxStory
import net.skyscanner.backpack.demo.compose.ChipStoryDefault
import net.skyscanner.backpack.demo.compose.ChipStoryOnDark
import net.skyscanner.backpack.demo.compose.ChipStoryOnImage
import net.skyscanner.backpack.demo.compose.CollapsibleNavigationBarStory
import net.skyscanner.backpack.demo.compose.ColorsComposeStory
import net.skyscanner.backpack.demo.compose.DividedCardStory
import net.skyscanner.backpack.demo.compose.DividerStory
import net.skyscanner.backpack.demo.compose.ElevationComposeStory
import net.skyscanner.backpack.demo.compose.FabStory
import net.skyscanner.backpack.demo.compose.FieldSetStory
import net.skyscanner.backpack.demo.compose.FlareStory
import net.skyscanner.backpack.demo.compose.FloatingNotificationStory
import net.skyscanner.backpack.demo.compose.HeadingStyleStory
import net.skyscanner.backpack.demo.compose.HeroStyleStory
import net.skyscanner.backpack.demo.compose.IconsStoryCompose
import net.skyscanner.backpack.demo.compose.NavigationBarStory
import net.skyscanner.backpack.demo.compose.NudgerStory
import net.skyscanner.backpack.demo.compose.PageIndicatorStory
import net.skyscanner.backpack.demo.compose.PanelStory
import net.skyscanner.backpack.demo.compose.PriceLargeStory
import net.skyscanner.backpack.demo.compose.PriceSmallStory
import net.skyscanner.backpack.demo.compose.RadiiComposeStory
import net.skyscanner.backpack.demo.compose.RadioButtonStory
import net.skyscanner.backpack.demo.compose.RatingStory
import net.skyscanner.backpack.demo.compose.SkeletonStory
import net.skyscanner.backpack.demo.compose.SliderStory
import net.skyscanner.backpack.demo.compose.SpacingStory
import net.skyscanner.backpack.demo.compose.SpinnerStory
import net.skyscanner.backpack.demo.compose.StarRatingStory
import net.skyscanner.backpack.demo.compose.SwitchStory
import net.skyscanner.backpack.demo.compose.TextFiledStory
import net.skyscanner.backpack.demo.stories.BarChartFragment
import net.skyscanner.backpack.demo.stories.BottomNavFragment
import net.skyscanner.backpack.demo.stories.Calendar2Fragment
import net.skyscanner.backpack.demo.stories.ChangeableButtonsFragment
import net.skyscanner.backpack.demo.stories.ChipFragment
import net.skyscanner.backpack.demo.stories.ColoredCalendarFragment
import net.skyscanner.backpack.demo.stories.ComposeStory
import net.skyscanner.backpack.demo.stories.DefaultCalendarFragment
import net.skyscanner.backpack.demo.stories.DialogFragment
import net.skyscanner.backpack.demo.stories.DisabledCalendarFragment
import net.skyscanner.backpack.demo.stories.FooterViewCalendarFragment
import net.skyscanner.backpack.demo.stories.HorizontalNavFragment
import net.skyscanner.backpack.demo.stories.InteractiveStarRatingFragment
import net.skyscanner.backpack.demo.stories.LabeledCalendarFragment
import net.skyscanner.backpack.demo.stories.LoadingButtonFragment
import net.skyscanner.backpack.demo.stories.MapFragment
import net.skyscanner.backpack.demo.stories.NavBarFragment
import net.skyscanner.backpack.demo.stories.SnackbarFragment
import net.skyscanner.backpack.demo.stories.Story
import net.skyscanner.backpack.demo.stories.Story.Companion.Direction
import net.skyscanner.backpack.demo.stories.Story.Companion.scrollable
import net.skyscanner.backpack.demo.stories.Story.Companion.with
import net.skyscanner.backpack.demo.stories.StyleableButtonFragment
import net.skyscanner.backpack.demo.stories.SubStory
import net.skyscanner.backpack.demo.stories.TabStory
import net.skyscanner.backpack.demo.stories.TextSpansFragment
import net.skyscanner.backpack.demo.stories.ToastFragment
import net.skyscanner.backpack.demo.compose.DialogStory as ComposeDialogStory
import net.skyscanner.backpack.demo.compose.HorizontalNavStory as ComposeHorizontalNavStory
import net.skyscanner.backpack.demo.compose.InteractiveStarRatingStory as ComposeInteractiveStarRatingStory

interface RegistryItem {
  val name: String
  fun getParent(): RegistryItem?
  fun setParent(parent: RegistryItem)
  fun createStory(): Story
  fun getFullyQualifiedName(): String
}

open class NodeItem(
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
      fullName = "${parent.name} - $fullName"
      parent = parent.getParent()
    }

    return fullName
  }
}

class ComposeNode(
  name: String,
  val composable: @Composable () -> Unit,
) : NodeItem(name, { Story() }) {
  override fun createStory(): Story {
    return ComposeStory of getFullyQualifiedName()
  }
}

private class NodeData(
  val creator: (items: Array<String>) -> Story,
  val items: Map<String, RegistryItem> = emptyMap(),
) {

  constructor(creator: () -> Story) : this({ _ -> creator() })
}

private infix fun String.composeStory(composable: @Composable () -> Unit): Pair<String, NodeItem> {
  return Pair(this, ComposeNode(this, composable))
}

private infix fun String.story(story: NodeData): Pair<String, NodeItem> {
  return Pair(this, NodeItem(this, story.creator, story.items))
}

/**
 * Helper class to register the fragments for components
 */
object ComponentRegistry {

  private const val TAB_TITLE_COMPOSE = "Compose"
  private const val TAB_TITLE_VIEW = "View"

  val COMPONENTS = mapOf(
    "Badge" story NodeData(
      { children -> TabStory of children },
      mapOf(
        TAB_TITLE_VIEW story NodeData { Story of R.layout.fragment_badge },
        TAB_TITLE_COMPOSE composeStory { BadgeStory() },
      ),
    ),
    "Bar Chart" story NodeData(
      { children -> TabStory of children },
      mapOf(
        TAB_TITLE_VIEW story NodeData { BarChartFragment of R.layout.fragment_bar_chart },
        TAB_TITLE_COMPOSE composeStory { net.skyscanner.backpack.demo.compose.BarChartStory() },
      ),
    ),
    "Bottom Nav" story NodeData(
      { children -> TabStory of children },
      mapOf(
        TAB_TITLE_VIEW story NodeData { BottomNavFragment of R.layout.fragment_bottom_nav },
        TAB_TITLE_COMPOSE composeStory { BottomNavFragment() },
      ),
    ),
    "Bottom Sheet" story NodeData(
      { children -> TabStory of children },
      mapOf(
        TAB_TITLE_VIEW story NodeData { Story of R.layout.fragment_bottom_sheet scrollable false },
        TAB_TITLE_COMPOSE composeStory { BottomSheetStory() },
      ),
    ),
    "Button" story NodeData(
      { children -> TabStory of children },
      mapOf(
        TAB_TITLE_VIEW story NodeData(
          { children -> SubStory of children },
          mapOf(
            "Standard" story NodeData { LoadingButtonFragment of R.layout.fragment_button_standard },
            "Large" story NodeData { LoadingButtonFragment of R.layout.fragment_button_large },
            "Link" story NodeData { LoadingButtonFragment of R.layout.fragment_button_link },
            "Changeable" story NodeData { ChangeableButtonsFragment of R.layout.fragment_buttons_changeable },
            "Styleable" story NodeData { StyleableButtonFragment of R.layout.fragment_buttons_styleable },
          ),
        ),
        TAB_TITLE_COMPOSE story NodeData(
          { children -> SubStory of children },
          mapOf(
            "Default" composeStory { ButtonsDefaultSizeStory() },
            "Large" composeStory { ButtonsLargeSizeStory() },
            "Link" composeStory { ButtonLinkStory() },
            "Drawable Icon" composeStory { ButtonDrawableIconStory() },
          ),
        ),
      ),
    ),
    "Card" story NodeData(
      { children -> TabStory of children },
      mapOf(
        TAB_TITLE_VIEW story NodeData(
          { children -> SubStory of children },
          mapOf(
            "Default" story NodeData { Story of R.layout.fragment_card },
            "Without padding" story NodeData { Story of R.layout.fragment_card_without_padding },
            "Selected" story NodeData { Story of R.layout.fragment_card_selected },
            "No elevation" story NodeData { Story of R.layout.fragment_card_no_elevation },
            "Corner style large" story NodeData { Story of R.layout.fragment_card_cornerstyle_large },
            "With divider" story NodeData { Story of R.layout.fragment_card_with_divider },
            "With divider arranged vertically" story NodeData { Story of R.layout.fragment_card_with_divider_vertical },
            "With divider without padding" story NodeData { Story of R.layout.fragment_card_with_divider_no_padding },
            "With divider and corner style large" story NodeData {
              Story of R.layout.fragment_card_with_divider_cornerstyle_large
            },
          ),
        ),
        TAB_TITLE_COMPOSE story NodeData(
          { children -> SubStory of children },
          mapOf(
            "Card" composeStory { CardStory() },
            "Divided card" composeStory { DividedCardStory() },
            "Card wrapper" composeStory { CardWrapperStory() },
          ),
        ),
      ),
    ),
    "Card Button" story NodeData(
      { children -> SubStory of children },
      mapOf(
        "Default" composeStory { CardButtonDefaultStory() },
        "Small" composeStory { CardButtonSmallStory() },
      ),
    ),
    "Calendar" story NodeData(
      { children -> SubStory of children },
      mapOf(
        "Default" story NodeData { DefaultCalendarFragment of R.layout.fragment_calendar_default },
        "Colored" story NodeData { ColoredCalendarFragment of R.layout.fragment_calendar_colored },
        "Disabled Dates" story NodeData { DisabledCalendarFragment of R.layout.fragment_calendar_disabled },
        "Footer view" story NodeData { FooterViewCalendarFragment of R.layout.fragment_calendar_footer_view },
        "Footer view RTL" story NodeData {
          FooterViewCalendarFragment of R.layout.fragment_calendar_footer_view with Direction.RTL
        },
        "Labeled" story NodeData { LabeledCalendarFragment of R.layout.fragment_calendar_default },
      ),
    ),
    "Calendar 2" story NodeData(
      { children -> TabStory of children },
      mapOf(
        TAB_TITLE_VIEW story NodeData(
          { children -> SubStory of children },
          mapOf(
            "Selection Disabled" story NodeData { Calendar2Fragment of CalendarStoryType.SelectionDisabled },
            "Selection Single" story NodeData { Calendar2Fragment of CalendarStoryType.SelectionSingle },
            "Selection Range" story NodeData { Calendar2Fragment of CalendarStoryType.SelectionRange },
            "Selection Whole Month" story NodeData { Calendar2Fragment of CalendarStoryType.SelectionWholeMonth },
            "Disabled weekends" story NodeData { Calendar2Fragment of CalendarStoryType.WithDisabledDates },
            "Day labels" story NodeData { Calendar2Fragment of CalendarStoryType.WithLabels },
            "Pre-selected range" story NodeData { Calendar2Fragment of CalendarStoryType.PreselectedRange },
          ),
        ),
        TAB_TITLE_COMPOSE story NodeData(
          { children -> SubStory of children },
          mapOf(
            "Selection Disabled" composeStory { CalendarSelectionDisabledStory() },
            "Selection Single" composeStory { CalendarSelectionSingleStory() },
            "Selection Range" composeStory { CalendarSelectionRangeStory() },
            "Selection Whole Month" composeStory { CalendarSelectionWholeMonthStory() },
            "Disabled weekends" composeStory { CalendarDisabledWeekends() },
            "Day labels" composeStory { CalendarDayLabels() },
            "Pre-selected range" composeStory { CalendarPreSelectedRange() },
          ),
        ),
      ),
    ),
    "Chip" story NodeData(
      { children -> TabStory of children },
      mapOf(
        TAB_TITLE_VIEW story NodeData(
          { children -> SubStory of children },
          mapOf(
            "Default" story NodeData { ChipFragment of R.layout.fragment_chip },
            "On Dark" story NodeData { ChipFragment of R.layout.fragment_chip_ondark },
            "On Image" story NodeData { ChipFragment of R.layout.fragment_chip_on_image },
          ),
        ),
        TAB_TITLE_COMPOSE story NodeData(
          { children -> SubStory of children },
          mapOf(
            "Default" composeStory { ChipStoryDefault() },
            "On Dark" composeStory { ChipStoryOnDark() },
            "On Image" composeStory { ChipStoryOnImage() },
          ),
        ),
      ),
    ),
    "Checkbox" story NodeData(
      { children -> TabStory of children },
      mapOf(
        TAB_TITLE_VIEW story NodeData { Story of R.layout.fragment_checkbox },
        TAB_TITLE_COMPOSE composeStory { CheckboxStory() },
      ),
    ),
    "Dialog" story NodeData(
      { children -> TabStory of children },
      mapOf(
        TAB_TITLE_VIEW story NodeData { DialogFragment() },
        TAB_TITLE_COMPOSE composeStory { ComposeDialogStory() },
      ),
    ),
    "Divider" composeStory { DividerStory() },
    "FieldSet" composeStory { FieldSetStory() },
    "Flare" story NodeData(
      { children -> TabStory of children },
      mapOf(
        TAB_TITLE_VIEW story NodeData(
          { children -> SubStory of children },
          mapOf(
            "Default" story NodeData { Story of R.layout.fragment_flare },
            "Pointing up" story NodeData { Story of R.layout.fragment_flare_up },
            "Pointer offset" story NodeData { Story of R.layout.fragment_flare_pointer_offset },
            "Pointer offset RTL" story NodeData { Story of R.layout.fragment_flare_pointer_offset with Direction.RTL },
            "Rounded" story NodeData { Story of R.layout.fragment_flare_rounded },
            "Inset padding mode" story NodeData { Story of R.layout.fragment_flare_inset_padding_mode },
          ),
        ),
        TAB_TITLE_COMPOSE composeStory { FlareStory() },
      ),
    ),
    "Floating Action Button" story NodeData(
      { children -> TabStory of children },
      mapOf(
        TAB_TITLE_VIEW story NodeData { Story of R.layout.fragment_fab },
        TAB_TITLE_COMPOSE composeStory { FabStory() },
      ),
    ),
    "Floating Notification" composeStory { FloatingNotificationStory() },
    "Horizontal Nav" story NodeData(
      { children -> TabStory of children },
      mapOf(
        TAB_TITLE_VIEW story NodeData { HorizontalNavFragment of R.layout.fragment_horizontal_nav_default },
        TAB_TITLE_COMPOSE composeStory { ComposeHorizontalNavStory() },
      ),
    ),
    "Map Markers" story NodeData(
      { children -> SubStory of children },
      mapOf(
        "Pointers" story NodeData { MapFragment of MapFragment.Type.PointersOnly },
        "Badges " story NodeData { MapFragment of MapFragment.Type.Badges },
        "With icons" story NodeData { MapFragment of MapFragment.Type.BadgesWithIcons },
      ),
    ),
    "Nav Bar" story NodeData(
      { children -> TabStory of children },
      mapOf(
        TAB_TITLE_VIEW story NodeData(
          { children -> SubStory of children },
          mapOf(
            "Default" story NodeData { NavBarFragment of R.layout.fragment_nav_bar },
            "RTL" story NodeData { NavBarFragment of R.layout.fragment_nav_bar with Direction.RTL },
            "With Icon" story NodeData { NavBarFragment of R.layout.fragment_nav_bar_with_icon },
            "With Icon RTL" story NodeData { NavBarFragment of R.layout.fragment_nav_bar_with_icon with Direction.RTL },
            "With Menu" story NodeData { NavBarFragment of R.layout.fragment_nav_bar_with_menu },
            "With Menu RTL" story NodeData { NavBarFragment of R.layout.fragment_nav_bar_with_menu with Direction.RTL },
          ),
        ),
        TAB_TITLE_COMPOSE story NodeData(
          { children -> SubStory of children },
          mapOf(
            "Default" composeStory { NavigationBarStory() },
            "Collapsible" composeStory { CollapsibleNavigationBarStory() },
          ),
        ),
      ),
    ),
    "Nudger" story NodeData(
      { children -> TabStory of children },
      mapOf(
        TAB_TITLE_VIEW story NodeData { Story of R.layout.fragment_nudger },
        TAB_TITLE_COMPOSE composeStory { NudgerStory() },
      ),
    ),
    "Overlay" story NodeData { Story of R.layout.fragment_overlay },
    "Page Indicator" composeStory { PageIndicatorStory() },
    "Panel" story NodeData(
      { children -> TabStory of children },
      mapOf(
        TAB_TITLE_VIEW story NodeData { Story of R.layout.fragment_panel },
        TAB_TITLE_COMPOSE composeStory { PanelStory() },
      ),
    ),
    "Price" story NodeData(
      { children -> SubStory of children },
      mapOf(
        "Small" composeStory { PriceSmallStory() },
        "Large" composeStory { PriceLargeStory() },
      ),
    ),
    "RadioButton" story NodeData(
      { children -> TabStory of children },
      mapOf(
        TAB_TITLE_VIEW story NodeData { Story of R.layout.fragment_radio_button },
        TAB_TITLE_COMPOSE composeStory { RadioButtonStory() },
      ),
    ),
    "Rating" story NodeData(
      { children -> TabStory of children },
      mapOf(
        TAB_TITLE_VIEW story NodeData(
          { children -> SubStory of children },
          mapOf(
            "Default" story NodeData { Story of R.layout.fragment_rating_default },
            "Icons" story NodeData { Story of R.layout.fragment_rating_icons },
            "No selectors" story NodeData { Story of R.layout.fragment_rating_no_selectors },
            "Horizontal" story NodeData { Story of R.layout.fragment_rating_sizes },
            "Horizontal RTL" story NodeData { Story of R.layout.fragment_rating_sizes with Direction.RTL },
            "Vertical" story NodeData { Story of R.layout.fragment_rating_sizes_vertical },
            "Pill" story NodeData { Story of R.layout.fragment_rating_sizes_pill },
            "Zero to Five Scale" story NodeData { Story of R.layout.fragment_rating_zero_to_five },
          ),
        ),
        TAB_TITLE_COMPOSE composeStory { RatingStory() },
      ),
    ),
    "Skeleton" story NodeData(
      { children -> TabStory of children },
      mapOf(
        TAB_TITLE_VIEW story NodeData { Story of R.layout.fragment_skeletons },
        TAB_TITLE_COMPOSE composeStory { SkeletonStory() },
      ),
    ),
    "Slider" story NodeData(
      { children -> TabStory of children },
      mapOf(
        TAB_TITLE_VIEW story NodeData { Story of R.layout.fragment_slider },
        TAB_TITLE_COMPOSE composeStory { SliderStory() },
      ),
    ),
    "Snackbar" story NodeData { SnackbarFragment of R.layout.fragment_snackbar },
    "Spinner" story NodeData(
      { children -> TabStory of children },
      mapOf(
        TAB_TITLE_VIEW story NodeData { Story of R.layout.fragment_spinner },
        TAB_TITLE_COMPOSE composeStory { SpinnerStory() },
      ),
    ),
    "Star Rating" story NodeData(
      { children -> TabStory of children },
      mapOf(
        TAB_TITLE_VIEW story NodeData(
          { children -> SubStory of children },
          mapOf(
            "Default" story NodeData { Story of R.layout.fragment_star_rating_default },
            "RTL" story NodeData { Story of R.layout.fragment_star_rating_default with Direction.RTL },
            "Different values" story NodeData { Story of R.layout.fragment_star_rating_values },
            "Custom Max Rating" story NodeData { Story of R.layout.fragment_star_rating_max },
          ),
        ),
        TAB_TITLE_COMPOSE composeStory { StarRatingStory() },
      ),
    ),
    "Star Rating Interactive" story NodeData(
      { children -> TabStory of children },
      mapOf(
        TAB_TITLE_VIEW story NodeData { InteractiveStarRatingFragment of R.layout.fragment_star_rating_interactive },
        TAB_TITLE_COMPOSE composeStory { ComposeInteractiveStarRatingStory() },
      ),
    ),

    "Switch" story NodeData(
      { children -> TabStory of children },
      mapOf(
        TAB_TITLE_VIEW story NodeData { Story of R.layout.fragment_switch },
        TAB_TITLE_COMPOSE composeStory { SwitchStory() },
      ),
    ),
    "Text" story NodeData(
      { children -> TabStory of children },
      mapOf(
        TAB_TITLE_VIEW story NodeData(
          { children -> SubStory of children },
          mapOf(
            "Body" story NodeData { Story of R.layout.fragment_text_body },
            "Heading" story NodeData { Story of R.layout.fragment_text_heading },
            "Hero" story NodeData { Story of R.layout.fragment_text_hero },
            "With drawables" story NodeData { Story of R.layout.fragment_text_drawables },
            "With links" story NodeData { Story of R.layout.fragment_text_links },
          ),
        ),
        TAB_TITLE_COMPOSE story NodeData(
          { children -> SubStory of children },
          mapOf(
            "Hero" composeStory { HeroStyleStory() },
            "Heading" composeStory { HeadingStyleStory() },
            "Body" composeStory { BodyStyleStory() },
          ),
        ),
      ),
    ),
    "Text Field" story NodeData(
      { children -> TabStory of children },
      mapOf(
        TAB_TITLE_VIEW story NodeData(
          { children -> SubStory of children },
          mapOf(
            "Default" story NodeData { Story of R.layout.fragment_text_fields },
            "RTL" story NodeData { Story of R.layout.fragment_text_fields_rtl },
            "With labels" story NodeData { Story of R.layout.fragment_text_fields_labels },
          ),
        ),
        TAB_TITLE_COMPOSE composeStory { TextFiledStory() },
      ),
    ),
    "Text Spans" story NodeData { TextSpansFragment of R.layout.fragment_text_spans },
    "Toast" story NodeData { ToastFragment of R.layout.fragment_toasts },
  )

  val TOKENS = mapOf(
    "All icons" composeStory { IconsStoryCompose() },
    "Color" composeStory { ColorsComposeStory() },
    "Elevation" composeStory { ElevationComposeStory() },
    "Radii" composeStory { RadiiComposeStory() },
    "Spacing" composeStory { SpacingStory() },
  )

  fun getStoryCreator(fullyQualifiedName: String): RegistryItem {
    val parts = fullyQualifiedName.split(" - ")
    val first = parts[0]
    val rest = parts.drop(1)

    val story = TOKENS[first] ?: COMPONENTS[first]
    story ?: throw IllegalArgumentException("Invalid story name - $fullyQualifiedName")

    return rest.fold(story) { result, item ->
      result.subItems[item] as? NodeItem
        ?: throw IllegalArgumentException("Invalid story name - $fullyQualifiedName")
    }
  }

  fun getStoryName(fullyQualifiedName: String): String {
    return fullyQualifiedName.split(" - ").last()
  }
}
