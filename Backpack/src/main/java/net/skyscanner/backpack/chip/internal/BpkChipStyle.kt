package net.skyscanner.backpack.chip.internal

import android.graphics.drawable.Drawable
import androidx.annotation.ColorInt

internal interface BpkChipStyle {

  val background: Drawable

  @get:ColorInt
  @setparam:ColorInt
  var selectedBackgroundColor: Int

  @get:ColorInt
  @setparam:ColorInt
  var backgroundColor: Int

  @get:ColorInt
  @setparam:ColorInt
  var disabledBackgroundColor: Int
}
