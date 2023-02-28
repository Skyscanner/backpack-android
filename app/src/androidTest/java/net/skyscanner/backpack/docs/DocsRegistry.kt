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

import android.icu.text.NumberFormat
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.platform.app.InstrumentationRegistry
import net.skyscanner.backpack.calendar2.CalendarSelection
import net.skyscanner.backpack.compose.fieldset.BpkFieldStatus
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.demo.compose.ShownDialog
import org.threeten.bp.Month
import org.threeten.bp.YearMonth

object DocsRegistry {
  val screenshots = listOf(
    ComposeScreenshot("All icons", "default"),
    ViewScreenshot("Badge - View", "all"),
    ComposeScreenshot("Badge - Compose", "default"),
    ViewScreenshot("Bar Chart", "default"),
    ViewScreenshot("Bottom Nav - View", "default"),
    ComposeScreenshot("Bottom Nav - Compose", "default"),
    ViewScreenshot("Bar Chart - View", "default"),
    ComposeScreenshot("Bar Chart - Compose", "default", ::setupBarChart),
    ViewScreenshot("Bottom Nav", "default"),
    ViewScreenshot("Bottom Sheet - View", "default"),
    ComposeScreenshot("Bottom Sheet - Compose", "default"),
    ViewScreenshot("Button - View - Standard", "standard"),
    ViewScreenshot("Button - View - Large", "large"),
    ViewScreenshot("Button - View - Link", "link"),
    ComposeScreenshot("Button - Compose - Default", "default"),
    ComposeScreenshot("Button - Compose - Large", "large"),
    ComposeScreenshot("Button - Compose - Link", "link"),
    ComposeScreenshot("Button - Compose - Drawable Icon", "drawable-icon"),
    ViewScreenshot("Calendar 2 - View - Pre-selected range", "range") { setupCalendar2() },
    ViewScreenshot("Calendar 2 - View - Day labels", "labeled") { setupCalendar2() },
    ViewScreenshot("Calendar 2 - View - Selection Whole Month", "month") { setupWholeMonthCalendar() },
    ComposeScreenshot("Calendar 2 - Compose - Pre-selected range", "range") { setupCalendar2(it) },
    ComposeScreenshot("Calendar 2 - Compose - Day labels", "labeled") { setupCalendar2(it) },
    ComposeScreenshot("Calendar 2 - Compose - Selection Whole Month", "month") { setupWholeMonthCalendar(it) },
    ViewScreenshot("Card - View - Default", "default"),
    ViewScreenshot("Card - View - Without padding", "without-padding"),
    ViewScreenshot("Card - View - Selected", "selected"),
    ViewScreenshot("Card - View - Corner style large", "corner-style-large"),
    ViewScreenshot("Card - View - With divider", "with-divider"),
    ViewScreenshot("Card - View - With divider arranged vertically", "with-divider-arranged-vertically"),
    ViewScreenshot("Card - View - With divider without padding", "with-divider-without-padding"),
    ViewScreenshot("Card - View - With divider and corner style large", "with-divider-and-corner-style-large"),
    ComposeScreenshot("Card - Compose - Card", "default"),
    ComposeScreenshot("Card - Compose - Divided card", "divided-card"),
    ComposeScreenshot("Card - Compose - Card wrapper", "card-wrapper"),
    ComposeScreenshot("Card Button - Default", "default"),
    ComposeScreenshot("Card Button - Small", "small"),
    ViewScreenshot("Chip - View - Default", "all"),
    ViewScreenshot("Chip - View - On Dark", "on-dark"),
    ViewScreenshot("Chip - View - On Image", "on-image"),
    ComposeScreenshot("Chip - Compose - Default", "default"),
    ComposeScreenshot("Chip - Compose - On Dark", "on-dark"),
    ComposeScreenshot("Chip - Compose - On Image", "on-image"),
    ViewScreenshot("Checkbox - View", "default"),
    ComposeScreenshot("Checkbox - Compose", "default"),
    ViewScreenshot("Dialog - View", "with-cta") { setupDialog("Success Three Buttons") },
    ViewScreenshot("Dialog - View", "delete-confirmation") { setupDialog("Destructive") },
    ViewScreenshot("Dialog - View", "with-flare") { setupDialog("Flare") },
    ComposeScreenshot("Dialog - Compose", "success") { setupComposeDialog(it, ShownDialog.SuccessThreeButtons) },
    ComposeScreenshot("Dialog - Compose", "warning") { setupComposeDialog(it, ShownDialog.Warning) },
    ComposeScreenshot("Dialog - Compose", "destructive") { setupComposeDialog(it, ShownDialog.Destructive) },
    ComposeScreenshot("Dialog - Compose", "flare") { setupComposeDialog(it, ShownDialog.Flare) },
    ComposeScreenshot("Dialog - Compose", "image-start-alignment") {
      setupComposeDialog(it, ShownDialog.ImageStartAlignment)
    },
    ComposeScreenshot("Dialog - Compose", "image-end-alignment") {
      setupComposeDialog(it, ShownDialog.ImageEndAlignment)
    },
    ComposeScreenshot("Divider", "default"),
    ComposeScreenshot("FieldSet", "default"),
    ComposeScreenshot("FieldSet", "disabled") { it.switchFieldStatus(BpkFieldStatus.Disabled) },
    ComposeScreenshot("FieldSet", "validated") { it.switchFieldStatus(BpkFieldStatus.Validated) },
    ComposeScreenshot("FieldSet", "error") { it.switchFieldStatus(BpkFieldStatus.Error("Error text")) },
    ViewScreenshot("Flare - View - Default", "default"),
    ViewScreenshot("Flare - View - Pointing up", "pointing-up"),
    ViewScreenshot("Flare - View - Pointer offset", "pointer-offset"),
    ViewScreenshot("Flare - View - Rounded", "rounded"),
    ViewScreenshot("Flare - View - Inset padding mode", "inset-padding"),
    ComposeScreenshot("Flare - Compose", "default"),
    ComposeScreenshot("Floating Notification", "default") { setupFloatingNotification(it) },
    ViewScreenshot("Floating Action Button - View", "default"),
    ComposeScreenshot("Floating Action Button - Compose", "default"),
    ViewScreenshot("Horizontal Nav - View", "default"),
    ComposeScreenshot("Horizontal Nav - Compose", "default"),
    ViewScreenshot("Nav Bar - View - Default", "expanded"),
    ViewScreenshot("Nav Bar - View - Default", "collapsed") { setupNavBarCollapsed() },
    ViewScreenshot("Nav Bar - View - With Menu", "navigation") { setupNavBarCollapsed() },
    ComposeScreenshot("Nav Bar - Compose - Default", "default"),
    ComposeScreenshot("Nav Bar - Compose - Collapsible", "collapsible"),
    ViewScreenshot("Nudger - View", "all"),
    ComposeScreenshot("Nudger - Compose", "all"),
    ViewScreenshot("Overlay", "all"),
    ComposeScreenshot("Page Indicator", "default"),
    ViewScreenshot("Panel - View", "all"),
    ComposeScreenshot("Panel - Compose", "all"),
    ComposeScreenshot("Price - Small", "small"),
    ComposeScreenshot("Price - Large", "large"),
    ViewScreenshot("RadioButton - View", "default"),
    ComposeScreenshot("RadioButton - Compose", "default"),
    ViewScreenshot("Rating - View - Default", "default"),
    ViewScreenshot("Rating - View - Horizontal", "sizes"),
    ViewScreenshot("Rating - View - Vertical", "vertical"),
    ViewScreenshot("Rating - View - Pill", "pill"),
    ComposeScreenshot("Rating - Compose", "default"),
    ViewScreenshot("Skeleton - View", "default"),
    ComposeScreenshot("Skeleton - Compose", "default"),
    ViewScreenshot("Slider - View", "all"),
    ComposeScreenshot("Slider - Compose", "default"),
    ViewScreenshot("Snackbar", "default") { setupSnackbar() },
    ViewScreenshot("Snackbar", "icon") { setupSnackbarIconAction() },
    ViewScreenshot("Spinner - View", "default"),
    ComposeScreenshot("Spinner - Compose", "default"),
    ViewScreenshot("Star Rating - View - Default", "default"),
    ComposeScreenshot("Star Rating - Compose", "default"),
    ViewScreenshot("Star Rating Interactive - View", "default"),
    ComposeScreenshot("Star Rating Interactive - Compose", "default"),
    ViewScreenshot("Switch - View", "default"),
    ComposeScreenshot("Switch - Compose", "default"),
    ViewScreenshot("Text - View - Body", "body"),
    ViewScreenshot("Text - View - Heading", "heading"),
    ViewScreenshot("Text - View - Hero", "hero"),
    ComposeScreenshot("Text - Compose - Hero", "hero"),
    ComposeScreenshot("Text - Compose - Heading", "heading"),
    ComposeScreenshot("Text - Compose - Body", "body"),
    ViewScreenshot("Text Field - View - Default", "default"),
    ViewScreenshot("Text Field - View - With labels", "labels"),
    ComposeScreenshot("Text Field - Compose", "default"),
    ComposeScreenshot("Text Field - Compose", "disabled") { it.switchFieldStatus(BpkFieldStatus.Disabled) },
    ComposeScreenshot("Text Field - Compose", "validated") { it.switchFieldStatus(BpkFieldStatus.Validated) },
    ComposeScreenshot("Text Field - Compose", "error") { it.switchFieldStatus(BpkFieldStatus.Error("Error text")) },
    ViewScreenshot("Text Spans", "default"),
    // Leave toast last as it stays visible in the screen for a while
    ViewScreenshot("Toast", "default") { setupToast() }
  )
}

