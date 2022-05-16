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

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.offset
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.TriStateCheckbox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.unit.dp
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkColor
import net.skyscanner.backpack.compose.utils.dynamicColorOf

@Composable
fun BpkCheckbox(
  text: String,
  checked: Boolean,
  onCheckedChange: (Boolean) -> Unit,
  modifier: Modifier = Modifier,
  enabled: Boolean = true,
  interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
  BpkCheckbox(
    text = text,
    state = ToggleableState(checked),
    onClick = { onCheckedChange(!checked) },
    interactionSource = interactionSource,
    enabled = enabled,
    modifier = modifier,
  )
}

@Composable
fun BpkCheckbox(
  text: String,
  state: ToggleableState,
  onClick: () -> Unit,
  modifier: Modifier = Modifier,
  enabled: Boolean = true,
  interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
  Row(
    modifier = modifier,
    verticalAlignment = Alignment.CenterVertically,
  ) {

    TriStateCheckbox(
      state = state,
      onClick = onClick,
      enabled = enabled,
      modifier = Modifier.scale(0.89f),
      interactionSource = interactionSource,
      colors = CheckboxDefaults.colors(
        checkedColor = BpkTheme.colors.primary,
      ),
    )

    BpkText(
      text = text,
      style = BpkTheme.typography.footnote,
      modifier = Modifier
        .offset(x = (-8).dp)
        .clickable(
        enabled = enabled,
        onClickLabel = null,
        role = Role.Checkbox,
        interactionSource = interactionSource,
        indication = null,
        onClick = onClick,
      ),
      color = when {
        enabled -> BpkTheme.colors.textPrimary
        else -> dynamicColorOf(BpkColor.SkyGrayTint04, BpkColor.BlackTint06)
      },
    )
  }
}
