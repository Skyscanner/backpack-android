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

package net.skyscanner.backpack.compose.dividedcard

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntSize
import androidx.test.ext.junit.runners.AndroidJUnit4
import net.skyscanner.backpack.compose.BpkSnapshotTest
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.demo.R
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BpkDividedCardTest : BpkSnapshotTest() {

  @Before
  fun setup() {
    snapshotSize = IntSize(450, 200)
  }

  @Test
  fun cardWidthSmallerThanMinimum() {
    snap {
      BpkDividedCard(
        modifier = Modifier.dividedCardWidth(BpkSpacing.Xxl),
        primaryContent = {
          Image(
            modifier = Modifier
              .height(BpkSpacing.Xxl * 2)
              .fillMaxWidth(),
            painter = painterResource(id = R.drawable.canadian_rockies_canada),
            contentDescription = "",
            contentScale = ContentScale.Crop
          )
        },
        secondaryContent = {
          BpkText(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(id = R.string.divided_card_width_less_than_minimum),
            style = BpkTheme.typography.bodyDefault,
            textAlign = TextAlign.Center
          )
        },
        onClick = {}
      )
    }
  }

  @Test
  fun cardWidthGreaterThanMinimum() {
    snap {
      BpkDividedCard(
        modifier = Modifier.fillMaxWidth(),
        primaryContent = {
          Image(
            modifier = Modifier
              .height(BpkSpacing.Xxl * 2)
              .fillMaxWidth(),
            painter = painterResource(id = R.drawable.canadian_rockies_canada),
            contentDescription = "",
            contentScale = ContentScale.Crop
          )
        },
        secondaryContent = {
          BpkText(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(id = R.string.divided_card_width_more_than_minimum),
            style = BpkTheme.typography.bodyDefault,
            textAlign = TextAlign.Center
          )
        },
        onClick = {}
      )
    }
  }

  @Test
  fun emptyCard() {
    snap {
      BpkDividedCard(
        modifier = Modifier.fillMaxWidth(),
        primaryContent = {
          Spacer(modifier = Modifier.height(BpkSpacing.Xxl))
          BpkText(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(id = R.string.divided_card_primary_content),
            style = BpkTheme.typography.bodyDefault,
            textAlign = TextAlign.Center,
          )
        },
        secondaryContent = {
          Spacer(modifier = Modifier.height(BpkSpacing.Xxl))
          BpkText(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(id = R.string.divided_card_secondary_content),
            style = BpkTheme.typography.bodyDefault,
            textAlign = TextAlign.Center,
          )
        },
      )
    }
  }
}
