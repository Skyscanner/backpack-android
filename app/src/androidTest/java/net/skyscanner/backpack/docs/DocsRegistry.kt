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

package net.skyscanner.backpack.docs

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import net.skyscanner.backpack.calendar.BpkCalendar
import net.skyscanner.backpack.calendar.model.CalendarRange
import net.skyscanner.backpack.calendar2.CalendarSelection
import net.skyscanner.backpack.calendar2.data.CalendarDispatchers
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.util.ExperimentalBackpackApi
import net.skyscanner.backpack.util.InternalBackpackApi
import org.threeten.bp.LocalDate

@OptIn(InternalBackpackApi::class, ExperimentalCoroutinesApi::class)
object DocsRegistry {
  val screenshots = listOf(
    Screenshot("Badge", "all"),
    Screenshot("Bar Chart", "default"),
    Screenshot("Bottom Nav", "default"),
    Screenshot("Bottom Sheet", "default"),
    Screenshot("Button - Primary", "primary"),
    Screenshot("Button - Secondary", "secondary"),
    Screenshot("Button - Featured", "featured"),
    Screenshot("Button - Destructive", "destructive"),
    Screenshot("Button - Outline", "outline"),
    Screenshot("ButtonLink - Default", "default"),
    Screenshot("Calendar - Default", "range", ::setupCalendar),
    Screenshot("Calendar - Colored", "colored", ::setupCalendar),
    Screenshot("Calendar - Labeled", "labeled", ::setupCalendar),
    Screenshot("Calendar 2 - Pre-selected range", "range", ::setupCalendar2),
    Screenshot("Calendar 2 - Day colours", "colored", ::setupCalendar2),
    Screenshot("Calendar 2 - Day labels", "labeled", ::setupCalendar2),
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
    Screenshot("Nudger", "all"),
    Screenshot("Overlay", "all"),
    Screenshot("Panel", "all"),
    Screenshot("RadioButton", "default"),
    Screenshot("Rating - Default", "default"),
    Screenshot("Rating - Horizontal", "sizes"),
    Screenshot("Rating - Vertical", "vertical"),
    Screenshot("Rating - Pill", "pill"),
    Screenshot("Slider", "all"),
    Screenshot("Snackbar", "default", ::setupSnackbar),
    Screenshot("Snackbar", "icon", ::setupSnackbarIconAction),
    Screenshot("Star Rating - Default", "default"),
    Screenshot("Star Rating Interactive", "default"),
    Screenshot("Switch", "default"),
    Screenshot("Text - View - Default", "default"),
    Screenshot("Text - View - Emphasized", "emphasized"),
    Screenshot("Text - View - Heavy", "heavy"),
    Screenshot("Text Field - Default", "default"),
    Screenshot("Text Field - With labels", "labels"),
    Screenshot("Text Spans", "default"),
    Screenshot("Spinner - Default", "default"),
    Screenshot("Spinner - Small", "small"),
    // Leave toast last as it stays visible in the screen for a while
    Screenshot("Toast", "default", ::setupToast)
  ).map { it.toArgs() }

  init {
    CalendarDispatchers.setBackground(TestCoroutineDispatcher())
  }
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

@OptIn(ExperimentalBackpackApi::class)
private fun setupCalendar2() {
  Espresso.onView(ViewMatchers.withId(R.id.calendar2))
    .check { view, _ ->
      view as net.skyscanner.backpack.calendar2.BpkCalendar
      view.setSelection(
        CalendarSelection.Range(
          view.state.value.params.now.plusDays(5),
          view.state.value.params.now.plusDays(10)
        )
      )
    }
}

private fun setupNavBarCollapsed() {
  Espresso.onView(ViewMatchers.withId(R.id.navBarCoordinator))
    .perform(ViewActions.swipeUp())

  Thread.sleep(100)
}

private fun setupDialog() {
  Espresso.onView(ViewMatchers.withText("Show"))
    .perform(ViewActions.click())

  Thread.sleep(50)
}

private fun setupSnackbar() {
  Espresso.onView(ViewMatchers.withText("Message (Duration Indefinite)"))
    .perform(ViewActions.click())

  Thread.sleep(50)
}

private fun setupSnackbarIconAction() {
  Espresso.onView(ViewMatchers.withText("Icon + title + message (Icon Action)"))
    .perform(ViewActions.click())

  Thread.sleep(50)
}

private fun setupToast() {
  Espresso.onView(ViewMatchers.withText("Short Toast"))
    .perform(ViewActions.click())

  Thread.sleep(50)
}
