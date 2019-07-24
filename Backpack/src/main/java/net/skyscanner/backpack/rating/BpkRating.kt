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
import net.skyscanner.backpack.rating.internal.RatingScore
import net.skyscanner.backpack.rating.internal.RatingAppearance
import net.skyscanner.backpack.rating.internal.RatingSelectors
import net.skyscanner.backpack.text.BpkText
import net.skyscanner.backpack.util.createContextThemeWrapper
import net.skyscanner.backpack.util.unsafeLazy

open class BpkRating @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
) : ConstraintLayout(
  createContextThemeWrapper(context, attrs, R.attr.bpkRatingStyle),
  attrs,
  defStyleAttr) {

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

  enum class Orientation {
    Horizontal,
    Vertical
  }

  private val score = RatingScore(this.context, attrs, defStyleAttr)
  private val selectors = RatingSelectors(this.context, attrs, defStyleAttr)
  private val appearance = RatingAppearance(this.context, attrs, defStyleAttr)

  private val badge by unsafeLazy {
    findViewById<BpkText>(R.id.bpk_rating_badge).apply {
      appearance.score.applyTo(this)
      appearance.badgeSize.let {
        minHeight = it
        maxHeight = it
        minWidth = it
        maxWidth = it
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
  var rating: Float
    get() = score.rating
    set(@FloatRange(from = 0.0, to = 10.0) value) {
      score.rating = value
      update(score())
    }

  init {
    when (appearance.orientation) {
      Orientation.Horizontal -> R.layout.view_bpk_rating_horizontal
      Orientation.Vertical -> R.layout.view_bpk_rating_vertical
    }.let {
      LayoutInflater.from(this.context).inflate(it, this, true)
    }

    titleView.layoutParams = titleView.layoutParams
      .let { it as MarginLayoutParams }
      .apply {
        when (appearance.orientation) {
          Orientation.Horizontal -> marginStart = appearance.spacing
          Orientation.Vertical -> topMargin = appearance.spacing
        }
      }

    update(score())
  }

  private fun update(score: Score) {
    updateScore(score)
    updateTitle(score)
    updateSubtitle(score)
  }

  private fun updateScore(score: Score) {
    val tintList = selectors.colors(score)
    ViewCompat.setBackgroundTintList(badge, tintList)

    if (appearance.size == Size.Icon) {
      badge.text = null
      selectors.icon(score)
        ?.let {
          val size = resources.getDimensionPixelSize(R.dimen.bpkSpacingBase)
          it.setBounds(0, 0, size, size)
          val drawablePadding = (appearance.badgeSize / 2) - (size / 2)
          badge.setPaddingRelative(drawablePadding, 0, drawablePadding, 0)
          badge.setCompoundDrawablesRelative(it, null, null, null)
          // todo: move this to badge initialization then we have consistent behaviour of setDrawableTint
          badge.setDrawableTint(badge.currentTextColor)
        }
    } else {
      badge.setPadding(0, 0, 0, 0)
      badge.text = "$rating"
    }
  }

  private fun updateTitle(score: Score) {
    titleView.text = title(score)
  }

  private fun updateSubtitle(score: Score) {
    subtitleView.text = subtitle(score)
  }
}
