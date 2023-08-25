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

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import net.skyscanner.backpack.calendar2.data.CalendarCell
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.utils.applyIf

@Composable
internal fun BpkCalendarSpaceCell(
    model: CalendarCell.Space,
    modifier: Modifier = Modifier,
) {
    Spacer(
        modifier = modifier
            .height(BpkCalendarSizes.SelectionHeight)
            .applyIf(model.selected) {
                background(BpkTheme.colors.surfaceSubtle)
            },
    )
}
