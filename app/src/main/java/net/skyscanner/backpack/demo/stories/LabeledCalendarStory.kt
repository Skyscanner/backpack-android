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

@file:Suppress("DEPRECATION")

package net.skyscanner.backpack.demo.stories

import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import net.skyscanner.backpack.calendar.BpkCalendar
import net.skyscanner.backpack.calendar.model.CalendarLabel
import net.skyscanner.backpack.calendar.presenter.SelectionType
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.demo.components.CalendarComponent
import net.skyscanner.backpack.demo.data.ExampleBpkCalendarController
import net.skyscanner.backpack.demo.meta.StoryKind
import net.skyscanner.backpack.demo.meta.ViewStory
import net.skyscanner.backpack.demo.ui.AndroidLayout
import net.skyscanner.backpack.demo.ui.LocalAutomationMode
import org.threeten.bp.LocalDate

@Composable
@CalendarComponent
@ViewStory("Labeled", StoryKind.DemoOnly)
fun LabeledCalendarStory(modifier: Modifier = Modifier) {
  val automationMode = LocalAutomationMode.current
  AndroidLayout(R.layout.fragment_calendar_default, modifier.fillMaxSize()) {
    val calendar = findViewById<BpkCalendar>(R.id.bpkCalendar)
    initSelectionTypeSwitcher(this, automationMode, calendar::setController)
  }
}

private fun initSelectionTypeSwitcher(
  view: View,
  automationMode: Boolean,
  onControllerChange: (ExampleBpkCalendarController) -> Unit,
) {
  val single = view.findViewById<RadioButton>(R.id.single)
  val range = view.findViewById<RadioButton>(R.id.range)
  val selectionType = view.findViewById<RadioGroup>(R.id.selection_type)

  single.text = view.context.getString(R.string.calendar_single)
  range.text = view.context.getString(R.string.calendar_range)

  selectionType.visibility = View.VISIBLE

  selectionType.setOnCheckedChangeListener { _, checkedId ->
    val controller = when (checkedId) {
      R.id.single -> ExampleBpkCalendarController(view.context, SelectionType.SINGLE, calendarLabels = createLabels(), automationMode = automationMode)
      R.id.range -> ExampleBpkCalendarController(view.context, SelectionType.RANGE, calendarLabels = createLabels(), automationMode = automationMode)
      else -> throw IllegalStateException("Unknown selection type")
    }
    onControllerChange(controller)
  }

  // this invokes the listener and does the initial assignment
  range.isChecked = true
}

private fun createLabels(startDate: LocalDate = LocalDate.now()) = mapOf(
  startDate.plusDays(1) to CalendarLabel(text = "£10", style = CalendarLabel.Style.PriceHigh),
  startDate.plusDays(2) to CalendarLabel(text = "£11", style = CalendarLabel.Style.PriceMedium),
  startDate.plusDays(3) to CalendarLabel(text = "£12", style = CalendarLabel.Style.PriceLow),
  startDate.plusDays(4) to CalendarLabel(text = "£900000000000000", style = CalendarLabel.Style.PriceLow),
  startDate.plusDays(5) to CalendarLabel(text = "£900000", style = CalendarLabel.Style.PriceLow),
)
