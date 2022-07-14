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

package net.skyscanner.backpack.docs

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import net.skyscanner.backpack.calendar.BpkCalendar
import net.skyscanner.backpack.calendar.model.CalendarRange
import net.skyscanner.backpack.calendar2.CalendarSelection
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.demo.compose.ShownDialog
import net.skyscanner.backpack.util.InternalBackpackApi
import org.threeten.bp.LocalDate
import org.threeten.bp.Month
import org.threeten.bp.YearMonth

@OptIn(InternalBackpackApi::class, ExperimentalCoroutinesApi::class)
object DocsRegistry {
  val screenshots = listOf(
    ComposeScreenshot("All Icons - Compose", "default"),
    ViewScreenshot("Badge - View", "all"),
    ComposeScreenshot("Badge - Compose", "default"),
    ViewScreenshot("Bar Chart", "default"),
    ViewScreenshot("Bottom Nav", "default"),
    ViewScreenshot("Bottom Sheet", "default"),
    ViewScreenshot("Button - View - Standard", "standard"),
    ViewScreenshot("Button - View - Large", "large"),
    ViewScreenshot("Button - View - Link", "link"),
    ComposeScreenshot("Button - Compose - Default", "default"),
    ComposeScreenshot("Button - Compose - Large", "large"),
    ComposeScreenshot("Button - Compose - Link", "link"),
    ViewScreenshot("Calendar - Default", "range") { setupCalendar() },
    ViewScreenshot("Calendar - Colored", "colored") { setupCalendar() },
    ViewScreenshot("Calendar - Labeled", "labeled") { setupCalendar() },
    ViewScreenshot("Calendar 2 - Pre-selected range", "range") { setupCalendar2() },
    ViewScreenshot("Calendar 2 - Day colours", "colored") { setupCalendar2() },
    ViewScreenshot("Calendar 2 - Day labels", "labeled") { setupCalendar2() },
    ViewScreenshot("Calendar 2 - Selection Whole Month", "month") { setupWholeMonthCalendar() },
    ViewScreenshot("Card - View - Default", "default"),
    ViewScreenshot("Card - View - Without padding", "without-padding"),
    ViewScreenshot("Card - View - Selected", "selected"),
    ViewScreenshot("Card - View - Corner style large", "corner-style-large"),
    ViewScreenshot("Card - View - With divider", "with-divider"),
    ViewScreenshot("Card - View - With divider arranged vertically", "with-divider-arranged-vertically"),
    ViewScreenshot("Card - View - With divider without padding", "with-divider-without-padding"),
    ViewScreenshot("Card - View - With divider and corner style large", "with-divider-and-corner-style-large"),
    ComposeScreenshot("Card - Compose", "default"),
    ViewScreenshot("Chip - View - Default", "all"),
    ViewScreenshot("Chip - View - On Dark", "on-dark"),
    ViewScreenshot("Chip - View - With icon", "with-icon"),
    ComposeScreenshot("Chip - Compose", "default"),
    ViewScreenshot("Checkbox - View", "default"),
    ComposeScreenshot("Checkbox - Compose", "default"),
    ViewScreenshot("Dialog - View - With call to action", "with-cta") { setupDialog() },
    ViewScreenshot("Dialog - View - Delete confirmation", "delete-confirmation") { setupDialog() },
    ViewScreenshot("Dialog - View - Flare", "with-flare") { setupDialog() },
    ComposeScreenshot("Dialog - Compose", "success") { setupComposeDialog(it, ShownDialog.SuccessThreeButtons) },
    ComposeScreenshot("Dialog - Compose", "warning") { setupComposeDialog(it, ShownDialog.Warning) },
    ComposeScreenshot("Dialog - Compose", "destructive") { setupComposeDialog(it, ShownDialog.Destructive) },
    ComposeScreenshot("Dialog - Compose", "flare") { setupComposeDialog(it, ShownDialog.Flare) },
    ViewScreenshot("Flare - View - Default", "default"),
    ViewScreenshot("Flare - View - Pointing up", "pointing-up"),
    ViewScreenshot("Flare - View - Pointer offset", "pointer-offset"),
    ViewScreenshot("Flare - View - Rounded", "rounded"),
    ViewScreenshot("Flare - View - Inset padding mode", "inset-padding"),
    ComposeScreenshot("Flare - Compose", "default"),
    ViewScreenshot("Horizontal Nav", "default"),
    ViewScreenshot("Floating Action Button", "default"),
    ViewScreenshot("Nav Bar - View - Default", "expanded"),
    ViewScreenshot("Nav Bar - View - Default", "collapsed") { setupNavBarCollapsed() },
    ViewScreenshot("Nav Bar - View - With Menu", "navigation") { setupNavBarCollapsed() },
    ComposeScreenshot("Nav Bar - Compose", "default"),
    ViewScreenshot("Nudger", "all"),
    ViewScreenshot("Overlay", "all"),
    ViewScreenshot("Panel - View", "all"),
    ComposeScreenshot("Panel - Compose", "all"),
    ViewScreenshot("RadioButton - View", "default"),
    ComposeScreenshot("RadioButton - Compose", "default"),
    ViewScreenshot("Rating - Default", "default"),
    ViewScreenshot("Rating - Horizontal", "sizes"),
    ViewScreenshot("Rating - Vertical", "vertical"),
    ViewScreenshot("Rating - Pill", "pill"),
    ViewScreenshot("Slider", "all"),
    ViewScreenshot("Snackbar", "default") { setupSnackbar() },
    ViewScreenshot("Snackbar", "icon") { setupSnackbarIconAction() },
    ViewScreenshot("Star Rating - Default", "default"),
    ViewScreenshot("Star Rating Interactive", "default"),
    ViewScreenshot("Switch - View", "default"),
    ComposeScreenshot("Switch - Compose", "default"),
    ViewScreenshot("Text - View - Body", "body"),
    ViewScreenshot("Text - View - Heading", "heading"),
    ViewScreenshot("Text - View - Hero", "hero"),
    ComposeScreenshot("Text - Compose - Hero", "hero"),
    ComposeScreenshot("Text - Compose - Heading", "heading"),
    ComposeScreenshot("Text - Compose - Body", "body"),
    ViewScreenshot("Text Field - Default", "default"),
    ViewScreenshot("Text Field - With labels", "labels"),
    ViewScreenshot("Text Spans", "default"),
    ViewScreenshot("Spinner - View - Default", "default"),
    ViewScreenshot("Spinner - View - Small", "small"),
    ComposeScreenshot("Spinner - Compose", "default"),
    // Leave toast last as it stays visible in the screen for a while
    ViewScreenshot("Toast", "default") { setupToast() }
  )
}

