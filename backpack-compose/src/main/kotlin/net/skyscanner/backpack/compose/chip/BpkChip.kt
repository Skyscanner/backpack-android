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

package net.skyscanner.backpack.compose.chip

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.dismiss
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.icon.BpkIconSize
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkColor
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.compose.tokens.CloseCircle
import net.skyscanner.backpack.compose.tokens.Tick
import net.skyscanner.backpack.compose.utils.applyIf
import net.skyscanner.backpack.compose.utils.dynamicColorOf

enum class BpkChipStyle {
  Default,
  OnDark,
}

enum class BpkChipType {
  Option,
  Select,
  Dismiss,
}

@Composable
fun BpkChip(
  text: String,
  modifier: Modifier = Modifier,
  selected: Boolean = false,
  enabled: Boolean = true,
  style: BpkChipStyle = BpkChipStyle.Default,
  icon: BpkIcon? = null,
  type: BpkChipType = BpkChipType.Option,
  onClick: (Boolean) -> Unit = {},
) {

  val backgroundColor by animateColorAsState(
    targetValue = when {
      !enabled -> dynamicColorOf(BpkColor.SkyGrayTint06, BpkColor.BlackTint03)
      selected -> BpkTheme.colors.primary
      else -> style.backgroundColor
    }
  )

  val contentColor by animateColorAsState(
    targetValue = when {
      !enabled -> dynamicColorOf(BpkColor.SkyGrayTint04, BpkColor.BlackTint06)
      selected -> BpkTheme.colors.background
      else -> style.contentColor
    }
  )

  Row(
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.spacedBy(BpkSpacing.Md),
    modifier = modifier
      .height(BpkSpacing.Xl)
      .clip(ChipShape)
      .background(backgroundColor)
      .applyIf(type == BpkChipType.Option || type == BpkChipType.Select) {
        selectable(
          enabled = enabled,
          selected = selected,
          onClick = { onClick(!selected) }
        )
      }
      .applyIf(type == BpkChipType.Dismiss) {
        clickable(
          enabled = enabled,
          onClick = { onClick(!selected) }
        )
          .semantics {
            if (enabled) {
              dismiss {
                onClick(!selected)
                true
              }
            }
          }
      }
      .padding(horizontal = BpkSpacing.Base),
  ) {

    if (icon != null) {
      BpkIcon(
        icon = icon,
        size = BpkIconSize.Small,
        contentDescription = null,
        tint = contentColor,
      )
    }

    BpkText(
      text = text,
      color = contentColor,
      style = BpkTheme.typography.footnote,
      maxLines = 1,
      overflow = TextOverflow.Ellipsis,
    )

    val trailingIcon = type.icon
    if (trailingIcon != null) {
      BpkIcon(
        icon = trailingIcon,
        size = BpkIconSize.Small,
        contentDescription = null,
        tint = contentColor,
      )
    }
  }
}

private val BpkChipStyle.backgroundColor: Color
  @Composable
  get() = when (this) {
    BpkChipStyle.Default -> dynamicColorOf(BpkColor.SkyGrayTint06, BpkColor.BlackTint03)
    BpkChipStyle.OnDark -> dynamicColorOf(BpkColor.White, BpkColor.BlackTint03)
  }

private val BpkChipStyle.contentColor: Color
  @Composable
  get() = dynamicColorOf(BpkColor.SkyGray, BpkColor.White)

private val BpkChipType.icon: BpkIcon?
  get() = when (this) {
    BpkChipType.Option -> null
    BpkChipType.Select -> BpkIcon.Tick
    BpkChipType.Dismiss -> BpkIcon.CloseCircle
  }

private val ChipShape = RoundedCornerShape(50)
