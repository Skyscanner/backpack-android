package net.skyscanner.backpack.dialog.internal

import android.content.Context
import net.skyscanner.backpack.R

internal class DialogConstants(val context: Context) {

  // 4dp
  private val spacingSm = context.resources.getDimension(R.dimen.bpkSpacingSm)

  // 72dp
  val iconContainerSize = (spacingSm * 18).toInt()

  // 36
  val iconContainerRadius = (iconContainerSize / 2.0).toFloat()

  // 4
  val iconContainerBorderSize = spacingSm

  // 32
//  val contentContainerMarginTop = iconContainerSize / 2

  // 48
//  val contentContainerPaddingTop = (contentContainerMarginTop + context.resources.getDimension(R.dimen.bpkSpacingBase)).toInt()

  // 8
//  val contentContainerPaddingBottom = context.resources.getDimension(R.dimen.bpkSpacingMd).toInt()

  // 32dp
//  val contentContainerPaddingHorizontal = context.resources.getDimension(R.dimen.bpkSpacingXl).toInt()

  // 4dp
//  val contentRadius = context.resources.getDimension(R.dimen.bpkBorderRadiusSm)

  // 8dp
//  val buttonStackItemSpace = context.resources.getDimension(R.dimen.bpkSpacingMd).toInt()
}
