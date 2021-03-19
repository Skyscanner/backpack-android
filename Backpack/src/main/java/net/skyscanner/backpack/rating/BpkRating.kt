/**
 * Backpack for Android - Skyscanner's Design System
 *
 * Copyright 2018-2021 Skyscanner Ltd
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

package net.skyscanner.backpack.rating

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.FloatRange
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import net.skyscanner.backpack.R
import net.skyscanner.backpack.rating.internal.RatingAppearance
import net.skyscanner.backpack.rating.internal.RatingScore
import net.skyscanner.backpack.rating.internal.RatingSelectors
import net.skyscanner.backpack.text.BpkText
import net.skyscanner.backpack.util.createContextThemeWrapper
import net.skyscanner.backpack.util.unsafeLazy

open class BpkRating private constructor(
  context: Context,
  attrs: AttributeSet?,
  defStyleAttr: Int,
  defaultStyle: Style,
  defaultSize: Size
) : ConstraintLayout(
  createContextThemeWrapper(context, attrs, R.attr.bpkRatingStyle), attrs, defStyleAttr
) {

  @JvmOverloads
  constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
  ) : this(context, attrs, defStyleAttr, Style.Horizontal, Size.Base)

  constructor(
    context: Context,
    style: Style,
    size: Size
  ) : this(context, null, 0, style, size)

  enum class Score {
    Low,
    Medium,
    High
  }

  enum class Size {
    Icon,
    ExtraSmall,
    Small,
    Base,
    Large,
  }

  enum class Style {
    Horizontal,
    Vertical,
    Pill,
  }

  private val score = RatingScore(this.context, attrs, defStyleAttr)
  private val selectors = RatingSelectors(this.context, attrs, defStyleAttr)
  private val appearance = RatingAppearance(this.context, attrs, defStyleAttr, defaultStyle, defaultSize)

  private val badge by unsafeLazy {
    findViewById<BpkText>(R.id.bpk_rating_badge).apply {
      appearance.score.applyTo(this)
      appearance.badgeWidth.let {
        minWidth = it
        maxWidth = it
      }
      appearance.badgeHeight.let {
        minHeight = it
        maxHeight = it
      }
    }
  }

  private val titleView by unsafeLazy {
    findViewById<BpkText>(R.id.bpk_rating_title).apply {
      appearance.title.applyTo(this)
    }
  }

  private val subtitleView by unsafeLazy {
    findViewById<BpkText>(R.id.bpk_rating_subtitle).apply {
      val subtitleAppearance = appearance.subtitle
      if (subtitleAppearance != null) {
        subtitleAppearance.applyTo(this)
      } else {
        visibility = View.GONE
      }
    }
  }

  var icon: (Score) -> Drawable?
    get() = selectors.icon
    set(value) {
      selectors.icon = value
      updateScore(score())
    }

  var title: (Score) -> CharSequence?
    get() = selectors.title
    set(value) {
      selectors.title = value
      updateTitle(score())
    }

  var subtitle: (Score) -> CharSequence?
    get() = selectors.subtitle
    set(value) {
      selectors.subtitle = value
      updateSubtitle(score())
    }

  @get:FloatRange(from = 0.0, to = 10.0)
  var value: Float
    get() = score.rating
    set(@FloatRange(from = 0.0, to = 10.0) value) {
      score.rating = value
      update(score())
    }

  init {
    when (appearance.style) {
      Style.Pill -> R.layout.view_bpk_rating_pill
      Style.Horizontal -> R.layout.view_bpk_rating_horizontal
      Style.Vertical -> R.layout.view_bpk_rating_vertical
    }.let {
      LayoutInflater.from(this.context).inflate(it, this, true)
    }

    titleView.layoutParams = titleView.layoutParams
      .let { it as MarginLayoutParams }
      .apply {
        when (appearance.style) {
          Style.Horizontal -> marginStart = appearance.spacing
          Style.Pill -> marginStart = appearance.spacing
          Style.Vertical -> topMargin = appearance.spacing
        }
      }

    update(score())
  }

  private fun update(value: Score) {
    updateScore(value)
    updateTitle(value)
    updateSubtitle(value)
  }

  private fun updateScore(value: Score) {
    ViewCompat.setBackgroundTintList(badge, selectors.backgroundColor(value))
    badge.setTextColor(selectors.contentColor(value))

    if (appearance.size == Size.Icon) {
      badge.text = null
      selectors.icon(value)
        ?.let {
          val size = resources.getDimensionPixelSize(R.dimen.bpkSpacingBase)
          it.setBounds(0, 0, size, size)
          val drawablePadding = (appearance.badgeWidth - size) / 2
          badge.setPaddingRelative(drawablePadding, 0, drawablePadding, 0)
          badge.setCompoundDrawablesRelative(it, null, null, null)
          // todo: move this to badge initialization then we have consistent behaviour of setDrawableTint
          badge.setDrawableTint(badge.currentTextColor)
        }
    } else {
      badge.setPadding(0, 0, 0, 0)
      badge.text = score.toString()
    }
  }

  private fun updateTitle(value: Score) {
    titleView.text = title(value)
  }

  private fun updateSubtitle(value: Score) {
    subtitleView.text = subtitle(value)
  }
}
