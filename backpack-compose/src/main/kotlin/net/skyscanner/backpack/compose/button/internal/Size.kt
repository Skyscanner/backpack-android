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

package net.skyscanner.backpack.compose.button.internal

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import net.skyscanner.backpack.compose.button.BpkButtonSize
import net.skyscanner.backpack.compose.button.BpkButtonType
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkSpacing

@Composable
internal fun BpkButtonSize.textStyle(): TextStyle =
  when (this) {
    BpkButtonSize.Default -> BpkTheme.typography.label2
    BpkButtonSize.Large -> BpkTheme.typography.label1
  }

internal val BpkButtonSize.iconSize: Dp
  get() =
    when (this) {
      BpkButtonSize.Default -> BpkSpacing.Base
      BpkButtonSize.Large -> BpkSpacing.Lg
    }

internal val BpkButtonSize.minHeight: Dp
  get() =
    when (this) {
      BpkButtonSize.Default -> 36.dp
      BpkButtonSize.Large -> 48.dp
    }

internal val BpkButtonSize.horizontalSpacing: Dp
  get() =
    BpkSpacing.Md

internal val BpkButtonType.contentPadding: PaddingValues
  get() =
    when (this) {
      BpkButtonType.Link, BpkButtonType.LinkOnDark -> PaddingValues(0.dp)
      else -> PaddingValues(horizontal = BpkSpacing.Base)
    }
