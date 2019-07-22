package net.skyscanner.backpack.rating

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.TextUtils
import android.text.style.ForegroundColorSpan
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import androidx.annotation.FloatRange
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.view.ViewCompat
import net.skyscanner.backpack.R
import net.skyscanner.backpack.rating.internal.RatingScore
import net.skyscanner.backpack.rating.internal.RatingAppearance
import net.skyscanner.backpack.rating.internal.RatingSelectors
import net.skyscanner.backpack.text.BpkFontSpan
import net.skyscanner.backpack.text.BpkText
import net.skyscanner.backpack.util.BpkTheme
import net.skyscanner.backpack.util.createContextThemeWrapper

open class BpkRating @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
) : LinearLayoutCompat(
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

  var icon: (Score) -> Drawable?
    get() = selectors.icon
    set(value) {
      selectors.icon = value
      updateScore()
    }

  var title: (Score) -> CharSequence?
    get() = selectors.title
    set(value) {
      selectors.title = value
      updateDescription()
    }

  var subtitle: (Score) -> CharSequence?
    get() = selectors.subtitle
    set(value) {
      selectors.subtitle = value
      updateDescription()
    }

  @get:FloatRange(from = 0.0, to = 10.0)
  var rating: Float
    get() = score.rating
    set(@FloatRange(from = 0.0, to = 10.0) value) {
      score.rating = value
      updateScore()
      updateDescription()
    }

  private val score = RatingScore(this.context, attrs, defStyleAttr)
  private val selectors = RatingSelectors(this.context, attrs, defStyleAttr)
  private val appearance = RatingAppearance(this.context, attrs, defStyleAttr)

  private val badge = BpkText(this.context).apply {
    setTextColor(BpkTheme.getColor(this.context, R.color.bpkWhite))
    setBackgroundResource(R.drawable.bpk_bg_rating_badge)
    appearance.score.applyTo(this)
    gravity = Gravity.CENTER
    maxLines = 1
    ellipsize = TextUtils.TruncateAt.END
  }

  private val descriptionView = BpkText(this.context).apply {
    setTextColor(BpkTheme.getColor(this.context, R.color.bpkGray500))
    appearance.title.applyTo(this)
    maxLines = 2
    ellipsize = TextUtils.TruncateAt.END
    gravity = Gravity.START
  }

  init {
    val badgeParams = LayoutParams(appearance.badgeSize, appearance.badgeSize)
    when (appearance.orientation) {
      Orientation.Horizontal -> {
        super.setOrientation(HORIZONTAL)
        gravity = Gravity.CENTER_VERTICAL
        badgeParams.marginEnd = appearance.spacing
      }
      Orientation.Vertical -> {
        super.setOrientation(VERTICAL)
        gravity = Gravity.CENTER_VERTICAL
        badgeParams.bottomMargin = appearance.spacing
      }
    }

    addView(badge, badgeParams)
    addView(descriptionView)
    updateScore()
    updateDescription()
  }

  final override fun addView(child: View?) {
    super.addView(child)
  }

  final override fun addView(child: View?, index: Int) {
    super.addView(child, index)
  }

  final override fun addView(child: View?, width: Int, height: Int) {
    super.addView(child, width, height)
  }

  final override fun addView(child: View?, params: ViewGroup.LayoutParams?) {
    super.addView(child, params)
  }

  final override fun addView(child: View?, index: Int, params: ViewGroup.LayoutParams?) {
    super.addView(child, index, params)
  }

  private fun updateScore() {
    val score = score()
    val tintList = selectors.color(score)
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

  private fun updateDescription() {
    val score = score()
    val subtitle = subtitle(score)
    val subtitleAppearance = appearance.subtitle

    if (subtitleAppearance != null && !subtitle.isNullOrEmpty()) {
      descriptionView.text = SpannableStringBuilder(title(score))
        .append('\n')
        .apply {
          val start = length
          append(subtitle, BpkFontSpan(subtitleAppearance), Spannable.SPAN_INCLUSIVE_INCLUSIVE)
          setSpan(ForegroundColorSpan(resources.getColor(R.color.bpkGray500)), start, length, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
        }
    } else {
      descriptionView.text = title(score)
    }
  }
}
