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

package net.skyscanner.backpack.compose.calendar2.internal

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import net.skyscanner.backpack.calendar2.CalendarState
import net.skyscanner.backpack.calendar2.data.CalendarCell
import net.skyscanner.backpack.calendar2.data.CalendarInteraction

@Composable
internal fun BpkCalendarGrid(
  state: CalendarState,
  lazyGridState: LazyGridState,
  modifier: Modifier = Modifier,
  onClick: (CalendarInteraction) -> Unit,
) {
  LazyVerticalGrid(
    modifier = modifier,
    state = lazyGridState,
    columns = GridCells.Fixed(COLUMN_COUNT),
  ) {

    items(
      count = state.cells.size,
      span = { index ->
        when (state.cells[index]) {
          is CalendarCell.Header -> GridItemSpan(COLUMN_COUNT)
          else -> GridItemSpan(1)
        }
      },
      key = { index ->
        when (val item = state.cells[index]) {
          is CalendarCell.Day -> item.date
          is CalendarCell.Header -> item.yearMonth
          is CalendarCell.Space -> index
        }
      },
      contentType = { index ->
        when (state.cells[index]) {
          is CalendarCell.Day -> CONTENT_TYPE_DAY
          is CalendarCell.Header -> CONTENT_TYPE_HEADER
          is CalendarCell.Space -> CONTENT_TYPE_SPACE
        }
      },
      itemContent = { index ->
        when (val item = state.cells[index]) {
          is CalendarCell.Day -> BpkCalendarDayCell(item) { onClick(CalendarInteraction.DateClicked(it)) }
          is CalendarCell.Header -> BpkCalendarHeaderCell(item) { onClick(CalendarInteraction.SelectMonthClicked(it)) }
          is CalendarCell.Space -> BpkCalendarSpaceCell(item)
        }
      },
    )
  }
}

private const val DAYS_IN_WEEK = 7
private const val COLUMN_COUNT = DAYS_IN_WEEK
private const val CONTENT_TYPE_HEADER = 1
private const val CONTENT_TYPE_DAY = 2
private const val CONTENT_TYPE_SPACE = 3
