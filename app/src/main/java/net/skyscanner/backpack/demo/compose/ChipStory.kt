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

package net.skyscanner.backpack.demo.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import net.skyscanner.backpack.compose.LocalContentColor
import net.skyscanner.backpack.compose.chip.BpkChip
import net.skyscanner.backpack.compose.chip.BpkChipStyle
import net.skyscanner.backpack.compose.chip.BpkChipType
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.surface.BpkSurface
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkColor
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.compose.tokens.Deals
import net.skyscanner.backpack.demo.R

@Composable
@Preview
fun ChipStory() {
  Column(
    verticalArrangement = Arrangement.spacedBy(BpkSpacing.Base),
  ) {
    BpkChipStyle.values().forEach { style ->
      ChipsColumn(style)
    }
  }
}

@Composable
private fun ChipsColumn(
  style: BpkChipStyle,
  modifier: Modifier = Modifier,
) {

  val forceDarkBackground = style == BpkChipStyle.OnDark && BpkTheme.colors.isLight

  BpkSurface(
    modifier = modifier,
    color = if (forceDarkBackground) BpkTheme.colors.textOnLight else Color.Transparent,
    contentColor = if (forceDarkBackground) BpkColor.White else LocalContentColor.current,
  ) {

    Column(
      modifier =
      Modifier.padding(vertical = BpkSpacing.Base, horizontal = BpkSpacing.Base),
      verticalArrangement = Arrangement.spacedBy(BpkSpacing.Base),
    ) {

      BpkText(text = style.toString(), style = BpkTheme.typography.heading5)

      BpkChipType.values().forEach { type ->
        ChipsRow(style, type, null)
      }

      ChipsRow(style, BpkChipType.Option, BpkIcon.Deals, text = stringResource(R.string.with_icon))
    }
  }
}

@Composable
private fun ChipsRow(
  style: BpkChipStyle,
  type: BpkChipType,
  icon: BpkIcon?,
  modifier: Modifier = Modifier,
  text: String = type.toString(),
) {
  Row(modifier) {
    ChipSample(
      text = text,
      modifier = Modifier.weight(1f).wrapContentWidth(Alignment.CenterHorizontally),
      initialState = false,
      enabled = true,
      style = style,
      icon = icon,
      type = type,
    )
    ChipSample(
      text = text,
      modifier = Modifier.weight(1f).wrapContentWidth(Alignment.CenterHorizontally),
      initialState = true,
      enabled = true,
      style = style,
      icon = icon,
      type = type,
    )
    ChipSample(
      text = text,
      modifier = Modifier.weight(1f).wrapContentWidth(Alignment.CenterHorizontally),
      initialState = false,
      enabled = false,
      style = style,
      icon = icon,
      type = type,
    )
  }
}

@Composable
private fun ChipSample(
  text: String,
  modifier: Modifier = Modifier,
  initialState: Boolean,
  enabled: Boolean,
  style: BpkChipStyle,
  icon: BpkIcon?,
  type: BpkChipType,
) {

  var selected by rememberSaveable { mutableStateOf(initialState) }

  BpkChip(
    text = text,
    modifier = modifier,
    selected = selected,
    enabled = enabled,
    style = style,
    icon = icon,
    type = type,
    onSelectedChange = { selected = it },
  )
}
