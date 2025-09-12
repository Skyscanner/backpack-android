/**
 * Backpack for Android - Skyscanner's Design System
 *
 * Copyright 2018 - 2025 Skyscanner Ltd
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
    @DimenRes val spacingPill: Int,
) {
    Icon(
        badgeSize = net.skyscanner.backpack.common.R.dimen.bpkSpacingLg,
        pillWidth = net.skyscanner.backpack.common.R.dimen.bpkSpacingXl,
        pillHeight = net.skyscanner.backpack.common.R.dimen.bpkSpacingLg,
        scoreStyle = BpkText.TextStyle.Label2,
        titleStyle = BpkText.TextStyle.Caption,
        subtitleStyle = null,
        spacing = net.skyscanner.backpack.common.R.dimen.bpkSpacingSm,
        spacingPill = net.skyscanner.backpack.common.R.dimen.bpkSpacingMd,
    ),
    ExtraSmall(
        badgeSize = net.skyscanner.backpack.common.R.dimen.bpkSpacingXl,
        pillWidth = net.skyscanner.backpack.common.R.dimen.bpkSpacingXl,
        pillHeight = net.skyscanner.backpack.common.R.dimen.bpkSpacingLg,
        scoreStyle = BpkText.TextStyle.Label2,
        titleStyle = BpkText.TextStyle.Caption,
        subtitleStyle = null,
        spacing = net.skyscanner.backpack.common.R.dimen.bpkSpacingSm,
        spacingPill = net.skyscanner.backpack.common.R.dimen.bpkSpacingMd,
    ),
    Small(
        badgeSize = R.dimen.bpk_rating_score_badge_size_small,
        pillWidth = net.skyscanner.backpack.common.R.dimen.bpkSpacingXl,
        pillHeight = net.skyscanner.backpack.common.R.dimen.bpkSpacingLg,
        scoreStyle = BpkText.TextStyle.Label2,
        titleStyle = BpkText.TextStyle.Label2,
        subtitleStyle = BpkText.TextStyle.Caption,
        spacing = net.skyscanner.backpack.common.R.dimen.bpkSpacingMd,
        spacingPill = net.skyscanner.backpack.common.R.dimen.bpkSpacingMd,
    ),
    Base(
        badgeSize = net.skyscanner.backpack.common.R.dimen.bpkSpacingXxl,
        pillWidth = R.dimen.bpk_rating_score_badge_size_large,
        pillHeight = R.dimen.bpk_rating_score_pill_height_base,
        scoreStyle = BpkText.TextStyle.Heading5,
        titleStyle = BpkText.TextStyle.Heading5,
        subtitleStyle = BpkText.TextStyle.Footnote,
        spacing = net.skyscanner.backpack.common.R.dimen.bpkSpacingMd,
        spacingPill = R.dimen.bpk_rating_score_pill_spacing_medium,
    ),
    Large(
        badgeSize = R.dimen.bpk_rating_score_badge_size_large,
        pillWidth = R.dimen.bpk_rating_score_pill_width_large,
        pillHeight = R.dimen.bpk_rating_score_pill_height_large,
        scoreStyle = BpkText.TextStyle.Heading4,
        titleStyle = BpkText.TextStyle.Heading4,
        subtitleStyle = BpkText.TextStyle.BodyDefault,
        spacing = net.skyscanner.backpack.common.R.dimen.bpkSpacingMd,
        spacingPill = net.skyscanner.backpack.common.R.dimen.bpkSpacingBase,
    ),
}
