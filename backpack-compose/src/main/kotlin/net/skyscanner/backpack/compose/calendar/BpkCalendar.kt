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

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import net.skyscanner.backpack.calendar2.data.CalendarInteraction
import net.skyscanner.backpack.compose.calendar.internal.BpkCalendarBadge
import net.skyscanner.backpack.compose.calendar.internal.BpkCalendarGrid
import net.skyscanner.backpack.compose.calendar.internal.BpkCalendarHeader
import net.skyscanner.backpack.compose.tokens.BpkSpacing

@Composable
fun BpkCalendar(
    controller: BpkCalendarComposeController,
    modifier: Modifier = Modifier,
    customDateHandling: ((CalendarInteraction) -> Unit)? = null,
) {

    val state = controller.state

    Column(modifier = modifier) {

        BpkCalendarHeader(
            params = state.params,
            modifier = Modifier.fillMaxWidth(),
        )

        Box(modifier = Modifier.weight(1f)) {

            BpkCalendarGrid(
                state = state,
                lazyGridState = controller.lazyGridState,
                onClick = customDateHandling ?: controller::onClick,
                modifier = Modifier.fillMaxSize(),
            )

            if (!state.params.yearLabelInMonthHeader) {
                BpkCalendarBadge(
                    firstVisibleItemYear = controller.firstVisibleItemYear,
                    params = state.params,
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(top = BpkSpacing.Base),
                )
            }
        }
    }
}
