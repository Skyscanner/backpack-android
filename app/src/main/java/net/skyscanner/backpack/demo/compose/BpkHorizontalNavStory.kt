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

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import net.skyscanner.backpack.compose.horizontalnav.BpkHorizontalNav
import net.skyscanner.backpack.compose.horizontalnav.BpkHorizontalNavSize
import net.skyscanner.backpack.compose.horizontalnav.BpkHorizontalNavTab
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.compose.tokens.CloseCircle
import net.skyscanner.backpack.compose.tokens.TickCircle
import net.skyscanner.backpack.demo.R

@Composable
fun HorizontalNavComposeStory() {

  Column(
    modifier = Modifier
      .background(BpkTheme.colors.surfaceHighlight)
      .padding(BpkSpacing.Base),
    verticalArrangement = Arrangement.spacedBy(BpkSpacing.Base),
  ) {

    BpkText(text = stringResource(R.string.tabs_small))
    BpkHorizontalNav_Small_Sample()

    BpkText(text = stringResource(R.string.tabs_small_with_icon))
    BpkHorizontalNav_SmallWithIcon_Sample()

    BpkText(text = stringResource(R.string.tabs_large))
    BpkHorizontalNav_Large_Sample()

    BpkText(text = stringResource(R.string.tabs_large_with_icon))
    BpkHorizontalNav_LargeWithIcon_Sample()
  }
}

@Preview
@Composable
internal fun BpkHorizontalNav_Small_Sample() {
  val tabs = listOf(
    BpkHorizontalNavTab(
      title = stringResource(R.string.tabs_one),
    ),
    BpkHorizontalNavTab(
      title = stringResource(R.string.tabs_two),
    ),
  )

  var activeIndex by rememberSaveable { mutableStateOf(0) }

  BpkHorizontalNav(
    tabs = tabs,
    activeIndex = activeIndex,
    size = BpkHorizontalNavSize.Small,
    onChanged = { activeIndex = it },
  )
}

@Preview
@Composable
internal fun BpkHorizontalNav_SmallWithIcon_Sample() {
  val tabs = listOf(
    BpkHorizontalNavTab(
      title = stringResource(R.string.tabs_one),
      icon = BpkIcon.TickCircle,
    ),
    BpkHorizontalNavTab(
      title = stringResource(R.string.tabs_two),
      icon = BpkIcon.CloseCircle,
    ),
  )

  var activeIndex by rememberSaveable { mutableStateOf(0) }

  BpkHorizontalNav(
    tabs = tabs,
    activeIndex = activeIndex,
    size = BpkHorizontalNavSize.Small,
    onChanged = { activeIndex = it },
  )
}

@Preview
@Composable
internal fun BpkHorizontalNav_Large_Sample() {
  val tabs = listOf(
    BpkHorizontalNavTab(
      title = stringResource(R.string.tabs_one),
    ),
    BpkHorizontalNavTab(
      title = stringResource(R.string.tabs_two),
    ),
  )

  var activeIndex by rememberSaveable { mutableStateOf(0) }

  BpkHorizontalNav(
    tabs = tabs,
    activeIndex = activeIndex,
    onChanged = { activeIndex = it },
  )
}

@Preview
@Composable
internal fun BpkHorizontalNav_LargeWithIcon_Sample() {
  val tabs = listOf(
    BpkHorizontalNavTab(
      title = stringResource(R.string.tabs_one),
      icon = BpkIcon.TickCircle,
    ),
    BpkHorizontalNavTab(
      title = stringResource(R.string.tabs_two),
      icon = BpkIcon.CloseCircle,
    ),
  )

  var activeIndex by rememberSaveable { mutableStateOf(0) }

  BpkHorizontalNav(
    tabs = tabs,
    activeIndex = activeIndex,
    onChanged = { activeIndex = it },
  )
}
