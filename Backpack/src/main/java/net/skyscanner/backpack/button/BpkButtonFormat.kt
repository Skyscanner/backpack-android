package net.skyscanner.backpack.button

import androidx.annotation.DimenRes
import net.skyscanner.backpack.R
import net.skyscanner.backpack.text.BpkText

enum class BpkButtonFormat {
  Small,
  Large,
  ;

  internal companion object {
    internal fun fromId(id: Int): BpkButtonFormat =
      when (id) {
        0 -> Small
        1 -> Large
        else -> throw IllegalArgumentException()
      }
  }
}

internal val BpkButtonFormat.textStyle: BpkText.TextStyle
  get() =
    when (this) {
      BpkButtonFormat.Small -> BpkText.TextStyle.Label2
      BpkButtonFormat.Large -> BpkText.TextStyle.Label1
    }

@get:DimenRes
internal val BpkButtonFormat.iconSize: Int
  get() =
    when (this) {
      BpkButtonFormat.Small -> R.dimen.bpkSpacingBase
      BpkButtonFormat.Large -> R.dimen.bpkSpacingLg
    }

@get:DimenRes
internal val BpkButtonFormat.minHeight: Int
  get() =
    when (this) {
      BpkButtonFormat.Small -> R.dimen.bpk_button_default_height
      BpkButtonFormat.Large -> R.dimen.bpk_button_large_height
    }

@get:DimenRes
internal val BpkButtonFormat.horizontalPadding: Int
  get() =
    R.dimen.bpkSpacingBase

@get:DimenRes
internal val BpkButtonFormat.horizontalSpacing: Int
  get() =
    R.dimen.bpkSpacingMd
