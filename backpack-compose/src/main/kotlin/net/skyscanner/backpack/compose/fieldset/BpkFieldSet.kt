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

package net.skyscanner.backpack.compose.fieldset

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.icon.BpkIconSize
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.compose.tokens.ExclamationCircle

internal val LocalFieldStatus = staticCompositionLocalOf<BpkFieldStatus> { BpkFieldStatus.Default }

sealed interface BpkFieldStatus {

  object Default : BpkFieldStatus

  object Disabled : BpkFieldStatus

  data class Error(val text: String) : BpkFieldStatus

  object Validated : BpkFieldStatus
}

@Composable
fun BpkFieldSet(
  modifier: Modifier = Modifier,
  label: String? = null,
  description: String? = null,
  status: BpkFieldStatus = BpkFieldStatus.Default,
  content: @Composable ColumnScope.(BpkFieldStatus) -> Unit,
) {

  Column(modifier) {

    if (label != null) {
      BpkText(
        text = label,
        style = BpkTheme.typography.label2,
        modifier = Modifier.padding(bottom = BpkSpacing.Md),
        color = animateColorAsState(
          when (status) {
            is BpkFieldStatus.Disabled -> BpkTheme.colors.textDisabled
            is BpkFieldStatus.Error -> BpkTheme.colors.textError
            is BpkFieldStatus.Validated -> BpkTheme.colors.textPrimary
            is BpkFieldStatus.Default -> BpkTheme.colors.textPrimary
          }
        ).value,
      )
    }

    CompositionLocalProvider(LocalFieldStatus provides status) {
      content(status)
    }

    if (description != null) {
      BpkText(
        text = description,
        style = BpkTheme.typography.footnote,
        color = animateColorAsState(
          when (status) {
            is BpkFieldStatus.Disabled -> BpkTheme.colors.textDisabled
            else -> BpkTheme.colors.textSecondary
          }
        ).value,
        modifier = Modifier.padding(top = BpkSpacing.Md),
      )
    }

    var lastErrorText by remember { mutableStateOf("") }
    if (status is BpkFieldStatus.Error) {
      lastErrorText = status.text
    }

    AnimatedVisibility(status is BpkFieldStatus.Error) {
      Row(
        horizontalArrangement = Arrangement.spacedBy(BpkSpacing.Md),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(top = BpkSpacing.Md),
      ) {
        BpkIcon(
          icon = BpkIcon.ExclamationCircle,
          contentDescription = null,
          size = BpkIconSize.Small,
          tint = BpkTheme.colors.textError,
        )
        BpkText(
          text = lastErrorText,
          style = BpkTheme.typography.label2,
          color = BpkTheme.colors.textError,
        )
      }
    }
  }
}
