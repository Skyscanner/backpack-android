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

package net.skyscanner.backpack.compose.switch

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.LocalContentColor
import androidx.compose.material.LocalMinimumTouchTargetEnforcement
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
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
import net.skyscanner.backpack.compose.tokens.BpkColor
import net.skyscanner.backpack.compose.utils.dynamicColorOf

@Composable
fun BpkSwitch(
  text: String,
  checked: Boolean,
  onCheckedChange: ((Boolean) -> Unit)?,
  modifier: Modifier = Modifier,
  enabled: Boolean = true,
  interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
  BpkSwitch(
    checked = checked,
    onCheckedChange = onCheckedChange,
    modifier = modifier,
    enabled = enabled,
    interactionSource = interactionSource,
  ) {
    BpkText(text = text)
  }
}

@Composable
fun BpkSwitch(
  checked: Boolean,
  onCheckedChange: ((Boolean) -> Unit)?,
  modifier: Modifier = Modifier,
  enabled: Boolean = true,
  interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
  content: @Composable RowScope.(Boolean) -> Unit,
) {
  val rowModifier = if (onCheckedChange != null) {
    modifier.toggleable(
      value = checked,
      role = Role.Switch,
      interactionSource = interactionSource,
      indication = null,
      onValueChange = onCheckedChange,
    )
  } else {
    modifier
  }
  Row(
    horizontalArrangement = Arrangement.SpaceBetween,
    modifier = rowModifier,
    verticalAlignment = Alignment.CenterVertically,
  ) {
    val contentColor =
      if (enabled) BpkTheme.colors.textPrimary else dynamicColorOf(BpkColor.SkyGrayTint04, BpkColor.BlackTint06)
    CompositionLocalProvider(
      LocalContentColor provides contentColor,
      LocalTextStyle provides BpkTheme.typography.footnote,
    ) {
      content(checked)
    }
    BpkSwitchImpl(
      checked = checked,
      onCheckedChange = onCheckedChange,
      enabled = enabled,
      interactionSource = interactionSource,
    )
  }
}

@OptIn(ExperimentalMaterialApi::class, ExperimentalComposeUiApi::class)
@Composable
private fun BpkSwitchImpl(
  checked: Boolean,
  onCheckedChange: ((Boolean) -> Unit)?,
  modifier: Modifier = Modifier,
  enabled: Boolean = true,
  interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
  // our design system isn't designed with the minimum touch target in mind at the moment.
  // Disable the enforcement to avoid the extra padding
  CompositionLocalProvider(LocalMinimumTouchTargetEnforcement provides false) {
    val uncheckedTrackColor = dynamicColorOf(BpkColor.SkyGrayTint06, BpkColor.BlackTint03)
    Switch(
      checked = checked,
      onCheckedChange = onCheckedChange,
      modifier = modifier.semantics { invisibleToUser() },
      enabled = enabled,
      interactionSource = interactionSource,
      colors = SwitchDefaults.colors(
        checkedThumbColor = BpkTheme.colors.primary,
        checkedTrackColor = BpkColor.SkyBlueTint03,
        checkedTrackAlpha = 1f,
        uncheckedThumbColor = BpkColor.White,
        uncheckedTrackColor = uncheckedTrackColor,
        uncheckedTrackAlpha = 1f,
        disabledCheckedThumbColor = BpkTheme.colors.primary,
        disabledCheckedTrackColor = BpkColor.SkyBlueTint03,
        disabledUncheckedThumbColor = BpkColor.White,
        disabledUncheckedTrackColor = uncheckedTrackColor,
      ),
    )
  }
}
