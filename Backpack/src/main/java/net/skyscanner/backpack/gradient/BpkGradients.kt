package net.skyscanner.backpack.gradient

import android.content.Context
import android.graphics.drawable.GradientDrawable
import androidx.core.content.ContextCompat
import net.skyscanner.backpack.R

open class BpkGradients @JvmOverloads constructor(
  context: Context,
  orientation: GradientDrawable.Orientation = GradientDrawable.Orientation.TL_BR
) : GradientDrawable(orientation, intArrayOf(ContextCompat.getColor(context, R.color.bpkBlue500), ContextCompat.getColor(context, R.color.bpkPrimaryGradientLight))) {

  companion object {
    @Deprecated(message = "Use BpkGradients constructor", replaceWith = ReplaceWith("BpkGradients(context, orientation)", "net.skyscanner.backpack.gradient.BpkGradients"))
    fun getPrimary(context: Context, orientation: GradientDrawable.Orientation = GradientDrawable.Orientation.TL_BR): BpkGradients {
      return BpkGradients(context, orientation)
    }
  }
}
