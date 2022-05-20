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

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import net.skyscanner.backpack.compose.panel.BpkPanel
import net.skyscanner.backpack.compose.panel.BpkPanelPadding
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.demo.R

@Composable
fun PanelStory() {
  Column(
    modifier = Modifier.padding(BpkSpacing.Base),
    verticalArrangement = Arrangement.spacedBy(BpkSpacing.Base),
  ) {

    val panelModifier = Modifier
      .fillMaxWidth()
      .weight(1f)

    BpkText(
      text = stringResource(id = R.string.panel_default),
      style = BpkTheme.typography.heading3,
    )
    DefaultPanelExample(panelModifier)

    BpkText(
      text = stringResource(id = R.string.panel_no_padding),
      style = BpkTheme.typography.heading3,
    )
    NoPaddingPanelExample(Modifier.fillMaxWidth())
  }
}

@Composable
@Preview
fun DefaultPanelExample(
  modifier: Modifier = Modifier,
) {
  BpkPanel(
    modifier = modifier,
    contentAlignment = Alignment.Center,
  ) {
    BpkText(
      text = stringResource(R.string.stub),
      overflow = TextOverflow.Clip,
    )
  }
}

@Composable
fun NoPaddingPanelExample(
  modifier: Modifier = Modifier,
) {
  BpkPanel(
    modifier = modifier,
    contentAlignment = Alignment.Center,
    padding = BpkPanelPadding.None,
  ) {
    BpkText(
      text = stringResource(R.string.stub),
      overflow = TextOverflow.Clip,
    )
  }
}
