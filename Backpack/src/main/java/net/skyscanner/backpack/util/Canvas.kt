package net.skyscanner.backpack.util

import android.graphics.Canvas

internal inline fun Canvas.withSave(block: Canvas.() -> Unit) {
  val count = save()
  block()
  restoreToCount(count)
}
