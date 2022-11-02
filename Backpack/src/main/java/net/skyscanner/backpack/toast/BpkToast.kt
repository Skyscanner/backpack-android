/**
 * Backpack for Android - Skyscanner's Design System
 * <p>
 * Copyright 2018 Skyscanner Ltd
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.skyscanner.backpack.toast

import android.content.Context
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.widget.Toast
import androidx.annotation.StringRes
import net.skyscanner.backpack.text.BpkFontSpan
import net.skyscanner.backpack.text.BpkText
import net.skyscanner.backpack.text.BpkText.Companion.getFont
import net.skyscanner.backpack.text.BpkText.FontDefinition

object BpkToast {
  const val LENGTH_SHORT = Toast.LENGTH_SHORT
  const val LENGTH_LONG = Toast.LENGTH_LONG

  @JvmStatic
  fun makeText(context: Context, @StringRes text: Int, duration: Int): Toast =
    makeText(context, context.resources.getText(text), duration)

  @JvmStatic
  fun makeText(context: Context, text: CharSequence?, duration: Int): Toast =
    Toast.makeText(context, wrapText(getFont(context, BpkText.TextStyle.Footnote), text), duration)

  private fun wrapText(font: FontDefinition, text: CharSequence?): CharSequence? =
    if (text == null) {
      null
    } else SpannableStringBuilder()
      .append(text, BpkFontSpan(font), Spanned.SPAN_INCLUSIVE_INCLUSIVE)
}
