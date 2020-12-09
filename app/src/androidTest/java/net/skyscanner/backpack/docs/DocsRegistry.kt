/**
 * Backpack for Android - Skyscanner's Design System
 *
 * Copyright 2018-2020 Skyscanner Ltd
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

package net.skyscanner.backpack.docs

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import net.skyscanner.backpack.calendar.BpkCalendar
import net.skyscanner.backpack.calendar.model.CalendarRange
import net.skyscanner.backpack.demo.R
import org.threeten.bp.LocalDate

object DocsRegistry {
  val screenshots = listOf(
    Screenshot("Badge", "all"),
    Screenshot("Bar Chart", "default"),
    Screenshot("Bottom Nav", "default"),
    Screenshot("Button - Primary", "primary"),
    Screenshot("Button - Secondary", "secondary"),
    Screenshot("Button - Featured", "featured"),
    Screenshot("Button - Destructive", "destructive"),
    Screenshot("Button - Outline", "outline"),
    Screenshot("Button - Loading", "loading"),
    Screenshot("ButtonLink - Default", "default"),
    Screenshot("Calendar - Default", "range", ::setupCalendar),
    Screenshot("Calendar - Colored", "colored", ::setupCalendar),
    Screenshot("Calendar - Labeled", "labeled", ::setupCalendar),
    Screenshot("Card - Default", "default"),
    Screenshot("Card - Without padding", "without-padding"),
    Screenshot("Card - Selected", "selected"),
    Screenshot("Card - Corner style large", "corner-style-large"),
    Screenshot("Card - With divider", "with-divider"),
    Screenshot("Card - With divider arranged vertically", "with-divider-arranged-vertically"),
    Screenshot("Card - With divider without padding", "with-divider-without-padding"),
    Screenshot("Card - With divider and corner style large", "with-divider-and-corner-style-large"),
    Screenshot("Chip - Default", "all"),
    Screenshot("Chip - Outline", "outline"),
    Screenshot("Chip - Custom", "custom"),
    Screenshot("Chip - With icon", "with-icon"),
    Screenshot("Checkbox", "default"),
    Screenshot("Dialog - With call to action", "with-cta", ::setupDialog),
    Screenshot("Dialog - Delete confirmation", "delete-confirmation", ::setupDialog),
    Screenshot("Dialog - Flare", "with-flare", ::setupDialog),
    Screenshot("Flare - Default", "default"),
    Screenshot("Flare - Pointing up", "pointing-up"),
    Screenshot("Flare - Pointer offset", "pointer-offset"),
    Screenshot("Flare - Rounded", "rounded"),
    Screenshot("Flare - Inset padding mode", "inset-padding"),
    Screenshot("Horizontal Nav", "default"),
    Screenshot("Floating Action Button", "default"),
    Screenshot("Nav Bar - Default", "expanded"),
    Screenshot("Nav Bar - Default", "collapsed", ::setupNavBarCollapsed),
    Screenshot("Nav Bar - With Menu", "navigation", ::setupNavBarCollapsed),
    Screenshot("Overlay", "all"),
    Screenshot("Panel", "all"),
    Screenshot("Rating - Default", "default"),
    Screenshot("Rating - Horizontal", "sizes"),
    Screenshot("Rating - Vertical", "vertical"),
    Screenshot("Rating - Pill", "pill"),
    Screenshot("Snackbar", "default", ::setupSnackbar),
    Screenshot("Snackbar", "icon", ::setupSnackbarIconAction),
    Screenshot("Star Rating - Default", "default"),
    Screenshot("Star Rating Interactive", "default"),
    Screenshot("Switch", "default"),
    Screenshot("Text - Default", "default"),
    Screenshot("Text - Emphasized", "emphasized"),
    Screenshot("Text - Heavy", "heavy"),
    Screenshot("Text Field", "default"),
    Screenshot("Text Spans", "default"),
    Screenshot("Spinner - Default", "default"),
    Screenshot("Spinner - Small", "small"),
    // Leave toast last as it stays visible in the screen for a while
    Screenshot("Toast", "default", ::setupToast)
  ).map { it.toArgs() }
}

data class Screenshot(
  val name: String,
  val screenshotName: String,
  val setup: (() -> Unit)? = null
) {
  fun toArgs() = arrayOf(name, screenshotName, setup)
}

private fun setupCalendar() {
  Espresso.onView(ViewMatchers.withId(R.id.bpkCalendar))
    .check { view, _ ->
      view as BpkCalendar
      view.controller?.updateSelection(
        CalendarRange(
          LocalDate.now().plusDays(5),
          LocalDate.now().plusDays(10)
        )
      )
      view.controller?.updateContent()
    }
}

private fun setupNavBarCollapsed() {
  Espresso.onView(ViewMatchers.withId(R.id.component_detail_container))
    .perform(ViewActions.swipeUp())

  Thread.sleep(100)
}

private fun setupDialog() {
  Espresso.onView(ViewMatchers.withText("SHOW"))
    .perform(ViewActions.click())

  Thread.sleep(50)
}

private fun setupSnackbar() {
  Espresso.onView(ViewMatchers.withText("MESSAGE (DURATION INDEFINITE)"))
    .perform(ViewActions.click())

  Thread.sleep(50)
}

private fun setupSnackbarIconAction() {
  Espresso.onView(ViewMatchers.withText("ICON + TITLE + MESSAGE (ICON ACTION)"))
    .perform(ViewActions.click())

  Thread.sleep(50)
}

private fun setupToast() {
  Espresso.onView(ViewMatchers.withText("SHORT TOAST"))
    .perform(ViewActions.click())

  Thread.sleep(50)
}
