/**
 * Backpack for Android - Skyscanner's Design System
 *
 * Copyright 2018-2020 Skyscanner Ltd
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
  defaultStyle: BpkRating.Style,
  defaultSize: BpkRating.Size
) {

  val style: BpkRating.Style

  val size: BpkRating.Size

  val score: BpkText.FontDefinition

  val title: BpkText.FontDefinition

  val subtitle: BpkText.FontDefinition?

  @Px
  val badgeWidth: Int

  @Px
  val badgeHeight: Int

  @Px
  val spacing: Int

  init {
    var style = defaultStyle
    var size = defaultSize
    context.theme.obtainStyledAttributes(
      attrs,
      R.styleable.BpkRating,
      defStyleAttr, 0
    ).use { ta ->
      style = ta.getInt(R.styleable.BpkRating_ratingStyle, style.xmlId)
        .let(::mapXmlToStyle) ?: style

      size = ta.getInt(R.styleable.BpkRating_ratingSize, size.xmlId)
        .let(::mapXmlToSize) ?: size
    }

    this.style = style
    this.size = size
    val styles = size.style

    this.title = BpkText.getFont(context, styles.titleSize, BpkText.Weight.EMPHASIZED)
    this.subtitle = styles.subtitleSize?.let { BpkText.getFont(context, it, BpkText.Weight.NORMAL) }
    this.score = BpkText.getFont(context, styles.scoreSize, BpkText.Weight.EMPHASIZED)
    this.spacing = context.resources.getDimensionPixelSize(styles.spacing)
    this.badgeWidth = when (style) {
      BpkRating.Style.Pill -> context.resources.getDimensionPixelSize(styles.pillWidth)
      BpkRating.Style.Horizontal -> context.resources.getDimensionPixelSize(styles.badgeSize)
      BpkRating.Style.Vertical -> context.resources.getDimensionPixelSize(styles.badgeSize)
    }
    this.badgeHeight = when (style) {
      BpkRating.Style.Pill -> context.resources.getDimensionPixelSize(styles.pillHeight)
      BpkRating.Style.Horizontal -> context.resources.getDimensionPixelSize(styles.badgeSize)
      BpkRating.Style.Vertical -> context.resources.getDimensionPixelSize(styles.badgeSize)
    }
  }

  private val BpkRating.Style.xmlId
    get() = when (this) {
      BpkRating.Style.Horizontal -> 0
      BpkRating.Style.Vertical -> 1
      BpkRating.Style.Pill -> 2
    }

  private fun mapXmlToStyle(id: Int) =
    BpkRating.Style.values().find { it.xmlId == id }

  private val BpkRating.Size.xmlId
    get() = when (this) {
      BpkRating.Size.Icon -> 0
      BpkRating.Size.ExtraSmall -> 1
      BpkRating.Size.Small -> 2
      BpkRating.Size.Base -> 3
      BpkRating.Size.Large -> 4
    }

  private fun mapXmlToSize(id: Int) =
    BpkRating.Size.values().find { it.xmlId == id }

  private val BpkRating.Size.style
    get() = when (this) {
      BpkRating.Size.Icon -> RatingStyles.Icon
      BpkRating.Size.ExtraSmall -> RatingStyles.ExtraSmall
      BpkRating.Size.Small -> RatingStyles.Small
      BpkRating.Size.Base -> RatingStyles.Base
      BpkRating.Size.Large -> RatingStyles.Large
    }
}
