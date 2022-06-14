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

package net.skyscanner.backpack.compose.badge

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.icon.BpkIconSize
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkBorderRadius
import net.skyscanner.backpack.compose.tokens.BpkColor
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.compose.utils.dynamicColorOf

enum class BpkBadgeType {
  Normal,
  Strong,
  Success,
  Warning,
  Destructive,
  Inverse,
  Outline,
}

@Composable
fun BpkBadge(
  text: String,
  type: BpkBadgeType,
  modifier: Modifier = Modifier,
  icon: BpkIcon? = null,
) {
  Row(
    modifier = modifier
      .semantics(mergeDescendants = true) { }
      .requiredHeight(BpkSpacing.Lg)
      .border(1.dp, type.borderColor, BadgeShape)
      .background(type.backgroundColor, BadgeShape)
      .padding(horizontal = BpkSpacing.Md),
    horizontalArrangement = Arrangement.spacedBy(BpkSpacing.Sm),
    verticalAlignment = Alignment.CenterVertically,
  ) {
    val contentColor = type.contentColor
    if (icon != null) {
      BpkIcon(
        icon = icon,
        contentDescription = null,
        size = BpkIconSize.Small,
        tint = contentColor,
      )
    }
    BpkText(
      text = text,
      color = contentColor,
      style = BpkTheme.typography.caption,
      maxLines = 1,
      overflow = TextOverflow.Ellipsis,
    )
  }
}

private val BpkBadgeType.contentColor: Color
  @Composable
  get() = when (this) {
    BpkBadgeType.Normal -> dynamicColorOf(BpkColor.SkyGray, BpkColor.White)
    BpkBadgeType.Strong -> dynamicColorOf(BpkColor.White, BpkColor.SkyGray)
    BpkBadgeType.Success -> BpkColor.SkyGray
    BpkBadgeType.Warning -> BpkColor.SkyGray
    BpkBadgeType.Destructive -> BpkColor.White
    BpkBadgeType.Inverse -> BpkColor.SkyGray
    BpkBadgeType.Outline -> BpkColor.White
  }

private val BpkBadgeType.backgroundColor: Color
  @Composable
  get() = when (this) {
    BpkBadgeType.Normal -> dynamicColorOf(BpkColor.SkyGrayTint07, BpkColor.BlackTint02)
    BpkBadgeType.Strong -> dynamicColorOf(BpkColor.SkyGray, BpkColor.White)
    BpkBadgeType.Success -> BpkColor.Glencoe
    BpkBadgeType.Warning -> BpkColor.Erfoud
    BpkBadgeType.Destructive -> BpkColor.Panjin
    BpkBadgeType.Inverse -> BpkColor.White
    BpkBadgeType.Outline -> Color.Transparent
  }

private val BpkBadgeType.borderColor: Color
  get() = when (this) {
    BpkBadgeType.Outline -> BpkColor.White
    else -> Color.Transparent
  }

private val BadgeShape = RoundedCornerShape(BpkBorderRadius.Xs)
