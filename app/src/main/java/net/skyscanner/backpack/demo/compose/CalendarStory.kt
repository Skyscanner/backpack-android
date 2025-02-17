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

package net.skyscanner.backpack.demo.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import kotlinx.coroutines.launch
import net.skyscanner.backpack.calendar2.CalendarSelection
import net.skyscanner.backpack.compose.calendar.BpkCalendar
import net.skyscanner.backpack.compose.calendar.rememberCalendarComposeController
import net.skyscanner.backpack.demo.components.Calendar2Component
import net.skyscanner.backpack.demo.data.CalendarStorySelection
import net.skyscanner.backpack.demo.data.CalendarStorySelection.PreselectedRange
import net.skyscanner.backpack.demo.data.CalendarStoryType
import net.skyscanner.backpack.demo.meta.ComposeStory
import net.skyscanner.backpack.demo.ui.LocalAutomationMode
import net.skyscanner.backpack.demo.ui.LocalFloatingNotification
import net.skyscanner.backpack.meta.StoryKind

@Composable
@Calendar2Component
@ComposeStory("Selection Disabled", StoryKind.DemoOnly)
fun CalendarSelectionDisabledStory(modifier: Modifier = Modifier) =
    CalendarDemo(CalendarStoryType.SelectionDisabled, modifier)

@Composable
@Calendar2Component
@ComposeStory("Selection Single", StoryKind.DemoOnly)
fun CalendarSelectionSingleStory(modifier: Modifier = Modifier) =
    CalendarDemo(CalendarStoryType.SelectionSingle, modifier)

@Composable
@Calendar2Component
@ComposeStory("Selection Range", StoryKind.DemoOnly)
fun CalendarSelectionRangeStory(modifier: Modifier = Modifier) =
    CalendarDemo(CalendarStoryType.SelectionRange, modifier)

@Composable
@Calendar2Component
@ComposeStory("Selection Whole Month")
fun CalendarSelectionWholeMonthStory(modifier: Modifier = Modifier) =
    CalendarDemo(CalendarStoryType.SelectionWholeMonth, modifier)

@Composable
@Calendar2Component
@ComposeStory("Disabled weekends", StoryKind.DemoOnly)
fun CalendarDisabledWeekends(modifier: Modifier = Modifier) =
    CalendarDemo(CalendarStoryType.WithDisabledDates, modifier)

@Composable
@Calendar2Component
@ComposeStory("Day labels")
fun CalendarDayLabels(modifier: Modifier = Modifier) =
    CalendarDemo(CalendarStoryType.WithLabels, modifier)

@Composable
@Calendar2Component
@ComposeStory("Pre-selected range")
fun CalendarPreSelectedRange(modifier: Modifier = Modifier) =
    CalendarDemo(CalendarStoryType.PreselectedRange, modifier)

@Composable
@Calendar2Component
@ComposeStory("Day icon as labels")
fun CalendarSelectionIconLabelStory(modifier: Modifier = Modifier) =
    CalendarDemo(CalendarStoryType.WithIconAsLabels, modifier)

@Composable
@Calendar2Component
@ComposeStory("Year in month label no floating year")
fun CalendarNoFloatingYearLabel(modifier: Modifier = Modifier) =
    CalendarDemo(CalendarStoryType.YearLabelInMonthHeader, modifier)

@Composable
private fun CalendarDemo(
    type: CalendarStoryType,
    modifier: Modifier = Modifier,
) {
    val automationMode = LocalAutomationMode.current
    val floatingNotification = LocalFloatingNotification.current
    val coroutineScope = rememberCoroutineScope()
    val controller = rememberCalendarComposeController(initialParams = CalendarStoryType.createInitialParams(type)) {
        coroutineScope.launch {
            if (!automationMode) {
                when (it) {
                    CalendarSelection.None -> {
                        floatingNotification.show("No selection")
                    }

                    is CalendarSelection.Dates -> {
                        floatingNotification.show("Range selected ${it.start} - ${it.end}")
                    }

                    is CalendarSelection.Month -> {
                        floatingNotification.show("Month selected ${it.month}")
                    }

                    is CalendarSelection.Single -> {
                        floatingNotification.show("Single date selected ${it.date}")
                    }
                }
                floatingNotification.show(it.toString())
            }
        }
    }

    LaunchedEffect(type, controller, automationMode) {
        when (type) {
            CalendarStoryType.SelectionWholeMonth -> controller.setSelection(CalendarStorySelection.WholeMonthRange)
            CalendarStoryType.PreselectedRange -> controller.setSelection(PreselectedRange)
            CalendarStoryType.WithLabels -> controller.setSelection(CalendarStorySelection.PreselectedRange)
            else -> Unit
        }
    }

    BpkCalendar(controller = controller, modifier = modifier)
}