fun ComposeScreenshot(
  name: String,
  screenshotName: String,
  setup: ((ComposeTestRule) -> Unit)? = null,
): Array<Any?> =
  arrayOf(name, screenshotName, "docs/compose", setup)

fun ViewScreenshot(
  name: String,
  screenshotName: String,
  setup: ((ComposeTestRule) -> Unit)? = null,
): Array<Any?> =
  arrayOf(name, screenshotName, "docs/view", setup)

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

private fun setupCalendar2() {
  Espresso.onView(ViewMatchers.withId(R.id.calendar2))
    .check { view, _ ->
      view as net.skyscanner.backpack.calendar2.BpkCalendar
      view.setSelection(
        CalendarSelection.Dates(
          view.state.value.params.now.plusDays(5),
          view.state.value.params.now.plusDays(10)
        )
      )
    }
}

private fun setupWholeMonthCalendar() {
  Espresso.onView(ViewMatchers.withId(R.id.calendar2))
    .check { view, _ ->
      view as net.skyscanner.backpack.calendar2.BpkCalendar
      view.setSelection(
        CalendarSelection.Month(YearMonth.of(2019, Month.JANUARY))
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

private fun setupComposeDialog(testRule: ComposeTestRule, dialog: ShownDialog) {
  testRule.onNodeWithText(dialog.buttonText).performClick().assertIsDisplayed()
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
