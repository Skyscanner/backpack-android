package net.skyscanner.backpack.rating.internal

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.LayerDrawable
import android.util.AttributeSet
import androidx.core.content.ContextCompat
import net.skyscanner.backpack.R
import net.skyscanner.backpack.rating.BpkRating
import net.skyscanner.backpack.util.get
import net.skyscanner.backpack.util.use

internal class RatingSelectors(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
) {

  private val scoresCount = BpkRating.Score.values().size

  private var icons: Drawable? = null
  var icon: (BpkRating.Score) -> Drawable? = {
    val icons = icons
    if (icons is LayerDrawable && icons.numberOfLayers >= scoresCount) {
      icons[it.index]
    } else {
      icons
    }
  }

  private var titleArray: Array<CharSequence>? = null
  private var titleString: CharSequence? = null
  var title: (BpkRating.Score) -> CharSequence? = {
    val array = titleArray
    if (array != null && array.size >= scoresCount) {
      array[it.index]
    } else {
      titleString
    }
  }

  private var subtitleArray: Array<CharSequence>? = null
  private var subtitleString: CharSequence? = null
  var subtitle: (BpkRating.Score) -> CharSequence? = {
    val array = subtitleArray
    if (array != null && array.size >= scoresCount) {
      array[it.index]
    } else {
      subtitleString
    }
  }

  val color: (BpkRating.Score) -> ColorStateList

  init {
    var colorsDrawable: LayerDrawable? = null
    context.theme.obtainStyledAttributes(
      attrs,
      R.styleable.BpkRating,
      defStyleAttr, 0
    ).use {
      colorsDrawable = it.getDrawable(R.styleable.BpkRating_ratingColor) as? LayerDrawable
      icons = it.getDrawable(R.styleable.BpkRating_ratingIcon)
      titleString = it.getText(R.styleable.BpkRating_ratingTitle)
      if (titleString == null) {
        titleArray = it.getTextArray(R.styleable.BpkRating_ratingTitle)
      }
      subtitleString = it.getText(R.styleable.BpkRating_ratingSubtitle)
      if (subtitleString == null) {
        subtitleArray = it.getTextArray(R.styleable.BpkRating_ratingSubtitle)
      }
    }

    val colors = colorsDrawable?.let { ld ->
      Array(ld.numberOfLayers) { ColorStateList.valueOf(ld.get<ColorDrawable>(it).color) }
    } ?: arrayOf(
      ColorStateList.valueOf(ContextCompat.getColor(context, R.color.bpkRed500)),
      ColorStateList.valueOf(ContextCompat.getColor(context, R.color.bpkYellow500)),
      ColorStateList.valueOf(ContextCompat.getColor(context, R.color.bpkGreen500))
    )

    this.color = { colors[it.index] }
  }

  private val BpkRating.Score.index: Int
    get() = when (this) {
      BpkRating.Score.Low -> 0
      BpkRating.Score.Medium -> 1
      BpkRating.Score.High -> 2
    }
}
