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
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.launch
import net.skyscanner.backpack.compose.button.BpkButton
import net.skyscanner.backpack.compose.pageindicator.BpkPageIndicator
import net.skyscanner.backpack.compose.pageindicator.BpkPageIndicatorStyle
import net.skyscanner.backpack.compose.pageindicator.rememberBpkPageIndicatorState
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.demo.R

@Composable
@Preview
fun PageIndicatorStory() {

  Column(
    modifier = Modifier
      .fillMaxSize()
      .padding(BpkSpacing.Base),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.spacedBy(BpkSpacing.Base)
  ) {
    BpkText(text = stringResource(id = R.string.page_indicator_less_than_5))
    PageIndicatorRow(totalIndicators = 3, style = BpkPageIndicatorStyle.Default)

    BpkText(text = stringResource(id = R.string.page_indicator_more_than_5))
    PageIndicatorRow(totalIndicators = 8, style = BpkPageIndicatorStyle.Default)
    BpkText(text = stringResource(id = R.string.page_indicator_over_image))
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .height(BpkSpacing.Xxl * 2),
      contentAlignment = Alignment.BottomCenter,
    ) {
      Image(
        painter = painterResource(id = R.drawable.canadian_rockies_canada),
        contentDescription = "",
        contentScale = ContentScale.FillWidth
      )
      PageIndicatorRow(totalIndicators = 8, style = BpkPageIndicatorStyle.OverImage)
    }
  }
}

@Composable
private fun PageIndicatorRow(totalIndicators: Int, style: BpkPageIndicatorStyle) {
  Row(
    modifier = Modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.SpaceEvenly,
    verticalAlignment = Alignment.CenterVertically
  ) {
    val coroutineScope = rememberCoroutineScope()
    val state = rememberBpkPageIndicatorState()
    BpkButton(text = stringResource(id = R.string.page_indicator_prev)) {
      coroutineScope.launch {
        state.scrollToPage(state.currentPage - 1)
      }
    }
    BpkPageIndicator(
      state = state,
      totalIndicators = totalIndicators,
      style = style,
      indicatorLabel = "",
    )
    BpkButton(text = stringResource(id = R.string.page_indicator_next)) {
      coroutineScope.launch {
        state.scrollToPage(state.currentPage + 1)
      }
    }
  }
}
