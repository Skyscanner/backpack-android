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

package net.skyscanner.backpack.compose.button.internal

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import net.skyscanner.backpack.compose.button.BpkButtonSize
import net.skyscanner.backpack.compose.button.BpkButtonType
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.spinner.BpkSpinner
import net.skyscanner.backpack.compose.spinner.BpkSpinnerSize
import net.skyscanner.backpack.compose.spinner.BpkSpinnerStyle
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.tokens.BpkBorderRadius
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.compose.utils.hideContentIf

@Composable
internal fun BpkButtonImpl(
  modifier: Modifier = Modifier,
  size: BpkButtonSize = BpkButtonSize.Default,
  type: BpkButtonType = BpkButtonType.Primary,
  enabled: Boolean = true,
  loading: Boolean = false,
  interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
  onClick: () -> Unit,
  content: @Composable RowScope.() -> Unit,
) {
  CompositionLocalProvider(LocalRippleTheme provides ButtonRippleTheme(type.rippleColor())) {
    Button(
      onClick = onClick,
      enabled = enabled && !loading,
      modifier = modifier.defaultMinSize(BpkSpacing.Sm, size.minHeight).requiredHeight(size.minHeight),
      interactionSource = interactionSource,
      colors = ButtonDefaults.buttonColors(
        backgroundColor = type.backgroundColor(interactionSource),
        contentColor = type.contentColor(interactionSource),
        disabledBackgroundColor = type.disabledBackgroundColor(),
        disabledContentColor = type.disabledContentColor(),
      ),
      shape = ButtonShape,
      contentPadding = type.contentPadding,
      elevation = null,
      content = {
        CompositionLocalProvider(LocalTextStyle provides size.textStyle()) {
          Box {
            Row(
              modifier = Modifier.hideContentIf(loading),
              horizontalArrangement = Arrangement.spacedBy(size.horizontalSpacing),
              verticalAlignment = Alignment.CenterVertically,
              content = content,
            )

            if (loading) {
              BpkSpinner(
                style = BpkSpinnerStyle.Disabled,
                size = when (size) {
                  BpkButtonSize.Default -> BpkSpinnerSize.Small
                  BpkButtonSize.Large -> BpkSpinnerSize.Medium
                },
              )
            }
          }
        }
      },
    )
  }
}

@Composable
internal fun ButtonIcon(
  icon: BpkIcon,
  contentDescription: String?,
  size: BpkButtonSize,
  modifier: Modifier = Modifier,
) {
  BpkIcon(icon, contentDescription, size = size.iconSize, modifier = modifier)
}

@Composable
internal fun ButtonText(text: String, modifier: Modifier = Modifier) {
  BpkText(
    text = text,
    modifier = modifier,
    maxLines = 1,
    overflow = TextOverflow.Ellipsis,
  )
}

private class ButtonRippleTheme(
  private val color: Color = Color.Black,
) : RippleTheme {

  private val alpha = RippleAlpha(color.alpha, color.alpha, color.alpha, color.alpha)

  @Composable
  override fun defaultColor(): Color =
    color

  @Composable
  override fun rippleAlpha(): RippleAlpha =
    alpha
}

private val ButtonShape = RoundedCornerShape(BpkBorderRadius.Sm)
