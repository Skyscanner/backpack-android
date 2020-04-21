package net.skyscanner.backpack.util

import android.content.res.ColorStateList

internal fun ColorStateList.getColorForState(state: IntArray) =
  getColorForState(state, defaultColor)
