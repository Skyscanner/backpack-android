package net.skyscanner.backpack.text.internal

import android.content.Context
import android.graphics.Typeface
import android.util.TypedValue
import net.skyscanner.backpack.R
import net.skyscanner.backpack.text.BpkText
import net.skyscanner.backpack.text.FontCache

internal object FontFamilyResolver {

  private val fontAttributes = intArrayOf(
    R.attr.bpkFontFamilyBase,
    R.attr.bpkFontFamilyEmphasized,
    R.attr.bpkFontFamilyHeavy
  )

  private fun getFontResources(context: Context): List<Typeface?> {
    return fontAttributes.map {
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

  operator fun invoke(context: Context, fontWeight: BpkText.Weight): Typeface? {
    val (fontBase, fontEmphasized, fontHeavy) = getFontResources(context)

    return when (fontWeight) {
      BpkText.Weight.EMPHASIZED -> fontEmphasized
      BpkText.Weight.HEAVY -> fontHeavy
      BpkText.Weight.NORMAL -> fontBase
    }
  }
}
