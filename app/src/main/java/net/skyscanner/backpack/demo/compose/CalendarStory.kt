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

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.launch
import net.skyscanner.backpack.calendar2.CalendarSelection
import net.skyscanner.backpack.compose.calendar.BpkCalendar
import net.skyscanner.backpack.compose.calendar.rememberCalendarController
import net.skyscanner.backpack.demo.R
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
@Calendar2Component
@ComposeStory("Highlighted Dates")
fun CalendarMultiSelection(modifier: Modifier = Modifier) =
    CalendarDemo(CalendarStoryType.MultiSelection, modifier)

@Composable
private fun CalendarDemo(
    type: CalendarStoryType,
    modifier: Modifier = Modifier,
) {
    val automationMode = LocalAutomationMode.current
    val floatingNotification = LocalFloatingNotification.current
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val controller =
        rememberCalendarController(
            initialParams = CalendarStoryType.createInitialParams(type),
            onSelectionChanged = { selection ->
                if (!automationMode) {
                    coroutineScope.launch {
                        floatingNotification.show(context.getSelectionMessage(selection))
                    }
                }
            },
        )

    LaunchedEffect(type, controller, automationMode) {
        when (type) {
            CalendarStoryType.SelectionWholeMonth -> controller.setSelection(CalendarStorySelection.WholeMonthRange)
            CalendarStoryType.PreselectedRange -> controller.setSelection(PreselectedRange)
            CalendarStoryType.WithLabels -> controller.setSelection(CalendarStorySelection.PreselectedRange)
            CalendarStoryType.MultiSelection -> controller.setSelection(CalendarStorySelection.PreselectedDate)
            else -> Unit
        }
    }

    BpkCalendar(controller = controller, modifier = modifier)
}

private fun Context.getSelectionMessage(
    selection: CalendarSelection,
): String {
    return when (selection) {
        CalendarSelection.None -> getString(R.string.calendar_no_selection)
        is CalendarSelection.Dates -> getString(R.string.calendar_range_selected, selection.start, selection.end)
        is CalendarSelection.Month -> getString(R.string.calendar_month_selected, selection.month)
        is CalendarSelection.Single -> getString(R.string.calendar_single_selected, selection.date)
    }
}
