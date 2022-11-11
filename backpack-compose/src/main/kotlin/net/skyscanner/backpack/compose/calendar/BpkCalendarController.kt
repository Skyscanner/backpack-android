/*
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

package net.skyscanner.backpack.compose.calendar

import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import kotlinx.coroutines.CoroutineScope
import net.skyscanner.backpack.calendar2.CalendarComponent
import net.skyscanner.backpack.calendar2.CalendarParams
import net.skyscanner.backpack.calendar2.CalendarSelection
import net.skyscanner.backpack.calendar2.data.CalendarStateMachine
import org.threeten.bp.LocalDate
import java.io.Serializable

@Stable
class BpkCalendarController private constructor(
  internal val lazyGridState: LazyGridState,
  internal val stateMachine: CalendarStateMachine,
) : CalendarComponent by stateMachine {

  constructor(
    initialParams: CalendarParams,
    coroutineScope: CoroutineScope,
    lazyGridState: LazyGridState = LazyGridState(),
  ) : this(lazyGridState, CalendarStateMachine(coroutineScope, initialParams))

  /**
   * Scrolls to a specific date in a calendar.
   * Does nothing if the date is out of range.
   */
  suspend fun scrollToDate(date: LocalDate) {
    val index = state.value.cells.indexOf(date)
    if (index >= 0) {
      lazyGridState.scrollToItem(index)
    }
  }

  /**
   * Scrolls with animation to a specific date in a calendar.
   * Does nothing if the date is out of range.
   */
  suspend fun smoothScrollToDate(date: LocalDate) {
    val index = state.value.cells.indexOf(date)
    if (index >= 0) {
      lazyGridState.animateScrollToItem(index)
    }
  }

  internal val firstVisibleItemYear by derivedStateOf {
    state.value.cells[lazyGridState.firstVisibleItemIndex].yearMonth.year
  }

}

@Composable
fun rememberCalendarController(
  initialParams: CalendarParams,
  coroutineScope: CoroutineScope = rememberCoroutineScope(),
  lazyGridState: LazyGridState = rememberLazyGridState(),
): BpkCalendarController =
  rememberSaveable(
    coroutineScope, lazyGridState,
    saver = Saver<BpkCalendarController, Serializable>(
      save = { it.state.value.selection },
      restore = {
        BpkCalendarController(initialParams, coroutineScope, lazyGridState).apply {
          setSelection(it as CalendarSelection)
        }
      },
    ),
    init = {
      BpkCalendarController(initialParams, coroutineScope, lazyGridState)
    }
  )
