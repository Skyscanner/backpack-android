package net.skyscanner.backpack.util

import android.view.View
import android.view.ViewGroup

internal inline fun ViewGroup.forEachIndexed(block: (Int, View) -> Unit) {
  for (i in 0 until childCount) {
    block(i, getChildAt(i))
  }
}

internal inline fun ViewGroup.forEach(block: (View) -> Unit) {
  for (i in 0 until childCount) {
    block(getChildAt(i))
  }
}