fun ComposeScreenshot(
  name: String,
  screenshotName: String,
  setup: ((AndroidComposeTestRule<*, *>) -> Unit)? = null,
): Array<Any?> =
  arrayOf(name, screenshotName, "compose", setup)

fun ViewScreenshot(
  name: String,
  screenshotName: String,
  setup: ((ComposeTestRule) -> Unit)? = null,
): Array<Any?> =
  arrayOf(name, screenshotName, "view", setup)

private fun setupCalendar2() {
  Espresso.onView(ViewMatchers.withId(R.id.calendar2))
    .check { view, _ ->
      view as net.skyscanner.backpack.calendar2.BpkCalendar
      view.setSelection(
        CalendarSelection.Dates(
          view.state.value.params.now.plusDays(5),
          view.state.value.params.now.plusDays(10),
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

private fun setupCalendar2(rule: AndroidComposeTestRule<*, *>) {
  rule
    .onAllNodesWithText("6")
    .onFirst()
    .performClick()
    .assertIsDisplayed()

  rule
    .onAllNodesWithText("11")
    .onFirst()
    .performClick()
    .assertIsDisplayed()
}

private fun setupWholeMonthCalendar(rule: AndroidComposeTestRule<*, *>) {
  rule
    .onAllNodesWithText("Select whole month")
    .onFirst()
    .performClick()
    .assertIsDisplayed()
}

private fun setupBarChart(rule: AndroidComposeTestRule<*, *>) {
  rule
    .onAllNodesWithText(NumberFormat.getCurrencyInstance().currency.symbol, substring = true)
    .onFirst()
    .assertIsDisplayed()
}

private fun setupNavBarCollapsed() {
  Espresso.onView(ViewMatchers.withId(R.id.navBarCoordinator))
    .perform(ViewActions.swipeUp())

  InstrumentationRegistry.getInstrumentation().waitForIdleSync()
}

private fun setupDialog(text: String) {
  Espresso.onView(ViewMatchers.withText(text))
    .perform(ViewActions.click())

  InstrumentationRegistry.getInstrumentation().waitForIdleSync()
}

private fun setupComposeDialog(testRule: ComposeTestRule, dialog: ShownDialog) {
  testRule.onNodeWithTag(dialog.buttonText.toString()).performClick().assertIsDisplayed()
}

private fun setupSnackbar() {
  Espresso.onView(ViewMatchers.withText("Message (Duration Indefinite)"))
    .perform(ViewActions.click())

  InstrumentationRegistry.getInstrumentation().waitForIdleSync()
}

private fun setupFloatingNotification(testRule: AndroidComposeTestRule<*, *>) {
  testRule
    .onNodeWithText(testRule.activity.getString(R.string.floating_notification_with_icon_and_action))
    .performClick()
    .assertIsDisplayed()
}

private fun setupSnackbarIconAction() {
  Espresso.onView(ViewMatchers.withText("Icon + title + message (Icon Action)"))
    .perform(ViewActions.click())

  InstrumentationRegistry.getInstrumentation().waitForIdleSync()
}

private fun setupToast() {
  Espresso.onView(ViewMatchers.withText("Short Toast"))
    .perform(ViewActions.click())

  InstrumentationRegistry.getInstrumentation().waitForIdleSync()
}

private fun ComposeTestRule.switchFieldStatus(to: BpkFieldStatus) {
  onNodeWithText(to::class.simpleName!!).performClick().assertIsDisplayed()
}
