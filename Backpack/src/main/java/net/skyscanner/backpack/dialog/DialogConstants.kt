package net.skyscanner.backpack.dialog

import android.content.Context
import net.skyscanner.backpack.R

internal class DialogConstants(val context: Context) {

  private val spacingSm = context.resources.getDimension(R.dimen.bpkSpacingSm)

  val iconContainerSize = (spacingSm * 18).toInt()
  val iconContainerRadius = (iconContainerSize / 2.0).toFloat()
  val iconContainerBorderSize = spacingSm

  val contentContainerMarginTop = iconContainerSize / 2
  val contentContainerPaddingTop = (contentContainerMarginTop + context.resources.getDimension(R.dimen.bpkSpacingBase)).toInt()
  val contentContainerPaddingBottom = context.resources.getDimension(R.dimen.bpkSpacingMd).toInt()
  val contentContainerPaddingHorizontal = context.resources.getDimension(R.dimen.bpkSpacingXl).toInt()
  val contentRadius = context.resources.getDimension(R.dimen.bpkBorderRadiusSm)

  val buttonStackItemSpace = context.resources.getDimension(R.dimen.bpkSpacingMd).toInt()
}
