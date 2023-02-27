package net.skyscanner.backpack.compose.flightleg.internal

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkSpacing

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

@Composable
fun BpkAccessoryTitleDetails(
  title: AnnotatedString,
  modifier: Modifier = Modifier,
  titleStyle: TextStyle = BpkTheme.typography.heading5,
  details: List<AnnotatedString>? = null,
  textAlignment: Alignment.Horizontal = Alignment.Start,
  accessoryView: @Composable (BoxScope.() -> Unit)? = null
) {
  Row(modifier = modifier) {
    accessoryView?.let {
      Box(content = it)
      Spacer(modifier = Modifier.width(BpkSpacing.Base))
    }

    Column(
      horizontalAlignment = textAlignment,
    ) {
      BpkText(
        text = title,
        style = titleStyle,
        color = BpkTheme.colors.textPrimary,
      )
      details?.let { details ->
        details.map {
          BpkText(
            text = it,
            style = BpkTheme.typography.caption,
            color = BpkTheme.colors.textSecondary,
          )
        }
      }
    }
  }
}
