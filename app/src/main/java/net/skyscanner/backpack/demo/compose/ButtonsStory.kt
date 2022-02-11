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
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import net.skyscanner.backpack.compose.button.BpkButton
import net.skyscanner.backpack.compose.button.BpkButtonIconPosition
import net.skyscanner.backpack.compose.button.BpkButtonSize
import net.skyscanner.backpack.compose.button.BpkButtonType
import net.skyscanner.backpack.compose.icons.BpkIcons
import net.skyscanner.backpack.compose.icons.lg.LongArrowRight
import net.skyscanner.backpack.compose.icons.sm.LongArrowRight
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.demo.R
import kotlin.time.Duration
import kotlin.time.ExperimentalTime

@Composable
fun ButtonsStory(
  size: BpkButtonSize,
  modifier: Modifier = Modifier,
) {
  LazyColumn(modifier.fillMaxSize()) {
    item {
      ButtonsRow(
        type = BpkButtonType.Primary,
        size = size,
        enabled = false,
      )
    }
    items(BpkButtonType.values()) {
      ButtonsRow(
        type = it,
        size = size,
        enabled = true,
      )
    }
    item {
      ButtonsRow(
        type = BpkButtonType.Link,
        size = size,
        enabled = false,
      )
    }
  }
}

@Composable
private fun ButtonsRow(
  type: BpkButtonType,
  size: BpkButtonSize,
  enabled: Boolean,
  modifier: Modifier = Modifier,
) {
  val icon = when (size) {
    BpkButtonSize.Default -> BpkIcons.Sm.LongArrowRight
    BpkButtonSize.Large -> BpkIcons.Lg.LongArrowRight
  }

  Row(
    modifier
      .fillMaxWidth()
      .padding(vertical = BpkSpacing.Md, horizontal = BpkSpacing.Base),
    horizontalArrangement = Arrangement.SpaceBetween,
  ) {

    LoadingScope {
      BpkButton(
        text = stringResource(R.string.button),
        size = size,
        type = type,
        loading = loading,
        enabled = enabled,
        onClick = ::load,
      )
    }

    LoadingScope {
      BpkButton(
        text = stringResource(R.string.button),
        icon = icon,
        position = BpkButtonIconPosition.Start,
        size = size,
        type = type,
        enabled = enabled,
        loading = loading,
        onClick = ::load,
      )
    }

    LoadingScope {
      BpkButton(
        text = stringResource(R.string.button),
        icon = icon,
        position = BpkButtonIconPosition.End,
        size = size,
        type = type,
        enabled = enabled,
        loading = loading,
        onClick = ::load,
      )
    }

    LoadingScope {
      BpkButton(
        icon = icon,
        contentDescription = stringResource(R.string.button),
        size = size,
        type = type,
        enabled = enabled,
        loading = loading,
        onClick = ::load,
      )
    }
  }
}

@Composable
private inline fun LoadingScope(
  content: @Composable LoadingScope.() -> Unit,
) {
  val coroutineScope = rememberCoroutineScope()
  val loadingScope = remember(coroutineScope) { LoadingScope(coroutineScope) }
  loadingScope.content()
}

private class LoadingScope(private val scope: CoroutineScope) {

  var loading by mutableStateOf(false)

  @OptIn(ExperimentalTime::class)
  fun load() {
    scope.launch {
      loading = true
      delay(Duration.seconds(2))
      loading = false
    }
  }
}
