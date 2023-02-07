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

package net.skyscanner.backpack.compose.cardwrapper

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import net.skyscanner.backpack.compose.BpkSnapshotTest
import net.skyscanner.backpack.compose.dividedcard.dividedCardWidth
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.demo.R
import org.junit.Test

class BpkCardWrapperTest : BpkSnapshotTest() {

  @Test
  fun wrapperWidthSmallerThanMinimum() {
    snap {
      BpkCardWrapper(
        modifier = Modifier.dividedCardWidth(BpkSpacing.Xxl),
        backgroundColor = BpkTheme.colors.coreEco,
        header = {
          BpkText(
            modifier = Modifier
              .fillMaxWidth()
              .height(BpkSpacing.Xxl),
            text = stringResource(id = R.string.card_wrapper_header),
            style = BpkTheme.typography.bodyDefault,
            textAlign = TextAlign.Center,
          )
        },
        card = {
          Image(
            modifier = Modifier
              .height(BpkSpacing.Xxl * 2)
              .fillMaxWidth(),
            painter = painterResource(id = R.drawable.canadian_rockies_canada),
            contentDescription = "",
            contentScale = ContentScale.Crop
          )
          BpkText(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(id = R.string.card_wrapper_card),
            style = BpkTheme.typography.bodyDefault,
            textAlign = TextAlign.Center
          )
        }
      )
    }
  }

  @Test
  fun wrapperWidthGreaterThanMinimum() {
    snap {
      BpkCardWrapper(
        modifier = Modifier.width(400.dp),
        backgroundColor = BpkTheme.colors.coreEco,
        header = {
          BpkText(
            modifier = Modifier
              .fillMaxWidth()
              .height(BpkSpacing.Xxl),
            text = stringResource(id = R.string.card_wrapper_header),
            style = BpkTheme.typography.bodyDefault,
            textAlign = TextAlign.Center,
          )
        },
        card = {
          Image(
            modifier = Modifier
              .height(BpkSpacing.Xxl * 2)
              .fillMaxWidth(),
            painter = painterResource(id = R.drawable.canadian_rockies_canada),
            contentDescription = "",
            contentScale = ContentScale.Crop
          )
          BpkText(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(id = R.string.card_wrapper_card),
            style = BpkTheme.typography.bodyDefault,
            textAlign = TextAlign.Center
          )
        }
      )
    }
  }

  @Test
  fun emptyWrapper() {
    snap {
      BpkCardWrapper(
        modifier = Modifier.width(400.dp),
        backgroundColor = BpkTheme.colors.coreEco,
        header = {
          BpkText(
            modifier = Modifier
              .fillMaxWidth()
              .height(BpkSpacing.Xxl),
            text = stringResource(id = R.string.card_wrapper_header),
            style = BpkTheme.typography.bodyDefault,
            textAlign = TextAlign.Center,
          )
        },
        card = {
          BpkText(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(id = R.string.card_wrapper_card),
            style = BpkTheme.typography.bodyDefault,
            textAlign = TextAlign.Center
          )
        }
      )
    }
  }
}
