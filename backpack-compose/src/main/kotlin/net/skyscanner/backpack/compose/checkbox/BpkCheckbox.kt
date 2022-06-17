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

package net.skyscanner.backpack.compose.checkbox

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.triStateToggleable
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.LocalMinimumTouchTargetEnforcement
import androidx.compose.material.TriStateCheckbox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.invisibleToUser
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.state.ToggleableState
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkColor
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.compose.utils.BpkToggleableContent
import net.skyscanner.backpack.compose.utils.applyIf
import net.skyscanner.backpack.compose.utils.dynamicColorOf

@Composable
fun BpkCheckbox(
  text: String,
  checked: Boolean,
  onCheckedChange: ((Boolean) -> Unit)?,
  modifier: Modifier = Modifier,
  enabled: Boolean = true,
  interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
  BpkCheckbox(
    checked = checked,
    onCheckedChange = onCheckedChange,
    interactionSource = interactionSource,
    enabled = enabled,
    modifier = modifier,
    content = { BpkText(text) },
  )
}

@Composable
fun BpkCheckbox(
  text: String,
  state: ToggleableState,
  onClick: (() -> Unit)?,
  modifier: Modifier = Modifier,
  enabled: Boolean = true,
  interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
  BpkCheckbox(
    state = state,
    onClick = onClick,
    modifier = modifier,
    enabled = enabled,
    interactionSource = interactionSource,
    content = { BpkText(text) },
  )
}

@Composable
fun BpkCheckbox(
  checked: Boolean,
  onCheckedChange: ((Boolean) -> Unit)?,
  modifier: Modifier = Modifier,
  enabled: Boolean = true,
  interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
  content: @Composable RowScope.(Boolean) -> Unit,
) {
  BpkCheckbox(
    state = ToggleableState(checked),
    onClick = if (onCheckedChange != null) {
      { onCheckedChange(!checked) }
    } else null,
    interactionSource = interactionSource,
    enabled = enabled,
    modifier = modifier,
    content = { content(checked) },
  )
}

@Composable
fun BpkCheckbox(
  state: ToggleableState,
  onClick: (() -> Unit)?,
  modifier: Modifier = Modifier,
  enabled: Boolean = true,
  interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
  content: @Composable RowScope.(ToggleableState) -> Unit,
) {
  Row(
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.SpaceBetween,
    modifier = modifier.applyIf(onClick != null) {
      triStateToggleable(
        state = state,
        interactionSource = interactionSource,
        indication = null,
        enabled = enabled,
        role = Role.Checkbox,
        onClick = onClick!!,
      )
    },
  ) {

    BpkCheckboxImpl(
      modifier = Modifier.padding(end = BpkSpacing.Md),
      state = state,
      enabled = enabled,
      interactionSource = interactionSource,
      onClick = onClick,
    )

    BpkToggleableContent(
      enabled = enabled,
      content = { content(state) }
    )
  }
}

@OptIn(ExperimentalMaterialApi::class, androidx.compose.ui.ExperimentalComposeUiApi::class)
@Composable
private fun BpkCheckboxImpl(
  state: ToggleableState,
  onClick: (() -> Unit)?,
  modifier: Modifier = Modifier,
  enabled: Boolean,
  interactionSource: MutableInteractionSource,
) {
  // our design system isn't designed with the minimum touch target in mind at the moment.
  // Disable the enforcement to avoid the extra padding
  CompositionLocalProvider(LocalMinimumTouchTargetEnforcement provides false) {
    TriStateCheckbox(
      state = state,
      onClick = onClick,
      enabled = enabled,
      modifier = modifier.scale(BackpackCheckboxScale).semantics { invisibleToUser() },
      interactionSource = interactionSource,
      colors = CheckboxDefaults.colors(
        checkedColor = BpkTheme.colors.primary,
        uncheckedColor = BpkTheme.colors.textSecondary,
        checkmarkColor = BpkTheme.colors.background,
        disabledColor = dynamicColorOf(BpkColor.SkyGrayTint04, BpkColor.BlackTint06),
        disabledIndeterminateColor = dynamicColorOf(BpkColor.SkyGrayTint04, BpkColor.BlackTint06),
      ),
    )
  }
}

private const val BackpackCheckboxScale = 0.89f
