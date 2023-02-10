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

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import net.skyscanner.backpack.compose.chip.BpkChip
import net.skyscanner.backpack.compose.chip.BpkChipStyle
import net.skyscanner.backpack.compose.chip.BpkDismissibleChip
import net.skyscanner.backpack.compose.chip.BpkDropdownChip
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.compose.tokens.Deals
import net.skyscanner.backpack.demo.R

@Composable
@Preview
fun ChipStory(style: BpkChipStyle = BpkChipStyle.Default) {

  Box {
    if (style == BpkChipStyle.OnImage) {
      Image(
        painter = painterResource(R.drawable.canadian_rockies_canada),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier.fillMaxSize(),
      )
    }
    val background = when (style) {
      BpkChipStyle.Default -> Color.Transparent
      BpkChipStyle.OnDark -> BpkTheme.colors.surfaceContrast
      BpkChipStyle.OnImage -> Color.Transparent
    }

    Column(
      modifier = Modifier
        .fillMaxSize()
        .background(background)
        .padding(vertical = BpkSpacing.Base, horizontal = BpkSpacing.Base),
      verticalArrangement = Arrangement.spacedBy(BpkSpacing.Base),
    ) {
      ChipsRow(style, text = stringResource(R.string.chip_option))
      ChipsRow(style, withDropdown = true, text = stringResource(R.string.chip_dropdown))
      DismissibleChipsRow(style = style, icon = null, text = stringResource(R.string.chip_dismiss))
      ChipsRow(style, icon = BpkIcon.Deals, text = stringResource(R.string.with_icon))
    }
  }
}

@Composable
private fun ChipsRow(
  style: BpkChipStyle,
  text: String,
  modifier: Modifier = Modifier,
  withDropdown: Boolean = false,
  icon: BpkIcon? = null,
) {
  Row(modifier) {
    ChipSample(
      text = text,
      modifier = Modifier
        .weight(1f)
        .wrapContentWidth(Alignment.CenterHorizontally),
      initialState = false,
      enabled = true,
      style = style,
      icon = icon,
      withDropdown = withDropdown,
    )
    ChipSample(
      text = text,
      modifier = Modifier
        .weight(1f)
        .wrapContentWidth(Alignment.CenterHorizontally),
      initialState = true,
      enabled = true,
      style = style,
      icon = icon,
      withDropdown = withDropdown,
    )
    ChipSample(
      text = text,
      modifier = Modifier
        .weight(1f)
        .wrapContentWidth(Alignment.CenterHorizontally),
      initialState = false,
      enabled = false,
      style = style,
      icon = icon,
      withDropdown = withDropdown,
    )
  }
}

@Composable
private fun DismissibleChipsRow(
  style: BpkChipStyle,
  icon: BpkIcon?,
  text: String,
  modifier: Modifier = Modifier,
) {
  Row(
    horizontalArrangement = Arrangement.Center,
    modifier = modifier.fillMaxWidth(),
  ) {
    BpkDismissibleChip(
      text = text,
      style = style,
      icon = icon,
      onClick = {},
    )
  }
}

@Composable
private fun ChipSample(
  text: String,
  initialState: Boolean,
  enabled: Boolean,
  style: BpkChipStyle,
  icon: BpkIcon?,
  withDropdown: Boolean,
  modifier: Modifier = Modifier,
) {

  var selected by rememberSaveable { mutableStateOf(initialState) }

  if (withDropdown) {
    BpkDropdownChip(
      text = text,
      modifier = modifier,
      selected = selected,
      enabled = enabled,
      style = style,
      icon = icon,
      onSelectedChange = { selected = it },
    )
  } else {
    BpkChip(
      text = text,
      modifier = modifier,
      selected = selected,
      enabled = enabled,
      style = style,
      icon = icon,
      onSelectedChange = { selected = it },
    )
  }
}
