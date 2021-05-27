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

package net.skyscanner.backpack.demo.stories

import android.os.Bundle
import android.view.View
import java.time.LocalDate
import java.time.Period
import net.skyscanner.backpack.calendar2.BpkCalendar
import net.skyscanner.backpack.calendar2.CalendarParams
import net.skyscanner.backpack.demo.R

class Calendar2Story : Story() {

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    val calendar = view.findViewById<BpkCalendar>(R.id.calendar2)!!

    calendar.setParams(
      CalendarParams(
        range = LocalDate.now() - Period.ofYears(1)..(LocalDate.now() + Period.ofYears(1)),
        selectionMode = CalendarParams.SelectionMode.Range,
        cells = mapOf(
          LocalDate.of(2020, 6, 25) to CalendarParams.Info(status = CalendarParams.Status.Disabled),
          LocalDate.of(2020, 6, 26) to CalendarParams.Info(label = "Test"),
          LocalDate.of(2020, 6, 27) to CalendarParams.Info(status = CalendarParams.Status.Positive),
          LocalDate.of(2020, 6, 28) to CalendarParams.Info(status = CalendarParams.Status.Neutral),
          LocalDate.of(2020, 6, 29) to CalendarParams.Info(status = CalendarParams.Status.Negative),
          LocalDate.of(2020, 6, 30) to CalendarParams.Info(status = CalendarParams.Status.Empty),
        ),
      )
    )
  }

  companion object {
    private const val LAYOUT_ID = "fragment_id"

    infix fun of(fragmentLayout: Int) = Calendar2Story().apply {
      arguments = Bundle()
      arguments?.putInt(LAYOUT_ID, fragmentLayout)
      arguments?.putBoolean(SCROLLABLE, false)
    }
  }
}
