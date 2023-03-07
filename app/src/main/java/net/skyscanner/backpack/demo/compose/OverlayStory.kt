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
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import net.skyscanner.backpack.compose.overlay.BpkOverlay
import net.skyscanner.backpack.compose.overlay.BpkOverlayType
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.demo.components.OverlayComponent
import net.skyscanner.backpack.demo.meta.ComposeStory

@Composable
@OverlayComponent
@ComposeStory
fun OverlayStory(modifier: Modifier = Modifier) {
  Column(modifier = modifier) {
    OverlaySampleRow {
      DefaultOverlaySample(overlayType = BpkOverlayType.SolidLow)
      DefaultOverlaySample(overlayType = BpkOverlayType.SolidMedium)
      DefaultOverlaySample(overlayType = BpkOverlayType.SolidHigh)
    }
    OverlaySampleRow {
      DefaultOverlaySample(overlayType = BpkOverlayType.TopLow)
      DefaultOverlaySample(overlayType = BpkOverlayType.TopMedium)
      DefaultOverlaySample(overlayType = BpkOverlayType.TopHigh)
    }
    OverlaySampleRow {
      DefaultOverlaySample(overlayType = BpkOverlayType.BottomLow)
      DefaultOverlaySample(overlayType = BpkOverlayType.BottomMedium)
      DefaultOverlaySample(overlayType = BpkOverlayType.BottomHigh)
    }
    OverlaySampleRow {
      DefaultOverlaySample(overlayType = BpkOverlayType.LeftLow)
      DefaultOverlaySample(overlayType = BpkOverlayType.LeftMedium)
      DefaultOverlaySample(overlayType = BpkOverlayType.LeftHigh)
    }
    OverlaySampleRow {
      DefaultOverlaySample(overlayType = BpkOverlayType.RightLow)
      DefaultOverlaySample(overlayType = BpkOverlayType.RightMedium)
      DefaultOverlaySample(overlayType = BpkOverlayType.RightHigh)
    }
    OverlaySampleRow {
      DefaultOverlaySample(overlayType = BpkOverlayType.Vignette)
      WithTextOverlaySample(overlayType = BpkOverlayType.SolidHigh)
    }
  }
}

@Composable
fun OverlaySampleRow(
  modifier: Modifier = Modifier,
  content: @Composable RowScope.() -> Unit,
) {
  Row(
    content = content,
    horizontalArrangement = Arrangement.SpaceEvenly,
    modifier = modifier.fillMaxWidth(),
  )
}

@Composable
fun DefaultOverlaySample(
  overlayType: BpkOverlayType,
  modifier: Modifier = Modifier,
) {
  Column(horizontalAlignment = Alignment.CenterHorizontally) {
    BpkOverlay(
      modifier = modifier
        .width(110.dp)
        .height(78.dp)
        .padding(top = BpkSpacing.Sm),
      overlayType = overlayType,
    ) {
      Image(
        painter = painterResource(R.drawable.sea),
        contentDescription = stringResource(R.string.image_rockies_content_description),
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.Crop,
      )
    }
    BpkText(text = overlayType.toString())
  }
}

@Composable
fun WithTextOverlaySample(
  overlayType: BpkOverlayType,
  modifier: Modifier = Modifier,
) {
  Column(horizontalAlignment = Alignment.CenterHorizontally) {
    BpkOverlay(
      modifier = modifier
        .width(110.dp)
        .height(78.dp)
        .padding(top = BpkSpacing.Sm),
      overlayType = overlayType,
      foregroundContent = {
        BpkText(
          modifier = Modifier.align(Alignment.Center),
          text = "With text",
          color = BpkTheme.colors.canvas,
          style = BpkTheme.typography.bodyDefault,
        )
      },
    ) {
      Image(
        painter = painterResource(R.drawable.sea),
        contentDescription = stringResource(R.string.image_sea_content_description),
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.Crop,
      )
    }
    BpkText(text = overlayType.toString())
  }
}
