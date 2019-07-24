package net.skyscanner.backpack.rating.internal

import androidx.annotation.DimenRes
import net.skyscanner.backpack.R
import net.skyscanner.backpack.rating.BpkRating
import net.skyscanner.backpack.text.BpkText

internal enum class RatingStyles(
  @DimenRes val badgeSize: Int,
  @BpkText.Styles val scoreSize: Int,
  @BpkText.Styles val titleSize: Int,
  @BpkText.Styles val subtitleSize: Int?,
  @DimenRes val spacing: Int
) {
  Icon(
    badgeSize = R.dimen.bpkSpacingLg,
    scoreSize = BpkText.SM,
    titleSize = BpkText.XS,
    subtitleSize = null,
    spacing = R.dimen.bpkSpacingSm
  ),
  ExtraSmall(
    badgeSize = R.dimen.bpkSpacingXl,
    scoreSize = BpkText.SM,
    titleSize = BpkText.XS,
    subtitleSize = null,
    spacing = R.dimen.bpkSpacingSm
  ),
  Small(
    badgeSize = R.dimen.bpk_rating_score_badge_size_small,
    scoreSize = BpkText.SM,
    titleSize = BpkText.SM,
    subtitleSize = BpkText.XS,
    spacing = R.dimen.bpkSpacingMd
  ),
  Base(
    badgeSize = R.dimen.bpkSpacingXxl,
    scoreSize = BpkText.BASE,
    titleSize = BpkText.BASE,
    subtitleSize = BpkText.SM,
    spacing = R.dimen.bpkSpacingMd
  ),
  Large(
    badgeSize = R.dimen.bpk_rating_score_badge_size_large,
    scoreSize = BpkText.LG,
    titleSize = BpkText.LG,
    subtitleSize = BpkText.BASE,
    spacing = R.dimen.bpkSpacingMd
  ),
}
