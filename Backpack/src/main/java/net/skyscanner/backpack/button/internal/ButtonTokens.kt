package net.skyscanner.backpack.button.internal

import android.content.Context
import net.skyscanner.backpack.R

internal class ButtonTokens(val context: Context) {
  val bpkSpacingBase = context.resources.getDimensionPixelSize(R.dimen.bpkSpacingBase)
  val bpkSpacingLg = context.resources.getDimensionPixelSize(R.dimen.bpkSpacingLg)
  val bpkSpacingMd = context.resources.getDimensionPixelSize(R.dimen.bpkSpacingMd)
  val bpkSpacingSm = context.resources.getDimensionPixelSize(R.dimen.bpkSpacingSm)
  val bpkBorderSizeLg = context.resources.getDimensionPixelSize(R.dimen.bpkBorderSizeLg)
}
