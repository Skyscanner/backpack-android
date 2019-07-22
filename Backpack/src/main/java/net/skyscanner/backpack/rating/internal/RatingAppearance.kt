package net.skyscanner.backpack.rating.internal

import android.content.Context
import android.util.AttributeSet
import androidx.annotation.Px
import net.skyscanner.backpack.R
import net.skyscanner.backpack.rating.BpkRating
import net.skyscanner.backpack.text.BpkText
import net.skyscanner.backpack.util.use

internal class RatingAppearance(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
) {

  val orientation: BpkRating.Orientation

  val size: BpkRating.Size

  val score: BpkText.FontDefinition

  val title: BpkText.FontDefinition

  val subtitle: BpkText.FontDefinition?

  @Px
  val badgeSize: Int

  @Px
  val spacing: Int

  init {
    var orientation = BpkRating.Orientation.Horizontal
    var size = RatingStyles.Base
    context.theme.obtainStyledAttributes(
      attrs,
      R.styleable.BpkRating,
      defStyleAttr, 0
    ).use { ta ->
      orientation = ta.getInt(R.styleable.BpkRating_ratingOrientation, 0).let(::getOrientation)
        ?: orientation
      size = ta.getInt(R.styleable.BpkRating_ratingSize, size.id).let(::getStyle) ?: size
    }

    this.orientation = orientation
    this.size = size.size
    this.title = BpkText.getFont(context, size.titleSize, BpkText.Weight.EMPHASIZED)
    this.subtitle = size.subtitleSize?.let { BpkText.getFont(context, it, BpkText.Weight.NORMAL) }
    this.score = BpkText.getFont(context, size.scoreSize, BpkText.Weight.EMPHASIZED)
    this.badgeSize = context.resources.getDimensionPixelSize(size.badgeSize)
    this.spacing = context.resources.getDimensionPixelSize(size.spacing)
  }

  private fun getStyle(id: Int) = RatingStyles.values().find { it.id == id }

  private fun getOrientation(id: Int) = when (id) {
    0 -> BpkRating.Orientation.Horizontal
    1 -> BpkRating.Orientation.Vertical
    else -> null
  }
}
