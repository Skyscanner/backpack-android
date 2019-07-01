package net.skyscanner.backpack.text

import android.content.Context
import android.text.TextPaint
import android.text.style.CharacterStyle
import net.skyscanner.backpack.util.BpkTheme

class BpkPrimaryColorSpan(context: Context) : CharacterStyle() {

  private val color = BpkTheme.getPrimaryColor(context)

  override fun updateDrawState(tp: TextPaint) {
    tp.color = color
  }
}
