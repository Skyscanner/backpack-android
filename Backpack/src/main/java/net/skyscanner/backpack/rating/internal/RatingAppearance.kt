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
  defStyleAttr: Int = 0,
  defaultOrientation: BpkRating.Orientation,
  defaultSize: BpkRating.Size
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
    var orientation = defaultOrientation
    var style = defaultSize.style
    context.theme.obtainStyledAttributes(
      attrs,
      R.styleable.BpkRating,
      defStyleAttr, 0
    ).use { ta ->
      orientation = ta.getInt(R.styleable.BpkRating_ratingOrientation, orientation.xmlId)
        .let(::mapXmlToOrientation) ?: orientation

      style = ta.getInt(R.styleable.BpkRating_ratingSize, style.xmlId)
        .let(::mapXmlToStyle) ?: style
    }

    this.orientation = orientation
    this.size = style.size

    this.title = BpkText.getFont(context, style.titleSize, BpkText.Weight.EMPHASIZED)
    this.subtitle = style.subtitleSize?.let { BpkText.getFont(context, it, BpkText.Weight.NORMAL) }
    this.score = BpkText.getFont(context, style.scoreSize, BpkText.Weight.EMPHASIZED)
    this.badgeSize = context.resources.getDimensionPixelSize(style.badgeSize)
    this.spacing = context.resources.getDimensionPixelSize(style.spacing)
  }

  private val BpkRating.Orientation.xmlId
    get() = when (this) {
      BpkRating.Orientation.Horizontal -> 0
      BpkRating.Orientation.Vertical -> 1
    }

  private fun mapXmlToOrientation(id: Int) =
    BpkRating.Orientation.values().find { it.xmlId == id }

  private val RatingStyles.xmlId
    get() = when (this) {
      RatingStyles.Icon -> 0
      RatingStyles.ExtraSmall -> 1
      RatingStyles.Small -> 2
      RatingStyles.Base -> 3
      RatingStyles.Large -> 4
    }

  private fun mapXmlToStyle(id: Int) =
    RatingStyles.values().find { it.xmlId == id }

  private val BpkRating.Size.style
    get() = when (this) {
      BpkRating.Size.Icon -> RatingStyles.Icon
      BpkRating.Size.ExtraSmall -> RatingStyles.ExtraSmall
      BpkRating.Size.Small -> RatingStyles.Small
      BpkRating.Size.Base -> RatingStyles.Base
      BpkRating.Size.Large -> RatingStyles.Large
    }

  private val RatingStyles.size
    get() = when (this) {
      RatingStyles.Icon -> BpkRating.Size.Icon
      RatingStyles.ExtraSmall -> BpkRating.Size.ExtraSmall
      RatingStyles.Small -> BpkRating.Size.Small
      RatingStyles.Base -> BpkRating.Size.Base
      RatingStyles.Large -> BpkRating.Size.Large
    }

}
