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

package net.skyscanner.backpack.compose.calendar.internal

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import net.skyscanner.backpack.calendar2.CalendarParams
import net.skyscanner.backpack.compose.badge.BpkBadge
import net.skyscanner.backpack.compose.badge.BpkBadgeType

@Composable
internal fun BpkCalendarBadge(
  firstVisibleItemYear: Int,
  params: CalendarParams,
  modifier: Modifier = Modifier,
) {

  var yearToBadge by remember { mutableStateOf(firstVisibleItemYear) }
  if (firstVisibleItemYear != params.now.year) {
    yearToBadge = firstVisibleItemYear
  }

  AnimatedVisibility(
    modifier = modifier,
    visible = firstVisibleItemYear != params.now.year,
    enter = fadeIn() + slideInVertically(),
    exit = slideOutVertically() + fadeOut(),
  ) {
    BpkBadge(
      text = yearToBadge.toString(),
      type = BpkBadgeType.Strong,
    )
  }
}
