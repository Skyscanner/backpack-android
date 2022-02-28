package net.skyscanner.backpack.button

import androidx.annotation.DimenRes
import net.skyscanner.backpack.R
import net.skyscanner.backpack.text.BpkText

enum class BpkButtonSize {
  Default,
  Large,
}

internal val BpkButtonSize.textStyle: BpkText.TextStyle
  get() =
    when (this) {
      BpkButtonSize.Default -> BpkText.TextStyle.Label2
      BpkButtonSize.Large -> BpkText.TextStyle.Label1
    }

@get:DimenRes
internal val BpkButtonSize.iconSize: Int
  get() =
    when (this) {
      BpkButtonSize.Default -> R.dimen.bpkSpacingBase
      BpkButtonSize.Large -> R.dimen.bpkSpacingLg
    }

@get:DimenRes
internal val BpkButtonSize.minHeight: Int
  get() =
    when (this) {
      BpkButtonSize.Default -> R.dimen.bpk_button_default_height
      BpkButtonSize.Large -> R.dimen.bpk_button_large_height
    }

@get:DimenRes
internal val BpkButtonSize.horizontalPadding: Int
  get() =
    R.dimen.bpkSpacingBase

@get:DimenRes
internal val BpkButtonSize.horizontalSpacing: Int
  get() =
    R.dimen.bpkSpacingMd
