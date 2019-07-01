package net.skyscanner.backpack.button.internal

import android.graphics.Rect
import android.text.TextPaint
import androidx.annotation.VisibleForTesting

internal class TextMeasurement(private val paint: TextPaint) {
  private var textBoundsRect: Rect? = null

  fun getTextWidth(text: String): Int {
    if (textBoundsRect == null) {
      textBoundsRect = Rect()
    }
    val longest = findLongest(text).toUpperCase()
    paint.getTextBounds(longest, 0, longest.length, textBoundsRect)
    return textBoundsRect!!.width()
  }

  @VisibleForTesting
  internal fun findLongest(text: String): String {
    if (text.isEmpty()) {
      return ""
    }

    val lines = text.split("\n")
    return lines.foldRight(lines[0]) { line: String, longest: String ->
      if (line.length > longest.length) line else longest
    }
  }
}
