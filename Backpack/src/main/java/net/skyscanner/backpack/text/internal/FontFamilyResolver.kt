package net.skyscanner.backpack.text.internal

import android.content.Context
import android.graphics.Typeface
import android.util.TypedValue
import net.skyscanner.backpack.R
import net.skyscanner.backpack.text.BpkText
import net.skyscanner.backpack.text.FontCache

class FontFamilyResolver(val context: Context) {

  private val fontResources by lazy {
    val fontAttributes = intArrayOf(
      R.attr.bpkFontFamilyBase,
      R.attr.bpkFontFamilyEmphasized,
      R.attr.bpkFontFamilyHeavy
    )

    fontAttributes.map {
      val outValue = TypedValue()
      val resolved = context.theme.resolveAttribute(it, outValue, true)

      if (resolved && outValue.resourceId == 0) {
        Typeface.create(outValue.string.toString(), Typeface.NORMAL)
      } else if (resolved) {
        FontCache[outValue.resourceId, context]
      } else {
        null
      }
    }
  }

  fun getForWeight(fontWeight: BpkText.Weight): Typeface? {
    val (fontBase, fontEmphasized, fontHeavy) = fontResources

    return when (fontWeight) {
      BpkText.Weight.EMPHASIZED -> fontEmphasized
      BpkText.Weight.HEAVY -> fontHeavy
      BpkText.Weight.NORMAL -> fontBase
    }
  }
}
