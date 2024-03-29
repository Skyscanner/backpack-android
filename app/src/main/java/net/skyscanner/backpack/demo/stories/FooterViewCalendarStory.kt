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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import net.skyscanner.backpack.calendar.BpkCalendar
import net.skyscanner.backpack.calendar.model.CalendarCellStyle
import net.skyscanner.backpack.calendar.model.CalendarColoring
import net.skyscanner.backpack.calendar.model.ColoredBucket
import net.skyscanner.backpack.calendar.presenter.HighlightedDaysAdapter
import net.skyscanner.backpack.calendar.presenter.HighlightedDaysAdapter.HighlightedDay
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.demo.components.CalendarComponent
import net.skyscanner.backpack.demo.data.ExampleBpkCalendarController
import net.skyscanner.backpack.meta.StoryKind
import net.skyscanner.backpack.demo.meta.ViewStory
import net.skyscanner.backpack.demo.ui.AndroidLayout

@Composable
@CalendarComponent
@ViewStory("Footer view", StoryKind.DemoOnly)
fun FooterViewCalendarStory(modifier: Modifier = Modifier) =
    AndroidLayout<BpkCalendar>(R.layout.fragment_calendar_default, R.id.bpkCalendar, modifier.fillMaxSize()) {
        setController(FooterViewCalendarController(context))
    }

private class FooterViewCalendarController(
    context: Context,
) : ExampleBpkCalendarController(context) {
    override val calendarColoring: CalendarColoring = CalendarColoring(
        coloredBuckets = setOf(
            ColoredBucket(
                CalendarCellStyle.Hightlight,
                setOf(
                    startDate.plusDays(2),
                    endDate.minusDays(1),
                ),
            ),
            ColoredBucket(
                CalendarCellStyle.Negative,
                setOf(
                    startDate.plusDays(1),
                ),
            ),
        ),
    )

    override val monthFooterAdapter =
        HighlightedDaysAdapter(
            context,
            locale,
            setOf(
                HighlightedDay(
                    startDate.plusDays(1),
                    "Do nothing day",
                    CalendarCellStyle.Negative.color(context),
                ),
                HighlightedDay(
                    startDate.plusDays(2),
                    "Tea day",
                    descriptionOnly = true,
                ),
                HighlightedDay(
                    endDate.minusDays(1),
                    "I wish it was Friday day",
                ),
            ),
        )
}
