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

package net.skyscanner.backpack.compose.utils

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import net.skyscanner.backpack.compose.theme.BpkTheme

@Composable
internal fun InteractionSource.animateAsColor(
  default: Color,
  pressed: Color = default,
  focused: Color = pressed,
): Color {
  if (default == pressed && default == focused) {
    return default
  }
  val isPressed by collectIsPressedAsState()
  val isFocused by collectIsFocusedAsState()
  val target = when {
    isPressed -> pressed
    isFocused -> focused
    else -> default
  }
  return animateColorAsState(target).value
}

@Composable
internal fun dynamicColorOf(light: Color, dark: Color): Color =
  if (BpkTheme.colors.isLight) light else dark
