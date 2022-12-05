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

package net.skyscanner.backpack.compose.surface

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import net.skyscanner.backpack.compose.theme.BpkTheme

@Composable
fun BpkSurface(
  modifier: Modifier = Modifier,
  shape: Shape = RectangleShape,
  color: Color = BpkTheme.colors.surfaceDefault,
  contentColor: Color = BpkTheme.colors.textPrimary,
  border: BorderStroke? = null,
  elevation: Dp = 0.dp,
  content: @Composable () -> Unit,
) = Surface(modifier, shape, color, contentColor, border, elevation, content)

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BpkSurface(
  onClick: () -> Unit,
  modifier: Modifier = Modifier,
  enabled: Boolean = true,
  shape: Shape = RectangleShape,
  color: Color = BpkTheme.colors.surfaceDefault,
  contentColor: Color = BpkTheme.colors.textPrimary,
  border: BorderStroke? = null,
  elevation: Dp = 0.dp,
  interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
  content: @Composable () -> Unit,
) = Surface(onClick, modifier, enabled, shape, color, contentColor, border, elevation, interactionSource, content)

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BpkSurface(
  selected: Boolean,
  onClick: () -> Unit,
  modifier: Modifier = Modifier,
  enabled: Boolean = true,
  shape: Shape = RectangleShape,
  color: Color = BpkTheme.colors.surfaceDefault,
  contentColor: Color = BpkTheme.colors.textPrimary,
  border: BorderStroke? = null,
  elevation: Dp = 0.dp,
  interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
  content: @Composable () -> Unit,
) = Surface(selected, onClick, modifier, enabled, shape, color, contentColor, border, elevation, interactionSource, content)

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BpkSurface(
  checked: Boolean,
  onCheckedChange: (Boolean) -> Unit,
  modifier: Modifier = Modifier,
  enabled: Boolean = true,
  shape: Shape = RectangleShape,
  color: Color = BpkTheme.colors.surfaceDefault,
  contentColor: Color = BpkTheme.colors.textPrimary,
  border: BorderStroke? = null,
  elevation: Dp = 0.dp,
  interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
  content: @Composable () -> Unit
) = Surface(
  checked, onCheckedChange, modifier, enabled, shape, color,
  contentColor, border, elevation, interactionSource, content,
)
