package net.skyscanner.backpack.chip.internal

import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import androidx.annotation.ColorInt

internal interface BpkChipStyle {

  val background: Drawable

  val text: ColorStateList

  @get:ColorInt
  @setparam:ColorInt
  var selectedBackgroundColor: Int

  @get:ColorInt
  @setparam:ColorInt
  var backgroundColor: Int

  @get:ColorInt
  @setparam:ColorInt
  var disabledBackgroundColor: Int

  @get:ColorInt
  @setparam:ColorInt
  var textColor: Int
}
