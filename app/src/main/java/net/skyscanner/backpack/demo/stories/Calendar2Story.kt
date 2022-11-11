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

package net.skyscanner.backpack.demo.stories

import android.os.Bundle
import android.view.View
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import net.skyscanner.backpack.calendar2.BpkCalendar
import net.skyscanner.backpack.calendar2.CalendarEffect
import net.skyscanner.backpack.calendar2.CalendarSelection
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.demo.data.CalendarStorySelection
import net.skyscanner.backpack.demo.data.CalendarStoryType
import net.skyscanner.backpack.toast.BpkToast
import net.skyscanner.backpack.util.unsafeLazy

class Calendar2Story : Story() {

  private val calendar by unsafeLazy { requireView().findViewById<BpkCalendar>(R.id.calendar2)!! }
  private val type by unsafeLazy { requireArguments().getSerializable(TYPE) as CalendarStoryType }

  private var scope: CoroutineScope? = null

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    scope?.cancel()
    scope = CoroutineScope(Dispatchers.Main)

    val automationMode = arguments?.getBoolean(AUTOMATION_MODE) ?: false
    calendar.state
      .filter { it.selection !is CalendarSelection.None }
      .onEach {
        if (!automationMode) {
          BpkToast.makeText(requireContext(), it.selection.toString(), BpkToast.LENGTH_SHORT).show()
        }
      }
      .launchIn(scope!!)

    calendar.effects
      .filter { it is CalendarEffect.MonthSelected }
      .onEach {
        if (!automationMode) {
          BpkToast.makeText(requireContext(), it.toString(), BpkToast.LENGTH_SHORT).show()
        }
      }
      .launchIn(scope!!)

    calendar.setParams(CalendarStoryType.createInitialParams(type))
    when (type) {
      CalendarStoryType.SelectionWholeMonth -> calendar.setSelection(CalendarStorySelection.WholeMonthRange)
      CalendarStoryType.PreselectedRange -> calendar.setSelection(CalendarStorySelection.PreselectedRange)
      else -> Unit
    }
  }

  override fun onDestroyView() {
    super.onDestroyView()
    scope?.cancel()
    scope = null
  }

  companion object {
    private const val TYPE = "TYPE"

    infix fun of(type: CalendarStoryType) = Calendar2Story().apply {
      arguments = Bundle()
      arguments?.putInt(LAYOUT_ID, R.layout.fragment_calendar_2)
      arguments?.putBoolean(SCROLLABLE, false)
      arguments?.putSerializable(TYPE, type)
    }
  }
}
