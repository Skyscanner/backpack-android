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

package net.skyscanner.backpack.compose.cardbutton

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import net.skyscanner.backpack.compose.cardbutton.internal.BpkSaveCardButtonImpl
import net.skyscanner.backpack.compose.cardbutton.internal.BpkShareCardButtonImpl

enum class BpkCardButtonStyle {
  Default,
  Contained,
  OnDark,
}

enum class BpkCardButtonSize {
  Default,
  Small,
}

@Composable
fun BpkSaveButton(
  checked: Boolean,
  contentDescription: String,
  onCheckedChange: (Boolean) -> Unit,
  modifier: Modifier = Modifier,
  size: BpkCardButtonSize = BpkCardButtonSize.Default,
  style: BpkCardButtonStyle = BpkCardButtonStyle.Default,
) {
  BpkSaveCardButtonImpl(
    checked = checked,
    contentDescription = contentDescription,
    style = style,
    size = size,
    onCheckedChange = onCheckedChange,
    modifier = modifier,
  )
}

@Composable
fun BpkShareButton(
  contentDescription: String,
  onClick: () -> Unit,
  modifier: Modifier = Modifier,
  size: BpkCardButtonSize = BpkCardButtonSize.Default,
  style: BpkCardButtonStyle = BpkCardButtonStyle.Default,
) {
  BpkShareCardButtonImpl(
    contentDescription = contentDescription,
    style = style,
    size = size,
    onClick = onClick,
    modifier = modifier,
  )
}
