package net.skyscanner.backpack.text

import android.content.Context
import android.text.TextPaint
import android.text.style.CharacterStyle

class BpkFontSpan(private val font: BpkText.FontDefinition) : CharacterStyle() {

  constructor(context: Context, textStyle: Int = BpkText.BASE, weight: BpkText.Weight = BpkText.Weight.NORMAL) :
    this(BpkText.getFont(context, textStyle, weight))

  override fun updateDrawState(tp: TextPaint) =
    font.applyTo(tp)
}
