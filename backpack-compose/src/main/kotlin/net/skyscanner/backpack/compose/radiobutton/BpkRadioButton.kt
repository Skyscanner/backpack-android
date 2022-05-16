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

package net.skyscanner.backpack.compose.radiobutton

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.LocalMinimumTouchTargetEnforcement
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkColor
import net.skyscanner.backpack.compose.tokens.BpkDimension
import net.skyscanner.backpack.compose.utils.dynamicColorOf

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BpkRadioButton(
  selected: Boolean,
  onClick: (() -> Unit)?,
  modifier: Modifier = Modifier,
  enabled: Boolean = true,
  interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
  CompositionLocalProvider(LocalMinimumTouchTargetEnforcement provides false) {
    RadioButton(
      selected = selected,
      onClick = onClick,
      modifier = modifier,
      enabled = enabled,
      interactionSource = interactionSource,
      colors = RadioButtonDefaults.colors(
        selectedColor = BpkTheme.colors.primary,
        unselectedColor = BpkTheme.colors.textSecondary,
        disabledColor = BpkColor.SkyGrayTint04,
      ),
    )
  }
}

@Composable
fun BpkRadioButton(
  selected: Boolean,
  text: String,
  onClick: (() -> Unit)?,
  modifier: Modifier = Modifier,
  enabled: Boolean = true,
  interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
  val rowModifier = if (onClick != null) {
    modifier.clickable(interactionSource = interactionSource, indication = null, onClick = onClick)
  } else {
    modifier
  }
  Row(
    horizontalArrangement = Arrangement.spacedBy(BpkDimension.Spacing.Sm),
    modifier = rowModifier,
    verticalAlignment = Alignment.CenterVertically,
  ) {
    BpkRadioButton(selected = selected, onClick = onClick, enabled = enabled, interactionSource = interactionSource)
    BpkText(
      text = text,
      style = BpkTheme.typography.footnote,
      color = if (enabled) BpkTheme.colors.textPrimary else dynamicColorOf(BpkColor.SkyGrayTint04, BpkColor.BlackTint03),
    )
  }
}
