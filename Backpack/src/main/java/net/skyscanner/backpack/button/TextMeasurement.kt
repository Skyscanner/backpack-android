package net.skyscanner.backpack.button

import android.graphics.Rect
import android.text.TextPaint
import java.util.StringTokenizer

private const val DELIMITERS = "\n"

internal class TextMeasurement(private val paint: TextPaint) {
  private var textBoundsRect: Rect? = null

  fun getTextWidth(text: String): Int {
    if (textBoundsRect == null) {
      textBoundsRect = Rect()
    }
    val tokenized = tokenize(text)
    paint.getTextBounds(tokenized, 0, tokenized.length, textBoundsRect)
    return textBoundsRect!!.width()
  }

  private fun tokenize(text: String): String {
    if (text.isEmpty()) {
      return ""
    }
    val list = mutableListOf<String>()
    val tokenizer = StringTokenizer(text, DELIMITERS, false)
    while (tokenizer.hasMoreTokens()) {
      list.add(tokenizer.nextToken())
    }
    if (list.size == 1) {
      return list.get(0).toUpperCase()
    }
    var longPart = list[0]
    for (i in 0 until list.size - 1) {
      if (list.get(i + 1).length > list.get(i).length) {
        longPart = list[i + 1]
      }
    }

    return longPart.toUpperCase()
  }
}
