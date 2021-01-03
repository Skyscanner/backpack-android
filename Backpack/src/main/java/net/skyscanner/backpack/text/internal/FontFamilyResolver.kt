/**
 * Backpack for Android - Skyscanner's Design System
 *
 * Copyright 2018-2021 Skyscanner Ltd
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
