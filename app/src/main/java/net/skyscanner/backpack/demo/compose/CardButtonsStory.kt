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

package net.skyscanner.backpack.demo.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import net.skyscanner.backpack.compose.cardbutton.BpkCardButton
import net.skyscanner.backpack.compose.cardbutton.BpkCardButtonSize
import net.skyscanner.backpack.compose.cardbutton.BpkCardButtonStyle
import net.skyscanner.backpack.compose.cardbutton.BpkCardButtonType
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkDimension
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.demo.R

@Composable
fun CardButtonsStory(
  size: BpkCardButtonSize,
  modifier: Modifier = Modifier,
) {
  LazyColumn(
    modifier
      .fillMaxSize()
      .padding(top = BpkDimension.Spacing.Md)
  ) {
    item {
      Row(
        modifier = modifier.background(BpkTheme.colors.textOnDark).fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
      ) {
        var checked1 by remember { mutableStateOf(false) }
        BpkCardButton(
          type = BpkCardButtonType.Save(checked = checked1, onCheckedChange = { checked1 = !checked1 }),
          size = size,
          style = BpkCardButtonStyle.Default,
          contentDescription = "",
        )
        var checked2 by remember { mutableStateOf(true) }
        BpkCardButton(
          type = BpkCardButtonType.Save(checked = !checked2, onCheckedChange = { checked2 = !checked2 }),
          size = size,
          style = BpkCardButtonStyle.Default,
          contentDescription = "",
        )
        BpkCardButton(
          type = BpkCardButtonType.Share(onClick = {}),
          size = size,
          style = BpkCardButtonStyle.Default,
          contentDescription = "",
        )
      }
    }
    item {
      Row(
        modifier = modifier.background(BpkTheme.colors.surfaceContrast).fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
      ) {
        var checked1 by remember { mutableStateOf(false) }
        BpkCardButton(
          type = BpkCardButtonType.Save(checked = checked1, onCheckedChange = { checked1 = !checked1 }),
          size = size,
          style = BpkCardButtonStyle.OnDark,
          contentDescription = "",
        )
        var checked2 by remember { mutableStateOf(true) }
        BpkCardButton(
          type = BpkCardButtonType.Save(checked = !checked2, onCheckedChange = { checked2 = !checked2 }),
          size = size,
          style = BpkCardButtonStyle.OnDark,
          contentDescription = "",
        )
        BpkCardButton(
          type = BpkCardButtonType.Share(onClick = {}),
          size = size,
          style = BpkCardButtonStyle.OnDark,
          contentDescription = "",
        )
      }
    }
    item {
      Box(modifier = modifier.height(BpkSpacing.Xxl + BpkSpacing.Md)) {
        Image(
          modifier = modifier.fillMaxWidth(),
          painter = painterResource(id = R.drawable.canadian_rockies_canada),
          contentScale = ContentScale.FillWidth,
          contentDescription = ""
        )
        Row(
          modifier = modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceEvenly
        ) {
          var checked1 by remember { mutableStateOf(false) }
          BpkCardButton(
            type = BpkCardButtonType.Save(checked = checked1, onCheckedChange = { checked1 = !checked1 }),
            size = size,
            style = BpkCardButtonStyle.Contained,
            contentDescription = "",
          )
          var checked2 by remember { mutableStateOf(true) }
          BpkCardButton(
            type = BpkCardButtonType.Save(checked = !checked2, onCheckedChange = { checked2 = !checked2 }),
            size = size,
            style = BpkCardButtonStyle.Contained,
            contentDescription = "",
          )
          BpkCardButton(
            type = BpkCardButtonType.Share(onClick = {}),
            size = size,
            style = BpkCardButtonStyle.Contained,
            contentDescription = "",
          )
        }
      }
    }
  }
}
