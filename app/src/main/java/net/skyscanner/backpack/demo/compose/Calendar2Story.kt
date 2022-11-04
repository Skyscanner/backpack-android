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
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import kotlinx.coroutines.flow.filter
import net.skyscanner.backpack.calendar2.CalendarEffect
import net.skyscanner.backpack.calendar2.CalendarSelection
import net.skyscanner.backpack.compose.calendar2.BpkCalendar
import net.skyscanner.backpack.compose.calendar2.rememberCalendarController
import net.skyscanner.backpack.demo.data.CalendarStorySelection
import net.skyscanner.backpack.demo.data.CalendarStoryType
import net.skyscanner.backpack.toast.BpkToast
import org.threeten.bp.Month
import org.threeten.bp.YearMonth

@Composable
fun Calendar2Story(
  type: CalendarStoryType,
  modifier: Modifier = Modifier,
) {
  val context = LocalContext.current
  val automationMode = LocalInspectionMode.current
  val controller = rememberCalendarController(initialParams = CalendarStoryType.createInitialParams(type))

  LaunchedEffect(type, controller, automationMode) {
    when (type) {
      CalendarStoryType.SelectionWholeMonth -> controller.setSelection(CalendarStorySelection.WholeMonthRange)
      CalendarStoryType.PreselectedRange -> controller.setSelection(CalendarStorySelection.PreselectedRange)
      else -> Unit
    }

    if (automationMode) {
      if (type == CalendarStoryType.SelectionWholeMonth) {
        controller.setSelection(
          CalendarSelection.Month(YearMonth.of(2019, Month.JANUARY))
        )
      } else {
        controller.setSelection(
          CalendarSelection.Dates(
            controller.state.value.params.now.plusDays(5),
            controller.state.value.params.now.plusDays(10),
          )
        )
      }
    }

    controller.state
      .filter { it.selection !is CalendarSelection.None }
      .collect {
        if (!automationMode) {
          BpkToast.makeText(context, it.selection.toString(), BpkToast.LENGTH_SHORT).show()
        }
      }

    controller.effects
      .filter { it is CalendarEffect.MonthSelected }
      .collect {
        if (!automationMode) {
          BpkToast.makeText(context, it.toString(), BpkToast.LENGTH_SHORT).show()
        }
      }
  }

  BpkCalendar(controller = controller, modifier = modifier)
}
