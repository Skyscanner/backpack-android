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
import net.skyscanner.backpack.calendar.model.CalendarLabel
import net.skyscanner.backpack.calendar.presenter.SelectionType
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.demo.data.ExampleBpkCalendarController
import org.threeten.bp.LocalDate

class LabeledCalendarStory : Story() {

  lateinit var controller: ExampleBpkCalendarController

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    val automationMode = arguments?.getBoolean(AUTOMATION_MODE) ?: false
    val calendar = view.findViewById<BpkCalendar>(R.id.bpkCalendar)
    controller = ExampleBpkCalendarController(
      requireContext(),
      SelectionType.RANGE,
      disableDates = false,
      calendarLabels = createLabels(),
      automationMode = automationMode
    )
    calendar.setController(controller)
    initSelectionTypeSwitcher()
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
          controller = ExampleBpkCalendarController(requireContext(), SelectionType.SINGLE, calendarLabels = createLabels())
        }
        R.id.range -> {
          controller = ExampleBpkCalendarController(requireContext(), SelectionType.RANGE, calendarLabels = createLabels())
        }
      }
      bpkCalendar.setController(controller)
    }
  }

  private fun createLabels(startDate: LocalDate = LocalDate.now()) = mapOf(
    startDate.plusDays(1) to CalendarLabel(text = "£10", style = CalendarLabel.Style.PriceHigh),
    startDate.plusDays(2) to CalendarLabel(text = "£11", style = CalendarLabel.Style.PriceMedium),
    startDate.plusDays(3) to CalendarLabel(text = "£12", style = CalendarLabel.Style.PriceLow),
    startDate.plusDays(4) to CalendarLabel(text = "£900000000000000", style = CalendarLabel.Style.PriceLow),
    startDate.plusDays(5) to CalendarLabel(text = "£900000", style = CalendarLabel.Style.PriceLow),
  )

  companion object {
    private const val LAYOUT_ID = "fragment_id"

    infix fun of(fragmentLayout: Int) = LabeledCalendarStory().apply {
      arguments = Bundle()
      arguments?.putInt(LAYOUT_ID, fragmentLayout)
    }
  }
}
