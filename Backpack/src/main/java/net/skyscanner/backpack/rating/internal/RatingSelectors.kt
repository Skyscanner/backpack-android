package net.skyscanner.backpack.rating.internal

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.TypedArray
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.LayerDrawable
import android.util.AttributeSet
import android.util.TypedValue
import androidx.annotation.StyleableRes
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
    icons
      ?.takeIf { d -> d is LayerDrawable && d.numberOfLayers >= scoresCount }
      ?.let { d -> (d as LayerDrawable).getDrawable(it.index) }
      ?: icons
  }

  private var titles: Array<CharSequence>? = null
  var title: (BpkRating.Score) -> CharSequence? = {
    titles?.let { array ->
      if (array.size >= scoresCount) {
        array[it.index]
      } else {
        array.getOrNull(0)
      }
    }
  }

  private var subtitles: Array<CharSequence>? = null
  var subtitle: (BpkRating.Score) -> CharSequence? = {
    subtitles?.let { array ->
      if (array.size >= scoresCount) {
        array[it.index]
      } else {
        array.getOrNull(0)
      }
    }
  }

  val colors: (BpkRating.Score) -> ColorStateList

  init {
    var colorsDrawable: LayerDrawable? = null
    context.theme.obtainStyledAttributes(
      attrs,
      R.styleable.BpkRating,
      defStyleAttr, 0
    ).use {
      colorsDrawable = it.getDrawable(R.styleable.BpkRating_ratingColor) as? LayerDrawable
      icons = it.getDrawable(R.styleable.BpkRating_ratingIcon)
      titles = it.resolveStringOrArray(R.styleable.BpkRating_ratingTitle)
      subtitles = it.resolveStringOrArray(R.styleable.BpkRating_ratingSubtitle)
    }

    val colors = colorsDrawable?.let { ld ->
      Array(ld.numberOfLayers) {
        val cd = ld.getDrawable(it) as ColorDrawable
        ColorStateList.valueOf(cd.color)
      }
    } ?: arrayOf(
      ColorStateList.valueOf(ContextCompat.getColor(context, R.color.bpkRed500)),
      ColorStateList.valueOf(ContextCompat.getColor(context, R.color.bpkYellow500)),
      ColorStateList.valueOf(ContextCompat.getColor(context, R.color.bpkGreen500))
    )

    this.colors = { colors[it.index] }
  }

  private fun TypedArray.resolveStringOrArray(@StyleableRes index: Int): Array<CharSequence>? {
    val tv = TypedValue()
    if (getValue(index, tv)) {

      if (tv.string != null) {
        return arrayOf(tv.string)
      }

      if (tv.resourceId != 0) {
        if (resources.getResourceTypeName(tv.resourceId) == "string") {
          return arrayOf(resources.getString(tv.resourceId))
        }
      }

      return resources.getTextArray(tv.resourceId)
    }
    return null
  }

  private val BpkRating.Score.index: Int
    get() = when (this) {
      BpkRating.Score.Low -> 0
      BpkRating.Score.Medium -> 1
      BpkRating.Score.High -> 2
    }
}
