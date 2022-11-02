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

package net.skyscanner.backpack.compose.rating.internal

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.LocalContentColor
import androidx.compose.material.LocalTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.semantics.invisibleToUser
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
import net.skyscanner.backpack.compose.rating.BpkRatingScale
import net.skyscanner.backpack.compose.rating.BpkRatingSize
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import kotlin.math.roundToInt

@Composable
internal fun BpkRatingNumbers(
  value: Float,
  scale: BpkRatingScale?,
  size: BpkRatingSize,
  modifier: Modifier = Modifier,
) {
  Row(
    modifier = modifier,
  ) {

    BpkText(
      modifier = Modifier.alignByBaseline(),
      text = value.roundToOneDecimal().toString(),
      style = when (size) {
        BpkRatingSize.Base -> BpkTheme.typography.label1
        BpkRatingSize.Large -> BpkTheme.typography.hero5
      },
      color = BpkTheme.colors.textPrimary,
      maxLines = 1,
      overflow = TextOverflow.Ellipsis,
    )

    if (scale != null) {
      BpkText(
        modifier = Modifier.alignByBaseline(),
        text = when (scale) {
          BpkRatingScale.ZeroToFive -> "/5"
          BpkRatingScale.ZeroToTen -> "/10"
        },
        style = when (size) {
          BpkRatingSize.Base -> BpkTheme.typography.caption
          BpkRatingSize.Large -> BpkTheme.typography.bodyDefault
        },
        color = BpkTheme.colors.textSecondary,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
      )
    }

  }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
internal fun BpkRatingTitle(
  modifier: Modifier = Modifier,
  content: @Composable () -> Unit,
) {
  CompositionLocalProvider(
    LocalTextStyle provides BpkTheme.typography.heading5,
    LocalContentColor provides BpkTheme.colors.textPrimary,
    LocalContentAlpha provides 1f,
  ) {
    Box(
      modifier = modifier.heightIn(max = BpkSpacing.Lg),
      contentAlignment = Alignment.CenterStart,
    ) {
      // a little trick to provide baseline params for the custom layouts with invisible text
  BpkText(text = "", modifier = modifier
    .alpha(0f)
    .semantics { invisibleToUser() },
  )
      content()
    }
  }
}

@Composable
internal fun BpkRatingSubtitle(
  subtitle: String,
  modifier: Modifier = Modifier,
) {
  BpkText(
    modifier = modifier,
    text = subtitle,
    style = BpkTheme.typography.bodyDefault,
    color = BpkTheme.colors.textSecondary,
    maxLines = 1,
    overflow = TextOverflow.Ellipsis,
  )
}

private fun Float.roundToOneDecimal(): Float =
  (this * 10f).roundToInt() / 10f
