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

import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import net.skyscanner.backpack.calendar.BpkCalendar
import net.skyscanner.backpack.calendar.presenter.SelectionType
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.demo.components.CalendarComponent
import net.skyscanner.backpack.demo.data.ExampleBpkCalendarController
import net.skyscanner.backpack.demo.meta.ViewStory
import net.skyscanner.backpack.demo.ui.AndroidLayout
import net.skyscanner.backpack.demo.ui.LocalAutomationMode

@Composable
@CalendarComponent
@ViewStory("Colored")
fun ColoredCalendarStory(modifier: Modifier = Modifier) {
  val automationMode = LocalAutomationMode.current
  AndroidLayout(R.layout.fragment_calendar_colored) {
    var controller = ExampleBpkCalendarController(context, SelectionType.RANGE, false, automationMode)
    val bpkCalendar = findViewById<BpkCalendar>(R.id.bpkCalendar)
    val shiftColorsButton = findViewById<View>(R.id.shiftColorsButton)

    initSelectionTypeSwitcher(this) {
      controller = it
      bpkCalendar.setController(controller)
    }

    shiftColorsButton.setOnClickListener {
      controller.newColors()
      controller.updateContent()
    }
    if (automationMode) {
      shiftColorsButton.visibility = View.INVISIBLE
    }
  }
}

class ColoredCalendarStory : Story() {

  lateinit var controller: ExampleBpkCalendarController

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    val automationMode = arguments?.getBoolean(AUTOMATION_MODE) ?: false
    controller = ExampleBpkCalendarController(requireContext(), SelectionType.RANGE, false, automationMode)
    val bpkCalendar = view.findViewById<BpkCalendar>(R.id.bpkCalendar)
    val shiftColorsButton = view.findViewById<View>(R.id.shiftColorsButton)

    initSelectionTypeSwitcher(view) {
      controller = it
      bpkCalendar.setController(controller)
    }
    shiftColorsButton.setOnClickListener {
      controller.newColors()
      controller.updateContent()
    }
    if (automationMode) {
      shiftColorsButton.visibility = View.INVISIBLE
    }
  }

  companion object {
    private const val LAYOUT_ID = "fragment_id"

    infix fun of(fragmentLayout: Int) = ColoredCalendarStory().apply {
      arguments = Bundle()
      arguments?.putInt(LAYOUT_ID, fragmentLayout)
    }
  }
}

private fun initSelectionTypeSwitcher(view: View, onControllerChange: (ExampleBpkCalendarController) -> Unit) {
  val single = view.findViewById<RadioButton>(R.id.single)
  val range = view.findViewById<RadioButton>(R.id.range)
  val selectionType = view.findViewById<RadioGroup>(R.id.selection_type)

  single.text = view.context.getString(R.string.calendar_single)
  range.text = view.context.getString(R.string.calendar_range)

  selectionType.visibility = View.VISIBLE

  selectionType.setOnCheckedChangeListener { _, checkedId ->
    val controller = when (checkedId) {
      R.id.single -> ExampleBpkCalendarController(view.context, SelectionType.SINGLE)
      R.id.range -> ExampleBpkCalendarController(view.context, SelectionType.RANGE)
      else -> throw IllegalStateException("Unknown selection type")
    }
    controller.isColoredCalendar = true
    onControllerChange(controller)
  }

  // this invokes the listener and does the initial assignment
  range.isChecked = true
}
