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
import net.skyscanner.backpack.calendar.BpkCalendar
import net.skyscanner.backpack.calendar.presenter.SelectionType
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.demo.data.ExampleBpkCalendarController

class DisabledCalendarStory : Story() {

  lateinit var controller: ExampleBpkCalendarController

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    controller = ExampleBpkCalendarController(requireContext(), disableDates = true)
    val bpkCalendar = view.findViewById<BpkCalendar>(R.id.bpkCalendar)
    val shiftColorsButton = view.findViewById<View>(R.id.shiftColorsButton)

    controller.isColoredCalendar = true
    initSelectionTypeSwitcher()
    shiftColorsButton.setOnClickListener {
      controller.shiftDisabledDates()
      controller.updateContent()
    }
    bpkCalendar.setController(controller)
  }

  private fun initSelectionTypeSwitcher() {
    val single = requireView().findViewById<RadioButton>(R.id.single)
    val range = requireView().findViewById<RadioButton>(R.id.range)
    val selectionType = requireView().findViewById<RadioGroup>(R.id.selection_type)
    val bpkCalendar = requireView().findViewById<BpkCalendar>(R.id.bpkCalendar)

    single.text = "Single"
    range.text = "Range"
    range.isChecked = true

    selectionType.visibility = View.VISIBLE

    selectionType.setOnCheckedChangeListener { _, checkedId ->
      when (checkedId) {
        R.id.single -> {
          controller = ExampleBpkCalendarController(requireContext(), SelectionType.SINGLE, disableDates = true)
        }
        R.id.range -> {
          controller = ExampleBpkCalendarController(requireContext(), SelectionType.RANGE, disableDates = true)
        }
      }
      controller.isColoredCalendar = true
      bpkCalendar.setController(controller)
    }
  }

  companion object {
    private const val LAYOUT_ID = "fragment_id"

    infix fun of(fragmentLayout: Int) = DisabledCalendarStory().apply {
      arguments = Bundle()
      arguments?.putInt(LAYOUT_ID, fragmentLayout)
    }
  }
}
