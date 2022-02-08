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

package net.skyscanner.backpack.rating.internal

import androidx.annotation.DimenRes
import net.skyscanner.backpack.R
import net.skyscanner.backpack.text.BpkText

internal enum class RatingStyles(
  @DimenRes val badgeSize: Int,
  @DimenRes val pillWidth: Int,
  @DimenRes val pillHeight: Int,
  val scoreStyle: BpkText.TextStyle,
  val titleStyle: BpkText.TextStyle,
  val subtitleStyle: BpkText.TextStyle?,
  @DimenRes val spacing: Int,
  @DimenRes val spacingPill: Int
) {
  Icon(
    badgeSize = R.dimen.bpkSpacingLg,
    pillWidth = R.dimen.bpkSpacingXl,
    pillHeight = R.dimen.bpkSpacingLg,
    scoreStyle = BpkText.TextStyle.LABEL2,
    titleStyle = BpkText.TextStyle.CAPTION,
    subtitleStyle = null,
    spacing = R.dimen.bpkSpacingSm,
    spacingPill = R.dimen.bpkSpacingMd
  ),
  ExtraSmall(
    badgeSize = R.dimen.bpkSpacingXl,
    pillWidth = R.dimen.bpkSpacingXl,
    pillHeight = R.dimen.bpkSpacingLg,
    scoreStyle = BpkText.TextStyle.LABEL2,
    titleStyle = BpkText.TextStyle.CAPTION,
    subtitleStyle = null,
    spacing = R.dimen.bpkSpacingSm,
    spacingPill = R.dimen.bpkSpacingMd
  ),
  Small(
    badgeSize = R.dimen.bpk_rating_score_badge_size_small,
    pillWidth = R.dimen.bpkSpacingXl,
    pillHeight = R.dimen.bpkSpacingLg,
    scoreStyle = BpkText.TextStyle.LABEL2,
    titleStyle = BpkText.TextStyle.LABEL2,
    subtitleStyle = BpkText.TextStyle.CAPTION,
    spacing = R.dimen.bpkSpacingMd,
    spacingPill = R.dimen.bpkSpacingMd
  ),
  Base(
    badgeSize = R.dimen.bpkSpacingXxl,
    pillWidth = R.dimen.bpk_rating_score_badge_size_large,
    pillHeight = R.dimen.bpk_rating_score_pill_height_base,
    scoreStyle = BpkText.TextStyle.HEADING5,
    titleStyle = BpkText.TextStyle.HEADING5,
    subtitleStyle = BpkText.TextStyle.FOOTNOTE,
    spacing = R.dimen.bpkSpacingMd,
    spacingPill = R.dimen.bpk_rating_score_pill_spacing_medium
  ),
  Large(
    badgeSize = R.dimen.bpk_rating_score_badge_size_large,
    pillWidth = R.dimen.bpk_rating_score_pill_width_large,
    pillHeight = R.dimen.bpk_rating_score_pill_height_large,
    scoreStyle = BpkText.TextStyle.HEADING4,
    titleStyle = BpkText.TextStyle.HEADING4,
    subtitleStyle = BpkText.TextStyle.BODY_DEFAULT,
    spacing = R.dimen.bpkSpacingMd,
    spacingPill = R.dimen.bpkSpacingBase
  ),
}
