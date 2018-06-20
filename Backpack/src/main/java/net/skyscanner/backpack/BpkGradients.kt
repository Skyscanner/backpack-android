package net.skyscanner.backpack

import android.graphics.drawable.GradientDrawable

import io.github.backpack.backpack.R

/**
 * Backpack gradient utility
 */
object BpkGradients {

  /**
   * @param orientation The orientation of the requested gradient. Default is TL_BR
   * @return Gradient drawable with default backpack colors
   */
  operator fun get(orientation: GradientDrawable.Orientation = GradientDrawable.Orientation.TL_BR): GradientDrawable {
    return GradientDrawable(
      orientation,
      intArrayOf(R.color.bpkBlue500, R.color.bpkWhite))
  }
}
