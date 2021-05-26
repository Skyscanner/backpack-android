package net.skyscanner.backpack.util

import android.graphics.Rect
import kotlin.math.min

internal fun Rect.smallestDimension(): Int =
  min(width(), height())
