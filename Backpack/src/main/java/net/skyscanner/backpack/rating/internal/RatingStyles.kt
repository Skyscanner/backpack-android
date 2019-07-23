package net.skyscanner.backpack.rating.internal

import androidx.annotation.DimenRes
import net.skyscanner.backpack.R
import net.skyscanner.backpack.rating.BpkRating
import net.skyscanner.backpack.text.BpkText

internal enum class RatingStyles(
  val id: Int,
  val size: BpkRating.Size,
  @DimenRes val badgeSize: Int,
  @BpkText.Styles val scoreSize: Int,
  @BpkText.Styles val titleSize: Int,
  @BpkText.Styles val subtitleSize: Int?,
  @DimenRes val spacing: Int
) {
  Icon(
    id = 0,
    size = BpkRating.Size.Icon,
    badgeSize = R.dimen.bpkSpacingLg,
    scoreSize = BpkText.SM,
    titleSize = BpkText.XS,
    subtitleSize = null,
    spacing = R.dimen.bpkSpacingSm
  ),
  ExtraSmall(
    id = 1,
    size = BpkRating.Size.ExtraSmall,
    badgeSize = R.dimen.bpkSpacingXl,
    scoreSize = BpkText.SM,
    titleSize = BpkText.XS,
    subtitleSize = null,
    spacing = R.dimen.bpkSpacingSm
  ),
  Small(
    id = 2,
    size = BpkRating.Size.Small,
    badgeSize = R.dimen.bpk_rating_score_badge_size_small,
    scoreSize = BpkText.SM,
    titleSize = BpkText.SM,
    subtitleSize = BpkText.XS,
    spacing = R.dimen.bpkSpacingMd
  ),
  Base(
    id = 3,
    size = BpkRating.Size.Base,
    badgeSize = R.dimen.bpkSpacingXxl,
    scoreSize = BpkText.BASE,
    titleSize = BpkText.BASE,
    subtitleSize = BpkText.SM,
    spacing = R.dimen.bpkSpacingMd
  ),
  Large(
    id = 4,
    size = BpkRating.Size.Large,
    badgeSize = R.dimen.bpk_rating_score_badge_size_large,
    scoreSize = BpkText.LG,
    titleSize = BpkText.LG,
    subtitleSize = BpkText.BASE,
    spacing = R.dimen.bpkSpacingMd
  ),
}
