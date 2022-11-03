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

package net.skyscanner.backpack.compose.rating

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import net.skyscanner.backpack.compose.rating.internal.BpkRatingLayout
import net.skyscanner.backpack.compose.text.BpkText

enum class BpkRatingSize {
  Base,
  Large,
}

enum class BpkRatingScale {
  ZeroToFive,
  ZeroToTen,
}

@Composable
fun BpkRating(
  title: String,
  value: Float,
  modifier: Modifier = Modifier,
  scale: BpkRatingScale = BpkRatingScale.ZeroToFive,
  size: BpkRatingSize = BpkRatingSize.Base,
  subtitle: String? = null,
  showScale: Boolean = true,
) {
  BpkRatingLayout(
    value = value,
    modifier = modifier,
    scale = scale,
    size = size,
    subtitle = subtitle,
    showScale = showScale,
  ) {
    BpkText(
      text = title,
      maxLines = 1,
      overflow = TextOverflow.Ellipsis,
    )
  }
}

@Composable
fun BpkRating(
  value: Float,
  modifier: Modifier = Modifier,
  scale: BpkRatingScale = BpkRatingScale.ZeroToFive,
  size: BpkRatingSize = BpkRatingSize.Base,
  subtitle: String? = null,
  showScale: Boolean = true,
  title: @Composable () -> Unit,
) {
  BpkRatingLayout(
    value = value,
    modifier = modifier,
    scale = scale,
    size = size,
    subtitle = subtitle,
    showScale = showScale,
  ) {
    title()
  }
}
