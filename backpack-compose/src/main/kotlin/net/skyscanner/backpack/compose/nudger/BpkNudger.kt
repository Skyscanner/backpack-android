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

package net.skyscanner.backpack.compose.nudger

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import net.skyscanner.backpack.compose.button.BpkButton
import net.skyscanner.backpack.compose.button.BpkButtonSize
import net.skyscanner.backpack.compose.button.BpkButtonType
import net.skyscanner.backpack.compose.fieldset.BpkFieldStatus
import net.skyscanner.backpack.compose.fieldset.LocalFieldStatus
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.compose.tokens.Minus
import net.skyscanner.backpack.compose.tokens.Plus

@Composable
fun BpkNudger(
  value: Int,
  onValueChange: (Int) -> Unit,
  range: IntRange,
  modifier: Modifier = Modifier,
  enabled: Boolean = LocalFieldStatus.current != BpkFieldStatus.Disabled,
) {

  val trimmedValue = value.coerceIn(range)

  Row(
    modifier = modifier,
    verticalAlignment = Alignment.CenterVertically,
  ) {

    BpkButton(
      icon = BpkIcon.Minus,
      contentDescription = "-",
      enabled = enabled && trimmedValue > range.first,
      size = BpkButtonSize.Default,
      type = BpkButtonType.Secondary,
      onClick = { onValueChange(trimmedValue - 1) },
    )

    BpkText(
      text = trimmedValue.toString(),
      style = BpkTheme.typography.heading5,
      maxLines = 1,
      textAlign = TextAlign.Center,
      modifier = Modifier
        .padding(horizontal = BpkSpacing.Md)
        .widthIn(min = BpkSpacing.Lg),
      color = animateColorAsState(
        when {
          enabled -> BpkTheme.colors.textPrimary
          else -> BpkTheme.colors.textDisabled
        }
      ).value,
    )

    BpkButton(
      icon = BpkIcon.Plus,
      contentDescription = "+",
      enabled = enabled && trimmedValue < range.last,
      size = BpkButtonSize.Default,
      type = BpkButtonType.Secondary,
      onClick = { onValueChange(trimmedValue + 1) },
    )
  }
}
