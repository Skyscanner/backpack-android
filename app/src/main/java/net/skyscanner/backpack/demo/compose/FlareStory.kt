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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import net.skyscanner.backpack.compose.flare.BpkFlare
import net.skyscanner.backpack.compose.flare.BpkFlarePointerDirection
import net.skyscanner.backpack.compose.flare.BpkFlareRadius
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.demo.R

@Composable
fun FlareStory() {
  Column(
    modifier = Modifier.padding(BpkSpacing.Base),
    verticalArrangement = Arrangement.spacedBy(BpkSpacing.Base),
  ) {

    val flareModifier = Modifier
      .fillMaxWidth()
      .height(CardHeight)
    DefaultFlareExample(flareModifier)
    UpFlareExample(flareModifier)
    RadiusFlareExample(flareModifier)
    ContentPaddingFlareExample(flareModifier)
  }
}

@Composable
@Preview
fun DefaultFlareExample(modifier: Modifier = Modifier) {
  BpkFlare(modifier) {
    RockiesImage()
  }
}

@Composable
@Preview
fun UpFlareExample(modifier: Modifier = Modifier) {
  BpkFlare(modifier, pointerDirection = BpkFlarePointerDirection.Up) {
    RockiesImage()
  }
}

@Composable
@Preview
fun RadiusFlareExample(modifier: Modifier = Modifier) {
  BpkFlare(modifier, radius = BpkFlareRadius.Medium) {
    RockiesImage()
  }
}

@Composable
@Preview
fun ContentPaddingFlareExample(modifier: Modifier = Modifier) {
  BpkFlare(modifier, background = BpkTheme.colors.coreAccent, insetContent = true) {
    BpkText(text = stringResource(R.string.stub), color = BpkTheme.colors.textPrimaryInverse)
  }
}

@Composable
private fun RockiesImage() {
  Image(
    painter = painterResource(R.drawable.canadian_rockies_canada),
    contentDescription = stringResource(R.string.image_rockies_content_description),
    contentScale = ContentScale.Crop,
  )
}

private val CardHeight = 150.dp
