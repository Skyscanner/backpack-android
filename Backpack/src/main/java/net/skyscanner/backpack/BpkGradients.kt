package net.skyscanner.backpack

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.support.v4.content.ContextCompat
import net.skyscanner.backpack.core.R

/**
 * Backpack gradient utility
 */
object BpkGradients {

  /**
   * @param orientation The orientation of the requested gradient. Default is TL_BR
   * @return Gradient drawable with default backpack colors
   */
  fun getPrimary(context: Context, orientation: GradientDrawable.Orientation = GradientDrawable.Orientation.TL_BR): GradientDrawable {

    return GradientDrawable(
      orientation,
      intArrayOf(ContextCompat.getColor(context, R.color.bpkBlue500), ContextCompat.getColor(context, R.color.bpkPrimaryGradientLight)))
  }

}
