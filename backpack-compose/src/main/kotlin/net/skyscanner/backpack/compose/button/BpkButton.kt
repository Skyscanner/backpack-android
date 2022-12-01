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

package net.skyscanner.backpack.compose.button

import androidx.annotation.DrawableRes
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import net.skyscanner.backpack.compose.button.internal.BpkButtonImpl
import net.skyscanner.backpack.compose.button.internal.ButtonDrawable
import net.skyscanner.backpack.compose.button.internal.ButtonIcon
import net.skyscanner.backpack.compose.button.internal.ButtonText
import net.skyscanner.backpack.compose.button.internal.minHeight
import net.skyscanner.backpack.compose.icon.BpkIcon

enum class BpkButtonIconPosition {
  Start,
  End,
}

enum class BpkButtonSize {
  Default,
  Large,
}

enum class BpkButtonType {
  Primary,
  Secondary,
  Featured,
  PrimaryOnDark,
  PrimaryOnLight,
  SecondaryOnDark,
  Destructive,
  Link,
  LinkOnDark,
}

@Composable
fun BpkButton(
  text: String,
  modifier: Modifier = Modifier,
  size: BpkButtonSize = DefaultSize,
  type: BpkButtonType = DefaultType,
  enabled: Boolean = DefaultEnabled,
  loading: Boolean = DefaultLoading,
  interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
  onClick: () -> Unit,
) {
  BpkButtonImpl(
    size = size,
    type = type,
    enabled = enabled,
    loading = loading,
    interactionSource = interactionSource,
    modifier = modifier,
    onClick = onClick,
    content = { ButtonText(text) },
  )
}

@Composable
fun BpkButton(
  icon: BpkIcon,
  contentDescription: String,
  modifier: Modifier = Modifier,
  size: BpkButtonSize = DefaultSize,
  type: BpkButtonType = DefaultType,
  enabled: Boolean = DefaultEnabled,
  loading: Boolean = DefaultLoading,
  interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
  onClick: () -> Unit,
) {
  BpkButtonImpl(
    size = size,
    type = type,
    enabled = enabled,
    loading = loading,
    interactionSource = interactionSource,
    modifier = modifier.requiredWidth(size.minHeight),
    onClick = onClick,
    content = { ButtonIcon(icon, contentDescription, size) },
  )
}

@Composable
fun BpkButton(
  text: String,
  icon: BpkIcon,
  position: BpkButtonIconPosition,
  modifier: Modifier = Modifier,
  size: BpkButtonSize = DefaultSize,
  type: BpkButtonType = DefaultType,
  enabled: Boolean = DefaultEnabled,
  loading: Boolean = DefaultLoading,
  interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
  onClick: () -> Unit,
) {
  BpkButtonImpl(
    size = size,
    type = type,
    enabled = enabled,
    loading = loading,
    interactionSource = interactionSource,
    modifier = modifier,
    onClick = onClick,
  ) {
    when (position) {
      BpkButtonIconPosition.Start -> {
        ButtonIcon(icon, null, size)
        ButtonText(text)
      }
      BpkButtonIconPosition.End -> {
        ButtonText(text)
        ButtonIcon(icon, null, size)
      }
    }
  }
}

@Composable
fun BpkButton(
  @DrawableRes iconResource: Int,
  contentDescription: String,
  modifier: Modifier = Modifier,
  size: BpkButtonSize = DefaultSize,
  type: BpkButtonType = DefaultType,
  enabled: Boolean = DefaultEnabled,
  loading: Boolean = DefaultLoading,
  interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
  onClick: () -> Unit,
) {
  BpkButtonImpl(
    size = size,
    type = type,
    enabled = enabled,
    loading = loading,
    interactionSource = interactionSource,
    modifier = modifier.requiredWidth(size.minHeight),
    onClick = onClick,
    content = { ButtonDrawable(iconResource, contentDescription, size) },
  )
}

@Composable
fun BpkButton(
  text: String,
  @DrawableRes iconResource: Int,
  position: BpkButtonIconPosition,
  modifier: Modifier = Modifier,
  size: BpkButtonSize = DefaultSize,
  type: BpkButtonType = DefaultType,
  enabled: Boolean = DefaultEnabled,
  loading: Boolean = DefaultLoading,
  interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
  onClick: () -> Unit,
) {
  BpkButtonImpl(
    size = size,
    type = type,
    enabled = enabled,
    loading = loading,
    interactionSource = interactionSource,
    modifier = modifier,
    onClick = onClick,
  ) {
    when (position) {
      BpkButtonIconPosition.Start -> {
        ButtonDrawable(iconResource, null, size)
        ButtonText(text)
      }
      BpkButtonIconPosition.End -> {
        ButtonText(text)
        ButtonDrawable(iconResource, null, size)
      }
    }
  }
}


private val DefaultSize = BpkButtonSize.Default
private val DefaultType = BpkButtonType.Primary
private const val DefaultEnabled = true
private const val DefaultLoading = false
