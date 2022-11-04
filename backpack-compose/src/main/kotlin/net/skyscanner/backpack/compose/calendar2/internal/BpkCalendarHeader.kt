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

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.invisibleToUser
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import net.skyscanner.backpack.calendar2.CalendarParams
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.theme.BpkTheme

@OptIn(ExperimentalComposeUiApi::class)
@Composable
internal fun BpkCalendarHeader(
  params: CalendarParams,
  modifier: Modifier = Modifier,
) {
  Column(modifier = modifier.semantics { invisibleToUser() }) {
    Row(
      modifier = Modifier.fillMaxWidth(),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.SpaceAround,
    ) {
      var current = params.weekFields.firstDayOfWeek
      do {
        BpkText(
          text = current.getDisplayName(params.dayOfWeekText, params.locale).uppercase(params.locale),
          style = BpkTheme.typography.label2,
          color = BpkTheme.colors.textSecondary,
          textAlign = TextAlign.Center,
          maxLines = 1,
        )
        current += 1
      } while (current != params.weekFields.firstDayOfWeek)
    }
    Divider(color = BpkTheme.colors.line, thickness = 1.dp)
  }
}
