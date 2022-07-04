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

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.LocalMinimumTouchTargetEnforcement
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.invisibleToUser
import androidx.compose.ui.semantics.semantics
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkDimension
import net.skyscanner.backpack.compose.utils.BpkToggleableContent
import net.skyscanner.backpack.compose.utils.applyIf
import net.skyscanner.backpack.compose.utils.textDisabled

@Composable
fun BpkRadioButton(
  text: String,
  selected: Boolean,
  onClick: (() -> Unit)?,
  modifier: Modifier = Modifier,
  enabled: Boolean = true,
  interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
  BpkRadioButton(
    selected = selected,
    onClick = onClick,
    modifier = modifier,
    enabled = enabled,
    interactionSource = interactionSource,
    content = { BpkText(text) },
  )
}

@Composable
fun BpkRadioButton(
  selected: Boolean,
  onClick: (() -> Unit)?,
  modifier: Modifier = Modifier,
  enabled: Boolean = true,
  interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
  content: @Composable RowScope.(Boolean) -> Unit,
) {

  Row(
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.spacedBy(BpkDimension.Spacing.Md),
    modifier = modifier.applyIf(onClick != null) {
      selectable(
        selected = selected,
        interactionSource = interactionSource,
        indication = null,
        role = Role.RadioButton,
        onClick = onClick!!,
      )
    },
  ) {

    BpkRadioButtonImpl(
      selected = selected,
      onClick = onClick,
      enabled = enabled,
      interactionSource = interactionSource,
    )

    BpkToggleableContent(
      enabled = enabled,
      content = { content(selected) },
    )
  }
}

@OptIn(ExperimentalMaterialApi::class, ExperimentalComposeUiApi::class)
@Composable
private fun BpkRadioButtonImpl(
  selected: Boolean,
  onClick: (() -> Unit)?,
  modifier: Modifier = Modifier,
  enabled: Boolean,
  interactionSource: MutableInteractionSource,
) {
  // our design system isn't designed with the minimum touch target in mind at the moment.
  // Disable the enforcement to avoid the extra padding
  CompositionLocalProvider(LocalMinimumTouchTargetEnforcement provides false) {
    RadioButton(
      selected = selected,
      onClick = onClick,
      modifier = modifier.semantics { invisibleToUser() },
      enabled = enabled,
      interactionSource = interactionSource,
      colors = RadioButtonDefaults.colors(
        selectedColor = BpkTheme.colors.primary,
        unselectedColor = BpkTheme.colors.textSecondary,
        disabledColor = BpkTheme.colors.textDisabled,
      ),
    )
  }
}
