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

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import net.skyscanner.backpack.calendar2.BpkCalendar
import net.skyscanner.backpack.calendar2.CalendarEffect
import net.skyscanner.backpack.calendar2.CalendarSelection
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.demo.components.Calendar2Component
import net.skyscanner.backpack.demo.data.CalendarStorySelection
import net.skyscanner.backpack.demo.data.CalendarStorySelection.PreselectedRange
import net.skyscanner.backpack.demo.data.CalendarStoryType
import net.skyscanner.backpack.demo.meta.StoryKind
import net.skyscanner.backpack.demo.meta.ViewStory
import net.skyscanner.backpack.demo.ui.AndroidLayout
import net.skyscanner.backpack.demo.ui.LocalAutomationMode
import net.skyscanner.backpack.toast.BpkToast

@Composable
@Calendar2Component
@ViewStory("Selection Disabled", StoryKind.DemoOnly)
fun CalendarSelectionDisabledStory(modifier: Modifier = Modifier) =
  Calendar2Demo(CalendarStoryType.SelectionDisabled, modifier)

@Composable
@Calendar2Component
@ViewStory("Selection Single", StoryKind.DemoOnly)
fun CalendarSelectionSingleStory(modifier: Modifier = Modifier) =
  Calendar2Demo(CalendarStoryType.SelectionSingle, modifier)

@Composable
@Calendar2Component
@ViewStory("Selection Range", StoryKind.DemoOnly)
fun CalendarSelectionRangeStory(modifier: Modifier = Modifier) =
  Calendar2Demo(CalendarStoryType.SelectionRange, modifier)

@Composable
@Calendar2Component
@ViewStory("Selection Whole Month")
fun CalendarSelectionWholeMonthStory(modifier: Modifier = Modifier) =
  Calendar2Demo(CalendarStoryType.SelectionWholeMonth, modifier)

@Composable
@Calendar2Component
@ViewStory("Disabled weekends", StoryKind.DemoOnly)
fun CalendarDisabledWeekends(modifier: Modifier = Modifier) =
  Calendar2Demo(CalendarStoryType.WithDisabledDates, modifier)

@Composable
@Calendar2Component
@ViewStory("Day labels")
fun CalendarDayLabels(modifier: Modifier = Modifier) =
  Calendar2Demo(CalendarStoryType.WithLabels, modifier)

@Composable
@Calendar2Component
@ViewStory("Pre-selected range")
fun CalendarPreSelectedRange(modifier: Modifier = Modifier) =
  Calendar2Demo(CalendarStoryType.PreselectedRange, modifier)

@Composable
private fun Calendar2Demo(
  type: CalendarStoryType,
  modifier: Modifier = Modifier,
) {
  val scope = rememberCoroutineScope()
  val automationMode = LocalAutomationMode.current

  AndroidLayout<BpkCalendar>(R.layout.fragment_calendar_2, R.id.calendar2, modifier.fillMaxSize()) {
    state
      .filter { it.selection !is CalendarSelection.None }
      .onEach {
        if (!automationMode) {
          BpkToast.makeText(context, it.selection.toString(), BpkToast.LENGTH_SHORT).show()
        }
      }
      .launchIn(scope)

    effects
      .filter { it is CalendarEffect.MonthSelected }
      .onEach {
        if (!automationMode) {
          BpkToast.makeText(context, it.toString(), BpkToast.LENGTH_SHORT).show()
        }
      }
      .launchIn(scope)

    setParams(CalendarStoryType.createInitialParams(type))
    when (type) {
      CalendarStoryType.SelectionWholeMonth -> setSelection(CalendarStorySelection.WholeMonthRange)
      CalendarStoryType.PreselectedRange -> setSelection(PreselectedRange)
      CalendarStoryType.WithLabels -> setSelection(CalendarStorySelection.PreselectedRange)
      else -> Unit
    }
  }
}
