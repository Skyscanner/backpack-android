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

import android.content.Context
import android.os.Bundle
import android.view.View
import net.skyscanner.backpack.calendar.BpkCalendar
import net.skyscanner.backpack.calendar.model.CalendarCellStyle
import net.skyscanner.backpack.calendar.model.CalendarColoring
import net.skyscanner.backpack.calendar.model.ColoredBucket
import net.skyscanner.backpack.calendar.presenter.HighlightedDaysAdapter
import net.skyscanner.backpack.calendar.presenter.HighlightedDaysAdapter.HighlightedDay
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.demo.data.ExampleBpkCalendarController

private class FooterViewCalendarController(
  context: Context,
) : ExampleBpkCalendarController(context) {
  override val calendarColoring: CalendarColoring = CalendarColoring(
    coloredBuckets = setOf(
      ColoredBucket(
        CalendarCellStyle.Hightlight,
        setOf(
          startDate.plusDays(2),
          endDate.minusDays(1)
        )
      ),
      ColoredBucket(
        CalendarCellStyle.Negative,
        setOf(
          startDate.plusDays(1)
        )
      )
    )
  )

  override val monthFooterAdapter =
    HighlightedDaysAdapter(
      context,
      locale,
      setOf(
        HighlightedDay(
          startDate.plusDays(1),
          "Do nothing day",
          CalendarCellStyle.Negative.color(context)
        ),
        HighlightedDay(
          startDate.plusDays(2),
          "Tea day",
          descriptionOnly = true
        ),
        HighlightedDay(
          endDate.minusDays(1),
          "I wish it was Friday day"
        )
      )
    )
}

class FooterViewCalendarStory : Story() {
  private lateinit var controller: FooterViewCalendarController

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    controller = FooterViewCalendarController(requireContext())
    val bpkCalendar = view.findViewById<BpkCalendar>(R.id.bpkCalendar)
    bpkCalendar.setController(controller)
  }

  companion object {
    private const val LAYOUT_ID = "fragment_id"

    infix fun of(fragmentLayout: Int) = FooterViewCalendarStory().apply {
      arguments = Bundle()
      arguments?.putInt(LAYOUT_ID, fragmentLayout)
    }
  }
}
